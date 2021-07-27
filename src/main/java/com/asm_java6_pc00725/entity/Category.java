package com.asm_java6_pc00725.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@Entity
@NoArgsConstructor
@Table(name = "Categories")
public class Category implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	@NotBlank(message = "Không được để trống Tên loại")
	@Pattern(regexp = "^\\S([a-zA-Z\\xC0-\\uFFFF]{1,100}[ \\-\\']{0,1}){1,}$", message = "Tên loại sản phẩm không đúng định dạng")
	String name;
	Boolean status;

	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "category")
	List<Product> products;

	@Override
	public String toString() {
		return "";
	}
}
