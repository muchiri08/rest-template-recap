package com.muchiri.resttemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
public class RestTemplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestTemplateApplication.class, args);
	}

	record Student(String name, int admission, int age){}
	@Bean
	ApplicationRunner applicationRunner() {
		return args -> {
			String url = "http://localhost:4000/students";
			RestTemplate restTemplate = new RestTemplate();
			// getting a json string
			ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
			var students = response.getBody();
			System.out.println(students);

			// getting a list of student
			ResponseEntity<List> list = restTemplate.getForEntity(url,List.class);
			List<Student> students1 = list.getBody();
			System.out.println(students1);

			// posting. other methods eg put follows similar way
			Student student = new Student("Dwayne", 1234, 45);
			HttpEntity<Student> request = new HttpEntity<>(student);
			String resp = restTemplate.postForObject(url+"/new", request, String.class);
			System.out.println(resp);

			// post using exchange. other methods eg put follows similar way
			ResponseEntity<String> resp2 = restTemplate.exchange(url+"/new", HttpMethod.POST, request, String.class);
			System.out.println(resp2.getBody());
			System.out.println(resp2.getStatusCode());
			System.out.println(resp2.getHeaders());

			// post using execute. offers more control
		};
	}

}
