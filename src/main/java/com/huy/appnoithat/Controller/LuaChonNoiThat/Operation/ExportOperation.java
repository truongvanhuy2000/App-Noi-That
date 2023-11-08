package com.huy.appnoithat.Controller.LuaChonNoiThat.Operation;

import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.TableUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangThanhToan;
import com.huy.appnoithat.Controller.LuaChonNoiThat.LuaChonNoiThatController;
import com.huy.appnoithat.DataModel.NtFile.DataPackage;
import com.huy.appnoithat.DataModel.ThongTinCongTy;
import com.huy.appnoithat.DataModel.ThongTinKhachHang;
import com.huy.appnoithat.DataModel.ThongTinNoiThat;
import com.huy.appnoithat.DataModel.ThongTinThanhToan;
import com.huy.appnoithat.Enums.FileType;
import com.huy.appnoithat.Service.LuaChonNoiThat.LuaChonNoiThatService;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
public class ExportOperation {
    @NonNull
    private TextField TenCongTy, VanPhong, DiaChiXuong, DienThoaiCongTy, Email;
    @NonNull
    private ImageView ImageView;
    @NonNull
    private TextField TenKhachHang, DienThoaiKhachHang, DiaChiKhachHang, NgayLapBaoGia, SanPham;
    @NonNull
    private TreeTableView<BangNoiThat> TableNoiThat;
    @NonNull
    private TableView<BangThanhToan> bangThanhToan;
    @NonNull
    private TextArea noteTextArea;
    private StackPane loadingPane;
    LuaChonNoiThatController luaChonNoiThatController;
    public ExportOperation(LuaChonNoiThatController luaChonNoiThatController) {
        TenCongTy = luaChonNoiThatController.getTenCongTy();
        VanPhong = luaChonNoiThatController.getVanPhong();
        DiaChiXuong = luaChonNoiThatController.getDiaChiXuong();
        DienThoaiCongTy = luaChonNoiThatController.getDienThoaiCongTy();
        Email = luaChonNoiThatController.getEmail();
        ImageView = luaChonNoiThatController.getImageView();
        TenKhachHang = luaChonNoiThatController.getTenKhachHang();
        DienThoaiKhachHang = luaChonNoiThatController.getDienThoaiKhachHang();
        DiaChiKhachHang = luaChonNoiThatController.getDiaChiKhachHang();
        NgayLapBaoGia = luaChonNoiThatController.getNgayLapBaoGia();
        SanPham = luaChonNoiThatController.getSanPham();
        TableNoiThat = luaChonNoiThatController.getTableNoiThat();
        bangThanhToan = luaChonNoiThatController.getBangThanhToan();
        noteTextArea = luaChonNoiThatController.getNoteTextArea();
        loadingPane = luaChonNoiThatController.getLoadingPane();
        this.luaChonNoiThatController = luaChonNoiThatController;
    }
    private void showLoading() {
        loadingPane.setVisible(true);
        loadingPane.setDisable(false);
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        progressIndicator.setMinHeight(200);
        progressIndicator.setMinWidth(200);
        Label textField = new Label("Đang xuất tệp...");
        textField.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-font-family: 'Segoe UI';");
        loadingPane.getChildren().addAll(progressIndicator, textField);
        loadingPane.toFront();
    }
    private void hideLoading(Boolean result, boolean showPopup, FileType fileType) {
        Platform.runLater(() -> {
            loadingPane.setVisible(false);
            loadingPane.setDisable(true);
            loadingPane.getChildren().clear();
            showResult(result, showPopup, fileType);
        });
    }
    private void showResult(Boolean result, boolean showPopup, FileType fileType) {
        if (!result) {
            PopupUtils.throwErrorSignal("Xuất file thất bại");
        }
        else {
            PopupUtils.throwSuccessSignal("Xuất file thành công");
        }
    }
    public String exportFile(FileType fileType) {
        File selectedFile = PopupUtils.fileSaver();
        if (selectedFile == null) {
            return null;
        }
        return exportFile(fileType, selectedFile, true);
    }
    public String exportFile(FileType fileType, File selectedFile, boolean showPopup) {
        DataPackage dataPackage = exportData();
        showLoading();
        new LuaChonNoiThatService().exportFile(selectedFile, fileType, dataPackage, (exportResult) -> {
            hideLoading(exportResult, showPopup, fileType);
        });
        return selectedFile.getAbsolutePath();
    }

    /**
     * THis function will return a list of ThongTinNoiThat from item root from the table
     *
     * @return
     */
    private List<ThongTinNoiThat> getThongTinNoiThatList() {
        // This function will return a list of ThongTinNoiThat
        List<ThongTinNoiThat> listNoiThat = new ArrayList<>();
        // Get a list of phong cach item
        TableNoiThat.getRoot().getChildren().forEach(
                item -> {
                    listNoiThat.add(TableUtils.convertFromTreeItem(item));
                    item.getChildren().forEach(
                            item1 -> {
                                listNoiThat.add(TableUtils.convertFromTreeItem(item1));
                                item1.getChildren().forEach(
                                        item2 -> {
                                            listNoiThat.add(TableUtils.convertFromTreeItem(item2));
                                        }
                                );
                            }
                    );
                }
        );
        return listNoiThat;
    }

    /**
     * This function will return ThongTinThanhToan from bang thanh toan
     *
     * @return
     */
    private ThongTinThanhToan getThongTinThanhToan() {
        return new ThongTinThanhToan(
                bangThanhToan.getItems().get(0));
    }

    /**
     * This function will return ThongTinCongTy from thong tin cong ty text field
     *
     * @return
     */
    private ThongTinCongTy getThongTinCongTy() {

        return new ThongTinCongTy(
                new ByteArrayInputStream(luaChonNoiThatController.getImageStream().toByteArray()),
                TenCongTy.getText(),
                VanPhong.getText(),
                DiaChiXuong.getText(),
                DienThoaiCongTy.getText(),
                Email.getText()
        );
    }
    /**
     * This function will return ThongTinKhachHang from thong tin khach hang text field
     *
     * @return
     */
    private ThongTinKhachHang getThongTinKhachHang() {
        return new ThongTinKhachHang(
                TenKhachHang.getText(),
                DiaChiKhachHang.getText(),
                DienThoaiKhachHang.getText(),
                NgayLapBaoGia.getText(),
                SanPham.getText()
        );
    }

    public DataPackage exportData() {
        return new DataPackage(
                getThongTinCongTy(),
                getThongTinKhachHang(),
                noteTextArea.getText(),
                getThongTinNoiThatList(),
                getThongTinThanhToan()
        );
    }
}
