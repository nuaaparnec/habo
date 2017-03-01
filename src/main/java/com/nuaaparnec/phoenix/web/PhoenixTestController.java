package com.nuaaparnec.phoenix.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nuaaparnec.phoenix.bean.DemoDO;
import com.nuaaparnec.phoenix.model.PUser;
import com.nuaaparnec.phoenix.model.PUserExample;
import com.nuaaparnec.phoenix.service.HbaseService;

@Controller
@RestController
@Configuration
public class PhoenixTestController {
    private Logger       logger = LoggerFactory.getLogger(PhoenixTestController.class);
    @Autowired
    private HbaseService hbaseService;

    @RequestMapping("/hello")
    public String getHello() {
        return "hello dowld";
    }

    @RequestMapping("user_info")
    List<DemoDO> getUserInfo() {
        logger.info("Start Get Info");
        List<DemoDO> mesg = null;
        Map<String, String> map = new HashMap<>();
        try {
            PUserExample example = new PUserExample();
            List<PUser> selectByExample = hbaseService.selectByExample(example);
            System.out.println(selectByExample.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Info: " + mesg);
        return mesg;
    }

    @RequestMapping("insert")
    String insert() {

        PUser record = new PUser();
        record.setAge(666);
        record.setFirstname("Zhou");
        record.setId(17);
        record.setLastname("David");
        hbaseService.insert(record);
        return "success";
    }
}
