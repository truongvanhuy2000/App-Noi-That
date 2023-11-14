package com.huy.appnoithat.Service.FileExport.Operation.PDF;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExportPDFTest {
    ExportPDF exportPDF = new ExportPDF();
    @Test
    void export() {
        exportPDF.convertXLStoPDF("");
    }
}