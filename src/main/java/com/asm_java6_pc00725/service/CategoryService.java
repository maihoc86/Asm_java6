package com.asm_java6_pc00725.service;

import java.util.List;

import com.asm_java6_pc00725.entity.Category;

public interface CategoryService {

	List<Category> findAll();

	List<Category> getCateProduct();

	Category update(Category category);

	Category create(Category category);

	void update(Integer id);

}
