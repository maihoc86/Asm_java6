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

import com.asm_java6_pc00725.entity.Product;
import com.asm_java6_pc00725.entity.Report;
import com.asm_java6_pc00725.service.ProductService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/products")
public class ProductRestController {

	@Autowired
	ProductService productService;

	@GetMapping("{id}")
	public ResponseEntity<Product> getOne(@PathVariable("id") Integer id) {
		return new ResponseEntity<>(productService.findById(id), HttpStatus.OK);
	}

	@GetMapping()
	public ResponseEntity<List<Product>> getList() {
		return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<Product> save(@Valid @RequestBody Product product) {
		return new ResponseEntity<>(productService.create(product), HttpStatus.OK);
	}

	@PutMapping("{id}")
	public ResponseEntity<Product> put(@PathVariable("id") Integer id, @Valid @RequestBody Product product) {
		return new ResponseEntity<>(productService.update(product), HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Integer id) {
		productService.deleteById(id);
	}

	@GetMapping("/thongke/sp")
	public ResponseEntity<List<Report>> getTK_SP() {
		return new ResponseEntity<>(productService.getTk_sp(), HttpStatus.OK);
	}

	@GetMapping("/thongke/loai")
	public List<Report> getTK_Loai() {
		return productService.getTk_loai();
	}

	@GetMapping("/topgiamgia")
	public List<Product> get_topGiamGia() {
		System.out.println(productService.getTopGiamGia());
		return productService.getTopGiamGia();
	}

}
