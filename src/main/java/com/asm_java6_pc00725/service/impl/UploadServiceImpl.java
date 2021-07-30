package com.asm_java6_pc00725.service.impl;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import com.asm_java6_pc00725.service.UploadService;

@Service
public class UploadServiceImpl implements UploadService {

	@Autowired
	ServletContext context;

	@Override
	public File save(MultipartFile file, String folder) {
		try {
			File dirFile = new File(context.getRealPath("/assets/") + folder);
			dirFile.getParentFile().mkdirs();
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}
			String s = System.currentTimeMillis() + file.getOriginalFilename();
			String nameString = Integer.toHexString(s.hashCode()) + s.substring(s.lastIndexOf("."));
			File savedFile = new File(dirFile, nameString);
			file.transferTo(savedFile);
			return savedFile;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;

	}

}
