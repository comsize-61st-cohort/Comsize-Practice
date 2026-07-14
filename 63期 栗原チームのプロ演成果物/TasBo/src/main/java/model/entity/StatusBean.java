package model.entity;

import java.io.Serializable;

public class StatusBean implements Serializable {
	
	private String statusCode;
	private String statusName;
	
	public StatusBean() {
		
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
}
