package com.clotzer.marsrover.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

/**
 * The Config class holds the settings used to retrieve and store the images.
 * The configuration settings are related to the NASA API to pull images based
 * on the earth date they were taken on mars. The URL used to query the image
 * webservice are for all images for all rovers for all cameras on a given earth
 * day. Spring manages this POJO. The NASA earth date URI has three major parts.
 * The first part id the base URL, the second part is the date parameter and the
 * third part is the API key. The second and third pieces have a key/value pair.
 * 
 * @author <a href="mailto:c12345ll@gmail.com">Carey Lotzer</a>
 * @version 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "config")
@PropertySource("classpath:application.properties")
@Validated
public class Config {

	private static final Logger log = LoggerFactory.getLogger(Config.class);

	@Value("config.nasa_login")
	private String nasaLogin;

	@NotNull
	@Value("config.nasa_key")
	private String nasaKey;

	@NotEmpty
	@Pattern(regexp = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)")
	@Value("config.nasa_earth_date_base_uri")
	private String nasaEarthDateBaseUri;

	@NotEmpty
	@Value("config.earth_date_format")
	private String earthDateFormat;

	@NotEmpty
	@Value("config.query_string_one")
	private String queryStringOne;

	@NotEmpty
	@Value("config.query_string_two")
	private String queryStringTwo;

	@NotEmpty
	@Value("config.local_image_store_path")
	private String localImageStorePath;

	@NotEmpty
	@Value("config.local_image_list_name")
	private String localImageListName;

	@NotEmpty
	@Value("config.image_query_date_file")
	private String imageQueryDateFile;

	/**
	 * Set the NASA login from the config file. This is the login name used to
	 * create the API key.
	 * 
	 * @param - String the NASA login
	 */
	public void setNasaLogin(String nasaLogin) {
		this.nasaLogin = nasaLogin;
	}

	/**
	 * Set the NASA key from the config file. This is the API key used to download
	 * the JSON details for the image files.
	 * 
	 * @param - String the NASA key string
	 */
	public void setNasaKey(String nasaKey) {
		this.nasaKey = nasaKey;
	}

	/**
	 * Set the NASA earth date URI base from the config file. This is the portion of
	 * the URL before the query components.
	 * 
	 * @param - String the NASA base URI
	 */
	public void setNasaEarthDateBaseUri(String nasaEarthDateBaseUri) {
		this.nasaEarthDateBaseUri = nasaEarthDateBaseUri;
	}

	/**
	 * Set the NASA earth date format string from the config file. This is the
	 * format which the NASA API accepts.
	 * 
	 * @param - String the NASA earth date format string
	 */
	public void setEarthDateFormat(String earthDateFormat) {
		this.earthDateFormat = earthDateFormat;
	}

	/**
	 * The NASA earth date URI requires a date key. This is that key that we read
	 * from the config file.
	 * 
	 * @param - String the key for the first part of the WS query string.
	 */
	public void setQueryStringOne(String queryStringOne) {
		this.queryStringOne = queryStringOne;
	}

	/**
	 * The NASA earth date URI requires an API key. This is the key part of the
	 * key/value pair we read from the config file.
	 * 
	 * @param - String the key for the second part of the WS query string.
	 */
	public void setQueryStringTwo(String queryStringTwo) {
		this.queryStringTwo = queryStringTwo;
	}

	/**
	 * Get the NASA login from the config file.
	 * 
	 * @return - String the NASA login string
	 */
	public String getNasaLogin() {
		return nasaLogin;
	}

	/**
	 * Get the NASA API key from the config file.
	 * This is the API key used to download the JSON details
	 * for the image files.
	 * 
	 * @return - String the NASA key string
	 */
	public String getNasaKey() {
		return nasaKey;
	}

	/**
	 * Get the NASA earth date URI base from the config file. This is the portion of
	 * the URL before the query components.
	 * 
	 * @return - String the NASA base URI
	 */
	public String getNasaEarthDateBaseUri() {
		return nasaEarthDateBaseUri;
	}

	/**
	 * Get the NASA earth date format string from the config file. This is the
	 * format which the NASA API accepts.
	 * 
	 * @return - String the NASA earth date format string
	 */
	public String getEarthDateFormat() {
		return earthDateFormat;
	}

	/**
	 * The NASA earth date URI requires a date key. This is that key that we read
	 * from the config file.
	 * 
	 * @return - String the key for the first part of the WS query string.
	 */
	public String getQueryStringOne() {
		return queryStringOne;
	}

	/**
	 * The NASA earth date URI requires an API key. This is the key part of the
	 * key/value pair we read from the config file.
	 * 
	 * @return - String the key for the second part of the WS query string.
	 */
	public String getQueryStringTwo() {
		return queryStringTwo;
	}

	/**
	 * Get the path on the local machine from the root drive which is used
	 * to hold the image files, the image_list.json file, and the date_list.json
	 * file.
	 * 
	 * @return - String the path to the image store on the local computer
	 */
	public String getLocalImageStorePath() {
		return localImageStorePath;
	}

	/**
	 * Set the path on the local machine from the root drive which is used
	 * to hold the image files, the image_list.dat file, and the date_list.json
	 * file.
	 * 
	 * @param - String the path to the image store on the local computer
	 */
	public void setLocalImageStorePath(String localImageStorePath) throws IOException {
		this.localImageStorePath = localImageStorePath;
		FileHandler fh = new FileHandler();
		fh.createPath(localImageStorePath);
	}

	/**
	 * Get the file name to store the image download information. This is a binary file
	 * of Java objects. This could have been a database on the local machine.
	 * 
	 * @return - String the name of the image download file
	 */
	public String getLocalImageListName() {
		return localImageListName;
	}

	public void setLocalImageListName(String localImageListName) {
		this.localImageListName = localImageListName;
	}

	public String getImageQueryDateFile() {
		return imageQueryDateFile;
	}

	public void setImageQueryDateFile(String imageQueryDateFile) {
		this.imageQueryDateFile = imageQueryDateFile;
	}

	public String buildUrlFromDate(String date) {
		StringBuilder sb = new StringBuilder("");

		sb.append(this.nasaEarthDateBaseUri);
		sb.append("?");
		sb.append(this.queryStringOne);
		sb.append("=");
		sb.append(date);
		sb.append("&");
		sb.append(this.queryStringTwo);
		sb.append("=");
		sb.append(this.nasaKey);

		String output = sb.toString();
		log.info(output);
		return output;
	}

	@Override
	public String toString() {
		return "Config [nasaLogin=" + nasaLogin + ", nasaKey=" + nasaKey + ", nasaEarthDateBaseUri="
				+ nasaEarthDateBaseUri + ", earthDateFormat=" + earthDateFormat + ", queryStringOne=" + queryStringOne
				+ ", queryStringTwo=" + queryStringTwo + ", localImageStorePath=" + localImageStorePath
				+ ", localImageListName=" + localImageListName + ", imageQueryDateFile=" + imageQueryDateFile + "]";
	}

	public String convertDate(String dateString) throws ParseException {

		List<String> formatStrings = Arrays.asList("yyyy-M-d", "M/d/y", "MMMM d, yyyy", "MMM-d-yyyy", "MMM-D-yyyy",
				"M-d-y");

		for (String formatString : formatStrings) {
			try {
				Date theDate = new SimpleDateFormat(formatString, Locale.US).parse(dateString);
				log.info(theDate.toString());

				SimpleDateFormat SDFormat = new SimpleDateFormat(this.earthDateFormat, Locale.US);
				return SDFormat.format(theDate);
			}

			catch (ParseException e) {
			}
		}

		return null;
	}
}
