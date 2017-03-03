package db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="job")
public class Job {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String name;
	private String fileName;
	private String schedule;
	private String machineIpAddress;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getMachineIpAddress() {
		return machineIpAddress;
	}

	public void setMachineIpAddress(String machineIpAddress) {
		this.machineIpAddress = machineIpAddress;
	}

	@Override
	public String toString() {
		return "Job [id=" + id + ", name=" + name + ", fileName=" + fileName + ", schedule=" + schedule
				+ ", machineIpAddress=" + machineIpAddress + "]";
	}
	
	

}
