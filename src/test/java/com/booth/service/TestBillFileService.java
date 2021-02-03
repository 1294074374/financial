package com.booth.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;

/**
 * @author laijunlin
 * @date 2021-01-31 14:43
 */
public class TestBillFileService {
    private static String openId = "ojSJU0aAiUuNWpOqUBBeV5aicvgM";
    private static String filePath = "D:\\账单.xlsx";

    public static void main(String[] args) {
        TestBillFileService testBillFileService  =  new TestBillFileService();
        testBillFileService.loadBillFile();
    }

    public String loadBillFile() {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        File file = new File(filePath);
        FileInputStream fileInputStream = null;
        try{
            fileInputStream = new FileInputStream(file);
            XSSFWorkbook workBook = new XSSFWorkbook(fileInputStream);

            XSSFSheet sheet = workBook.getSheetAt(1);
            int rowNumber = sheet.getPhysicalNumberOfRows();
            System.out.println("excel文件一共" + rowNumber + "行");
            for (int i = 1; i < rowNumber; i++) {
                int cellNumber = sheet.getRow(i).getPhysicalNumberOfCells();
                StringBuffer data = new StringBuffer();
                XSSFRow row = sheet.getRow(i);
                for (int j = 0; j < cellNumber; j++) {
                    Object cellValue = "";
                    if (row.getCell(j) != null) {
                        //必须使用switch来获取单元格数据，否则会报错，除非确保execl所有单元格的数据类型一致
                        switch (row.getCell(j).getCellType()) {
                            //获取布尔型数据
                            case Cell.CELL_TYPE_BOOLEAN:
                                cellValue = row.getCell(j).getBooleanCellValue();
                                break;//获取空值
                            case Cell.CELL_TYPE_BLANK:
                                cellValue = row.getCell(j).getBooleanCellValue();
                                break;
                            //获取错误单元格
                            case Cell.CELL_TYPE_ERROR:
                                cellValue = row.getCell(j).getErrorCellString();
                                break;
                            //获取数字类型的数据
                            case Cell.CELL_TYPE_NUMERIC:
                                Cell cell = row.getCell(j);
                                if(DateUtil.isCellDateFormatted(cell)) {
                                    cellValue = fmt.format(cell.getDateCellValue()); //日期型
                                }else{
                                    cellValue = row.getCell(j).getNumericCellValue();
                                }
                                break;
                            //获取字符串
                            case Cell.CELL_TYPE_STRING:
                                cellValue = row.getCell(j).getStringCellValue();
                                break;
                            default:
                                cellValue = "";
                                break;
                        }

                    } else {
                        cellValue = "";
                    }
                    System.out.println(cellValue);
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    public String readExcel()
    {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            //同时支持Excel 2003、2007
            File excelFile = new File(filePath); //创建文件对象
            FileInputStream is = new FileInputStream(excelFile); //文件流
            Workbook workbook = WorkbookFactory.create(is); //这种方式 Excel 2003/2007/2010 都是可以处理的
            int sheetCount = workbook.getNumberOfSheets();  //Sheet的数量
            //遍历每个Sheet
            for (int s = 1; s < sheetCount; s++) {
                Sheet sheet = workbook.getSheetAt(s);
                int rowCount = sheet.getPhysicalNumberOfRows(); //获取总行数
                //遍历每一行
                for (int r = 0; r < rowCount; r++) {
                    Row row = sheet.getRow(r);
                    int cellCount = row.getPhysicalNumberOfCells(); //获取总列数
                    //遍历每一列
                    for (int c = 0; c < cellCount; c++) {
                        Cell cell = row.getCell(c);
                        int cellType = cell.getCellType();
                        String cellValue = null;
                        switch(cellType) {
                            case Cell.CELL_TYPE_STRING: //文本
                                cellValue = cell.getStringCellValue();
                                break;
                            case Cell.CELL_TYPE_NUMERIC: //数字、日期
                                if(DateUtil.isCellDateFormatted(cell)) {
                                    cellValue = fmt.format(cell.getDateCellValue()); //日期型
                                }
                                else {
                                    cellValue = String.valueOf(cell.getNumericCellValue()); //数字
                                }
                                break;
                            case Cell.CELL_TYPE_BOOLEAN: //布尔型
                                cellValue = String.valueOf(cell.getBooleanCellValue());
                                break;
                            case Cell.CELL_TYPE_BLANK: //空白
                                cellValue = cell.getStringCellValue();
                                break;
                            case Cell.CELL_TYPE_ERROR: //错误
                                cellValue = "错误";
                                break;
                            case Cell.CELL_TYPE_FORMULA: //公式
                                cellValue = "错误";
                                break;
                            default:
                                cellValue = "错误";
                        }
                        System.out.print(cellValue + "    ");
                    }
                    System.out.println();
                }
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}
