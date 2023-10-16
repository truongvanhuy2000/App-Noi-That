package com.huy.appnoithat.Controller.DatabaseModify;

import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Entity.ThongSo;
import com.huy.appnoithat.Scene.DatabaseModify.DatabaseModifyVatLieuScene;
import com.huy.appnoithat.Service.DatabaseModifyService.DatabaseModifyThongSoService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import lombok.Setter;

import java.util.List;

public class ChangeProductSpecificationController {
    private int parentID;
    private final DatabaseModifyThongSoService databaseModifyThongSoService;
    @FXML
    private Button backButton, btnOK;
    @FXML
    private TextField txtCao, txtDai, txtDonGia, txtDonVi, txtRong;
    private ThongSo currentThongSoItem;
    @Setter
    private Parent root;
    public ChangeProductSpecificationController() {
        databaseModifyThongSoService = new DatabaseModifyThongSoService();
    }

    /**
     * Handles the action event triggered when the user clicks the OK button to edit "Thong So" properties.
     * Validates input values, updates the ThongSo object, and navigates back to the main view.
     *
     * @param event The ActionEvent triggered by clicking the OK button.
     */
    @FXML
    void clickOK(ActionEvent event) {
        // Validate input: Ensure all input fields contain numerical values
        String regex = "[0-9].+";
        if (!txtCao.getText().matches(regex) ||
                !txtDai.getText().matches(regex) ||
                !txtRong.getText().matches(regex) ||
                !txtDonGia.getText().matches(regex)) {
            PopupUtils.throwErrorSignal("Please input is a number !!!");
            return;
        }
        // Retrieve ThongSo objects from the database based on the parent ID
        List<ThongSo> thongSoList = databaseModifyThongSoService.findThongSoByParentId(this.parentID);

        // Check if the ThongSo list is empty or null, and exit if so
        if (thongSoList == null || thongSoList.isEmpty()) {
            return;
        }

        // Update the first ThongSo object with the values from the input fields
        ThongSo thongSo = thongSoList.get(0);
        thongSo.setCao(Double.valueOf(txtCao.getText()));
        thongSo.setDai(Double.valueOf(txtDai.getText()));
        thongSo.setRong(Double.valueOf(txtRong.getText()));
        thongSo.setDon_vi(txtDonVi.getText());
        thongSo.setDon_gia(Long.valueOf(txtDonGia.getText()));

        // Update the ThongSo object in the database
        databaseModifyThongSoService.EditThongSo(thongSo);

        // Navigate back to the main view
        backToMain();
    }

    /**
     * Navigates back to the main view of the Vat Lieu modification scene.
     * Clears the current view, loads the main content, and refreshes the displayed data.
     */
    void backToMain() {
        DatabaseModifyVatLieuScene databaseModifyVatLieuScene = new DatabaseModifyVatLieuScene();
        HBox hBox = (HBox) ((AnchorPane)databaseModifyVatLieuScene.getRoot()).getChildren().get(0);

        // Clear the current content and add the new HBox to the root AnchorPane
        ((AnchorPane)this.root).getChildren().clear();
        ((AnchorPane)this.root).getChildren().add(hBox);
        DatabaseModifyVatLieuScene.getController().refresh();
        // Set the root for the controller of the DatabaseModifyVatLieuScene
        DatabaseModifyVatLieuScene.getController().setRoot(this.root);
    }

    @FXML
    private void sceneSwitcher(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (source == backButton) {
            backToMain();
        }
    }

    /**
     * Initializes the ThongSo (specifications) based on the given ID.
     * If the ID is 0, no initialization is performed.
     * If ThongSo data for the given ID is not found, a new ThongSo instance is created and added to the database.
     * If ThongSo data is found, it populates the relevant fields in the UI with the retrieved data.
     *
     * @param id The ID of the parent entity.
     */
    public void initializeThongSo(int id) {
        // Set the parent ID
        this.parentID = id;
        if (id == 0) {
            return;
        }
        ThongSo thongSo = new ThongSo(0, 0.0, 0.0, 0.0, "", 0L);

        // Retrieve ThongSo data for the given parent ID
        List<ThongSo> thongSoList = databaseModifyThongSoService.findThongSoByParentId(this.parentID);
        if (thongSoList == null || thongSoList.isEmpty()) {
            databaseModifyThongSoService.addNewThongSo(thongSo, this.parentID);
        }
        else {
            // If ThongSo data is found, use the retrieved ThongSo instance
            thongSo = thongSoList.get(0);
        }

        // Populate UI fields with ThongSo data
        txtCao.setText(thongSo.getCao().toString());
        txtDai.setText(thongSo.getDai().toString());
        txtRong.setText(thongSo.getRong().toString());
        txtDonGia.setText(thongSo.getDon_gia().toString());
        txtDonVi.setText(thongSo.getDon_vi());
    }

}
