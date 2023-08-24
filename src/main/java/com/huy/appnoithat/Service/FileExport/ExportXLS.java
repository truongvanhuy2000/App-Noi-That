package com.huy.appnoithat.Service.FileExport;

import com.huy.appnoithat.DataModel.ThongTinCongTy;
import com.huy.appnoithat.DataModel.ThongTinKhachHang;
import com.huy.appnoithat.Entity.PhongCachNoiThat;
import lombok.Getter;
import lombok.Setter;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.jxls.util.Util;

import java.io.*;
import java.util.List;
@Getter
@Setter
public class ExportXLS implements ExportFileService {
    // USED FOR TESTING ONLY
    private static final String DEFAULT_TEMPLATE_PATH = "/home/huy/Project/Java/AppNoiThat/AppNoiThat/template.xlsx";
    private static final String DEFAULT_OUTPUT_PATH = "/home/huy/Desktop/object_collection_output.xls";

    private ThongTinCongTy thongTinCongTy;
    private ThongTinKhachHang thongTinKhachHang;
    private List<PhongCachNoiThat> phongCachNoiThatList;

    private InputStream inputTemplate;
    private OutputStream outputFile;
    public ExportXLS(InputStream inputTemplate, OutputStream outputFile) {
        this.inputTemplate = inputTemplate;
        this.outputFile = outputFile;
    }
    public ExportXLS() {
        try {
            this.inputTemplate = new FileInputStream(DEFAULT_TEMPLATE_PATH);
            this.outputFile = new FileOutputStream(DEFAULT_OUTPUT_PATH);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void export() {

    }

    private void exportThongTinCongTy() {

    }

    private void exportThongTinKhachHang() {

    }

    private void exportNoiThat() {

    }

    private void exportLogo(InputStream image) throws IOException {
        Context context = new Context();
        context.putVar("companyLogo", Util.toByteArray(image));
        JxlsHelper.getInstance().processTemplate(inputTemplate, outputFile, context);
    }

}
