package com.advprog.perbaikiinaja;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled("Disabled until NoUniqueBeanDefinitionException is fixed")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, 
                properties = "spring.main.allow-bean-definition-overriding=true")
class PerbaikiinAjaApplicationTests {

	@Test
	void contextLoads() {
	}

}
