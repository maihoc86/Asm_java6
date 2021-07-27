package com.asm_java6_pc00725.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.asm_java6_pc00725.entity.Order;
import com.fasterxml.jackson.databind.JsonNode;

public interface OrderService {

	Order create(JsonNode order);

	Order findById(Long id);

	List<Order> findByUsername(String username);

	Page<Order> findByUsername(String username, PageRequest of);

	/* List<Order> findByUsernameAndId(String username, Long id); */

}
