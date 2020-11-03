package com.hd.test.excel;

import com.hd.test.common.ObjectUtils;
import com.hd.test.common.StringUtils;
import com.sun.istack.internal.NotNull;
import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.stream.FileImageOutputStream;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

/**
 * @auther houdu
 * @date 2020/7/13
 */
public class ExcelReadUtil {

    /**
     * 文件是否为excel
     *
     * @param fileName 文件名
     * @return 是excel
     */
    public static boolean isExcel(String fileName) {
        return StringUtils.isNotEmpty(fileName) && (fileName.matches("^.+\\.(?i)(xlsx)$") || fileName.matches("^.+\\.(?i)(xls)$"));
    }

    /**
     * 是否为 excel 2003
     *
     * @param fileName 文件名
     * @return 是
     */
    public static boolean isExcel2003(String fileName) {
        return fileName.matches("^.+\\.(?i)(xls)$") ? true : false;
    }


    /**
     * 读取Excel
     *
     * @param mFile    文件
     * @param function 执行逻辑
     * @param <E>      返回值
     * @return 解析列表
     * @throws IOException IO异常
     */
    public static <E> List<E> readExcelValue(MultipartFile mFile, BiFunction<Row, Integer, E> function) throws IOException {
        String fileName = mFile.getOriginalFilename();// 获取文件名
        if (!isExcel(fileName)) {// 验证文件名是否合格
            return null;
        }

        InputStream is = mFile.getInputStream();

        Workbook wb;
        if (isExcel2003(fileName)) {// 当excel是2003时,创建excel2003
            wb = new HSSFWorkbook(is);
        } else {// 当excel是2007时,创建excel2007
            wb = new XSSFWorkbook(is);
        }

        int totalRows = 0;  // 行数
        int totalCells = 0; // 列数
        // 得到第一个shell
        Sheet sheet = wb.getSheetAt(0);
        // 得到Excel的行数
        totalRows = sheet.getPhysicalNumberOfRows();
        // 得到Excel的列数(前提是有行数)
        if (totalRows > 1 && sheet.getRow(0) != null) {
            totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }

        // 循环Excel行数
        List<E> list = new ArrayList<>();
        for (int r = 1; r < totalRows; r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            // 循环Excel的列  以下逻辑交由调用方实现
//			for (int c = 0; c < totalCells; c++) {
//				Cell cell = row.getCell(c); // 获取列
//				if (null == cell) continue;
//				if (c == 0) {
//					if (Func.equals(cell.getCellTypeEnum(), CellType.STRING)) {
//							...
//					}
//				} else if (c == 1) {
//					if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
//							...
//					}
//				}
            E apply = function.apply(row, totalCells);
            if (ObjectUtils.isNotNull(apply)) {
                list.add(apply);
            }
        }
        return list;
    }
    public static void main(@NotNull() String[] args) {
//        System.out.println(String.format("0%s", "123"));
//        System.out.println(String.format("0%%", "123"));
        System.out.println(String.format("%s", 123));
    }
}
