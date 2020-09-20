package com.brightcoding.app.ws.responses;

import java.time.LocalDate;


public class ErrorMessage {
	//private LocalDate timestamp;
	private String timestamp;
	private String message;

	public ErrorMessage(String timestamp, String message) {
		super();
		this.timestamp = timestamp;
		this.message = message;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
