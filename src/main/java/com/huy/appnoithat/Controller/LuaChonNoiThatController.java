package com.huy.appnoithat.Controller;

import com.huy.appnoithat.Service.LuaChonNoiThat.LuaChonNoiThatService;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;

public class LuaChonNoiThatController {
    @FXML
    private TableColumn<?, ?> Cao;

    @FXML
    private TableColumn<?, ?> Dai;

    @FXML
    private TableColumn<?, ?> DonGia;

    @FXML
    private TableColumn<?, ?> DonVi;

    @FXML
    private TableColumn<?, ?> HangMuc;

    @FXML
    private TableColumn<?, ?> NoiThat;

    @FXML
    private TableColumn<?, ?> PhongCach;

    @FXML
    private TableColumn<?, ?> Rong;

    @FXML
    private TableView<?> TableNoiThat;

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
}
