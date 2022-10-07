package uweaccsystem;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Raul-Andrei Ginj-Groszhart (18007497)
 */
public class General {
    
    // Hall
    private final SimpleStringProperty hallName;
    private final SimpleStringProperty hallID;
    private final SimpleStringProperty hallAdress;
    private final SimpleStringProperty hallPhoneNumber;
    
    // Room
    private final SimpleStringProperty roomNumber;
    private final SimpleStringProperty monthlyRent;
    private final SimpleStringProperty occupancyStatusStr;
    private final SimpleStringProperty cleaningStatus;
    
    private boolean occupancyStatus;
    
    // Lease
    private final SimpleStringProperty leaseNumber;
    private final SimpleStringProperty leaseDuration;
    
    //Student
    private final SimpleStringProperty studentName;
    private final SimpleStringProperty studentID;
    
    // Constructor
    public General(
            String hallName, 
            String hallAddress,
            String hallID,
            String hallPhone,
            String roomNumber, 
            boolean occupancyStatusBool, 
            String occupancyStatus,
            String cleaningStatus,
            String monthlyRent,
            String leaseNumber,
            String leaseDuration,
            String studentID,
            String studentName 
    ){    
        this.hallName = new SimpleStringProperty(hallName);
        this.hallID = new SimpleStringProperty(hallID);
        this.hallPhoneNumber = new SimpleStringProperty(hallPhone);
        this.hallAdress = new SimpleStringProperty(hallAddress);
        this.leaseNumber = new SimpleStringProperty(leaseNumber);
        this.leaseDuration = new SimpleStringProperty(leaseDuration);
        this.roomNumber = new SimpleStringProperty(roomNumber);
        this.studentID = new SimpleStringProperty(studentID);
        this.studentName = new SimpleStringProperty(studentName);
        this.monthlyRent = new SimpleStringProperty(monthlyRent);
        this.occupancyStatus = occupancyStatusBool;
        this.occupancyStatusStr = new SimpleStringProperty(occupancyStatus);
        this.cleaningStatus = new SimpleStringProperty(cleaningStatus);
    }
    
    public String getHallName() {
        return hallName.get();
    }
       
    public String getHallID() {
        return hallID.get();
    }

    public String getHallAdress() {
        return hallAdress.get();
    }

    public String getHallPhoneNumber() {
        return hallPhoneNumber.get();
    }

    public String getRoomNumber() {
        return roomNumber.get();
    }
    
    public String getMonthlyRent() {
        return monthlyRent.get();
    }
    
    public void setMonthlyRent(String rent){
        this.monthlyRent.set(rent);
    }

    public String getOccupancyStatus() {
        return occupancyStatusStr.get();
    }
    
    public void setOccupancyStatus(String occupancy){
        this.occupancyStatusStr.set(occupancy);
    }
    
    public boolean isOccupied() {
        return occupancyStatus;
    }

    public void setIsOccupied(boolean occupancyStatus) {
        this.occupancyStatus = occupancyStatus;
    }

    public String getCleaningStatus() {
        return cleaningStatus.get();
    }
    
    public void setCleaningStatus(String cleaningStatus) {
        this.cleaningStatus.set(cleaningStatus);
    }
    
    public String getLeaseNumber() {
        return leaseNumber.get();
    }
    
    public void setLeaseNumber(String leaseNum){
        this.leaseNumber.set(leaseNum);
    }

    public String getLeaseDuration() {
        return leaseDuration.get();
    }
    
    public void setLeaseDuration(String leaseDuration){
        this.leaseDuration.set(leaseDuration);
    }
    
    public String getStudentName() {
        return studentName.get();
    }
    
    public void setStudentName(String studentName){
        this.studentName.set(studentName);
    }

    public String getStudentID() {
        return studentID.get();
    }
    
    public void setStudentID(String studentID){
        this.studentID.set(studentID);
    }
}
