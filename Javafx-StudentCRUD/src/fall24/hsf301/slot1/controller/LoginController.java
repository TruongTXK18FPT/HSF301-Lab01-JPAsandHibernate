package fall24.hsf301.slot1.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import fall24.hsf301.slot1.pojo.Accounts;
import fall24.hsf301.slot1.service.AccountService;
import fall24.hsf301.slot1.service.IAccountService;
import fall24.hsf301.slot1.service.StudentService;

public class LoginController implements Initializable {

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    @FXML
    private Button btLogin;

    @FXML
    private Button btCancel;
    
    private IAccountService accountService;
    
    Scanner sc = new Scanner(System.in);
    
    public LoginController() {
    	accountService = new AccountService("JPAs");
    }
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	// TODO Auto-generated method stub
    	
    }
    @FXML
    public void closeLoginForm(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    	//Platform.exit();
    	System.out.println("Close Form");
    }
    /*public void btLoginAction(ActionEvent e) throws IOException{
    	((Stage)((Node)e.getSource()).getScene().getWindow()).close();
    		int roleID = 1;
    	if(txtUsername.getText().equals("admin")&&txtPassword.getText().equals("admin")) {
    		//Parent root = (Parent)FXMLLoader.load(getClass().getResource("../application/StudentManagement.fxml"));
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("../application/StudentManagement.fxml"));
    		Parent root = loader.load();
    		StudentManagementController svController = loader.getController();
    		svController.setRoleID(1);
    		Stage primaryStage = new Stage();
    		primaryStage.setScene(new Scene(root));
    		primaryStage.show();
    	}
    }*/
    @FXML
    public void btLoginAction(ActionEvent event) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        // Authenticate the user using the service
        Accounts account = accountService.findByUserNameAndPassword(username, password);

        if (account != null) {
            System.out.println("Login successful for user: " + account.getUserName());

            try {
                // Load StudentManagement.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../application/StudentManagement.fxml"));
                Parent root = loader.load();

                // Get the controller of StudentManagement and set necessary data
                StudentManagementController controller = loader.getController();
                controller.setRoleID(1);  // Assuming roleID is 1 for the admin role

                // Set the new scene in a new window (or the same one if preferred)
                Stage primaryStage = new Stage();
                primaryStage.setScene(new Scene(root));
                primaryStage.show();

                // Close the login window
                ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    
}


