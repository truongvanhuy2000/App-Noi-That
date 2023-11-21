package com.huy.appnoithat.Service.FileExport.Operation.PDF;

import com.huy.appnoithat.Service.LuaChonNoiThat.FileExport.Operation.PDF.ExportPDF;
import org.junit.jupiter.api.Test;

class ExportPDFTest {
    ExportPDF exportPDF = new ExportPDF();
    @Test
    void export() {
        exportPDF.convertXLStoPDF("");
    }
}