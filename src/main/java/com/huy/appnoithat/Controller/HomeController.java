package com.huy.appnoithat.Controller;

import com.huy.appnoithat.Scene.DatabaseModify.DatabaseModifyPhongCachScene;
import com.huy.appnoithat.Scene.LoginScene;
import com.huy.appnoithat.Scene.LuaChonNoiThat.FileNoiThatExplorerScene;
import com.huy.appnoithat.Scene.StageFactory;
import com.huy.appnoithat.Scene.UseManagement.UserManagementScene;
import com.huy.appnoithat.Service.SessionService.UserSessionService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HomeController {
    final static Logger LOGGER = LogManager.getLogger(HomeController.class);
    @FXML
    private Button LogoutButton;
    @FXML
    private Button LuaChonNoiThatButton;
    @FXML
    private Button QuanLyNguoiDungButton;
    @FXML
    private Button suadoidatabaseButton;
    @FXML
    private Text UserName;
    @FXML
    private AnchorPane PCPane;
    private final UserSessionService sessionService;

    public HomeController() {
        this.sessionService = new UserSessionService();
    }

    /**
     * Handles the logout action by cleaning the user session, closing the current window,
     * and switching to a different scene.
     *
     * @param event The ActionEvent triggering the logout.
     * Clean the user session
     * Close the current window
     * Switch to a different scene (assuming sceneSwitcher method exists)
     */
    @FXML
    void logout(ActionEvent event) {
        sessionService.cleanUserSession();
        LogoutButton.getScene().getWindow().hide();
        sceneSwitcher(event);
    }
    private void initController() {
    }

    /**
     * Initializes the user interface based on the logged-in user's role and displays relevant buttons and actions.
     *
     */
    public void init() {
        // Show the primary content pane and clear its children
        PCPane.setVisible(true);
        PCPane.getChildren().clear();

        // Disable all buttons by default
        toggleButton(false, false, false);

        // Set actions for different buttons
        QuanLyNguoiDungButton.setOnAction(this::OnClickQuanLyNguoiDung);
        suadoidatabaseButton.setOnAction(this::OnClickSuaDoiDatabase);
        LuaChonNoiThatButton.setOnAction(this::OnClickLuaChonNoiThat);

        // Get the username from the session service and display a welcome message
        String username = sessionService.getLoginAccount().getUsername();
        UserName.setText("Welcome " + username);

        // Determine user role and show appropriate buttons and actions
        String role = sessionService.getLoginAccount().getRoleList().contains("ROLE_ADMIN") ? "Admin" : "User";
        switch (role) {
            // Enable Admin-related buttons and trigger the QuanLyNguoiDungButton action
            case "Admin" -> {
                toggleButton(false, true, false);
                QuanLyNguoiDungButton.fire();
            }
            case "User" -> {
                // Enable User-related buttons and trigger the LuaChonNoiThatButton action
                toggleButton(true, false, true);
                LuaChonNoiThatButton.fire();
            }
            default -> {
            }
        }
        LOGGER.info("Login as " + username + " with role " + role);
    }

    /**
     * Handles the action when the user clicks on the "Lua Chon Noi That" button.
     * Clears the primary content pane, loads the furniture explorer scene, initializes it, and sets it as the root.
     *
     * @param actionEvent The ActionEvent triggering the button click.
     */
    private void OnClickLuaChonNoiThat(ActionEvent actionEvent) {
        // Clear the primary content pane
        PCPane.getChildren().clear();

        // Load the furniture explorer scene
        FileNoiThatExplorerScene fileNoiThatExplorerScene = new FileNoiThatExplorerScene();

        // Add the children of the furniture explorer scene to the primary content pane
        PCPane.getChildren().addAll(((AnchorPane)fileNoiThatExplorerScene.getRoot()).getChildren());
        FileNoiThatExplorerScene.getController().init();

        // Set the primary content pane as the root for the furniture explorer scene controller
        FileNoiThatExplorerScene.getController().setRoot(PCPane);
    }

    private void toggleButton(boolean luaChonNoiThatBtn, boolean quanLyNguoiDungBtn, boolean suadoidatabaseBtn) {
        LuaChonNoiThatButton.setDisable(!luaChonNoiThatBtn);
        QuanLyNguoiDungButton.setDisable(!quanLyNguoiDungBtn);
        suadoidatabaseButton.setDisable(!suadoidatabaseBtn);
    }

    /**
     * Handles scene switching based on the action event source.
     *
     * @param actionEvent The ActionEvent triggering the scene switch.
     */
    @FXML
    private void sceneSwitcher(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        Stage stage = (Stage) ((Node) source).getScene().getWindow();
        if (source == LogoutButton) {
            // Switch to the login scene if the Logout button is clicked
            Scene scene = LoginScene.getInstance().getScene();
            StageFactory.closeAndCreateNewUnresizeableStage(stage, scene);
        }
    }

    /**
     * Handles the action when the user clicks on the "Sua Doi Database" button.
     * Clears the primary content pane, loads the database modification scene,
     * extracts the required UI elements, adds them to the primary content pane, and initializes the scene controller.
     *
     * @param actionEvent The ActionEvent triggering the button click.
     */
    private void OnClickSuaDoiDatabase(ActionEvent actionEvent) {
        // Clear the primary content pane
        PCPane.getChildren().clear();

        // Load the database modification scene
        DatabaseModifyPhongCachScene databaseModifyPhongCachScene = new DatabaseModifyPhongCachScene();

        // Extract HBox from the scene and add it to the primary content pane
        HBox hBox = (HBox) ((AnchorPane)databaseModifyPhongCachScene.getRoot()).getChildren().get(0);
        PCPane.getChildren().addAll(hBox);

        // Initialize the database modification scene controller
        DatabaseModifyPhongCachScene.getController().init();
        DatabaseModifyPhongCachScene.getController().setRoot(PCPane);
    }

    /**
     * Handles the action when the user clicks on the "Quan Ly Nguoi Dung" button.
     * Clears the primary content pane, loads the user management scene,
     * extracts the required UI elements, adds them to the primary content pane, and initializes the scene controller.
     *
     * @param actionEvent The ActionEvent triggering the button click.
     */
    private void OnClickQuanLyNguoiDung(ActionEvent actionEvent) {
        // Clear the primary content pane
        PCPane.getChildren().clear();

        // Load the user management scene
        UserManagementScene userManagementScene = new UserManagementScene();

        // Extract VBox from the scene and add it to the primary content pane
        VBox vBox = (VBox) ((AnchorPane)userManagementScene.getRoot()).getChildren().get(0);

        // Initialize the user management scene controller
        PCPane.getChildren().addAll(vBox);
        UserManagementScene.getController().init();
    }
}
