package com.deyuan.weather.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import org.springframework.scheduling.annotation.Async;

public class WeatherDataServer {

	public static String getWeatherInform(String cityName) {
		StringBuffer strBuf = new StringBuffer();
		JSONObject jsonObject = null;
		try {
			URL myUrl = new URL(
					"http://api.map.baidu.com/telematics/v3/weather?");
			HttpURLConnection conn = (HttpURLConnection) myUrl.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			OutputStreamWriter osw = new OutputStreamWriter(conn
					.getOutputStream());
			osw.write("location="
					+ java.net.URLEncoder.encode(cityName, "utf-8")
					+ "&output=json&ak=" + WeatherUtils.getAk());
			osw.flush();
			osw.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(conn
					.getInputStream(), "utf-8"));
			String s = null;
			while ((s = br.readLine()) != null) {
				strBuf.append(s);
			}
			JsonConfig jsonConfig = new JsonConfig(); // 建立配置文件
			jsonConfig.setIgnoreDefaultExcludes(false); // 设置默认忽略
			jsonConfig.setExcludes(new String[] { "index", "nightPictureUrl",
					"dayPictureUrl" });// 忽略index/nightPictureUrl/dayPictureUrl
			// 长度缩减
			jsonObject = (JSONObject) JSONSerializer.toJSON(strBuf.toString(),
					jsonConfig);

		} catch (Exception e) {

		}
		return jsonObject.toString();
	}

	@Async
	// 异步方法，将数据库中查不到信息的城市增加到配置文件中，加文件锁
	public static void saveWeatherCityFile(String cityName) {
		File file = new File(WeatherUtils.cityFile);
		RandomAccessFile fout = null;
		FileChannel fcout = null;
		try {
			fout = new RandomAccessFile(file, "rw");
			long filelength = fout.length();// 获取文件的长度
			fout.seek(filelength);// 将文件的读写指针定位到文件的末尾
			fcout = fout.getChannel();// 打开文件通道
			FileLock flout = null;
			while (true) {
				try {
					flout = fcout.tryLock();// 不断的请求锁，如果请求不到，等一秒再请求
					break;
				} catch (Exception e) {
					System.out.print("lock is exist ......");
					Thread.sleep(1000);
				}
			}
			String str = cityName + "\r\n";
			fout.write(str.getBytes());
			flout.release();
			fcout.close();
			fout.close();
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.print("file no find ...");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if (fcout != null) {
				try {
					fcout.close();
				} catch (IOException e) {
					e.printStackTrace();
					fcout = null;
				}
			}
			if (fout != null) {
				try {
					fout.close();
				} catch (IOException e) {
					e.printStackTrace();
					fout = null;
				}
			}
		}

	}
}
