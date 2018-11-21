package com.sezioo.wechar_demo.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.sezioo.wechar_demo.commons.ResponseHolder;

public class WlwHttpClient {
	private CookieStore cookieStore = new BasicCookieStore();
	private CloseableHttpClient httpCilent;
	private Exception httpCilentException;

	private RequestConfig postRequestConfig;
	private RequestConfig getRequestConfig;
	private RequestConfig deleteRequestConfig;
	private RequestConfig putRequestConfig;
	private Map<String, String> urlencodedHeader = new HashMap<String, String>();
	private Map<String, String> jsonHeader = new HashMap<String, String>();

	public WlwHttpClient() {
		this(false);
	}

	public List<Integer> checkCode = new ArrayList<Integer>();

	public WlwHttpClient(boolean https) {
		try {
			checkCode.add(200);

			urlencodedHeader.put("content-type", "application/x-www-form-urlencoded");
			jsonHeader.put("content-type", "application/json;charset=UTF-8");
			setTimeout(5000);

			HttpClientBuilder httpClientBuilder = HttpClients.custom().setDefaultCookieStore(cookieStore);

			if (https) {
				ignoreVerifySSL(httpClientBuilder);
			}
			httpCilent = httpClientBuilder.build();

		} catch (Exception e) {
			httpCilent = null;
			httpCilentException = e;
		}
	}

	public void setTimeout(int timeout) {
		setTimeout(timeout, timeout, timeout);
	}

	public void setTimeout(int connectTimeout, int requestTimeout, int socketTimeout) {
		postRequestConfig = RequestConfig.custom().setConnectTimeout(connectTimeout)
				.setConnectionRequestTimeout(requestTimeout).setSocketTimeout(socketTimeout).setRedirectsEnabled(true)
				.build();
		getRequestConfig = RequestConfig.custom().setConnectTimeout(connectTimeout)
				.setConnectionRequestTimeout(requestTimeout).setSocketTimeout(socketTimeout).setRedirectsEnabled(true)
				.build();
	}

	private String phaseReponse2string(HttpResponse response) throws Exception {
		if (response == null) {
			return null;
		}
		StatusLine statusLine = response.getStatusLine();
		int code = statusLine.getStatusCode();
		if ((checkCode != null) && (checkCode.contains(code))) {
			return EntityUtils.toString(response.getEntity());
		} else {
			throw new Exception(statusLine.toString());
			// throw new Exception( statusLine.getReasonPhrase());
		}
	}

	public JSONObject phase2json(String str) throws Exception {
		if (str == null) {
			return null;
		} else {
			return JSONObject.parseObject(str);
		}
	}

	public JSONObject postJSON(String url, String body) throws Exception {
		return phase2json(post(url, body));
	}

	public JSONObject postJSON(String url, JSONObject body) throws Exception {
		return phase2json(post(url, body));
	}
	
	public JSONObject postJSON(String url, JSONObject body, Map<String, String> headers) throws Exception {
		return postJSON(url, body.toString(), headers);
	}
	

	public JSONObject postJSON(String url, String body, Map<String, String> headers) throws Exception {
		return phase2json(post(url, body, headers));
	}

	public JSONObject postJSON(String url, HttpEntity entity, Map<String, String> headers) throws Exception {
		return phase2json(post(url, entity, headers));
	}

	public String post(String url, String body) throws Exception {
		StringEntity entity = null;
		if (body != null) {
			entity = new StringEntity(body);
		}
		return post(url, entity, urlencodedHeader);
	}

	public String post(String url, JSONObject body) throws Exception {
		StringEntity entity = null;
		if (body != null) {
			entity = new StringEntity(body.toString());
		}
		return post(url, entity, jsonHeader);
	}

	public String post(String url, String body, Map<String, String> headers) throws Exception {
		StringEntity entity = null;
		if (body != null) {
			entity = new StringEntity(body);
		}
		return post(url, entity, headers);
	}

