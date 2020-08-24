package com.zty.robot.utils;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public class MessageUtil {

    /**
     * 欧拉蜜识别
     * @param message
     * @return
     */
    public  static  String getResultMessage(String message){
        String url = "http://cn.olami.ai/cloudservice/api";
        StringBuffer parm = new StringBuffer();
        String appkey="d92bb5a040e54ad7b84de16db1cb4737";
        String api="nli";
        String timestamp= String.valueOf(System.currentTimeMillis());
        String rq="{'data':{'input_type':1,'text':'"+message+"'},'data_type':'stt'}";
        String cusid= "3889daf4-4fc5-48e3-b2aa-b779da913eb9";
        StringBuffer str=(new StringBuffer("e17b20863a43451fb0315d15d4d8e439api=nliappkey=d92bb5a040e54ad7b84de16db1cb4737timestamp=")).append(timestamp).append("e17b20863a43451fb0315d15d4d8e439");
        String sign= MD5Util.signature(str.toString());
        parm.append("appkey=").append(appkey).append("&").append("api=").append(api).append("&").append("timestamp=")
                .append(timestamp).append("&").append("sign=").append(sign).append("&").append("rq=").append(rq).append("&").append("cusid=").append(cusid);
        String result= HttpUtil.sendPost(url,parm.toString());
        System.out.println(result);
        Map jsonMap = (Map) JSONObject.parse(result);
       Map<String,Map> map= (Map<String ,Map>)jsonMap.get("data");
        JSONArray array=(JSONArray) map.get("nli");
       JSONObject object=(JSONObject) array.get(0);
        JSONObject object1= (JSONObject)object.get("desc_obj");
        String resultStr=(String) object1.get("result");
       return resultStr;
    }

}
