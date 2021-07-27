package com.asm_java6_pc00725.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.asm_java6_pc00725.entity.Order;
import com.asm_java6_pc00725.service.OrderService;

@Controller
public class OrderController {

	@Autowired
	OrderService orderService;
	@Autowired
	HttpServletRequest request;

	@RequestMapping("/order/checkout")
	public String checkout() {
		return "order/checkout";
	}

	@RequestMapping("/order/list")
	public String list(Model model, HttpServletRequest request, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(6);
		String username = request.getRemoteUser();
		Page<Order> productPage = orderService.findByUsername(username, PageRequest.of(currentPage - 1, pageSize));
		productPage.forEach(e -> {
			System.out.println(e.getId());
		});
		int totalPages = productPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			System.out.print(pageNumbers);
			model.addAttribute("pageNumbers", pageNumbers);
		}
		model.addAttribute("orders", productPage);
		model.addAttribute("pagePresent", currentPage);
		return "order/list";
	}

	@RequestMapping("/order/detail/{id}")
	public String detail(@PathVariable("id") Long id, Model model) {
		Order order = orderService.findById(id);
		if (!order.getAccount().getUsername().equals(request.getRemoteUser())) {
			return "redirect:/home/index";
		}
		model.addAttribute("order", order);
		return "order/detail";
	}

}
