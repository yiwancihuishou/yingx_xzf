package com.baizhi.xzf;

import com.baizhi.xzf.entity.Emp;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/*
@RunWith(SpringRunner.class)
@SpringBootTest*/
public class TestsExcel {


    @Test
    public void test1(){

        //创建集合
        Emp emp1 = new Emp("1", "小黑", 12, new Date());
        Emp emp2 = new Emp("2", "小红", 26, new Date());
        Emp emp3 = new Emp("3", "小绿", 23, new Date());
        Emp emp4 = new Emp("4", "小紫", 17, new Date());
        Emp emp5 = new Emp("5", "小蓝", 31, new Date());
        Emp emp6 = new Emp("6", "小黄", 18, new Date());
        List<Emp> emps = Arrays.asList(emp1,emp2,emp3,emp4,emp5,emp6);

        Workbook workbook = new HSSFWorkbook();
        //设置字体样式
        Font font = workbook.createFont();
        font.setBold(true);//加粗
        font.setColor(Font.COLOR_RED);//设置字体颜色
        font.setFontHeightInPoints((short) 15);//字体大小
        font.setFontName("宋体"); //设置字体
        font.setItalic(true); //设置斜体

        //创建样式对象
        CellStyle cellStyle1 = workbook.createCellStyle();
        cellStyle1.setFont(font);
        //设置时间格式
        CellStyle cellStyle = workbook.createCellStyle();
        DataFormat dataFormat = workbook.createDataFormat();
        cellStyle.setDataFormat(dataFormat.getFormat("yyyy年MM月dd日"));

        Sheet sheet = workbook.createSheet("工作簿1");
        //设置行高和列宽
        sheet.setColumnWidth(3,20*256);
        Row row2 = sheet.createRow(0);
        Cell cell1 = row2.createCell(0);
        //合并单元格
        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 3);
        sheet.addMergedRegion(cellRangeAddress);
        cell1.setCellValue("用户数据");
        cell1.setCellStyle(cellStyle1);
        //设置行高
        row2.setHeight((short)550);

        //创建标题行
        Row row = sheet.createRow(1);

        String[] title={"ID","名字","年龄","生日"};
        //处理单元格对象

        for (int i = 0; i < title.length; i++) {
            row.createCell(i).setCellValue(title[i]);
        }

        for (int i = 0; i < emps.size(); i++) {
            Row row1 = sheet.createRow(i + 2);
            row1.createCell(0).setCellValue(emps.get(i).getId());
            row1.createCell(1).setCellValue(emps.get(i).getName());
            row1.createCell(2).setCellValue(emps.get(i).getAge());
            Cell cell = row1.createCell(3);
            cell.setCellStyle(cellStyle);

            cell.setCellValue(emps.get(i).getBir());


        }



        try {
            workbook.write(new FileOutputStream(new File("C:\\Users\\xizhifeng\\Desktop\\test.xls")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testIn(){
        try {
            //获取要导入的文件
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new
                    File("C:\\Users\\xizhifeng\\Desktop\\test.xls")));
            //获取工作薄
            HSSFSheet sheet = workbook.getSheet("工作簿1");
            for (int i = 2; i <= sheet.getLastRowNum(); i++) {
                Emp emp = new Emp();
                //获取行
                HSSFRow row = sheet.getRow(i);
                //获取Id
                emp.setId(row.getCell(0).getStringCellValue());
                //获取name
                emp.setName(row.getCell(1).getStringCellValue());
                //获取age
                double ages = row.getCell(2).getNumericCellValue();
                emp.setAge((int) ages);
            //获取生日
                emp.setBir(row.getCell(3).getDateCellValue());
                //调用一个插入数据库的方法
                System.out.println(emp);
            } //关闭资源
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}