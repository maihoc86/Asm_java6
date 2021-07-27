package com.asm_java6_pc00725.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asm_java6_pc00725.entity.Category;
import com.asm_java6_pc00725.entity.Product;
import com.asm_java6_pc00725.service.CategoryService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/categories")
public class CategoryRestController {
	@Autowired
	CategoryService service;

	@GetMapping()
	public List<Category> getList() {
		return service.findAll();
	}

	@GetMapping("/product")
	public List<Category> getCount() {
		return service.getCateProduct();
	}

	@PostMapping()
	public ResponseEntity<Category> save(@Valid @RequestBody Category category) {
		return new ResponseEntity<>(service.create(category), HttpStatus.OK);
	}

	@PutMapping("{id}")
	public ResponseEntity<Category> put(@PathVariable("id") String id, @Valid @RequestBody Category category) {
		return new ResponseEntity<>(service.update(category), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable("id") Integer id) {
		service.update(id);
	}

}
