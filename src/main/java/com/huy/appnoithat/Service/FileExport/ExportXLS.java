package com.huy.appnoithat.Service.FileExport;

import com.huy.appnoithat.Entity.Account;

import java.util.ArrayList;
import java.util.List;

public class ExportXLS implements ExportFileService {


    @Override
    public void readExcelData(String fileName) {
        List<Account> listacc = new ArrayList<Account>();
        Workbook workbook = null;
    }
}
