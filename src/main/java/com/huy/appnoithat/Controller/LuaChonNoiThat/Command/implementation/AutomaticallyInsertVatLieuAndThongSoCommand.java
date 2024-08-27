package com.huy.appnoithat.Controller.LuaChonNoiThat.Command.implementation;

import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.Command;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.Memento;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.TableCalculationUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import com.huy.appnoithat.DataModel.Entity.ThongSo;
import com.huy.appnoithat.DataModel.Entity.VatLieu;
import com.huy.appnoithat.Service.LuaChonNoiThat.LuaChonNoiThatService;
import javafx.scene.control.TreeItem;
import lombok.Builder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


public class AutomaticallyInsertVatLieuAndThongSoCommand implements Command {
    // TODO: Write unit test for this shit

    private final Logger LOGGER = LogManager.getLogger(this);

    private final LuaChonNoiThatService luaChonNoiThatService;
    private final TreeItem<BangNoiThat> rowValue;
    private Memento snapshot;
    private BangNoiThat bangNoiThat;

    @Builder
    public AutomaticallyInsertVatLieuAndThongSoCommand(LuaChonNoiThatService luaChonNoiThatService, TreeItem<BangNoiThat> rowValue) {
        this.luaChonNoiThatService = luaChonNoiThatService;
        this.rowValue = rowValue;
    }

    @Override
    public void execute() {
        automaticallyInsertVatLieuAndThongSo(rowValue);
    }

    @Override
    public void undo() {
        snapshot.restore();
        rowValue.setValue(bangNoiThat);
    }

    private void automaticallyInsertVatLieuAndThongSo(TreeItem<BangNoiThat> rowValue) {
        Optional<VatLieu> vatLieu = getTheFirstVatLieu(rowValue);
        if (vatLieu.isPresent()) {
            bangNoiThat = rowValue.getValue();
            snapshot = bangNoiThat.createSnapshot();

            String firstVatLieu = vatLieu.get().getName();
            ThongSo thongSo = vatLieu.get().getThongSo();
            if (thongSo != null) {
                Double dai = Objects.requireNonNullElse(thongSo.getDai(), 0.0);
                Double rong = Objects.requireNonNullElse(thongSo.getRong(), 0.0);
                Double cao = Objects.requireNonNullElse(thongSo.getCao(), 0.0);
                Long donGia = thongSo.getDon_gia();
                String donVi = thongSo.getDon_vi();
                Double khoiLuong = TableCalculationUtils.calculateKhoiLuong(dai, cao, rong, donVi);
                Long thanhTien = TableCalculationUtils.calculateThanhTien(khoiLuong, donGia);

                bangNoiThat.setThanhTien(thanhTien);
                bangNoiThat.setKhoiLuong(khoiLuong);
                bangNoiThat.setDai(dai);
                bangNoiThat.setRong(rong);
                bangNoiThat.setCao(cao);
                bangNoiThat.setDonGia(donGia);
                bangNoiThat.setDonVi(donVi);
            }
            bangNoiThat.setVatLieu(firstVatLieu);
        }
    }

    private Optional<VatLieu> getTheFirstVatLieu(TreeItem<BangNoiThat> currentItem) {
        try {
            String noiThat = currentItem.getParent().getValue().getHangMuc().getValue();
            String phongCach = currentItem.getParent().getParent().getValue().getHangMuc().getValue();
            String hangMuc = currentItem.getValue().getHangMuc().getValue();
            List<VatLieu> items = luaChonNoiThatService.findVatLieuListBy(phongCach, noiThat, hangMuc);
            if (items.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(items.get(0));
        } catch (NullPointerException e) {
            PopupUtils.throwErrorNotification("Chưa lựa chọn thông tin phía trên");
            LOGGER.error("Error when get the first vat lieu item");
            return Optional.empty();
        }
    }
}
