package com.asm_java6_pc00725.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.asm_java6_pc00725.dao.OrderDAO;
import com.asm_java6_pc00725.dao.OrderDetailDAO;
import com.asm_java6_pc00725.entity.Accounts;
import com.asm_java6_pc00725.entity.Order;
import com.asm_java6_pc00725.entity.OrderDetail;
import com.asm_java6_pc00725.service.AccountsService;
import com.asm_java6_pc00725.service.OrderService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	OrderDAO orderDAO;
	@Autowired
	HttpServletRequest request;
	@Autowired
	AccountsService AccountsService;
	@Autowired
	OrderDetailDAO orderDetailDAO;

	@Override
	public Order create(JsonNode orderData) {
		ObjectMapper mapper = new ObjectMapper();
		Order order = mapper.convertValue(orderData, Order.class);
		// chuyển đổi JSON thành cấu trúc theo lớp Order
		Accounts accounts = AccountsService.findById(request.getRemoteUser());
		order.setAccount(accounts);
		orderDAO.save(order); // lưu vào order

		TypeReference<List<OrderDetail>> type = new TypeReference<List<OrderDetail>>() {
		};
		List<OrderDetail> details = mapper.convertValue(orderData.get("orderDetails"), type).stream()
				.peek(d -> d.setOrder(order)).collect(Collectors.toList());
		// Chuyển đổi đổi tượng orderDetails trong Order thành lớp OrderDetail.Và thu
		// thập thành list
		orderDetailDAO.saveAll(details); // Lưu lại toàn bộ List
		return order;

	}

	@Override
	public Order findById(Long id) {
		return orderDAO.findById(id).get();
	}

	@Override
	public List<Order> findByUsername(String username) {
		// TODO Auto-generated method stub
		return orderDAO.findByUsername(username);
	}

	@Override
	public Page<Order> findByUsername(String username, PageRequest of) {
		List<Order> order = orderDAO.findByUsername(username);
		int pageSize = of.getPageSize();
		int currentPage = of.getPageNumber();
		int startItem = currentPage * pageSize;
		List<Order> list;
		if (order.size() < startItem) {
			list = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, order.size());
			list = order.subList(startItem, toIndex);
		}
		Page<Order> orderPage = new PageImpl<Order>(list, PageRequest.of(currentPage, pageSize), order.size());
		return orderPage;
	}

	/*
	 * @Override public List<Order> findByUsernameAndId(String username, Long id) {
	 * return orderDAO.findByUsernameAndId(username, id); }
	 */
}
