package com.huy.appnoithat.Controller.DatabaseModify;


import com.huy.appnoithat.Common.KeyboardUtils;
import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Controller.DatabaseModify.Cell.CustomEditingListCell;
import com.huy.appnoithat.Controller.DatabaseModify.Common.DBModifyUtils;
import com.huy.appnoithat.Entity.NoiThat;
import com.huy.appnoithat.Entity.PhongCachNoiThat;
import com.huy.appnoithat.Enums.Action;
import com.huy.appnoithat.Scene.DatabaseModify.DatabaseModifyNoiThatScene;
import com.huy.appnoithat.Scene.HomeScene;
import com.huy.appnoithat.Service.DatabaseModifyService.DatabaseModifyNoiThatService;
import com.huy.appnoithat.Service.DatabaseModifyService.DatabaseModifyPhongCachService;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import lombok.Setter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DatabaseModifyPhongCachController implements Initializable {
    @FXML
    private Label Title;
    @FXML
    private Button addButton, deleteButton, backButton, nextButton;
    @FXML
    private ListView<NoiThat> childrenList;
    @FXML
    private ListView<PhongCachNoiThat> listView;
    @FXML
    private Button getSampleDataButton;
    @FXML
    private Button swapButton;

    private final DatabaseModifyPhongCachService databaseModifyPhongCachService;
    private final DatabaseModifyNoiThatService databaseModifyNoiThatService;
    private final ObservableList<PhongCachNoiThat> phongCachNoiThatObservableList;
    private final ObservableList<NoiThat> noiThatObservableList;
    @Setter
    private Parent root;

    /**
     * Constructor for the DatabaseModifyPhongCachController class.
     * Initializes the services and observable lists used in the controller.
     */
    public DatabaseModifyPhongCachController() {
        databaseModifyPhongCachService = new DatabaseModifyPhongCachService();
        databaseModifyNoiThatService = new DatabaseModifyNoiThatService();

        // Initialize observable lists for phong cach noi that and noi that entities
        phongCachNoiThatObservableList = FXCollections.observableArrayList();
        noiThatObservableList = FXCollections.observableArrayList();
    }


    /**
     * Handles the action event for adding a new PhongCachNoiThat entity.
     * Adds a new PhongCachNoiThat entity to the list and updates the database.
     * Refreshes the list view.
     * Called when the corresponding button is clicked.
     *
     * @param event The action event triggered by the user.
     */
    @FXML
    void addAction(ActionEvent event) {
        int currentPos = phongCachNoiThatObservableList.size();
        databaseModifyPhongCachService.addNewPhongCach(new PhongCachNoiThat(0, DBModifyUtils.getNewName(currentPos), new ArrayList<>()));
        refreshList();
    }

    @FXML
    void swap(ActionEvent event) {
        PhongCachNoiThat phongCachNoiThat = listView.getSelectionModel().getSelectedItem();
        if (phongCachNoiThat == null) {
            return;
        }
        String swapIndex = PopupUtils.openDialog("Đổi vị trí", "Đổi vị trí", "Vị trí");
        if (swapIndex == null || swapIndex.isEmpty()) {
            return;
        }
        int index;
        try {
            index = Integer.parseInt(swapIndex);
            if (index == 0 || index > phongCachNoiThatObservableList.size()) {
                return;
            }
        } catch (NumberFormatException e) {
            return;
        }
        databaseModifyPhongCachService.swap(phongCachNoiThat.getId(), phongCachNoiThatObservableList.get(index - 1).getId());
        refreshList();
//        listView.getSelectionModel().select(index - 1);
    }

    /**
     * Handles the action event for deleting a PhongCachNoiThat entity.
     * Deletes the selected PhongCachNoiThat entity from the list and the database.
     * Refreshes the list view and the children list.
     * Called when the corresponding button is clicked.
     *
     * @param event The action event triggered by the user.
     */
    @FXML
    void deleteAction(ActionEvent event) {
        PhongCachNoiThat phongCachNoiThat = listView.getSelectionModel().getSelectedItem();
        if (phongCachNoiThat == null) {
            return;
        }
        if (phongCachNoiThat.getId() == 0) {
            phongCachNoiThatObservableList.remove(phongCachNoiThat);
            return;
        }
        databaseModifyPhongCachService.deletePhongCach(phongCachNoiThat.getId());
        refreshList();
        refreshChildrenList(0);
    }

    /**
     * Handles the action event for moving to the next step.
     * Checks if an item is selected in the list view. If not, does nothing.
     * If an item is selected, loads the next scene related to modifying NoiThat entities.
     * Initializes the scene with the selected item's ID, sets it as the root, and clears the selection in the list view.
     * Called when the corresponding button is clicked.
     *
     * @param event The action event triggered by the user.
     */
    @FXML
    void nextAction(ActionEvent event) {
        if (listView.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        int selectID = listView.getSelectionModel().getSelectedItem().getId();
        DatabaseModifyNoiThatScene databaseModifyNoiThatScene = new DatabaseModifyNoiThatScene();
        HBox hBox = (HBox) ((AnchorPane)databaseModifyNoiThatScene.getRoot()).getChildren().get(0);

        ((AnchorPane)this.root).getChildren().clear();
        ((AnchorPane)this.root).getChildren().add(hBox);
        DatabaseModifyNoiThatScene.getController().init(selectID);
        DatabaseModifyNoiThatScene.getController().setRoot(this.root);
    }

    /**
     * Initializes the controller by refreshing the list and the children list.
     */
    public void init() {
        refreshList();
        refreshChildrenList(0);
    }

    /**
     * Refreshes both the list view and the children list.
     */
    public void refresh() {
        refreshList();
        refreshChildrenList(0);
        listView.getSelectionModel().clearSelection();
    }

    /**
     * Handles scene switching based on the action event.
     * If the source of the event is the backButton, switches to the HomeScene,
     * clears the items in the listView, and updates the stage's scene accordingly.
     * Called when the backButton or other specific buttons are clicked.
     *
     * @param actionEvent The action event triggered by the user.
     */
    @FXML
    private void sceneSwitcher(ActionEvent actionEvent) {
        Scene scene = null;
        Stage stage = null;
        Object source = actionEvent.getSource();
        stage = (Stage) ((Node) source).getScene().getWindow();
        if (source == backButton) {
            scene = HomeScene.getInstance().getScene();
            listView.getItems().clear();
        } else {
            return;
        }
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void onKeyPressed(KeyEvent event) {
        if (KeyboardUtils.isRightKeyCombo(Action.ADD_NEW_ROW, event)) {
            addButton.fire();
        }
        else if (KeyboardUtils.isRightKeyCombo(Action.DELETE, event)) {
            deleteButton.fire();
        }
        else if (KeyboardUtils.isRightKeyCombo(Action.NEXT_SCREEN, event)) {
            nextButton.fire();
        }
    }
    @FXML
    void FetchSampleData(ActionEvent event) {
        databaseModifyPhongCachService.fetchSamplePhongCachData();
        refresh();
    }


    /**
     * Initializes the controller after the FXML has been loaded.
     * Configures the UI elements, sets up event listeners, and populates initial data.
     *
     * @param url The location used to resolve relative paths for root object or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getSampleDataButton.disableProperty().bind(Bindings.size(phongCachNoiThatObservableList).greaterThan(0));
        // Hides the backButton initially
        backButton.setVisible(false);

        // Sets the title of the scene
        Title.setText("Danh sách phong cách");

        // Configures the listView for displaying phongCachNoiThatObservableList
        listView.setItems(phongCachNoiThatObservableList);
        listView.setEditable(true);
        listView.setCellFactory(param -> new CustomEditingListCell<>());

        // Handles editing events in the listView
        listView.setOnEditCommit(event -> {
            PhongCachNoiThat item = event.getNewValue();
            item.setName(DBModifyUtils.getNotDuplicateName(item.getName(), phongCachNoiThatObservableList));
            if (item.getId() == 0) {
                databaseModifyPhongCachService.addNewPhongCach(item);
            } else {
                databaseModifyPhongCachService.EditPhongCach(item);
            }
            refreshList();
        });

        // Listens for changes in the selected item of the listView
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                PhongCachNoiThat phongCachNoiThat = listView.getSelectionModel().getSelectedItem();
                if (phongCachNoiThat == null) {
                    return;
                }
                refreshChildrenList(phongCachNoiThat.getId());
            }
        });

        // Configures the childrenList for displaying noiThatObservableList
        childrenList.setEditable(false);
        childrenList.setCellFactory(param -> new CustomEditingListCell<>());
        childrenList.setItems(noiThatObservableList);
        init();
    }

    /**
     * Refreshes the phongCachNoiThatObservableList with the latest data from the database.
     */
    private void refreshList() {
        List<PhongCachNoiThat> phongCachNoiThatList = databaseModifyPhongCachService.findAllPhongCach();
        if (phongCachNoiThatList == null) {
            phongCachNoiThatList = new ArrayList<>();
        }
        phongCachNoiThatObservableList.clear();
        phongCachNoiThatObservableList.addAll(phongCachNoiThatList);
//        listView.getSelectionModel().select(0);
    }

    /**
     * Refreshes the childrenList with the NoiThat objects associated with the given parent ID from the database.
     *
     * @param parentID The ID of the parent item.
     */
    private void refreshChildrenList(int parentID) {
        List<NoiThat> noiThatList = databaseModifyNoiThatService.findNoiThatListByParentID(parentID);
        if (noiThatList == null) {
            noiThatList = new ArrayList<>();
        }
        noiThatObservableList.clear();
        noiThatObservableList.addAll(noiThatList);
    }
}
