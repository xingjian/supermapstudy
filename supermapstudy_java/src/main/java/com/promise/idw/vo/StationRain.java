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
public class StationRain {

	private double x;
	private double y;
	private double z;
	private String name;
	
	public StationRain(double x, double y, double z, String name) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.name = name;
	}
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getZ() {
		return z;
	}
	public void setZ(double z) {
		this.z = z;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

}
