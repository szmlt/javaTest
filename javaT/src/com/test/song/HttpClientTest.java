package com.test.song;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;


public class HttpClientTest {
	private static HttpClient httpClient = new HttpClient();
	//设置代理服务器   
	static{
		//设置代理服务器的IP地址和端口
		httpClient.getHostConfiguration().setProxy("172.17.18.84", 8080);
	}
	public static boolean downloadPage(String path) throws HttpException,IOException {
		InputStream input = null;
		java.io.OutputStream output = null;
		//得到post方法的参数
		PostMethod postMethod = new PostMethod(path);
		//设置post方法的参数
		NameValuePair[] postData = new NameValuePair[2];
		postData[0] = new NameValuePair("name","lietu");
		postData[1] = new NameValuePair("password","*****");
		postMethod.addParameters(postData);
		//执行，返回状态码
		int statusCode = httpClient.executeMethod(postMethod);
		//针对状态码进行处理
		if(statusCode == HttpStatus.SC_OK) {
			input = postMethod.getResponseBodyAsStream();
			//得到文件名
			String filename = path.substring(path.lastIndexOf('/')+1);
			//得到文件输出流
			output = new FileOutputStream(filename);
			
			//输出到文件
			int tempByte = -1;
			while((tempByte=input.read())>0) {
				output.write(tempByte);
			}
			//关闭输入输出流
			if(input!=null) {
				input.close();
			}
			if(output!=null) {
				output.close();
			}
			return true;
			
		}
		return false;
	}
	
	/*
	 * 测试代码
	 */
	public static void main(String[] args) {
		//抓取lietu首页，输出
		try {
			HttpClientTest.downloadPage("http://www.lietu.com");
		}catch (HttpException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
		
}
	
