/** @文件名: IDWMainOfJava.java @创建人：邢健  @创建日期： 2014-7-25 上午8:18:44 */
package com.promise.idw;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.promise.idw.vo.IDWStationRain;
import com.supermap.analyst.spatialanalyst.SurfaceAnalyst;
import com.supermap.analyst.spatialanalyst.SurfaceExtractParameter;
import com.supermap.data.CursorType;
import com.supermap.data.DatasetVector;
import com.supermap.data.Datasource;
import com.supermap.data.Feature;
import com.supermap.data.GeoRegion;
import com.supermap.data.Recordset;
import com.supermap.data.Workspace;
import com.supermap.data.WorkspaceConnectionInfo;
import com.supermap.data.WorkspaceType;


/**   
 * @类名: IDWMainOfJava.java 
 * @包名: com.promise.idw 
 * @描述: TODO 
 * @作者: xingjian xingjian@yeah.net   
 * @日期:2014-7-25 上午8:18:44 
 * @版本: V1.0   
 */
public class IDWMainOfJava {

	private static List<IDWStationRain> list = new ArrayList<IDWStationRain>();
	private static List<Double> zList = new ArrayList<Double>();
	private String fileURL = "com\\promise\\idw\\idw.xls";
	private String idwFileURL  = "E:/工作目录/山洪灾害预警/省级平台/map/idw.smwu";
	public static Workspace workspace = null;
	public static DatasetVector dataset = null;
	public static DatasetVector datasetClip = null;
	public static DatasetVector idwresultRegion = null;
	public static WorkspaceConnectionInfo workspaceConnectionInfo = new WorkspaceConnectionInfo();
	public static GeoRegion clipGeoRegion = null;
	public static Datasource datasource = null;
	public IDWMainOfJava(){
		initList();
	}
	
	public void initList(){
		try {
			File file = new File(this.getClass().getResource("/").getPath()+fileURL); 
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
			HSSFWorkbook wb;
			wb = new HSSFWorkbook(in);
			for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
				HSSFSheet st = wb.getSheetAt(sheetIndex);
				for (int rowIndex = 0; rowIndex <= st.getLastRowNum(); rowIndex++) {
					HSSFRow row = st.getRow(rowIndex);
					if(row == null){
						continue;
					}
		            String stcd = row.getCell(0).getStringCellValue();
		            String stnm = row.getCell(1).getStringCellValue();
		            double lgtd = row.getCell(2).getNumericCellValue();
		            double lttd = row.getCell(3).getNumericCellValue();
		            double zValue = row.getCell(4).getNumericCellValue();
		            String adcd = row.getCell(5).getStringCellValue();
		            list.add(new IDWStationRain(lgtd, lttd, zValue, stnm,stcd,adcd));
		            zList.add(zValue);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public Recordset getRecordset(){
		workspace = new Workspace();
        workspaceConnectionInfo.setType(WorkspaceType.SMWU);
        workspaceConnectionInfo.setServer(idwFileURL);
        workspace.open(workspaceConnectionInfo);
        datasource = workspace.getDatasources().get(0);
        dataset = (DatasetVector) datasource.getDatasets().get("idw");
        Recordset recordset = dataset.getRecordset(false, CursorType.DYNAMIC);
        datasetClip = (DatasetVector) datasource.getDatasets().get("prov");
        Recordset recordsetClip = datasetClip.query("SmID=1",CursorType.STATIC);
        idwresultRegion = (DatasetVector) datasource.getDatasets().get("IDWRegion");
        clipGeoRegion = (GeoRegion)recordsetClip.getFeature().getGeometry();
        
//        for(IDWStationRain idwStationRain : list){
//        	GeoPoint geoPoint = new GeoPoint(idwStationRain.getLgtd(),idwStationRain.getLttd());
//        	Map<String,Object> hashMap = new HashMap<String, Object>();
//        	hashMap.put("RAINVALUE", idwStationRain.getRainValue());
//        	hashMap.put("STNM", idwStationRain.getStnm());
//        	hashMap.put("STCD", idwStationRain.getStcd());
//        	hashMap.put("ADCD", idwStationRain.getAdcd());
//        	recordset.addNew(geoPoint, hashMap);
//        	recordset.update();
//        }
        return recordset;
	}
	
	public void executeEnd(){
		dataset.close();
        workspaceConnectionInfo.dispose();
        workspace.close();
        workspace.dispose();
	}
	
	public void executeIDW(){
		SurfaceExtractParameter surfaceExtractParameter = new SurfaceExtractParameter();
		surfaceExtractParameter.setInterval(5);
		Recordset recordset = getRecordset();
		System.out.println("数据集要素个数 : "+recordset.getRecordCount());
		long start = System.currentTimeMillis();
		System.out.println("开始提取等值面......");
		String zValueFieldName = "RAINVALUE";
		double resolution = 0.001148;
		GeoRegion[] result = SurfaceAnalyst.extractIsoregion(surfaceExtractParameter, recordset, zValueFieldName,resolution,clipGeoRegion);
		Recordset recordSet = idwresultRegion.getRecordset(true, CursorType.DYNAMIC);
		for(int i=0;i<result.length;i++){
			GeoRegion gr = result[i];
			recordSet.addNew(gr);
			recordSet.update();
		}
		long end = System.currentTimeMillis();
		System.out.println("提取等值面结束......");
		System.out.println("用时:"+(end-start)/1000);
		executeEnd();
	}
	
	public void executeIDW2(){
		SurfaceExtractParameter surfaceExtractParameter = new SurfaceExtractParameter();
		surfaceExtractParameter.setExpectedZValues(new double[]{5,10,20,100});
//		surfaceExtractParameter.setInterval(5);
		Recordset recordset = getRecordset();
		System.out.println("数据集要素个数 : "+recordset.getRecordCount());
		long start = System.currentTimeMillis();
		System.out.println("开始提取等值面......");
		String zValueFieldName = "RAINVALUE";
		double resolution = 0.001148;
		DatasetVector result = SurfaceAnalyst.extractIsoregion(surfaceExtractParameter, recordset, zValueFieldName,datasource,"idwresult3",resolution,clipGeoRegion);
//		Map<Integer,Feature> mapresult = result.getAllFeatures();
//	    Set<Integer> key = mapresult.keySet();
//        for (Iterator it = key.iterator(); it.hasNext();) {
//        	Integer s = (Integer) it.next();
//            Feature feature = mapresult.get(s);
//            System.out.println(feature.toString());
//        }
		long end = System.currentTimeMillis();
		System.out.println("提取等值面结束......");
		System.out.println("用时:"+(end-start)/1000);
		executeEnd();
	}
	
	public static void main(String[] args) {
		IDWMainOfJava main = new IDWMainOfJava();
		main.executeIDW2();
	}

}
