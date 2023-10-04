package com.huy.appnoithat.Controller.LuaChonNoiThat;

import com.huy.appnoithat.Common.KeyboardUtils;
import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Common.Utils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangThanhToan;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Operation.ExportOperation;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Operation.ImportOperation;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Setup.SetupBangNoiThat;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Setup.SetupBangThanhToan;
import com.huy.appnoithat.Enums.Action;
import com.huy.appnoithat.Enums.FileType;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Getter;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
@Getter
public class LuaChonNoiThatController implements Initializable {
    final static Logger LOGGER = LogManager.getLogger(LuaChonNoiThatController.class);
    // Thong Tin ve cong ty
    @FXML
    private TextField TenCongTy, VanPhong, DiaChiXuong, DienThoaiCongTy, Email;
    // Thong tin ve khach hang
    @FXML
    private TextField TenKhachHang, DienThoaiKhachHang, DiaChiKhachHang, NgayLapBaoGia, SanPham;
    // Bang noi that
    @FXML
    private TreeTableView<BangNoiThat> TableNoiThat;
    @FXML
    private TreeTableColumn<BangNoiThat, Double> Cao, Dai, Rong, KhoiLuong;
    @FXML
    private TreeTableColumn<BangNoiThat, Long> DonGia, ThanhTien;
    @FXML
    private TreeTableColumn<BangNoiThat, String> DonVi, HangMuc, VatLieu, STT;
    // Button
    @FXML
    private Button addContinuousButton, addNewButton, ExportButton, SaveButton;
    @FXML
    private ImageView ImageView;
    @FXML
    private TextArea noteTextArea;
    @FXML
    private TableColumn<BangThanhToan, Long> DatCocThiCong30, DatCocThietKe10, HangDenChanCongTrinh50, NghiemThuQuyet;
    @FXML
    private TableView<BangThanhToan> bangThanhToan;
    private ByteArrayOutputStream imageStream;
    public LuaChonNoiThatController() {
        imageStream = new ByteArrayOutputStream();
    }
    @FXML
    void OnMouseClickedHandler(MouseEvent event) {
        Object source = event.getSource();
        if (source == ImageView) {
            imageViewHandler();
        }
    }
    /**
     * @param event
     * @see javafx.scene.input.KeyEvent
     * This function will handle some key pressed event that will trigger clear selection and delete selection
     */
    @FXML
    void onKeyPressed(KeyEvent event) {
        if (KeyboardUtils.isRightKeyCombo(Action.DELETE, event)) {
            handleDeleteAction();
        }
        if (KeyboardUtils.isRightKeyCombo(Action.CLEAR_SELECTION, event)) {
            TableNoiThat.getSelectionModel().clearSelection();
        }
    }
    private void handleDeleteAction() {
        if (TableNoiThat.getSelectionModel().getSelectedItems().isEmpty()) {
            return;
        }
        ObservableList<TreeItem<BangNoiThat>> listItem = TableNoiThat.getSelectionModel().getSelectedItems();
        for (int i = listItem.size() - 1; i >= 0; i--) {
            if (listItem.get(i) == null) continue;
            if (listItem.get(i).getParent() == null) continue;

            listItem.get(i).getParent().getChildren().remove(listItem.get(i));
        }
        TableNoiThat.getSelectionModel().clearSelection();
        reArrangeList();
    }
    private void reArrangeList() {
        ObservableList<TreeItem<BangNoiThat>> listItem = TableNoiThat.getRoot().getChildren();
        for (int i = 0; i < listItem.size(); i++) {
            TreeItem<BangNoiThat> item1 = listItem.get(i);
            for (int j = 0; j < item1.getChildren().size(); j++) {
                TreeItem<BangNoiThat> item2 = item1.getChildren().get(j);
                for (int z = 0; z < item2.getChildren().size(); z++) {
                    TreeItem<BangNoiThat> item3 = item2.getChildren().get(z);
                    item3.getValue().setSTT(String.valueOf(z + 1));
                }
                item2.getValue().setSTT(Utils.RomanNumber.toRoman(j + 1));
            }
            item1.getValue().setSTT(Utils.toAlpha(i + 1));
        }
    }
    /**
     * @param url
     * @param resourceBundle
     * @see Initializable#initialize(URL, ResourceBundle)
     */
    @Override
    public final void initialize(URL url, ResourceBundle resourceBundle) {
        setUpBangThanhToan();
        setUpBangNoiThat();
        setUpButton();
        setUpTruongThongTin();
    }
    /**
     * @param fileType This function will export the table to a file
     */
    public void exportFile(FileType fileType) {
        ExportOperation exportOperation = new ExportOperation(this);
        exportOperation.exportFile(fileType);
    }
    /**
     * @param directory This function will import the table from a file
     */
    public void importFile(String directory) {
        ImportOperation importOperation = new ImportOperation(this);
        importOperation.importFile(directory);
    }
    /**
     * This function will handle the event when user want to choose an image
     */
    private void imageViewHandler() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        if (file == null) return;
        String fileExtension = FilenameUtils.getExtension(file.getName());
        if (!(fileExtension.equals("jpg") || fileExtension.equals("jpeg") || fileExtension.equals("png"))) {
            PopupUtils.throwErrorSignal("File không hợp lệ");
            return;
        }
        try {
            InputStream imageInputStream = new FileInputStream(file);
            imageStream = new ByteArrayOutputStream();
            IOUtils.copy(imageInputStream, imageStream);
            Image image = new Image(new ByteArrayInputStream(imageStream.toByteArray()));
            ImageView.setImage(image);
        } catch (IOException e) {
            LOGGER.error("Error while reading image file", e);
            throw new RuntimeException(e);
        }
    }
    /**
     * This function will set up the bang noi that
     */
    private void setUpBangNoiThat() {
        SetupBangNoiThat setupBangNoiThat = new SetupBangNoiThat(this);
        setupBangNoiThat.setUpBangNoiThat();
    }
    /**
     * This function will set up the collum for KichThuoc
     */
    private void setUpBangThanhToan() {
        SetupBangThanhToan setupBangThanhToan = new SetupBangThanhToan(this);
        setupBangThanhToan.setUpBangThanhToan();
    }

    private void setUpButton() {
        ButtonHandler buttonHandler = new ButtonHandler(this);
        addContinuousButton.setOnAction(buttonHandler::continuousLineAdd);
        ExportButton.setOnAction(buttonHandler::exportButtonHandler);
        SaveButton.setOnAction(buttonHandler::onSaveAction);
    }

    private void setUpTruongThongTin() {
        NgayLapBaoGia.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
    }
    //    private void bindExportButton() {
//        ExportButton.disableProperty().bind(
//            TenCongTy.textProperty().isEmpty().or(
//            VanPhong.textProperty().isEmpty().or(
//            DiaChiXuong.textProperty().isEmpty().or(
//            DienThoaiCongTy.textProperty().isEmpty().or(
//            Email.textProperty().isEmpty().or(
//            TenKhachHang.textProperty().isEmpty().or(
//            DienThoaiKhachHang.textProperty().isEmpty().or(
//            DiaChiKhachHang.textProperty().isEmpty().or(
//            SanPham.textProperty().isEmpty()))))))))
//        );
//    }
}
