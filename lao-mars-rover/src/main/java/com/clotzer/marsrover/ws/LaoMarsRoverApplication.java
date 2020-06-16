package com.clotzer.marsrover.ws;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.clotzer.marsrover.controller.Config;
import com.clotzer.marsrover.controller.FileHandler;
import com.clotzer.marsrover.controller.HttpHandler;
import com.clotzer.marsrover.dao.ImageHeader;
import com.clotzer.marsrover.model.Photo;
import com.clotzer.marsrover.model.PhotoList;

/**
 * The Living As One NASA mars rover exercise interface class. This is a
 * SpringBoot application which uses a REST template to walk through the
 * provided date file, dynamically build the GET request URL, check if an image
 * has been previously downloaded, and if not, downloads it and updates the
 * download cache file. The download location is specified on the local machine
 * and is configurable.
 * 
 * @author <a href="mailto:c12345ll@gmail.com">Carey Lotzer</a>
 * @version 1.0
 */
@SpringBootApplication
@EnableConfigurationProperties(Config.class)
public class LaoMarsRoverApplication {

	private static final Logger log = LoggerFactory.getLogger(LaoMarsRoverApplication.class);

	@Autowired
	private Config config;

	/**
	 * The main entry point of the application.
	 * 
	 * @param args - none used at this time
	 */
	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(LaoMarsRoverApplication.class, args);

		int exitCode = SpringApplication.exit(ctx, new ExitCodeGenerator() {
			@Override
			public int getExitCode() {
				// return the error code
				return 0;
			}
		});

		System.exit(exitCode);
	}

	/**
	 * The Spring REST template builder
	 * 
	 * @param builder - the REST template builder
	 * @return RestTemplate
	 */
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	/**
	 * The customized Spring run method
	 * 
	 * @param restTemplate - the REST template
	 * @return CommandLineRunner
	 */
	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {

		FileHandler fileHandler = new FileHandler();

		//
		// Read query date file
		//
		List<String> dates = fileHandler.readDateFile(config);

		Date curentDate = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = dateFormat.format(curentDate);

		//
		// read prior image download records
		//
		HashMap<String, ImageHeader> downloadedMap = fileHandler.readDownloadFile(config);

		return args -> {

			boolean mapChanged = false;

			log.info(config.toString());
			log.info(dates.toString());

			//
			// Walk through array of dates, building the HashMap of image headers
			// from the query.
			//
			for (String date : dates) {

				//
				// convert the date to a valid format
				//
				String validDate = config.convertDate(date);

				if (validDate != null) {

					//
					// create the URL given a valid date and the config parameters
					//
					String url = config.buildUrlFromDate(validDate);

					//
					// de-serialize the JSON file into an list of Java objects
					//
					PhotoList response = restTemplate.getForObject(url, PhotoList.class);
					List<Photo> photos = response.getPhotos();

					//
					// For each photo, pull down the header, check if we have the Etag for
					// it already. If we don't, download the file and store it in the destination
					// folder.
					//
					for (Photo photo : photos) {

						log.info(photo.toString());
						String id = String.valueOf(photo.getId());
						HttpHandler httpHandler = new HttpHandler();
						ImageHeader imageHeader = httpHandler.collectImageHeader(id, photo.getImg_src());
						ImageHeader existingImageHeader = null;

						if (downloadedMap != null && !downloadedMap.isEmpty()) {
							existingImageHeader = downloadedMap.get(id);
						}

						if (existingImageHeader != null) {
							log.info(existingImageHeader.toString());

							String existingETag = existingImageHeader.getE_tag();
							String imageETag = imageHeader.getE_tag();

							log.info("Existing ETag: [" + existingETag + "]");
							log.info("Download ETag: [" + imageETag + "]");

							if (!existingETag.equalsIgnoreCase(imageETag)) {
								if (httpHandler.downloadFile(config, imageHeader)) {
									log.info("ETag mis-match for file id " + id + ". Refreshing download.");
									imageHeader.setLast_downloaded_date(strDate);
									downloadedMap.put(id, imageHeader);
									mapChanged = true;
								} else {
									log.error("File id " + id + " could not be downloaded!");
								}
							} else {
								log.info("File id " + id + " has already been downloaded!");
							}
						} else {
							if (httpHandler.downloadFile(config, imageHeader)) {
								log.info("Downloading file id " + id + ".");
								imageHeader.setLast_downloaded_date(strDate);
								downloadedMap.put(id, imageHeader);
								mapChanged = true;
							} else {
								log.error("File id " + id + " could not be downloaded!");
							}
						}
					}
				}
			}

			//
			// write the final download file
			//
			if (mapChanged) {
				fileHandler.writeDownloadFile(config, downloadedMap);
				log.info("Finished writing download file...");
			}
		};

	}
}
