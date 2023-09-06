package com.huy.appnoithat.Controller.DatabaseModify;

import com.huy.appnoithat.Entity.NoiThat;
import com.huy.appnoithat.Entity.ThongSo;
import com.huy.appnoithat.Scene.DatabaseModify.DatabaseModifyVatLieuScene;
import com.huy.appnoithat.Service.DatabaseModifyService.DatabaseModifyThongSoService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ChangeProductSpecificationController {

    List<ThongSo> thongSoList = new ArrayList<>();
    int parentID;

    DatabaseModifyThongSoService databaseModifyThongSoService = new DatabaseModifyThongSoService();
    @FXML
    private Button backButton;

    @FXML
    private Button btnOK;

    @FXML
    private TextField txtCao;

    @FXML
    private TextField txtDai;

    @FXML
    private TextField txtDonGia;

    @FXML
    private TextField txtDonVi;

    @FXML
    private TextField txtRong;

    @FXML
    void clickOK(ActionEvent event) {
        //click to Edit thong so

        String regex = "[0-9].+";
        Alert alert = new Alert(Alert.AlertType.ERROR);
        ThongSo thongSo = new ThongSo();
        thongSo.setId(parentID);
        System.out.println(txtCao.getText());
        System.out.println(txtCao.getText().matches(regex));
        if(!txtCao.getText().matches(regex) || !txtDai.getText().matches(regex) || !txtRong.getText().matches(regex) || !txtDonGia.getText().matches(regex)) {
            alert.setTitle("INPUT ERROR");
            alert.setHeaderText("look, a error to input");
            alert.setContentText("Please input is a number !!!");
            alert.showAndWait();
        }else {
            thongSo.setCao(Float.valueOf(txtCao.getText()));
            thongSo.setDai(Float.valueOf(txtDai.getText()));
            thongSo.setRong(Float.valueOf(txtRong.getText()));
            thongSo.setDon_vi(txtDonVi.getText());
            thongSo.setDon_gia(Long.valueOf(txtDonGia.getText()));
            databaseModifyThongSoService.EditThongSo(thongSo);
            initializeThongSo(parentID);
        }
    }
    @FXML
    private void sceneSwitcher(ActionEvent actionEvent) {
        Scene scene = null;
        Stage stage = null;
        Object source = actionEvent.getSource();
        stage = (Stage) ((Node)source).getScene().getWindow();
        if (source == backButton){
            scene = DatabaseModifyVatLieuScene.getInstance().getScene();
            thongSoList.clear();
        }
        else {
            return;
        }
        stage.setScene(scene);
        stage.show();
    }

    public void initializeThongSo(int id) {
        thongSoList.clear();
        parentID=id;
        thongSoList = databaseModifyThongSoService.findThongSoByID(id);
        for (ThongSo ts : thongSoList) {
            txtCao.setText(ts.getCao() !=null ? ts.getCao().toString() : " ");
            txtDai.setText(ts.getDai() !=null ? ts.getDai().toString() : " ");
            txtRong.setText(ts.getRong() !=null ? ts.getRong().toString() : " ");
            txtDonGia.setText(ts.getDon_gia() != null ? ts.getDon_gia().toString() : " ");
            txtDonVi.setText(ts.getDon_vi() != null ? ts.getDon_vi() : " ");
        }
    }

}
