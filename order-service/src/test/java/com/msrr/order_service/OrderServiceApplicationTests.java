package com.msrr.order_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msrr.order_service.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class OrderServiceApplicationTests {

	@Container
	static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql");
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private OrderRepository orderRepository;

	@BeforeEach
	public void setUp() {
		mySQLContainer.start();
		System.setProperty("spring.datasource.url", mySQLContainer.getJdbcUrl());
		System.setProperty("spring.datasource.username", mySQLContainer.getUsername());
		System.setProperty("spring.datasource.password", mySQLContainer.getPassword());
	}
	@Test
	void contextLoads() {
	}

}
