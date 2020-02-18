package taa.poc.springbootsoapclient;

import org.springframework.stereotype.Component;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.transport.context.TransportContext;
import org.springframework.ws.transport.context.TransportContextHolder;
import org.springframework.ws.transport.http.HttpUrlConnection;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;

@Component
public class CustomClientInterceptor implements ClientInterceptor {

    ThreadLocal<String> stringThreadLocal = new ThreadLocal<>();

    @Override
    public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
        System.out.println("handleRequest: ");
        logMessage("afterCompletion", messageContext.getRequest());
        HttpUrlConnection httpUrlConnection = getHttpUrlConnection();
        HttpURLConnection connection = httpUrlConnection.getConnection();
        try {
            connection.addRequestProperty("X-FORWARDED-FOR", InetAddress.getLocalHost().getHostAddress());
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return true;
    }

    private HttpUrlConnection getHttpUrlConnection() {
        TransportContext context = TransportContextHolder.getTransportContext();
        return (HttpUrlConnection) context.getConnection();
    }

    @Override
    public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
        System.out.println("handleResponse: " );
        logMessage("afterCompletion", messageContext.getResponse());
        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
        System.out.println("handleFault");
        return true;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Exception ex) throws WebServiceClientException {
        System.out.println("afterCompletion");
        logMessage("afterCompletion", messageContext.getRequest());
    }

    public void logMessage(String id, WebServiceMessage webServiceMessage) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            webServiceMessage.writeTo(byteArrayOutputStream);

            String httpMessage = new String(byteArrayOutputStream.toByteArray());
            stringThreadLocal.set(httpMessage);
            System.out.println("stringThreadLocal: " + stringThreadLocal.get());
            System.out.println(id + "----------------------------" + httpMessage );
        } catch (Exception e) {
            System.err.println("Unable to log HTTP message.");
        }
    }
}
