package com.mirana.frame.utils.ip;

/**
 * @Title IP POJO
 * @Description
 * @CreatedBy Assassin
 * @DateTime 2017年11月10日下午4:23:18
 */
public class IpBean {
	private String from;// ip地址库，淘宝ip，新浪ip，126ip
	private String ipaddress;// ip地址
	private String asnumber;// 互联网AS号码
	private String longitude;// 经度
	private String latitude;// 纬度
	private String country;// 国家
	private String countryid;// 国家id
	private String area;// 区域
	private String areaid;// 区域id
	private String region;// 省份
	private String regionid;// 省份id
	private String city;// 城市
	private String cityid;// 城市id
	private String county;// 县区
	private String countyid;// 县区id
	private String isp;// 互联网服务提供商
	private String ispid;// 互联网服务提供商id

	public String getFrom () {
		return from;
	}

	public void setFrom (String from) {
		this.from = from;
	}

	public String getIpaddress () {
		return ipaddress;
	}

	public void setIpaddress (String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public String getAsnumber () {
		return asnumber;
	}

	public void setAsnumber (String asnumber) {
		this.asnumber = asnumber;
	}

	public String getLongitude () {
		return longitude;
	}

	public void setLongitude (String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude () {
		return latitude;
	}

	public void setLatitude (String latitude) {
		this.latitude = latitude;
	}

	public String getCountry () {
		return country;
	}

	public void setCountry (String country) {
		this.country = country;
	}

	public String getCountryid () {
		return countryid;
	}

	public void setCountryid (String countryid) {
		this.countryid = countryid;
	}

	public String getArea () {
		return area;
	}

	public void setArea (String area) {
		this.area = area;
	}

	public String getAreaid () {
		return areaid;
	}

	public void setAreaid (String areaid) {
		this.areaid = areaid;
	}

	public String getRegion () {
		return region;
	}

	public void setRegion (String region) {
		this.region = region;
	}

	public String getRegionid () {
		return regionid;
	}

	public void setRegionid (String regionid) {
		this.regionid = regionid;
	}

	public String getCity () {
		return city;
	}

	public void setCity (String city) {
		this.city = city;
	}

	public String getCityid () {
		return cityid;
	}

	public void setCityid (String cityid) {
		this.cityid = cityid;
	}

	public String getCounty () {
		return county;
	}

	public void setCounty (String county) {
		this.county = county;
	}

	public String getCountyid () {
		return countyid;
	}

	public void setCountyid (String countyid) {
		this.countyid = countyid;
	}

	public String getIsp () {
		return isp;
	}

	public void setIsp (String isp) {
		this.isp = isp;
	}

	public String getIspid () {
		return ispid;
	}

	public void setIspid (String ispid) {
		this.ispid = ispid;
	}

	@Override
	public String toString () {
		// return super.toString();
		return String.format("ip: %s, country: %s, area: %s, region: %s, city: %s", ipaddress, country, area, region, city);
	}
}
