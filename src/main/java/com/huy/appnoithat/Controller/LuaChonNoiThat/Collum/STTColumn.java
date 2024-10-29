package com.huy.appnoithat.Controller.LuaChonNoiThat.Collum;

import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Utils.ItemTypeUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class STTColumn implements CustomColumn  {
    private final TreeTableColumn<BangNoiThat, String> STT;

    /**
     * Handles the commit event when editing the STT column in the TreeTableView.
     * Calls the 'handleInputedSTT' method to handle the inputted STT value and clears the selection.
     *
     * @param event The CellEditEvent containing information about the edit event.
     */
    public void onEditCommitSTT(TreeTableColumn.CellEditEvent<BangNoiThat, String> event) {
        handleInputedSTT(event);
        event.getTreeTableView().getSelectionModel().clearSelection();
    }

    @Override
    public void setup() {
        // Set up collum for STT
        STT.setCellValueFactory(this::getCustomCellValueFactory);
        STT.setCellFactory(this::getCustomCellFactory);
        STT.setOnEditCommit(this::onEditCommitSTT);
    }


    /**
     * Handles the inputted STT value during editing. Updates the STT property of the BangNoiThat item.
     * (Deprecated methods below handled specific scenarios related to STT input; these are commented out).
     *
     * @param event The CellEditEvent containing information about the edit event.
     */
    private void handleInputedSTT(TreeTableColumn.CellEditEvent<BangNoiThat, String> event) {
        String newValue = event.getNewValue();
        event.getRowValue().getValue().setSTT(newValue);
    }

    /**
     * Provides a custom cell factory for the STT column in the TreeTableView.
     * Customizes the appearance of STT cells based on their content.
     *
     * @param param The TreeTableColumn instance for which the custom cell factory is provided.
     * @return A customized TreeTableCell for the STT column.
     */
    public TreeTableCell<BangNoiThat, String> getCustomCellFactory(TreeTableColumn<BangNoiThat, String> param) {
        return new TreeTableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                    return;
                }
                setText(item);
                TreeTableRow<BangNoiThat> currentRow = getTableRow();
                BangNoiThat bangNoiThat = currentRow.getItem();
                if (bangNoiThat == null) {
                    return;
                }
                ItemTypeUtils.rowStyling(bangNoiThat.getItemType(), currentRow);
            }
        };
    }


    /**
     * Provides a custom cell value factory for the STT column in the TreeTableView.
     * Retrieves the 'STT' property value from the BangNoiThat object associated with the current cell.
     *
     * @param param The CellDataFeatures instance representing the data for the current cell.
     * @return An ObservableValue<String> representing the 'STT' property of the current cell's data.
     * Returns null if the current row's data is null or STT value is empty.
     */
    public ObservableValue<String> getCustomCellValueFactory(TreeTableColumn.CellDataFeatures<BangNoiThat, String> param) {
        if (param.getValue() == null) {
            return null;
        }
        SimpleStringProperty tempSTT = param.getValue().getValue().getSTT();
        return tempSTT.getValue().isEmpty() ? null : tempSTT;
    }
}
