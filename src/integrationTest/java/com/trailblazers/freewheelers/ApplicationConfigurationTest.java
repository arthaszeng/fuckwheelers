package com.trailblazers.freewheelers;

import com.trailblazers.freewheelers.configuration.ApplicationConfiguration;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class ApplicationConfigurationTest {
    @Ignore
    @Test
    public void shouldAddFreeWheelersPaymentGatewayCertificate() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        new ApplicationConfiguration();

        URL url = new URL("https://test-payment-gateway.freewheelers.bike");
        URLConnection urlConnection = url.openConnection();
        urlConnection.connect();
    }

}
