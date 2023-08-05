package com.huy.appnoithat.Controller;

import com.huy.appnoithat.Entity.PhongCachNoiThat;
import com.huy.appnoithat.Scene.HomeScene;
import com.huy.appnoithat.Service.LuaChonNoiThat.LuaChonNoiThatService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


public class LuaChonNoiThatController implements Initializable {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class BangNoiThat {
        private int id;
        private ComboBox<String> PhongCach;
        private float Cao;
        private float Dai;
        private float Rong;
        private long DonGia;
        private String DonVi;
        private ComboBox<String> HangMuc;
        private ComboBox<String> NoiThat;
        private ComboBox<String> VatLieu;
        private long ThanhTien;
        private float SoLuong;
    }

    @FXML
    private TableView<BangNoiThat> TableNoiThat;
    @FXML
    private TableColumn<BangNoiThat, Float> Cao, Dai, Rong, SoLuong;
    @FXML
    private TableColumn<BangNoiThat, Long> DonGia, ThanhTien;
    @FXML
    private TableColumn<BangNoiThat, String> DonVi, HangMuc, NoiThat, PhongCach, VatLieu;
    @FXML
    private TableColumn<BangNoiThat, Integer> id;
    @FXML
    private Button BackButton;
    private int current_id = 0;
    List<PhongCachNoiThat> listPhongCachNoiThat;
    LuaChonNoiThatService luaChonNoiThatService;
    public LuaChonNoiThatController() {
        luaChonNoiThatService = new LuaChonNoiThatService();
    }
    // Call this method everytime you switch scene

    public void initialize() {
    }
    public void setUpTable(){
        TableNoiThat.setEditable(true);

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
        listPhongCachNoiThat = luaChonNoiThatService.findAllPhongCachNoiThat();
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
        else return;
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void addNewLine(ActionEvent event) {
        current_id += 1;
        ComboBox<String> phongCachCombobox = new ComboBox<String>(FXCollections.observableList(getObjectNameList(listPhongCachNoiThat)));
//        phongCachCombobox.setOnAction(e -> {
//            System.out.println(e.getSource().toString());
//            System.out.println(phongCachCombobox.getValue());
//        });
        TableNoiThat.getItems().add(new BangNoiThat(current_id,
                phongCachCombobox,
                0, 0, 0, 0, "",
                null,
                null,
                null,
                0,
                0));
    }

    public List<String> getObjectNameList(List<?> list){
        return list.stream().map(Object::toString).collect(Collectors.toList());
    }

//    private void

    @FXML
    void cellComitEventHandler(ActionEvent event) {
        Object source = event.getSource();
        if (source == PhongCach){
            System.out.println("Phong cach");
        }
        else if (source == HangMuc){
            System.out.println("Hang muc");
        }
        else if (source == NoiThat){
            System.out.println("Noi that");
        }
        else if (source == VatLieu){
            System.out.println("Vat lieu");
        }
        else return;
    }

    @FXML
    void cellEditEventHandler(ActionEvent event) {
        Object source = event.getSource();
        if (source == PhongCach){
            System.out.println("Phong cach");
        }
        else if (source == HangMuc){
            System.out.println("Hang muc");
        }
        else if (source == NoiThat){
            System.out.println("Noi that");
        }
        else if (source == VatLieu){
            System.out.println("Vat lieu");
        }
        else return;
    }
}
