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
    }

    @AfterEach
    void tearDown() {
    }

}