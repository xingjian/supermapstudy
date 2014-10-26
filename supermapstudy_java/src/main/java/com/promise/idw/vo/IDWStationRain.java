/** @文件名: StationRain.java @创建人：邢健  @创建日期： 2013-6-25 下午3:22:54 */
package com.promise.idw.vo;

/**   
 * @类名: StationRain.java 
 * @包名: com.promise.idw.vo 
 * @描述: TODO 
 * @作者: xingjian xingjian@yeah.net   
 * @日期:2013-6-25 下午3:22:54 
 * @版本: V1.0   
 */
public class IDWStationRain {

	private double lgtd;
	private double lttd;
	private double rainValue;
	private String stnm;
	private String stcd;
	private String adcd;
	
	public IDWStationRain(double lgtd, double lttd, double rainValue,String stnm, String stcd, String adcd) {
		this.lgtd = lgtd;
		this.lttd = lttd;
		this.rainValue = rainValue;
		this.stnm = stnm;
		this.stcd = stcd;
		this.adcd = adcd;
	}

	public double getLgtd() {
		return lgtd;
	}

	public void setLgtd(double lgtd) {
		this.lgtd = lgtd;
	}

	public double getLttd() {
		return lttd;
	}

	public void setLttd(double lttd) {
		this.lttd = lttd;
	}

	public double getRainValue() {
		return rainValue;
	}

	public void setRainValue(double rainValue) {
		this.rainValue = rainValue;
	}

	public String getStnm() {
		return stnm;
	}

	public void setStnm(String stnm) {
		this.stnm = stnm;
	}

	public String getStcd() {
		return stcd;
	}

	public void setStcd(String stcd) {
		this.stcd = stcd;
	}

	public String getAdcd() {
		return adcd;
	}

	public void setAdcd(String adcd) {
		this.adcd = adcd;
	}

	

}
