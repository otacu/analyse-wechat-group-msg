package com.egoist.myitchat.controller;

import com.alibaba.fastjson.JSON;
import com.egoist.myitchat.Wechat;
import com.egoist.myitchat.pojo.FuncResult;
import com.egoist.myitchat.service.ExcelDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Controller
public class WechatController {

    @Autowired
    private ExcelDataService excelDataService;

    @Autowired
    private Wechat wechat;

    /**
     * 获取二维码
     *
     * @return
     */
    @PostMapping("wechat/getQRCode")
    @ResponseBody
    public FuncResult getQRCode() {
        try {
            FuncResult result = wechat.getQRCode();
            if (FuncResult.isOk(result)) {
                byte[] bytes = (byte[]) result.getData();
                result.setData(JSON.toJSONString(bytes).replace("\"", ""));
            }
            return result;
        } catch (Exception e) {
            return new FuncResult(400, "获取二维码异常【0】", null);
        }
    }

    /**
     * 登录
     *
     * @return
     */
    @PostMapping("wechat/login")
    @ResponseBody
    public FuncResult login() {
        return wechat.login();
    }

    /**
     * 跳转到首页
     *
     * @return
     */
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    /**
     * 上传
     *
     * @param upfile
     * @return
     */
    @PostMapping("wechat/upload")
    @ResponseBody
    public FuncResult upload(MultipartFile upfile) {
        try {
            if (upfile == null) {
                return new FuncResult(400, "上传文件为空", null);
            }
            InputStream in = upfile.getInputStream();
            return excelDataService.saveUploadData(in);
        } catch (Exception e) {
            return new FuncResult(400, "上传异常", null);
        }
    }

    /**
     * 重置状态
     *
     * @return
     */
    @PostMapping("wechat/reset")
    @ResponseBody
    public FuncResult reset() {
        FuncResult resetResult = excelDataService.resetAllStatus();
        if (FuncResult.isOk(resetResult)) {
            return new FuncResult(200, "重置成功", null);
        }
        return new FuncResult(400, "重置失败", null);
    }

    /**
     * 获取表格数据
     *
     * @return
     */
    @PostMapping("wechat/getTableData")
    @ResponseBody
    public FuncResult getTableData() {
        return excelDataService.getAllData();
    }
}
