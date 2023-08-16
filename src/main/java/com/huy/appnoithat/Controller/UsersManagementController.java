package com.huy.appnoithat.Controller;

import com.huy.appnoithat.Entity.Account;
import com.huy.appnoithat.Entity.AccountInformation;
import com.huy.appnoithat.Scene.HomeScene;
import com.huy.appnoithat.Scene.ListAccountWaitToApproveScene;
import com.huy.appnoithat.Scene.UserManagementAddAccountScene;
import com.huy.appnoithat.Scene.UserManagementEditorScene;
import com.huy.appnoithat.Service.SessionService.UserSessionService;
import com.huy.appnoithat.Service.UsersManagement.UsersManagementService;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UsersManagementController{
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AccountTable {
        private int id;
        private String username;
        private String password;
        private int active;
        private ImageView activeImage;
    }

    @FXML
    private TableColumn<AccountTable, ImageView> active;

    @FXML
    private Button btnActiveAccount;

    @FXML
    private Button btnAddacount;

    @FXML
    private Button btnAllAccount;

    @FXML
    private Button btnDeleteAccount;

    @FXML
    private Button btnEditAccount;

    @FXML
    private Button btnSearch;
    @FXML
    private Button backButton;
    @FXML
    private TableColumn<AccountTable, String> password;

    @FXML
    private TableView<AccountTable> tableManageUser;

    @FXML
    private TextField txtSearchUser;

    @FXML
    private TableColumn<AccountTable, String> username;


    UsersManagementService user = new UsersManagementService();

    @Getter
    List<Account> list = user.findAllAccount();
    public ImageView convertActiveIcon(boolean checked){
        ImageView activeIcon;
        if(checked){
             activeIcon = new ImageView(new Image(this.getClass().getResourceAsStream("/com/huy/appnoithat/Scene/icons/activeicon.png")));
        }else{
             activeIcon = new ImageView(new Image(this.getClass().getResourceAsStream("/com/huy/appnoithat/Scene/icons/notActive.png")));
        }
        activeIcon.setFitHeight(20);
        activeIcon.setFitWidth(20);
        return activeIcon;
    }
    ObservableList<AccountTable> listUser = FXCollections.observableArrayList(
            new AccountTable(1, "long", "ilovecoding", 1,convertActiveIcon(true)),
            new AccountTable(2, "linh", "ilovecoding", 0,convertActiveIcon(false)),
            new AccountTable(3, "trang", "ilovecoding", 0,convertActiveIcon(false)),
            new AccountTable(4, "huy", "ilovecoding", 1,convertActiveIcon(true))
    );


    UserSessionService userSessionService;
    public UsersManagementController() {
        userSessionService = new UserSessionService();
    }
    public void initialize() {
        username.setCellValueFactory(new PropertyValueFactory<AccountTable,String>("username"));
        password.setCellValueFactory(new PropertyValueFactory<AccountTable,String>("password"));
        active.setCellValueFactory(new PropertyValueFactory<AccountTable,ImageView>("activeImage"));
        tableManageUser.setItems(listUser);
    }

    @FXML
    void getAllAcount(ActionEvent event) {
        initialize();
    }

    @FXML
    void SearchByUserName(ActionEvent event) {
        String username= txtSearchUser.getText();
        List<AccountTable> listFilter = seach(username);
        ObservableList<AccountTable> listUser = FXCollections.observableArrayList(listFilter);
        tableManageUser.setItems(listUser);
    }


    @FXML
    void ActiveAccount(ActionEvent event) {
        tableManageUser.getSelectionModel().getSelectedItem().setActiveImage(convertActiveIcon(true));
        tableManageUser.refresh();

    }

    @FXML
    void InActiveAccount(ActionEvent event) {
        tableManageUser.getSelectionModel().getSelectedItem().setActiveImage(convertActiveIcon(false));
        tableManageUser.refresh();
    }

    @FXML
    void AddAccount(ActionEvent event) {
        try {
            Stage userManageMentStage = new Stage();
            Scene userManagementAddAccountScene = UserManagementAddAccountScene.getInstance().getScene();

            TextField txtusername = (TextField) userManagementAddAccountScene.lookup("#txtaddusername");
            TextField txtpassword = (TextField) userManagementAddAccountScene.lookup("#txtaddpassword");
            TextField txtactive = (TextField) userManagementAddAccountScene.lookup("#txtaddactive");
            Button btnadd = (Button) userManagementAddAccountScene.lookup("#btnadd");
            Button btncancel = (Button) userManagementAddAccountScene.lookup("#btncancel");

            userManageMentStage.setScene(userManagementAddAccountScene);

            btnadd.setOnAction(actionEvent -> {
                listUser.add(new AccountTable(listUser.size(),txtusername.getText(),txtpassword.getText(),Integer.parseInt(txtactive.getText()),convertActiveIcon(true)));
                System.out.println(listUser.size());
                tableManageUser.refresh();
                userManageMentStage.close();
                // You might need additional logic to handle saving or updating data
            });

            btncancel.setOnAction(actionEvent -> {
                userManageMentStage.close();
            });

            userManageMentStage.setTitle("ADD NEW USER");
            userManageMentStage.show();
        }catch (Exception e){
            System.out.println("co loi dialog pane roi dai ca oi");
        }

    }

    @FXML
    void DeleteAccount(ActionEvent event) {
        int indexSelector = tableManageUser.getSelectionModel().getSelectedIndex();
        tableManageUser.getItems().remove(indexSelector);
    }

    @FXML
    void EditAccount(ActionEvent event) {
        try {

            Stage userManageMentStage = new Stage();
            Scene userManagementEditorScene = UserManagementEditorScene.getInstance().getScene();
//element field of AccountTable
            String username = tableManageUser.getSelectionModel().getSelectedItem().getUsername();
            String password = tableManageUser.getSelectionModel().getSelectedItem().getPassword();

//element field of userManagementEditorScene
            TextField txtusername = (TextField) userManagementEditorScene.lookup("#txteditusername");
            TextField txtpassword = (TextField) userManagementEditorScene.lookup("#txteditpassword");
            Button btnedit = (Button) userManagementEditorScene.lookup("#btnedit");
            Button btncancel = (Button) userManagementEditorScene.lookup("#btncancel");

// Create an example AccountTable object
            AccountTable exampleAccount = new AccountTable();
            exampleAccount.setUsername(username);
            exampleAccount.setPassword(password);

// Set the object's attributes to the text fields
            txtusername.setText(exampleAccount.getUsername());
            txtpassword.setText(exampleAccount.getPassword());

            userManageMentStage.setScene(userManagementEditorScene);
            userManageMentStage.setTitle("EDIT USER");
            userManageMentStage.show();


            btnedit.setOnAction(actionEvent -> {
                tableManageUser.getSelectionModel().getSelectedItem().setUsername(txtusername.getText());
                tableManageUser.getSelectionModel().getSelectedItem().setPassword(txtpassword.getText());
                tableManageUser.refresh();
                userManageMentStage.close();
                // You might need additional logic to handle saving or updating data
            });

            btncancel.setOnAction(actionEvent -> {
                userManageMentStage.close();
            });
        }catch (Exception e){
            System.out.println("co loi dialog pane roi dai ca oi");
        }
    }

    @FXML
    void tableClickToSelectItem(MouseEvent event) {
//        String username = tableManageUser.getSelectionModel().getSelectedItem().getUsername();
    }


    @FXML
    void DuyetAccount(ActionEvent event) {
        try {

            Stage userManageMentStage = new Stage();
            Scene listAccountWaitToApprove = ListAccountWaitToApproveScene.getInstance().getScene();

//element field of userManagementEditorScene
            TableView tableView = (TableView) listAccountWaitToApprove.lookup("#tableViewListAccount");
            Button btnApprove = (Button) listAccountWaitToApprove.lookup("#btnApprove");
            Button btnReject = (Button) listAccountWaitToApprove.lookup("#btnReject");
            Button btnCancel = (Button) listAccountWaitToApprove.lookup("#btnCancel");

            userManageMentStage.setScene(listAccountWaitToApprove);
            userManageMentStage.setTitle("LIST ACCOUNT TO APPROVE");
            userManageMentStage.show();


            btnApprove.setOnAction(actionEvent -> {

                userManageMentStage.close();
                // You might need additional logic to handle saving or updating data
            });

            btnCancel.setOnAction(actionEvent -> {
                userManageMentStage.close();
            });
        }catch (Exception e){
            System.out.println("co loi dialog pane roi dai ca oi");
        }
    }

    public List<AccountTable> seach(String searchUsername) {
        List<AccountTable> listFilter = listUser.stream()
                .filter(account -> account.getUsername().equals(searchUsername))
                .collect(Collectors.toList());
        return listFilter;
    }
    // Used to switch between scence
    @FXML
    private void sceneSwitcher(ActionEvent actionEvent) {
        Scene scene = null;
        Stage stage = null;
        Object source = actionEvent.getSource();
        stage = (Stage) ((Node)source).getScene().getWindow();
        if (source == backButton){
            scene = HomeScene.getInstance().getScene();
        }
        stage.setScene(scene);
        stage.show();
    }
}
