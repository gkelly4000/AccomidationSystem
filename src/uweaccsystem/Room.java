package uweaccsystem;

/**
 *
 * @author George Kelly
 */
public class Room {
    private Lease lease;
    private int roomNumber;
    private int cleaningStatus;
    private int monthlyRate;
    private boolean isOccupied;

    public Room(int roomNumber, int cleaningStatus, int monthlyRate, boolean isOccupied) {
        this.roomNumber = roomNumber;
        this.cleaningStatus = cleaningStatus;
        this.monthlyRate = monthlyRate;
        this.isOccupied = isOccupied;
        this.lease = null; 
    }

    public Lease getLease() {
        return lease;
    }

    public void setLease(Lease lease) {
        this.lease = lease;
    }
    
    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getCleaningStatus() {
        return cleaningStatus;
    }

    public void setCleaningStatus(int cleaningStatus) {
        this.cleaningStatus = cleaningStatus;
    }

    public int getMonthlyRate() {
        return monthlyRate;
    }

    public void setMonthlyRate(int monthlyRate) {
        this.monthlyRate = monthlyRate;
    }

    public boolean isOccupied()
    {
        return !(getLease() == null);
    }

    public void setIsOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }
    
}
