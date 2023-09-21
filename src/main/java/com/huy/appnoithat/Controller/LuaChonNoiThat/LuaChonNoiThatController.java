package com.huy.appnoithat.Controller.LuaChonNoiThat;

import com.huy.appnoithat.Controller.LuaChonNoiThat.Cell.CustomNumberCell;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Cell.CustomTextAreaCell;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Collum.HangMucCollumHandler;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Collum.KichThuocHandler;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Collum.STTCollumHandler;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Collum.VatLieuCollumHandler;
import com.huy.appnoithat.Controller.LuaChonNoiThat.CustomConverter.CustomLongStringConverter;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangThanhToan;
import com.huy.appnoithat.DataModel.ThongTinCongTy;
import com.huy.appnoithat.DataModel.ThongTinKhachHang;
import com.huy.appnoithat.DataModel.ThongTinNoiThat;
import com.huy.appnoithat.DataModel.ThongTinThanhToan;
import com.huy.appnoithat.Enums.FileType;
import com.huy.appnoithat.Service.LuaChonNoiThat.LuaChonNoiThatService;
import com.huy.appnoithat.Shared.PopupUtils;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.FloatStringConverter;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class LuaChonNoiThatController implements Initializable {
    final static Logger LOGGER = LogManager.getLogger(LuaChonNoiThatController.class);
    private static final String DEFAULT_IMAGE_PATH = "/com/huy/appnoithat/Scene/icons/blank-user.jpg";
    private int itemCount = 0;
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
    private TreeTableColumn<BangNoiThat, Float> Cao, Dai, Rong, KhoiLuong;
    @FXML
    private TreeTableColumn<BangNoiThat, Long> DonGia, ThanhTien;
    @FXML
    private TreeTableColumn<BangNoiThat, String> DonVi, HangMuc, VatLieu, STT;
    // Button
    @FXML
    private Button deleteButton, addContinuousButton, addNewButton, ExportButton;
    @FXML
    private ImageView ImageView;
    @FXML
    private TextArea noteTextArea;
    @FXML
    private TableColumn<BangThanhToan, Long> DatCocThiCong30, DatCocThietKe10, HangDenChanCongTrinh50, NghiemThuQuyet;
    @FXML
    private TableView<BangThanhToan> bangThanhToan;
    private ByteArrayOutputStream imageStream;
    private final LuaChonNoiThatService luaChonNoiThatService;
    public LuaChonNoiThatController () {
        luaChonNoiThatService = new LuaChonNoiThatService();
    }
    @FXML
    void OnMouseClickedHandler(MouseEvent event) {
        Object source = event.getSource();
        if (source == ImageView) {
            imageViewHandler();
        }
    }

    @FXML
    void onKeyPressed(KeyEvent event) {
//        AnchorPane anchorPane = new AnchorPane();
        if (event.getCode() == KeyCode.DELETE) {
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
        }
        if (event.getCode() == KeyCode.ESCAPE) {
            TableNoiThat.getSelectionModel().clearSelection();
        }
    }
    @Override
    public final void initialize(URL url, ResourceBundle resourceBundle) {
        resizeToFit();
        setUpBangThanhToan();
        setUpBangNoiThat();
        ButtonHandler buttonHandler = new ButtonHandler(TableNoiThat);
        deleteButton.setOnAction(buttonHandler::onDeleteLine);
        addNewButton.setOnAction(buttonHandler::addNewLine);
        addContinuousButton.setOnAction(buttonHandler::continuousLineAdd);
        ExportButton.setOnAction(this::exportButtonHandler);
        ExportButton.disableProperty().bind(
                TenCongTy.textProperty().isEmpty().or(
                VanPhong.textProperty().isEmpty().or(
                DiaChiXuong.textProperty().isEmpty().or(
                DienThoaiCongTy.textProperty().isEmpty().or(
                Email.textProperty().isEmpty().or(
                TenKhachHang.textProperty().isEmpty().or(
                DienThoaiKhachHang.textProperty().isEmpty().or(
                DiaChiKhachHang.textProperty().isEmpty().or(
                SanPham.textProperty().isEmpty()))))))))
        );
        NgayLapBaoGia.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
    }

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

    private void exportButtonHandler(ActionEvent event) {
        File selectedFile = PopupUtils.fileChooser();
        boolean result = luaChonNoiThatService.exportFile(selectedFile,
                FileType.EXCEL,
                getThongTinCongTy(),
                getThongTinKhachHang(),
                getThongTinNoiThatList(),
                getThongTinThanhToan(),
                noteTextArea.getText());
        if (!result) {
            PopupUtils.throwErrorSignal("Xuất file thất bại");
            return;
        }
        PopupUtils.throwSuccessSignal("Xuất file thành công");
    }

    private List<ThongTinNoiThat> getThongTinNoiThatList() {
        // This function will return a list of ThongTinNoiThat
        List<ThongTinNoiThat> listNoiThat = new ArrayList<>();
        // Get a list of phong cach item
        TableNoiThat.getRoot().getChildren().forEach(
                item -> {
                    item.getChildren().forEach(
                            item1 -> {
                                listNoiThat.add(convertFromTreeItem(item1));
                                item1.getChildren().forEach(
                                        item2 -> {
                                            listNoiThat.add(convertFromTreeItem(item2));
                                        }
                                );
                            }
                    );
                }
        );
        return listNoiThat;
    }

    private ThongTinThanhToan getThongTinThanhToan() {
        return new ThongTinThanhToan(
                bangThanhToan.getItems().get(0));
    }

    private ThongTinNoiThat convertFromTreeItem(TreeItem<BangNoiThat> item) {
        return new ThongTinNoiThat(item.getValue());
    }

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
            throw new RuntimeException(e);
        }
    }

    private ThongTinCongTy getThongTinCongTy(){
        return new ThongTinCongTy(
                new ByteArrayInputStream(imageStream.toByteArray()),
                TenCongTy.getText(),
                VanPhong.getText(),
                DiaChiXuong.getText(),
                DienThoaiCongTy.getText(),
                Email.getText()
        );
    }

    private ThongTinKhachHang getThongTinKhachHang() {
        return new ThongTinKhachHang(
                TenKhachHang.getText(),
                DiaChiKhachHang.getText(),
                DienThoaiKhachHang.getText(),
                NgayLapBaoGia.getText(),
                SanPham.getText()
        );
    }

    private void setUpBangNoiThat() {
        setUpCollum();
        TableNoiThat.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        if (TableNoiThat.getRoot() != null) return;
        TreeItem<BangNoiThat> itemRoot = TableUtils.createNewItem("0");
        itemRoot.getValue().getThanhTien().addListener((observableValue, aLong, t1) -> {
            calculateBangThanhToan(t1.longValue());
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

    private void setUpBangThanhToan() {
        DatCocThietKe10.setCellValueFactory(param -> param.getValue().getDatCocThietKe10().asObject());
        DatCocThietKe10.setCellFactory(param -> new TextFieldTableCell<>(new CustomLongStringConverter()));

        DatCocThiCong30.setCellValueFactory(param -> param.getValue().getDatCocThiCong30().asObject());
        DatCocThiCong30.setCellFactory(param -> new TextFieldTableCell<>(new CustomLongStringConverter()));

        HangDenChanCongTrinh50.setCellValueFactory(param -> param.getValue().getHangDenChanCongTrinh50().asObject());
        HangDenChanCongTrinh50.setCellFactory(param -> new TextFieldTableCell<>(new CustomLongStringConverter()));

        NghiemThuQuyet.setCellValueFactory(param -> param.getValue().getNghiemThuQuyet().asObject());
        NghiemThuQuyet.setCellFactory(param -> new TextFieldTableCell<>(new CustomLongStringConverter()));

        if (bangThanhToan.getItems().isEmpty()) {
            bangThanhToan.setItems(FXCollections.observableArrayList(
                    new BangThanhToan(0L, 0L, 0L, 0L)
            ));
        }
    }

    private void calculateBangThanhToan(Long tongTien) {
        Long datCocThietKe10 = (long) (tongTien * 0.1);
        Long datCocThiCong30 = (long) (tongTien * 0.3);
        Long hangDenChanCongTrinh50 = (long) (tongTien * 0.5);
        Long nghiemThuQuyet = tongTien - datCocThietKe10 - datCocThiCong30 - hangDenChanCongTrinh50;

        bangThanhToan.getItems().get(0).setDatCocThietKe10(datCocThietKe10);
        bangThanhToan.getItems().get(0).setDatCocThiCong30(datCocThiCong30);
        bangThanhToan.getItems().get(0).setHangDenChanCongTrinh50(hangDenChanCongTrinh50);
        bangThanhToan.getItems().get(0).setNghiemThuQuyet(nghiemThuQuyet);
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

    private void setUpSTT() {
        STTCollumHandler sttCollumHandler = new STTCollumHandler(TableNoiThat);
        // Set up collum for STT
        STT.setCellValueFactory(sttCollumHandler::getCustomCellValueFactory);
        STT.setCellFactory(sttCollumHandler::getCustomCellFactory);
        STT.setOnEditCommit(sttCollumHandler::onEditCommitSTT);
    }

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
            long thanhTien = TableUtils.calculateThanhTien(khoiLuong, donGia);
            event.getRowValue().getValue().setThanhTien(thanhTien);
            event.getRowValue().getValue().setKhoiLuong(khoiLuong);
            TableUtils.calculateTongTien(event.getRowValue().getParent());
        });
    }

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
            }
            else {
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
            }
            else {
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

    private void setUpDonVi() {
        DonVi.setCellValueFactory(param -> {
            if (param.getValue() == null) return null;
            return param.getValue().getValue().getDonVi();
        });
    }

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
