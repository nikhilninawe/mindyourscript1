package db;

import java.util.Date;

public class JobDetailEntry {
	
	private String name;
	private String status;
	private String lastResponse;
	private String lastRunTime;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLastResponse() {
		return lastResponse;
	}
	public void setLastResponse(String lastResponse) {
		this.lastResponse = lastResponse;
	}
	public String getLastRunTime() {
		return lastRunTime;
	}
	public void setLastRunTime(String lastRunTime) {
		this.lastRunTime = lastRunTime;
	}
	
	

}
