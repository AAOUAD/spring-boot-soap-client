package taa.poc.springbootsoapclient;

import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;

@Component
public class SOAPConnector {

	private WebServiceTemplate webServiceTemplate;

	public SOAPConnector(WebServiceTemplate webServiceTemplate) {
		this.webServiceTemplate = webServiceTemplate;
	}

	public Object callWebService(String url, Object request){
		return webServiceTemplate.marshalSendAndReceive(url, request);
	}

	public Object callWebService(String url, Object request, WebServiceMessageCallback webServiceMessageCallback){
		return webServiceTemplate.marshalSendAndReceive(url, request, webServiceMessageCallback);
	}

}