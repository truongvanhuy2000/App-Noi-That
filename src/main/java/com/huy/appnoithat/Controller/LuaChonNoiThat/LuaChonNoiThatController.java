package com.huy.appnoithat.Controller.LuaChonNoiThat;

import com.huy.appnoithat.Common.KeyboardUtils;
import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.CommandManager;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangThanhToan;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Operation.ExportOperation;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Operation.ImportOperation;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Setup.SetupBangNoiThat;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Setup.SetupBangThanhToan;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Setup.SetupTruongThongTin;
import com.huy.appnoithat.DataModel.DataPackage;
import com.huy.appnoithat.DataModel.Enums.Action;
import com.huy.appnoithat.DataModel.ThongTinCongTy;
import com.huy.appnoithat.Service.LuaChonNoiThat.LuaChonNoiThatService;
import com.huy.appnoithat.Service.PersistenceStorage.StorageService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
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
    @FXML
    private TextField TenCongTy, VanPhong, DiaChiXuong, DienThoaiCongTy, Email;
    @FXML
    private TextField TenKhachHang, DienThoaiKhachHang, DiaChiKhachHang, NgayLapBaoGia, SanPham;
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
    private Button addContinuousButton, addNewButton, ExportButton, SaveButton, duplicateButton, deleteButton;
    @FXML
    private ImageView ImageView;
    @FXML
    private TextArea noteTextArea;
    @FXML
    private TableColumn<BangThanhToan, Long> DatCocThiCong30, DatCocThietKe10, HangDenChanCongTrinh50, NghiemThuQuyet, TongTien;
    @FXML
    private TableView<BangThanhToan> bangThanhToan;
    @Setter
    private ByteArrayOutputStream imageStream = new ByteArrayOutputStream();
    private final StorageService persistenceStorageService;
    private final LuaChonNoiThatService luaChonNoiThatService;
    private final CommandManager commandManager;

    public LuaChonNoiThatController(
            StorageService persistenceStorageService,
            LuaChonNoiThatService luaChonNoiThatService,
            CommandManager commandManager
    ) {
        this.persistenceStorageService = persistenceStorageService;
        this.luaChonNoiThatService = luaChonNoiThatService;
        this.commandManager = commandManager;
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
            deleteButton.fire();
        } else if (KeyboardUtils.isRightKeyCombo(Action.CLEAR_SELECTION, event)) {
            TableNoiThat.getSelectionModel().clearSelection();
        }
    }

    /**
     * @param event
     * @see javafx.event.ActionEvent
     */

    public void saveNoteArea() {
        String noteArea = noteTextArea.getText();
        persistenceStorageService.saveNoteArea(noteArea);
    }

    public void saveThongTinCongTy() {
        ThongTinCongTy thongTinCongTy = new ThongTinCongTy(
                imageStream.toByteArray(),
                TenCongTy.getText(),
                VanPhong.getText(),
                DiaChiXuong.getText(),
                DienThoaiCongTy.getText(),
                Email.getText()
        );
        persistenceStorageService.saveThongTinCongTy(thongTinCongTy);
    }

    public void undo() {
        commandManager.undo();
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

    public DataPackage exportData() {
        ExportOperation exportOperation = new ExportOperation(this);
        return exportOperation.exportData();
    }

    public void importData(DataPackage dataPackage) {
        ImportOperation importOperation = new ImportOperation(this);
        importOperation.importData(dataPackage);
    }

    /**
     * This function will handle the event when user want to choose an image
     */
    private void imageViewHandler() {
        File file = PopupUtils.fileOpener();
        if (file == null) return;
        String fileExtension = FilenameUtils.getExtension(file.getName());
        if (!(fileExtension.equals("jpg") || fileExtension.equals("jpeg") || fileExtension.equals("png"))) {
            PopupUtils.throwErrorNotification("File không hợp lệ");
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

    /**
     * Sets up event handlers for buttons in the user interface.
     * Associates specific actions with corresponding buttons when clicked.
     */
    private void setUpButton() {
        // Creates a ButtonHandler instance with the current object.
        ButtonHandler buttonHandler = new ButtonHandler(this, commandManager);
        // Assigns the continuousLineAdd method to the addContinuousButton's action event.
        addContinuousButton.setOnAction(buttonHandler::continuousLineAdd);
        duplicateButton.setOnAction(buttonHandler::duplicateButtonHandler);
        duplicateButton.disableProperty().bind(TableNoiThat.getSelectionModel().selectedItemProperty().isNull());
        deleteButton.setOnAction(buttonHandler::handleDeleteAction);
        deleteButton.disableProperty().bind(TableNoiThat.getSelectionModel().selectedItemProperty().isNull());
    }

    /**
     * Sets up initial values for fields related to information fields in the user interface.
     * Populates the NgayLapBaoGia field with the current date in the "dd/MM/yyyy" format.
     */
    private void setUpTruongThongTin() {
        // Formats the current date to "dd/MM/yyyy" format and sets it to NgayLapBaoGia field.
        NgayLapBaoGia.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

        // Creates a SetupTruongThongTin instance with the current object and performs setup operations.
        SetupTruongThongTin setupTruongThongTin = new SetupTruongThongTin(this);
        setupTruongThongTin.setup();
    }

    public void init(Stage stage) {

    }
}
