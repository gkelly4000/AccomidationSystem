package uweaccsystem;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.image.Image;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Raul-Andrei Ginj-Groszhart (18007497)
 */
public class UWEAS extends Application {
    
    private static UWEAS mainInstance = new UWEAS();
        
    private ArrayList<Hall> halls;

    private static UWEAS instance;
    
    private String keyMain = "";

    public String getKeyMain() {
        return keyMain;
    }

    public void setKeyMain(String keyMain) {
        this.keyMain = keyMain;
    }

    public static UWEAS getMainInstance() {
        return mainInstance;
    }

    public static UWEAS getInstance() {
        if (instance == null) {
            instance = new UWEAS();
        }
        return instance;
    }
    
    public UWEAS(){
        //ArrayList of Halls
        halls = new ArrayList<Hall>();
    }
    
    public ArrayList<Hall> getHalls(){
        return halls;
    }
    
    public void addHall(Hall hall){
        halls.add(hall);
    }
    
    public void populateData(){
        
        // Generate halls
        Hall mendip = new Hall(100, "Mendip Court", "Mendip Court, Coldharbour Lane, BS16 1ZL", "+44 (0)117 9652261");
        Hall quantock = new Hall(200, "Quantock Court", "Quantock Court, Coldharbour Lane, BS16 1ZP", "+44 (0)117 9653262");
        Hall carroll = new Hall(300, "Carroll Court", "Carroll Court, Coldharbour Lane, BS16 1QY", "+44 (0)117 9654263");
        
        // Generate random rooms for each hall
        generateRandomRooms(mendip);
        generateRandomRooms(quantock);
        generateRandomRooms(carroll);
        
        // Add halls to list
        addHall(mendip);
        addHall(quantock);
        addHall(carroll);
        
        // Generate leases and students
        Lease l1 = new Lease(new Student(18007497, "Raul-Andrei Ginj-Groszhart"), 1001, 10);
        Lease l2 = new Lease(new Student(17024831, "Alexandra Rotaru"), 1002, 5);
        Lease l3 = new Lease(new Student(12345678, "Joe Bloggs"), 1003, 12);
        Lease l4 = new Lease(new Student(87654321, "Bloe Joggs"), 1004, 7);
        
        // Pick a random room and assign the lease
        pickRandomRoom(mendip, l1);
        pickRandomRoom(quantock, l2);
        pickRandomRoom(carroll, l3);
        pickRandomRoom(quantock, l4);
    }
    
    // Choose a random room to assign a lease to, if the room is not offline
    void pickRandomRoom(Hall hall, Lease lease){
        int randRoom = new Random().nextInt(hall.getNumberOfRooms());
        Room room = hall.getRooms().get(randRoom);
        if (room.getCleaningStatus() != 3){
            room.setLease(lease);
        }
    }
    
    void generateRandomRooms(Hall hall){

        // Array of monthly rates 
        int[] monthlyRateArray = new int[]{600, 650, 700};
        
        for(int i = 0; i < 16; i++){
            // Generate random clean status
           int cleaningStatus = new Random().nextInt(3);
           
           // Generate random monthly rate from array
           int randNum = new Random().nextInt(3);
           int monthlyRate = monthlyRateArray[randNum];
           
           // Generate roomNum
           int roomNum = 100 + i;
           
           // Generate temp room
           Room roomObj = new Room(roomNum, cleaningStatus+1, monthlyRate, false);
           //roomObj.setHall(hall);
           
           // Add room to halls
            hall.addRoom(roomObj);
        }
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        primaryStage.setTitle("UWE Accommodation System Login");
        Scene scene = new Scene(root, 550, 250);
        
        primaryStage.getIcons().add(new Image("images/uwe_icon.png"));
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
    }

    public static void main(String[] args) {
        mainInstance.populateData();
        mainInstance.setKeyMain("Manager");
        launch(args);      
    }
}
