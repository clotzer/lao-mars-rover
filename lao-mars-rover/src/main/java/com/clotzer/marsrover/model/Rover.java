package com.clotzer.marsrover.model;

/**
 * The Rover class is used to hold the information about the rover
 * and the cameras on the rover related to the Photo class.
 * 
 * @author <a href="mailto:c12345ll@gmail.com">Carey Lotzer</a>
 * @version 1.0
 */
public class Rover {
	int id;
	String name;
	String landing_date;
	String launch_date;
	String status;
	int max_sol;
	String max_date;
	long total_photos;
	
	CameraName[] cameras;
}
