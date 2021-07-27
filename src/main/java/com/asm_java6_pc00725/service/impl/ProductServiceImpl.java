package com.asm_java6_pc00725.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.asm_java6_pc00725.dao.ProductDAO;
import com.asm_java6_pc00725.entity.Product;
import com.asm_java6_pc00725.entity.Report;
import com.asm_java6_pc00725.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	ProductDAO dao;

	@Override
	public Page<Product> findAll(Pageable pageable) {
		return dao.findAll(pageable);
	}

	@Override
	public Product findById(Integer id) {
		return dao.findById(id).get();
	}

	@Override
	public Page<Product> findByCategoryId(Integer id, Pageable pageable) {
		return dao.findByCategoryId(id, pageable);
	}

	@Override
	public Product create(Product product) {
		return dao.save(product);
	}

	@Override
	public Product update(Product product) {
		return dao.save(product);
	}

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
	}

	@Override
	public List<Product> findAll() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

	@Override
	public List<Report> getTk_sp() {
		// TODO Auto-generated method stub
		return dao.reportTheoProduct();
	}

	@Override
	public List<Report> getTk_loai() {
		// TODO Auto-generated method stub
		return dao.getInventoryByCategory();
	}

	@Override
	public List<Product> getTopGiamGia() {
		System.out.println(dao.getTopGiamGia());
		return dao.getTopGiamGia();
	}

	@Override
	public Page<Product> findProductByKeyword(String keyword, Pageable pageable) {
		return dao.findProductByKeyword(keyword, pageable);
	}

	@Override
	public Page<Product> findCategoryByKeyword(String keyword, Pageable pageable) {
		return dao.findCategoryByKeyword(keyword, pageable);
	}
}
