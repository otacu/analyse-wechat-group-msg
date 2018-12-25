package com.egoist.myitchat.service;

import com.egoist.myitchat.dao.StudentFinishStatusMapper;
import com.egoist.myitchat.pojo.FuncResult;
import com.egoist.myitchat.pojo.StudentFinishStatus;
import com.egoist.myitchat.pojo.StudentFinishStatusExample;
import com.egoist.parent.common.utils.collection.EgoistCollectionUtil;
import com.egoist.parent.common.utils.string.EgoistStringUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelDataService {

    @Autowired
    private StudentFinishStatusMapper studentFinishStatusMapper;

    public FuncResult saveUploadData(InputStream in) {
        final String templateTitle = "学生姓名";
        try {
            //创建Excel工作薄
            Workbook wb = WorkbookFactory.create(in);
            Sheet sheet = wb.getSheetAt(0);
            // 判断标题
            Row titleRow = sheet.getRow(0);
            if (titleRow == null) {
                return new FuncResult(400, "Excel模板错误", null);
            }
            String title = String.valueOf(titleRow.getCell(0));
            if (!templateTitle.equals(title)) {
                return new FuncResult(400, "Excel模板错误", null);
            }
            List<String> valueList = new ArrayList<>();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                String value = String.valueOf(row.getCell(0));
                if (EgoistStringUtil.isBlank(value)) {
                    continue;
                }
                valueList.add(value);
            }
            if (EgoistCollectionUtil.isEmpty(valueList)) {
                return new FuncResult(400, "没有有效数据", null);
            }
            // 保存数据
            saveStudents(valueList);
            return new FuncResult(200, "上传成功", null);
        } catch (Exception e) {
            return new FuncResult(400, "保存上传数据异常", null);
        }
    }

    private FuncResult saveStudents(List<String> nameList) {
        //先删除全部已有数据
        StudentFinishStatusExample example = new StudentFinishStatusExample();
        List<StudentFinishStatus> list = studentFinishStatusMapper.selectByExample(example);
        for (StudentFinishStatus record : list){
            studentFinishStatusMapper.deleteByPrimaryKey(record.getIdx());
        }

        for (String name : nameList) {
            StudentFinishStatus record = new StudentFinishStatus();
            record.setName(name);
            record.setStatus((short) 0);
            studentFinishStatusMapper.insert(record);
        }
        return FuncResult.ok();
    }

    public FuncResult updateStatusByName(String name, Short status) {
        StudentFinishStatusExample example = new StudentFinishStatusExample();
        StudentFinishStatusExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(name);
        List<StudentFinishStatus> list = studentFinishStatusMapper.selectByExample(example);
        if (EgoistCollectionUtil.isEmpty(list)) {
            return new FuncResult(400, "更新异常【0】", null);
        }
        StudentFinishStatus record = list.get(0);
        record.setStatus(status);
        int effectNum = studentFinishStatusMapper.updateByPrimaryKey(record);
        if (effectNum > 0) {
            return FuncResult.ok();
        }
        return new FuncResult(400, "更新异常【1】", null);
    }

    public FuncResult resetAllStatus() {
        StudentFinishStatusExample example = new StudentFinishStatusExample();
        StudentFinishStatusExample.Criteria criteria = example.createCriteria();
        criteria.andStatusNotEqualTo((short) 0);
        List<StudentFinishStatus> list = studentFinishStatusMapper.selectByExample(example);
        if (EgoistCollectionUtil.isEmpty(list)) {
            return FuncResult.ok();
        }
        for (StudentFinishStatus record : list) {
            record.setStatus((short) 0);
            studentFinishStatusMapper.updateByPrimaryKey(record);
        }
        return FuncResult.ok();
    }


    public FuncResult getAllData() {
        StudentFinishStatusExample example = new StudentFinishStatusExample();
        List<StudentFinishStatus> list = studentFinishStatusMapper.selectByExample(example);
        return FuncResult.ok(list);
    }
}
