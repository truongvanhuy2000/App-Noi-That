package com.huy.appnoithat.Controller.LuaChonNoiThat.Operation;

import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Common.Utils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.TableUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangThanhToan;
import com.huy.appnoithat.Controller.LuaChonNoiThat.LuaChonNoiThatController;
import com.huy.appnoithat.DataModel.*;
import com.huy.appnoithat.DataModel.NtFile.DataPackage;
import com.huy.appnoithat.Service.LuaChonNoiThat.LuaChonNoiThatService;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
public class ImportOperation {
    final static Logger LOGGER = LogManager.getLogger(ImportOperation.class);
    private TextField TenCongTy, VanPhong, DiaChiXuong, DienThoaiCongTy, Email;
    // Thong tin ve khach hang
    private TextField TenKhachHang, DienThoaiKhachHang, DiaChiKhachHang, NgayLapBaoGia, SanPham;
    // Bang noi that
    private TreeTableView<BangNoiThat> TableNoiThat;
    private TableView<BangThanhToan> bangThanhToan;
    private TextArea noteTextArea;
    private ImageView ImageView;
    LuaChonNoiThatController luaChonNoiThatController;

    public ImportOperation(LuaChonNoiThatController luaChonNoiThatController) {
        TenCongTy = luaChonNoiThatController.getTenCongTy();
        VanPhong = luaChonNoiThatController.getVanPhong();
        DiaChiXuong = luaChonNoiThatController.getDiaChiXuong();
        DienThoaiCongTy = luaChonNoiThatController.getDienThoaiCongTy();
        Email = luaChonNoiThatController.getEmail();
        TenKhachHang = luaChonNoiThatController.getTenKhachHang();
        DienThoaiKhachHang = luaChonNoiThatController.getDienThoaiKhachHang();
        DiaChiKhachHang = luaChonNoiThatController.getDiaChiKhachHang();
        NgayLapBaoGia = luaChonNoiThatController.getNgayLapBaoGia();
        SanPham = luaChonNoiThatController.getSanPham();
        TableNoiThat = luaChonNoiThatController.getTableNoiThat();
        bangThanhToan = luaChonNoiThatController.getBangThanhToan();
        noteTextArea = luaChonNoiThatController.getNoteTextArea();
        ImageView = luaChonNoiThatController.getImageView();
        this.luaChonNoiThatController = luaChonNoiThatController;
    }

