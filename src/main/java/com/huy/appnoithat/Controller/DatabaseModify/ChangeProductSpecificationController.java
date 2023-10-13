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

    @FXML
    void clickOK(ActionEvent event) {
        //click to Edit thong so
        String regex = "[0-9].+";
        if (!txtCao.getText().matches(regex) ||
                !txtDai.getText().matches(regex) ||
                !txtRong.getText().matches(regex) ||
                !txtDonGia.getText().matches(regex)) {
            PopupUtils.throwErrorSignal("Please input is a number !!!");
            return;
        }
        List<ThongSo> thongSoList = databaseModifyThongSoService.findThongSoByParentId(this.parentID);
        if (thongSoList == null || thongSoList.isEmpty()) {
            return;
        }
        ThongSo thongSo = thongSoList.get(0);
        thongSo.setCao(Double.valueOf(txtCao.getText()));
        thongSo.setDai(Double.valueOf(txtDai.getText()));
        thongSo.setRong(Double.valueOf(txtRong.getText()));
        thongSo.setDon_vi(txtDonVi.getText());
        thongSo.setDon_gia(Long.valueOf(txtDonGia.getText()));
        databaseModifyThongSoService.EditThongSo(thongSo);
        backToMain();
    }

    void backToMain() {
        DatabaseModifyVatLieuScene databaseModifyVatLieuScene = new DatabaseModifyVatLieuScene();
        HBox hBox = (HBox) ((AnchorPane)databaseModifyVatLieuScene.getRoot()).getChildren().get(0);
        ((AnchorPane)this.root).getChildren().clear();
        ((AnchorPane)this.root).getChildren().add(hBox);
        DatabaseModifyVatLieuScene.getController().refresh();
        DatabaseModifyVatLieuScene.getController().setRoot(this.root);
    }

    @FXML
    private void sceneSwitcher(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (source == backButton) {
            backToMain();
        }
    }

    public void initializeThongSo(int id) {
        this.parentID = id;
        if (id == 0) {
            return;
        }
        ThongSo thongSo = new ThongSo(0, 0.0, 0.0, 0.0, "", 0L);
        List<ThongSo> thongSoList = databaseModifyThongSoService.findThongSoByParentId(this.parentID);
        if (thongSoList == null || thongSoList.isEmpty()) {
            databaseModifyThongSoService.addNewThongSo(thongSo, this.parentID);
        }
        else {
            thongSo = thongSoList.get(0);
        }
        txtCao.setText(thongSo.getCao().toString());
        txtDai.setText(thongSo.getDai().toString());
        txtRong.setText(thongSo.getRong().toString());
        txtDonGia.setText(thongSo.getDon_gia().toString());
        txtDonVi.setText(thongSo.getDon_vi());
    }

}
