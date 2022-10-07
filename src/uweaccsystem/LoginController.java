package uweaccsystem;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Raul-Andrei Ginj-Groszhart (18007497)
 */
public class LoginController implements Initializable {
    
    @FXML
    private Label labelStatus;

    @FXML
    private TextField username;

    @FXML
    private TextField password;
    
    UWEAS uweasInstanceLogin = new UWEAS();
    
    ObservableList<General> dataLogin = FXCollections.observableArrayList(); 
    
    public void passData(ObservableList<General> parameterData){
        dataLogin.addAll(parameterData);
    }
    
    public void passInstance(UWEAS parameterInstance){
        uweasInstanceLogin = parameterInstance;
    }

    public void login(ActionEvent event) throws IOException {
        // Manager authentication
        if ((username.getText().equals("manager")) && (password.getText().equals("managerPass")))
        {
            UWEAS.getMainInstance().setKeyMain("Manager");
            changeStage(event);
        }
        else if (((username.getText().equals("warden") ) && (password.getText().equals("wardenPass"))))
        {   
            UWEAS.getMainInstance().setKeyMain("Warden");
            changeStage(event);
        }
        else if ((username.getText().equals("sudo")) && (password.getText().equals("admin")))
        {
            UWEAS.getMainInstance().setKeyMain("sudo");
            changeStage(event);
        }
        else 
        {
            labelStatus.setTextFill(Color.web("#ff0000"));
            labelStatus.setText("Wrong credentials!");
        }
    }
    
    public void changeStage(ActionEvent event) throws IOException{
        Parent mainView = FXMLLoader.load(getClass().getResource("MainView.fxml"));
        Scene mainViewScene = new Scene(mainView);
            
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainViewScene);
        window.show();
        window.centerOnScreen();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {    
        
    }    
    
}
