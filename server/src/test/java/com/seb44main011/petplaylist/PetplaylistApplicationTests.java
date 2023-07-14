package com.seb44main011.petplaylist;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

@SpringBootTest
class PetplaylistApplicationTests {
	@MockBean
	ClientRegistrationRepository clientRegistrationRepository;
	@Test
	void contextLoads() {
	}

}
