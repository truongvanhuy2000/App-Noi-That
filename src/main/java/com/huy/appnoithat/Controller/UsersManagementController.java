package com.huy.appnoithat.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.huy.appnoithat.Entity.Account;
import com.huy.appnoithat.Entity.AccountInformation;
import com.huy.appnoithat.Scene.HomeScene;
import com.huy.appnoithat.Scene.ListAccountWaitToApproveScene;
import com.huy.appnoithat.Scene.UserManagementAddAccountScene;
import com.huy.appnoithat.Scene.UserManagementEditorScene;
import com.huy.appnoithat.Service.SessionService.UserSessionService;
import com.huy.appnoithat.Service.UsersManagement.UsersManagementService;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
        private boolean active;
        private ImageView activeImage;
    }
    @FXML
    private Button backButton;
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
    private TableColumn<AccountTable, String> password;

    @FXML
    private TableView<AccountTable> tableManageUser;

    @FXML
    private TextField txtSearchUser;

    @FXML
    private TableColumn<AccountTable, String> username;


    UsersManagementService user = new UsersManagementService();

    @Getter
    List<Account> list = user.findAllAccountEnable();
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
    );



    UsersManagementService usersManagementService = new UsersManagementService();

    UserSessionService userSessionService;
    public UsersManagementController() {
        userSessionService = new UserSessionService();
    }
    public void initialize() throws JsonProcessingException {
            // 2. convert JSON array to List of objects
            List<Account> accountList = usersManagementService.findAllAccountEnable();

            for (Account account: accountList
                 ) {
                listUser.add(new AccountTable(account.getId(),account.getUsername(), account.getPassword(),account.isActive(),convertActiveIcon(account.isActive())));
            }
            username.setCellValueFactory(new PropertyValueFactory<AccountTable,String>("username"));
            password.setCellValueFactory(new PropertyValueFactory<AccountTable,String>("password"));
            active.setCellValueFactory(new PropertyValueFactory<AccountTable,ImageView>("activeImage"));
            tableManageUser.setItems(listUser);
    }

    void clearData(){
        list.clear();
        tableManageUser.getItems().clear();
    }

    @FXML
    void getAllAcount(ActionEvent event) throws JsonProcessingException{
        listUser.clear();
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
        int indexSelector = tableManageUser.getSelectionModel().getSelectedIndex();
        int activeID = tableManageUser.getItems().get(indexSelector).getId();
        tableManageUser.getSelectionModel().getSelectedItem().setActiveImage(convertActiveIcon(true));
        usersManagementService.ActiveAccount(activeID);
        tableManageUser.refresh();

    }

    @FXML
    void InActiveAccount(ActionEvent event) {
        int indexSelector = tableManageUser.getSelectionModel().getSelectedIndex();
        int inactiveID = tableManageUser.getItems().get(indexSelector).getId();
        usersManagementService.InActiveAccount(inactiveID);
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
                try {
                listUser.add(new AccountTable(listUser.size(), txtusername.getText(), txtpassword.getText(), Boolean.parseBoolean(txtactive.getText()),convertActiveIcon(true)));
                tableManageUser.getItems().clear();
                tableManageUser.refresh();
                List<String> roleList = new ArrayList<>();
                roleList.add("ROLE_USER");
                // --- REMEMBER TO SUA DATE
                    // --- REMEMBER TO SUA SAU
                    // --- REMEMBER TO SUA SAU
                    // --- REMEMBER TO SUA SAU
                    // --- REMEMBER TO SUA SAU
                    // --- REMEMBER TO SUA SAU
                    // --- REMEMBER TO SUA SAU
                usersManagementService.addNewAccount(
                        new Account(0, txtusername.getText(), txtpassword.getText(), Boolean.parseBoolean(txtactive.getText()),new AccountInformation(),roleList,true, null));
                    // --- REMEMBER TO SUA SAU
                    // --- REMEMBER TO SUA SAU

                clearData();
                initialize();
                userManageMentStage.close();

                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
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
        int deleteid = tableManageUser.getItems().get(indexSelector).getId();
        usersManagementService.deleteAccount(deleteid);
        tableManageUser.getItems().remove(indexSelector);
    }

    @FXML
    void EditAccount(ActionEvent event) {
        try {
            int selectIndex = tableManageUser.getSelectionModel().getSelectedIndex();
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
                int id = tableManageUser.getItems().get(selectIndex).getId();
                Account account = usersManagementService.findAccountById(id);
                account.setUsername(txtusername.getText());
                account.setPassword(txtpassword.getText());
                usersManagementService.EditAccount(account);
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
        ObservableList<Account> listUserNotEnable = FXCollections.observableArrayList(
        );
        try {
            Stage userManageMentStage = new Stage();
            Scene listAccountWaitToApprove = ListAccountWaitToApproveScene.getInstance().getScene();

//element field of userManagementEditorScene
            TableView tableView = (TableView) listAccountWaitToApprove.lookup("#tableViewListAccount");
            Button btnApprove = (Button) listAccountWaitToApprove.lookup("#btnApprove");
            Button btnReject = (Button) listAccountWaitToApprove.lookup("#btnReject");
            Button btnCancel = (Button) listAccountWaitToApprove.lookup("#btnCancel");

            ObservableList<TableColumn<Account, ?>> columns = tableView.getColumns();
            TableColumn<Account, String> usernameColumn = null;
            TableColumn<Account, String> passwordColumn = null;
            TableColumn<Account, String> sttColumn = null;
            for (TableColumn<Account, ?> column : columns) {
                if (column.getId().equals("usernameColumn")) {
                    usernameColumn = (TableColumn<Account, String>) column;
                    // Now you can work with the usernameColumn

                }
                if (column.getId().equals("passwordColumn")) {
                    passwordColumn = (TableColumn<Account, String>) column;
                    // Now you can work with the usernameColumn
//                    break; // Assuming there's only one column with the given ID
                }
                if (column.getId().equals("sttColumn")) {
                    sttColumn = (TableColumn<Account, String>) column;
                    // Now you can work with the usernameColumn
//                    break; // Assuming there's only one column with the given ID
                }
            }

            List<Account> accountList = usersManagementService.findAllNotEnabledAccount();

            for (Account account : accountList
            ) {
                listUserNotEnable.add(account);
            }
            usernameColumn.setCellValueFactory(new PropertyValueFactory<Account, String>("username"));
            passwordColumn.setCellValueFactory(new PropertyValueFactory<Account,String>("password"));
            sttColumn.setCellValueFactory(new PropertyValueFactory<Account,String>("id"));
            tableView.setItems(listUserNotEnable);


//
            userManageMentStage.setScene(listAccountWaitToApprove);
            userManageMentStage.setTitle("LIST ACCOUNT TO APPROVE");
            userManageMentStage.show();


            btnApprove.setOnAction(actionEvent -> {
                int enableid = 0;
                Account acc =(Account)tableView.getSelectionModel().getSelectedItem();
                enableid = acc.getId();
                usersManagementService.enableAccount(enableid);
                userManageMentStage.close();
                // You might need additional logic to handle saving or updating data
            });


            //chua lam reject
            btnReject.setOnAction(actionEvent -> {

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
        else {
            return;
        }
        stage.setScene(scene);
        stage.show();
    }
}
