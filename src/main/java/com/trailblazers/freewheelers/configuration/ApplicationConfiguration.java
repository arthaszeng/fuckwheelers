package com.trailblazers.freewheelers.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

@Configuration
@PropertySource("env.properties")
public class ApplicationConfiguration {
    @Autowired
    private Environment env;

    public ApplicationConfiguration() throws IOException, CertificateException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        addCertificateToDefaultSSLContext("payment-freewheelers", "/payment-gateway.freewheelers.bike.crt");
    }

    public String getPaymentGatewayUrl() {
        return env.getProperty("PAYMENT_GATEWAY_URL");
    }

    private void addCertificateToDefaultSSLContext(String alias, String certificatePath) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, KeyManagementException {
        KeyStore keyStore = getDefaultKeyStore();
        Certificate certificate = getCertificate(certificatePath);
        keyStore.setCertificateEntry(alias, certificate);
        updateSSLContextWithKeyStore(keyStore);
    }

    private void updateSSLContextWithKeyStore(KeyStore keyStore) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(keyStore);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), null);
        SSLContext.setDefault(sslContext);
    }

    private Certificate getCertificate(String certificatePath) throws CertificateException {
        InputStream certificateStream = this.getClass().getResourceAsStream(certificatePath);
        return CertificateFactory.getInstance("x.509").generateCertificate(certificateStream);
    }

    private KeyStore getDefaultKeyStore() throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        String keyStorePath = Paths.get(System.getProperty("java.home"), "lib", "security", "cacerts").toString();
        keyStore.load(new FileInputStream(keyStorePath), "changeit".toCharArray());
        return keyStore;
    }
}
