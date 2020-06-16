package com.clotzer.marsrover.controller;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.clotzer.marsrover.ws.AbstractTest;

public class ConfigTest extends AbstractTest {

	@Autowired
	private Config config;

	@Test
	public void testGetEarthDateFormat() {
		String earthDateFormat = config.getEarthDateFormat();
		Assert.assertEquals(earthDateFormat, "yyyy-M-d");
		log.info("Earth Date Format is:" + earthDateFormat);
	}

	@Test
	public void testGetQueryStringOne() {
		String queryStringOne = config.getQueryStringOne();
		Assert.assertEquals(queryStringOne, "earth_date");
	}

	@Test
	public void testGetQueryStringTwo() {
		String queryStringTwo = config.getQueryStringTwo();
		Assert.assertEquals(queryStringTwo, "api_key");
	}

}
