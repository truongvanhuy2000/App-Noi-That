package com.huy.appnoithat.Controller.DatabaseModify;

import com.huy.appnoithat.Scene.DatabaseModify.DatabaseModifyChiTietScene;
import com.huy.appnoithat.Scene.DatabaseModify.DatabaseModifyHangMucScene;
import com.huy.appnoithat.Scene.DatabaseModify.DatabaseModifyPhongCachScene;
import com.huy.appnoithat.Scene.HomeScene;
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

public class DatabaseModifyHangMucController implements Initializable {

    String[] items = {"Tủ bếp dưới","Tủ bếp trên","Tủ  trên tầng 2","Tủ trang trí"};

    @FXML
    private Button EditHangMucButton;

    @FXML
    private Button addHangMucButton;

    @FXML
    private Button clearTextbtn;

    @FXML
    private Button deleteHangMucButton;

    @FXML
    private ListView<String> listViewHangMuc;

    @FXML
    private Button nextScreenButton;

    @FXML
    private TextField txtHangMuc;
    @FXML
    private Button backButton;

    @FXML
    void AddNewHangMuc(ActionEvent event) {
        try {
            Dialog dialog = new Dialog();
            String txt = txtHangMuc.getText().trim().toUpperCase();
            boolean hasDuplicate = false;
            if(!txt.isEmpty()){
                List<String> array = listViewHangMuc.getItems().stream().filter(e->e.equals(txt)).collect(Collectors.toList());
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
                    listViewHangMuc.getItems().add(txt);
                    txtHangMuc.setText("");
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
        txtHangMuc.setText("");
    }

    @FXML
    void DeleteHangMuc(ActionEvent event) {
        try {
            Alert deleteDialog = new Alert(Alert.AlertType.CONFIRMATION);
            Dialog dialog = new Dialog();
            int selectIndex = listViewHangMuc.getSelectionModel().getSelectedIndex();
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
                    listViewHangMuc.getItems().remove(selectIndex);
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
    void EditHangMuc(ActionEvent event) {
        try {
            Dialog dialog = new Dialog();

            String txt = txtHangMuc.getText().trim().toUpperCase();
            int selectIndex = listViewHangMuc.getSelectionModel().getSelectedIndex();
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
                listViewHangMuc.getItems().set(selectIndex, txtHangMuc.getText());
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
            scene = DatabaseModifyChiTietScene.getInstance().getScene();
        }else {
            return;
        }
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void tableClickToSelectItem(MouseEvent event) {
        String selectedItem = listViewHangMuc.getSelectionModel().getSelectedItem();
        txtHangMuc.setText(selectedItem);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listViewHangMuc.getItems().addAll(items);
    }

    @FXML
    private void sceneSwitcher(ActionEvent actionEvent) {
        Scene scene = null;
        Stage stage = null;
        Object source = actionEvent.getSource();
        stage = (Stage) ((Node)source).getScene().getWindow();
        if (source == backButton){
            scene = DatabaseModifyPhongCachScene.getInstance().getScene();
        }
        else {
            return;
        }
        stage.setScene(scene);
        stage.show();
    }
}
