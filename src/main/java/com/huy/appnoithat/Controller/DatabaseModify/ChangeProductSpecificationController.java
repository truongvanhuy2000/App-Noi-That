package com.huy.appnoithat.Controller.DatabaseModify;

import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Entity.ThongSo;
import com.huy.appnoithat.Scene.DatabaseModify.DatabaseModifyPhongCachScene;
import com.huy.appnoithat.Scene.DatabaseModify.DatabaseModifyVatLieuScene;
import com.huy.appnoithat.Service.DatabaseModifyService.DatabaseModifyThongSoService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;

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
        this.parentID = id;
        if (id == 0) {
            return;
        }
        ThongSo thongSo = new ThongSo(0, 0.0f, 0.0f, 0.0f, "", 0L);
        List<ThongSo> thongSoList = databaseModifyThongSoService.findThongSoByParentId(id);
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
