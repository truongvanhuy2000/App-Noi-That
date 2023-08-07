package com.huy.appnoithat.Controller;

import com.huy.appnoithat.Entity.PhongCachNoiThat;
import com.huy.appnoithat.Scene.HomeScene;
import com.huy.appnoithat.Service.LuaChonNoiThat.LuaChonNoiThatService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


public class LuaChonNoiThatController implements Initializable {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class BangNoiThat {
        private int id;
        private String PhongCach;
        private Float Cao;
        private Float Dai;
        private Float Rong;
        private Long DonGia;
        private String DonVi;
        private String HangMuc;
        private String NoiThat;
        private String VatLieu;
        private Long ThanhTien;
        private Float SoLuong;
    }

    @FXML
    private TableView<BangNoiThat> TableNoiThat;
    @FXML
    private TableColumn<BangNoiThat, Float> Cao, Dai, Rong, SoLuong;
    @FXML
    private TableColumn<BangNoiThat, Long> DonGia, ThanhTien;
    @FXML
    private TableColumn<BangNoiThat, String> DonVi, HangMuc, NoiThat, PhongCach, VatLieu;
    @FXML
    private TableColumn<BangNoiThat, Integer> id;
    @FXML
    private Button BackButton;

    private int current_id = 0;
    List<PhongCachNoiThat> listPhongCachNoiThat;
    private final LuaChonNoiThatService luaChonNoiThatService;
    private ObservableList<BangNoiThat> list = FXCollections.observableArrayList();
    public LuaChonNoiThatController() {
        luaChonNoiThatService = new LuaChonNoiThatService();
    }
    // Call this method everytime you switch scene

    public void initialize() {
    }
    public void setUpTable(){
        TableNoiThat.setEditable(true);

        PhongCach.setCellValueFactory(new PropertyValueFactory<>("PhongCach"));
        PhongCach.setCellFactory(column -> new ComboboxCell());

        Cao.setCellValueFactory(new PropertyValueFactory<>("Cao"));
        Dai.setCellValueFactory(new PropertyValueFactory<>("Dai"));
        Rong.setCellValueFactory(new PropertyValueFactory<>("Rong"));
        DonGia.setCellValueFactory(new PropertyValueFactory<>("DonGia"));
        DonVi.setCellValueFactory(new PropertyValueFactory<>("DonVi"));
        HangMuc.setCellValueFactory(new PropertyValueFactory<>("HangMuc"));
        NoiThat.setCellValueFactory(new PropertyValueFactory<>("NoiThat"));
        VatLieu.setCellValueFactory(new PropertyValueFactory<>("VatLieu"));
        ThanhTien.setCellValueFactory(new PropertyValueFactory<>("ThanhTien"));
        SoLuong.setCellValueFactory(new PropertyValueFactory<>("SoLuong"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableNoiThat.setItems(list);
    }
    @Override
    public final void initialize(URL url, ResourceBundle resourceBundle) {
        setUpTable();
        listPhongCachNoiThat = luaChonNoiThatService.findAllPhongCachNoiThat();
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
    void addNewLine(ActionEvent event) {
        current_id += 1;
        list.add(new BangNoiThat(current_id, "", 0f, 0f, 0f, 0L, "", "", "", "", 0L, 0f));
    }

    public List<String> getObjectNameList(List<?> list){
        return list.stream().map(Object::toString).collect(Collectors.toList());
    }

//    private void
    public class ComboboxCell extends TableCell<BangNoiThat, String> {
        private ComboBox<String> comboBox;
        ObservableList<String> dropDownData = FXCollections.observableArrayList();
        @Override
        public void startEdit() {
            if (isEmpty()){
                return;
            }
            super.startEdit();
            createComboBox();
            setText(null);
            setGraphic(comboBox);
        }
//        @Override
//        public void updateItem(String item, boolean empty) {
//            super.updateItem(item, empty);
//
//            if (empty) {
//                setText(null);
//                setGraphic(null);
//            } else {
//                if (isEditing()) {
//                    if (comboBox != null) {
//                        comboBox.setValue(getTyp());
//                    }
//                    setText(getTyp().getTyp());
//                    setGraphic(comboBox);
//                } else {
//                    setText(getTyp().getTyp());
//                    setGraphic(null);
//                }
//            }
//        }
        @Override
        public void cancelEdit() {
            super.cancelEdit();
//            setText(getTyp().getTyp());
            setGraphic(null);
        }
        private void createComboBox() {
            comboBox = new ComboBox<>(dropDownData);
//            comboBoxConverter(comboBox);
//            comboBox.valueProperty().set(getTyp());
            comboBox.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            comboBox.setOnAction((e) -> {
                System.out.println("Committed: " + comboBox.getSelectionModel().getSelectedItem());
                commitEdit(comboBox.getSelectionModel().getSelectedItem());
            });
    //            comboBox.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
    //                if (!newValue) {
    //                    commitEdit(comboBox.getSelectionModel().getSelectedItem());
    //                }
    //            });
        }
    }
}
