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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Discount")
public class Discount implements Serializable {
	private static final long serialVersionUID = -4542626190732663653L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotNull
	@DecimalMin(value = "1", message = "Giảm giá phải lớn hơn 1")
	@DecimalMax(value = "100", message = "Giảm giá phải nhỏ hơn 100")
	private Double discount;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "since")
	Date since;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "todate")
	Date todate;
	private Boolean activated;
	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "discount")
	List<Product> products;

	@Override
	public String toString() {
		return "";
	}
}
