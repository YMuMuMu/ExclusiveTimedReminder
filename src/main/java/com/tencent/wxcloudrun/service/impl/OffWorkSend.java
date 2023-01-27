package com.tencent.wxcloudrun.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.model.SendTypeRequest;
import com.tencent.wxcloudrun.model.WeChatTemplateMsg;
import com.tencent.wxcloudrun.service.WxChatService;
import com.tencent.wxcloudrun.utils.WxSendMessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 下班推送策略
 * */
@Service("OffWorkSend")
public class OffWorkSend implements WxChatService {
    public static TimeZone DEFAULT_SERVER_TIME_ZONE = TimeZone.getTimeZone("GMT+08:00");
    @Autowired
    protected WxSendMessageUtils wxSendMessageUtils;


    public void sendLove(SendTypeRequest send) {
        // 下班模板Id
        String templateId = "XoI6swEAZVjLTTfvVvBK6pnR_SjO1JaP20JLTIkBsdQ";
        // 模板参数
        Map<String, WeChatTemplateMsg> sendMag = new HashMap<String, WeChatTemplateMsg>();
        sendMag.put("offWork", new WeChatTemplateMsg("宝~马上就要下班咯，收拾好随身物品准备早退啦！","#b89485"));
        // 发送
        wxSendMessageUtils.send(send.getOpenId(), templateId, sendMag);
    }
    @Override
    public void sendTest(SendTypeRequest send) {

        JSONObject ccWeather = getWeather("101060101");
        JSONObject syWeather = getWeather("101070101");
        // 下班模板Id
        String templateId = "n27L4tr9sx7hQTBqv7nzOyu6c0L8LLPCzE4bZegV64I";
        // 模板参数
        SimpleDateFormat formatter  = new SimpleDateFormat("yyyy-MM-dd E");
        Map<String, WeChatTemplateMsg> sendMag = new HashMap<String, WeChatTemplateMsg>();
//        sendMag.put("user", new WeChatTemplateMsg("古德猫宁~ 小闫同学","#b89485"));
        sendMag.put("date", new WeChatTemplateMsg(formatter.format(new Date()),"#3366CC"));
        sendMag.put("city", new WeChatTemplateMsg(ccWeather.get("city").toString(),"#b89485"));
        sendMag.put("weather", new WeChatTemplateMsg(ccWeather.get("weather").toString(),"#ff9800"));
//        sendMag.put("min_temperature", new WeChatTemplateMsg(ccWeather.get("temp1").toString(),"#b89485"));
        sendMag.put("min_temperature", new WeChatTemplateMsg("-10°","#5cbce7"));
        sendMag.put("max_temperature", new WeChatTemplateMsg("-19°","#20A5DD"));
//        sendMag.put("max_temperature", new WeChatTemplateMsg(ccWeather.get("temp2").toString(),"#b89485"));
        sendMag.put("count_down", new WeChatTemplateMsg(getDay(2,29),"#ff0707"));
        sendMag.put("en", new WeChatTemplateMsg("Patience is painful, but its fruit is sweet","#1670D1"));
        sendMag.put("ch", new WeChatTemplateMsg("忍耐是痛苦的，但它的果实是甜蜜的","#1670D1"));
        // 发送
        wxSendMessageUtils.send(send.getOpenId(), templateId, sendMag);
    }


    public JSONObject getWeather(String code) {
        String str = null;
        try{
            URL url = new URL("http://www.weather.com.cn/data/cityinfo/"+code+".html");//由网址创建URL对象
            InputStreamReader isReader =  new InputStreamReader(url.openStream(),"UTF-8");//“UTF- 8”万国码，可以显示中文，这是为了防止乱码
            BufferedReader br = new BufferedReader(isReader);//采用缓冲式读入
            if ((str = br.readLine()) != null){
                System.out.println(str);//输出
            }
            br.close();//网上资源使用结束后，数据流及时关闭
            isReader.close();
        }
        catch(Exception exp){
            System.out.println(exp);
        }
        JSONObject jsonObject = JSONObject.parseObject(str);
        String weatherInfo = JSON.toJSON(jsonObject.get("weatherinfo")).toString();
        JSONObject weatherInfoObject = JSONObject.parseObject(weatherInfo);
        return weatherInfoObject;
    }

    public static long getTimeInMillis(int year, int month, int day, int hours, int minutes, int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(DEFAULT_SERVER_TIME_ZONE);
        calendar.set(year, month - 1, day, hours, minutes, seconds);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        format.setTimeZone(DEFAULT_SERVER_TIME_ZONE);
        try {
            return format.parse(format.format(calendar.getTime())).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String getDay(int month,int days) {
        long timeMillis = getTimeInMillis(2023, month, days, 0, 0, 0);
        Calendar current = Calendar.getInstance(TimeZone.getDefault());
        current.setTimeInMillis(System.currentTimeMillis());

        Calendar date = Calendar.getInstance(TimeZone.getDefault());
        date.setTimeInMillis(timeMillis);
        long time = date.getTimeInMillis() - current.getTimeInMillis();
        // 天
        int day = Math.round(time / 1000 / 60 / 60 / 24);
        // 时
        int hour = Math.round(time / 1000 / 60 / 60 % 24);
        // 分
        int minute = Math.round(time / 1000 / 60 % 60);
        // 秒
        int second = Math.round(time / 1000 % 60);

        System.out.println(String.format("%s天%s时%s分%s秒", day, hour, minute, second));
        return String.format("%s天", day, hour, minute, second);
    }
}
