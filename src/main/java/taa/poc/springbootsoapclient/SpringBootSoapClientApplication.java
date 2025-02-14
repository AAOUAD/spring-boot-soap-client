package taa.poc.springbootsoapclient;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import taa.poc.schemas.school.StudentDetailsRequest;
import taa.poc.schemas.school.StudentDetailsResponse;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;

@SpringBootApplication
public class SpringBootSoapClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSoapClientApplication.class, args);
	}

	@Bean
	CommandLineRunner lookup(WebServiceTemplate webServiceTemplate) {
		return args -> {
			String name = "Sajal";//Default Name
			if(args.length>0){
				name = args[0];
			}
			StudentDetailsRequest request = new StudentDetailsRequest();
			request.setName(name);
			StudentDetailsResponse response =(StudentDetailsResponse) webServiceTemplate.marshalSendAndReceive("http://localhost:8088/service/student-details", request, new SoapActionCallback("StudentDetailsRequestAction"));
			System.out.println("Got Response As below ========= : " + response);
			System.out.println("Name : "+response.getStudent().getName());
			System.out.println("Standard : "+response.getStudent().getStandard());
			System.out.println("Address : "+response.getStudent().getAddress());
		};
	}

}
