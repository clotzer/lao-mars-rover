package com.clotzer.marsrover.controller;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.clotzer.marsrover.dao.ImageHeader;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

/**
 * The FileHandler class deals with file I/O. This is used to read the date JSON
 * file, read the old download file and read a new one. The GSON library is used
 * to read the JSON file. An example date file may look like this:
 * 
 * [ "2015-6-3", "02/27/17", "April 31, 2018", "Jul-13-2016", "June 2, 2018" ]
 * 
 * @author <a href="mailto:c12345ll@gmail.com">Carey Lotzer</a>
 * @version 1.0
 */
public class FileHandler {

	private static final Logger log = LoggerFactory.getLogger(FileHandler.class);

	/**
	 * createPath
	 * 
	 * This method creates one or more directories based on the config setting for
	 * the image store.
	 * 
	 * @param - string the full path to create
	 * @throws - IOException
	 */
	public void createPath(String string) throws IOException {
		Path path = Paths.get(string);
		Files.createDirectories(path);
	}

	/**
	 * readDateFile
	 * 
	 * This method reads one or more raw dates from a JSON file and stores them in a
	 * list returned to the caller.
	 * 
	 * @param	- config the Config class used for the path and file name
	 * @return	- List<String> a list of raw dates read from the date JSON file
	 * @throws	- IOException
	 */
	public List<String> readDateFile(Config config) throws IOException {
		Gson gson = new Gson();
		JsonReader reader = new JsonReader(
				new FileReader(config.getLocalImageStorePath() + "/" + config.getImageQueryDateFile()));
		List<String> dates = gson.fromJson(reader, new TypeToken<List<String>>() {
		}.getType());
		reader.close();
		log.info(dates.toString());
		return dates;
	}

	/**
	 * writeDownloadFile
	 * 
	 * This method takes a HashMap of ImageHeaders and writes them to a single file
	 * based on these image files being downloaded.
	 * 
	 * @param - config the Config class used for the path and file name
	 * @param - map a HashMap of ImageHeaders of image files which have been
	 *          downloaded
	 * @throws - IOException
	 * @throws - ClassNotFoundException
	 */
	public void writeDownloadFile(Config config, HashMap<String, ImageHeader> map)
			throws IOException, ClassNotFoundException {

		String outputFile = config.getLocalImageStorePath() + "/" + config.getLocalImageListName();

		try (ObjectOutputStream objectOutput = new ObjectOutputStream(new FileOutputStream(outputFile));) {

			for (Map.Entry<String, ImageHeader> entry : map.entrySet()) {
				log.info("Key = " + entry.getKey() + ", Value = " + entry.getValue());
				ImageHeader image = entry.getValue();
				objectOutput.writeObject(image);
			}

		} catch (IOException ex) {
			log.error(ex.getMessage());
		}
	}

	/**
	 * readDownloadFile
	 * 
	 * This method takes the download file of ImageHeaders and reads them into a
	 * single HashMap. This is used to compare the photo id and the ETag against the
	 * file header we want to check if it has been previously downloaded or has
	 * changed so that the file is not downloaded again unnecessarily. This method
	 * either returns a map of the image headers that have been downloaded or an
	 * empty map which can be used for new image headers.
	 * 
	 * @param - config the Config class used for the path and file name
	 * @return - HashMap a HashMap of ImageHeaders of image files which have been
	 *         downloaded
	 * @throws - IOException
	 * @throws - ClassNotFoundException
	 */
	public HashMap<String, ImageHeader> readDownloadFile(Config config) throws IOException, ClassNotFoundException {
		String inputFile = config.getLocalImageStorePath() + "/" + config.getLocalImageListName();
		HashMap<String, ImageHeader> map = new HashMap<>();

		try (ObjectInputStream objectInput = new ObjectInputStream(new FileInputStream(inputFile));) {

			while (true) {
				ImageHeader imageHeader = (ImageHeader) objectInput.readObject();
				map.put(imageHeader.getId(), imageHeader);
				System.out.println(imageHeader.toString());
			}
		} catch (EOFException eof) {
			return map;
		} catch (IOException | ClassNotFoundException ex) {
			return map;
		}
	}
}
