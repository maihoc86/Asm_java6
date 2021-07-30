package com.asm_java6_pc00725.rest.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asm_java6_pc00725.entity.Accounts;
import com.asm_java6_pc00725.entity.Authorities;
import com.asm_java6_pc00725.service.AuthoritesService;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/authorities")
public class AuthoritiesRestController {

	@Autowired
	HttpServletRequest request;

	@Autowired
	AuthoritesService service;

	@GetMapping
	public List<Authorities> findAll(@RequestParam("admins") Optional<Boolean> admin) {
		if (admin.orElse(false)) {
			return service.findAuthoritiesOfAdministrators();
		}
		return service.findAll();
	}

	@PostMapping
	public Authorities post(@RequestBody Authorities authorities) {
		return service.save(authorities);
	}

	@GetMapping("/checkrole")
	public Accounts getRole() {
		return service.findRole(request.getRemoteUser());
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Integer id) {
		System.out.println("ALo3");
		service.delete(id);
	}
}
