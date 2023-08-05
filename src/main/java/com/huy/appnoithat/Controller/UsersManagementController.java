package com.huy.appnoithat.Controller;

import com.huy.appnoithat.Entity.Account;
import com.huy.appnoithat.Entity.AccountInformation;
import com.huy.appnoithat.Service.SessionService.UserSessionService;
import com.huy.appnoithat.Service.UsersManagement.UsersManagementService;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

public class UsersManagementController{

    @FXML
    private TableColumn<Account, Integer> active;

    @FXML
    private Button btnAddUser;

    @FXML
    private Button btnSearch;

    @FXML
    private TableColumn<Account, String> password;

    @FXML
    private TableView<Account> tableManageUser;

    @FXML
    private TextField txtSearchUser;

    @FXML
    private TableColumn<Account, String> username;


    UsersManagementService user = new UsersManagementService();

    List<Account> list = user.findAllAccount();
    ObservableList<Account> listUser = FXCollections.observableArrayList(
            new Account(1, "john_doe", "password123", 1, "ADMIN", new AccountInformation()),
            new Account(2, "jane_smith", "ilovecoding", 1, "USER", new AccountInformation())
    );
    UserSessionService userSessionService;
    public UsersManagementController() {
        userSessionService = new UserSessionService();
    }
    public void initialize() {
        username.setCellValueFactory(new PropertyValueFactory<Account,String>("username"));
        password.setCellValueFactory(new PropertyValueFactory<Account,String>("password"));
        active.setCellValueFactory(new PropertyValueFactory<Account,Integer>("active"));
        tableManageUser.setItems(listUser);
    }


    // Used to switch between scence
    @FXML
    private void sceneSwitcher(ActionEvent actionEvent) {
        Scene scene = null;
        Stage stage = null;
        Object source = actionEvent.getSource();
        stage = (Stage) ((Node)source).getScene().getWindow();
//        if (source == LogoutButton){
//            scene = LoginScene.getInstance().getScene();
//        }
//        else {
//            return;
//        }
        stage.setScene(scene);
        stage.show();
    }
}
