package com.asm_java6_pc00725.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asm_java6_pc00725.entity.OrderDetail;
import com.asm_java6_pc00725.entity.Product;
import com.asm_java6_pc00725.entity.Report;
import com.asm_java6_pc00725.service.OrderDetailService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/orderDetails")
public class OrderDetailsRestController {

	@Autowired
	OrderDetailService service;

	@GetMapping("/list")
	public List<OrderDetail> getList() {
		return service.findAll();
	}

	@PutMapping("/update/{id}")
	public OrderDetail update(@PathVariable("id") Long id, @RequestBody OrderDetail orderDetail) {
		return service.save(orderDetail);
	}

	@GetMapping("/thongke")
	public List<Report> thongke() {
		return service.thongke();
	}
}
