package com.asm_java6_pc00725.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.asm_java6_pc00725.entity.OrderDetail;
import com.asm_java6_pc00725.entity.Report;

public interface OrderDetailDAO extends JpaRepository<OrderDetail, Long> {
	@Query("SELECT new Report(o, sum(o.price * o.quantity), sum(o.quantity)) FROM OrderDetail o" + " GROUP BY o"
			+ " ORDER BY sum(o.price *o.quantity) DESC")
	List<Report> reportTheoLuotMuaHang();
}
