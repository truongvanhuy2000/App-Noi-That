package com.huy.appnoithat.Controller.UserManagement;

import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Controller.UserManagement.DataModel.AccountTable;
import com.huy.appnoithat.Entity.Account;
import com.huy.appnoithat.Entity.AccountInformation;
import com.huy.appnoithat.HelloApplication;
import com.huy.appnoithat.Scene.HomeScene;
import com.huy.appnoithat.Scene.StageFactory;
import com.huy.appnoithat.Scene.UseManagement.ListAccountWaitToApproveScene;
import com.huy.appnoithat.Scene.UseManagement.UserManagementAddAccountScene;
import com.huy.appnoithat.Scene.UseManagement.UserManagementEditorScene;
import com.huy.appnoithat.Service.SessionService.UserSessionService;
import com.huy.appnoithat.Service.UsersManagement.UsersManagementService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UsersManagementController {
    final static Logger LOGGER = LogManager.getLogger(UsersManagementController.class);
    @FXML
    private TableColumn<AccountTable, ImageView> active;
    @FXML
    private Button btnActiveAccount, btnAddacount, btnAllAccount, btnInactiveAccount, btnDeleteAccount, btnEditAccount, btnSearch, backButton;
    @FXML
    private TableColumn<AccountTable, String> password;
    @FXML
    private TableColumn<AccountTable, LocalDate> expiredDate;
    @FXML
    private TableView<AccountTable> tableManageUser;
    @FXML
    private TextField txtSearchUser;
    @FXML
    private TableColumn<AccountTable, String> username;

    @FXML
    private TableColumn<AccountTable, String> email;

    @FXML
    private TableColumn<AccountTable, String> phone;

    UsersManagementService user = new UsersManagementService();

    @Getter
    List<Account> list = user.findAllAccountEnable();

    /**
     * Converts a boolean value into an active icon.
     * Returns an ImageView representing a check mark if checked is true,
     * and a cancel icon if checked is false.
     *
     * @param checked A boolean value indicating whether the icon should be active or not.
     * @return An ImageView representing the active or inactive state based on the checked parameter.
     */
    public ImageView convertActiveIcon(boolean checked) {
        ImageView activeIcon;
        if (checked) {

            // Load a check mark icon if checked is true
            activeIcon = new ImageView(new Image(this.getClass().getResourceAsStream("/com/huy/appnoithat/Scene/icons/check-mark.png")));
        } else {

            // Load a cancel icon if checked is false
            activeIcon = new ImageView(new Image(this.getClass().getResourceAsStream("/com/huy/appnoithat/Scene/icons/cancel.png")));
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


    /**
     * Initializes the user management table with data from the JSON array.
     * Retrieves a list of enabled accounts, converts them into a list of AccountTable objects,
     * and populates the TableView with the formatted data.
     */
    public void init(){
        // Retrieve a list of enabled accounts from the service
        List<Account> accountList = usersManagementService.findAllAccountEnable();

        // Clear the existing data in the list
        listUser.clear();

        // Convert Account objects to AccountTable objects and add them to the list
        for (Account account : accountList) {
            listUser.add(new AccountTable(account.getId(), account.getUsername(),account.getPassword(),account.getAccountInformation().getPhone(),account.getAccountInformation().getEmail(), account.isActive(), convertActiveIcon(account.isActive()), account.getExpiredDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
        }

        // Set up cell value factories for each column in the TableView
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        active.setCellValueFactory(new PropertyValueFactory<>("activeImage"));
        expiredDate.setCellValueFactory(new PropertyValueFactory<>("expiredDate"));

        // Populate the TableView with the list of AccountTable objects
        tableManageUser.setItems(listUser);
    }


    /**
     * Clears the data in the list and the items in the TableView.
     * This method is used to clear the existing data in both the data list and the displayed table.
     */
    void clearData() {
        // Clear the data list
        list.clear();

        // Clear the items in the TableView
        tableManageUser.getItems().clear();
    }


    /**
     * Handles the action to retrieve all accounts.
     * Clears the existing list of users and initializes the user management table with all accounts.
     *
     * @param event The ActionEvent triggering the action to retrieve all accounts.
     */
    @FXML
    void getAllAcount(ActionEvent event){
        listUser.clear();
        init();
    }

    /**
     * Handles the action to search for accounts by username.
     * Retrieves the search query from the text field, performs the search,
     * and updates the user management table with the filtered results.
     *
     * @param event The ActionEvent triggering the search action.
     */

    @FXML
    void SearchByUserName(ActionEvent event) {
        // Get the search query from the text field
        String username = txtSearchUser.getText();

        // Perform the search and get the filtered list of AccountTable objects
        List<AccountTable> listFilter = seach(username);

        // Convert the filtered list to an ObservableList
        ObservableList<AccountTable> listUser = FXCollections.observableArrayList(listFilter);
        tableManageUser.setItems(listUser);

    }

    /**
     * Handles the action to activate a selected user account.
     * Checks if a user is selected, activates the user in the service,
     * updates the active status in the table, and refreshes the table view.
     *
     * @param event The ActionEvent triggering the activation action.
     */
    @FXML
    void ActiveAccount(ActionEvent event) {

        // Check if a user is selected in the table
        if (tableManageUser.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        // Get the index and ID of the selected user
        int indexSelector = tableManageUser.getSelectionModel().getSelectedIndex();
        int activeID = tableManageUser.getItems().get(indexSelector).getId();

        // Set the active status in the selected user's row in the table

        tableManageUser.getSelectionModel().getSelectedItem().setActiveImage(convertActiveIcon(true));

        // Activate the selected user account using the service

        usersManagementService.ActiveAccount(activeID);

        // Refresh the table view to update the changes

        tableManageUser.refresh();

    }


    /**
     * Handles the action to deactivate a selected user account.
     * Checks if a user is selected, deactivates the user in the service,
     * updates the inactive status in the table, and refreshes the table view.
     *
     * @param event The ActionEvent triggering the deactivation action.
     */
    @FXML
    void InActiveAccount(ActionEvent event) {

        // Check if a user is selected in the table
        if (tableManageUser.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        // Get the index and ID of the selected user
        int indexSelector = tableManageUser.getSelectionModel().getSelectedIndex();
        int inactiveID = tableManageUser.getItems().get(indexSelector).getId();

        // Deactivate the selected user account using the service
        usersManagementService.InActiveAccount(inactiveID);

        // Set the inactive status in the selected user's row in the table
        tableManageUser.getSelectionModel().getSelectedItem().setActiveImage(convertActiveIcon(false));

        // Refresh the table view to update the changes
        tableManageUser.refresh();
    }


    /**
     * Handles the action to add a new user account.
     * Displays a form for adding a new account, takes user input, adds the account to the system,
     * updates the user management table, and closes the form upon completion.
     *
     * @param event The ActionEvent triggering the add account action.
     */
    @FXML
    void AddAccount(ActionEvent event) {
        try {
            // Retrieve the scene for adding a new account
            Scene userManagementAddAccountScene = UserManagementAddAccountScene.getInstance().getScene();

            // Create a new stage for the add account form
            Stage userManageMentStage = StageFactory.CreateNewUnresizeableStage(userManagementAddAccountScene);

            // Populate the active status ComboBox
            ObservableList<String> listActive = FXCollections.observableArrayList("Có", "Không");

            // Get UI elements from the scene
            TextField txtusername = (TextField) userManagementAddAccountScene.lookup("#txtaddusername");
            TextField txtpassword = (TextField) userManagementAddAccountScene.lookup("#txtaddpassword");
            ComboBox comboBoxActive = (ComboBox) userManagementAddAccountScene.lookup("#txtaddactive");
            Button btnadd = (Button) userManagementAddAccountScene.lookup("#btnadd");
            Button btncancel = (Button) userManagementAddAccountScene.lookup("#btncancel");
            comboBoxActive.setItems(listActive);


            // Handle the add button click event
            btnadd.setOnAction(actionEvent -> {
                String active = "false";
                LocalDate localDate = LocalDate.now().plusDays(30);
                if(comboBoxActive.getSelectionModel().getSelectedItem()!=null){
                    active = comboBoxActive.getSelectionModel().getSelectedItem().toString().equals("Có") ? "true" : "false";
                }

                // Add the new account to the list and update the table
                listUser.add(new AccountTable(listUser.size(), txtusername.getText(),txtpassword.getText(),"","", Boolean.parseBoolean(active), convertActiveIcon(true), localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
                tableManageUser.getItems().clear();
                tableManageUser.refresh();

                // Add the new account to the service
                List<String> roleList = new ArrayList<>();
                roleList.add("ROLE_USER");
                // --- REMEMBER TO SUA DATE
                usersManagementService.addNewAccount(
                        new Account(0, txtusername.getText(), txtpassword.getText(), Boolean.parseBoolean(active), new AccountInformation(), roleList, true, localDate));

                // Clear data, reinitialize the table, and close the form
                clearData();
                init();
                userManageMentStage.close();
                txtusername.clear();
                txtpassword.clear();
            });

            btncancel.setOnAction(actionEvent -> {
                userManageMentStage.close();
            });
        } catch (Exception e) {
            LOGGER.error("Error when add new account", e);
            throw new RuntimeException(e);
        }

    }


    /**
     * Handles the action to delete a selected user account.
     * Checks if a user is selected, retrieves the user ID, deletes the user account from the service,
     * updates the table, and displays an error message if trying to delete an admin account.
     *
     * @param event The ActionEvent triggering the delete account action.
     */
    @FXML
    void DeleteAccount(ActionEvent event) {
        // Check if a user is selected in the table
        if (tableManageUser.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        // Get the index and ID of the selected user
        int indexSelector = tableManageUser.getSelectionModel().getSelectedIndex();
        int deleteid = tableManageUser.getItems().get(indexSelector).getId();

        // Retrieve the account information from the service
        Account account = usersManagementService.findAccountById(deleteid);
        System.out.println(account.getRoleList().get(0));

        // Check if the selected account is not an admin account
        if (!account.getRoleList().get(0).equals("ROLE_ADMIN")) {
            // Delete the user account from the service
            usersManagementService.deleteAccount(deleteid);

            // Remove the user account from the table
            tableManageUser.getItems().remove(indexSelector);
        } else {
            // Display an error message if trying to delete an admin account
            PopupUtils.throwErrorSignal("Không thể xóa tài khoản admin");
        }
    }


    /**
     * Parses a string to an integer.
     *
     * @param data The string to be parsed.
     * @param def The default integer value to be returned if parsing fails.
     * @return The parsed integer value if parsing is successful, or the default value if parsing fails.
     */
    public Integer parseStringToINT(String data,int def) {
        Integer val = def;
        try {
            val = Integer.parseInt(data);
        } catch (NumberFormatException nfe) { }
        return val;
    }


    /**
     * Handles the action to edit a selected user account.
     * Displays a form with the selected account details, allows the user to edit the details,
     * updates the account in the system, updates the table, and closes the form upon completion.
     *
     * @param event The ActionEvent triggering the edit account action.
     */
    @FXML
    void EditAccount(ActionEvent event) {
        try {
            // Check if a user is selected in the table
            if (tableManageUser.getSelectionModel().getSelectedItem() == null) {
                return;
            }
            // Get the selected user's index in the table
            int selectIndex = tableManageUser.getSelectionModel().getSelectedIndex();
            Stage userManageMentStage;
            Scene userManagementEditorScene = UserManagementEditorScene.getInstance().getScene();

            //element field of AccountTable
            String username = tableManageUser.getSelectionModel().getSelectedItem().getUsername();
            String password = tableManageUser.getSelectionModel().getSelectedItem().getPassword();

            //element field of userManagementEditorScene
            TextField txtusername = (TextField) userManagementEditorScene.lookup("#txteditusername");
            TextField txtpassword = (TextField) userManagementEditorScene.lookup("#txteditpassword");
            TextField txtGiaHan = (TextField) userManagementEditorScene.lookup("#txtGiaHan");

            Button btnedit = (Button) userManagementEditorScene.lookup("#btnedit");
            Button btncancel = (Button) userManagementEditorScene.lookup("#btncancel");

            // Create an AccountTable object
            AccountTable exampleAccount = new AccountTable();
            exampleAccount.setUsername(username);
            exampleAccount.setPassword(password);

            // Set the object's attributes to the text fields
            txtusername.setText(exampleAccount.getUsername());
            txtpassword.setText(exampleAccount.getPassword());

            userManageMentStage = StageFactory.CreateNewUnresizeableStage(userManagementEditorScene);

            // Handle the edit button click event
            btnedit.setOnAction(actionEvent -> {
                tableManageUser.getSelectionModel().getSelectedItem().setUsername(txtusername.getText());
                tableManageUser.getSelectionModel().getSelectedItem().setPassword(txtpassword.getText());

                // Get the selected account's ID and details from the table
                int id = tableManageUser.getItems().get(selectIndex).getId();
                Account account = usersManagementService.findAccountById(id);

                // Extend the account's expiration date by the specified number of months
                LocalDate soThangGiaHanThem = account.getExpiredDate().plusMonths(parseStringToINT(txtGiaHan.getText(),0));

                // Update the account details
                account.setUsername(txtusername.getText());
                account.setPassword(txtpassword.getText());
                account.setExpiredDate(soThangGiaHanThem);

                // Update the account in the service
                usersManagementService.EditAccount(account);

                // Refresh the table view to update the changes
                tableManageUser.refresh();

                // Close the edit account form
                userManageMentStage.close();

                // Clear the form fields
                txtusername.setText("");
                txtpassword.clear();
                // You might need additional logic to handle saving or updating data
                init();
            });

            btncancel.setOnAction(actionEvent -> {
                userManageMentStage.close();
            });
        } catch (Exception e) {
            LOGGER.error("Error when EDIT account", e);
            throw new RuntimeException(e);
        }
    }


    /**
     * Handles the mouse click event on the user management table to select an item.
     * Enables or disables delete and inactive buttons based on the selected item's role.
     *
     * @param event The MouseEvent triggering the item selection.
     */
    @FXML
    void tableClickToSelectItem(MouseEvent event) {
        try {
            // Check if a user is selected in the table
            if (tableManageUser.getSelectionModel().getSelectedItem() != null) {
                // Get the index and ID of the selected user
                int indexSelector = tableManageUser.getSelectionModel().getSelectedIndex();
                int deleteid = tableManageUser.getItems().get(indexSelector).getId();

                // Retrieve the account information from the service
                Account account = usersManagementService.findAccountById(deleteid);

                // Check if the selected account is an admin
                if (account.getRoleList().get(0).equals("ROLE_ADMIN")) {
                    // Disable delete and inactive buttons for admin accounts
                    btnDeleteAccount.setDisable(true);
                    btnInactiveAccount.setDisable(true);
                } else {
                    // Enable delete and inactive buttons for regular user accounts
                    btnDeleteAccount.setDisable(false);
                    btnInactiveAccount.setDisable(false);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error when click selected item", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Handles the action to approve or reject user accounts awaiting approval.
     * Displays a list of accounts awaiting approval, allows the user to approve or reject accounts,
     * updates the account status in the system, updates the table, and closes the form upon completion.
     *
     * @param event The ActionEvent triggering the approve/reject action.
     */
    @FXML
    void DuyetAccount(ActionEvent event) {
        ObservableList<Account> listUserNotEnable = FXCollections.observableArrayList(
        );
        try {
            // Create a new stage for the list of accounts awaiting approval form
            Scene listAccountWaitToApprove = ListAccountWaitToApproveScene.getInstance().getScene();
            Stage userManageMentStage = StageFactory.CreateNewUnresizeableStage(listAccountWaitToApprove);

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
                    // Assuming there's only one column with the given ID
                }
                if (column.getId().equals("sttColumn")) {
                    sttColumn = (TableColumn<Account, String>) column;
                    // Now you can work with the usernameColumn
                    // Assuming there's only one column with the given ID
                }
            }

            // Retrieve accounts awaiting approval from the service
            List<Account> accountList = usersManagementService.findAllNotEnabledAccount();

            for (Account account : accountList
            ) {
                listUserNotEnable.add(account);
            }

            // Set cell value factories for the columns
            usernameColumn.setCellValueFactory(new PropertyValueFactory<Account, String>("username"));
            passwordColumn.setCellValueFactory(new PropertyValueFactory<Account, String>("password"));
            sttColumn.setCellValueFactory(new PropertyValueFactory<Account, String>("id"));
            tableView.setItems(listUserNotEnable);


            // Handle the approve button click event
            btnApprove.setOnAction(actionEvent -> {
                try {
                    // Get the selected account for approval
                    if (tableView.getSelectionModel().getSelectedItem() != null) {
                        int enableid = 0;
                        Account acc = (Account) tableView.getSelectionModel().getSelectedItem();
                        enableid = acc.getId();

                        // Update the table view
                        usersManagementService.enableAccount(enableid);
                        listUser.clear();
                        init();

                        // Close the form
                        userManageMentStage.close();
                    }
                } catch (Exception e) {
                    LOGGER.error("Error when duyet account", e);
                    throw new RuntimeException(e);
                }

            });
            btnReject.setOnAction(actionEvent -> {
                try {
                    // Get the selected account for rejection
                    if (tableView.getSelectionModel().getSelectedItem() != null) {
                        int rejectid = 0;
                        Account acc = (Account) tableView.getSelectionModel().getSelectedItem();
                        rejectid = acc.getId();

                        // Delete the selected account
                        usersManagementService.deleteAccount(rejectid);

                        // Close the form
                        userManageMentStage.close();
                    }
                } catch (Exception e) {
                    LOGGER.error("Error when duyet account", e);
                    throw new RuntimeException(e);
                }
            });

            btnCancel.setOnAction(actionEvent -> {
                userManageMentStage.close();
            });
        } catch (Exception e) {
            LOGGER.error("Error when duyet account", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Searches for AccountTable objects in the listUser based on the given searchUsername.
     *
     * @param searchUsername The username to search for.
     * @return A list of matched AccountTable objects.
     */
    public List<AccountTable> seach(String searchUsername) {
        // Filter the listUser based on the searchUsername
        List<AccountTable> listFilter = listUser.stream()
                .filter(account -> account.getUsername().equals(searchUsername))
                .collect(Collectors.toList());

        // Return the filtered list of AccountTable objects
        return listFilter;
    }

    // Used to switch between scence
    @FXML
    private void sceneSwitcher(ActionEvent actionEvent) {
    }
}
