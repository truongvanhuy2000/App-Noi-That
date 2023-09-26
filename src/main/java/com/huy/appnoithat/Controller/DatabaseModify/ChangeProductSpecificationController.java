package com.huy.appnoithat.Controller.DatabaseModify;

import com.huy.appnoithat.Entity.ThongSo;
import com.huy.appnoithat.Scene.DatabaseModify.DatabaseModifyPhongCachScene;
import com.huy.appnoithat.Scene.DatabaseModify.DatabaseModifyVatLieuScene;
import com.huy.appnoithat.Service.DatabaseModifyService.DatabaseModifyThongSoService;
import com.huy.appnoithat.Shared.PopupUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ChangeProductSpecificationController {
    int parentID;
    private final DatabaseModifyThongSoService databaseModifyThongSoService;
    @FXML
    private Button backButton, btnOK;
    @FXML
    private TextField txtCao, txtDai, txtDonGia, txtDonVi, txtRong;

    public ChangeProductSpecificationController() {
        databaseModifyThongSoService = new DatabaseModifyThongSoService();
    }

    @FXML
    void clickOK(ActionEvent event) {
        //click to Edit thong so
        String regex = "[0-9].+";
        ThongSo thongSo = new ThongSo();
        thongSo.setId(parentID);
        if (!txtCao.getText().matches(regex) || !txtDai.getText().matches(regex) || !txtRong.getText().matches(regex) || !txtDonGia.getText().matches(regex)) {
            PopupUtils.throwErrorSignal("Please input is a number !!!");
        } else {
            thongSo.setCao(Float.valueOf(txtCao.getText()));
            thongSo.setDai(Float.valueOf(txtDai.getText()));
            thongSo.setRong(Float.valueOf(txtRong.getText()));
            thongSo.setDon_vi(txtDonVi.getText());
            thongSo.setDon_gia(Long.valueOf(txtDonGia.getText()));
            databaseModifyThongSoService.EditThongSo(thongSo);
            initializeThongSo(parentID);
        }

    }

    void backToMain() {
        Scene scene = DatabaseModifyPhongCachScene.getInstance().getScene();
        Stage stage = (Stage) btnOK.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void sceneSwitcher(ActionEvent actionEvent) {
        Scene scene = null;
        Stage stage = null;
        Object source = actionEvent.getSource();
        stage = (Stage) ((Node) source).getScene().getWindow();
        if (source == backButton) {
            scene = DatabaseModifyVatLieuScene.getInstance().getScene();
        } else {
            return;
        }
        stage.setScene(scene);
        stage.show();
    }

    public void initializeThongSo(int id) {
        parentID = id;
        if (id == 0) {
            return;
        }
        if (databaseModifyThongSoService.findThongSoByParentId(id) == null || databaseModifyThongSoService.findThongSoByParentId(id).isEmpty()) {
            return;
        }
        ThongSo ts = databaseModifyThongSoService.findThongSoByParentId(id).get(0);
        txtCao.setText(ts.getCao() != null ? ts.getCao().toString() : "0.0");
        txtDai.setText(ts.getDai() != null ? ts.getDai().toString() : "0.0");
        txtRong.setText(ts.getRong() != null ? ts.getRong().toString() : "0.0");
        txtDonGia.setText(ts.getDon_gia() != null ? ts.getDon_gia().toString() : " ");
        txtDonVi.setText(ts.getDon_vi() != null ? ts.getDon_vi() : " ");
    }

}
