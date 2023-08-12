package com.huy.appnoithat.Controller.LuaChonNoiThat;

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
    private ImageView ImageView;
    ObservableList<String> hangMucList = FXCollections.observableArrayList();
    ObservableList<String> noiThatList = FXCollections.observableArrayList();
    ObservableList<String> vatLieuList = FXCollections.observableArrayList();
//    private Integer current_id = 0;
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
    @FXML
    void addNewLine(ActionEvent event) {
        TreeItem<BangNoiThat> temp = new TreeItem<>(new BangNoiThat(
                "A", 0f, 0f, 0f, 0L,
                "", "", "", 0L, 0f));
        TableNoiThat.getRoot().getChildren().add(temp);
//        temp.getChildren().add(new TreeItem<>(new BangNoiThat("", 0f, 0f, 0f, 0L, "", "", "", 0L, 0f)));
    }

    public List<String> getObjectNameList(List<?> list){
        return list.stream().map(Object::toString).collect(Collectors.toList());
    }

    private void workAroundToCollumWidthBug(){
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(2000),
                ae -> TreeTableView.CONSTRAINED_RESIZE_POLICY.call(new TreeTableView.ResizeFeatures<>(TableNoiThat, HangMuc, 1.0))));
        timeline.play();
    }

    private void onEditCommitSTT(TreeTableColumn.CellEditEvent<BangNoiThat, String> event) {
        String newValue = event.getNewValue();
        event.getRowValue().getValue().setSTT(newValue);
        if(Utils.RomanNumber.isRoman(newValue)){
            handleCommitedRomanSTT(event, newValue);
            return;
        }
        if (Utils.isAlpha(newValue)){
            System.out.println(":))))) \n");
            return;
        }
        if (Utils.isNumeric(newValue)){
            handleCommitedNumericSTT(event, newValue);
            return;
        }

    }
    private void handleCommitedRomanSTT(TreeTableColumn.CellEditEvent<BangNoiThat, String> event, String item){
        TreeItem<BangNoiThat> currentItem = event.getRowValue();
        TreeItem<BangNoiThat> newItem = new TreeItem<>(currentItem.getValue());
        currentItem.getParent().getChildren().remove(currentItem);

        ObservableList<TreeItem<BangNoiThat>> tempPhongCachList = TableNoiThat.getRoot().getChildren();
        if (tempPhongCachList.size() == 0){
//            ErrorUtils.throwErrorSignal("Chưa lựa chọn phong cách");
            return;
        }
        TreeItem<BangNoiThat> latestPhongCachNode = tempPhongCachList.get(tempPhongCachList.size() - 1);
        latestPhongCachNode.getChildren().add(newItem);
    }
    private void handleCommitedNumericSTT(TreeTableColumn.CellEditEvent<BangNoiThat, String> event, String item){
        TreeItem<BangNoiThat> currentItem = event.getRowValue();
        TreeItem<BangNoiThat> newItem = new TreeItem<>(currentItem.getValue());
        currentItem.getParent().getChildren().remove(currentItem);

        ObservableList<TreeItem<BangNoiThat>> tempPhongCachList = TableNoiThat.getRoot().getChildren();
        if (tempPhongCachList.size() == 0){
//            ErrorUtils.throwErrorSignal("Chưa lựa chọn phong cách");
            return;
        }
        ObservableList<TreeItem<BangNoiThat>> tempNoiThatList = tempPhongCachList.get(tempPhongCachList.size() - 1).getChildren();
        if (tempNoiThatList.size() == 0){
//            ErrorUtils.throwErrorSignal("Chưa lựa chọn nội thất");
            return;
        }
        TreeItem<BangNoiThat> latestNoiThatNode = tempNoiThatList.get(tempNoiThatList.size() - 1);
        latestNoiThatNode.getChildren().add(newItem);
    }

    private void onEditCommitHangMuc(TreeTableColumn.CellEditEvent<BangNoiThat, String> event) {
        String newValue =  event.getNewValue();
        event.getRowValue().getValue().setHangMuc(newValue);
    }

    private void onEditCommitVatLieu(TreeTableColumn.CellEditEvent<BangNoiThat, String> event) {
        String newValue =  event.getNewValue();
        event.getRowValue().getValue().setVatLieu(newValue);
    }

    private void onStartEditHangMuc(TreeTableColumn.CellEditEvent<BangNoiThat, String> event) {
        TreeItem<BangNoiThat> currentItem = event.getRowValue();
        String stt = currentItem.getValue().getSTT().getValue();

        if (Utils.RomanNumber.isRoman(stt)){

        }

        if (Utils.isAlpha(stt)){
            System.out.println(":))))) \n");
            return;
        }

        if (Utils.isNumeric(stt)){
            System.out.println(":))))) \n");
            return;
        }
        hangMucList.clear();
        hangMucList.addAll(getObjectNameList(luaChonNoiThatService.findAllPhongCachNoiThat()));
    }

    private void setUpTable(){
        // Set up collum for Cao
        Cao.setCellValueFactory(param -> {
            SimpleFloatProperty tempCao =  param.getValue().getValue().getCao();
            return tempCao.getValue().equals(0f) ? null : tempCao.asObject();
        });
        Cao.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn(new FloatStringConverter()));
        Cao.setOnEditCommit(event -> {
            event.getRowValue().getValue().setCao(event.getNewValue());
        });

        // Set up collum for Dai
        Dai.setCellValueFactory(param -> {
            SimpleFloatProperty tempDai =  param.getValue().getValue().getDai();
            return tempDai.getValue().equals(0f) ? null : tempDai.asObject();
        });
        Dai.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn(new FloatStringConverter()));
        Dai.setOnEditCommit(event -> {
            event.getRowValue().getValue().setDai(event.getNewValue());
        });

        // Set up collum for Rong
        Rong.setCellValueFactory(param -> {
            SimpleFloatProperty tempRong =  param.getValue().getValue().getRong();
            return tempRong.getValue().equals(0f) ? null : tempRong.asObject();
        });
        Rong.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn(new FloatStringConverter()));
        Rong.setOnEditCommit(event -> {
            event.getRowValue().getValue().setRong(event.getNewValue());
        });

        // Set up collum for DonGia
        DonGia.setCellValueFactory(param -> {
            SimpleLongProperty tempDonGia =  param.getValue().getValue().getDonGia();
            return tempDonGia.getValue().equals(0L) ? null : tempDonGia.asObject();
        });
        DonGia.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn(new LongStringConverter()));
        DonGia.setOnEditCommit(event -> {
            event.getRowValue().getValue().setDonGia(event.getNewValue());
        });

        // Set up collum for KhoiLuong
        DonVi.setCellValueFactory(param -> {
            return param.getValue().getValue().getDonVi();
        });
        DonVi.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        DonVi.setOnEditCommit(event -> {
            event.getRowValue().getValue().setDonVi(event.getNewValue());
        });

        // Set up collum for HangMuc
        HangMuc.setCellValueFactory(param -> {
            return param.getValue().getValue().getHangMuc();
        });
        HangMuc.setOnEditCommit(event -> {
            onEditCommitHangMuc(event);
        });
        HangMuc.setCellFactory(column -> new CustomComboboxCell(hangMucList));
        HangMuc.setOnEditStart(event -> {
            onStartEditHangMuc(event);
        });

        // Set up collum for VatLieu
        VatLieu.setCellValueFactory(param -> {
            return param.getValue().getValue().getVatLieu();
        });
        VatLieu.setCellFactory(column -> new CustomComboboxCell(hangMucList));
        VatLieu.setOnEditCommit(event -> {
            onEditCommitVatLieu(event);
        });

        // Set up collum for ThanhTien
        ThanhTien.setCellValueFactory(param -> {
            SimpleLongProperty tempThanhTien = param.getValue().getValue().getThanhTien();
            return tempThanhTien.getValue().equals(0L) ? null : tempThanhTien.asObject();
        });
        ThanhTien.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn(new LongStringConverter()));
        ThanhTien.setOnEditCommit(event -> {
            event.getRowValue().getValue().setThanhTien(event.getNewValue());
        });

        // Set up collum for KhoiLuong
        KhoiLuong.setCellValueFactory(param -> {
            SimpleFloatProperty tempKhoiLuong = param.getValue().getValue().getKhoiLuong();
            return tempKhoiLuong.getValue().equals(0f) ? null : tempKhoiLuong.asObject();
        });
        KhoiLuong.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn(new FloatStringConverter()));
        KhoiLuong.setOnEditCommit(event -> {
            event.getRowValue().getValue().setKhoiLuong(event.getNewValue());
        });

        // Set up collum for STT
        STT.setCellValueFactory(param -> {
            SimpleStringProperty tempSTT = param.getValue().getValue().getSTT();
            return tempSTT.getValue().equals("") ? null : tempSTT;
        });
        STT.setCellFactory(column -> new CustomEditingCell<>());
        STT.setOnEditCommit(event -> {
            onEditCommitSTT(event);
        });

        TreeItem<BangNoiThat> itemRoot = new TreeItem<>(new BangNoiThat("0", 0f, 0f, 0f, 0L, "", "IM root", "", 0L, 0f));
        TableNoiThat.setRoot(itemRoot);
        TableNoiThat.setShowRoot(false);
        TableNoiThat.setEditable(true);
    }

}
