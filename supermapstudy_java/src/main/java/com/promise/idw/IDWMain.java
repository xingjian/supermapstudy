/** @文件名: IDWMain.java @创建人：邢健  @创建日期： 2014-7-22 下午1:48:38 */
package com.promise.idw;

import java.util.ArrayList;
import java.util.List;

import com.supermap.services.components.MapContext;
import com.supermap.services.components.commontypes.DataReturnMode;
import com.supermap.services.components.commontypes.DataReturnOption;
import com.supermap.services.components.commontypes.DatasetSpatialAnalystResult;
import com.supermap.services.components.commontypes.ExtractParameter;
import com.supermap.services.components.commontypes.Feature;
import com.supermap.services.components.commontypes.Point2D;
import com.supermap.services.components.commontypes.Recordset;
import com.supermap.services.components.commontypes.SmoothMethod;
import com.supermap.services.components.impl.SpatialAnalystImpl;
import com.supermap.services.components.spi.MapProvider;
import com.supermap.services.providers.RestMapProvider;
import com.supermap.services.providers.RestMapProviderSetting;

/**   
 * @类名: IDWMain.java 
 * @包名: com.promise.idw 
 * @描述: TODO 
 * @作者: xingjian xingjian@yeah.net   
 * @日期:2014-7-22 下午1:48:38 
 * @版本: V1.0   
 */
public class IDWMain {

	public static Point2D[] points = new Point2D[11];
	public static double[] zValue = new double[11];
	public static List<StationRain> dataList = new ArrayList<StationRain>();
	
	public static void initData(){
		StationRain sr1 = new StationRain(110.841512962,35.878525544,0,"测站1");
		StationRain sr2 = new StationRain(111.111780897,35.963430097,0,"测站2");
		StationRain sr3 = new StationRain(110.782741623,35.797748642,31,"测站3");
		StationRain sr4 = new StationRain(110.622448449,35.843286476,21,"测站4");
		StationRain sr5 = new StationRain(111.074183758,36.099209100,11,"测站5");
		StationRain sr6 = new StationRain(111.134293698,36.001758136,5,"测站6");
		StationRain sr7 = new StationRain(111.110580044,35.863729331,10,"测站7");
		StationRain sr8 = new StationRain(111.021039979,36.066071464,15,"测站8");
		StationRain sr9 = new StationRain(110.854433702,35.985743438,30,"测站9");
		StationRain sr10 = new StationRain(111.196571592,36.051195904,40,"测站10");
		StationRain sr11 = new StationRain(110.967487961,35.890539851,110,"测站11");
		dataList.add(sr1);
		dataList.add(sr2);
		dataList.add(sr3);
		dataList.add(sr4);
		dataList.add(sr5);
		dataList.add(sr6);
		dataList.add(sr7);
		dataList.add(sr8);
		dataList.add(sr9);
		dataList.add(sr10);
		dataList.add(sr11);
	}
	
	public static Feature[] IDWAnalysis(Point2D[] points,double[] zValues,double resolution,ExtractParameter parameter,DataReturnOption resultSetting,MapContext mapContext){
		SpatialAnalystImpl spatialAnalyst = new SpatialAnalystImpl(mapContext);
		DatasetSpatialAnalystResult dsar = spatialAnalyst.extractIsoline(points,zValues,resolution,parameter,resultSetting);
		if(dsar.succeed){
			Recordset recordset = dsar.recordset;
			Feature[] features = recordset.features;
			return features;
		}else{
			return null;
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		initData();
		String strURL="http://10.10.142.135:8090/iserver/services/spatialAnalysis-floodwarn_gis/restjsr/spatialanalyst";
		RestMapProviderSetting providerSetting = new RestMapProviderSetting();
		// 指定REST地图服务提供者所使用的REST服务地址。
		providerSetting.restServiceRootURL = strURL;
		// 构建 RestMapProvider 对象
		RestMapProvider restMapProvider = new RestMapProvider(providerSetting);
		List<MapProvider> providers = new ArrayList<MapProvider>();
		providers.add(restMapProvider);
		// 初始化地图服务组件上下文对象。
		MapContext mapContext = new MapContext();
		mapContext.setProviders(providers);
		ExtractParameter ep = new ExtractParameter();
		ep.smoothMethod = SmoothMethod.BSPLINE;
		ep.smoothness = 3;
		ep.interval = 5;//等值距
		DataReturnOption dro = new DataReturnOption();
		dro.dataReturnMode = DataReturnMode.DATASET_AND_RECORDSET;
		Feature[] features = IDWAnalysis(points, zValue, 0.001148, ep,dro,mapContext);
		System.out.println(features.length);
	}

}
class StationRain {

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