package com.example.demo;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

import com.netflix.config.DynamicPropertyFactory;



@SpringBootApplication
@RestController
@EnableSwagger2
@Slf4j
public class ThirdPartyComponentApplication {

	

	public static void main(String[] args) {
		SpringApplication.run(ThirdPartyComponentApplication.class, args);
	
	}
	
	private static  Random fRandom = new Random();

	
	  private static double getGaussian(double aMean, double aVariance){
	    return aMean + fRandom.nextGaussian() * aVariance;
	  }
	  

	@RequestMapping("/sayHelloFromThirdParty")
	public static  String sayHello() throws InterruptedException{

		double MEAN = DynamicPropertyFactory
			.getInstance().getDoubleProperty("app.mean",100.0f).get();
		
		double VARIANCE =  DynamicPropertyFactory
				.getInstance().getDoubleProperty("app.variance",5.0f).get();;

		    
		double timeout=getGaussian(MEAN, VARIANCE);
		    
		log.info("timeout:"+timeout);		
			
		TimeUnit.MILLISECONDS.sleep((long) timeout);
		
		
		return "Hello from ThirdPartyComponentApplication";
	}
}
