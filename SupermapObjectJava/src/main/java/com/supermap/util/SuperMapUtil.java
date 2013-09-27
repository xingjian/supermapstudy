/** @�ļ���: SuperMapUtil.java @�����ˣ��Ͻ�  @�������ڣ� 2013-9-25 ����10:42:59 */
package com.supermap.util;

import com.supermap.data.DatasetVector;
import com.supermap.data.Datasource;
import com.supermap.data.DatasourceConnectionInfo;
import com.supermap.data.EngineType;
import com.supermap.data.Workspace;

/**   
 * @����: SuperMapUtil.java 
 * @����: com.supermap.util 
 * @����: TODO 
 * @����: xingjian xingjian@yeah.net   
 * @����:2013-9-25 ����10:42:59 
 * @�汾: V1.0   
 */
public class SuperMapUtil {

	/**
	 * ͨ��һ�²�����ȡ����Դ
	 * @param server server����
	 * @param database ���ݿ�����
	 * @param user �û���
	 * @param pass ����
	 * @param alias ����
	 * @param driver ��������
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
	 * ��ȡʸ�����ݼ�
	 * @param datasetName
	 * @return
	 */
	public static DatasetVector GetDatasetVectorByName(Datasource dataSource,String datasetName){
		return (DatasetVector) dataSource.getDatasets().get("ststbprpb");
	}
}
