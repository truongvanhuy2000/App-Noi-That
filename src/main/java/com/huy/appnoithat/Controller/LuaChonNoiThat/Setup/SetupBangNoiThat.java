package com.huy.appnoithat.Controller.LuaChonNoiThat.Setup;

import com.huy.appnoithat.Controller.LuaChonNoiThat.Collum.*;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.CommandManager;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.implementation.AddNewItemIfEmptyCommand;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.TableUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Constant.ItemType;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangThanhToan;
import com.huy.appnoithat.Controller.LuaChonNoiThat.LuaChonNoiThatController;
import com.huy.appnoithat.Service.LuaChonNoiThat.LuaChonNoiThatService;
import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SetupBangNoiThat {
    private final Logger LOGGER = LogManager.getLogger(this);

    private final TreeTableColumn<BangNoiThat, Double> Cao;
    private final TreeTableColumn<BangNoiThat, Double> Dai;
    private final TreeTableColumn<BangNoiThat, Double> Rong;
    private final TreeTableColumn<BangNoiThat, Double> KhoiLuong;
    private final TreeTableColumn<BangNoiThat, Long> DonGia;
    private final TreeTableColumn<BangNoiThat, Long> ThanhTien;
    private final TreeTableColumn<BangNoiThat, String> DonVi;
    private final TreeTableColumn<BangNoiThat, String> HangMuc;
    private final TreeTableColumn<BangNoiThat, String> VatLieu;
    private final TreeTableColumn<BangNoiThat, String> STT;
    private final TreeTableView<BangNoiThat> TableNoiThat;
    private final TableView<BangThanhToan> bangThanhToan;
    private final LuaChonNoiThatService luaChonNoiThatService;
    private final CommandManager commandManager;

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
        this.luaChonNoiThatService = luaChonNoiThatController.getLuaChonNoiThatService();
        commandManager = luaChonNoiThatController.getCommandManager();
    }

    public void setUpBangNoiThat() {
        resizeToFit();
        setUpCollum();
        addInitialItem();
        TableNoiThat.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        TableNoiThat.getSelectionModel().selectedItemProperty().addListener(this::onSelectItemChanged);
    }

    private void onSelectItemChanged(
            ObservableValue<? extends TreeItem<BangNoiThat>> obs,
            TreeItem<BangNoiThat> oldSelection,
            TreeItem<BangNoiThat> newSelection
    ) {
        if (oldSelection != null) {
            oldSelection.setGraphic(null);
        }
        if (newSelection != null) {
            ObservableList<TreeTablePosition<BangNoiThat, ?>> selectedCells = TableNoiThat.getSelectionModel().getSelectedCells();
            if (selectedCells.isEmpty()) {
                return;
            }
            int row = TableNoiThat.getSelectionModel().getSelectedCells().get(0).getRow();
            int column = TableNoiThat.getSelectionModel().getSelectedCells().get(0).getColumn();
            Platform.runLater(() -> {
                TableNoiThat.edit(row, TableNoiThat.getVisibleLeafColumn(column));
            });
        }
    }

    private void addInitialItem() {
        if (TableNoiThat.getRoot() != null) {
            LOGGER.info("Table Noi That already has a root");
            return;
        }
        TreeItem<BangNoiThat> itemRoot = TableUtils.createRootItem(ItemType.ROOT, TableNoiThat, bangThanhToan);
        TableNoiThat.setRoot(itemRoot);
        TableNoiThat.setShowRoot(false);
        TableNoiThat.setEditable(true);

        commandManager.execute(new AddNewItemIfEmptyCommand(TableNoiThat));
    }

    /**
     * This function will resize the table to fit the current screen
     */

    private void resizeToFit() {
        VatLieu.setResizable(false);
        TableNoiThat.widthProperty().addListener((observableValue, number, t1) -> {
            STT.setPrefWidth(t1.doubleValue() * WidthConfiguration.STT / 100);
            HangMuc.setPrefWidth(t1.doubleValue() * WidthConfiguration.HANG_MUC / 100);
            Dai.setPrefWidth(t1.doubleValue() * WidthConfiguration.DAI / 100);
            Rong.setPrefWidth(t1.doubleValue() * WidthConfiguration.RONG / 100);
            Cao.setPrefWidth(t1.doubleValue() * WidthConfiguration.CAO / 100);
            DonVi.setPrefWidth(t1.doubleValue() * WidthConfiguration.DON_VI_TINH / 100);
            DonGia.setPrefWidth(t1.doubleValue() * WidthConfiguration.DON_GIA / 100);
            KhoiLuong.setPrefWidth(t1.doubleValue() * WidthConfiguration.KHOI_LUONG / 100);
            ThanhTien.setPrefWidth(t1.doubleValue() * WidthConfiguration.THANH_TIEN / 100);

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
            double width = t1.doubleValue() - usedWidth.doubleValue() + VatLieu.getWidth() - 10;
            DoubleProperty observableDouble = new SimpleDoubleProperty(width);
            VatLieu.prefWidthProperty().bind(observableDouble);
        });
    }

    private void setUpCollum() {
        new DonViColumn(DonVi, commandManager).setup();
        new DonGiaColumn(DonGia, commandManager).setup();
        new HangMucColumn(HangMuc, luaChonNoiThatService, commandManager).setup();
        new VatLieuColumn(VatLieu, luaChonNoiThatService, commandManager).setup();
        new ThanhTienColumn(ThanhTien, commandManager).setup();
        new KhoiLuongColumn(KhoiLuong, commandManager).setup();
        new STTColumn(STT).setup();
        new CaoColumn(Cao, commandManager).setup();
        new DaiColumn(Dai, commandManager).setup();
        new RongColumn(Rong, commandManager).setup();
    }

    private static class WidthConfiguration {
        public static final double STT = 3;
        public static final double HANG_MUC = 15;
        public static final double DAI = 5;
        public static final double RONG = 5;
        public static final double CAO = 5;
        public static final double DON_VI_TINH = 5.5;
        public static final double DON_GIA = 6.5;
        public static final double KHOI_LUONG = 4.5;
        public static final double THANH_TIEN = 8;
    }
}
