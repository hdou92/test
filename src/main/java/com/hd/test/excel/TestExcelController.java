package com.hd.test.excel;

import com.xxl.tool.excel.ExcelTool;
import com.xxl.tool.excel.annotation.ExcelField;
import com.xxl.tool.excel.annotation.ExcelSheet;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @auther houdu
 * @date 2020/7/20
 */
@RestController
public class TestExcelController {

    @SneakyThrows
    @RequestMapping(value = "test", method = RequestMethod.POST)
    public List<Object> test(@RequestParam("file") MultipartFile file) {
        return ExcelTool.importExcel(file.getInputStream(), Test.class);
    }

    @Data
    @ExcelSheet(name = "sheet1", headColor = HSSFColor.HSSFColorPredefined.LIGHT_GREEN)
    public static class Test {
        @ExcelField(name = "id")
        private long id;
        @ExcelField(name = "name")
        private String name;
    }
}
