package com.asm_java6_pc00725.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.asm_java6_pc00725.entity.Category;

public interface CategoryDAO extends JpaRepository<Category, Integer> {

	@Query(value = "SELECT DISTINCT(c.Name), c.id FROM dbo.Categories as c, dbo.Products p WHERE p.CategoryId = c.Id", nativeQuery = true)
	List<Category> getCateProduct();

	@Transactional
	@Modifying
	@Query("Update Category c set c.status = false where c.id = ?1")
	void updateStatus(Integer id);
}
