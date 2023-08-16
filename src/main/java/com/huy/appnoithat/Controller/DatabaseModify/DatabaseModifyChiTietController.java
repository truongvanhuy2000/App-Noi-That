package com.huy.appnoithat.Controller.DatabaseModify;

import com.huy.appnoithat.Scene.DatabaseModify.ChangeProductSpecificationScene;
import com.huy.appnoithat.Scene.DatabaseModify.DatabaseModifyHangMucScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class DatabaseModifyChiTietController implements Initializable {

    String[] items = {"- Thùng khung :  nhựa Picomat chống nước tuyệt đối độ dày 18mm","-Cánh : MDF chống ẩm an cường  soi CNC sơn inchem 5 lớp mỹ độ dày  cánh 25mm","-Hậu  Tấm nhôm Alu 3 ly"};

    @FXML
    private Button EditChiTietButton;

    @FXML
    private Button addChiTietButton;

    @FXML
    private Button clearTextbtn;

    @FXML
    private Button deleteChiTietButton;

    @FXML
    private ListView<String> listViewChiTiet;

    @FXML
    private Button nextScreenButton;

    @FXML
    private TextField txtChiTiiet;

    @FXML
    void AddNewChiTiet(ActionEvent event) {
        try {
            Dialog dialog = new Dialog();
            String txt = txtChiTiiet.getText().trim().toUpperCase();
            boolean hasDuplicate = false;
            if(!txt.isEmpty()){
                List<String> array = listViewChiTiet.getItems().stream().filter(e->e.equals(txt)).collect(Collectors.toList());
                for (int i = 0; i < array.size(); i++) {
                    if(array.get(i).trim().equals(txt.toUpperCase())){
                        hasDuplicate = true;
                    }
                }
                if(hasDuplicate){
                    dialog.setTitle("ADD DUPLICATE ERROR");
                    dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                    dialog.getDialogPane().setContentText("cannot add duplicate element !!!");
                    dialog.show();
                }
                if(!hasDuplicate){
                    listViewChiTiet.getItems().add(txt);
                    txtChiTiiet.setText("");
                }

            }else{

                dialog.setTitle("ADD ERROR");
                dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                dialog.getDialogPane().setContentText("please input something !!!");
                dialog.show();
            }

        }catch (Exception e){
            System.out.println("co loi add roi dai ca");
        }
    }

    @FXML
    void ClearText(ActionEvent event) {
        txtChiTiiet.setText("");
    }

    @FXML
    void DeleteChiTiet(ActionEvent event) {
        try {
            Alert deleteDialog = new Alert(Alert.AlertType.CONFIRMATION);
            Dialog dialog = new Dialog();
            int selectIndex = listViewChiTiet.getSelectionModel().getSelectedIndex();
            if(selectIndex>=0){
                deleteDialog.setTitle("Delete Confirmation");
                deleteDialog.setHeaderText("Are you sure you want to delete this item?");
                deleteDialog.setContentText("This action cannot be undone.");
                // Add "Yes" button
                deleteDialog.getDialogPane().getButtonTypes().add(ButtonType.YES);
                deleteDialog.getDialogPane().getButtonTypes().remove(ButtonType.OK);
                // Show the dialog and wait for user interaction
                ButtonType result = deleteDialog.showAndWait().orElse(ButtonType.CANCEL);
                // Handle the user's choice
                if (result == ButtonType.YES) {
                    listViewChiTiet.getItems().remove(selectIndex);
                }
            }else{
                dialog.setTitle("DELETE ERROR");
                dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                dialog.getDialogPane().setContentText("Please select one item to delete !!!");
                dialog.show();
            }
        }catch (Exception e){
            System.out.println("Something went wrong.");
        }
    }

    @FXML
    void EditChiTiet(ActionEvent event) {
        try {
            Dialog dialog = new Dialog();

            String txt = txtChiTiiet.getText().trim().toUpperCase();
            int selectIndex = listViewChiTiet.getSelectionModel().getSelectedIndex();
            if(selectIndex<=0){
                dialog.setTitle("EDIT ERROR");
                dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                dialog.getDialogPane().setContentText("Please select one item to edit !!!");
                dialog.show();
            }else if(txt.isEmpty()){
                dialog.setTitle("EDIT ERROR");
                dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                dialog.getDialogPane().setContentText("Cannot edit with text empty !!!");
                dialog.show();
            }else{
                listViewChiTiet.getItems().set(selectIndex, txtChiTiiet.getText());
            }
        }catch (Exception e){
            System.out.println("co loi roi dai ca oi");
        }
    }

    @FXML
    void NextScreen(ActionEvent event) {
        Scene scene = null;
        Stage stage = null;
        Object source = event.getSource();
        stage = (Stage) ((Node)source).getScene().getWindow();
        if (source == nextScreenButton){
            scene = ChangeProductSpecificationScene.getInstance().getScene();
        }else {
            return;
        }
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void tableClickToSelectItem(MouseEvent event) {
        String selectedItem = listViewChiTiet.getSelectionModel().getSelectedItem();
        txtChiTiiet.setText(selectedItem);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listViewChiTiet.getItems().addAll(items);
    }
}
