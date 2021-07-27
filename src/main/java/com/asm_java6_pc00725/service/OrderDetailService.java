package com.asm_java6_pc00725.service;

import java.util.List;

import com.asm_java6_pc00725.entity.OrderDetail;
import com.asm_java6_pc00725.entity.Report;

public interface OrderDetailService {

	List<Report> findAllReport();

	List<OrderDetail> findAll();

	OrderDetail save(OrderDetail orderDetail);

	List<Report> thongke();
}
