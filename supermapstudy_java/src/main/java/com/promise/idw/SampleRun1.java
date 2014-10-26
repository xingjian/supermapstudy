/** @文件名: SampleRun1.java @创建人：邢健  @创建日期： 2014-7-28 下午12:52:58 */
package com.promise.idw;

/**   
 * @类名: SampleRun1.java 
 * @包名: com.promise.idw 
 * @描述: TODO 
 * @作者: xingjian xingjian@yeah.net   
 * @日期:2014-7-28 下午12:52:58 
 * @版本: V1.0   
 */

import java.awt.Color;

import javax.swing.JPanel;

import com.supermap.data.DatasetVector;
import com.supermap.data.Datasource;
import com.supermap.data.GeoStyle;
import com.supermap.data.Workspace;
import com.supermap.data.WorkspaceConnectionInfo;
import com.supermap.data.WorkspaceType;
import com.supermap.mapping.Layer;
import com.supermap.mapping.Layers;
import com.supermap.mapping.ThemeRange;
import com.supermap.mapping.ThemeRangeItem;
import com.supermap.ui.Action;
import com.supermap.ui.MapControl;

/**
 * <p>
 * Title:分段专题图
 * </p>
 * 
 * <p>
 * Description:
 * ============================================================================>
 * ------------------------------版权声明----------------------------
 * 此文件为SuperMap Objects Java 的示范代码 
 * 版权所有：北京超图软件股份有限公司
 * ----------------------------------------------------------------
 * ---------------------SuperMap Objects Java 示范程序说明------------------------
 * 
 * 1、范例简介：示范如何使用ThemeRange
 * 2、示例数据：安装目录\SampleData\ThematicMaps\Thematicmaps.smwu； 
 * 3、关键类型/成员: 
 *        Layers.indexOf 方法
 *        Layers.insert 方法
 *        ThemeRange.addToHead 方法
 *        ThemeRange.addToTail 方法
 *        ThemeRangeItem.setStart 方法
 *        ThemeRangeItem.setEnd 方法
 *        ThemeRangeItem.setStyle 方法
 *        GeoStyle.setFillForeColor 方法
 * 4、使用步骤：
 *  (1)使用平移浏览地图，地图上显示分段专题图
 *  (2)通过 CheckBox 来控制是否显示分段专题图
 * -----------------------------------------------------------------------------
 * ============================================================================>
 * </p> 
 * 
 * <p>
 * Company: 北京超图软件股份有限公司
 * </p>
 * 
 */

public class SampleRun1 {
	private Workspace m_workspace;

	private MapControl m_mapControl;

	// 要表达的数据
	private DatasetVector m_datasetVector;

	private Layer m_layerThemeRange;

	private Datasource m_datasource;

	private JPanel m_panel;

	/**
	 * 根据workspace、MapControl及panel构造 SampleRun对象
	 */
	public SampleRun1(Workspace workspace, MapControl mapContol, JPanel panel) {
		this.m_workspace = workspace;
		this.m_mapControl = mapContol;
		this.m_panel = panel;

		m_mapControl.getMap().setWorkspace(m_workspace);
		initialize();
	}

	/**
	 * 打开需要的工作空间文件及地图
	 */
	private void initialize() {
		try {
			// 打开工作空间及地图
			WorkspaceConnectionInfo conInfo = new WorkspaceConnectionInfo(
					"E:/ThematicMaps/Thematicmaps.smwu");
			conInfo.setType(WorkspaceType.SMWU);
			m_workspace.open(conInfo);

			m_datasource = m_workspace.getDatasources().get(0);
			m_datasetVector = (DatasetVector) m_workspace.getDatasources().get(
					0).getDatasets().get("BaseMap_R");
			m_mapControl.getMap().setWorkspace(m_workspace);
			m_mapControl.getMap().open("京津地区地图");

			// 调整mapControl的状态
			m_mapControl.setAction(Action.PAN);
			m_mapControl.getMap().viewEntire();

			//设置显示分段专题图图层
			setThemeRange(true);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	/**
	 * 设置是否显示分段专题图
	 */
	public void setThemeRange(boolean value) {

		if (m_layerThemeRange == null) {
			try {
				//构造分段专题图对象并设置分段字段表达式
				ThemeRange themeRange = new ThemeRange();
				themeRange.setRangeExpression("UrbanRural");

				GeoStyle geoStyle = new GeoStyle();
				geoStyle.setLineColor(Color.WHITE);
				geoStyle.setLineWidth(0.3);

				//初始化分段专题图子项并设置各自的风格
				ThemeRangeItem item0 = new ThemeRangeItem();
				item0.setStart(Double.MIN_VALUE);
				item0.setEnd(50);
				geoStyle.setFillForeColor(new Color(209, 182, 210));
				item0.setStyle(geoStyle);

				ThemeRangeItem item1 = new ThemeRangeItem();
				item1.setStart(50);
				item1.setEnd(60);
				geoStyle.setFillForeColor(new Color(205, 167, 183));
				item1.setStyle(geoStyle);

				ThemeRangeItem item2 = new ThemeRangeItem();
				item2.setStart(60);
				item2.setEnd(70);
				geoStyle.setFillForeColor(new Color(183, 128, 151));
				item2.setStyle(geoStyle);

				ThemeRangeItem item3 = new ThemeRangeItem();
				item3.setStart(70);
				item3.setEnd(java.lang.Double.MAX_VALUE);
				geoStyle.setFillForeColor(new Color(164, 97, 136));
				item3.setStyle(geoStyle);

				//将分段专题图子项依次添加到分段专题图
				themeRange.addToHead(item0);
				themeRange.addToTail(item1);
				themeRange.addToTail(item2);
				themeRange.addToTail(item3);

				//添加分段专题图层
				Layers layers = this.m_mapControl.getMap().getLayers();
				int index = layers.indexOf(m_datasetVector.getName() + "@"
						+ m_datasource.getAlias());
				m_layerThemeRange = layers.insert(index, m_datasetVector,
						themeRange);
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}

		}

		// 设置图层是否可显示，并刷新地图
		m_layerThemeRange.setVisible(value);
		this.m_mapControl.getMap().refresh();
		setPanel(value);
	}

	/**
	 * 设置图例面板位置
	 */
	public void setPanel(boolean value) {
		//更新图例面板的位置并设置其是否显示
		int mapControlWidth = (int) m_mapControl.getSize().getWidth();
		int mapControlHeight = (int) m_mapControl.getSize().getHeight();
		m_panel.setLocation(mapControlWidth - 180, mapControlHeight - 150);
		m_panel.setVisible(value);
	}
}
