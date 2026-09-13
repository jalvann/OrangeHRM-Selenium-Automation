package Utilities;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {

    private Workbook workbook;
    private Sheet sheet;

    public ExcelUtility(String filePath, String sheetName) throws IOException {

        FileInputStream fis = new FileInputStream(filePath);

        workbook = new XSSFWorkbook(fis);
        sheet = workbook.getSheet(sheetName);
    }

    // Get number of rows
    public int getRowCount() {
        return sheet.getPhysicalNumberOfRows();
    }

    // Get cell value
    public String getCellData(int row, int column) {

        Cell cell = sheet.getRow(row).getCell(column);

        DataFormatter formatter = new DataFormatter();

        return formatter.formatCellValue(cell);
    }

    public void closeWorkbook() throws IOException {
        workbook.close();
    }
}