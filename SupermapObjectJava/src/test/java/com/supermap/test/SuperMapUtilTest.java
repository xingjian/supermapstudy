/** @文件名: SuperMapUtilTest.java @创建人：邢健  @创建日期： 2013-9-25 上午10:29:51 */
package com.supermap.test;

import org.junit.Assert;
import org.junit.Test;

import com.supermap.data.Datasource;
import com.supermap.data.Workspace;
import com.supermap.util.SuperMapUtil;

/**   
 * @类名: SuperMapUtilTest.java 
 * @包名: com.supermap.test 
 * @描述: supermap object java 测试 
 * @作者: xingjian xingjian@yeah.net   
 * @日期: 2013-9-25 上午10:29:51 
 * @版本: V1.0   
 */
public class SuperMapUtilTest {
	
	/**
	 * 获取数据源对象
	 */
	@Test
	public void testOpenDatasource(){
		Workspace workspace = new Workspace();
        Datasource datasource = SuperMapUtil.OpenDatasource("172.18.18.66\\SQLSERVER2008", "floodwarncity_gis", "floodwarn", "floodwarn", "sqltest", "SQL Server",workspace);
        Assert.assertNotNull(datasource);
	}
}
