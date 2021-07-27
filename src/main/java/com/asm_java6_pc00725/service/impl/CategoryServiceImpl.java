package com.asm_java6_pc00725.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asm_java6_pc00725.dao.CategoryDAO;
import com.asm_java6_pc00725.entity.Category;
import com.asm_java6_pc00725.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryDAO dao;

	@Override
	public List<Category> findAll() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

	@Override
	public List<Category> getCateProduct() {
		// TODO Auto-generated method stub
		return dao.getCateProduct();
	}

	@Override
	public Category update(Category category) {
		return dao.save(category);
	}

	@Override
	public Category create(Category category) {
		// TODO Auto-generated method stub
		return dao.save(category);
	}

	@Override
	public void update(Integer id) {
		// TODO Auto-generated method stub
		dao.updateStatus(id);
	}

}
