package com.asm_java6_pc00725.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.hql.internal.ast.tree.BooleanLiteralNode;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "Products")
public class Product implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	@NotNull(message = "Không được để trống Tên sản phẩm")
	@Pattern(regexp = "^\\S([a-zA-Z0-9\\xC0-\\uFFFF]{0,}[ \\-\\']{0,1}){1,}$", message = "Tên sản phẩm không đúng định dạng")
	@Size(min = 4, max = 50, message = "Tên sản phẩm phải có độ dài ký tự từ 4 - 50 ký tự")
	String name;
	String image;
//	@Pattern(regexp = "[+-]?([0-9]*[.])?[0-9]+", message = "Giá không đúng định dạng")
	@NotNull(message = "Không được để trống giá")
	@DecimalMin(value = "1000.0", message = "Giá phải lớn hơn 1000")
	@DecimalMax(value = "100000000.0", message = "Giá phải nhỏ hơn 100 triệu")
	Double price;
	@Temporal(TemporalType.DATE)
	@Column(name = "Createdate")
	Date createDate = new Date();
	Boolean available;
	Boolean status;
	@Size(max = 500, message = "Mô tả phải có độ dài ký tự dưới 500")
	String description;

	@ManyToOne
	@JoinColumn(name = "Categoryid")
	Category category;
	@ManyToOne
	@JoinColumn(name = "Discountid")
	Discount discount;
	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "product")
	List<OrderDetail> orderDetails;

	@Override
	public String toString() {
		return "";
	}

}
