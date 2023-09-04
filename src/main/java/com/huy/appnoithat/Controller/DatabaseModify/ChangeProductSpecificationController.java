package com.huy.appnoithat.Controller.DatabaseModify;

import com.huy.appnoithat.Entity.NoiThat;
import com.huy.appnoithat.Scene.DatabaseModify.DatabaseModifyVatLieuScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ChangeProductSpecificationController {
    @FXML
    private Button backButton;
    @FXML
    private void sceneSwitcher(ActionEvent actionEvent) {
        Scene scene = null;
        Stage stage = null;
        Object source = actionEvent.getSource();
        stage = (Stage) ((Node)source).getScene().getWindow();
        if (source == backButton){
            scene = DatabaseModifyVatLieuScene.getInstance().getScene();
        }
        else {
            return;
        }
        stage.setScene(scene);
        stage.show();
    }

    public void initializeThongSo(int id) {
//        noiThatList.clear();
//        parentID=id;
//        noiThatList = databaseModifyNoiThatService.findNoiThatByID(id);
//        for (NoiThat nt : noiThatList) {
//            listViewNoiThat.getItems().add(new NoiThat(nt.getId(),nt.getName(),nt.getHangMucList()));
//        }
    }

}
