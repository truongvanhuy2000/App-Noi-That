package com.huy.appnoithat.Controller.LuaChonNoiThat;

import com.huy.appnoithat.Controller.LuaChonNoiThat.Cell.CustomNumberCell;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Collum.HangMucCollumHandler;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Collum.STTCollumHandler;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Collum.VatLieuCollumHandler;
import com.huy.appnoithat.Scene.HomeScene;
import com.huy.appnoithat.Service.LuaChonNoiThat.LuaChonNoiThatService;
import com.huy.appnoithat.Shared.ErrorUtils;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.LongStringConverter;
import org.apache.commons.io.FilenameUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import java.io.File;

import com.huy.appnoithat.Shared.Utils;

public class LuaChonNoiThatController implements Initializable {
    @FXML
    private TreeTableView<BangNoiThat> TableNoiThat;
    @FXML
    private TreeTableColumn<BangNoiThat, Float> Cao, Dai, Rong, KhoiLuong;
    @FXML
    private TreeTableColumn<BangNoiThat, Long> DonGia, ThanhTien;
    @FXML
    private TreeTableColumn<BangNoiThat, String> DonVi, HangMuc, VatLieu;
    @FXML
    private TreeTableColumn<BangNoiThat, String> STT;
    @FXML
    private Button BackButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button addContinuousButton;
    @FXML
    private Button addNewButton;
    @FXML
    private ImageView ImageView;
    ObservableList<String> hangMucList = FXCollections.observableArrayList();
    ObservableList<String> vatLieuList = FXCollections.observableArrayList();
    private TreeItem<BangNoiThat> currentSelectedItem;
    private final LuaChonNoiThatService luaChonNoiThatService;
    public LuaChonNoiThatController() {
        luaChonNoiThatService = new LuaChonNoiThatService();
    }
    // Call this method everytime you switch scene
    public void initialize() {
    }