    public void importFile(String directory) {
        File file = new File(directory);
        LOGGER.info("Import file: " + file.getName());
        DataPackage dataPackage = new LuaChonNoiThatService().importFile(file);
        importData(dataPackage);
    }
    private void importThongTinCongTy(ThongTinCongTy thongTinCongTy) {
        if (thongTinCongTy == null) return;
        TenCongTy.setText(thongTinCongTy.getTenCongTy());
        VanPhong.setText(thongTinCongTy.getDiaChiVanPhong());
        DiaChiXuong.setText(thongTinCongTy.getDiaChiXuong());
        DienThoaiCongTy.setText(thongTinCongTy.getSoDienThoai());
        Email.setText(thongTinCongTy.getEmail());

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            IOUtils.copy(thongTinCongTy.getLogo(), byteArrayOutputStream);
            luaChonNoiThatController.setImageStream(byteArrayOutputStream);

            ImageView.setImage(new Image(new ByteArrayInputStream(byteArrayOutputStream.toByteArray())));
        } catch (IOException e) {
            LOGGER.error("Error while importing logo", e);
        }
    }

    /**
     * @param thongTinKhachHang This function will import thong tin khach hang to the table
     */
    private void importThongTinKhachHang(ThongTinKhachHang thongTinKhachHang) {
        if (thongTinKhachHang == null) return;
        TenKhachHang.setText(thongTinKhachHang.getTenKhachHang());
        DienThoaiKhachHang.setText(thongTinKhachHang.getSoDienThoai());
        DiaChiKhachHang.setText(thongTinKhachHang.getDiaChi());
        NgayLapBaoGia.setText(thongTinKhachHang.getDate());
        SanPham.setText(thongTinKhachHang.getSanPham());
    }

    /**
     * @param noteArea This function will import note area to the table
     */
    public void importNoteArea(String noteArea) {
        noteTextArea.setText(noteArea);
    }

    /**
     * @param thongTinThanhToan This function will import bang thanh toan to the table
     */
    public void importBangThanhToan(ThongTinThanhToan thongTinThanhToan) {
        if (thongTinThanhToan == null) return;
        bangThanhToan.getItems().clear();
        bangThanhToan.getItems().add(new BangThanhToan(thongTinThanhToan));
    }
    /**
     * @param thongTinNoiThatList
     * @return
     * @see ThongTinNoiThat
     * This function will import bang noi that to the table
     * @see TreeItem
     */
    public TreeItem<BangNoiThat> importFromThongTinList(List<ThongTinNoiThat> thongTinNoiThatList) {
        TreeItem<BangNoiThat> itemRoot = TableUtils.createRootItem("0", TableNoiThat, bangThanhToan);
        TreeItem<BangNoiThat> lv1Item = null;
        TreeItem<BangNoiThat> lv2Item = null;
        TreeItem<BangNoiThat> lv3Item = null;
        boolean isNewLv1 = false;
        boolean isNewLv2 = false;
        boolean isNewLv3 = false;
        try {
            for (ThongTinNoiThat item : thongTinNoiThatList) {
                String stt = item.getSTT();
                TreeItem<BangNoiThat> tempItem = TableUtils.convertToTreeItem(item);
                if (!Utils.RomanNumber.isRoman(stt) && Utils.isAlpha(stt)) {
                    // Must be alpha numeric
                    if (tempItem != lv1Item) {
                        lv1Item = tempItem;
                        isNewLv1 = true;
                    }
                }
                if (Utils.RomanNumber.isRoman(stt)) {
                    if (tempItem != lv2Item) {
                        lv2Item = tempItem;
                        isNewLv2 = true;
                    }
                }
                if (Utils.isNumeric(stt)) {
                    if (tempItem != lv3Item) {
                        lv3Item = tempItem;
                        isNewLv3 = true;
                    }
                }
                if (isNewLv1) {
                    itemRoot.getChildren().add(lv1Item);
                    isNewLv1 = false;
                }
                if (isNewLv2) {
                    if (lv1Item != null) {
                        lv1Item.getChildren().add(lv2Item);
                        isNewLv2 = false;
                    }
                }
                if (isNewLv3) {
                    if (lv2Item != null) {
                        lv2Item.getChildren().add(lv3Item);
                        isNewLv3 = false;
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error while importing file", e);
            return null;
        }
        return itemRoot;
    }

    public void importData(DataPackage dataPackage) {
        if (dataPackage == null) {
            PopupUtils.throwErrorSignal("File không hợp lệ");
            return;
        }
        if (dataPackage.getThongTinCongTy() != null) {
            importThongTinCongTy(dataPackage.getThongTinCongTy());
        }
        if (dataPackage.getThongTinKhachHang() != null) {
            importThongTinKhachHang(dataPackage.getThongTinKhachHang());
        }
        if (dataPackage.getThongTinThanhToan() != null) {
            importBangThanhToan(dataPackage.getThongTinThanhToan());
        }
        if (dataPackage.getNoteArea() != null) {
            importNoteArea(dataPackage.getNoteArea());
        }
        if (dataPackage.getThongTinNoiThatList() != null) {
            TreeItem<BangNoiThat> itemRoot = importFromThongTinList(dataPackage.getThongTinNoiThatList());
            if (itemRoot == null) {
                PopupUtils.throwErrorSignal("Thông tin nội thất không hợp lệ");
                return;
            }
            TableNoiThat.setRoot(itemRoot);
        }

    }
}
