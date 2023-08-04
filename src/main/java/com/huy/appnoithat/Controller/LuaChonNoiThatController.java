package com.huy.appnoithat.Controller;

import com.huy.appnoithat.Scene.HomeScene;
import com.huy.appnoithat.Service.LuaChonNoiThat.LuaChonNoiThatService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import lombok.Data;
@Data
class BangNoiThat {
    private int id;
    private int cao;
    private int dai;
    private int rong;
    private int donGia;
    private String donVi;
    private String hangMuc;
    private String noiThat;
    private String phongCach;
    private String vatLieu;
    private int thanhTien;
}
public class LuaChonNoiThatController {
    @FXML
    private Button BackButton;
    @FXML
    private TableView<?> TableNoiThat;
    private TableColumn<?, ?> PhongCach;
    @FXML
    private TableColumn<?, ?> Cao, Dai, Rong;
    @FXML
    private TableColumn<?, ?> DonGia;
    @FXML
    private TableColumn<?, ?> DonVi;
    @FXML
    private TableColumn<?, ?> HangMuc;
    @FXML
    private TableColumn<?, ?> ThanhTien;
    @FXML
    private TableColumn<?, ?> VatLieu;
    @FXML
    private TableColumn<?, ?> id;

    LuaChonNoiThatService luaChonNoiThatService;
    public LuaChonNoiThatController() {
        luaChonNoiThatService = new LuaChonNoiThatService();
    }
    public void initialize() {
    }

    @FXML
    private void sceneSwitcher(ActionEvent actionEvent) {
        Scene scene = null;
        Stage stage = null;
        Object source = actionEvent.getSource();
        stage = (Stage) ((Node)source).getScene().getWindow();
        if (source == BackButton){
            scene = HomeScene.getInstance().getScene();
        }
        else {
            return;
        }
        stage.setScene(scene);
        stage.show();
    }
}
