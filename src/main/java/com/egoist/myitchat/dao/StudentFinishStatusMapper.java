package com.egoist.myitchat.dao;

import com.egoist.myitchat.pojo.StudentFinishStatus;
import com.egoist.myitchat.pojo.StudentFinishStatusExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StudentFinishStatusMapper {
    long countByExample(StudentFinishStatusExample example);

    int deleteByExample(StudentFinishStatusExample example);

    int deleteByPrimaryKey(Integer idx);

    int insert(StudentFinishStatus record);

    int insertSelective(StudentFinishStatus record);

    List<StudentFinishStatus> selectByExample(StudentFinishStatusExample example);

    StudentFinishStatus selectByPrimaryKey(Integer idx);

    int updateByExampleSelective(@Param("record") StudentFinishStatus record, @Param("example") StudentFinishStatusExample example);

    int updateByExample(@Param("record") StudentFinishStatus record, @Param("example") StudentFinishStatusExample example);

    int updateByPrimaryKeySelective(StudentFinishStatus record);

    int updateByPrimaryKey(StudentFinishStatus record);
}