	public String post(String url, HttpEntity entity, Map<String, String> headers) throws Exception {
		if (httpCilent == null) {
			throw httpCilentException;
		}
		HttpPost httpPost = new HttpPost(url);
		try {
			httpPost.setConfig(postRequestConfig);
			for (String key : headers.keySet()) {
				httpPost.addHeader(key, headers.get(key));
			}
			;
			if (entity != null) {
				httpPost.setEntity(entity);
			}

			HttpResponse httpResponse = httpCilent.execute(httpPost);
			if (httpResponse != null) {
				int code = httpResponse.getStatusLine().getStatusCode();
				if (code == 302) {
					Header header = httpResponse.getFirstHeader("location");
					String newuri = header.getValue();
					newuri = newuri + "&" + entity.toString();
					return get(newuri, headers);
					// return post(newuri, entity, headers);
				}
			}
			String str = phaseReponse2string(httpResponse);

			return str;
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				httpPost.abort();
			} catch (Exception e) {
			}
		}
	}

	public JSONObject getJSON(String url) throws Exception {
		return phase2json(get(url));
	}

	public JSONObject getJSON(String url, Map<String, String> headers) throws Exception {
		return phase2json(get(url, headers));
	}

	public String get(String url) throws Exception {
		return get(url, new HashMap<String, String>());
	}

	public String get(String url, Map<String, String> headers) throws Exception {
		if (httpCilent == null) {
			throw httpCilentException;
		}
		HttpGet httpGet = new HttpGet(url);
		try {
			httpGet.setConfig(getRequestConfig);
			for (String key : headers.keySet()) {
				httpGet.addHeader(key, headers.get(key));
			}
			;

			HttpResponse httpResponse = httpCilent.execute(httpGet);
			if (httpResponse != null) {
				int code = httpResponse.getStatusLine().getStatusCode();
				if (code == 302) {
					Header header = httpResponse.getFirstHeader("location");
					String newuri = header.getValue();
					return get(newuri, headers);
				}
			}
			String str = phaseReponse2string(httpResponse);
			return str;
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				httpGet.abort();
			} catch (Exception e) {
			}
		}
	}

	
	
	//DELETE
	
	public JSONObject deleteJSON(String url, String body) throws Exception {
		return phase2json(delete(url, body));
	}

	public JSONObject deleteJSON(String url, JSONObject body) throws Exception {
		return phase2json(delete(url, body));
	}
	
	public JSONObject deleteJSON(String url, JSONObject body, Map<String, String> headers) throws Exception {
		return deleteJSON(url, body.toString(), headers);
	}
	public JSONObject deleteJSON(String url, String body, Map<String, String> headers) throws Exception {
		return phase2json(delete(url, body, headers));
	}

	public JSONObject deleteJSON(String url, HttpEntity entity, Map<String, String> headers) throws Exception {
		return phase2json(delete(url, entity, headers));
	}

	public String delete(String url, String body) throws Exception {
		StringEntity entity = null;
		if (body != null) {
			entity = new StringEntity(body);
		}
		return delete(url, entity, urlencodedHeader);
	}

	public String delete(String url, JSONObject body) throws Exception {
		StringEntity entity = null;
		if (body != null) {
			entity = new StringEntity(body.toString());
		}
		return delete(url, entity, jsonHeader);
	}

	public String delete(String url, String body, Map<String, String> headers) throws Exception {
		StringEntity entity = null;
		if (body != null) {
			entity = new StringEntity(body);
		}
		return delete(url, entity, headers);
	}

	public String delete(String url, HttpEntity entity, Map<String, String> headers) throws Exception {
		if (httpCilent == null) {
			throw httpCilentException;
		}
		HttpDelete  httpDelete=new HttpDelete(url);
		try {
			
			httpDelete.setConfig(deleteRequestConfig);
			for (String key : headers.keySet()) {
				httpDelete.addHeader(key,  headers.get(key));
			}
			;
//			if (entity != null) {
//				httpPost.setEntity(entity);
//				httpDelete.
//			}

			HttpResponse httpResponse = httpCilent.execute(httpDelete);
			String str = phaseReponse2string(httpResponse);

			return str;
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				httpDelete.abort();
			} catch (Exception e) {
			}
		}
	}
	
	
	
	//PUT
	
		public JSONObject putJSON(String url, String body) throws Exception {
			return phase2json(put(url, body));
		}

		public JSONObject putJSON(String url, JSONObject body) throws Exception {
			return phase2json(put(url, body));
		}
		
		public JSONObject putJSON(String url, JSONObject body, Map<String, String> headers) throws Exception {
			return putJSON(url, body.toString(), headers);
		}
		public JSONObject putJSON(String url, String body, Map<String, String> headers) throws Exception {
			return phase2json(put(url, body, headers));
		}

		public JSONObject putJSON(String url, HttpEntity entity, Map<String, String> headers) throws Exception {
			return phase2json(put(url, entity, headers));
		}

		public String put(String url, String body) throws Exception {
			StringEntity entity = null;
			if (body != null) {
				entity = new StringEntity(body);
			}
			return put(url, entity, urlencodedHeader);
		}

		public String put(String url, JSONObject body) throws Exception {
			StringEntity entity = null;
			if (body != null) {
				entity = new StringEntity(body.toString());
			}
			return put(url, entity, jsonHeader);
		}

		public String put(String url, String body, Map<String, String> headers) throws Exception {
			StringEntity entity = null;
			if (body != null) {
				entity = new StringEntity(body);
			}
			return put(url, entity, headers);
		}

		public String put(String url, HttpEntity entity, Map<String, String> headers) throws Exception {
			if (httpCilent == null) {
				throw httpCilentException;
			}
			HttpPut  httpPut=new HttpPut(url);
			try {
				httpPut.setConfig(putRequestConfig);
				for (String key : headers.keySet()) {
					httpPut.addHeader(key,  headers.get(key));
				};
				if (entity != null) {
					httpPut.setEntity(entity);
				}
				HttpResponse httpResponse = httpCilent.execute(httpPut);
				String str = phaseReponse2string(httpResponse);

				return str;
			} catch (Exception e) {
				throw e;
			} finally {
				try {
					httpPut.abort();
				} catch (Exception e) {
				}
			}
		}
		
		//获取流
		public File getFile(String url, Map<String, String> headers) throws Exception {
			if (httpCilent == null) {
				throw httpCilentException;
			}
			HttpGet httpGet = new HttpGet(url);
			InputStream inputStream = null;
			OutputStream outputStream = null;
			try {
				httpGet.setConfig(getRequestConfig);
				for (String key : headers.keySet()) {
					httpGet.addHeader(key, headers.get(key));
				}
				;

				HttpResponse httpResponse = httpCilent.execute(httpGet);
				if (httpResponse != null) {
					int code = httpResponse.getStatusLine().getStatusCode();
					if (code == 302) {
						Header header = httpResponse.getFirstHeader("location");
						String newuri = header.getValue();
						return getFile(newuri, headers);
					}
				}
				HttpEntity entity = httpResponse.getEntity();
				
				String path = WlwHttpClient.class.getResource("/").getPath();
				path = path + File.separator + "pictrues" ;
				File fileDir = new File(path);
				if(!fileDir.exists())
					fileDir.mkdirs();
				String filePath = UUID.randomUUID().toString()+".jpg";
				File tempFile = new File(fileDir, filePath);
				if(!tempFile.exists())
					tempFile.createNewFile();
				
				inputStream = entity.getContent();
				outputStream = new FileOutputStream(tempFile); 
				byte[] arr = new byte[1024];
				int len = 0;
				while((len = inputStream.read(arr))!=-1) {
					System.out.println("len:"+len);
					outputStream.write(arr, 0, len);
				}
				outputStream.flush();
				return tempFile;
			} catch (Exception e) {
				throw e;
			} finally {
				if(inputStream != null) {
					inputStream.close();
				}
				if(outputStream != null) {
					outputStream.close();
				}
				try {
					httpGet.abort();
				} catch (Exception e) {
				}
			}
		}
		
		//获取流
				public void getFile1(String url, Map<String, String> headers) throws Exception {
					if (httpCilent == null) {
						throw httpCilentException;
					}
					HttpGet httpGet = new HttpGet(url);
					InputStream inputStream = null;
					OutputStream outputStream = null;
					try {
						httpGet.setConfig(getRequestConfig);
						for (String key : headers.keySet()) {
							httpGet.addHeader(key, headers.get(key));
						}
						;

						HttpResponse httpResponse = httpCilent.execute(httpGet);
						if (httpResponse != null) {
							int code = httpResponse.getStatusLine().getStatusCode();
							if (code == 302) {
								Header header = httpResponse.getFirstHeader("location");
								String newuri = header.getValue();
								getFile1(newuri, headers);//TODO:
							}
						}
						HttpEntity entity = httpResponse.getEntity();
						
						
						inputStream = entity.getContent();
						outputStream = ResponseHolder.getStream(); 
						byte[] arr = new byte[1024];
						int len = 0;
						while((len = inputStream.read(arr))!=-1) {
							System.out.println("len:"+len);
							outputStream.write(arr, 0, len);
						}
					} catch (Exception e) {
						throw e;
					} finally {
						if(inputStream != null) {
							inputStream.close();
						}
						if(outputStream != null) {
							outputStream.close();
						}
						try {
							httpGet.abort();
						} catch (Exception e) {
						}
					}
				}
		
		
		//发送文件
		public String postFile(String url, File file) throws Exception {
			MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
			multipartEntityBuilder.addBinaryBody("file", file);
			HttpEntity httpEntity = multipartEntityBuilder.build();
			Map<String, String> headers = Maps.newHashMap(); 
			return post(url, httpEntity, headers);
		}
		
		
	
	
	
	
	public void createCookie(List<BasicClientCookie> cookielist) {
		for (BasicClientCookie cookie : cookielist) {
			cookieStore.addCookie(cookie);
		}
	}

	/**
	 * 绕过验证
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */

	public void ignoreVerifySSL(HttpClientBuilder httpClientBuilder) throws Exception {
		SSLContext sslcontext = createIgnoreVerifySSL();
		// 设置协议http和https对应的处理socket链接工厂的对象
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
				.register("http", PlainConnectionSocketFactory.INSTANCE)
				// .register("https", new
				// SSLConnectionSocketFactory(sslcontext))
				// 正常的SSL连接会验证码所有证书信息
				// .register("https", new
				// SSLConnectionSocketFactory(sslcontext)).build();
				// 只忽略域名验证码
				.register("https", new SSLConnectionSocketFactory(sslcontext, NoopHostnameVerifier.INSTANCE))

				.build();
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		HttpClients.custom().setConnectionManager(connManager);
		httpClientBuilder.setConnectionManager(connManager);
	}

	public SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
		SSLContext sc = SSLContext.getInstance("SSLv3");

		// 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
		X509TrustManager trustManager = new X509TrustManager() {
			@Override
			public void checkClientTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
					String paramString) throws CertificateException {
			}

			@Override
			public void checkServerTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
					String paramString) throws CertificateException {
			}

			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};

		sc.init(null, new TrustManager[] { trustManager }, null);
		return sc;
	}

}
