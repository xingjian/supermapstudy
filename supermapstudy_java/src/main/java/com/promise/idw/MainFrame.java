/** @文件名: MainFrame.java @创建人：邢健  @创建日期： 2014-7-28 上午11:32:25 */
package com.promise.idw;

/**   
 * @类名: MainFrame.java 
 * @包名: com.promise.idw 
 * @描述: TODO 
 * @作者: xingjian xingjian@yeah.net   
 * @日期:2014-7-28 上午11:32:25 
 * @版本: V1.0   
 */

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import com.supermap.data.Workspace;
import com.supermap.ui.MapControl;

/**
 * <p>
 * Title:地图输出
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
 * 1、范例简介：示范如何使用SceneControl进行三维量算
 * 2、示例数据：安装目录\SampleData\City\Changchun.smwu 
 * 3、关键类型/成员: 
 * 		MapControl.getMap 方法
 * 		MapControl.setWaitCursorEnabled	方法
 *      Map.outputMapToBMP 方法
 *      Map.outputMapToEPS 方法
 *      Map.outputMapToPNG 方法 
 * 4、使用步骤：
 *   (1)点击相应的出图按钮出各种格式的图片
 *   (2)出PNG图片根据提示选择出的图片是否背景透明
 * ------------------------------------------------------------------------------
 * ============================================================================>
 * </p> 
 * 
 * <p>
 * Company: 北京超图软件股份有限公司
 * </p>
 * 
 */

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel m_contentPane;

	private JToolBar m_jToolBar;

	private JButton m_outToBMPButton;

	private JButton m_outToPNGButton;

	private JButton m_outToEPSButton;

	private SampleRun m_sampleRun;

	private Workspace m_workspace;

	private MapControl m_mapControl;

	/**
	 * 程序入口点
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainFrame thisClass = new MainFrame();
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				thisClass.setVisible(true);
			}
		});
	}

	/**
	 * 构造函数
	 */
	public MainFrame() {
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
		this.setTitle("地图输出");
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			// 在主窗体加载时，初始化SampleRun类型，来完成功能的展现
			public void windowOpened(java.awt.event.WindowEvent e) {
				m_workspace = new Workspace();
				m_sampleRun = new SampleRun(m_workspace,m_mapControl);
			}

			// 在窗体关闭时，需要释放相关的资源
			public void windowClosing(java.awt.event.WindowEvent e) {
				m_mapControl.dispose();
				m_workspace.dispose();
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
			m_contentPane.add(getJToolBar(), BorderLayout.NORTH);
			m_contentPane.add(getMapControl(), BorderLayout.CENTER);
		}
		return m_contentPane;
	}

	/**
	 * 构建MapControl控件
	 */
	private MapControl getMapControl() {
		if (m_mapControl == null) {
			m_mapControl = new MapControl();
		}
		return m_mapControl;
	}

	/**
	 * 获取m_jToolBar
	 */
	private JToolBar getJToolBar() {
		if (m_jToolBar == null) {
			m_jToolBar = new JToolBar();
			m_jToolBar
					.setLayout(new BoxLayout(getJToolBar(), BoxLayout.X_AXIS));
			m_jToolBar.setFloatable(false);
			m_jToolBar.add(getOutToBMPButton());
			String osName = System.getProperty("os.name").toLowerCase();
			if (osName.startsWith("win")){			
				m_jToolBar.add(getOutToEPSButton());
				}			
			m_jToolBar.add(getOutToPNGButton());
		}
		return m_jToolBar;
	}

	/**
	 * 获取m_outToBMPButton
	 */
	private JButton getOutToBMPButton() {
		if (m_outToBMPButton == null) {
			m_outToBMPButton = new JButton();
			m_outToBMPButton.setText("BMP出图");
			m_outToBMPButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					m_sampleRun.outToBMP();
				}

			});
		}
		return m_outToBMPButton;
	}

	/**
	 * 获取m_outToEPSButton
	 */
	private JButton getOutToEPSButton() {
		if (m_outToEPSButton == null) {
			m_outToEPSButton = new JButton();
			m_outToEPSButton.setText("EPS出图");
			m_outToEPSButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					m_sampleRun.outToEPS();
				}

			});
		}
		return m_outToEPSButton;
	}

	/**
	 * 获取m_outToPNGButton.
	 */
	private JButton getOutToPNGButton() {
		if (m_outToPNGButton == null) {
			m_outToPNGButton = new JButton();
			m_outToPNGButton.setText("PNG出图");
			m_outToPNGButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					m_sampleRun.outToPNG();
				}

			});
		}
		return m_outToPNGButton;
	}
}

