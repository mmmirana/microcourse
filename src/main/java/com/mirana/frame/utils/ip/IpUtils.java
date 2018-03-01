package com.mirana.frame.utils.ip;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.mirana.frame.exception.UtilsException;
import com.mirana.frame.utils.HttpUtils;
import com.mirana.frame.utils.browser.Browser;
import com.mirana.frame.utils.log.LogUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * IP工具类
 *
 * @Title
 * @Description
 * @CreatedBy Assassin
 * @DateTime 2017年11月10日下午2:58:40
 */
public class IpUtils {

	/**
	 * 查询ip地址，如果查询本机ip，直接传null或者""即可
	 *
	 * @param ip
	 * @return
	 */
	public static IpBean query (String ip) {

		// 如果是本机ip，则获取外网ip
		if (ip.equals("localhost") || ip.equals("127.0.0.1") || ip.equals(getLocalIP())) {
			ip = getNetworkIp();
		}

		IpBean ipBean = null;
		try {

			/** 2、taobaoip **/
			ipBean = taobaoIp(ip);
			if (ipBean != null) {
				return ipBean;
			}

			/** 1、ipapi json **/
			ipBean = ipAPI(ip);
			if (ipBean != null) {
				return ipBean;
			}

			/** 3、sinaip **/
			ipBean = sinaIp(ip);

			return ipBean;

		} catch (Exception e) {
			throw new UtilsException("查询ip=" + ip + "发生异常！", e);
		}
	}

	/**
	 * 从IP-API接口获取ip地址信息，参考http://ip-api.com/json
	 *
	 * @param ip
	 * @return
	 */
	public static IpBean ipAPI (String ip) {
		IpBean ipBean = null;

		String ipapiUrl = "http://ip-api.com/json";
		if (StringUtils.isNotBlank(ip)) {
			ipapiUrl += "/" + ip;
		}
		String ipjson = HttpUtils.get(ipapiUrl, null, HttpUtils.createHeader(Browser.CHROME));

		Map<String, JSONObject> ipapiMap = JSON.parseObject(ipjson, new TypeReference<Map<String, Object>>() {
		}.getType());

		if (MapUtils.getString(ipapiMap, "status").equals("success")) {
			ipBean = new IpBean();
			ipBean.setFrom(ipapiUrl);
			ipBean.setIpaddress(MapUtils.getString(ipapiMap, "query"));
			ipBean.setAsnumber(MapUtils.getString(ipapiMap, "as"));
			ipBean.setCountry(MapUtils.getString(ipapiMap, "country"));
			ipBean.setCountryid(MapUtils.getString(ipapiMap, "countryCode"));
			ipBean.setCity(MapUtils.getString(ipapiMap, "city"));
			ipBean.setRegion(MapUtils.getString(ipapiMap, "regionName"));
			ipBean.setRegionid(MapUtils.getString(ipapiMap, "region"));
			ipBean.setIsp(MapUtils.getString(ipapiMap, "isp"));
		} else {
			LogUtils.error(MessageFormat.format("从{0}获取IP失败", ipapiUrl));
		}

		return ipBean;
	}

	/**
	 * 从淘宝ip接口获取ip地址信息，参考http://ip.taobao.com/service/getIpInfo.php
	 *
	 * @param ip
	 * @return
	 */
	public static IpBean taobaoIp (String ip) {
		IpBean ipBean = null;

		String taobaoipUrl = "http://ip.taobao.com/service/getIpInfo.php";

		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(ip)) {
			paramMap.put("ip", ip);
		} else {
			paramMap.put("ip", getNetworkIp());
		}
		String taobaoipJson = HttpUtils.get(taobaoipUrl, paramMap, HttpUtils.createHeader(Browser.CHROME));

		Map<String, JSONObject> taobaoMap = JSON.parseObject(taobaoipJson, new TypeReference<Map<String, Object>>() {
		}.getType());

