package cn.lichuachua.mp.mpserver.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import static cn.lichuachua.mp.common.constant.SysConstant.*;

/**
 * @author 李歘歘
 */
public class SendCodeUtil {
    public static SendSmsResponse sendSms(String telephone,String code){

        //可以自助调整时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient，暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",ACCESS_KEY_ID,ACCESS_KEY_SECRET);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou","cn-hangzhou",PRODUCT,DOMAIN);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填：待发送的手机号
        request.setPhoneNumbers(telephone);
        //必填：短信签名-可在短信控制台中找到
        request.setSignName("开源互助平台");
        //必填：短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_162115459");
        //可选：模板中的变量替换JSON串，如模板内容为“亲爱的用户，您的验证码为${code}”时，此处的值为
        request.setTemplateParam("{\"code\":\"" + code + "\"}");
        //可选：outId提供给业务方扩展字段，最终在短信回执消息中将此值待会给调用者
        request.setOutId("yourOutId");

        //hint此处可能抛出异常，注意catch
        SendSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")){
            System.out.println("发送成功");
        }else {
            System.out.println("发送失败");
        }
        return sendSmsResponse;
    }
}
