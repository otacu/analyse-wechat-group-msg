package com.egoist.myitchat;

import com.alibaba.fastjson.JSONObject;
import com.egoist.myitchat.dao.StudentFinishStatusMapper;
import com.egoist.myitchat.pojo.StudentFinishStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMyitchatApplication {
    @Autowired
    private StudentFinishStatusMapper studentFinishStatusMapper;

    @Test
    public void testMybatis() {
        StudentFinishStatus studentFinishStatus = studentFinishStatusMapper.selectByPrimaryKey(1);
        System.out.println("##########" + JSONObject.toJSONString(studentFinishStatus));
    }
}
