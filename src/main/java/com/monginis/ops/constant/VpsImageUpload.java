package com.monginis.ops.constant;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class VpsImageUpload {
	
	/*private static final String FR_FOLDER = "/opt/apache-tomcat-8.5.37/webapps/uploadspune/FR/";
	private static final String SP_CAKE_FOLDER = "/opt/apache-tomcat-8.5.37/webapps/uploadspune/SPCAKE/";
	private static final String CUST_CHOICE_PHOTO_CAKE_FOLDER = "/opt/apache-tomcat-8.5.37/webapps/uploadspune/CUSTCHOICEPHOTOCAKE/";
	private static final String GVN_PHOTO_FOLDER = "/opt/apache-tomcat-8.5.37/webapps/uploadspune/GVN/";
	*/
	private static final String FR_FOLDER = "/opt/apache-tomcat-8.5.37/webapps/uploads/FR/";
	private static final String SP_CAKE_FOLDER = "/opt/apache-tomcat-8.5.37/webapps/uploads/SPCAKE/";
	private static final String CUST_CHOICE_PHOTO_CAKE_FOLDER = "/opt/apache-tomcat-8.5.37/webapps/uploads/CUSTCHOICEPHOTOCAKE/";
	private static final String GVN_PHOTO_FOLDER = "/opt/apache-tomcat-8.5.37/webapps/uploads/GVN/";

	public void saveUploadedFiles(List<MultipartFile> files, int imageType, String imageName) throws IOException {

		for (MultipartFile file : files) {

			if (file.isEmpty()) {

				continue;

			}

			Path path = Paths.get(FR_FOLDER + imageName);

			byte[] bytes = file.getBytes();

			if (imageType == 1) {
				System.out.println("Inside Image Type =1");

				path = Paths.get(FR_FOLDER + imageName);

				System.out.println("Path= " + path.toString());

			} 
			else if (imageType == 4) {

				path = Paths.get(SP_CAKE_FOLDER + imageName);

			}
			
			else if (imageType == 5) {

				path = Paths.get(CUST_CHOICE_PHOTO_CAKE_FOLDER + imageName);

			}
			else if (imageType == 6) {

				path = Paths.get(GVN_PHOTO_FOLDER + imageName);

			}


			Files.write(path, bytes);

		}

	}

}
