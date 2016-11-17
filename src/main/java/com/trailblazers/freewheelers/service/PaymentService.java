package com.trailblazers.freewheelers.service;

import com.trailblazers.freewheelers.configuration.ApplicationConfiguration;
import com.trailblazers.freewheelers.model.CreditCardPayment;
import com.trailblazers.freewheelers.model.CreditCardPaymentValidator;
import com.trailblazers.freewheelers.model.PaymentResult;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.net.ssl.SSLContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.trailblazers.freewheelers.model.PaymentResult.NET_ERR;
import static com.trailblazers.freewheelers.model.PaymentResult.RVK_CARD;
import static com.trailblazers.freewheelers.model.PaymentResult.UNAUTH;

@Service
public class PaymentService {

    private final ApplicationConfiguration applicationConfiguration;
    List<PaymentResult> creditCardResults = Arrays.asList(UNAUTH, RVK_CARD, NET_ERR);
    private RestTemplate restTemplate;

    @Autowired
    public PaymentService(ApplicationConfiguration applicationConfiguration) {
        this.applicationConfiguration = applicationConfiguration;
        this.restTemplate = new RestTemplate();
    }

    public HashMap<String, String> checkOut(CreditCardPayment creditCardPayment) {
        HashMap<String, String> errors = CreditCardPaymentValidator.verifyInputs(creditCardPayment);
        if (!errors.isEmpty())
            return errors;
        PaymentResult result = sendPaymentToGateway(creditCardPayment);
        errors = handleResult(result);
        return errors;
    }

    private HashMap<String, String> handleResult(PaymentResult result) {
        HashMap<String, String> errors = new HashMap();

        for (PaymentResult creditCardResult : creditCardResults) {
            if (result == creditCardResult){
                errors.put(creditCardResult.toString(), result.getMessage());
            }
        }

        return errors;
    }


    private PaymentResult sendPaymentToGateway(CreditCardPayment creditCard) {
        try {
            String xml = createXmlForPaymentGateway(creditCard);
            ResponseEntity<String> response = sendXmlToPaymentGateway(xml);
            return parseResponse(response);
        } catch (JAXBException | SAXException | ParserConfigurationException | IOException | NoSuchAlgorithmException e) {
            return PaymentResult.NET_ERR;
        }
    }

    private PaymentResult parseResponse(ResponseEntity<String> response) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(new ByteArrayInputStream(response.getBody().getBytes()));
        Node node = doc.getChildNodes().item(0).getFirstChild();
        return PaymentResult.valueOf(node.getNodeName());
    }

    private ResponseEntity<String> sendXmlToPaymentGateway(String xml) throws NoSuchAlgorithmException {
        String paymentGatewayUrl = applicationConfiguration.getPaymentGatewayUrl();
        CloseableHttpClient httpClient =
                HttpClients.custom()
                        .setSSLContext(SSLContext.getDefault())
                        .setSSLHostnameVerifier(new NoopHostnameVerifier())
                        .build();
        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        restTemplate.setRequestFactory(requestFactory);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/xml");
        HttpEntity<String> entity = new HttpEntity<>(xml, headers);

        return restTemplate.postForEntity(paymentGatewayUrl, entity, String.class);
    }

    private String createXmlForPaymentGateway(CreditCardPayment creditCard) throws JAXBException {
        ByteArrayOutputStream xml = new ByteArrayOutputStream();
        JAXBContext jaxbContext = JAXBContext.newInstance(CreditCardPayment.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.marshal(creditCard, xml);
        return xml.toString();
    }

    public void setRestTemplate(RestTemplate restTeplate) {
        this.restTemplate = restTeplate;
    }
}
