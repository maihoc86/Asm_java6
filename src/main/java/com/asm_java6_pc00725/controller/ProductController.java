package com.asm_java6_pc00725.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.asm_java6_pc00725.entity.Product;
import com.asm_java6_pc00725.service.CategoryService;
import com.asm_java6_pc00725.service.ProductService;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@Controller
public class ProductController {
	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductService service;

	@RequestMapping("/product/list")
	public String list(Model model, @RequestParam("cid") Optional<Integer> id,
			@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size,
			@RequestParam("sort") Optional<String> sort) {
		String valueFieldString;
		String[] split;
		Sort tempSort;
		if (sort.isPresent()) {
			valueFieldString = sort.get();
			if (sort.get().contains("DESC")) {
				split = valueFieldString.split("DESC");
				System.out.println(split[0]);
				tempSort = Sort.by(Direction.DESC, split[0]);
			} else {
				split = valueFieldString.split("ASC");
				tempSort = Sort.by(Direction.ASC, split[0]);
			}
		} else {
			valueFieldString = "price";
			tempSort = Sort.by(Direction.DESC, valueFieldString);
		}
		model.addAttribute("sort", valueFieldString);
		int currentPage = page.orElse(0);
		int pageSize = size.orElse(6);
		if (pageSize > 15) {
			pageSize = 15;
		}
		if (id.isPresent()) {
			Pageable pageable = PageRequest.of(currentPage, pageSize, tempSort);
			Page<Product> pageF = service.findByCategoryId(id.get(), pageable);
			int totalPages = pageF.getTotalPages();
			if (totalPages > 0) {
				List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
				model.addAttribute("pageNumbers", pageNumbers);
			}
			model.addAttribute("pagePresent", currentPage);
			model.addAttribute("items", pageF);
			model.addAttribute("cid", id.get());
		} else {
			Pageable pageable = PageRequest.of(currentPage, pageSize, tempSort);
			Page<Product> pageF = service.findAll(pageable);
			int totalPages = pageF.getTotalPages();
			if (totalPages > 0) {
				List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
				model.addAttribute("pageNumbers", pageNumbers);
			}
			model.addAttribute("pagePresent", currentPage);
			model.addAttribute("items", pageF);
		}
		return "product/list";
	}

	@RequestMapping("/product/search")
	public String search(@RequestParam("key") Optional<String> keySearch,
			@RequestParam("keyword") Optional<String> valueSearch, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size, @RequestParam("sort") Optional<String> sort, Model model) {
		Page<Product> productPage = null;
		String valueFieldString;
		String[] split;
		Sort tempSort;
		if (sort.isPresent()) {
			valueFieldString = sort.get();
			if (sort.get().contains("DESC")) {
				split = valueFieldString.split("DESC");
				System.out.println(split[0]);
				tempSort = Sort.by(Direction.DESC, split[0]);
			} else {
				split = valueFieldString.split("ASC");
				tempSort = Sort.by(Direction.ASC, split[0]);
			}
		} else {
			valueFieldString = "price";
			tempSort = Sort.by(Direction.DESC, valueFieldString);
		}
		int currentPage = page.orElse(0);
		int pageSize = size.orElse(6);
		if (pageSize > 15) {
			pageSize = 15;
		}
		if (keySearch.get().equals("nameProduct")) {
			Pageable pageable = PageRequest.of(currentPage, pageSize, tempSort);
			productPage = service.findProductByKeyword("%" + valueSearch.get() + "%", pageable);
		} else {
			Pageable pageable = PageRequest.of(currentPage, pageSize, tempSort);
			productPage = service.findCategoryByKeyword("%" + valueSearch.get() + "%", pageable);
		}
		int totalPages = productPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		model.addAttribute("valueSearch", valueSearch.get());
		model.addAttribute("keySearch", valueSearch.get());
		model.addAttribute("pagePresent", currentPage);
		model.addAttribute("items", productPage);
		return "product/list";
	}

	@RequestMapping("/product/details/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {
		Product product = service.findById(id);
		model.addAttribute("item", product);
		return "product/detail";
	}

}
