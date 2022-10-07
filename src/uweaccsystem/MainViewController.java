package uweaccsystem;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Raul-Andrei Ginj-Groszhart (18007497)
 */
public class MainViewController implements Initializable {
    
    @FXML private Text roomsText;
    @FXML private Text footerText;
    
    @FXML private Button createLeaseBtn;
    @FXML private Button deleteLeaseBtn;
    
    @FXML private TableView<General> tableView;
    @FXML private TableColumn<General, String> leaseNumberCol;
    @FXML private TableColumn<General, String> hallNameCol;
    @FXML private TableColumn<General, String> hallNumberCol;
    @FXML private TableColumn<General, String> roomNumberCol;
    @FXML private TableColumn<General, String> studentNameCol;
    @FXML private TableColumn<General, String> occupancyStatusCol;
    @FXML private TableColumn<General, String> cleaningStatusCol;
    
    @FXML private ComboBox cleaningStatusBox;
    @FXML private CheckBox isOccupiedBox;
    
    @FXML private TextField hallIDField;
    @FXML private TextField hallNameField;
    @FXML private TextField hallAddressField;
    @FXML private TextField hallPhoneNumberField;
    @FXML private TextField roomNumberField;
    @FXML private TextField studentIDField;
    @FXML private TextField studentNameField;
    @FXML private TextField monthlyRentField;
    @FXML private TextField leaseDurationField;
    @FXML private TextField leaseNumberField;
    
    ObservableList<General> dataManager = FXCollections.observableArrayList();
    ObservableList<String> cleaningStatusList = FXCollections.observableArrayList("Clean","Dirty","Offline");
    
    private List<String> leaseNumberList = new ArrayList<>();
    
    private int totalRooms;
    
    @FXML void applyChanges(ActionEvent event) {
        General selection = tableView.getSelectionModel().getSelectedItem();
        if(selection == null){
            createAlertBox(AlertType.ERROR, "ERROR", "NO SELECTION", "You must select a row before applying any changes.");
        }
        else if(UWEAS.getMainInstance().getKeyMain() == "Warden"){
            selection.setCleaningStatus(String.valueOf(cleaningStatusBox.getValue()));
            tableView.refresh();
            createAlertBox(AlertType.INFORMATION, "CLEANING STATUS CHANGED", null, "Cleaning status for room " + selection.getRoomNumber() + " in hall " + selection.getHallName() + " has been set to: " + selection.getCleaningStatus());
            
            // WHAT HAPPENS WHEN CHANGED TO OFFLINE?
            // just delete lease and student info??
        }
        else if (UWEAS.getMainInstance().getKeyMain() == "Manager"){
            //edit room details without cleaning
            selection.setMonthlyRent(monthlyRentField.getText());
            selection.setIsOccupied(isOccupiedBox.isSelected());
            
            if(isOccupiedBox.isSelected()){
                selection.setOccupancyStatus("Occupied");
            } else {
                selection.setOccupancyStatus("Unoccupied");
            }
            
            selection.setStudentName(studentNameField.getText());
            selection.setStudentID(studentIDField.getText());
            selection.setLeaseNumber(leaseNumberField.getText());
            selection.setLeaseDuration(leaseDurationField.getText());
            
            tableView.refresh();
            createAlertBox(AlertType.INFORMATION, "CHANGES APPLIED", null, "Changes have been saved.");
            //System.out.println("Stuff has been edited by the manager");
        }
        else if (UWEAS.getMainInstance().getKeyMain() == "sudo"){
            selection.setCleaningStatus(String.valueOf(cleaningStatusBox.getValue()));
            selection.setMonthlyRent(monthlyRentField.getText());
            selection.setIsOccupied(isOccupiedBox.isSelected());
            
            if(isOccupiedBox.isSelected()){
                selection.setOccupancyStatus("Occupied");
            } else {
                selection.setOccupancyStatus("Unoccupied");
            }
            
            selection.setStudentName(studentNameField.getText());
            selection.setStudentID(studentIDField.getText());
            selection.setLeaseNumber(leaseNumberField.getText());
            selection.setLeaseDuration(leaseDurationField.getText());
            selection.setCleaningStatus(String.valueOf(cleaningStatusBox.getValue()));
            
            tableView.refresh();
            createAlertBox(AlertType.INFORMATION, "CHANGES APPLIED", null, "Changes have been saved.");
            //System.out.println("Stuff has been edited by sudo");
        }
    }
    
