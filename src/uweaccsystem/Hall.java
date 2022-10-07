package uweaccsystem;

import java.util.ArrayList;

/**
 *
 * @author George Kelly
 */
public class Hall {
    private ArrayList<Room> rooms;
    private int hallID;
    private String hallName;
    private String address;
    private String tel;

    public Hall(int hallID, String name, String address, String tel) {
        this.hallID = hallID;
        this.hallName = name;
        this.address = address;
        this.tel = tel;
        
        this.rooms = new ArrayList<Room>(); //Creates a blank Array List of rooms
    }
    
    public void addRoom(Room room){
        rooms.add(room);
    }
    
    public int getNumberOfRooms(){
        return rooms.size();
    }
    
    public ArrayList<Room> getRooms()
    {
        return rooms;
    }

    public int getHallID() {
        return hallID;
    }

    public void setHallID(int hallID) {
        this.hallID = hallID;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
    
    

}
