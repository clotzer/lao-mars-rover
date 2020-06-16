package com.clotzer.marsrover.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to group the photos together in a list when reading multiple records
 * from the JSON response from the WS query to the NASA earth date API REST
 * call.
 * 
 * @author <a href="mailto:c12345ll@gmail.com">Carey Lotzer</a>
 * @version 1.0
 */
public class PhotoList {

	private List<Photo> photos;

	/**
	 * Parameterless constructor creates a new empty array list of photos.
	 */
	public PhotoList() {
		photos = new ArrayList<>();
	}

	/**
	 * Get the list of photos.
	 * 
	 * @return List<Photo> - the list of photos
	 */
	public List<Photo> getPhotos() {
		return photos;
	}
}
