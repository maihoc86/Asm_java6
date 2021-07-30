package com.asm_java6_pc00725.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.asm_java6_pc00725.entity.Accounts;
import com.asm_java6_pc00725.entity.Utility;
import com.asm_java6_pc00725.service.AccountsService;
import com.asm_java6_pc00725.service.ParamService;

import net.bytebuddy.utility.RandomString;

@Controller
public class AccountController {
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	AccountsService service;
	@Autowired
	HttpServletRequest request;
	@Autowired
	ParamService param;

	@GetMapping("/my-account/update-profile")
	public String showView(Model model, @ModelAttribute("account") Accounts account) {
		model.addAttribute("account", service.findById(request.getRemoteUser()));
		return "accounts/update-profile";
	}

	@GetMapping("/sign-up")
	public String showViewSignUp(Model model) {
		if (request.getRemoteUser() != null) {
			return "redirect:/product/list";
		}
		return "accounts/sign-up";
	}

	@GetMapping("/my-account/change-password")
	public String showViewChangePass(@ModelAttribute("account") Accounts account) {
		return "accounts/change-password";
	}

	@PostMapping("/my-account/change-password")
	public String ChangePass(@RequestParam("newPassword") String password, @ModelAttribute("account") Accounts account,
			Model model) {
		Accounts accounts = service.findById(request.getRemoteUser());
		if (!accounts.getPassword().equals(account.getPassword())) {
			model.addAttribute("error", "Mật khẩu cũ không chính xác");
		} else {
			model.addAttribute("success", "Đổi mật khẩu thành công");
			accounts.setPassword(password);
		}
		service.save(accounts);
		return "accounts/change-password";
	}

	@PostMapping("/my-account/update-profile")
	public String update(Model model, @RequestParam("file") MultipartFile file,
			@Valid @ModelAttribute("account") Accounts account, Errors error) {
		if (error.getAllErrors().size() <= 3 && (error.getFieldError().getField().toString().equals("fullname")
				|| error.getFieldError().getField().toString().equals("address")
				|| error.getFieldError().getField().toString().equals("phone"))) {
			model.addAttribute("message", "Vui lòng sửa các lỗi sau:");
		}
		if (error.getAllErrors().size() <= 3 && (error.getFieldError().getField().toString().equals("username")
				|| error.getFieldError().getField().toString().equals("password")
				|| error.getFieldError().getField().toString().equals("email"))) {
			Accounts accountFind = service.findById(request.getRemoteUser());
			if (file.getOriginalFilename().equals("")) {
				accountFind.setPhoto(accountFind.getPhoto());
			} else {
				String fileString = file.getOriginalFilename();
				String[] fileCat = fileString.split("\\.");
				param.save(file, "/assets/images/user/", accountFind.getUsername() + "." + fileCat[1]);
				accountFind.setPhoto(accountFind.getUsername() + "." + fileCat[1]);
			}
			accountFind.setFullname(account.getFullname());
			accountFind.setAddress(account.getAddress());
			accountFind.setPhone(account.getPhone());
			service.save(accountFind);
			model.addAttribute("success", "Cập nhật thành công");
			model.addAttribute("account", service.findById(accountFind.getUsername()));
		}
		return "accounts/update-profile";
	}

	@GetMapping("/forgot-password")
	public String forgotPassword() {
		return "accounts/forgot-password";
	}

	@PostMapping("/forgot-password")
	public String processForgotPassword(Model model) throws UnsupportedEncodingException {
		String email = request.getParameter("email");
		String token = RandomString.make(30);

		try {
			service.updateResetPasswordToken(token, email);
			String resetPasswordLink = Utility.getSiteUrl(request) + "/reset_password?token=" + token;
			sendEmail(email, resetPasswordLink);
			model.addAttribute("message", "We have sent a reset password link to your email. Please check.");

		} catch (Exception ex) {
			model.addAttribute("error", ex.getMessage());
		}
		return "accounts/forgot-password";
	}

	public void sendEmail(String recipientEmail, String link) throws UnsupportedEncodingException, MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("contact@fclothes.com", "F-CLothes Support");
		helper.setTo(recipientEmail);

		String subject = "Đây là liên kết để đặt lại mật khẩu của bạn";

		String content = "<p>Hello,</p>" + "<p>Bạn đã yêu cầu lấy lại mật khẩu cho tài khoản của mình.</p>"
				+ "<p>Nhấp vào địa chỉ sau đây để cập nhật lại mật khẩu:</p>" + "<p><a href=\"" + link
				+ "\">Đổi mật khẩu</a></p>" + "<br>" + "<p>\r\n Bỏ qua email này nếu bạn nhớ mật khẩu của mình, "
				+ "hoặc bạn đã không thực hiện yêu cầu.</p>";

		helper.setSubject(subject);

		helper.setText(content, true);

		mailSender.send(message);
	}

	@GetMapping("/reset_password")
	public String showResetPasswordForm(@RequestParam(value = "token") String token, Model model) {
		Accounts accounts = service.getByResetPasswordToken(token);
		model.addAttribute("token", token);
		if (accounts == null) {
			model.addAttribute("message", "Invalid Token");
			return "message";
		}

		return "accounts/reset_password_form";
	}

	@PostMapping("/reset_password")
	public String processResetPassword(HttpServletRequest request, Model model) {
		String token = request.getParameter("token");
		String password = request.getParameter("password");
		System.out.println(password);
		Accounts accounts = service.getByResetPasswordToken(token);
		model.addAttribute("title", "Reset your password");
		if (accounts == null) {
			model.addAttribute("error", "Token không tồn tại !");
			return "accounts/reset_password_form";
		} else {
			System.out.println(accounts.getUsername());
			System.out.println(password);
			try {
				service.updatePassword(accounts, password);
				model.addAttribute("message", "Cập nhật mật khẩu thành công");
			} catch (Exception e) {
				model.addAttribute("error", "Cập nhật mật khẩu thất bại");
			}

			return "accounts/reset_password_form";
		}
	}
}
