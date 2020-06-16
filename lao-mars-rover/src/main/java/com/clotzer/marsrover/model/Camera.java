package com.clotzer.marsrover.model;

/**
 * Represents the camera and its relationship to the rover. This is part of
 * the photo JSON structure.
 * 
 * @author <a href="mailto:c12345ll@gmail.com">Carey Lotzer</a>
 * @version 1.0
 */
public class Camera {
	int id;
	String name;
	int rover_id;
	String full_name;

	/**
	 * Get the camera id. This relates to the photo taken.
	 * 
	 * @return the camera identifier
	 */
	public int getId() {
		return id;
	}

	/**
	 * Get the camera short name related to the photo taken.
	 * 
	 * @return the camera short name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the rover identifier related to the photo taken.
	 * 
	 * @return the rover id
	 */
	public int getRover_id() {
		return rover_id;
	}

	/**
	 * Get the camera long name related to the photo taken.
	 * 
	 * @return the camera long name
	 */
	public String getFull_name() {
		return full_name;
	}

	@Override
	public String toString() {
		return "Camera [id=" + id + ", name=" + name + ", rover_id=" + rover_id + ", full_name=" + full_name + "]";
	}
}
