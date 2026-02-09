package application;

public class Patient_ver {
	public String queueNo;
	public String patientId;
	public String patientName;
	public String type;
	public String triageLevel;
	public String arrivalTime;
	public String status;
	public String serviceStart;
	public String serviceEnd;
	public String createdAt;
	public String updatedAt;
	
	public Patient_ver(String queueNo, String patientId, String patientName, String type, String triageLevel, String arrivalTime, String status, String serviceStart, String serviceEnd, String createdAt, String updatedAt) {
		this.queueNo = queueNo;
		this.patientId = patientId;
		this.patientName = patientName;
		this.type = type;
		this.triageLevel = triageLevel;
		this.arrivalTime = arrivalTime;
		this.status = status;
		this.serviceStart = serviceStart;
		this.serviceEnd = serviceEnd;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
}