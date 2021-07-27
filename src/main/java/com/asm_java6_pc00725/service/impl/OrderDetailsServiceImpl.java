package com.asm_java6_pc00725.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asm_java6_pc00725.dao.OrderDetailDAO;
import com.asm_java6_pc00725.entity.OrderDetail;
import com.asm_java6_pc00725.entity.Report;
import com.asm_java6_pc00725.service.OrderDetailService;

@Service
public class OrderDetailsServiceImpl implements OrderDetailService {

	@Autowired
	OrderDetailDAO dao;

	@Override
	public List<Report> findAllReport() {
		// TODO Auto-generated method stub
		return dao.reportTheoLuotMuaHang();
	}

	@Override
	public List<OrderDetail> findAll() {
		return dao.findAll();
	}

	@Override
	public OrderDetail save(OrderDetail orderDetail) {
		// TODO Auto-generated method stub
		return dao.save(orderDetail);
	}

	@Override
	public List<Report> thongke() {
		return dao.reportTheoLuotMuaHang();
	}

}
