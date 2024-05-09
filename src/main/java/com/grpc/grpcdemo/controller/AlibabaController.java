package com.grpc.grpcdemo.controller;

import com.grpc.grpcdemo.service.AlibabaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/alibaba")
public class AlibabaController {


    @Autowired
    private AlibabaService alibabaService;

    @GetMapping("/rpcCheck")
    public ResponseData rpcCheck() {

        //1.进行阿里云验证
        //2.进行星光验证
        String checkResult = alibabaService.rpcCheck();

        return ResponseData.success(checkResult);
    }

}
