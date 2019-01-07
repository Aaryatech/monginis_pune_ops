package com.monginis.ops.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("session")
public class FileUploadController {

	// file unloader
	
	private static final String HOME_PROPERTY = "/Users/MIRACLEINFOTAINMENT/ATS/uplaods/";

	private static final File IMAGES_DIR = new File(HOME_PROPERTY);
	private static final String IMAGES_DIR_ABSOLUTE_PATH = IMAGES_DIR.getAbsolutePath() + File.separator;

	private static final String FAILED_UPLOAD_MESSAGE = "You failed to upload [%s] because the file because %s";
	private static final String SUCCESS_UPLOAD_MESSAGE = "You successfully uploaded file = [%s]";

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public ModelAndView uploadFileHandler(@RequestParam("name") String name, @RequestParam("file") MultipartFile file) {
		ModelAndView modelAndView = new ModelAndView("login");
		name = file.getName();
		if (file.isEmpty()) {
			modelAndView.addObject("message", String.format(FAILED_UPLOAD_MESSAGE, name, "file is empty"));
		} else {
			createPizzaImagesDirIfNeeded();
			modelAndView.addObject("message", createImage(name, file));
		}

		return modelAndView;
	}

	@RequestMapping(value = "/addNewFrProcess", method = RequestMethod.POST)
	public ModelAndView uploadFileHandlerNew(@RequestParam("fr_code") String fr_code,@RequestParam("fr_opening_date") String fr_opening_date,@RequestParam("fr_name") String fr_name,  @RequestParam("fr_image") MultipartFile file) {
		ModelAndView modelAndView = new ModelAndView("login");
		
		System.out.println("code "+fr_code);
		System.out.println("date "+fr_opening_date);
		System.out.println("name "+fr_name);
		System.out.println("file "+file.getName());

		
		 String name = file.getName();
		if (file.isEmpty()) {
			modelAndView.addObject("message", String.format(FAILED_UPLOAD_MESSAGE, name, "file is empty"));
		} else {
			createPizzaImagesDirIfNeeded();
			modelAndView.addObject("message", createImage(name, file));
		}

		return modelAndView;
	}
	
	
	private void createPizzaImagesDirIfNeeded() {
		if (!IMAGES_DIR.exists()) {
			IMAGES_DIR.mkdirs();
		}
	}

	private String createImage(String name, MultipartFile file) {
		try {

			System.out.println("Directory : " + IMAGES_DIR_ABSOLUTE_PATH);
			File image = new File(IMAGES_DIR_ABSOLUTE_PATH + name);
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(image));
			stream.write(file.getBytes());
			stream.close();

			return String.format(SUCCESS_UPLOAD_MESSAGE, name);
		} catch (Exception e) {
			return String.format(FAILED_UPLOAD_MESSAGE, name, e.getMessage());
		}
	}

	@RequestMapping(value = "/upload/{imageName}")
	@ResponseBody
	public byte[] getImage(@PathVariable(value = "imageName") String imageName) throws IOException {
		createPizzaImagesDirIfNeeded();

		File serverFile = new File(IMAGES_DIR_ABSOLUTE_PATH + imageName + ".jpg");

		return Files.readAllBytes(serverFile.toPath());
	}

}
