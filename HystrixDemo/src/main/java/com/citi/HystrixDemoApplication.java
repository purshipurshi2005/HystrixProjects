package com.citi;

import java.net.URI;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.util.LongAdder;

@SpringBootApplication
@RestController
@EnableHystrix
@EnableSwagger2
@Slf4j
public class HystrixDemoApplication {
	
public static final LongAdder counter = new LongAdder();
	
	@Bean
	public RestTemplate rest(RestTemplateBuilder reBuilder){
		
		return reBuilder.build();
	}

		@Autowired
	private RestTemplate restTemplate; 
	
	
	public static void main(String[] args) {
		SpringApplication.run(HystrixDemoApplication.class, args);
		HystrixPlugins.getInstance().registerEventNotifier(
				new CircuitHystrixEventNotifier());
	}
	
	
	@RequestMapping("/sayHello")
	 @HystrixCommand(fallbackMethod = "fallbackHello",commandProperties={
			 @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="500"),
			 @HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value="50"),
			 @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value="3"),
			 @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value="10000")
	 })
	public String sayHello(){
		counter.increment();
		log.info("Inside SayHello::"+counter);
		URI u = URI.create("http://localhost:8081/sayHelloFromThirdParty");
		return restTemplate.getForObject(u,String.class);
		
		//return "Hello from Hystrix Demo";
	}
	
	public String fallbackHello(){
		log.info("Inside fallbackHello");
		return "Hello from Fallback";
	}
}