    // DONE
    @FXML void createLease(ActionEvent event) {      
        General selection = tableView.getSelectionModel().getSelectedItem();
        if(selection == null){
            createAlertBox(AlertType.ERROR, "ERROR", "NO SELECTION", "You must select a row before creating a lease.");
        }
        else if(selection.getLeaseNumber() == ""){
            if(selection.getCleaningStatus() == "Offline"){
                //System.out.println("LEASES CANNOT BE CREATED FOR AN OFFLINE ROOM!");
                createAlertBox(AlertType.ERROR, "ERROR", null, "LEASES CANNOT BE CREATED FOR AN OFFLINE ROOM!");
            } 
            else if (selection.isOccupied()){
                //System.out.println("LEASES CANNOT BE CREATED FOR AN OCCUPIED ROOM!");
                createAlertBox(AlertType.ERROR, "ERROR", null, "LEASES CANNOT BE CREATED FOR AN OCCUPIED ROOM!");
            }
            else if(leaseNumberField.getText().trim().isEmpty() || leaseDurationField.getText().trim().isEmpty()){
                //System.out.println("PLEASE ENTER LEASE INFORMATION!");
                createAlertBox(AlertType.ERROR, "ERROR", "PLEASE ENTER LEASE INFORMATION!", "A lease cannot be created because lease information is missing.");
            }
            else if (studentNameField.getText().trim().isEmpty() || studentIDField.getText().trim().isEmpty()){
                //System.out.println("PLEASE ENTER STUDENT INFORMATION!");
                createAlertBox(AlertType.ERROR, "ERROR", "PLEASE ENTER STUDENT INFORMATION!", "A lease cannot be created because student information is missing.");
            }
            else if (checkLeaseNum(leaseNumberField.getText())){
                createAlertBox(AlertType.ERROR, "ERROR", "LEASE NUMBER ALREADY EXISTS!", "A lease with the same lease number already exists, please use another number.");
                //System.out.println("LEASE NUMBER ALREADY EXISTS!");
            } 
            else if(!validateInt(leaseNumberField.getText()) || !validateInt(leaseDurationField.getText()) || !validateInt(studentIDField.getText())){
                createAlertBox(AlertType.ERROR, "ERROR", "INTEGER VALIDATION ERROR", "Check lease number, lease duration and student ID - they need to be integers.");
            }
            else if (Integer.parseInt(leaseDurationField.getText()) > 12 || Integer.parseInt(leaseDurationField.getText()) < 1){
                createAlertBox(AlertType.ERROR, "ERROR", "LEASE DURATION", "Lease duration must be between 1 and 12");
            }
            else {
                selection.setLeaseNumber(leaseNumberField.getText());
                leaseNumberList.add(leaseNumberField.getText());
                selection.setLeaseDuration(leaseDurationField.getText());
                selection.setIsOccupied(true);
                selection.setOccupancyStatus("Occupied");
                selection.setStudentName(studentNameField.getText());
                selection.setStudentID(studentIDField.getText());
                tableView.refresh();
                
                //System.out.println("LEASE HAS BEEN CREATED!");
                createAlertBox(AlertType.INFORMATION, "SUCCESS", "LEASE CREATED", "Lease " + leaseNumberField.getText() + " has been created successfully!");
            }
        } 
        else {
            createAlertBox(AlertType.ERROR, "ERROR", "LEASE ALREADY EXISTS FOR THIS ROOM", "Pick an unoccupied room and try again.");
            // System.out.println("LEASE ALREADY EXISTS!");
        }
    }
    // DONE
    @FXML void deleteLease(ActionEvent event) {
        General selection = tableView.getSelectionModel().getSelectedItem();
        if(selection == null){
            createAlertBox(AlertType.ERROR, "ERROR", "NO SELECTION", "You must select a row before deleting a lease.");
        }
        else if(selection.getLeaseNumber() != "" || selection.isOccupied()){
            leaseNumberList.remove(String.valueOf(selection.getLeaseNumber()));
            selection.setLeaseNumber("");
            selection.setLeaseDuration("");
            selection.setIsOccupied(false);
            selection.setOccupancyStatus("Unoccupied");
            selection.setStudentID("");
            selection.setStudentName("");
            
            //leaseNumberList.remove(String.valueOf(selection.getLeaseNumber()));
            
            tableView.refresh();
            createAlertBox(AlertType.INFORMATION, "LEASE DELETED", null, "Lease " + leaseNumberField.getText() + " has been deleted!");
        }
        else {
            createAlertBox(AlertType.ERROR, "ERROR", "LEASE DOES NOT EXIST!", "You cannot delete a non-existing lease.");
        } 
    }
    // DONE
    @FXML void logout(ActionEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Login.fxml"));
            Parent login = loader.load();

            Scene loginScene = new Scene(login);
            
            LoginController controller = loader.getController();
            controller.passData(dataManager);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(loginScene);
            window.show();
            window.centerOnScreen();
    }
    
