package com.jiang;

import com.jiang.repository.entity.UserRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringDataJpaApplicationTests {
	static final Logger logger = LoggerFactory.getLogger(SpringDataJpaApplication.class);
	@Autowired
	private UserRepository userRepository;
	@Test
	void contextLoads() {
		User byUsername = userRepository.findByUsername("aa");
	}

}
