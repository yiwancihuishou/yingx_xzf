package com.baizhi.xzf.serviceImpl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.xzf.dao.LogMapper;
import com.baizhi.xzf.entity.Log;
import com.baizhi.xzf.service.LogService;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class LogServiceImpl implements LogService {
    @Resource
    LogMapper logMapper;

    @Override
    public HashMap<String, Object> queryByPage(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();

        //总条数
        Integer records = logMapper.selectCount(new Log());
        map.put("records",records);

        //总页数 total  三元运算符  总条数%上每页展示几条是否有余数 有余数的话+1
        Integer total=records % rows==0? records/rows:records/rows+1;
        map.put("total",total);
        //当前页
        map.put("pager",page);
        //数据
        //RowBounds参数 忽略条数，获取几条
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<Log> Logs = logMapper.selectByRowBounds(new Log(), rowBounds);
        map.put("rows",Logs);
        return map;
    }
    //导出日志信息
    @Override
    public void outPutLogExcel()  {
        List<Log> logs = logMapper.selectAll();

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("日志信息一览表", "工作簿1"), Log.class, logs);

        try {
            workbook.write(new FileOutputStream(new File("C:\\Users\\xizhifeng\\Desktop\\logs.xls")));
            //释放资源
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
