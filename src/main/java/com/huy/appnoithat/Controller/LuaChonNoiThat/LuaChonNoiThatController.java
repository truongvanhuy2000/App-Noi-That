package com.huy.appnoithat.Controller.LuaChonNoiThat;

import com.huy.appnoithat.Entity.PhongCachNoiThat;
import com.huy.appnoithat.Scene.HomeScene;
import com.huy.appnoithat.Service.LuaChonNoiThat.LuaChonNoiThatService;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FilenameUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import java.io.File;
import com.huy.appnoithat.Controller.LuaChonNoiThat.CustomEditingCell;

public class LuaChonNoiThatController implements Initializable {
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
    @FXML
    private ImageView ImageView;

    private int current_id = 0;
    List<PhongCachNoiThat> listPhongCachNoiThat;
    private final LuaChonNoiThatService luaChonNoiThatService;
    private ObservableList<BangNoiThat> list = FXCollections.observableArrayList();
    public LuaChonNoiThatController() {
        luaChonNoiThatService = new LuaChonNoiThatService();
    }
    // Call this method everytime you switch scene

    public void initialize() {
    }
    public void setUpTable(){
        TableNoiThat.setEditable(true);

        PhongCach.setCellValueFactory(new PropertyValueFactory<>("PhongCach"));
//        PhongCach.setCellFactory(column -> new CustomComboboxCell());

        Cao.setCellValueFactory(new PropertyValueFactory<>("Cao"));
//        Cao.setCellFactory(column -> new CustomEditingCell<>());

        Dai.setCellValueFactory(new PropertyValueFactory<>("Dai"));
//        Dai.setCellFactory(column -> new CustomEditingCell<>());

        Rong.setCellValueFactory(new PropertyValueFactory<>("Rong"));
//        Rong.setCellFactory(column -> new CustomEditingCell<>());

        DonGia.setCellValueFactory(new PropertyValueFactory<>("DonGia"));
//        DonGia.setCellFactory(column -> new CustomEditingCell<>());

        DonVi.setCellValueFactory(new PropertyValueFactory<>("DonVi"));
        HangMuc.setCellValueFactory(new PropertyValueFactory<>("HangMuc"));
//        HangMuc.setCellFactory(column -> new CustomComboboxCell());

        NoiThat.setCellValueFactory(new PropertyValueFactory<>("NoiThat"));
//        NoiThat.setCellFactory(column -> new CustomComboboxCell());

        VatLieu.setCellValueFactory(new PropertyValueFactory<>("VatLieu"));
//        VatLieu.setCellFactory(column -> new CustomComboboxCell());

        ThanhTien.setCellValueFactory(new PropertyValueFactory<>("ThanhTien"));
//        ThanhTien.setCellFactory(column -> new CustomEditingCell<>());

        SoLuong.setCellValueFactory(new PropertyValueFactory<>("SoLuong"));
//        SoLuong.setCellFactory(column -> new CustomEditingCell<>());

        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableNoiThat.setItems(list);
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
    void OnMouseClickedHandler(MouseEvent event) {
        Object source = event.getSource();
        if (source == ImageView){
            imageViewHandler();
        }
    }
    private void imageViewHandler(){
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        String fileExtension = FilenameUtils.getExtension(file.getName());
        if (!(fileExtension.equals("jpg") || fileExtension.equals("jpeg") || fileExtension.equals("png"))){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Chỉ được chọn ảnh", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        try {
            Image image = new Image(new FileInputStream(file));
            ImageView.setImage(image);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void addNewLine(ActionEvent event) {
        current_id += 1;
        // Populate list with valid data
        BangNoiThat noiThat = new BangNoiThat(current_id, "NỘI THẤT PHONG CÁCH HIỆN ĐẠI ", 2f, 15f, 4f, 100L, "cm", "Tủ bếp dưới ", "NỘI THẤT PHÒNG BẾP", "- Thùng: nhựa Picomat 17mm chống nước tuyệt đối. ", 999L, 100f);
        list.add(noiThat);
    }

    public List<String> getObjectNameList(List<?> list){
        return list.stream().map(Object::toString).collect(Collectors.toList());
    }

}
