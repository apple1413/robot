package com.zty.robot.Controller;
import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import com.baidu.aip.util.Util;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.zty.robot.KnowBasePort.KnowBasePortText;
import com.zty.robot.base.ResultUtils;
import com.zty.robot.entity.Faq;
import com.zty.robot.entity.Stop;
import com.zty.robot.service.FaqService;
import com.zty.robot.service.StopService;
import com.zty.robot.utils.HttpUtil;
import com.zty.robot.utils.MessageUtil;
import com.zty.robot.utils.SocketClient;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * liujie
 */
@Controller
public class HelloController {

    @Autowired
    private FaqService faqService;

    @Autowired
    private StopService stopService;

    //百度语音参数
    public static final String APP_ID = "15092760";
    public static final String API_KEY = "GUTlLRtvoWPY31mI0RYjUoWi";
    public static final String SECRET_KEY = "Y7l086Mb0VsRhTxFWM9A9HL9o8lNlbBG";

    //智能AI模块
    public static final String AI_URL = "http://localhost:1001";

    @Autowired
    private KnowBasePortText knowBasePortText;


    @ResponseBody
    @RequestMapping("/robot/question")
    public ResultUtils RobotTextQuestion(@RequestBody Map<String, Object> param,HttpServletRequest request){
        String ques=null;
        String ans=null;
        //判断是否是语音输入
        if(param.get("type").equals("audio")){
            // 初始化一个AipSpeech
            AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);
            // 对语音二进制数据进行识别
            try {

                HashMap<String, Object> options = new HashMap<String,Object>();
                options.put("dev_pid",1936);
                if(param.get("question").toString().equals("data:audio/wav;base64,")){
                    ResultUtils test=knowBasePortText.SendTextQuestion("sorry","很抱歉，您说话声音太短，请您重新提问", this.textToVoice("很抱歉，您说话声音太短，请您重新提问",request));
                    return test;
                }
                JSONObject res = client.asr(Base64.decodeBase64( param.get("question").toString().replace("data:audio/wav;base64,","")), "pcm", 16000, options);
                if(!res.get("err_msg").toString().trim().equals("success.")){
                    ResultUtils test=knowBasePortText.SendTextQuestion("sorry","很抱歉，没有听懂，请您换个问法吧！",  this.textToVoice("很抱歉，没有听懂，请您换个问法吧！",request));
                    return test;
                }
                ques=  res.get("result").toString().replace("[","").replace("]","").replace("，","").replace("\"","");
                System.out.println(res.toString(2));
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            ques=param.get("question").toString();
        }
        ans=faqQuestion(ques);
        if( ans==null){
            ans=MessageUtil.getResultMessage(ques);
        }
        ResultUtils test=knowBasePortText.SendTextQuestion(ques,ans, this.textToVoice(ans,request));
        return test;
    }
    /**
     * 本地访问内容地址 ：http://localhost:8080/lmycc/hello
     *
     * @return
     */
    @RequestMapping("/hello")
    public String helloHtml() {
        return "index";
    }

    @ResponseBody
    @RequestMapping("/getResultMessage")
    public  String getResultMessage(String message) {
        String ans=faqQuestion(message);
       if( ans!=null){
           return ans;
       }
       //调用欧拉蜜
       String result = MessageUtil.getResultMessage(message);
        return result;
    }

    /**
     * 访问知识库
     * @param ques
     * @return
     */
    public String faqQuestion(String ques){
        //String url="http://127.0.0.1:8085";
        //String parm="question="+ques;
        String result=null;
        try{
            //HttpUtil.get(url);
           result= SocketClient.send(ques);
        }catch (Exception e){

        }

        return result;

    }

    /**
     * 过滤停用词
     * @param str
     * @return
     */
    public  List<String> filterString(String str){
        JiebaSegmenter segmenter = new JiebaSegmenter();
        String [] originStr= segmenter.sentenceProcess(str).toString().replace("[","").replace("]","").split(",");

        List<String> originList= new LinkedList<String>();
        for(String str1:originStr){
            originList.add(str1.trim());
        }
         List<Stop> stoplist=stopService.getAll();
        Iterator<String> it=originList.iterator();
        while(it.hasNext()){
            String tet1=it.next();
            tet1= tet1.trim();
            for(int j=0;j<stoplist.size();j++){
                String tet2= stoplist.get(j).getStopword().trim();
                // System.out.println(tet.toString().equals(constantStr.get(j).toString()));
                if(tet1.equals(tet2)){
                    it.remove();
                }
            }
        }
        return originList;
    }

    /**
     * 对比两个问句的余弦相似度
     * @param testStr
     * @param str
     * @return
     *//*
    public Double contrastStr(String testStr,String str){
        List<String> list1= this.filterString(testStr);
        List<String> list2= this.filterString(str);
        Set<String> set = new HashSet<String>();
        for(String st:list1){
            set.add(st);
        }
        for(String st:list2){
            set.add(st);
        }
        List<Integer> listInt1=   new ArrayList<Integer>();
        List<Integer> listInt2=   new ArrayList<Integer>();
        for (String strSet : set) {
            int num1=0;
            for(String st:list1){
                if(strSet.equals(st.trim())){
                    num1++;
                }
            }
            listInt1.add(num1);
            int num2=0;
            for(String st:list2){
                if(strSet.equals(st)){
                    num2++;
                }
            }
            listInt2.add(num2);

        }
        //计算余弦相似度
        int fenzi=0;
        //计算分子
        for(int i=0;i<listInt1.size();i++){

            fenzi+= listInt1.get(i)*listInt2.get(i);
        }
        int sqartSum1=0;
        int sqartSum2=0;
        //计算分母
        for(int i=0;i<listInt1.size();i++){

            sqartSum1+= (listInt1.get(i)*listInt1.get(i));
            sqartSum2+= (listInt2.get(i)*listInt2.get(i));

            //  fenzi+= listInt1.get(i)*listInt2.get(i);
        }
        return  fenzi/(double)( Math.sqrt(sqartSum1)* Math.sqrt(sqartSum2));
    }
*/

    /**
     * 文字转声音
     * @param message
     * @param request
     * @return
     */
    public String textToVoice(String message, HttpServletRequest request) {
        // 初始化一个AipSpeech
        AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);

        HashMap<String, Object> options = new HashMap<String, Object>();
       // options.put("spd", "5");
      //  options.put("pit", "5");
        options.put("per", "4");

        // 调用接口
        TtsResponse res = client.synthesis(message, "zh", 1,   options);
        byte[] data = res.getData();
        JSONObject res1 = res.getResult();
        String uuid=null;
        if (data != null) {
            try {
                //获取跟目录
                File path = new File(ResourceUtils.getURL("classpath:").getPath());
                if(!path.exists()) path = new File("");
                System.out.println("path:"+path.getAbsolutePath());

                //如果上传目录为/static/images/upload/，则可以如下获取：
                File upload = new File(path.getAbsolutePath(),"static/robot/");
                if(!upload.exists()) upload.mkdirs();
                System.out.println("upload url:"+upload.getAbsolutePath());

                uuid=UUID.randomUUID().toString();
                Util.writeBytesToFileSystem(data, upload.getAbsolutePath()+"\\"+uuid+".mp3");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (res1 != null) {
            try {
                System.out.println(res1.toString(2));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(uuid!=null){
            return uuid+".mp3";
        }
        else{
         return null;
        }
}

}