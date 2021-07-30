package com.asm_java6_pc00725.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.asm_java6_pc00725.entity.Product;
import com.asm_java6_pc00725.entity.Report;

public interface ProductDAO extends JpaRepository<Product, Integer> {

	@Query("SELECT p FROM Product p WHERE p.category.id= ?1")
	Page<Product> findByCategoryId(Integer id, Pageable pageable);

	@Query("SELECT new Report(o.category, sum(o.price), count(o)) " + " FROM Product o " + " GROUP BY o.category"
			+ " ORDER BY sum(o.price) DESC")
	List<Report> getInventoryByCategory();

	@Query("SELECT new Report(o.product, sum(o.price * o.quantity), sum(o.quantity)) FROM OrderDetail o"
			+ " GROUP BY o.product" + " ORDER BY sum(o.price * o.quantity) DESC")
	List<Report> reportTheoProduct();

	/*
	 * @Query(value =
	 * "SELECT * FROM dbo.Products AS a  WHERE a.DiscountId > 0 ORDER BY (SELECT Discount FROM dbo.Discount WHERE a.DiscountId = Id ) DESC"
	 * , nativeQuery = true)
	 */
	@Query("SELECT o FROM Product o WHERE o.discount > 0 ORDER BY o.discount.discount DESC")
	List<Product> getTopGiamGia();

	@Query(value = "SELECT * FROM dbo.Products AS a  ORDER BY (SELECT Discount FROM dbo.Discount WHERE a.DiscountId = Id ) DESC", nativeQuery = true)
	Page<Product> findAllDescDiscount(Pageable pageable);

	@Query("SELECT o FROM Product o WHERE o.name like ?1")
	Page<Product> findProductByKeyword(String keyword, Pageable pageable);

	@Query("SELECT o FROM Product o WHERE o.category.name like ?1")
	Page<Product> findCategoryByKeyword(String keyword, Pageable pageable);

	@Transactional
	@Modifying
	@Query("Update Product c set c.status = false where c.id = ?1")
	void updateStatus(Integer id);

}
