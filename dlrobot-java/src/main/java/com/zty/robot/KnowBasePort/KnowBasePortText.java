package com.zty.robot.KnowBasePort;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zty.robot.base.ResultUtils;
import com.zty.robot.base.ServerConfigure;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;


@Component
public class KnowBasePortText extends KnowBaseAbstract{

    private static Logger logger = Logger.getLogger(KnowBasePortText.class);

    @Autowired
    private ServerConfigure serverConfigure;

    public  ResultUtils SendTextQuestion(String ques, String ans,String uuid) {
        ResultUtils resultUtils = new ResultUtils();

        //纯文本回复
        Map<String,Object> rootNode= new HashMap<>();
        rootNode.put("answerTypeId",6);
        rootNode.put("protocolId",null);
        rootNode.put("sendTime",System.currentTimeMillis());
        rootNode.put("uuidname",uuid);
        Map<String,Object> singleNode= new HashMap<>();
        singleNode.put("answerMsg",ans);
        singleNode.put("cmd",null);
        singleNode.put("isRichText",null);
        singleNode.put("list",null);
        singleNode.put("score",null);
        singleNode.put("standardQuestion",ques);
        rootNode.put("singleNode",singleNode);


        resultUtils.setCode(0);
        resultUtils.setMsg(rootNode);

        return resultUtils;
    }



}
