package com.villege.common.utils;

import cn.hutool.core.util.IdUtil;
import com.villege.common.model.BASE64DecodedMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 公共工具类
 *
 * @author 赖国荣 31343080@qq.com
 */
public class PublicUtil {


    /**
     * 生成订单编号
     * 格式：年月日+6位随机数
     *
     * @return
     */
    public static synchronized String getOrderCode() {
        try {
            String orderNo;
            String randomNo = String.valueOf((Math.random() * 9 + 1) * 1000000);
            //随机生成数字
            orderNo = randomNo.substring(0, 6);
            String sdf = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            return sdf + orderNo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * base64转MultipartFile
     *
     * @param base64
     * @return
     */
    public static MultipartFile base64ToMultipart(String base64) {
        try {
            String[] baseStrs = base64.split(",");
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] b = new byte[0];
            b = decoder.decodeBuffer(baseStrs[1]);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            return new BASE64DecodedMultipartFile(b, baseStrs[0]);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 身份证号加*号
     */
    public static String hideMiddleCard(String realNo) {
        String newNo = "";
        if (realNo.length() == 15) {
            newNo = realNo.replaceAll("(\\d{4})\\d{7}(\\d{4})", "$1*******$2");
        }
        if (realNo.length() == 18) {
            newNo = realNo.replaceAll("(\\d{4})\\d{10}(\\d{4})", "$1**********$2");
        }
        return newNo;
    }

    /**
     * 名字加*号
     *
     * @param realname
     * @return
     */
    public static String hideMiddleName(String realname) {
        char[] r = realname.toCharArray();
        String name = "";
        if (r.length == 1) {
            name = realname;
        }
        if (r.length == 2) {
            name = realname.replaceFirst(realname.substring(1), "*");
        }
        if (r.length > 2) {
            name = realname.replaceFirst(realname.substring(1, r.length - 1), "*");
        }
        return name;
    }

    /**
     * 手机号加*号
     *
     * @param realPhone
     * @return
     */
    public static String hideMiddlePhone(String realPhone) {
        String phoneNumber;
        if (realPhone.length() == 11) {
            phoneNumber = realPhone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        } else {
            phoneNumber = realPhone;
        }
        return phoneNumber;
    }

    public static void main(String[] args) {
        // IdUtil.fastSimpleUUID()   //1d9fb6e731924e7f988b829cb305631a
        // IdUtil.randomUUID()       //eab88ac6-b519-4235-b1d5-ce77a63fe161
        // IdUtil.simpleUUID()       //40477da334a5404ca71727cd20c68116
        // IdUtil.objectId()         //5da9ed7777a49f5d8e465f41
        // IdUtil.fastUUID()         //55c112fc-bd2c-4e34-a52a-998c869584fa
        System.out.println(IdUtil.simpleUUID());
        //System.out.println(hideMiddlePhone("18693281982"));

        //boolean mobile = Validator.isMobile("12193281982");
        //for(int i = 0; i < 20; i ++){
        //	System.out.println(RandomUtil.randomInt(1000,9999));
        //}

    }
}
