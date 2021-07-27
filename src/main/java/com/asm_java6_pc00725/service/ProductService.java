package com.asm_java6_pc00725.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.asm_java6_pc00725.entity.Product;
import com.asm_java6_pc00725.entity.Report;

public interface ProductService {

	Page<Product> findAll(Pageable pageable);

	Page<Product> findProductByKeyword(String keyword, Pageable pageable);

	Page<Product> findCategoryByKeyword(String keyword, Pageable pageable);

	List<Product> findAll();

	Product findById(Integer id);

	Page<Product> findByCategoryId(Integer id, Pageable pageable);

	Product create(Product product);

	Product update(Product product);

	void deleteById(Integer id);

	List<Report> getTk_sp();

	List<Report> getTk_loai();

	List<Product> getTopGiamGia();

}
