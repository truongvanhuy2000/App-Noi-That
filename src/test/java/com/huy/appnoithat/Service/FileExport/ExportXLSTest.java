package com.huy.appnoithat.Service.FileExport;

import com.huy.appnoithat.Entity.AccountInformation;
import com.huy.appnoithat.Entity.UserSelection;
import com.huy.appnoithat.Service.LuaChonNoiThat.LuaChonNoiThatService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExportXLSTest {
    private ExportXLS exportXLS;
    private List<UserSelection> userSelectionList;
    private List<AccountInformation> accountInformationList;
    private LuaChonNoiThatService luachon;
    @BeforeEach
    void setUp() {
        exportXLS = new ExportXLS();
        luachon = new LuaChonNoiThatService();
        accountInformationList = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void writeInformationToFile() {
        AccountInformation acc = new AccountInformation(1,"huy","nam","ngoclong@gmail.com","hasadasd sadas","0123123123");
        accountInformationList.add(acc);
        assertDoesNotThrow(() -> exportXLS.writeInformationToFile("template.xlsx",accountInformationList));

    }
    @Test
    void writeProductToFile() {
        userSelectionList = luachon.getFakeUserSelection();
        assertDoesNotThrow(() -> exportXLS.writeProductToFile("template.xlsx",userSelectionList));
    }

    @Test
    void customFontExcel() {
    }

    @Test
    void customCellStyle() {
    }
}