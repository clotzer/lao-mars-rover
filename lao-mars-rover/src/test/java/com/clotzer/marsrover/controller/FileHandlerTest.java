package com.clotzer.marsrover.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.clotzer.marsrover.dao.ImageHeader;
import com.clotzer.marsrover.ws.AbstractTest;

public class FileHandlerTest extends AbstractTest {

	private final FileHandler fileHandler = new FileHandler();

	@Test
	public void testReadDateFile() throws IOException {
		Config config = new Config();
		String path = "src/test/resources";
		config.setLocalImageStorePath(path);
		config.setImageQueryDateFile("date_list.json");
		List<String> dates = fileHandler.readDateFile(config);
		Assert.assertNotNull(dates);

		List<String> expectedOutput = new ArrayList<>();
		expectedOutput.add("2015-6-3");
		expectedOutput.add("02/27/17");
		expectedOutput.add("April 31, 2018");
		expectedOutput.add("Jul-13-2016");
		expectedOutput.add("June 2, 2018");

		Assert.assertEquals(expectedOutput, dates);
	}

	@Test
	public void testReadDownloadFile() throws IOException, ClassNotFoundException {
		Config config = new Config();
		String path = "src/test/resources";
		config.setLocalImageStorePath(path);
		config.setLocalImageListName("image_list.dat");
		HashMap<String, ImageHeader> methodMap = fileHandler.readDownloadFile(config);
		Assert.assertNotNull(methodMap);
		ImageHeader imageHeader = methodMap.get("102686");
		Assert.assertEquals("8167e7fcef76d7176281eb2e2bc0b1dd", imageHeader.getE_tag());
	}

}
