package com.asm_java6_pc00725.service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ParamService {
	@Autowired
	HttpServletRequest request;

	@Autowired
	ServletContext context;

	public String getString(String name, String defaulValue) {
		String rname = request.getParameter(name);
		return rname == null ? defaulValue : rname;
	}

	public int getInt(String name, int defaulValue) {
		String value = getString(name, String.valueOf(defaulValue));
		return Integer.parseInt(value);
	}

	public Double getDouble(String name, double defaulValue) {
		String value = getString(name, String.valueOf(defaulValue));
		return Double.parseDouble(value);
	}

	public Boolean getBoolean(String name, Boolean defaulValue) {
		String value = getString(name, String.valueOf(defaulValue));
		return Boolean.parseBoolean(value);
	}

	public Date getDate(String name, String pattern) throws RuntimeException, ParseException {
		Date date1 = new SimpleDateFormat(pattern).parse(name);
		return date1;
	}

	public File save(MultipartFile file, String path, String fileName) {

		try {
			/*
			 * String serverpath =
			 * ResourceUtils.getURL("classpath:static").getPath().replace("%20",
			 * " ").replace('/', '\\'); String pathFilePath = serverpath.substring(1);
			 */
			File dirFile = new File(context.getRealPath(path));
			System.out.println(dirFile);
			dirFile.getParentFile().mkdirs();
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}

			File save = new File(dirFile, fileName);
			file.transferTo(save);
			return save;
		} catch (IllegalStateException | IOException e) {
			throw new RuntimeException(e);
		}
	}
}
