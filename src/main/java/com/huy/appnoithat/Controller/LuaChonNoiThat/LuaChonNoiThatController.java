package com.huy.appnoithat.Controller.LuaChonNoiThat;

import com.huy.appnoithat.Controller.LuaChonNoiThat.Cell.CustomNumberCell;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Collum.HangMucCollumHandler;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Collum.KichThuocHandler;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Collum.STTCollumHandler;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Collum.VatLieuCollumHandler;
import com.huy.appnoithat.Scene.HomeScene;
import com.huy.appnoithat.Scene.LuaChonNoiThatScene;
import com.huy.appnoithat.Service.LuaChonNoiThat.LuaChonNoiThatService;
import com.huy.appnoithat.Shared.ErrorUtils;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.LongStringConverter;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
    private Button deleteButton;
    @FXML
    private Button addContinuousButton;
    @FXML
    private Button addNewButton;
    @FXML
    private ImageView ImageView;
    private final static Logger LOGGER = LogManager.getLogger(LuaChonNoiThatController.class);
    public LuaChonNoiThatController() {
    }
    public void initialize() {
    }
    @Override
    public final void initialize(URL url, ResourceBundle resourceBundle) {
        setUpTable();
        ButtonHandler buttonHandler = new ButtonHandler(TableNoiThat);
        deleteButton.setOnAction(buttonHandler::onDeleteLine);
        addNewButton.setOnAction(buttonHandler::addNewLine);
        addContinuousButton.setOnAction(buttonHandler::continuousLineAdd);
        workAroundToCollumWidthBug();
        System.out.println("LuaChonNoiThatController initialized");
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
        if (file == null) return;
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
    private void workAroundToCollumWidthBug(){
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(1000),
                ae -> TreeTableView.CONSTRAINED_RESIZE_POLICY.call(new TreeTableView.ResizeFeatures<>(TableNoiThat, HangMuc, 1.0))));
        timeline.play();
    }
    private void setUpTable() {
        setUpCollum();
        TreeItem<BangNoiThat> itemRoot = new TreeItem<>(new BangNoiThat("0", 0f, 0f, 0f, 0L, "", "IM root", "", 0L, 0f));
        TableNoiThat.setRoot(itemRoot);
        TableNoiThat.setShowRoot(false);
        TableNoiThat.setEditable(true);
        TreeItem<BangNoiThat> newItem = new TreeItem<>(new BangNoiThat("A", 0f, 0f, 0f, 0L, "", "", "", 0L, 0f));
        itemRoot.getChildren().add(newItem);
    }
    private void setUpCollum(){
        setUpKichThuoc();
        setUpDonGia();
        setUpDonVi();
        setUpHangMuc();
        setUpVatLieu();
        setUpThanhTien();
        setUpKhoiLuong();
        setUpSTT();
    }
    private void setUpSTT() {
        STTCollumHandler sttCollumHandler = new STTCollumHandler(TableNoiThat);
        // Set up collum for STT
        STT.setCellValueFactory(sttCollumHandler::getCustomCellValueFactory);
        STT.setCellFactory(sttCollumHandler::getCustomCellFactory);
        STT.setOnEditCommit(sttCollumHandler::onEditCommitSTT);
    }
    private void setUpKhoiLuong(){
        // Set up collum for KhoiLuong
        KhoiLuong.setText("Khối\nlượng");
        KhoiLuong.setCellValueFactory(param -> param.getValue().getValue().getKhoiLuong().asObject());
        KhoiLuong.setCellFactory(param -> new CustomNumberCell<>(new FloatStringConverter(), TableNoiThat));
        KhoiLuong.setOnEditCommit(event -> {
            float khoiLuong = event.getNewValue();
            long donGia = event.getRowValue().getValue().getDonGia().getValue();
            long thanhTien = TableUtils.calculateThanhTien(khoiLuong, donGia);
            event.getRowValue().getValue().setThanhTien(thanhTien);
            event.getRowValue().getValue().setKhoiLuong(khoiLuong);
        });
    }
    private void setUpThanhTien(){
        // Set up collum for ThanhTien
        ThanhTien.setCellValueFactory(param -> param.getValue().getValue().getThanhTien().asObject());
        ThanhTien.setCellFactory(param -> new CustomNumberCell<>(new LongStringConverter(), TableNoiThat));
        ThanhTien.setOnEditCommit(event -> {
            event.getRowValue().getValue().setThanhTien(event.getNewValue());
        });
    }
    private void setUpVatLieu(){
        // Set up collum for VatLieu
        ObservableList<String> vatLieuList = FXCollections.observableArrayList();
        VatLieuCollumHandler vatLieuCollumHandler = new VatLieuCollumHandler(vatLieuList);
        VatLieu.setCellValueFactory(vatLieuCollumHandler::getCustomCellValueFactory);
        VatLieu.setCellFactory(vatLieuCollumHandler::getCustomCellFactory);
        VatLieu.setOnEditCommit(vatLieuCollumHandler::onEditCommitVatLieu);
        VatLieu.setOnEditStart(vatLieuCollumHandler::onStartEditVatLieu);
    }

    private void setUpHangMuc(){
        ObservableList<String> hangMucList = FXCollections.observableArrayList();
        HangMucCollumHandler hangMucCollumHandler = new HangMucCollumHandler(hangMucList);
        // Set up collum for HangMuc
        HangMuc.setCellValueFactory(hangMucCollumHandler::getCustomCellValueFactory);
        HangMuc.setOnEditCommit(hangMucCollumHandler::onEditCommitHangMuc);
        HangMuc.setCellFactory(hangMucCollumHandler::getCustomCellFactory);
        HangMuc.setOnEditStart(hangMucCollumHandler::onStartEditHangMuc);
    }

    private void setUpDonVi(){
        // Set up collum for Donvi
        DonVi.setCellValueFactory(param -> param.getValue().getValue().getDonVi());
//        DonVi.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
//        DonVi.setOnEditCommit(event -> {
//            event.getRowValue().getValue().setDonVi(event.getNewValue());
//        });
    }
    private void setUpDonGia(){
        // Set up collum for DonGia
        DonGia.setCellValueFactory(param -> param.getValue().getValue().getDonGia().asObject());
        DonGia.setCellFactory(param -> new CustomNumberCell<>(new LongStringConverter(), TableNoiThat));
        DonGia.setOnEditCommit(event -> {
            event.getRowValue().getValue().setDonGia(event.getNewValue());
        });
    }
    private void setUpKichThuoc(){
        KichThuocHandler kichThuocHandler = new KichThuocHandler(TableNoiThat, Cao, Dai, Rong);

        Cao.setCellValueFactory(param -> param.getValue().getValue().getCao().asObject());
        Cao.setCellFactory(param -> new CustomNumberCell<>(new FloatStringConverter(), TableNoiThat));
        Cao.setOnEditCommit(kichThuocHandler::onCommitEditKichThuoc);

        Dai.setCellValueFactory(param -> param.getValue().getValue().getDai().asObject());
        Dai.setCellFactory(param -> new CustomNumberCell<>(new FloatStringConverter(), TableNoiThat));
        Dai.setOnEditCommit(kichThuocHandler::onCommitEditKichThuoc);

        Rong.setCellValueFactory(param -> param.getValue().getValue().getRong().asObject());
        Rong.setCellFactory(param -> new CustomNumberCell<>(new FloatStringConverter(), TableNoiThat));
        Rong.setOnEditCommit(kichThuocHandler::onCommitEditKichThuoc);
    }
}
