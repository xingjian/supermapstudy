/** @�ļ���: SuperMapUtilTest.java @�����ˣ��Ͻ�  @�������ڣ� 2013-9-25 ����10:29:51 */
package com.supermap.test;

import org.junit.Assert;
import org.junit.Test;

import com.supermap.data.Datasource;
import com.supermap.data.Workspace;
import com.supermap.util.SuperMapUtil;

/**   
 * @����: SuperMapUtilTest.java 
 * @����: com.supermap.test 
 * @����: supermap object java ���� 
 * @����: xingjian xingjian@yeah.net   
 * @����: 2013-9-25 ����10:29:51 
 * @�汾: V1.0   
 */
public class SuperMapUtilTest {
	
	/**
	 * ��ȡ����Դ����
	 */
	@Test
	public void testOpenDatasource(){
		Workspace workspace = new Workspace();
        Datasource datasource = SuperMapUtil.OpenDatasource("172.18.18.66\\SQLSERVER2008", "floodwarncity_gis", "floodwarn", "floodwarn", "sqltest", "SQL Server",workspace);
        Assert.assertNotNull(datasource);
	}
}
