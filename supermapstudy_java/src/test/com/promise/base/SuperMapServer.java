/** @文件名: SuperMapServer.java @创建人：邢健  @创建日期： 2013-6-25 下午3:02:17 */
package com.promise.base;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.promise.idw.vo.StationRain;
import com.promise.supermap.SuperMapUtil;
import com.supermap.services.components.MapContext;
import com.supermap.services.components.commontypes.DataReturnMode;
import com.supermap.services.components.commontypes.DataReturnOption;
import com.supermap.services.components.commontypes.ExtractParameter;
import com.supermap.services.components.commontypes.Feature;
import com.supermap.services.components.commontypes.ImageOutputOption;
import com.supermap.services.components.commontypes.Layer;
import com.supermap.services.components.commontypes.LayerCollection;
import com.supermap.services.components.commontypes.MapImage;
import com.supermap.services.components.commontypes.MapParameter;
import com.supermap.services.components.commontypes.OutputFormat;
import com.supermap.services.components.commontypes.Point2D;
import com.supermap.services.components.commontypes.QueryOption;
import com.supermap.services.components.commontypes.QueryParameter;
import com.supermap.services.components.commontypes.QueryParameterSet;
import com.supermap.services.components.commontypes.QueryResult;
import com.supermap.services.components.commontypes.Recordset;
import com.supermap.services.components.commontypes.Rectangle2D;
import com.supermap.services.components.commontypes.ReturnType;
import com.supermap.services.components.commontypes.SmoothMethod;
import com.supermap.services.components.impl.MapImpl;
import com.supermap.services.components.spi.MapProvider;
import com.supermap.services.providers.RestMapProvider;
import com.supermap.services.providers.RestMapProviderSetting;


/**   
 * @类名: SuperMapServer.java 
 * @包名: com.primise.sample 
 * @描述: supermap server 测试代码 
 * @作者: xingjian xingjian@yeah.net   
 * @日期:2013-6-25 下午3:02:17 
 * @版本: V1.0   
 */
@SuppressWarnings("all")
public class SuperMapServer {

	//路径
	private static String strURL  = "http://172.18.18.34:8090/iserver/services/map-floodwarncity_gis/rest";     
	private static String userName  = null;     
	private static String password  = null;     
	
	public List<StationRain> dataList = new ArrayList<StationRain>();
	public Point2D[] points = new Point2D[11];
	public double[] zValue = new double[11];
	
	/**
	 * 初始化数据
	 */
	@Before
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
		for(int i=0;i<dataList.size();i++){
			StationRain sr = dataList.get(i);
			Point2D p = new Point2D();
			p.x = sr.getX();
			p.y = sr.getY();
			points[i] = p;
			zValue[i] = sr.getZ();
		}
	}
	
	/**
	 * 测试连接supermap server
	 * @throws Exception
	 */
	@Test
	public void testConnectServer() throws Exception{
		MapImpl mapImpl = SuperMapUtil.GetMapImplByRestServiceRootURL(strURL);
		List<String> mapNames = mapImpl.getMapNames();
		for(String mapName : mapNames){
			System.out.println(mapName);
			List<Layer> listLayer = mapImpl.getMapParameter(mapName).layers;
			for(Layer layer:listLayer){
				LayerCollection layerCollection = layer.subLayers;
				for(int m=0;m<layerCollection.size();m++){
					Layer subLayer = layerCollection.get(m);
					System.out.println(subLayer.name+"---"+subLayer.visible);
				}
			}
		}
	}
	
	/**
	 * 测试通过sql查询数据 
	 */
	@Test
	public void testQueryBySQL(){
		QueryParameterSet queryParameters = new QueryParameterSet(); 
		QueryParameter[] queryLayerParams = new QueryParameter[1]; 
		queryLayerParams[0] = new QueryParameter(); 
		queryLayerParams[0].name = "ststbprpb@floodwarncity_gis"; 
		// 属性过滤条件 
		queryLayerParams[0].attributeFilter = "MTYPE='PP1'"; 
		// 返回的属性字段名称(不区分大小写) 
		queryLayerParams[0].fields = new String[] { "MTYPE", "NAME","MIS_ID" }; 
		queryParameters.queryParams = queryLayerParams; 
		// 设置查询结果包含属性信息及空间信息。
		queryParameters.queryOption = QueryOption.ATTRIBUTEANDGEOMETRY;
		QueryResult queryResult = SuperMapUtil.QueryBySQL(SuperMapUtil.GetMapImplByRestServiceRootURL(strURL), "floodwarncity_gis", queryParameters);
		Recordset[] recordsetArray = queryResult.recordsets;
		for(int i=0;i<recordsetArray.length;i++){
			Recordset recordset = recordsetArray[i];
			System.out.println("datasetName = "+recordset.datasetName);
			Feature[] features = recordset.getFeatures();
			for(int n=0;n<features.length;n++){
				Feature feature = features[n];
				System.out.println(feature.fieldValues[0]+"---"+feature.fieldValues[1]+"---"+feature.fieldValues[2]);
			}
		}
	}
	
	/**
	 * 输出地图图片
	 */
	@Test
	public void testViewByScale(){
		ImageOutputOption outputOption = new ImageOutputOption(); 
		outputOption.format = OutputFormat.PNG;
		Point2D center = new Point2D(12408581.64, 4323254.31); 
		double scale = 0.00000086493788;
		MapImage mageImage = SuperMapUtil.ViewByScale(SuperMapUtil.GetMapImplByRestServiceRootURL(strURL), "floodwarncity_gis", center, scale, outputOption, "c:\\result0.png");
	}
	
	@Test
	public void testViewByBounds()throws Exception{
		MapImpl mapComponent = SuperMapUtil.GetMapImplByRestServiceRootURL(strURL);
		Rectangle2D viewBounds = new Rectangle2D(12222106.7690673,4437188.40859897,12595056.5131438,4209320.21331703); 
		MapParameter mapParameter = mapComponent.getDefaultMapParameter("floodwarncity_gis"); 
		ImageOutputOption outputOption = new ImageOutputOption(); 
		mapParameter.returnType = ReturnType.BINARY; 
		outputOption.format = OutputFormat.PNG; 
		mapParameter.viewBounds = viewBounds;
		MapImage mapImage = SuperMapUtil.viewByBounds(mapComponent, viewBounds, mapParameter, outputOption,"c:\\result1.png");
	}
	
	@Test
	public void testIDWAnalysis(){
		// 初始化一个REST地图服务提供者。
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
		Feature[] features = SuperMapUtil.IDWAnalysis(points, zValue, 0.001148, ep,dro,mapContext);
		System.out.println(features.length);
	}
}
