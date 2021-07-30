package com.asm_java6_pc00725.rest.controller;

import java.io.File;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.asm_java6_pc00725.service.ParamService;
import com.asm_java6_pc00725.service.UploadService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@CrossOrigin("*")
public class UploadRestController {
	@Autowired
	UploadService uploadService;

	@Autowired
	ParamService param;

	@PostMapping("/rest/upload/{folder}/{folder2}")
	public JsonNode upload(@PathParam("file") MultipartFile file, @PathVariable("folder") String folder,
			@PathParam("username") String username, @PathParam("product") String product,
			@PathVariable("folder2") String folder2) {
		File savedFile;
		if (!username.isEmpty() && folder2.equals("user")) {
			String fileString = file.getOriginalFilename();
			String[] fileCat = fileString.split("\\.");
			savedFile = param.save(file, "/assets/" + folder + "/" + folder2, username + "." + fileCat[1]);
		} else {
			savedFile = uploadService.save(file, folder + "/" + folder2);
		}
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		node.put("name", savedFile.getName());
		node.put("size", savedFile.length());
		return node;
		// trả về kiểu Json để client có thể đọc đc
	}
}
