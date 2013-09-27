/** @文件名: SuperMapUtil.java @创建人：邢健  @创建日期： 2013-9-25 上午10:42:59 */
package com.supermap.util;

import com.supermap.data.DatasetVector;
import com.supermap.data.Datasource;
import com.supermap.data.DatasourceConnectionInfo;
import com.supermap.data.EngineType;
import com.supermap.data.Workspace;

/**   
 * @类名: SuperMapUtil.java 
 * @包名: com.supermap.util 
 * @描述: TODO 
 * @作者: xingjian xingjian@yeah.net   
 * @日期:2013-9-25 上午10:42:59 
 * @版本: V1.0   
 */
public class SuperMapUtil {

	/**
	 * 通过一下参数获取数据源
	 * @param server server名称
	 * @param database 数据库名称
	 * @param user 用户名
	 * @param pass 密码
	 * @param alias 别名
	 * @param driver 驱动名称
	 * @return Datasource
	 */
	public static Datasource OpenDatasource(String server,String database,String user,String pass,String alias,String driver,Workspace workspace){
		DatasourceConnectionInfo datasourceconnection = new DatasourceConnectionInfo();
		datasourceconnection.setEngineType(EngineType.SQLPLUS);
        datasourceconnection.setServer(server);
        datasourceconnection.setDatabase(database);
        datasourceconnection.setUser(user);
        datasourceconnection.setPassword(pass);
        datasourceconnection.setAlias(alias);
        datasourceconnection.setDriver(driver);
        Datasource datasource = workspace.getDatasources().open(datasourceconnection);
		return datasource;
	}
	
	/**
	 * 获取矢量数据集
	 * @param datasetName
	 * @return
	 */
	public static DatasetVector GetDatasetVectorByName(Datasource dataSource,String datasetName){
		return (DatasetVector) dataSource.getDatasets().get("ststbprpb");
	}
}
