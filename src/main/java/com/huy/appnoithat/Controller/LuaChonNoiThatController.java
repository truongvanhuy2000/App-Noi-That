package com.huy.appnoithat.Controller;

import com.huy.appnoithat.Entity.BangNoiThat;
import com.huy.appnoithat.Scene.HomeScene;
import com.huy.appnoithat.Service.LuaChonNoiThat.LuaChonNoiThatService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class LuaChonNoiThatController implements Initializable {
    @FXML
    private Button BackButton;
    @FXML
    private TableView<BangNoiThat> TableNoiThat;
    @FXML
    private TableColumn<BangNoiThat, Float> Cao;
    @FXML
    private TableColumn<BangNoiThat, Float> Dai;
    @FXML
    private TableColumn<BangNoiThat, Long> DonGia;
    @FXML
    private TableColumn<BangNoiThat, String> DonVi;
    @FXML
    private TableColumn<BangNoiThat, String> HangMuc;
    @FXML
    private TableColumn<BangNoiThat, String> NoiThat;
    @FXML
    private TableColumn<BangNoiThat, String> PhongCach;
    @FXML
    private TableColumn<BangNoiThat, Float> Rong;
    @FXML
    private TableColumn<BangNoiThat, Float> SoLuong;
    @FXML
    private TableColumn<BangNoiThat, Long> ThanhTien;
    @FXML
    private TableColumn<BangNoiThat, String> VatLieu;
    @FXML
    private TableColumn<BangNoiThat, Integer> id;
    LuaChonNoiThatService luaChonNoiThatService;
    public LuaChonNoiThatController() {
        luaChonNoiThatService = new LuaChonNoiThatService();
    }
    // Call this method everytime you switch scene

    public void initialize() {
    }
    public void setUpTable(){
        PhongCach.setCellValueFactory(new PropertyValueFactory<>("PhongCach"));
        Cao.setCellValueFactory(new PropertyValueFactory<>("Cao"));
        Dai.setCellValueFactory(new PropertyValueFactory<>("Dai"));
        Rong.setCellValueFactory(new PropertyValueFactory<>("Rong"));
        DonGia.setCellValueFactory(new PropertyValueFactory<>("DonGia"));
        DonVi.setCellValueFactory(new PropertyValueFactory<>("DonVi"));
        HangMuc.setCellValueFactory(new PropertyValueFactory<>("HangMuc"));
        NoiThat.setCellValueFactory(new PropertyValueFactory<>("NoiThat"));
        VatLieu.setCellValueFactory(new PropertyValueFactory<>("VatLieu"));
        ThanhTien.setCellValueFactory(new PropertyValueFactory<>("ThanhTien"));
        SoLuong.setCellValueFactory(new PropertyValueFactory<>("SoLuong"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
    }
    @Override
    public final void initialize(URL url, ResourceBundle resourceBundle) {
        setUpTable();
        BangNoiThat bangNoiThat = new BangNoiThat(1, "Phong Cach", 1, 1, 1, 1, "cm", "Hang Muc", "Noi That", "Vat Lieu", 1, 1);
        System.out.println(bangNoiThat);
        TableNoiThat.getItems().add(bangNoiThat);
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
