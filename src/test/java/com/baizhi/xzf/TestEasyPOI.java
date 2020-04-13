package com.baizhi.xzf;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baizhi.xzf.dao.UserMapper;
import com.baizhi.xzf.entity.Head;
import com.baizhi.xzf.entity.Student;
import com.baizhi.xzf.entity.Teacher;
import com.baizhi.xzf.entity.User;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestEasyPOI {

    @Test
    public void testOutOne() throws IOException {
        List<Student> students = new ArrayList<Student>();
        students.add(new Student("1", "小黄", 23, new Date()));
        students.add(new Student("2", "小刘", 26, new Date()));
        students.add(new Student("3", "小黑", 24, new Date()));
        students.add(new Student("4", "小张", 18, new Date()));

        //参数：标题，表名，实体类类对象，导出的集合
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班学生", "学生"), Student.class, students);
            workbook.write(new FileOutputStream(new File("C:\\Users\\xizhifeng\\Desktop\\test.xls")));
    }

    @Test
    public void testOutMore() throws IOException {
        List<Student> students = new ArrayList<Student>();
        students.add(new Student("1", "小黄", 23, new Date()));
        students.add(new Student("2", "小刘", 26, new Date()));
        students.add(new Student("3", "小黑", 24, new Date()));
        students.add(new Student("4", "小张", 18, new Date()));

        ArrayList<Teacher> teachers = new ArrayList<>();
        Teacher teacher = new Teacher("1", "席志锋", students);
        Teacher teacher1 = new Teacher("2", "席志锋", students);
        Teacher teacher2= new Teacher("3", "席志锋", students);
        teachers.add(teacher);
        teachers.add(teacher1);
        teachers.add(teacher2);

        //参数：标题，表名，实体类类对象，导出的集合
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班学生", "上课表"), Teacher.class, teachers);
        workbook.write(new FileOutputStream(new File("C:\\Users\\xizhifeng\\Desktop\\test.xls")));
    }
    @Test
    public void testOutPicture()throws IOException{
        ArrayList<Head> heads = new ArrayList<>();
        heads.add(new Head("1", "小黄", 23, new Date(),"C:\\Users\\xizhifeng\\Pictures\\DeskTop\\1.jpg"));
        heads.add(new Head("2", "小黑", 23, new Date(),"src/main/webapp/bootstrap/img/pic1.jpg"));
        //参数：标题，表名，实体类类对象，导出的集合
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班学生", "上课表"), Head.class, heads);
        workbook.write(new FileOutputStream(new File("C:\\Users\\xizhifeng\\Desktop\\test.xls")));

    }
    //导入不包含图片的表格
    @Test
    public void testInput()  {
        //创建导入对象
        ImportParams params = new ImportParams();
        params.setTitleRows(1);//表格的标题行，默认0
        params.setHeadRows(1);//表头的行数,默认1

        //获取导入数据
        List<Student> list = null;
        try {
            list = ExcelImportUtil.importExcel(new FileInputStream(new File("C:\\Users\\xizhifeng\\Desktop\\test.xls")), Student.class, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Student student : list) {
            System.out.println(student);
        }
    }

//导入包含图片的表格
    @Test
    public void testInputPicture()  {
        //创建导入对象
        ImportParams params = new ImportParams();
        params.setTitleRows(1);//表标题占得行数，默认0
        params.setHeadRows(1);//表表头占得行数,默认1



        //获取导入数据
        List<Head> list = null;
        try {
            list = ExcelImportUtil.importExcel(new FileInputStream(new File("E:\\test.xls")), Head.class, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Head head : list) {
            System.out.println(head);
        }
    }




}
