package com.seb44main011.petplaylist;

import com.seb44main011.petplaylist.domain.music.service.storageService.S3Service;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
@WithMockUser
class PetplaylistApplicationTests {
	@MockBean
	private S3Service s3service;
	@Test
	@WithMockUser
	void contextLoads() {
	}

}
