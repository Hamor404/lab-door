package com.villege.dade.face;

import com.alibaba.fastjson.JSONObject;
import com.villege.dade.face.util.MyX509TrustManager;
import org.apache.log4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.URL;
import java.util.Map;

public class HttUtil {
	
	private Logger logger = Logger.getLogger(HttUtil.class);

	public String httpsRequest(String requestUrl, Map<String, Object> params, String method) {
		String result = null;
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			//conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-type", "application/json;charset=UTF-8");
			conn.setSSLSocketFactory(ssf);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod(method);

			if (params!=null && params.size()>0) {
				String jsonStr = new JSONObject(params).toJSONString();
				//System.out.println("request content:"+jsonStr);
				OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
				osw.write(jsonStr);
				osw.flush();
				osw.close();
			}

			InputStream inputStream = null;
			// 从输入流读取返回内容
			if (conn.getResponseCode() >= 400) {
				inputStream = conn.getErrorStream();
			}
			else {
				inputStream = conn.getInputStream();
			}
			//InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			result = buffer.toString();
		} catch (ConnectException ce) {
			logger.error("连接超时：{}", ce);
		} catch (Exception e) {
			logger.error("https请求异常：{}", e);
		}
		return result;
	}
}
