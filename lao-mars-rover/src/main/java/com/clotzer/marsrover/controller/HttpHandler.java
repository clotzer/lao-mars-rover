package com.clotzer.marsrover.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.clotzer.marsrover.dao.ImageHeader;
import org.apache.commons.io.FilenameUtils;

/**
 * The HttpHandler class deals with HTTP communications. These range from GET
 * requests for image headers to downloading image files.
 * 
 * @author <a href="mailto:c12345ll@gmail.com">Carey Lotzer</a>
 * @version 1.0
 */
public class HttpHandler {

	private static final Logger log = LoggerFactory.getLogger(HttpHandler.class);

	/**
	 * collectImageHeader
	 * 
	 * This method makes a connection to the NASA REST WS and pulls the header for
	 * the image file without downloading it. This image header is used to compare
	 * to an existing list of image headers to check if the image has been
	 * previously downloaded or not or has changed. This code even follows cases
	 * where image files have been moved to new locations.
	 * 
	 * @param - id the image identifier
	 * @param - url the first path to the image
	 * @return - ImageHeader a new image header built from the image header
	 * @throws - IOException
	 */
	public ImageHeader collectImageHeader(String id, String url) throws IOException {
		HttpURLConnection connection = null;
		try {

			connection = (HttpURLConnection) (new URL(url).openConnection());
			connection.setInstanceFollowRedirects(true);
			connection.connect();

			int responseCode = connection.getResponseCode();
			String location = connection.getHeaderField("Location");
			HashMap<String, ImageHeader> imageHeaders = new HashMap<String, ImageHeader>();
			ImageHeader image = null;

			if (location != null && responseCode == 301) { // file has been moved
				// close this connection and open a new one with the new location
				connection.getInputStream().close();
				connection.disconnect();

				connection = (HttpURLConnection) (new URL(location).openConnection());
				connection.connect();
			}

			String responseCodeString = String.valueOf(connection.getResponseCode());
			String eTag = connection.getHeaderField("ETag").replace("\"", "");

			image = new ImageHeader(id.toString(), url, eTag, responseCodeString,
					connection.getHeaderField("Content-Length").toString(), null);

			if (image != null) {
				log.info(image.toString());
				imageHeaders.put(id, image);
			}

			return image;
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		} finally {
			if (connection != null) {
				connection.getInputStream().close();
				connection.disconnect();
			}
		}
	}

	/**
	 * downloadFile
	 * 
	 * This method makes a connection to the NASA image file, reads the bytes and
	 * stores them to the local disk.
	 * 
	 * @param - config the Config class used for the path and file name
	 * @param - imageHeader an image header used to locate the image to download
	 * @return - boolean true for success; false for failure
	 * @throws - MalformedURLException
	 * @throws - IOException
	 */
	public boolean downloadFile(Config config, ImageHeader imageHeader) throws MalformedURLException, IOException {

		String url = imageHeader.getUrl();
		HttpURLConnection connection = null;
		try {

			connection = (HttpURLConnection) (new URL(url).openConnection());
			connection.setInstanceFollowRedirects(true);
			connection.connect();

			int responseCode = connection.getResponseCode();
			String location = connection.getHeaderField("Location");

			if (location != null && responseCode == 301) { // handle redirection
				downloadFile(connection, config, imageHeader, location);
			} else {
				downloadFile(connection, config, imageHeader, url);
			}

		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}

		return true;
	}

	private boolean downloadFile(HttpURLConnection connection, Config config, ImageHeader imageHeader, String url)
			throws IOException {
		connection.getInputStream().close();
		connection.disconnect();

		try (InputStream in = new URL(url).openStream()) {
			URL _url = new URL(url);
			String fileName = FilenameUtils.getName(_url.getPath());
			if (fileName != null) {
				Files.copy(in, Paths.get(config.getLocalImageStorePath() + "/" + fileName));
			} else {
				Files.copy(in, Paths.get(config.getLocalImageStorePath() + "/image_" + imageHeader.getId() + ".jpg"));
			}
		}

		return true;
	}

}
