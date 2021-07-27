package com.asm_java6_pc00725.rest.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asm_java6_pc00725.entity.Order;
import com.asm_java6_pc00725.service.AccountsService;
import com.asm_java6_pc00725.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/orders")
public class OrderRestController {

	@Autowired
	AccountsService AccountsService;
	@Autowired
	OrderService service;
	@Autowired
	HttpServletRequest request;

	@PostMapping()
	public Order create(@RequestBody JsonNode order) throws JsonProcessingException {
		/*
		 * Accounts accounts = AccountsService.findById(request.getRemoteUser());
		 * System.out.println(order); ObjectMapper mapper = new ObjectMapper(); Order
		 * newJsonNode = mapper.treeToValue(order, Order.class);
		 * System.out.println(newJsonNode); newJsonNode.setAccount(accounts);
		 * newJsonNode.setOrderDetails(newJsonNode.getOrderDetails());
		 * System.out.println(newJsonNode.getOrderDetails()); JsonNode node =
		 * mapper.convertValue(newJsonNode, JsonNode.class); System.out.println(node);
		 */
		
		return service.create(order);
	}
}
