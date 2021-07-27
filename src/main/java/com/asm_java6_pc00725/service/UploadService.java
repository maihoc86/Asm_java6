package com.asm_java6_pc00725.service;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

	File save(MultipartFile file, String folder);
}
