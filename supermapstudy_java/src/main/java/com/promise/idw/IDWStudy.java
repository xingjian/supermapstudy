/** @文件名: IDWStudy.java @创建人：邢健  @创建日期： 2013-6-25 下午3:20:14 */
package com.promise.idw;

import java.util.ArrayList;
import java.util.List;

import com.promise.idw.vo.StationRain;
import com.supermap.services.components.commontypes.DataReturnMode;
import com.supermap.services.components.commontypes.DataReturnOption;
import com.supermap.services.components.commontypes.DatasetSpatialAnalystResult;
import com.supermap.services.components.commontypes.ExtractParameter;
import com.supermap.services.components.commontypes.Point2D;
import com.supermap.services.components.commontypes.SmoothMethod;
import com.supermap.services.components.impl.SpatialAnalystImpl;


/**   
 * @类名: IDWStudy.java 
 * @包名: com.promise.idw 
 * @描述: TODO 
 * @作者: xingjian xingjian@yeah.net   
 * @日期:2013-6-25 下午3:20:14 
 * @版本: V1.0   
 */
public class IDWStudy {

	
	public List<StationRain> dataList = new ArrayList<StationRain>();
	public Point2D[] points = new Point2D[11];
	public double[] zValue = new double[11];
	/**
	 * 初始化数据
	 */
	public void initData(){
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
	
	public void dataConver(){
		for(int i=0;i<dataList.size();i++){
			StationRain sr = dataList.get(i);
			Point2D p = new Point2D();
			p.x = sr.getX();
			p.y = sr.getY();
			points[i] = p;
			zValue[i] = sr.getZ();
		}
	}
	
	public String excuteIDW(){
		ExtractParameter ep = new ExtractParameter();
		ep.smoothMethod = SmoothMethod.BSPLINE;
		ep.smoothness = 3;
		ep.interval = 5;//等值距
		SpatialAnalystImpl sai = new SpatialAnalystImpl();
		DataReturnOption dro = new DataReturnOption();
		dro.dataReturnMode = DataReturnMode.DATASET_AND_RECORDSET;
		DatasetSpatialAnalystResult dsar = sai.extractIsoline(points, zValue, 0.001148, ep,dro);
		System.out.println("dsar");
		return "success";
	}
	
	public static void main(String[] args) {
		IDWStudy idw = new IDWStudy();
		idw.initData();
		idw.dataConver();
		idw.excuteIDW();
	}

}
