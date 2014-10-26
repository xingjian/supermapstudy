/** @文件名: MainFrame1.java @创建人：邢健  @创建日期： 2014-7-28 下午12:55:38 */
package com.promise.idw;

/**   
 * @类名: MainFrame1.java 
 * @包名: com.promise.idw 
 * @描述: TODO 
 * @作者: xingjian xingjian@yeah.net   
 * @日期:2014-7-28 下午12:55:38 
 * @版本: V1.0   
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.supermap.data.Workspace;
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

public class MainFrame1 extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel m_contentPane;

	private JPanel m_panelCheckBoxes;

	private JCheckBox m_checkBoxThemeRange;

	private JPanel m_jPanelDiagram;
	
	private SampleRun1 m_sampleRun;
	
	private Workspace m_workspace;

	private MapControl m_mapControl;

	/**
	 * 程序入口点
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainFrame1 thisClass = new MainFrame1();
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				thisClass.setVisible(true);
			}
		});
	}

	/**
	 * 构造函数
	 */
	public MainFrame1() {
		super();
		initialize();
	}

	/**
	 * 初始化窗体
	 */
	private void initialize() {
		//最大化显示窗体
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setSize(800, 500);
		this.setContentPane(getJContentPane());
		this.setTitle("分段专题图");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			// 在主窗体加载时，初始化SampleRun类型，来完成功能的展现
			public void windowOpened(java.awt.event.WindowEvent e) {
				m_workspace = new Workspace();
				m_sampleRun = new SampleRun1(m_workspace, m_mapControl,
						getJPanel());
			}

			// 在窗体关闭时，需要释放相关的资源
			public void windowClosing(java.awt.event.WindowEvent e) {
				m_mapControl.dispose();
				m_workspace.dispose();
			}
		});
		//添加窗体事件，确保图例面板的位置正确
		this.addComponentListener(new ComponentAdapter() {

			public void componentResized(ComponentEvent e) {
				if (m_sampleRun == null) {
					return;
				}
				m_sampleRun.setPanel(getCheckBoxThemeRange().isSelected());
			}
		});
	}

	/**
	 * 获取m_contentPane
	 */
	private JPanel getJContentPane() {
		if (m_contentPane == null) {
			m_contentPane = new JPanel();
			m_contentPane.setLayout(new BorderLayout());
			m_contentPane.add(getPanelCheckBoxes(), BorderLayout.NORTH);
			m_contentPane.add(getMapControl(), BorderLayout.CENTER);
		}
		return m_contentPane;
	}

	/**
	 * 获取m_panelCheckBoxes 
	 */
	private JPanel getPanelCheckBoxes() {
		if (m_panelCheckBoxes == null) {
			m_panelCheckBoxes = new JPanel();
			m_panelCheckBoxes.setLayout(new BoxLayout(getPanelCheckBoxes(),
					BoxLayout.X_AXIS));
			m_panelCheckBoxes.add(getCheckBoxThemeRange(), null);
		}
		return m_panelCheckBoxes;
	}

	/**
	 * 获取m_mapControl 
	 */
	private MapControl getMapControl() {
		if (m_mapControl == null) {
			m_mapControl = new MapControl();
			m_mapControl.setLayout(null);
			m_mapControl.add(getJPanel());
		}
		return m_mapControl;
	}

	/**
	 * 获取m_checkBoxThemeRange	
	 * 	
	 */
	private JCheckBox getCheckBoxThemeRange() {
		if (m_checkBoxThemeRange == null) {
			m_checkBoxThemeRange = new JCheckBox();
			m_checkBoxThemeRange.setText("分段专题图");
			m_checkBoxThemeRange.setSelected(true);
			m_checkBoxThemeRange
					.addItemListener(new java.awt.event.ItemListener() {
						public void itemStateChanged(java.awt.event.ItemEvent e) {
							//是否显示分段专题图
							m_sampleRun.setThemeRange(m_checkBoxThemeRange
									.isSelected());
						}
					});
		}
		return m_checkBoxThemeRange;
	}

	/**
	 * 初始化图例面板
	 */
	private JPanel getJPanel() {
		if (m_jPanelDiagram == null) {

			//设置面板的标题
			JLabel titleLLabel = new JLabel();
			titleLLabel.setHorizontalAlignment(JLabel.RIGHT);
			titleLLabel.setText("2000年城");
			JLabel titleRLabel = new JLabel();
			titleRLabel.setText("乡建设用地");
			JLabel title1LLabel = new JLabel();
			title1LLabel.setHorizontalAlignment(JLabel.RIGHT);
			title1LLabel.setText("占建设用地总");
			JLabel title1RLabel = new JLabel();
			title1RLabel.setText("面积比例分级");
			
			//设置分段信息
			JLabel jLabel0 = new JLabel();
			jLabel0.setText("50% 以下");
			jLabel0.setHorizontalAlignment(JLabel.CENTER);
			JPanel jPanel0 = new JPanel();
			jPanel0.setBackground(new Color(209,182,210));
			jPanel0.setPreferredSize(new Dimension(30, 20));
			JLabel	jLabel1 = new JLabel();
			jLabel1.setText("50% - 60%");
			jLabel1.setHorizontalAlignment(JLabel.CENTER);
			JPanel jPanel1 = new JPanel();
			jPanel1.setBackground(new Color(205,167,183));
			JLabel jLabel2 = new JLabel();
			jLabel2.setText("60% - 70%");
			jLabel2.setHorizontalAlignment(JLabel.CENTER);
			JPanel jPanel2 = new JPanel();
			jPanel2.setBackground(new Color(183,128,151));
			JLabel jLabel3 = new JLabel();
			jLabel3.setText("70% 以上");
			jLabel3.setHorizontalAlignment(JLabel.CENTER);
			JPanel jPanel3 = new JPanel();
			jPanel3.setBackground(new Color(164,97,136));
			//初始化面板并添加组件
			m_jPanelDiagram = new JPanel();
			m_jPanelDiagram.setSize(new Dimension(180, 150));
			m_jPanelDiagram.setLayout(new GridLayout(6, 2));
			m_jPanelDiagram.setBorder(javax.swing.BorderFactory.createLineBorder(
					new java.awt.Color(143, 198, 203), 1));
			m_jPanelDiagram.add(titleLLabel);
			m_jPanelDiagram.add(titleRLabel);
			m_jPanelDiagram.add(title1LLabel);
			m_jPanelDiagram.add(title1RLabel);
			m_jPanelDiagram.add(jPanel0);
			m_jPanelDiagram.add(jLabel0);
			m_jPanelDiagram.add(jPanel1);
			m_jPanelDiagram.add(jLabel1);
			m_jPanelDiagram.add(jPanel2);
			m_jPanelDiagram.add(jLabel2);
			m_jPanelDiagram.add(jPanel3);
			m_jPanelDiagram.add(jLabel3);
		}
		return m_jPanelDiagram;
	}
}

