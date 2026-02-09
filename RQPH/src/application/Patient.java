package application;

import javafx.beans.property.*;

public class Patient {
	private SimpleIntegerProperty queueNo, waitingTime, serviceDuration;
	private SimpleStringProperty patientId, patientName, type, triageLevel,arrivalTime;
	private SimpleStringProperty serviceStart, serviceEnd;
	
	public Patient(int queueNo, String patientId, String patientName, String type, String triageLevel, String arrivalTime, int waitingTime, int serviceDuration, String serviceStart, 
			String serviceEnd) {
		this.queueNo = new SimpleIntegerProperty(queueNo);
		this.patientId = new SimpleStringProperty(patientId);
		this.patientName = new SimpleStringProperty(patientName);
		this.type = new SimpleStringProperty(type);
		this.triageLevel = new SimpleStringProperty(triageLevel);
		this.arrivalTime = new SimpleStringProperty(arrivalTime);
		this.waitingTime = new SimpleIntegerProperty(waitingTime);
		this.serviceDuration = new SimpleIntegerProperty(serviceDuration);
		this.serviceStart = new SimpleStringProperty(serviceStart != null ? serviceStart :"");
		this.serviceEnd = new SimpleStringProperty(serviceEnd != null ? serviceEnd : "");
	}
	
	public int getQueueNo() {return queueNo.get();}
	public String getPatientId() {return patientId.get();}
	public String getPatientName() {return patientName.get();}
	public String getType() {return type.get();}
	public String getTriageLevel() {return triageLevel.get();}
	public String getArrivalTime() {return arrivalTime.get();}
	public int getWaitingTime() {return waitingTime.get();}
	public int getServiceDuration() {return serviceDuration.get();}
	public String getServiceStart() {return serviceStart.get();}
    public String getServiceEnd() {return serviceEnd.get();}
	
	public void setPatientName(String patientName) { this.patientName.set(patientName); }
    public void setType(String type) { this.type.set(type); }
    public void setTriageLevel(String triageLevel) { this.triageLevel.set(triageLevel); }
    public void setArrivalTime(String arrivalTime) { this.arrivalTime.set(arrivalTime); }
    public void setServiceStart(String serviceStart) { this.serviceStart.set(serviceStart); }
    public void setServiceEnd(String serviceEnd) { this.serviceEnd.set(serviceEnd); }
    public void setWaitingTime(int waitingTime) { this.waitingTime.set(waitingTime); }
    public void setServiceDuration(int serviceDuration) { this.serviceDuration.set(serviceDuration); }
}