package com.huy.appnoithat.Controller.LuaChonNoiThat.Setup;

import com.huy.appnoithat.Controller.LuaChonNoiThat.Cell.CustomEditingCell;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Cell.CustomNumberCell;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Cell.CustomTextAreaCell;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Collum.HangMucCollumHandler;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Collum.KichThuocHandler;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Collum.STTCollumHandler;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Collum.VatLieuCollumHandler;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.TableCalculationUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.TableUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.CustomConverter.CustomLongStringConverter;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangThanhToan;
import com.huy.appnoithat.Controller.LuaChonNoiThat.LuaChonNoiThatController;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.util.converter.FloatStringConverter;

public class SetupBangNoiThat {
    private TreeTableColumn<BangNoiThat, Float> Cao, Dai, Rong, KhoiLuong;
    private TreeTableColumn<BangNoiThat, Long> DonGia, ThanhTien;
    private TreeTableColumn<BangNoiThat, String> DonVi, HangMuc, VatLieu, STT;
    private TreeTableView<BangNoiThat> TableNoiThat;
    private TableView<BangThanhToan> bangThanhToan;
    private int itemCount = 0;

    public SetupBangNoiThat(LuaChonNoiThatController luaChonNoiThatController) {
        Cao = luaChonNoiThatController.getCao();
        Dai = luaChonNoiThatController.getDai();
        Rong = luaChonNoiThatController.getRong();
        KhoiLuong = luaChonNoiThatController.getKhoiLuong();
        DonGia = luaChonNoiThatController.getDonGia();
        ThanhTien = luaChonNoiThatController.getThanhTien();
        DonVi = luaChonNoiThatController.getDonVi();
        HangMuc = luaChonNoiThatController.getHangMuc();
        VatLieu = luaChonNoiThatController.getVatLieu();
        STT = luaChonNoiThatController.getSTT();
        TableNoiThat = luaChonNoiThatController.getTableNoiThat();
        bangThanhToan = luaChonNoiThatController.getBangThanhToan();

    }

    public void setUpBangNoiThat() {
        resizeToFit();
        setUpCollum();
        TableNoiThat.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        if (TableNoiThat.getRoot() != null) return;
        TreeItem<BangNoiThat> itemRoot = TableUtils.createNewItem("0");
        itemRoot.getValue().getThanhTien().addListener((observableValue, aLong, t1) -> {
            TableCalculationUtils.calculateBangThanhToan(bangThanhToan, t1.longValue());
        });
        itemRoot.addEventHandler(TreeItem.childrenModificationEvent(), event -> {
            itemCount++;
            TableNoiThat.scrollTo(itemCount + 1);
        });
        TableNoiThat.setRoot(itemRoot);
        TableNoiThat.setShowRoot(false);
        TableNoiThat.setEditable(true);
        itemRoot.getChildren().add(TableUtils.createNewItem("A"));
    }
    /**
     * This function will resize the table to fit the current screen
     */

    private void resizeToFit() {
        VatLieu.setResizable(false);
        TableNoiThat.widthProperty().addListener((observableValue, number, t1) -> {
            DoubleBinding usedWidth = STT.widthProperty()
                    .add(VatLieu.widthProperty())
                    .add(HangMuc.widthProperty())
                    .add(DonVi.widthProperty())
                    .add(DonGia.widthProperty())
                    .add(KhoiLuong.widthProperty())
                    .add(Cao.widthProperty())
                    .add(Dai.widthProperty())
                    .add(Rong.widthProperty())
                    .add(ThanhTien.widthProperty());
            double width = t1.doubleValue() - usedWidth.doubleValue() + VatLieu.getWidth();
            DoubleProperty observableDouble = new SimpleDoubleProperty(width);
            VatLieu.prefWidthProperty().bind(observableDouble);
        });
    }
    private void setUpCollum() {
        setUpKichThuoc();
        setUpDonGia();
        setUpDonVi();
        setUpHangMuc();
        setUpVatLieu();
        setUpThanhTien();
        setUpKhoiLuong();
        setUpSTT();
    }

    /**
     * This function will set up the collum for KichThuoc
     */
    private void setUpSTT() {
        STTCollumHandler sttCollumHandler = new STTCollumHandler(TableNoiThat);
        // Set up collum for STT
        STT.setCellValueFactory(sttCollumHandler::getCustomCellValueFactory);
        STT.setCellFactory(sttCollumHandler::getCustomCellFactory);
        STT.setOnEditCommit(sttCollumHandler::onEditCommitSTT);
    }

    /**
     * This function will set up the collum for KichThuoc
     */
    private void setUpKhoiLuong() {
        // Set up collum for KhoiLuong
        KhoiLuong.setText("Khối\nlượng");
        KhoiLuong.setCellValueFactory(param -> {
            if (param.getValue() == null) return null;
            return param.getValue().getValue().getKhoiLuong().asObject();
        });
        KhoiLuong.setCellFactory(param -> new CustomNumberCell<>(new FloatStringConverter(), TableNoiThat));
        KhoiLuong.setOnEditCommit(event -> {
            float khoiLuong = event.getNewValue();
            long donGia = event.getRowValue().getValue().getDonGia().getValue();
            long thanhTien = TableCalculationUtils.calculateThanhTien(khoiLuong, donGia);
            event.getRowValue().getValue().setThanhTien(thanhTien);
            event.getRowValue().getValue().setKhoiLuong(khoiLuong);
            TableCalculationUtils.calculateTongTien(event.getRowValue().getParent());
        });
    }

