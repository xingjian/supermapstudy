/** @文件名: SuperMapUtil.java @创建人：邢健  @创建日期： 2013-9-23 上午8:47:58 */
package com.promise.supermap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.supermap.services.components.MapContext;
import com.supermap.services.components.MapException;
import com.supermap.services.components.commontypes.DataReturnOption;
import com.supermap.services.components.commontypes.DatasetSpatialAnalystResult;
import com.supermap.services.components.commontypes.ExtractParameter;
import com.supermap.services.components.commontypes.Feature;
import com.supermap.services.components.commontypes.ImageOutputOption;
import com.supermap.services.components.commontypes.MapImage;
import com.supermap.services.components.commontypes.MapParameter;
import com.supermap.services.components.commontypes.Point2D;
import com.supermap.services.components.commontypes.QueryParameterSet;
import com.supermap.services.components.commontypes.QueryResult;
import com.supermap.services.components.commontypes.Recordset;
import com.supermap.services.components.commontypes.Rectangle2D;
import com.supermap.services.components.commontypes.ReturnType;
import com.supermap.services.components.impl.MapImpl;
import com.supermap.services.components.impl.SpatialAnalystImpl;
import com.supermap.services.components.spi.MapProvider;
import com.supermap.services.providers.RestMapProvider;
import com.supermap.services.providers.RestMapProviderSetting;

/**
 * @类名: SuperMapUtil.java
 * @包名: com.promise.supermap
 * @描述: supermap 工具类
 * @作者: xingjian xingjian@yeah.net
 * @日期:2013-9-23 上午8:47:58
 * @版本: V1.0
 */
public class SuperMapUtil {

	/**
	 * 通过restServiceRootURL获取REST地图服务提供者
	 * @param restServiceRootURL
	 * @return
	 */
	public static MapImpl GetMapImplByRestServiceRootURL(String restServiceRootURL) {
		// 初始化一个REST地图服务提供者。
		RestMapProviderSetting providerSetting = new RestMapProviderSetting();
		// 指定REST地图服务提供者所使用的REST服务地址。
		providerSetting.restServiceRootURL = restServiceRootURL;
		// 构建 RestMapProvider 对象
		RestMapProvider restMapProvider = new RestMapProvider(providerSetting);
		List<MapProvider> providers = new ArrayList<MapProvider>();
		providers.add(restMapProvider);
		// 初始化地图服务组件上下文对象。
		MapContext mapContext = new MapContext();
		mapContext.setProviders(providers);
		// 创建地图服务组件。
		MapImpl mapComponent = new MapImpl();
		// 指定地图服务组件使用的服务提供者
		mapComponent.setComponentContext(mapContext);
		return mapComponent;
	}

	/**
	 * 
	 * @param mapComponent 地图服务提供者
	 * @param mapName 查询的目标地图的名称
	 * @param queryParameters 查询参数集，可以包含多个查询参数
	 * @return
	 */
	public static QueryResult QueryBySQL(MapImpl mapComponent,String mapName, QueryParameterSet queryParameters){
		QueryResult queryResult = null;
		try {
			queryResult = mapComponent.queryBySQL(mapName, queryParameters);
		} catch (MapException e) {
			e.printStackTrace();
		}
		return queryResult;
	}

	/**
	 * 
	 * @param mapc 地图服务提供者
	 * @param mapName 查询的目标地图的名称
	 * @param center 中点
	 * @param scale 比例
	 * @param outputOption 输入图片选项
	 * @param fileURL 输出图片路径
	 * @return
	 */
	public static MapImage ViewByScale(MapImpl mapc, String mapName, Point2D center,double scale, ImageOutputOption outputOption,String fileURL) {
		MapImage mapImage = null;
		try {
			MapParameter mapParameter = mapc.getDefaultMapParameter(mapName);
			mapParameter.returnType = ReturnType.BINARY;
			mapImage = mapc.viewByScale(center, scale, mapParameter,outputOption);
			// 输出地图图片到指定的 filePath 文件。
			File file = new File(fileURL);
			try {
				if (file.exists()){
					file.delete();
				}
				file = new File(file.getAbsolutePath());
				FileOutputStream outputstreamm = new FileOutputStream(file);
				outputstreamm.write(mapImage.imageData);
				outputstreamm.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 释放地图组件占用的资源
			mapc.dispose();
		} catch (MapException e1) {
			e1.printStackTrace();
		}
		return mapImage;
	}

	/**
	 * 
	 * @param mapc 地图服务提供者
	 * @param bounds 范围
	 * @param mapParameter
	 * @param outputOption
	 * @return
	 */
	public static MapImage viewByBounds(MapImpl mapc,Rectangle2D bounds, MapParameter mapParameter, ImageOutputOption outputOption,String fileURL){
		MapImage mapImage = null;
		try {
			mapImage = mapc.getMapImage(mapParameter, outputOption);
			// 输出地图图片到指定的 filePath 文件。
			File file = new File(fileURL);
			try {
				if (file.exists()){
					file.delete();
				}
				file = new File(file.getAbsolutePath());
				FileOutputStream outputstreamm = new FileOutputStream(file);
				outputstreamm.write(mapImage.imageData);
				outputstreamm.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 释放地图组件占用的资源
			mapc.dispose();
		} catch (MapException e) {
			e.printStackTrace();
		} 
		return mapImage;
	}

	/**
	 * 提取等值线,将结果集暂时输出
	 */
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
	 * 批量操作features
	 * @return
	 */
	public static boolean EditFeatures(String type,String dataSetName,Feature[] features){
		if(type.equals("add")){
			
		}else if(type.equals("update")){
			
		}else if(type.equals("delete")){
			
		}
		return false;
	}
	
}
