package com.clotzer.marsrover.dao;

import java.io.Serializable;

/**
 * The ImageHeader class stores the image headers for files which are supposed
 * to be downloaded or have been in the past.
 * 
 * @author <a href="mailto:c12345ll@gmail.com">Carey Lotzer</a>
 * @version 1.0
 */
public class ImageHeader implements Serializable {

	/**
	 * Auto-generated version ID
	 */
	private static final long serialVersionUID = -2491901479708515250L;

	private String id;
	private String url;
	private String e_tag;
	private String response_code;
	private String content_length;
	private String last_downloaded_date;

	/**
	 * Parameterless constructor
	 */
	public ImageHeader() {
	}

	/**
	 * Parameter constructor
	 * 
	 * @param id                   - the image id
	 * @param url                  - the image URL
	 * @param e_tag                - the ETag of the image found in the header
	 * @param response_code        - the HTTP response code
	 * @param content_length       - the number of bytes of the image
	 * @param last_downloaded_date - the date string of the last download
	 */
	public ImageHeader(String id, String url, String e_tag, String response_code, String content_length,
			String last_downloaded_date) {
		super();
		this.id = id;
		this.url = url;
		this.e_tag = e_tag;
		this.response_code = response_code;
		this.content_length = content_length;
		this.last_downloaded_date = last_downloaded_date;
	}

	/**
	 * Get the image id. This relates to the image on the site.
	 * 
	 * @return String - the image identifier
	 */
	public String getId() {
		return id;
	}

	/**
	 * Set the image id. This relates to the image on the site.
	 * 
	 * @param id - the image identifier
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Get the URL. This relates to the image on the site.
	 * 
	 * @return String - the image URL
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Set the URL. This relates to the image on the site.
	 * 
	 * @param String - the image URL
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Get the ETag. This relates to the image header on the site.
	 * 
	 * @return String - the image header ETag
	 */
	public String getE_tag() {
		return e_tag;
	}

	/**
	 * Set the ETag. This relates to the image header on the site.
	 * 
	 * @param e_tag - the image header ETag
	 */
	public void setE_tag(String e_tag) {
		this.e_tag = e_tag;
	}

	/**
	 * Get the response code. This relates to the image header on the site.
	 * 
	 * @return String - the response code
	 */
	public String getResponse_code() {
		return response_code;
	}

	/**
	 * Set the response code. This relates to the image header on the site.
	 * 
	 * @param response_code - the response code
	 */
	public void setResponse_code(String response_code) {
		this.response_code = response_code;
	}

	/**
	 * Get the content length. This relates to the image size in bytes.
	 * 
	 * @return String - the content length
	 */
	public String getContent_length() {
		return content_length;
	}

	/**
	 * Set the content length. This relates to the image size in bytes.
	 * 
	 * @param content_length - the content length
	 */
	public void setContent_length(String content_length) {
		this.content_length = content_length;
	}

	/**
	 * Get the last downloaded date. This locally set
	 * when the image has been successfully downloaded.
	 * 
	 * @return String - the last downloaded date
	 */
	public String getLast_downloaded_date() {
		return last_downloaded_date;
	}

	/**
	 * Set the last downloaded date. This locally set
	 * when the image has been successfully downloaded.
	 * 
	 * @param last_downloaded_date - the last downloaded date
	 */
	public void setLast_downloaded_date(String last_downloaded_date) {
		this.last_downloaded_date = last_downloaded_date;
	}

	@Override
	public String toString() {
		return "ImageHeader [id=" + id + ", url=" + url + ", e_tag=" + e_tag + ", response_code=" + response_code
				+ ", content_length=" + content_length + ", last_downloaded_date=" + last_downloaded_date + "]";
	}

}
