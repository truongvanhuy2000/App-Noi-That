package com.huy.appnoithat.Controller;

import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Common.Utils;
import com.huy.appnoithat.DataModel.PricingModelDTO;
import com.huy.appnoithat.Service.RestService.PricingModelRestService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import lombok.Data;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class GlobalSettingController implements Initializable {
    @FXML
    private CheckBox ActivateCheckBox;

    @FXML
    private TableColumn<PricingOption, Integer> BonusMonthColumn;

    @FXML
    private TableColumn<PricingOption, Integer> MonthOptionColumn;

    @FXML
    private TableColumn<PricingOption, Long> MonthlyPriceColumn;
    @FXML
    private TableView<PricingOption> PricingTable;
    @FXML
    private Button UpdateButton;
    @FXML
    private Button AddNewRowBtn;
    @FXML
    private Button DeleteRowBtn;

    private final PricingModelRestService pricingModelRestService;

    public GlobalSettingController(PricingModelRestService pricingModelRestService) {
        this.pricingModelRestService = pricingModelRestService;
    }

    public GlobalSettingController() {
        pricingModelRestService = new PricingModelRestService();
    }

    public void init() {
        refresh();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AddNewRowBtn.setOnAction(this::onClickAddNewRowBtn);

        DeleteRowBtn.disableProperty().bind(PricingTable.getSelectionModel().selectedItemProperty().isNull());
        DeleteRowBtn.setOnAction(this::onClickDeleteRowBtn);

        PricingTable.disableProperty().bind(ActivateCheckBox.selectedProperty().not());

        MonthOptionColumn.setCellValueFactory(param -> param.getValue().getMonthOption().asObject());
        MonthOptionColumn.setCellFactory(param -> new TextFieldTableCell<>(new IntegerStringConverter()));

        MonthlyPriceColumn.setCellValueFactory(param -> param.getValue().getMonthlyPriceColumn().asObject());
        MonthlyPriceColumn.setCellFactory(param -> new TextFieldTableCell<>(new PricingConverter()));

        BonusMonthColumn.setCellValueFactory(param -> param.getValue().getBonusMonthColumn().asObject());
        BonusMonthColumn.setCellFactory(param -> new TextFieldTableCell<>(new IntegerStringConverter()));

        UpdateButton.setOnAction(this::onClickUpdateButton);
    }

    private void onClickDeleteRowBtn(ActionEvent actionEvent) {
        PricingOption pricingOption = PricingTable.getSelectionModel().getSelectedItem();
        if (pricingOption == null) {
            return;
        }
        PricingTable.getItems().remove(pricingOption);
    }

    private void onClickAddNewRowBtn(ActionEvent actionEvent) {
        PricingTable.getItems().add(new PricingOption());
    }

    private void onClickUpdateButton(ActionEvent actionEvent) {
        List<PricingOption> pricingOptionList = PricingTable.getItems();
        PricingModelDTO pricingModelDTO = PricingModelDTO.builder()
                .active(ActivateCheckBox.isSelected())
                .pricingModelList(pricingOptionList.stream().map((item) -> PricingModelDTO.PricingModel.builder()
                        .bonusMonth(item.BonusMonthColumn.get())
                        .monthOption(item.MonthOption.get())
                        .price(item.MonthlyPriceColumn.get())
                        .build()).toList())
                .build();
        pricingModelRestService.setPricingModel(pricingModelDTO);
        refresh();
    }

    private void refresh() {
        PricingModelDTO pricingModelDTO = pricingModelRestService.getPricingModel();
        ActivateCheckBox.setSelected(pricingModelDTO.isActive());
        pricingModelDTO.getPricingModelList().sort(Comparator.comparingLong((PricingModelDTO.PricingModel::getMonthOption)));
        List<PricingOption> pricingOptionList = pricingModelDTO.getPricingModelList().stream().map((item) ->
                new PricingOption(item.getMonthOption(), item.getPrice(), item.getBonusMonth())).toList();

        ObservableList<PricingOption> observableList = PricingTable.getItems();
        if (observableList == null) {
            observableList = FXCollections.observableArrayList();
        }
        observableList.clear();
        observableList.addAll(pricingOptionList);
    }

    @Data
    public static class PricingOption {
        private SimpleIntegerProperty MonthOption;
        private SimpleLongProperty MonthlyPriceColumn;
        private SimpleIntegerProperty BonusMonthColumn;

        public PricingOption() {
            MonthlyPriceColumn = new SimpleLongProperty(0L);
            BonusMonthColumn = new SimpleIntegerProperty(0);
            MonthOption = new SimpleIntegerProperty(0);
        }

        public PricingOption(Integer monthOption, Long monthlyPriceColumn, Integer bonusMonthColumn) {
            MonthOption = new SimpleIntegerProperty(monthOption);
            MonthlyPriceColumn = new SimpleLongProperty(monthlyPriceColumn);
            BonusMonthColumn = new SimpleIntegerProperty(bonusMonthColumn);
        }
    }
    public static class PricingConverter extends StringConverter<Long> {
        @Override
        public String toString(Long aLong) {
            try {
                return Utils.convertLongToDecimal(aLong);
            } catch (Exception e) {
                PopupUtils.throwErrorNotification("Nhập sai định dạng số");
                return "0";
            }
        }

        @Override
        public Long fromString(String s) {
            try {
                return Utils.convertDecimalToLong(s);
            } catch (Exception e) {
                PopupUtils.throwErrorNotification("Nhập sai định dạng số");
                return 0L;
            }
        }
    }
}
