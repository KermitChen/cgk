package com.ht.utils;

import java.io.IOException;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;


/**
 */
public class HttpReqJsonUtils {
	private static final Log log = LogFactory.getLog(HttpClientUtils.class);
	private static final String gzip = "gzip";
	private static final String defaultCharset = "utf-8";
	private static final String useAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.94 Safari/537.36";

	private static final String FORM = "application/x-www-form-urlencoded";
	private static final String JSON = "application/json";
	private static final String TEXT = "text/plain";

	private String type = "http";
	private static HttpReqJsonUtils http = null;
	private static HttpReqJsonUtils https = null;

	public static HttpReqJsonUtils https() {
		if(https == null) {
			https = new HttpReqJsonUtils("https");
		}
		return https;
	}

	public static HttpReqJsonUtils http() {
		if(http == null) {
			http = new HttpReqJsonUtils("http");
		}
		return http;
	}

	private HttpReqJsonUtils() {
		
	}
	
	private HttpReqJsonUtils(String type) {
		this.type = type;
	}

	public JSONObject post(String url, HttpEntity entity) throws Exception {
		JSONObject result = null;
		CloseableHttpClient httpClient = null;
		try {
			//参数封装
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(entity);
			
			//类型
			if("https".equals(type)) {
				httpClient = HttpClientUtils.getSSLClient();
			} else {
				httpClient = HttpClientUtils.getHttpClient();
			}
			
			//发送请求
			HttpResponse response = httpClient.execute(httpPost);
			
			//处理返回结果
			HttpEntity httpEntity = response.getEntity();
			String content = EntityUtils.toString(httpEntity, "UTF-8");
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = JSONObject.parseObject(content);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//关闭
			if(httpClient != null) {
				httpClient.close();
			}
		}
		return result;
	}

	public JSONObject post(String url, String postData) throws Exception {
		HttpEntity entity = new StringEntity(postData, ContentType.create(JSON, "utf-8"));
		return post(url, entity);
	}
	/*
	public JSONObject post(String url, Map<String, String> postKeyVal) throws IOException, ClientProtocolException {
		String jsonStr = mapper.writeValueAsString(postKeyVal);
		return post(url, jsonStr);
	}*/

	public JSONObject post(String url, JSONObject postJson) throws Exception {
		return post(url, postJson.toJSONString());
	}
	
	/** post text */
	public JSONObject postText(String url, String postData) throws Exception {
		HttpEntity entity = new StringEntity(postData, ContentType.create(TEXT, "utf-8"));
		return post(url, entity);
	}

	/** post form */
	public JSONObject postForm(String url, String postData) throws Exception {
		HttpEntity entity = new StringEntity(postData, ContentType.create(FORM, "utf-8"));
		JSONObject obj = post(url, entity);
		return obj;
	}

	public JSONObject getRequest(String url) throws Exception {
		CloseableHttpClient httpClient = null;
		JSONObject result = null;
		try {
			HttpGet httpGet = new HttpGet(url);
			
			//类型
			if("https".equals(type)) {
				httpClient = HttpClientUtils.getSSLClient();
			} else {
				httpClient = HttpClientUtils.getHttpClient();
			}
			
			//发送请求
			HttpResponse response = httpClient.execute(httpGet);
			
			//处理结果
			HttpEntity httpEntity = response.getEntity();
			String content = EntityUtils.toString(httpEntity, "UTF-8");
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = JSONObject.parseObject(content);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//关闭
			if(httpClient != null) {
				httpClient.close();
			}
		}
		return result;
	}

	public static JSONObject getJsonData(String url) {

		CloseableHttpClient httpClient = HttpClientUtils.getHttpClient();

		HttpGet getMethod = new HttpGet(url);

		getMethod.setHeader(HttpHeaders.USER_AGENT, useAgent);
		HttpResponse response = null;
		try {
			response = httpClient.execute(getMethod);
			StatusLine line = response.getStatusLine();
			int code = line.getStatusCode();

			if (code != HttpStatus.SC_OK) {
				log.error("httpError" + response.getStatusLine());
				return null;
			}

			HttpEntity entity = response.getEntity();
			Header ceheader = entity.getContentEncoding();
			if (ceheader != null) {
				for (HeaderElement element : ceheader.getElements()) {
					if (element.getName().equalsIgnoreCase(gzip)) {
						entity = new GzipDecompressingEntity(response.getEntity());
					}
				}
			}

			String result = EntityUtils.toString(entity, defaultCharset);
			// log.info("result:" + result);
			return JSONObject.parseObject(result);
		} catch (IOException e) {
			log.error("io error," + e);
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new JSONObject();
	}

	public JSONObject putJson(String url, String putData) throws Exception {
		JSONObject result = null;
		CloseableHttpClient httpClient = null;
		try {
			HttpPut method = new HttpPut(url);
			HttpEntity entity = new StringEntity(putData, ContentType.create(JSON, "utf-8"));
			method.setEntity(entity);
			
			//类型
			if("https".equals(type)) {
				httpClient = HttpClientUtils.getSSLClient();
			} else {
				httpClient = HttpClientUtils.getHttpClient();
			}
			
			//发送请求
			HttpResponse response = httpClient.execute(method);

			//处理返回结构
			HttpEntity httpEntity = response.getEntity();
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String content = EntityUtils.toString(httpEntity, "UTF-8");
				result = JSONObject.parseObject(content);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//关闭
			if(httpClient != null) {
				httpClient.close();
			}
		}
		return result;
	}

	public JSONObject delete(String url) throws Exception {
		JSONObject result = null;
		CloseableHttpClient httpClient = null;
		try {
			HttpDelete httpDelete = new HttpDelete(url);
			
			//类型
			if("https".equals(type)) {
				httpClient = HttpClientUtils.getSSLClient();
			} else {
				httpClient = HttpClientUtils.getHttpClient();
			}
			
			//发送请求
			HttpResponse response = httpClient.execute(httpDelete);
			
			//处理返回结果
			HttpEntity httpEntity = response.getEntity();
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String content = EntityUtils.toString(httpEntity, "UTF-8");
				result = JSONObject.parseObject(content);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//关闭
			if(httpClient != null) {
				httpClient.close();
			}
		}
		return result;
	}
	
	public ObjectNode postReturnStr(String url, String postData) throws Exception {
		ObjectNode result = null;
		CloseableHttpClient httpClient = null;
		try {
			HttpPost httpPost = new HttpPost(url);
			HttpEntity entity = new StringEntity(postData, ContentType.create(JSON, "utf-8"));
			httpPost.setEntity(entity);
			
			//类型
			if("https".equals(type)) {
				httpClient = HttpClientUtils.getSSLClient();
			} else {
				httpClient = HttpClientUtils.getHttpClient();
			}
			
			//发送请求
			HttpResponse response = httpClient.execute(httpPost);

			//处理返回结果
			HttpEntity httpEntity = response.getEntity();
			String content = EntityUtils.toString(httpEntity, "UTF-8");
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				ObjectMapper mapper = new ObjectMapper();
				mapper.setSerializationInclusion(Include.ALWAYS);  
				result = mapper.readValue(content, ObjectNode.class);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//关闭
			if(httpClient != null) {
				httpClient.close();
			}
		}
		return result;
	}
}