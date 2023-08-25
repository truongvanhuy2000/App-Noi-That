package com.huy.appnoithat.Service.FileExport;

import com.huy.appnoithat.DataModel.Employee;
import com.huy.appnoithat.DataModel.ThongTinCongTy;
import com.huy.appnoithat.DataModel.ThongTinKhachHang;
import com.huy.appnoithat.Entity.PhongCachNoiThat;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFDrawing;

import java.io.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class ExportXLS implements ExportFileService {
    // USED FOR TESTING ONLY
    private static final String DEFAULT_TEMPLATE_PATH = "/test.xls";
    private static final String DEFAULT_OUTPUT_PATH = "/home/huy/Desktop/object_collection_output.xls";

    private ThongTinCongTy thongTinCongTy;
    private ThongTinKhachHang thongTinKhachHang;
    private List<PhongCachNoiThat> phongCachNoiThatList;

    private InputStream inputTemplate;
    private OutputStream outputFile;
    private XSSFWorkbook workbook;
    private XSSFSheet spreadsheet;
    public ExportXLS(InputStream inputTemplate, OutputStream outputFile) {
        this.inputTemplate = inputTemplate;
        this.outputFile = outputFile;
    }
    public ExportXLS() {
        try {
            this.inputTemplate = new FileInputStream(Objects.requireNonNull(ExportXLS.class.getResource(DEFAULT_TEMPLATE_PATH)).getFile());
            this.outputFile = new FileOutputStream(DEFAULT_OUTPUT_PATH);
            workbook = new XSSFWorkbook(inputTemplate);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        spreadsheet = workbook.getSheet("Sheet1");
    }

    @Override
    public void export() {
    }

    private void exportThongTinCongTy() {

    }

    public void exportThongTinKhachHang(List<Employee> employees) throws IOException {
    }

    private void exportNoiThat() {

    }

    public void exportLogo(InputStream image) throws IOException {
        byte[] bytes = image.readAllBytes();
        int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
        XSSFDrawing drawing = (XSSFDrawing) spreadsheet.createDrawingPatriarch();
        XSSFClientAnchor logoAnchor = new XSSFClientAnchor();

        logoAnchor.setCol1(0);
        image.close();
    }
}
