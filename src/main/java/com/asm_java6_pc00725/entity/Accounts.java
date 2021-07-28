package com.asm_java6_pc00725.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.asm_java6_pc00725.contraint.ExistEmailConstraint;
import com.asm_java6_pc00725.contraint.ExistPhoneConstraint;
import com.asm_java6_pc00725.contraint.ExistUsernameConstraint;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
public class Accounts implements Serializable {
	@Id
	@NotNull(message = "Không được để trống Tài khoản")
	@Pattern(regexp = "^[a-zA-Z0-9](_(?!(\\.|_))|\\.(?!(_|\\.))|[a-zA-Z0-9]){0,}[a-zA-Z0-9]$", message = "Tài khoản không đúng định dạng")
	@Size(min = 6, max = 18, message = "Tài khoản phải có độ dài từ 6 - 18 ký tự")
	@ExistUsernameConstraint(message = "Tài khoản đã tồn tại")
	private String username;
	@NotNull(message = "Không được để trống Họ tên")
	@Pattern(regexp = "^\\S([a-zA-Z\\xC0-\\uFFFF]{0,}[ \\-\\']{0,}){1,}$", message = "Họ tên không đúng định dạng")
	@Size(min = 4, max = 50, message = "Họ tên phải có độ dài từ 4 - 50 ký tự")
	private String fullname;
	@NotBlank(message = "Không được để trống Mật khẩu")
	@Size(min = 4, max = 50, message = "Mật khẩu phải có độ dài từ 4 - 50 ký tự")
	private String password;
	@NotNull(message = "Không được để trống Email")
	@Pattern(regexp = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$", message = "Email không đúng dịnh dạng")
	@ExistEmailConstraint(message = "Email đã tồn tại")
	private String email;
	@NotNull(message = "Không được để trống Địa chỉ")
	@Pattern(regexp = "^\\S([a-zA-Z0-9\\xC0-\\uFFFF\\.]{0,}[ \\-\\' \\.-]{0,}){1,}$", message = "Địa chỉ không đúng định dạng")
	@Size(min = 4, max = 100, message = "Địa chỉ phải có độ dài từ 4 - 100 ký tự")
	private String address;
	@ExistPhoneConstraint(message = "Số điện thoại đã tồn tại")
	@NotBlank(message = "Không được để trống số điện thoại")
	@Pattern(regexp = "(84|0[3|5|7|8|9])+([0-9]{8})\\b", message = "Số điện thoại không đúng định dạng")
	private String phone;
	private String photo;
	private Boolean status;
	private Boolean activated;
	private String reset_password_token;
	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "account")
	List<Authorities> authorities;

	@Override
	public String toString() {
		return "";
	}
}