    @Override
    public final void initialize(URL url, ResourceBundle resourceBundle) {
        setUpTable();
//        deleteButton.setDisable(true);
        ButtonHandler buttonHandler = new ButtonHandler(TableNoiThat);
        deleteButton.setOnAction(buttonHandler::onDeleteLine);
        addNewButton.setOnAction(buttonHandler::addNewLine);
        addContinuousButton.setOnAction(buttonHandler::continuousLineAdd);
        workAroundToCollumWidthBug();
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
            ErrorUtils.throwErrorSignal("File không hợp lệ");
            return;
        }
        try {
            Image image = new Image(new FileInputStream(file));
            ImageView.setImage(image);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public void onEditCommitVatLieu(TreeTableColumn.CellEditEvent<BangNoiThat, String> event) {
        String newValue =  event.getNewValue();
        event.getRowValue().getValue().setVatLieu(newValue);
    }

    private void workAroundToCollumWidthBug(){
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(1000),
                ae -> TreeTableView.CONSTRAINED_RESIZE_POLICY.call(new TreeTableView.ResizeFeatures<>(TableNoiThat, HangMuc, 1.0))));
        timeline.play();
    }

    private void setUpTable() {
        setUpCollum();
        TreeItem<BangNoiThat> itemRoot = new TreeItem<>(new BangNoiThat("0", 0f, 0f, 0f, 0L, "", "IM root", "", 0L, 0f));
//        itemRoot.getValue().setDonGia(90f);90f);
        TableNoiThat.setRoot(itemRoot);
        TableNoiThat.setShowRoot(false);
        TableNoiThat.setEditable(true);
//        TableNoiThat.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            currentSelectedItem = observable.getValue();
////            deleteButton.setDisable(false);
//        });
        itemRoot.getChildren().add(new TreeItem<>(new BangNoiThat("A", 0f, 0f, 0f, 0L, "", "", "", 0L, 0f)));
    }
    private void setUpCollum(){
        setUpCao();
        setUpDai();
        setUpRong();
        setUpDonGia();
        setUpDonVi();
        setUpHangMuc();
        setUpVatLieu();
        setUpThanhTien();
        setUpKhoiLuong();
        setUpSTT();
    }
    private void setUpSTT() {
        STTCollumHandler sttCollumHandler = new STTCollumHandler();
        // Set up collum for STT
        STT.setCellValueFactory(sttCollumHandler::getCustomCellValueFactory);
        STT.setCellFactory(sttCollumHandler::getCustomCellFactory);
        STT.setOnEditCommit(sttCollumHandler::onEditCommitSTT);
    }
    private void setUpKhoiLuong(){
        // Set up collum for KhoiLuong
        KhoiLuong.setText("Khối\nlượng");
        KhoiLuong.setCellValueFactory(param -> param.getValue().getValue().getKhoiLuong().asObject());
        KhoiLuong.setCellFactory(param -> new CustomNumberCell<>(new FloatStringConverter()));
        KhoiLuong.setOnEditCommit(event -> {
            event.getRowValue().getValue().setKhoiLuong(event.getNewValue());
        });
    }
    private void setUpThanhTien(){
        // Set up collum for ThanhTien
        ThanhTien.setCellValueFactory(param -> param.getValue().getValue().getThanhTien().asObject());
        ThanhTien.setCellFactory(param -> new CustomNumberCell<>(new LongStringConverter()));
        ThanhTien.setOnEditCommit(event -> {
            event.getRowValue().getValue().setThanhTien(event.getNewValue());
        });
    }
    private void setUpVatLieu(){
        // Set up collum for VatLieu
        VatLieuCollumHandler vatLieuCollumHandler = new VatLieuCollumHandler(this.vatLieuList);
        VatLieu.setCellValueFactory(vatLieuCollumHandler::getCustomCellValueFactory);
        VatLieu.setCellFactory(vatLieuCollumHandler::getCustomCellFactory);
        VatLieu.setOnEditCommit(vatLieuCollumHandler::onEditCommitVatLieu);
        VatLieu.setOnEditStart(vatLieuCollumHandler::onStartEditVatLieu);
    }

    private void setUpHangMuc(){
        HangMucCollumHandler hangMucCollumHandler = new HangMucCollumHandler(this.hangMucList);
        // Set up collum for HangMuc
        HangMuc.setCellValueFactory(hangMucCollumHandler::getCustomCellValueFactory);
        HangMuc.setOnEditCommit(hangMucCollumHandler::onEditCommitHangMuc);
        HangMuc.setCellFactory(hangMucCollumHandler::getCustomCellFactory);
        HangMuc.setOnEditStart(hangMucCollumHandler::onStartEditHangMuc);
    }

    private void setUpDonVi(){
        // Set up collum for Donvi
        DonVi.setCellValueFactory(param -> param.getValue().getValue().getDonVi());
        DonVi.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        DonVi.setOnEditCommit(event -> {
            event.getRowValue().getValue().setDonVi(event.getNewValue());
        });
    }
    private void setUpDonGia(){
        // Set up collum for DonGia
        DonGia.setCellValueFactory(param -> param.getValue().getValue().getDonGia().asObject());
        DonGia.setCellFactory(param -> new CustomNumberCell<>(new LongStringConverter()));
        DonGia.setOnEditCommit(event -> {
            event.getRowValue().getValue().setDonGia(event.getNewValue());
        });
    }
    private void setUpRong(){
        // Set up collum for Rong
        Rong.setCellValueFactory(param -> param.getValue().getValue().getRong().asObject());
        Rong.setCellFactory(param -> new CustomNumberCell<>(new FloatStringConverter()));
        Rong.setOnEditCommit(event -> {
            event.getRowValue().getValue().setRong(event.getNewValue());
        });
    }
    private void setUpDai(){
        // Set up collum for Dai
        Dai.setCellValueFactory(param -> param.getValue().getValue().getDai().asObject());
        Dai.setCellFactory(param -> new CustomNumberCell<>(new FloatStringConverter()));
        Dai.setOnEditCommit(event -> {
            event.getRowValue().getValue().setDai(event.getNewValue());
        });
    }
    private void setUpCao(){
        // Set up collum for Cao
        Cao.setCellValueFactory(param -> param.getValue().getValue().getCao().asObject());
        Cao.setCellFactory(param -> new CustomNumberCell<>(new FloatStringConverter()));
        Cao.setOnEditCommit(event -> {
            event.getRowValue().getValue().setCao(event.getNewValue());
        });
    }
}