    /**
     * This function will set up the collum for ThanhTien
     */
    private void setUpThanhTien() {
        // Set up collum for ThanhTien
        ThanhTien.setCellValueFactory(param -> {
            if (param.getValue() == null) return null;
            return param.getValue().getValue().getThanhTien().asObject();
        });
        ThanhTien.setCellFactory(param -> new CustomNumberCell<>(new CustomLongStringConverter(), TableNoiThat));
        ThanhTien.setOnEditCommit(event ->
                event.getRowValue().getValue().setThanhTien(event.getNewValue()));
    }

    /**
     * This function will set up the collum for VatLieu
     */
    private void setUpVatLieu() {
        // Set up collum for VatLieu
        ObservableList<String> vatLieuList = FXCollections.observableArrayList();
        VatLieuCollumHandler vatLieuCollumHandler = new VatLieuCollumHandler(vatLieuList);
        VatLieu.setCellValueFactory(vatLieuCollumHandler::getCustomCellValueFactory);
        CheckBox checkBox = new CheckBox();
        checkBox.setOnAction(actionEvent -> {
            if (checkBox.isSelected()) {
                VatLieu.setCellFactory(param -> new CustomTextAreaCell());
                VatLieu.setOnEditCommit(event -> {
                    event.getRowValue().getValue().setVatLieu(event.getNewValue());
                });
            } else {
                VatLieu.setCellFactory(vatLieuCollumHandler::getCustomCellFactory);
                VatLieu.setOnEditStart(vatLieuCollumHandler::onStartEditVatLieu);
                VatLieu.setOnEditCommit(vatLieuCollumHandler::onEditCommitVatLieu);
            }
        });
        VatLieu.setGraphic(checkBox);

        VatLieu.setCellFactory(vatLieuCollumHandler::getCustomCellFactory);
        VatLieu.setOnEditStart(vatLieuCollumHandler::onStartEditVatLieu);
        VatLieu.setOnEditCommit(vatLieuCollumHandler::onEditCommitVatLieu);
    }

    /**
     * This function will set up the collum for HangMuc
     */
    private void setUpHangMuc() {

        ObservableList<String> hangMucList = FXCollections.observableArrayList();
        HangMucCollumHandler hangMucCollumHandler = new HangMucCollumHandler(hangMucList);
        CheckBox checkBox = new CheckBox();
        checkBox.setOnAction(actionEvent -> {
            if (checkBox.isSelected()) {
                HangMuc.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
                HangMuc.setOnEditCommit(event -> {
                    event.getRowValue().getValue().setHangMuc(event.getNewValue());
                });
            } else {
                HangMuc.setCellFactory(hangMucCollumHandler::getCustomCellFactory);
                HangMuc.setOnEditCommit(hangMucCollumHandler::onEditCommitHangMuc);
                HangMuc.setOnEditStart(hangMucCollumHandler::onStartEditHangMuc);
            }
        });
        // Set up collum for HangMuc
        HangMuc.setCellValueFactory(hangMucCollumHandler::getCustomCellValueFactory);
        HangMuc.setGraphic(checkBox);

        HangMuc.setCellFactory(hangMucCollumHandler::getCustomCellFactory);
        HangMuc.setOnEditCommit(hangMucCollumHandler::onEditCommitHangMuc);
        HangMuc.setOnEditStart(hangMucCollumHandler::onStartEditHangMuc);
    }

    /**
     * This function will set up the collum for DonVi
     */
    private void setUpDonVi() {
        DonVi.setCellValueFactory(param -> {
            if (param.getValue() == null) return null;
            return param.getValue().getValue().getDonVi();
        });
        DonVi.setCellFactory(param -> new CustomEditingCell<>(false));
    }

    /**
     * This function will set up the collum for DonGia
     */
    private void setUpDonGia() {
        // Set up collum for DonGia
        DonGia.setCellValueFactory(param -> {
            if (param.getValue() == null) return null;
            return param.getValue().getValue().getDonGia().asObject();
        });
        DonGia.setCellFactory(param -> new CustomNumberCell<>(new CustomLongStringConverter(), TableNoiThat));
        DonGia.setOnEditCommit(event -> {
            event.getRowValue().getValue().setDonGia(event.getNewValue());
        });
    }

    /**
     * This function will set up the collum for KichThuoc
     */
    private void setUpKichThuoc() {
        KichThuocHandler kichThuocHandler = new KichThuocHandler(TableNoiThat, Cao, Dai, Rong);

        Cao.setCellValueFactory(param -> {
            if (param.getValue() == null) return null;
            return param.getValue().getValue().getCao().asObject();
        });
        Cao.setCellFactory(param -> new CustomNumberCell<>(new FloatStringConverter(), TableNoiThat));
        Cao.setOnEditCommit(kichThuocHandler::onCommitEditKichThuoc);

        Dai.setCellValueFactory(param -> {
            if (param.getValue() == null) return null;
            return param.getValue().getValue().getDai().asObject();
        });
        Dai.setCellFactory(param -> new CustomNumberCell<>(new FloatStringConverter(), TableNoiThat));
        Dai.setOnEditCommit(kichThuocHandler::onCommitEditKichThuoc);

        Rong.setCellValueFactory(param -> {
            if (param.getValue() == null) return null;
            return param.getValue().getValue().getRong().asObject();
        });
        Rong.setCellFactory(param -> new CustomNumberCell<>(new FloatStringConverter(), TableNoiThat));
        Rong.setOnEditCommit(kichThuocHandler::onCommitEditKichThuoc);
    }
}