    boolean checkLeaseNum(String checkNum){
        for(int i = 0; i < leaseNumberList.size(); i++){
            if(leaseNumberList.contains(checkNum)){
                return true;
            }
        }
        return false;
    }
    
    void createAlertBox(AlertType type, String title, String header, String content){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("images/uwe_icon.png"));
        
        alert.showAndWait();
    }
    
    boolean validateInt(String strToCheck){
        try {
            Integer.parseInt(strToCheck);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ArrayList<Hall> halls = UWEAS.getMainInstance().getHalls();
        for (int i=0 ; i < halls.size() ; i++){
            
            Hall currentHall = halls.get(i);
            ArrayList<Room> rooms = currentHall.getRooms();
            totalRooms += rooms.size();
            
            for (int j = 0 ; j < rooms.size() ;  j++){
                Room room = rooms.get(j);
                
                Student student = null;
                String leaseNumber = "";
                int leaseDur = 0;                
                String studentName = ""; 
                int studentID = 0;
                String cleaningStatus = "";
                boolean OccStatus = false;
                String OccStatusString = "";   
                
                if (room.isOccupied()){
                    Lease lease = room.getLease();
                    student = lease.getStudent();
                    leaseNumber = String.valueOf(lease.getLeaseNo());
                    leaseDur = lease.getLeaseDuration();
                    OccStatus = true;
                    leaseNumberList.add(String.valueOf(lease.getLeaseNo()));
                }
                
                if (OccStatus == true){
                    OccStatusString = "Occupied";
                }
                else {
                    OccStatusString = "Unoccupied";
                }
                
                if (student != null){
                    studentName = student.getStudentName();
                    studentID = student.getStudentID();
                }
                
                switch (room.getCleaningStatus()) {
                    case 1:
                        cleaningStatus = "Clean";
                        break;
                    case 2:
                        cleaningStatus = "Dirty";
                        break;
                    case 3:
                        cleaningStatus = "Offline";
                        break;
                }
                
                General row = new General(
                                    String.valueOf(currentHall.getHallName()),
                                    String.valueOf(currentHall.getAddress()),
                                    String.valueOf(currentHall.getHallID()),
                                    String.valueOf(currentHall.getTel()),
                                    String.valueOf(room.getRoomNumber()),
                                    OccStatus,
                                    OccStatusString,                       
                                    cleaningStatus,
                                    String.valueOf(room.getMonthlyRate()),
                                    leaseNumber,
                                    String.valueOf(leaseDur),
                                    String.valueOf(studentID),
                                    studentName
                );
                dataManager.add(row);
            }
        }
        
        // Sets up the columns in the table
        leaseNumberCol.setCellValueFactory(new PropertyValueFactory<>("leaseNumber"));
        hallNameCol.setCellValueFactory(new PropertyValueFactory<>("hallName"));
        hallNumberCol.setCellValueFactory(new PropertyValueFactory<>("hallID"));
        roomNumberCol.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        studentNameCol.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        occupancyStatusCol.setCellValueFactory(new PropertyValueFactory<>("occupancyStatus"));
        cleaningStatusCol.setCellValueFactory(new PropertyValueFactory<>("cleaningStatus"));
        
        tableView.setItems(dataManager);
        
        cleaningStatusBox.setValue("Clean");
        cleaningStatusBox.setItems(cleaningStatusList);
        
        tableView.setRowFactory(tableView -> 
        {
            TableRow<General> row = new TableRow<>();
            row.setOnMouseClicked(event -> 
            {
                General data = row.getItem();
                if (data != null) 
                {
                    leaseInfo(data);
                }
            });
            return row;
        });
        
        checkEntity();
        
        roomsText.setText("Total number of rooms: " + String.valueOf(totalRooms));
    }    
    
    void checkEntity(){
    if ("Warden".equals(UWEAS.getMainInstance().getKeyMain()))
        {
            footerText.setText("Copyright 2020 - "
                    + "UWE Accommodation System | Logged in as: Warden");
            
            // Disable input fields
            hallNameField.setEditable(false);
            hallAddressField.setEditable(false);              
            hallIDField.setEditable(false);
            hallPhoneNumberField.setEditable(false);
            studentIDField.setEditable(false);
            studentNameField.setEditable(false);
            monthlyRentField.setEditable(false);
            leaseDurationField.setEditable(false);
            leaseNumberField.setEditable(false);
            roomNumberField.setEditable(false);
            isOccupiedBox.setDisable(true);
            
            // Hide lease buttons
            createLeaseBtn.setVisible(false);
            deleteLeaseBtn.setVisible(false);
        }
        else if ("Manager".equals(UWEAS.getMainInstance().getKeyMain()))
        {
            footerText.setText("Copyright 2020 - "
                    + "UWE Accommodation System | Logged in as: Manager");
            
            // Disable some input fields
            hallNameField.setEditable(false);
            hallAddressField.setEditable(false);              
            hallIDField.setEditable(false);
            hallPhoneNumberField.setEditable(false);
            cleaningStatusBox.setDisable(true);
            roomNumberField.setEditable(false);
        }
        else if ("sudo".equals(UWEAS.getMainInstance().getKeyMain()))
        {
            footerText.setText("Copyright 2020 - "
                    + "UWE Accommodation System | Logged in as: sudo");
            
        }
    }
    
    private void leaseInfo(General row) {
        hallIDField.setText(row.getHallID());
        hallNameField.setText(row.getHallName());
        hallAddressField.setText(row.getHallAdress());
        hallPhoneNumberField.setText(row.getHallPhoneNumber());
        roomNumberField.setText(row.getRoomNumber());
        cleaningStatusBox.getSelectionModel().select(row.getCleaningStatus());
        monthlyRentField.setText(row.getMonthlyRent());
        if(row.isOccupied()){
            isOccupiedBox.setSelected(true);
            leaseNumberField.setText(row.getLeaseNumber());
            leaseDurationField.setText(row.getLeaseDuration());
            studentIDField.setText(row.getStudentID());
            studentNameField.setText(row.getStudentName());
        }
        else {
            leaseNumberField.setText("");
            leaseDurationField.setText("");
            studentIDField.setText("");
            studentNameField.setText("");
            isOccupiedBox.setSelected(false);
        }   
    }
}