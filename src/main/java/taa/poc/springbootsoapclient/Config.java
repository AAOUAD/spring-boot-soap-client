package taa.poc.springbootsoapclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;

@Configuration
public class Config {

	public static final String DEFAULT_URL = "http://localhost:8080/service/student-details";

	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath("taa.poc.schemas.school");
		return marshaller;
	}

	@Bean
	public WebServiceTemplate webServiceTemplate(Jaxb2Marshaller jaxb2Marshaller){
		WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
		webServiceTemplate.setDefaultUri(DEFAULT_URL);
		webServiceTemplate.setMarshaller(jaxb2Marshaller);
		webServiceTemplate.setUnmarshaller(jaxb2Marshaller);

		// Interceptors
		ClientInterceptor[] interceptors =
				new ClientInterceptor[] {new CustomClientInterceptor()};
		webServiceTemplate.setInterceptors(interceptors);

		return webServiceTemplate;
	}

}
