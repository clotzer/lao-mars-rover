package com.clotzer.marsrover.model;

/*
 * The com.google.gson.annotations.SerializedName is a nice package which is useful
 * for serializing/de-serializing JSON and Java objects. Given more time, I would
 * have like to figure out why I had it working and then added another dependency
 * which must have caused a conflict then lost it.
 */

/**
 * This is top-most level of the photo JSON structure.
 * 
 * @author <a href="mailto:c12345ll@gmail.com">Carey Lotzer</a>
 * @version 1.0
 */
public class Photo {

	long id;
	int sol;
	Camera camera;

	/*
	 * Tried using Gson's @SerializedName("img_src") here. I had it working
	 * beautifully until I added some other dependency. I would need more time to
	 * back-track and fix this. This was String imageSrc.
	 */
	String img_src;

	/*
	 * Same here... Tried using Gson's @SerializedName("earth_date") here. This was
	 * String earthDate.
	 */
	String earth_date;

	/**
	 * Get the photo id. This relates to the photo taken.
	 * 
	 * @return the photo identifier
	 */
	public long getId() {
		return id;
	}

	/**
	 * Set the photo id. This relates to the photo taken.
	 * 
	 * @param the photo identifier
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Get the sol (day on Mars). This relates to the photo taken.
	 * 
	 * @return the sol
	 */
	public int getSol() {
		return sol;
	}

	/**
	 * Set the sol (day on Mars). This relates to the photo taken.
	 * 
	 * @param the sol
	 */
	public void setSol(int sol) {
		this.sol = sol;
	}

	/**
	 * Get the camera. This returns the camera class related to the photo taken.
	 * 
	 * @return the camera class
	 */
	public Camera getCamera() {
		return camera;
	}

	/**
	 * Set the camera. This sets the camera class related to the photo taken.
	 * 
	 * @param the camera class
	 */
	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	/**
	 * Get the image URL.
	 * 
	 * @return the image source
	 */
	public String getImg_src() {
		return img_src;
	}

	/**
	 * Set the image URL from the JSON file.
	 * 
	 * @param the image URL
	 */
	public void setImg_src(String img_src) {
		this.img_src = img_src;
	}

	/**
	 * Get the Earth date that the photo was taken as a string.
	 * 
	 * @return the earth date
	 */
	public String getEarth_date() {
		return earth_date;
	}

	/**
	 * Set the Earth date that the photo was taken from the JSON input.
	 * 
	 * @param the earth date
	 */
	public void setEarth_date(String earth_date) {
		this.earth_date = earth_date;
	}

	@Override
	public String toString() {
		return "Photo [id=" + id + ", sol=" + sol + ", camera=" + camera + ", img_src=" + img_src + ", earth_date="
				+ earth_date + "]";
	}

}