		// MapUtils.getInteger(taobaoMap, "code", 1) == 0
		if (MapUtils.getInteger(taobaoMap, "code", 1) == 0) {
			JSONObject dataMap = taobaoMap.get("data");
			ipBean = new IpBean();
			ipBean.setFrom(taobaoipUrl);
			ipBean.setIpaddress(MapUtils.getString(dataMap, "ip"));
			ipBean.setCountry(MapUtils.getString(dataMap, "country"));
			ipBean.setCountryid(MapUtils.getString(dataMap, "country_id"));
			ipBean.setArea(MapUtils.getString(dataMap, "area"));
			ipBean.setAreaid(MapUtils.getString(dataMap, "area_id"));
			ipBean.setRegion(MapUtils.getString(dataMap, "region"));
			ipBean.setRegionid(MapUtils.getString(dataMap, "region_id"));
			ipBean.setCity(MapUtils.getString(dataMap, "city"));
			ipBean.setCityid(MapUtils.getString(dataMap, "city_id"));
			ipBean.setCounty(MapUtils.getString(dataMap, "county"));
			ipBean.setCountyid(MapUtils.getString(dataMap, "county_id"));
			ipBean.setIsp(MapUtils.getString(dataMap, "isp"));
			ipBean.setIspid(MapUtils.getString(dataMap, "isp_id"));
		} else {
			LogUtils.error(MessageFormat.format("从{0}获取IP失败", taobaoipUrl));
		}
		return ipBean;
	}

	/**
	 * 从新浪ip接口获取ip地址信息，参考http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json
	 *
	 * @param ip
	 * @return
	 */
	public static IpBean sinaIp (String ip) {
		IpBean ipBean = null;

		String sinaipUrl = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json";

		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(ip)) {
			paramMap.put("ip", ip);
		}
		String sinaipJson = HttpUtils.get(sinaipUrl, paramMap);

		Map<String, JSONObject> sinaipMap = JSON.parseObject(sinaipJson, new TypeReference<Map<String, Object>>() {
		}.getType());

		ipBean = new IpBean();
		ipBean.setFrom(sinaipUrl);
		ipBean.setIpaddress(ip);
		ipBean.setCountry(MapUtils.getString(sinaipMap, "country"));
		ipBean.setRegion(MapUtils.getString(sinaipMap, "province"));
		ipBean.setCity(MapUtils.getString(sinaipMap, "city"));
		ipBean.setIsp(MapUtils.getString(sinaipMap, "isp"));

		return ipBean;
	}

	/**
	 * 从新浪ip接口获取ip地址信息
	 *
	 * @param ip
	 * @return
	 */
	public static String net126IP (String ip) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(ip)) {
			paramMap.put("ip", ip);
		}
		String url = "http://ip.ws.126.net/ipquery";
		return HttpUtils.get(url, paramMap);
	}

	/**
	 * 获取本机IP地址
	 *
	 * @return
	 */
	public static String getLocalIP () {
		String ip = null;
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return ip;
	}

	/**
	 * 获取本机公网ip地址
	 *
	 * @return
	 */
	public static String getNetworkIp () {
		String url = "http://ip.chinaz.com/getip.aspx";
		String ipJson = HttpUtils.get(url);
		Map<String, String> ipMap = JSON.parseObject(ipJson, new TypeReference<Map<String, String>>() {
		}.getType());
		return MapUtils.getString(ipMap, "ip");
	}

	public static void main (String[] args) {
		System.out.println(query(getLocalIP()));
		// ----------------------------------------------
		// IpUtils.ipAPI("");// 查询本机ip地址
		// IpUtils.ipAPI(ip);// 查询固定ip地址
		// ----------------------------------------------
		// IpUtils.taobaoIp("");// 查询本机ip地址
		// IpUtils.taobaoIp(ip);// 查询固定ip地址
		// ----------------------------------------------
		// IpUtils.sinaIp("");// 查询本机ip地址
		// IpUtils.sinaIp(ip);// 查询固定ip地址
	}
}
