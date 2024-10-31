package fall24.hsf301.slot1.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import fall24.hsf301.slot1.pojo.Student;
import fall24.hsf301.slot1.service.IStudentService;
import fall24.hsf301.slot1.service.StudentService;

public class StudentManagementController implements Initializable {

    @FXML
    private Button btnCreate;
    @FXML
    private TableView<Student> stdTable;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtMark;
    @FXML
    private TableColumn<Student, Integer> id;
    @FXML
    private TableColumn<Student, String> firstName;
    @FXML
    private TableColumn<Student, String> lastName;
    @FXML
    private TableColumn<Student, String> mark;

    private IStudentService studentService;
    private ObservableList<Student> studentModel;

    private int roleID;

    public StudentManagementController() {
        int choice = 0;
        System.out.println("1. JPAs");
        System.out.println("2. Hibernate");
        System.out.println("Enter choice: ");
        choice = new Scanner(System.in).nextInt();
        studentService = new StudentService(choice == 1 ? "JPAs" : "hibernate.cfg.xml", choice);
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
        btnCreate.setDisable(this.roleID != 1);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        mark.setCellValueFactory(new PropertyValueFactory<>("Mark"));
        
        refreshDataGrid();

        // Add event listener for row selection
        stdTable.setOnMouseClicked(this::handleRowSelect);
    }

    // Event handler for row selection
    private void handleRowSelect(MouseEvent event) {
        Student selectedStudent = stdTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            txtId.setText(String.valueOf(selectedStudent.getId()));
            txtFirstName.setText(selectedStudent.getFirstName());
            txtLastName.setText(selectedStudent.getLastName());
            txtMark.setText(selectedStudent.getMark());
        }
    }

    @FXML
    public void btnCancelOnAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void btnCreateOnAction() {
        Student student = new Student();
        student.setFirstName(txtFirstName.getText());
        student.setLastName(txtLastName.getText());
        student.setMark(txtMark.getText());
        studentService.save(student);
        refreshDataGrid();
    }

    @FXML
    public void btnDeleteOnAction() {
        String idText = txtId.getText();
        if (!idText.isEmpty()) {
            int studentId = Integer.parseInt(idText);
            studentService.delete(studentId);
            clearInputFields();
            refreshDataGrid();
        }
    }

    @FXML
    public void btnUpdateOnAction() {
        String idText = txtId.getText();
        if (!idText.isEmpty()) {
            int studentId = Integer.parseInt(idText);
            Student existingStudent = studentService.findById(studentId);
            if (existingStudent != null) {
                existingStudent.setFirstName(txtFirstName.getText());
                existingStudent.setLastName(txtLastName.getText());
                existingStudent.setMark(txtMark.getText());
                studentService.update(existingStudent);
                refreshDataGrid();
                clearInputFields();
            }
        }
    }

    @FXML
    public void btnFindOnAction() {
        String idText = txtId.getText();
        if (!idText.isEmpty()) {
            try {
                int studentId = Integer.parseInt(idText);
                Student student = studentService.findById(studentId);
                if (student != null) {
                    ObservableList<Student> filteredList = FXCollections.observableArrayList();
                    filteredList.add(student);
                    stdTable.setItems(filteredList);
                    clearInputFields();
                } else {
                    showAlert("Warning", "No student found with ID: " + studentId);
                }
            } catch (NumberFormatException e) {
                showAlert("Error", "Invalid ID format. Please enter a number.");
            }
        } else {
            showAlert("Error", "Please enter a valid student ID.");
        }
    }

    @FXML
    public void btnSearchAllOnAction() {
        refreshDataGrid();
    }

    private void refreshDataGrid() {
        studentModel = FXCollections.observableArrayList(studentService.findAll());
        stdTable.setItems(studentModel);
    }

    private void clearInputFields() {
        txtId.clear();
        txtFirstName.clear();
        txtLastName.clear();
        txtMark.clear();
    }

    private void showAlert(String header, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
