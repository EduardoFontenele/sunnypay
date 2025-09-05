package br.com.sunnypay.bdd.config;

import br.com.sunnypay.SunnypayApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SunnypayApplication.class)
public class CucumberSpringConfiguration {
}
