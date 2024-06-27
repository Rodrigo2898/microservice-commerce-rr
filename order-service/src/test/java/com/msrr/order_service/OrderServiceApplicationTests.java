package com.msrr.order_service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msrr.order_service.dto.OrderLineItemsDto;
import com.msrr.order_service.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
	public void ShouldCreateOrder() throws Exception {
		OrderLineItemsDto orderLineItemsDto = new OrderLineItemsDto(1L, "Iphone_13",  new BigDecimal(2000), 2);
		OrderLineItemsDto orderLineItemsDto2 = new OrderLineItemsDto(2L, "Samsung_NOVO",  new BigDecimal(3000), 3);

		List<OrderLineItemsDto> orderLineItemsDtoList = Arrays.asList(orderLineItemsDto, orderLineItemsDto2);

		String orderRequestString = objectMapper.writeValueAsString(orderLineItemsDtoList);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/order")
						.contentType(MediaType.APPLICATION_JSON) // Set the content type
						.content(orderRequestString)) // Pass the request body
				.andExpect(status().isCreated());
		Assertions.assertEquals(1, orderRepository.findAll().size());
	}
}
