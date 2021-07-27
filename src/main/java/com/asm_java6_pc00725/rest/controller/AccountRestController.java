package com.asm_java6_pc00725.rest.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asm_java6_pc00725.entity.Accounts;
import com.asm_java6_pc00725.service.AccountsService;
import com.asm_java6_pc00725.service.AuthoritesService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/accounts")
public class AccountRestController {
	@Autowired
	AuthoritesService authoritesService;
	@Autowired

	AccountsService service;

	@Autowired
	HttpServletRequest request;

	@GetMapping()
	public List<Accounts> getAccounts(@RequestParam("admins") Optional<Boolean> admin) {
		if (admin.orElse(false)) {
			return authoritesService.getAdmin(request.getRemoteUser());
		}
		return service.findAll();
	}

	@GetMapping("/my-account")
	public Accounts getMyAccounts() {
		return service.findById(request.getRemoteUser());
	}

	@GetMapping("/my-account/{id}/{email}")
	public Accounts findExistEmailAndPhone(@PathVariable("id") String username, @PathVariable("email") String email) {
		return service.findEmailExist(email, username);
	}

	@GetMapping("/my-account/{id}")
	public Accounts findId(@PathVariable("id") String username) {
		System.out.println(username);
		return service.findById(username);
	}

	@PutMapping("/my-account/{id}")
	public Accounts updateAccount(@PathVariable("id") String username, @Valid @RequestBody Accounts account) {
		return service.update(account);
	}

	@PostMapping("/my-account/save2")
	public ResponseEntity<Accounts> saveMyAccount(@Valid @RequestBody Accounts account) {
		System.out.println("Vào đây");
		if (account.getPhoto() != null) {
			account.setPhoto(account.getPhoto());
		} else {
			account.setPhoto("user.png");
		}
		return new ResponseEntity<>(service.save(account), HttpStatus.OK);
	}

	@GetMapping("/list")
	public List<Accounts> getAllAccounts() {
		return service.findAll();
	}

	@DeleteMapping("/delete/{id}")

	public void delete(@PathVariable("id") String username) {
		service.delete(username);
	}
}
