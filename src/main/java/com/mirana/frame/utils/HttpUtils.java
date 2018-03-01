package com.mirana.frame.utils;

import com.mirana.frame.constants.SysConstants;
import com.mirana.frame.exception.UtilsException;
import com.mirana.frame.utils.browser.Browser;
import com.mirana.frame.utils.file.FileUtils;
import com.mirana.frame.utils.json.JsonUtils;
import com.mirana.frame.utils.log.LogUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtils {

	public static final int DEATULT_TIMEOUT = 60 * 1000;

	public static final int DEATULT_MULTOPART_TIMEOUT = 120 * 1000;

	public static final int DEATULT_DOWNLOAD_TIMEOUT = 0 * 1000;

	/**
	 * Http get 请求
	 *
	 * @param url url
	 * @return 结果
	 */
	public static String get (String url) {
		return get(url, null, null, DEATULT_TIMEOUT);
	}

	/**
	 * Http get 请求
	 *
	 * @param url     url
	 * @param timeout timeout
	 * @return 结果
	 */
	public static String get (String url, int timeout) {
		return get(url, null, null, timeout);
	}

	/**
	 * Http get 请求
	 *
	 * @param url      url
	 * @param paramMap 参数
	 * @return 结果
	 */
	public static String get (String url, Map<String, Object> paramMap) {
		return get(url, paramMap, null, DEATULT_TIMEOUT);
	}

	/**
	 * Http get 请求
	 *
	 * @param url      url
	 * @param paramMap 参数
	 * @param timeout  timeout
	 * @return 结果
	 */
	public static String get (String url, Map<String, Object> paramMap, int timeout) {
		return get(url, paramMap, null, timeout);
	}

	/**
	 * Http get 请求
	 *
	 * @param url      url
	 * @param paramMap map
	 * @param headers  请求头信息
	 * @return 结果
	 */
	public static String get (String url, Map<String, Object> paramMap, Header[] headers) {
		return get(url, paramMap, headers, DEATULT_TIMEOUT);
	}

	/**
	 * Http get 请求
	 *
	 * @param url      url
	 * @param paramMap map
	 * @param headers  请求头信息
	 * @param timeout  timeout
	 * @return 结果
	 */
	public static String get (String url, Map<String, Object> paramMap, Header[] headers, int timeout) {

		List<String> paramList = new ArrayList<String>();
		if (MapUtils.isNotEmpty(paramMap)) {
			for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
				String key = entry.getKey();
				String value = MapUtils.getString(paramMap, key, "");
				paramList.add(key + "=" + value);
			}
		}

		String finalurl = url;
		if (MapUtils.isNotEmpty(paramMap)) {
			String linkcharacter = url.contains("?") ? "&" : "?";
			finalurl = url + linkcharacter + StringUtils.join(paramList, "&");
		}

		StringBuffer httpgetlog = new StringBuffer();
		httpgetlog.append(SysPropUtils.LINE_SEPARATOR);
		httpgetlog.append("----- [ Http Get ] http get start..." + SysPropUtils.LINE_SEPARATOR);
		httpgetlog.append("----- [ Http Get ] url: " + finalurl + SysPropUtils.LINE_SEPARATOR);

		try {
			// 创建HttpClient实例
			CloseableHttpClient httpclient = HttpClients.createDefault();
			// 设置请求和传输超时时间
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();
			// 新建HttpGet
			HttpGet httpGet = new HttpGet(finalurl);
			// 设置超时时间
			httpGet.setConfig(requestConfig);
			// 设置请求的headers
			httpGet.setHeaders(headers);
			// 执行get请求的响应
			CloseableHttpResponse response = httpclient.execute(httpGet);
			// 响应的状态
			String statusline = response.getStatusLine().toString();
			httpgetlog.append("----- [ Http Get ] statusline: " + statusline + SysPropUtils.LINE_SEPARATOR);
			// 响应的文本
			String responseText = EntityUtils.toString(response.getEntity());
			httpgetlog.append("----- [ Http Get ] response: " + responseText);

			LogUtils.info(httpgetlog.toString());

			return responseText;

		} catch (Exception e) {
			httpgetlog.append("----- [ Http Get ] http get请求发生异常！");
			LogUtils.error(httpgetlog.toString());

			throw new UtilsException("http get请求发生异常", e);
		}
	}

	/**
	 * http post请求
	 *
	 * @param url url
	 * @return 结果
	 */
	public static String post (String url) {
		return post(url, null, null, DEATULT_TIMEOUT);
	}

	/**
	 * http post请求
	 *
	 * @param url     url
	 * @param timeout timeout
	 * @return 结果
	 */
	public static String post (String url, int timeout) {
		return post(url, null, null, timeout);
	}

	/**
	 * http post请求
	 *
	 * @param url      url
	 * @param paramMap map
	 * @return 结果
	 */
	public static String post (String url, Map<String, Object> paramMap) {
		return post(url, paramMap, null, DEATULT_TIMEOUT);
	}


	/**
	 * http post请求
	 *
	 * @param url      url
	 * @param paramMap map
	 * @param timeout  timeout
	 * @return 结果
	 */
	public static String post (String url, Map<String, Object> paramMap, int timeout) {
		return post(url, paramMap, null, timeout);
	}


	/**
	 * http post请求
	 *
	 * @param url      url
	 * @param paramMap map
	 * @param headers  请求头信息
	 * @return 结果
	 */
	public static String post (String url, Map<String, Object> paramMap, Header[] headers) {
		return post(url, paramMap, headers, DEATULT_TIMEOUT);
	}

	/**
	 * http post请求
	 *
	 * @param url      url
	 * @param paramMap map
	 * @param headers  请求头信息
	 * @param timeout  timeout
	 * @return 结果
	 */
	public static String post (String url, Map<String, Object> paramMap, Header[] headers, int timeout) {
		StringBuffer httpgetlog = new StringBuffer();
		httpgetlog.append(SysPropUtils.LINE_SEPARATOR);
		httpgetlog.append("----- [ Http Post ] http post start..." + SysPropUtils.LINE_SEPARATOR);
		httpgetlog.append("----- [ Http Post ] url: " + url + SysPropUtils.LINE_SEPARATOR);
		if (MapUtils.isNotEmpty(paramMap)) {
			httpgetlog.append("----- [ Http Post ] params: " + JsonUtils.stringify(paramMap) + SysPropUtils.LINE_SEPARATOR);
		}

		try {
			// 创建HttpClient实例
			CloseableHttpClient httpclient = HttpClients.createDefault();
			// 设置请求和传输超时时间
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();
			// 新建HttpPost
			HttpPost httppost = new HttpPost(url);
			// 设置请求config
			httppost.setConfig(requestConfig);
			// 设置请求的headers
			httppost.setHeaders(headers);
			// 创建参数队列
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			if (MapUtils.isNotEmpty(paramMap)) {
				for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
					BasicNameValuePair nameValuePair = new BasicNameValuePair(entry.getKey(),
						entry.getValue() == null ? null : entry.getValue().toString());
					formparams.add(nameValuePair);
				}
			}
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, SysConstants.DEFAULT_CHARSET);
			httppost.setEntity(uefEntity);

			// 执行get请求的响应
			CloseableHttpResponse response = httpclient.execute(httppost);
			// 响应的状态
			String statusline = response.getStatusLine().toString();
			httpgetlog.append("----- [ Http Post ] statusline: " + statusline + SysPropUtils.LINE_SEPARATOR);
			// 响应的文本
			String responseText = EntityUtils.toString(response.getEntity());
			httpgetlog.append("----- [ Http Post ] response: " + responseText);

			LogUtils.info(httpgetlog.toString());

			return responseText;

		} catch (Exception e) {
			httpgetlog.append("----- [ Http Post ] http get请求发生异常！");
			LogUtils.error(httpgetlog.toString());

			throw new UtilsException("http post请求发生异常", e);
		}
	}

	/**
	 * http post请求
	 *
	 * @param url url
	 * @return 结果
	 */
	public static String postString (String url) {
		return postString(url, null, null, DEATULT_TIMEOUT);
	}

	/**
	 * http post请求
	 *
	 * @param url     url
	 * @param timeout timeout
	 * @return 结果
	 */
	public static String postString (String url, int timeout) {
		return postString(url, null, null, timeout);
	}

	/**
	 * http post请求
	 *
	 * @param url        url
	 * @param paramsJson paramsJson
	 * @return 结果
	 */
	public static String postString (String url, String paramsJson) {
		return postString(url, paramsJson, null, DEATULT_TIMEOUT);
	}

	/**
	 * http post请求
	 *
	 * @param url        url
	 * @param paramsJson paramsJson
	 * @param timeout    timeout
	 * @return 结果
	 */
	public static String postString (String url, String paramsJson, int timeout) {
		return postString(url, paramsJson, null, timeout);
	}

	/**
	 * http post请求
	 *
	 * @param url        url
	 * @param paramsJson paramsJson
	 * @param headers    请求头信息
	 * @return 结果
	 */
	public static String postString (String url, String paramsJson, Header[] headers) {
		return postString(url, paramsJson, headers, DEATULT_TIMEOUT);
	}

	/**
	 * http post请求
	 *
	 * @param url        url
	 * @param paramsJson paramsJson
	 * @param headers    请求头信息
	 * @param timeout    timeout
	 * @return 结果
	 */
	public static String postString (String url, String paramsJson, Header[] headers, int timeout) {
		StringBuffer httpgetlog = new StringBuffer();
		httpgetlog.append(SysPropUtils.LINE_SEPARATOR);
		httpgetlog.append("----- [ Http Post ] http post start..." + SysPropUtils.LINE_SEPARATOR);
		httpgetlog.append("----- [ Http Post ] url: " + url + SysPropUtils.LINE_SEPARATOR);
		httpgetlog.append("----- [ Http Post ] paramsJson: " + paramsJson + SysPropUtils.LINE_SEPARATOR);

		try {
			// 创建HttpClient实例
			CloseableHttpClient httpclient = HttpClients.createDefault();
			// 设置请求和传输超时时间
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();
			// 新建HttpPost
			HttpPost httppost = new HttpPost(url);
			// 设置请求config
			httppost.setConfig(requestConfig);
			// 设置请求的headers
			httppost.setHeaders(headers);
			// 设置请求体为参数json
			httppost.setEntity(new StringEntity(paramsJson, SysConstants.DEFAULT_CHARSET));

			// 执行get请求的响应
			CloseableHttpResponse response = httpclient.execute(httppost);
			// 响应的状态
			String statusline = response.getStatusLine().toString();
			httpgetlog.append("----- [ Http Post ] statusline: " + statusline + SysPropUtils.LINE_SEPARATOR);
			// 响应的文本
			String responseText = EntityUtils.toString(response.getEntity());
			httpgetlog.append("----- [ Http Post ] response: " + responseText);

			LogUtils.info(httpgetlog.toString());

			return responseText;

		} catch (Exception e) {
			httpgetlog.append("----- [ Http Post ] http post请求发生异常！");
			LogUtils.error(httpgetlog.toString());

			throw new UtilsException("http post请求发生异常", e);
		}
	}

	/**
	 * http post文件请求
	 *
	 * @param url            url
	 * @param multipartFiles multipartFiles
	 * @return 结果
	 */
	public static String multipartPost (String url, Map<String, File> multipartFiles) {
		return multipartPost(url, null, multipartFiles);
	}

	/**
	 * http post文件请求
	 *
	 * @param url            url
	 * @param paramMap       paramMap
	 * @param multipartFiles multipartFiles
	 * @return 结果
	 */
	public static String multipartPost (String url, Map<String, Object> paramMap, Map<String, File> multipartFiles) {
		return multipartPost(url, paramMap, multipartFiles, null, DEATULT_MULTOPART_TIMEOUT);
	}

	/**
	 * http multipart post请求
	 *
	 * @param url            url
	 * @param paramMap       paramMap
	 * @param multipartFiles multipartFiles
	 * @param headers        请求头信息
	 * @param timeout        timeout
	 * @return 结果
	 */
	public static String multipartPost (String url, Map<String, Object> paramMap, Map<String, File> multipartFiles, Header[] headers, int timeout) {
		StringBuffer httpgetlog = new StringBuffer();
		httpgetlog.append(SysPropUtils.LINE_SEPARATOR);
		httpgetlog.append("----- [ Http Multipart Post ] http post start..." + SysPropUtils.LINE_SEPARATOR);
		httpgetlog.append("----- [ Http Multipart Post ] url: " + url + SysPropUtils.LINE_SEPARATOR);
		httpgetlog.append("----- [ Http Multipart Post ] paramMap: " + paramMap + SysPropUtils.LINE_SEPARATOR);
		httpgetlog.append("----- [ Http Multipart Post ] multipartFiles: " + multipartFiles + SysPropUtils.LINE_SEPARATOR);

		try {
			// 创建HttpClient实例
			CloseableHttpClient httpclient = HttpClients.createDefault();
			// 设置请求和传输超时时间
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();
			// 新建HttpPost
			HttpPost httppost = new HttpPost(url);
			// 设置请求config
			httppost.setConfig(requestConfig);
			// 设置请求的headers
			httppost.setHeader("Content-Type", ContentType.MULTIPART_FORM_DATA.toString());
			httppost.setHeaders(headers);

			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			// post参数
			if (MapUtils.isNotEmpty(paramMap)) {
				for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
					builder.addTextBody(entry.getKey(), entry.getValue() == null ? null : entry.getValue().toString());
				}
			}
			// file参数
			if (MapUtils.isNotEmpty(multipartFiles)) {
				for (Map.Entry<String, File> multiEntry : multipartFiles.entrySet()) {
					builder.addPart(multiEntry.getKey(), new FileBody(multiEntry.getValue()));
				}
			}
			HttpEntity multipartEntity = builder.build();
			httppost.setEntity(multipartEntity);

			// 执行get请求的响应
			CloseableHttpResponse response = httpclient.execute(httppost);
			// 响应的状态
			String statusline = response.getStatusLine().toString();
			httpgetlog.append("----- [ Http Multipart Post ] statusline: " + statusline + SysPropUtils.LINE_SEPARATOR);
			// 响应的文本
			String responseText = EntityUtils.toString(response.getEntity());
			httpgetlog.append("----- [ Http Multipart Post ] response: " + responseText);

			LogUtils.info(httpgetlog.toString());

			return responseText;

		} catch (Exception e) {
			httpgetlog.append("----- [ Http Multipart Post ] http  Multipart post请求发生异常！");
			LogUtils.error(httpgetlog.toString());

			throw new UtilsException("http Multipart post请求发生异常", e);
		}
	}

	/**
	 * http 获取远程文件
	 *
	 * @param url          url
	 * @param destFilepath destFilepath
	 * @return 是否成功
	 */
	public static boolean getFile (String url, String destFilepath) {
		return getFile(url, null, destFilepath, null, DEATULT_DOWNLOAD_TIMEOUT);
	}

	/**
	 * http 获取远程文件
	 *
	 * @param url          url
	 * @param paramMap     paramMap
	 * @param destFilepath destFilepath
	 * @param headers      headers
	 * @param timeout      timeout
	 * @return 是否成功
	 */
	public static boolean getFile (String url, Map<String, Object> paramMap, String destFilepath, Header[] headers,
								   int timeout) {
		List<String> paramList = new ArrayList<String>();
		if (MapUtils.isNotEmpty(paramMap)) {
			for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
				String key = entry.getKey();
				String value = MapUtils.getString(paramMap, key, "");
				paramList.add(key + "=" + value);
			}
		}

		String finalurl = url;
		if (MapUtils.isNotEmpty(paramMap)) {
			String linkcharacter = url.contains("?") ? "&" : "?";
			finalurl = url + linkcharacter + StringUtils.join(paramList, "&");
		}

		StringBuffer httpgetlog = new StringBuffer();
		httpgetlog.append(SysPropUtils.LINE_SEPARATOR);
		httpgetlog.append("----- [ Http GetFile ] http getfile start..." + SysPropUtils.LINE_SEPARATOR);
		httpgetlog.append("----- [ Http GetFile ] url: " + finalurl + SysPropUtils.LINE_SEPARATOR);

		try {
			// 创建HttpClient实例
			CloseableHttpClient httpclient = HttpClients.createDefault();
			// 设置请求和传输超时时间
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();
			// 新建HttpGet
			HttpGet httpGet = new HttpGet(finalurl);
			// 设置超时时间
			httpGet.setConfig(requestConfig);
			// 设置请求的headers
			httpGet.setHeaders(headers);
			// 执行get请求的响应
			CloseableHttpResponse response = httpclient.execute(httpGet);
			// 响应的状态
			String statusline = response.getStatusLine().toString();
			httpgetlog.append("----- [ Http GetFile ] statusline: " + statusline + SysPropUtils.LINE_SEPARATOR);
			// 响应的输出流

			InputStream content = response.getEntity().getContent();
			FileUtils.createNewFile(destFilepath);
			FileUtils.copyFileByInputStream(content, destFilepath);
			httpgetlog.append("----- [ Http GetFile ] 下载文件[" + destFilepath + "]成功！");
			return true;
		} catch (Exception e) {
			httpgetlog.append("----- [ Http GetFile ] 下载文件[" + destFilepath + "]失败！");
			LogUtils.error(httpgetlog.toString());
			throw new UtilsException("http getfile 请求发生异常", e);
		}
	}


	/**
	 * 创建pc端Http请求头的信息
	 *
	 * @param browser
	 */
	public static Header[] createHeader (Browser browser) {

		List<Header> headerList = new ArrayList<Header>();

		Header acceptHeader = createHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		headerList.add(acceptHeader);

		Header acceptEncodingHeader = createHeader("Accept-Encoding", "gzip, deflate");
		headerList.add(acceptEncodingHeader);

		Header acceptLanguageHeader = createHeader("Accept-Language", "zh-CN,zh;q=0.8");
		headerList.add(acceptLanguageHeader);

		Header connectionHeader = createHeader("Connection", "keep-alive");
		headerList.add(connectionHeader);

		Header useragentHeader = createUserAgentHeader(browser);
		if (useragentHeader != null) {
			headerList.add(useragentHeader);
		}

		return headerList.toArray(new Header[0]);
	}

	/**
	 * 根据不同的浏览器创建一个UserAgentHeader对象
	 *
	 * @param browser
	 * @return 结果
	 */
	private static Header createUserAgentHeader (Browser browser) {
		Header useragentHeader = null;
		switch (browser) {
			// ------------ MOBILE ---------------
			case ANDROID:
				useragentHeader = createHeader("User-Agent", "User-Agent	Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)");
				break;
			case IOS:
				useragentHeader = createHeader("User-Agent", "User-Agent	Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)");
				break;
			// ------------ PC ---------------
			case IE10:
				useragentHeader = createHeader("User-Agent", "User-Agent	Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)");
				break;
			case CHROME:
				useragentHeader = createHeader("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.86 Safari/537.36");
				break;
			case FIREFOX:
				useragentHeader = createHeader("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.86 Safari/537.36");
				break;
			default:
				break;
		}
		return useragentHeader;
	}

	/**
	 * 创建头部信息
	 *
	 * @param name
	 * @param value
	 * @return 结果
	 */
	private static Header createHeader (String name, String value) {
		BasicHeader header = new BasicHeader(name, value);
		return header;
	}
}
