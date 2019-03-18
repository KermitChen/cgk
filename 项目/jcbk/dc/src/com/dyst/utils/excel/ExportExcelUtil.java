package com.dyst.utils.excel;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

import com.dyst.BaseDataMsg.entities.Dictionary;
import com.dyst.BaseDataMsg.entities.Jcd;
import com.dyst.advancedAnalytics.entities.ExcelBeanForDwpz;
import com.dyst.advancedAnalytics.entities.FakeHphmExcelBean;
import com.dyst.advancedAnalytics.entities.FalseHphmExcelBean;
import com.dyst.advancedAnalytics.entities.ResDwpz;
import com.dyst.dispatched.entities.Dispatched;
import com.dyst.earlyWarning.entities.InstructionSign;
import com.dyst.jxkh.entities.BkckEntity;
import com.dyst.jxkh.entities.BklbEntity;
import com.dyst.jxkh.entities.BktjEntity;
import com.dyst.jxkh.entities.BktjItemEntity;
import com.dyst.jxkh.entities.CzfkEntity2;
import com.dyst.jxkh.entities.YjqsEntity2;
import com.dyst.jxkh.entities.YjqsEntity22;
import com.dyst.jxkh.entities.YjtjEntity;
import com.dyst.jxkh.entities.ZlqsEntity2;
import com.dyst.serverMonitor.entities.ExcelBeanForJcdStatus;
import com.dyst.serverMonitor.entities.JCDStatus;
import com.dyst.sjjk.entities.TjEnitity;
import com.dyst.sjjk.entities.YwtjEnitity;
import com.dyst.systemmanage.entities.User;
import com.dyst.utils.CommonUtils;
import com.dyst.utils.excel.bean.DyExcelBean;
import com.dyst.utils.excel.bean.DyjgExcelBean;
import com.dyst.utils.excel.bean.JjhomdExcelBean;
import com.dyst.utils.excel.bean.bktj.BkckExcelBean;
import com.dyst.utils.excel.bean.bktj.BktjExcelBean;
import com.dyst.utils.excel.bean.bukong.BKQueryExcelBean;
import com.dyst.utils.excel.bean.bukong.BkItemBean;
import com.dyst.utils.excel.bean.bukong.CKExcelBean;
import com.dyst.utils.excel.bean.bukong.CKItemBean;
import com.dyst.utils.excel.bean.yj.YjqsExcelBean;
import com.dyst.utils.excel.bean.yj.YjtjExcelBean;

public class ExportExcelUtil {

	/**
	 * 导出红名单到excel
	 * @param excelBeanList 红名单列表
	 * @param outputStream
	 * @throws Exception
	 */
	public static void exportExcel(List<JjhomdExcelBean> excelBeanList,ServletOutputStream outputStream) throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook();//创建工作簿
		//合并单元格样式
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 6);
		//头标题样式
		HSSFCellStyle head_CellStyle = createHSSFCellStyle(workbook, (short)16);
		//行标题样式
//		HSSFCellStyle row_CellStyle = createHSSFCellStyle(workbook, (short)14);
		//设置列标题样式
		HSSFCellStyle col_CellStyle = createTitleCellStyle(workbook, (short)14);
		//普通单元格 带颜色
//		HSSFCellStyle normal_CellStyleWithColor = createNormalCellStyleWithColor(workbook, (short)14, HSSFColor.GREY_25_PERCENT.index);
		//设置普通单元格样式
		HSSFCellStyle normal_CellStyle = createHSSFCellStypeNoBold(workbook,(short)12);
		//创建工作表
		HSSFSheet sheet = workbook.createSheet("红名单申请");
			//加载合并单元格对象
		sheet.addMergedRegion(cellRangeAddress);
		sheet.setDefaultColumnWidth(20);
		//创建行
			//头标题行,第一行第一列
		HSSFRow row1 = sheet.createRow(0);
		HSSFCell cell_1_1 = row1.createCell(0);
		cell_1_1.setCellStyle(head_CellStyle);
		cell_1_1.setCellValue("红名单申请列表");
			//创建列标题,第二行
		HSSFRow row2 = sheet.createRow(1);
		String [] col_titles = {"车牌号码","车辆类型","车辆使用人","申请人","记录状态","审批结果","当前节点"};
		for(int i=0;i<col_titles.length;i++){
			HSSFCell cell_2_x = row2.createCell(i);
			cell_2_x.setCellStyle(col_CellStyle);
			cell_2_x.setCellValue(col_titles[i]);
		}
		//操作单元格，把hmd列表，遍历出来，插入到excel中
		if(excelBeanList!=null){
			for(int j=0;j<excelBeanList.size();j++){
				JjhomdExcelBean jjhomdExcelBean = excelBeanList.get(j);
				HSSFRow row = sheet.createRow(j+2);
				HSSFCell cell_x_1 = row.createCell(0);
				cell_x_1.setCellStyle(normal_CellStyle);
				cell_x_1.setCellValue(jjhomdExcelBean.getJjhomd().getCphid());
				HSSFCell cell_x_2 = row.createCell(1);
				cell_x_2.setCellStyle(normal_CellStyle);
				cell_x_2.setCellValue(jjhomdExcelBean.getCllx());//车辆类型
				HSSFCell cell_x_3 = row.createCell(2);
				cell_x_3.setCellStyle(normal_CellStyle);
				cell_x_3.setCellValue(jjhomdExcelBean.getJjhomd().getClsyz());
				HSSFCell cell_x_4 = row.createCell(3);
				cell_x_4.setCellStyle(normal_CellStyle);
				cell_x_4.setCellValue(jjhomdExcelBean.getJjhomd().getSqrxm());
				HSSFCell cell_x_5 = row.createCell(4);
				cell_x_5.setCellStyle(normal_CellStyle);
				cell_x_5.setCellValue(jjhomdExcelBean.getJlzt());
				HSSFCell cell_x_6 = row.createCell(5);
				cell_x_6.setCellStyle(normal_CellStyle);
				cell_x_6.setCellValue(jjhomdExcelBean.getSpjg());
				HSSFCell cell_x_7 = row.createCell(6);
				cell_x_7.setCellStyle(normal_CellStyle);
				cell_x_7.setCellValue(jjhomdExcelBean.getDqjd());
			}
		}
		//输出
		workbook.write(outputStream);
		workbook.close();
	}
	
	//============ 订阅   导出Excel=============
	public static void ExcelExportForDy(List<DyExcelBean> dyExcelBeanList,ServletOutputStream outputStream)throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时");
		//创建工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		//合并单元格样式
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 7);
		
		//头标题样式
		HSSFCellStyle head_CellStyle = createHSSFCellStyle(workbook, (short)16);
		//行标题样式
//		HSSFCellStyle row_CellStyle = createHSSFCellStyle(workbook, (short)14);
		//设置列标题样式
		HSSFCellStyle col_CellStyle = createTitleCellStyle(workbook, (short)14);
		//普通单元格 带颜色
//		HSSFCellStyle normal_CellStyleWithColor = createNormalCellStyleWithColor(workbook, (short)14, HSSFColor.GREY_25_PERCENT.index);
		//设置普通单元格样式
		HSSFCellStyle normal_CellStyle = createHSSFCellStypeNoBold(workbook,(short)12);
		
		//创建工作表
		HSSFSheet sheet = workbook.createSheet("订阅申请");
		//加载..合并单元格对象
		sheet.addMergedRegion(cellRangeAddress);
		//设置单元格默认宽度
		sheet.setDefaultColumnWidth(20);
		//创建行
		//头标题行,第一行第一列
		HSSFRow row1 = sheet.createRow(0);
		HSSFCell cell_1_1 = row1.createCell(0);
		cell_1_1.setCellStyle(head_CellStyle);
		cell_1_1.setCellValue("订阅申请列表");
		//创建列标题,第二行
		HSSFRow row2 = sheet.createRow(1);
		String [] col_titles = {"订阅人姓名","有效起始时间","有效截止时间","订阅类型","记录状态","审批结果","业务状态","当前节点"};
		for(int i=0;i<col_titles.length;i++){
			HSSFCell cell_2_x = row2.createCell(i);
			cell_2_x.setCellStyle(col_CellStyle);
			cell_2_x.setCellValue(col_titles[i]);
		}
		//操作单元格，把hmd列表，遍历出来，插入到excel中
		if(dyExcelBeanList!=null&&dyExcelBeanList.size()>0){
			for(int j=0;j<dyExcelBeanList.size();j++){
				DyExcelBean bean = dyExcelBeanList.get(j);
				HSSFRow row = sheet.createRow(j+2);
				HSSFCell cell_x_1 = row.createCell(0);//第一列
				cell_x_1.setCellStyle(normal_CellStyle);
				cell_x_1.setCellValue(bean.getDyxx().getJyxm());
				
				HSSFCell cell_x_2 = row.createCell(1);//第二列
				cell_x_2.setCellStyle(normal_CellStyle);
				cell_x_2.setCellValue(dateFormat.format(bean.getDyxx().getQssj()));
				
				HSSFCell cell_x_3 = row.createCell(2);
				cell_x_3.setCellStyle(normal_CellStyle);
				cell_x_3.setCellValue(dateFormat.format(bean.getDyxx().getJzsj()));
				
				HSSFCell cell_x_4 = row.createCell(3);
				cell_x_4.setCellStyle(normal_CellStyle);
				cell_x_4.setCellValue(bean.getDylx());
				
				HSSFCell cell_x_5 = row.createCell(4);
				cell_x_5.setCellStyle(normal_CellStyle);
				cell_x_5.setCellValue(bean.getJlzt());
				
				HSSFCell cell_x_6 = row.createCell(5);
				cell_x_6.setCellStyle(normal_CellStyle);
				cell_x_6.setCellValue(bean.getSpjg());
				
				HSSFCell cell_x_7 = row.createCell(6);
				cell_x_7.setCellStyle(normal_CellStyle);
				cell_x_7.setCellValue(bean.getYwzt());
				
				HSSFCell cell_x_8 = row.createCell(7);
				cell_x_8.setCellStyle(normal_CellStyle);
				cell_x_8.setCellValue(bean.getDqjd());
			}
		}
		//输出
		workbook.write(outputStream);
		workbook.close();
	}
	
	//=============dyjg订阅结果列表导出excel===============
	public static void ExcelExportForDyjg(List<DyjgExcelBean> dyjgExcelBeanList,ServletOutputStream outputStream) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时");
		//创建工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		//合并单元格样式
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 5);
		//头标题样式
		HSSFCellStyle head_CellStyle = createHSSFCellStyle(workbook, (short)16);
		//行标题样式
//		HSSFCellStyle row_CellStyle = createHSSFCellStyle(workbook, (short)14);
		//设置列标题样式
		HSSFCellStyle col_CellStyle = createTitleCellStyle(workbook, (short)14);
		//普通单元格 带颜色
//		HSSFCellStyle normal_CellStyleWithColor = createNormalCellStyleWithColor(workbook, (short)14, HSSFColor.GREY_25_PERCENT.index);
		//设置普通单元格样式
		HSSFCellStyle normal_CellStyle = createHSSFCellStypeNoBold(workbook,(short)12);
		//创建工作表
		HSSFSheet sheet = workbook.createSheet("订阅结果");
		//加载..合并单元格对象
		sheet.addMergedRegion(cellRangeAddress);
		//设置单元格默认宽度
		sheet.setDefaultColumnWidth(20);
		//创建行
		//头标题行,第一行第一列
		HSSFRow row1 = sheet.createRow(0);
		HSSFCell cell_1_1 = row1.createCell(0);
		cell_1_1.setCellStyle(head_CellStyle);
		cell_1_1.setCellValue("订阅申请列表");
		//创建列标题,第二行
		HSSFRow row2 = sheet.createRow(1);
		String [] col_titles = {"车牌号码","车牌颜色","订阅类型","识别监测点","车道","识别时间"};
		for(int i=0;i<col_titles.length;i++){
			HSSFCell cell_2_x = row2.createCell(i);
			cell_2_x.setCellStyle(col_CellStyle);
			cell_2_x.setCellValue(col_titles[i]);
		}
		//遍历订阅结果列表，写入excel
		if(dyjgExcelBeanList!=null&&dyjgExcelBeanList.size()>0){
			for(int j=0;j<dyjgExcelBeanList.size();j++){
				DyjgExcelBean bean = dyjgExcelBeanList.get(j);
				HSSFRow row = sheet.createRow(j+2);
				HSSFCell cell_x_1 = row.createCell(0);//第一列
				cell_x_1.setCellStyle(normal_CellStyle);
				cell_x_1.setCellValue(bean.getDyjg().getHphm());
				HSSFCell cell_x_2 = row.createCell(1);//第二列
				cell_x_2.setCellStyle(normal_CellStyle);
				cell_x_2.setCellValue(bean.getHpzl());
				HSSFCell cell_x_3 = row.createCell(2);//第二列
				cell_x_3.setCellStyle(normal_CellStyle);
				cell_x_3.setCellValue(bean.getDylx());
				HSSFCell cell_x_4 = row.createCell(3);//第四列
				cell_x_4.setCellStyle(normal_CellStyle);
				cell_x_4.setCellValue(bean.getJcdmc());
				HSSFCell cell_x_5 = row.createCell(4);//第五列
				cell_x_5.setCellStyle(normal_CellStyle);
				cell_x_5.setCellValue(bean.getDyjg().getCdid());
				HSSFCell cell_x_6 = row.createCell(5);//第六列
				cell_x_6.setCellStyle(normal_CellStyle);
				cell_x_6.setCellValue(dateFormat.format(bean.getDyjg().getTgsj()));
			}
		}
		//输出
		workbook.write(outputStream);
		workbook.close();
	}
	
	//================  布控统计     导出Excel  =========================
	public static void excelExportForBktj(User user, BktjExcelBean bean,ServletOutputStream outputStream) throws IOException{
		//创建工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		//合并单元格样式
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 5);
		//头标题样式
		HSSFCellStyle head_CellStyle = createHSSFCellStyle(workbook, (short)16);
		//行标题样式
		HSSFCellStyle row_CellStyle = createHSSFCellStyle(workbook, (short)14);
		//设置列标题样式
		HSSFCellStyle col_CellStyle = createTitleCellStyle(workbook, (short)14);
		//普通单元格 带颜色
		HSSFCellStyle normal_CellStyleWithColor = createNormalCellStyleWithColor(workbook, (short)14, HSSFColor.GREY_25_PERCENT.index);
		//设置普通单元格样式
		HSSFCellStyle normal_CellStyle = createHSSFCellStypeNoBold(workbook,(short)12);
		//设置普通单元格样式
		HSSFCellStyle normal_CellStyle_bktj = createHSSFCellStypeNoBoldForBktj(workbook,(short)12);
		//创建工作表
		HSSFSheet sheet = workbook.createSheet("布控信息统计表");
		//加载..合并单元格对象
		sheet.addMergedRegion(cellRangeAddress);//标题
		//设置单元格默认宽度
		sheet.setDefaultColumnWidth(26);
		//创建行
		//头标题行,第一行第一列
		HSSFRow row1 = sheet.createRow(0);
		row1.setHeight((short)1000);
		HSSFCell cell_1_1 = row1.createCell(0);
		cell_1_1.setCellStyle(head_CellStyle);
		cell_1_1.setCellValue("布控信息统计表");
		cellRangeAddressSetBorder(cellRangeAddress, sheet, workbook);
		
		//第二行 
		HSSFRow row2 = sheet.createRow(1);
		HSSFCell cell_2_1 = row2.createCell(0);
		row2.setHeight((short)400);
		cell_2_1.setCellStyle(normal_CellStyle_bktj);
		cell_2_1.setCellValue("统计单位:" + user.getDeptName() + "      统计人:" + user.getUserName() 
				+ "      联系电话:" + (user.getTelPhone() != null && "".equals(user.getTelPhone().trim())?user.getTelPhone():"")
				+ "      统计时间范围:" + bean.getStartTime() + "至" + bean.getEndTime());
		CellRangeAddress cellRangeAddress2 = new CellRangeAddress(1, 1, 0, 5);
		sheet.addMergedRegion(cellRangeAddress2);
		cellRangeAddressSetBorder(cellRangeAddress2, sheet, workbook);
		
		//第三行
		HSSFRow row3 = sheet.createRow(2);
		HSSFCell cell_3_1 = row3.createCell(0);
		cell_3_1.setCellStyle(col_CellStyle);
		cell_3_1.setCellValue("布控大类");
		HSSFCell cell_3_2 = row3.createCell(1);
		cell_3_2.setCellStyle(col_CellStyle);
		cell_3_2.setCellValue("布控小类");
		HSSFCell cell_3_3 = row3.createCell(2);
		cell_3_3.setCellValue("本地市布控数");
		cell_3_3.setCellStyle(col_CellStyle);
		HSSFCell cell_3_4 = row3.createCell(3);
		cell_3_4.setCellStyle(col_CellStyle);
		cell_3_4.setCellValue("接处警平台布控数");
		HSSFCell cell_3_5 = row3.createCell(4);
		cell_3_5.setCellStyle(col_CellStyle);
		cell_3_5.setCellValue("省厅联动布控数");
		HSSFCell cell_3_6 = row3.createCell(5);
		cell_3_6.setCellStyle(col_CellStyle);
		cell_3_6.setCellValue("总数");
		
		//设置大类
		List<BktjEntity> list = bean.getList();
		int temp = 3;
		int bdbkCountAll = 0;
    	int bjfwtCountAll = 0;
    	int ldbkCountAll = 0;
		for(int i=0;i < list.size();i++){
			int height = 0 ;
			height = list.get(i).getList().size();
			CellRangeAddress cellRangeAddressBkdl1 = new CellRangeAddress(temp, temp+height, 0, 0);
			sheet.addMergedRegion(cellRangeAddressBkdl1);
			
			List<BktjItemEntity> itemList = list.get(i).getList();
			int bdbkCount = 0;
    		int bjfwtCount = 0;
    		int ldbkCount = 0;
			for(int j=0;j < itemList.size();j++){
				BktjItemEntity item = itemList.get(j);
				HSSFRow row = sheet.createRow(temp+j);
				if(j == 0){
					HSSFCell cell_x_1 = row.createCell(0);
					cell_x_1.setCellStyle(row_CellStyle);
					cell_x_1.setCellValue(list.get(i).getTypeDesc());//设置大类
				}
				
				//设置小类   条目
				HSSFCell cell_x_2 = row.createCell(1);//布控类型
				cell_x_2.setCellStyle(normal_CellStyle);
				cell_x_2.setCellValue(item.getTypeDesc());
				HSSFCell cell_x_3 = row.createCell(2);//本地布控数
				cell_x_3.setCellStyle(normal_CellStyle);
				cell_x_3.setCellValue(item.getBdbk());
				bdbkCount = bdbkCount + item.getBdbk();
				HSSFCell cell_x_4 = row.createCell(3);//110报警服务台数
				cell_x_4.setCellStyle(normal_CellStyle);
				cell_x_4.setCellValue(item.getBjfwt());
				bjfwtCount = bjfwtCount + item.getBjfwt();
				HSSFCell cell_x_5 = row.createCell(4);//联动布控你数
				cell_x_5.setCellStyle(normal_CellStyle);
				cell_x_5.setCellValue(item.getLdbk());
				ldbkCount = ldbkCount + item.getLdbk();
				HSSFCell cell_x_6 = row.createCell(5);//总数
				cell_x_6.setCellStyle(normal_CellStyle);
				cell_x_6.setCellValue(item.getSubtotal());
			}
			
			temp += height;
			HSSFRow rowSubTotal = sheet.createRow(temp);
			HSSFCell cell_subtotal_2 = rowSubTotal.createCell(1);
			cell_subtotal_2.setCellStyle(normal_CellStyle);
			cell_subtotal_2.setCellValue("小计");
			HSSFCell cell_subtotal_3 = rowSubTotal.createCell(2);
			cell_subtotal_3.setCellStyle(normal_CellStyle);
			cell_subtotal_3.setCellValue(bdbkCount);
			HSSFCell cell_subtotal_4 = rowSubTotal.createCell(3);
			cell_subtotal_4.setCellStyle(normal_CellStyle);
			cell_subtotal_4.setCellValue(bjfwtCount);
			HSSFCell cell_subtotal_5 = rowSubTotal.createCell(4);
			cell_subtotal_5.setCellValue(ldbkCount);
			cell_subtotal_5.setCellStyle(normal_CellStyle);
			HSSFCell cell_subtotal_6 = rowSubTotal.createCell(5);
			cell_subtotal_6.setCellStyle(normal_CellStyle);
			cell_subtotal_6.setCellValue(bdbkCount + bjfwtCount + ldbkCount);
			temp += 1;
			
			//计算总数
			bdbkCountAll = bdbkCountAll + bdbkCount;
			bjfwtCountAll = bjfwtCountAll + bjfwtCount;
			ldbkCountAll = ldbkCountAll + ldbkCount;
		}
		
		//添加总计
		CellRangeAddress cellRangeAddress_total = new CellRangeAddress(temp, temp, 0, 1);
		sheet.addMergedRegion(cellRangeAddress_total);
		HSSFRow row_total = sheet.createRow(temp);
		HSSFCell cell_total_1 = row_total.createCell(0);
		cell_total_1.setCellStyle(normal_CellStyleWithColor);
		cell_total_1.setCellValue("总计");
		cellRangeAddressSetBorder(cellRangeAddress_total, sheet, workbook);
		HSSFCell cell_total_3 = row_total.createCell(2);
		cell_total_3.setCellStyle(normal_CellStyleWithColor);
		cell_total_3.setCellValue(bdbkCountAll);
		HSSFCell cell_total_4 = row_total.createCell(3);
		cell_total_4.setCellStyle(normal_CellStyleWithColor);
		cell_total_4.setCellValue(bjfwtCountAll);
		HSSFCell cell_total_5 = row_total.createCell(4);
		cell_total_5.setCellStyle(normal_CellStyleWithColor);
		cell_total_5.setCellValue(ldbkCountAll);
		HSSFCell cell_total_6 = row_total.createCell(5);
		cell_total_6.setCellStyle(normal_CellStyleWithColor);
		cell_total_6.setCellValue(bdbkCountAll + bjfwtCountAll + ldbkCountAll);
		//输出
		workbook.write(outputStream);
		workbook.close();
	}
	
	//===================预警统计导出Excel========================
	public static void excelExportForYjtj(User user, YjtjExcelBean bean,ServletOutputStream outputStream) throws Exception{
		//创建工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		//合并单元格样式
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 11);
		//头标题样式
		HSSFCellStyle head_CellStyle = createHSSFCellStyle(workbook, (short)16);
		//行标题样式
		HSSFCellStyle row_CellStyle = createHSSFCellStyle(workbook, (short)10);
		//设置列标题样式
		HSSFCellStyle col_CellStyle = createTitleCellStyle(workbook, (short)10);
		//普通单元格 带颜色
		HSSFCellStyle normal_CellStyleWithColor = createNormalCellStyleWithColor(workbook, (short)10, HSSFColor.GREY_25_PERCENT.index);
		//设置普通单元格样式
		HSSFCellStyle normal_CellStyle = createHSSFCellStypeNoBold(workbook,(short)10);
		//设置普通单元格样式
		HSSFCellStyle normal_CellStyle_bjtj = createHSSFCellStypeNoBoldForBktj(workbook,(short)10);
		//创建工作表
		HSSFSheet sheet = workbook.createSheet("报警信息统计表");
		//加载..合并单元格对象
		sheet.addMergedRegion(cellRangeAddress);//标题
		//设置单元格默认宽度
		sheet.setDefaultColumnWidth(13);
		//创建行
		//头标题行,第一行第一列
		HSSFRow row1 = sheet.createRow(0);
		row1.setHeight((short)1000);
		HSSFCell cell_1_1 = row1.createCell(0);
		cell_1_1.setCellStyle(head_CellStyle);
		cell_1_1.setCellValue("报警信息统计表");
		cellRangeAddressSetBorder(cellRangeAddress, sheet, workbook);
		
		//第二行
		HSSFRow row2 = sheet.createRow(1);
		HSSFCell cell_2_1 = row2.createCell(0);
		row2.setHeight((short)400);
		cell_2_1.setCellStyle(normal_CellStyle_bjtj);
		cell_2_1.setCellValue("统计单位:" + user.getDeptName() + "      统计人:" + user.getUserName() 
				+ "      联系电话:" + (user.getTelPhone() != null && "".equals(user.getTelPhone().trim())?user.getTelPhone():"")
				+ "      统计时间范围:" + bean.getStartTime() + "至" + bean.getEndTime());
		CellRangeAddress cellRangeAddress2 = new CellRangeAddress(1, 1, 0, 11);
		sheet.addMergedRegion(cellRangeAddress2);
		cellRangeAddressSetBorder(cellRangeAddress2, sheet, workbook);
		
		//第三行，创建 列表标题
		HSSFRow row3 = sheet.createRow(2);
		HSSFCell cell_3_1 = row3.createCell(0);
		cell_3_1.setCellStyle(col_CellStyle);
		cell_3_1.setCellValue("报警大类");
		HSSFCell cell_3_2 = row3.createCell(1);
		cell_3_2.setCellStyle(col_CellStyle);
		cell_3_2.setCellValue("报警类型");
		HSSFCell cell_3_3 = row3.createCell(2);
		cell_3_3.setCellStyle(col_CellStyle);
		cell_3_3.setCellValue("报警总数");
		HSSFCell cell_3_4 = row3.createCell(3);
		cell_3_4.setCellStyle(col_CellStyle);
		cell_3_4.setCellValue("及时签收数");
		HSSFCell cell_3_5 = row3.createCell(4);
		cell_3_5.setCellStyle(col_CellStyle);
		cell_3_5.setCellValue("超时签收数");
		HSSFCell cell_3_6 = row3.createCell(5);
		cell_3_6.setCellStyle(col_CellStyle);
		cell_3_6.setCellValue("未签收数");
		HSSFCell cell_3_7 = row3.createCell(6);
		cell_3_7.setCellStyle(col_CellStyle);
		cell_3_7.setCellValue("有效数");
		HSSFCell cell_3_8 = row3.createCell(7);
		cell_3_8.setCellStyle(col_CellStyle);
		cell_3_8.setCellValue("无效数");
		HSSFCell cell_3_9 = row3.createCell(8);
		cell_3_9.setCellStyle(col_CellStyle);
		cell_3_9.setCellValue("已下达拦截指令数");
		HSSFCell cell_3_10 = row3.createCell(9);
		cell_3_10.setCellStyle(col_CellStyle);
		cell_3_10.setCellValue("拦截单位及时反馈数");
		HSSFCell cell_3_11 = row3.createCell(10);
		cell_3_11.setCellStyle(col_CellStyle);
		cell_3_11.setCellValue("拦截单位超时反馈数");
		HSSFCell cell_3_12 = row3.createCell(11);
		cell_3_12.setCellStyle(col_CellStyle);
		cell_3_12.setCellValue("拦截单位未反馈数");
		
		int temp = 3;
		//设置大类
		List<YjtjEntity> list = bean.getList();
		//设置普通单元格样式
		for(int i=0;i<list.size();i++){
			int height = 0;
			List<BklbEntity> itemList = list.get(i).getBklbList();
			height = itemList.size();
			//创建合并单元格
			CellRangeAddress cellRangeAddressBkdl1 = new CellRangeAddress(temp, temp+height, 0, 0);
			sheet.addMergedRegion(cellRangeAddressBkdl1);
			for(int j=0;j<itemList.size();j++){
				BklbEntity item = itemList.get(j);
				HSSFRow row = sheet.createRow(temp+j);
				if(j == 0){
					HSSFCell cell_x_1 = row.createCell(0);
					cell_x_1.setCellStyle(row_CellStyle);
					cell_x_1.setCellValue(list.get(i).getTypeDesc());
				}
				cellRangeAddressSetBorder(cellRangeAddressBkdl1, sheet, workbook);
				HSSFCell cell_x_2 = row.createCell(1);
				cell_x_2.setCellStyle(normal_CellStyle);
				cell_x_2.setCellValue(item.getTypeDesc());
				HSSFCell cell_x_3 = row.createCell(2);
				cell_x_3.setCellStyle(normal_CellStyle);
				cell_x_3.setCellValue(item.getBjzs());
				HSSFCell cell_x_4 = row.createCell(3);
				cell_x_4.setCellStyle(normal_CellStyle);
				cell_x_4.setCellValue(item.getBjjsqs());
				HSSFCell cell_x_5 = row.createCell(4);
				cell_x_5.setCellStyle(normal_CellStyle);
				cell_x_5.setCellValue(item.getBjcsqs());
				HSSFCell cell_x_6 = row.createCell(5);
				cell_x_6.setCellStyle(normal_CellStyle);
				cell_x_6.setCellValue(item.getBjwqs());
				HSSFCell cell_x_7 = row.createCell(6);
				cell_x_7.setCellStyle(normal_CellStyle);
				cell_x_7.setCellValue(item.getBjyxs());
				HSSFCell cell_x_8 = row.createCell(7);
				cell_x_8.setCellStyle(normal_CellStyle);
				cell_x_8.setCellValue(item.getBjwxs());
				HSSFCell cell_x_9 = row.createCell(8);
				cell_x_9.setCellStyle(normal_CellStyle);
				cell_x_9.setCellValue(item.getZlzs());
				HSSFCell cell_x_10 = row.createCell(9);
				cell_x_10.setCellStyle(normal_CellStyle);
				cell_x_10.setCellValue(item.getZljsfks());
				HSSFCell cell_x_11 = row.createCell(10);
				cell_x_11.setCellStyle(normal_CellStyle);
				cell_x_11.setCellValue(item.getZlwjsfks());
				HSSFCell cell_x_12 = row.createCell(11);
				cell_x_12.setCellStyle(normal_CellStyle);
				cell_x_12.setCellValue(item.getZlwfks());
			}
			temp += height;
			
			Map<String, Integer> subtotal_map = list.get(i).getSubTotalList();//小计
			HSSFRow row_subtotal = sheet.createRow(temp);
			HSSFCell cell_subtotal_2 = row_subtotal.createCell(1);
			cell_subtotal_2.setCellStyle(normal_CellStyle);
			cell_subtotal_2.setCellValue("小计");
			HSSFCell cell_subtotal_3 = row_subtotal.createCell(2);
			cell_subtotal_3.setCellStyle(normal_CellStyle);
			cell_subtotal_3.setCellValue(subtotal_map.get("xj_bjzs"));
			HSSFCell cell_subtotal_4 = row_subtotal.createCell(3);
			cell_subtotal_4.setCellStyle(normal_CellStyle);
			cell_subtotal_4.setCellValue(subtotal_map.get("xj_bjjsqs"));
			HSSFCell cell_subtotal_5 = row_subtotal.createCell(4);
			cell_subtotal_5.setCellStyle(normal_CellStyle);
			cell_subtotal_5.setCellValue(subtotal_map.get("xj_bjcsqs"));
			HSSFCell cell_subtotal_6 = row_subtotal.createCell(5);
			cell_subtotal_6.setCellStyle(normal_CellStyle);
			cell_subtotal_6.setCellValue(subtotal_map.get("xj_bjwqs"));
			HSSFCell cell_subtotal_7 = row_subtotal.createCell(6);
			cell_subtotal_7.setCellStyle(normal_CellStyle);
			cell_subtotal_7.setCellValue(subtotal_map.get("xj_bjyxs"));
			HSSFCell cell_subtotal_8 = row_subtotal.createCell(7);
			cell_subtotal_8.setCellStyle(normal_CellStyle);
			cell_subtotal_8.setCellValue(subtotal_map.get("xj_bjwxs"));
			HSSFCell cell_subtotal_9 = row_subtotal.createCell(8);
			cell_subtotal_9.setCellStyle(normal_CellStyle);
			cell_subtotal_9.setCellValue(subtotal_map.get("xj_zlzs"));
			HSSFCell cell_subtotal_10 = row_subtotal.createCell(9);
			cell_subtotal_10.setCellStyle(normal_CellStyle);
			cell_subtotal_10.setCellValue(subtotal_map.get("xj_zljsfks"));
			HSSFCell cell_subtotal_11 = row_subtotal.createCell(10);
			cell_subtotal_11.setCellStyle(normal_CellStyle);
			cell_subtotal_11.setCellValue(subtotal_map.get("xj_zlwjsfks"));
			HSSFCell cell_subtotal_12 = row_subtotal.createCell(11);
			cell_subtotal_12.setCellStyle(normal_CellStyle);
			cell_subtotal_12.setCellValue(subtotal_map.get("xj_zlwfks"));
			temp += 1;
		}
		
		//总计
		Map<String, Integer> total = bean.getTotal();
		CellRangeAddress cellRangeAddress_total = new CellRangeAddress(temp, temp, 0, 1);
		sheet.addMergedRegion(cellRangeAddress_total);
		HSSFRow row_total = sheet.createRow(temp);
		HSSFCell cell_total_1 = row_total.createCell(0);
		cell_total_1.setCellStyle(col_CellStyle);
		cell_total_1.setCellValue("总计");
		cellRangeAddressSetBorder(cellRangeAddress_total, sheet, workbook);
 
		HSSFCell cell_total_3 = row_total.createCell(2);
		cell_total_3.setCellStyle(normal_CellStyleWithColor);
		cell_total_3.setCellValue(total.get("zj_bjzs"));
		HSSFCell cell_total_4 = row_total.createCell(3);
		cell_total_4.setCellStyle(normal_CellStyleWithColor);
		cell_total_4.setCellValue(total.get("zj_bjjsqs"));
		HSSFCell cell_total_5 = row_total.createCell(4);
		cell_total_5.setCellStyle(normal_CellStyleWithColor);
		cell_total_5.setCellValue(total.get("zj_bjcsqs"));
		HSSFCell cell_total_6 = row_total.createCell(5);
		cell_total_6.setCellStyle(normal_CellStyleWithColor);
		cell_total_6.setCellValue(total.get("zj_bjwqs"));
		HSSFCell cell_total_7 = row_total.createCell(6);
		cell_total_7.setCellStyle(normal_CellStyleWithColor);
		cell_total_7.setCellValue(total.get("zj_bjyxs"));
		HSSFCell cell_total_8 = row_total.createCell(7);
		cell_total_8.setCellStyle(normal_CellStyleWithColor);
		cell_total_8.setCellValue(total.get("zj_bjwxs"));
		HSSFCell cell_total_9 = row_total.createCell(8);
		cell_total_9.setCellStyle(normal_CellStyleWithColor);
		cell_total_9.setCellValue(total.get("zj_zlzs"));
		HSSFCell cell_total_10 = row_total.createCell(9);
		cell_total_10.setCellStyle(normal_CellStyleWithColor);
		cell_total_10.setCellValue(total.get("zj_zljsfks"));
		HSSFCell cell_total_11 = row_total.createCell(10);
		cell_total_11.setCellStyle(normal_CellStyleWithColor);
		cell_total_11.setCellValue(total.get("zj_zlwjsfks"));
		HSSFCell cell_total_12 = row_total.createCell(11);
		cell_total_12.setCellStyle(normal_CellStyleWithColor);
		cell_total_12.setCellValue(total.get("zj_zlwfks"));
		
		//写出
		workbook.write(outputStream);
		workbook.close();
	}
	
	/**
	 * 导出Excel for JXKH->yjqs  预警签收查询
	 * @throws Exception 
	 */
	public static void exportExcelForYjqs(YjqsExcelBean bean,ServletOutputStream outputStream) throws Exception{
		//创建工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		//创建工作表
		HSSFSheet sheet = workbook.createSheet("深圳市缉查布控系统预警处置工作情况统计表");
		//合并单元格样式，excel 标题
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 19);
		//头标题样式
		HSSFCellStyle head_CellStyle = createHSSFCellStyle(workbook, (short)16);
		//设置列标题样式
		HSSFCellStyle col_CellStyle = createTitleCellStyle(workbook, (short)13);
		//设置普通单元格样式
		HSSFCellStyle normal_CellStyle = createHSSFCellStypeNoBold(workbook,(short)12);
		//普通单元格 带颜色
		HSSFCellStyle normal_CellStyleWithColor = createNormalCellStyleWithColor(workbook, (short)14, HSSFColor.GREY_25_PERCENT.index);
		//加载..合并单元格对象
		sheet.addMergedRegion(cellRangeAddress);//标题
		//设置单元格默认宽度
		sheet.setDefaultColumnWidth(8);
		//默认单元格 高度
		sheet.setDefaultRowHeightInPoints(25);
		//设置第二列的宽度
		sheet.setColumnWidth(0, 5*256);
		sheet.setColumnWidth(1, 23*256);
		sheet.setColumnWidth(6, 10*256);
		sheet.setColumnWidth(7, 10*256);
		sheet.setColumnWidth(12, 10*256);
		sheet.setColumnWidth(13, 10*256);
		sheet.setColumnWidth(18, 10*256);
		sheet.setColumnWidth(19, 10*256);
		
		//创建行
		//头标题行,第一行第一列
		HSSFRow row1 = sheet.createRow(0);
		row1.setHeight((short)1000);
		HSSFCell cell_1_1 = row1.createCell(0);
		cell_1_1.setCellStyle(head_CellStyle);
		cell_1_1.setCellValue("深圳市缉查布控系统预警处置工作情况统计表");
		cellRangeAddressSetBorder(cellRangeAddress, sheet, workbook);
		
		//第二行
		HSSFRow row2 = sheet.createRow(1);
		row2.setHeightInPoints(25);
		CellRangeAddress row2_cellRangeAddress_1 = new CellRangeAddress(1, 1, 0, 13);
		sheet.addMergedRegion(row2_cellRangeAddress_1);
		CellRangeAddress row2_cellRangeAddress_2 = new CellRangeAddress(1, 1, 14, 19);
		sheet.addMergedRegion(row2_cellRangeAddress_2);
		HSSFCell cell_2_1 = row2.createCell(0);
		StringBuilder sb_cell_2_1 = new StringBuilder();
		sb_cell_2_1.append("统计时段：").append(StringUtils.isNotBlank(bean.getStartTime())?bean.getStartTime():"").append("  ~  ").append(StringUtils.isNotBlank(bean.getEndTime())?bean.getEndTime():"");
		cell_2_1.setCellStyle(normal_CellStyle);
		cellRangeAddressSetBorder(row2_cellRangeAddress_1, sheet, workbook);
		cell_2_1.setCellValue(sb_cell_2_1.toString());
		HSSFCell cell_2_2 = row2.createCell(14);
		cell_2_2.setCellStyle(normal_CellStyle);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		cell_2_2.setCellValue("统计时间：" + dateFormat.format(new Date()));
		cellRangeAddressSetBorder(row2_cellRangeAddress_2, sheet, workbook);
		
		//第三行
		CellRangeAddress cellRangeAddress_title1_2 = new CellRangeAddress(2, 4, 1, 1);//单位
		sheet.addMergedRegion(cellRangeAddress_title1_2);
		CellRangeAddress cellRangeAddress_title1_3 = new CellRangeAddress(2, 2, 2, 7);//预警签收
		sheet.addMergedRegion(cellRangeAddress_title1_3);
		CellRangeAddress cellRangeAddress_title2 = new CellRangeAddress(2, 2, 8, 13);//指令签收
		sheet.addMergedRegion(cellRangeAddress_title2);
		CellRangeAddress cellRangeAddress_title3 = new CellRangeAddress(2, 2, 14, 19);//处置反馈
		sheet.addMergedRegion(cellRangeAddress_title3);
		
		CellRangeAddress cellRangeAddress_title_yjzs = new CellRangeAddress(3,4,2,2);
		sheet.addMergedRegion(cellRangeAddress_title_yjzs);
		CellRangeAddress cellRangeAddress_title_asqs = new CellRangeAddress(3,4,3,3);
		sheet.addMergedRegion(cellRangeAddress_title_asqs);
		CellRangeAddress cellRangeAddress_title_csqs = new CellRangeAddress(3,4,4,4);
		sheet.addMergedRegion(cellRangeAddress_title_csqs);
		CellRangeAddress cellRangeAddress_title_wqs = new CellRangeAddress(3,4,5,5);
		sheet.addMergedRegion(cellRangeAddress_title_wqs);
		CellRangeAddress cellRangeAddress_title_zqsl = new CellRangeAddress(3,4,6,6);
		sheet.addMergedRegion(cellRangeAddress_title_zqsl);
		CellRangeAddress cellRangeAddress_title_asqsl = new CellRangeAddress(3,4,7,7);
		sheet.addMergedRegion(cellRangeAddress_title_asqsl);
		CellRangeAddress cellRangeAddress_title_zlzs = new CellRangeAddress(3,4,8,8);
		sheet.addMergedRegion(cellRangeAddress_title_zlzs);
		CellRangeAddress cellRangeAddress_title_zlasqs = new CellRangeAddress(3,4,9,9);
		sheet.addMergedRegion(cellRangeAddress_title_zlasqs);
		CellRangeAddress cellRangeAddress_title_zlcsqs = new CellRangeAddress(3,4,10,10);
		sheet.addMergedRegion(cellRangeAddress_title_zlcsqs);
		CellRangeAddress cellRangeAddress_title_zlwqs = new CellRangeAddress(3,4,11,11);
		sheet.addMergedRegion(cellRangeAddress_title_zlwqs);
		CellRangeAddress cellRangeAddress_title_zlzqsl = new CellRangeAddress(3,4,12,12);
		sheet.addMergedRegion(cellRangeAddress_title_zlzqsl);
		CellRangeAddress cellRangeAddress_title_zlasqsl = new CellRangeAddress(3,4,13,13);
		sheet.addMergedRegion(cellRangeAddress_title_zlasqsl);
		CellRangeAddress cellRangeAddress_title_fkzs = new CellRangeAddress(3,4,14,14);
		sheet.addMergedRegion(cellRangeAddress_title_fkzs);
		CellRangeAddress cellRangeAddress_title_fkasqs = new CellRangeAddress(3,4,15,15);
		sheet.addMergedRegion(cellRangeAddress_title_fkasqs);
		CellRangeAddress cellRangeAddress_title_fkcsqs = new CellRangeAddress(3,4,16,16);
		sheet.addMergedRegion(cellRangeAddress_title_fkcsqs);
		CellRangeAddress cellRangeAddress_title_fkwqs = new CellRangeAddress(3,4,17,17);
		sheet.addMergedRegion(cellRangeAddress_title_fkwqs);
		CellRangeAddress cellRangeAddress_title_fkzqsl = new CellRangeAddress(3,4,18,18);
		sheet.addMergedRegion(cellRangeAddress_title_fkzqsl);
		CellRangeAddress cellRangeAddress_title_fkasqsl = new CellRangeAddress(3,4,19,19);
		sheet.addMergedRegion(cellRangeAddress_title_fkasqsl);
		
		HSSFRow row3 = sheet.createRow(2);
		row3.setHeight((short)400);
		CellRangeAddress cellRangeAddress_title1 = new CellRangeAddress(2, 4, 0, 0);//序号
		sheet.addMergedRegion(cellRangeAddress_title1);
		HSSFCell cell_index = row3.createCell(0);
		cell_index.setCellStyle(col_CellStyle);
		cell_index.setCellValue("序号");
		cellRangeAddressSetBorder(cellRangeAddress_title1, sheet, workbook);//序号   合并单元格，设置边框
		HSSFCell cell_company = row3.createCell(1);
		cell_company.setCellStyle(col_CellStyle);
		cell_company.setCellValue("单位");
		cellRangeAddressSetBorder(cellRangeAddress_title1_2, sheet, workbook);//单位   合并单元格，设置边框
		HSSFCell cell_3_1 = row3.createCell(2);
		cell_3_1.setCellStyle(col_CellStyle);
		cell_3_1.setCellValue("预警签收");
		cellRangeAddressSetBorder(cellRangeAddress_title1_3, sheet, workbook);//预警签收   合并单元格，设置边框
		HSSFCell cell_3_2 = row3.createCell(8);
		cell_3_2.setCellStyle(col_CellStyle);
		cell_3_2.setCellValue("指令签收");
		cellRangeAddressSetBorder(cellRangeAddress_title2, sheet, workbook);//指令签收   合并单元格，设置边框
		HSSFCell cell_3_3 = row3.createCell(14);
		cell_3_3.setCellStyle(col_CellStyle);
		cell_3_3.setCellValue("处置反馈");
		cellRangeAddressSetBorder(cellRangeAddress_title3, sheet, workbook);//处置反馈   合并单元格，设置边框
		
		//第五行
		HSSFRow row4 = sheet.createRow(3);
		row4.setHeightInPoints(25);
		HSSFCell cell_4_1 = row4.createCell(0);
		cell_4_1.setCellStyle(col_CellStyle);
		HSSFCell cell_4_2 = row4.createCell(2);
		cell_4_2.setCellStyle(col_CellStyle);
		cell_4_2.setCellValue("预警总数");
		cellRangeAddressSetBorder(cellRangeAddress_title_yjzs, sheet, workbook);//预警总数   合并单元格，设置边框
		HSSFCell cell_4_3 = row4.createCell(3);
		cell_4_3.setCellStyle(col_CellStyle);
		cell_4_3.setCellValue("按时签收数");
		cellRangeAddressSetBorder(cellRangeAddress_title_asqs, sheet, workbook);//按时签收数   合并单元格，设置边框
		HSSFCell cell_4_4 = row4.createCell(4);
		cell_4_4.setCellStyle(col_CellStyle);
		cell_4_4.setCellValue("超时签收数");
		cellRangeAddressSetBorder(cellRangeAddress_title_csqs, sheet, workbook);//超时签收数   合并单元格，设置边框
		HSSFCell cell_4_5 = row4.createCell(5);
		cell_4_5.setCellStyle(col_CellStyle);
		cell_4_5.setCellValue("未签收数");
		cellRangeAddressSetBorder(cellRangeAddress_title_wqs, sheet, workbook);//未签收数   合并单元格，设置边框
		HSSFCell cell_4_6 = row4.createCell(6);
		cell_4_6.setCellStyle(col_CellStyle);
		cell_4_6.setCellValue("总签收率");
		cellRangeAddressSetBorder(cellRangeAddress_title_zqsl, sheet, workbook);//总签收率   合并单元格，设置边框
		HSSFCell cell_4_7 = row4.createCell(7);
		cell_4_7.setCellStyle(col_CellStyle);
		cell_4_7.setCellValue("按时签收率");
		cellRangeAddressSetBorder(cellRangeAddress_title_asqsl, sheet, workbook);//按时签收率   合并单元格，设置边框
		
		HSSFCell cell_4_8 = row4.createCell(8);
		cell_4_8.setCellStyle(col_CellStyle);
		cell_4_8.setCellValue("指令总数");
		cellRangeAddressSetBorder(cellRangeAddress_title_zlzs, sheet, workbook);//指令总数   合并单元格，设置边框
		HSSFCell cell_4_9 = row4.createCell(9);
		cell_4_9.setCellStyle(col_CellStyle);
		cell_4_9.setCellValue("按时签收数");
		cellRangeAddressSetBorder(cellRangeAddress_title_zlasqs, sheet, workbook);//指令按时签收数   合并单元格，设置边框
		HSSFCell cell_4_10 = row4.createCell(10);
		cell_4_10.setCellStyle(col_CellStyle);
		cell_4_10.setCellValue("超时签收数");
		cellRangeAddressSetBorder(cellRangeAddress_title_zlcsqs, sheet, workbook);//指令超时签收数  合并单元格，设置边框
		HSSFCell cell_4_11 = row4.createCell(11);
		cell_4_11.setCellStyle(col_CellStyle);
		cell_4_11.setCellValue("未签收数");
		cellRangeAddressSetBorder(cellRangeAddress_title_zlwqs, sheet, workbook);//指令未签收数  合并单元格，设置边框
		HSSFCell cell_4_12 = row4.createCell(12);
		cell_4_12.setCellStyle(col_CellStyle);
		cell_4_12.setCellValue("总签收率");
		cellRangeAddressSetBorder(cellRangeAddress_title_zlzqsl, sheet, workbook);//指令总签收率  合并单元格，设置边框
		HSSFCell cell_4_13 = row4.createCell(13);
		cell_4_13.setCellStyle(col_CellStyle);
		cell_4_13.setCellValue("按时签收率");
		cellRangeAddressSetBorder(cellRangeAddress_title_zlasqsl, sheet, workbook);//指令按时签收率   合并单元格，设置边框
		
		HSSFCell cell_4_15 = row4.createCell(14);
		cell_4_15.setCellStyle(col_CellStyle);
		cell_4_15.setCellValue("反馈总数");
		cellRangeAddressSetBorder(cellRangeAddress_title_fkzs, sheet, workbook);//反馈总数   合并单元格，设置边框
		HSSFCell cell_4_16 = row4.createCell(15);
		cell_4_16.setCellStyle(col_CellStyle);
		cell_4_16.setCellValue("按时反馈数");
		cellRangeAddressSetBorder(cellRangeAddress_title_fkasqs, sheet, workbook);//反馈按时签收数   合并单元格，设置边框
		HSSFCell cell_4_17 = row4.createCell(16);
		cell_4_17.setCellStyle(col_CellStyle);
		cell_4_17.setCellValue("超时反馈数");
		cellRangeAddressSetBorder(cellRangeAddress_title_fkcsqs, sheet, workbook);//反馈超时签收数  合并单元格，设置边框
		HSSFCell cell_4_18 = row4.createCell(17);
		cell_4_18.setCellStyle(col_CellStyle);
		cell_4_18.setCellValue("未反馈数");
		cellRangeAddressSetBorder(cellRangeAddress_title_fkwqs, sheet, workbook);//反馈 未签收数   合并单元格，设置边框
		HSSFCell cell_4_19 = row4.createCell(18);
		cell_4_19.setCellStyle(col_CellStyle);
		cell_4_19.setCellValue("总反馈率");
		cellRangeAddressSetBorder(cellRangeAddress_title_fkzqsl, sheet, workbook);//反馈总签收率   合并单元格，设置边框
		HSSFCell cell_4_20 = row4.createCell(19);
		cell_4_20.setCellStyle(col_CellStyle);
		cell_4_20.setCellValue("按时反馈率");
		cellRangeAddressSetBorder(cellRangeAddress_title_fkasqsl, sheet, workbook);//反馈按时签收率   合并单元格，设置边框
		
		List<YjqsEntity22> list = bean.getList();
		int table_head_height = 5;
		int contend_height = list.size();
		for(int i=0;i<list.size();i++){
			YjqsEntity2 yjqsEntity = list.get(i).getYjqsEntity();
			ZlqsEntity2 zlqsEntity = list.get(i).getZlqsEntity();
			CzfkEntity2 czfkEntity = list.get(i).getCzfkEntity();
			HSSFRow rowx = sheet.createRow(table_head_height+i);
			HSSFCell cell_x_index = rowx.createCell(0);
			cell_x_index.setCellStyle(normal_CellStyle);
			cell_x_index.setCellValue(i+1);
			HSSFCell cell_x_deptName = rowx.createCell(1);//部门名称
			cell_x_deptName.setCellStyle(normal_CellStyle);
			cell_x_deptName.setCellValue(list.get(i).getDeptName());
			HSSFCell cell_x_yjzs = rowx.createCell(2);//预警总数
			cell_x_yjzs.setCellStyle(normal_CellStyle);
			cell_x_yjzs.setCellValue(yjqsEntity.getYjqs_qszs());
			HSSFCell cell_x_asqs = rowx.createCell(3);//按时签收
			cell_x_asqs.setCellStyle(normal_CellStyle);
			cell_x_asqs.setCellValue(yjqsEntity.getYjqs_asqs());
			HSSFCell cell_x_csqs = rowx.createCell(4);//超时签收
			cell_x_csqs.setCellStyle(normal_CellStyle);
			cell_x_csqs.setCellValue(yjqsEntity.getYjqs_csqs());
			HSSFCell cell_x_wqs = rowx.createCell(5);//未签收
			cell_x_wqs.setCellStyle(normal_CellStyle);
			cell_x_wqs.setCellValue(yjqsEntity.getYjqs_wqs());
			HSSFCell cell_x_zqsl = rowx.createCell(6);//总签收率
			cell_x_zqsl.setCellStyle(normal_CellStyle);
			cell_x_zqsl.setCellValue(yjqsEntity.getYjqs_zqsl());
			HSSFCell cell_x_asqsl = rowx.createCell(7);//按时签收率
			cell_x_asqsl.setCellStyle(normal_CellStyle);
			cell_x_asqsl.setCellValue(yjqsEntity.getYjqs_asqsl());
			
			HSSFCell cell_x_zlzs = rowx.createCell(8);
			cell_x_zlzs.setCellStyle(normal_CellStyle);
			cell_x_zlzs.setCellValue(zlqsEntity.getZlqs_zlzs());
			HSSFCell cell_x_zl_asqss = rowx.createCell(9);
			cell_x_zl_asqss.setCellStyle(normal_CellStyle);
			cell_x_zl_asqss.setCellValue(zlqsEntity.getZlqs_asqs());
			HSSFCell cell_x_zl_csqs = rowx.createCell(10);
			cell_x_zl_csqs.setCellStyle(normal_CellStyle);
			cell_x_zl_csqs.setCellValue(zlqsEntity.getZlqs_csqs());
			HSSFCell cell_x_zl_wqs = rowx.createCell(11);
			cell_x_zl_wqs.setCellStyle(normal_CellStyle);
			cell_x_zl_wqs.setCellValue(zlqsEntity.getZlqs_wqs());
			HSSFCell cell_x_zl_zqsl = rowx.createCell(12);
			cell_x_zl_zqsl.setCellStyle(normal_CellStyle);
			cell_x_zl_zqsl.setCellValue(zlqsEntity.getZlqs_zqsl());
			HSSFCell cell_x_zl_asqsl = rowx.createCell(13);
			cell_x_zl_asqsl.setCellStyle(normal_CellStyle);
			cell_x_zl_asqsl.setCellValue(zlqsEntity.getZlqs_asqsl());
			
			HSSFCell cell_x_cz_fkzs = rowx.createCell(14);
			cell_x_cz_fkzs.setCellStyle(normal_CellStyle);
			cell_x_cz_fkzs.setCellValue(czfkEntity.getCzfk_fkzs());
			HSSFCell cell_x_cz_asfks = rowx.createCell(15);
			cell_x_cz_asfks.setCellStyle(normal_CellStyle);
			cell_x_cz_asfks.setCellValue(czfkEntity.getCzfk_asfk());
			HSSFCell cell_x_cz_csfk = rowx.createCell(16);
			cell_x_cz_csfk.setCellStyle(normal_CellStyle);
			cell_x_cz_csfk.setCellValue(czfkEntity.getCzfk_csfk());
			HSSFCell cell_x_cz_wfk = rowx.createCell(17);
			cell_x_cz_wfk.setCellStyle(normal_CellStyle);
			cell_x_cz_wfk.setCellValue(czfkEntity.getCzfk_wfk());
			HSSFCell cell_x_cz_zfkl = rowx.createCell(18);
			cell_x_cz_zfkl.setCellStyle(normal_CellStyle);
			cell_x_cz_zfkl.setCellValue(czfkEntity.getCzfk_zfkl());
			HSSFCell cell_x_cz_asfkl = rowx.createCell(19);
			cell_x_cz_asfkl.setCellStyle(normal_CellStyle);
			cell_x_cz_asfkl.setCellValue(czfkEntity.getCzfk_asfkl());
		}
		//合计
		Map<String, String> total = bean.getTotal();
		CellRangeAddress total_cellRangeAddress = new CellRangeAddress(table_head_height+contend_height, table_head_height+contend_height, 0, 1);
		sheet.addMergedRegion(total_cellRangeAddress);
		HSSFRow total_row = sheet.createRow(table_head_height+contend_height);
		HSSFCell cell_total = total_row.createCell(0);
		cell_total.setCellStyle(col_CellStyle);
		cell_total.setCellValue("总计");
		cellRangeAddressSetBorder(total_cellRangeAddress, sheet, workbook);
		HSSFCell cell_total_3 = total_row.createCell(2);
		cell_total_3.setCellStyle(normal_CellStyleWithColor);
		cell_total_3.setCellValue(total.get("yjqs_yjzs"));
		HSSFCell cell_total_4 = total_row.createCell(3);
		cell_total_4.setCellStyle(normal_CellStyleWithColor);
		cell_total_4.setCellValue(total.get("yjqs_asqs"));
		HSSFCell cell_total_5 = total_row.createCell(4);
		cell_total_5.setCellStyle(normal_CellStyleWithColor);
		cell_total_5.setCellValue(total.get("yjqs_csqs"));
		HSSFCell cell_total_6 = total_row.createCell(5);
		cell_total_6.setCellStyle(normal_CellStyleWithColor);
		cell_total_6.setCellValue(total.get("yjqs_wqs"));
		HSSFCell cell_total_7 = total_row.createCell(6);
		cell_total_7.setCellStyle(normal_CellStyleWithColor);
		cell_total_7.setCellValue(total.get("yjqs_zqsl"));
		HSSFCell cell_total_8 = total_row.createCell(7);
		cell_total_8.setCellStyle(normal_CellStyleWithColor);
		cell_total_8.setCellValue(total.get("yjqs_asqsl"));
		HSSFCell cell_total_9 = total_row.createCell(8);
		cell_total_9.setCellStyle(normal_CellStyleWithColor);
		cell_total_9.setCellValue(total.get("zlqs_zlzs"));
		HSSFCell cell_total_10 = total_row.createCell(9);
		cell_total_10.setCellStyle(normal_CellStyleWithColor);
		cell_total_10.setCellValue(total.get("zlqs_asqs"));
		HSSFCell cell_total_11 = total_row.createCell(10);
		cell_total_11.setCellStyle(normal_CellStyleWithColor);
		cell_total_11.setCellValue(total.get("zlqs_csqs"));
		HSSFCell cell_total_12 = total_row.createCell(11);
		cell_total_12.setCellStyle(normal_CellStyleWithColor);
		cell_total_12.setCellValue(total.get("zlqs_wqs"));
		HSSFCell cell_total_13 = total_row.createCell(12);
		cell_total_13.setCellStyle(normal_CellStyleWithColor);
		cell_total_13.setCellValue(total.get("zlqs_zqsl"));
		HSSFCell cell_total_14 = total_row.createCell(13);
		cell_total_14.setCellStyle(normal_CellStyleWithColor);
		cell_total_14.setCellValue(total.get("zlqs_asqsl"));
		HSSFCell cell_total_15 = total_row.createCell(14);
		cell_total_15.setCellStyle(normal_CellStyleWithColor);
		cell_total_15.setCellValue(total.get("czfk_fkzs"));
		HSSFCell cell_total_16 = total_row.createCell(15);
		cell_total_16.setCellStyle(normal_CellStyleWithColor);
		cell_total_16.setCellValue(total.get("czfk_asfk"));
		HSSFCell cell_total_17 = total_row.createCell(16);
		cell_total_17.setCellStyle(normal_CellStyleWithColor);
		cell_total_17.setCellValue(total.get("czfk_csfk"));
		HSSFCell cell_total_18 = total_row.createCell(17);
		cell_total_18.setCellStyle(normal_CellStyleWithColor);
		cell_total_18.setCellValue(total.get("czfk_wfk"));
		HSSFCell cell_total_19 = total_row.createCell(18);
		cell_total_19.setCellStyle(normal_CellStyleWithColor);
		cell_total_19.setCellValue(total.get("czfk_zfkl"));
		HSSFCell cell_total_20 = total_row.createCell(19);
		cell_total_20.setCellStyle(normal_CellStyleWithColor);
		cell_total_20.setCellValue(total.get("czfk_asfkl"));
		
		HSSFCellStyle beiZhuCellStyle = createBeiZhuCellStyle(workbook, (short)12);
		HSSFRow beizhu_row1 = sheet.createRow(table_head_height+contend_height+1);
		HSSFCell beizhu_cell_1 = beizhu_row1.createCell(0);
		beizhu_cell_1.setCellStyle(beiZhuCellStyle);
		beizhu_cell_1.setCellValue("备注：1. 预警按时签收率=按时签收总数/预警信息总数×100%,按时签收时间以接收到报警时间2分钟内计算.");
		CellRangeAddress beizhu_cellRangeAddress = new CellRangeAddress(table_head_height+contend_height+1, table_head_height+contend_height+1, 0, 19);
		sheet.addMergedRegion(beizhu_cellRangeAddress);
		cellRangeAddressSetBorder(beizhu_cellRangeAddress, sheet, workbook);
		HSSFRow beizhu_row2 = sheet.createRow(table_head_height+contend_height+2);
		HSSFCell beizhu_row2_cell_1 = beizhu_row2.createCell(0);
		beizhu_row2_cell_1.setCellStyle(beiZhuCellStyle);
		beizhu_row2_cell_1.setCellValue("            2. 处置指令按时签收率=按时签收指令总数/处置指令总数×100%,处置指令按时签收时间以接收指令10分钟内计算.");
		CellRangeAddress beizhu_row2_cellRangeAddress = new CellRangeAddress(table_head_height+contend_height+2, table_head_height+contend_height+2, 0, 19);
		sheet.addMergedRegion(beizhu_row2_cellRangeAddress);
		cellRangeAddressSetBorder(beizhu_row2_cellRangeAddress, sheet, workbook);
		HSSFRow beizhu_row3 = sheet.createRow(table_head_height+contend_height+3);
		HSSFCell beizhu_row3_cell_1 = beizhu_row3.createCell(0);
		beizhu_row3_cell_1.setCellStyle(beiZhuCellStyle);
		beizhu_row3_cell_1.setCellValue("            3. 按时反馈率=按时反馈处警指令总数/已接收处警指令总数×100%,按时反馈时间以接收指令24小时内计算.");
		CellRangeAddress beizhu_row3_cellRangeAddress = new CellRangeAddress(table_head_height+contend_height+3, table_head_height+contend_height+3, 0, 19);
		sheet.addMergedRegion(beizhu_row3_cellRangeAddress);
		cellRangeAddressSetBorder(beizhu_row3_cellRangeAddress, sheet, workbook);
		workbook.write(outputStream);
		workbook.close();
	}
	
	
	/**
	 * 私有的，给工作簿设置样式(垂直，水平居中，默认加粗)的方法
	 */
	private static HSSFCellStyle createHSSFCellStyle(HSSFWorkbook workbook,short fontSize){
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
			//设置字体
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//设置字体加粗
		font.setFontHeightInPoints(fontSize);//设置字体的大小
		cellStyle.setFont(font);//样式加载字体
		cellStyle.setWrapText(true);//设置字体换行
		//设置边框
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setLeftBorderColor(IndexedColors.BLACK.index);
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		cellStyle.setTopBorderColor(HSSFColor.BLACK.index);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		return cellStyle;
	}
	
	/**
	 * 给工作簿设置样式  普通（垂直，水平居中，字体）无加粗
	 */
	private static HSSFCellStyle createHSSFCellStypeNoBold(HSSFWorkbook workbook,short fontSize){
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
			//设置字体
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints(fontSize);//设置字体的大小
		cellStyle.setFont(font);//样式加载字体
		cellStyle.setWrapText(true);//设置字体换行
		
		//设置边框
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		return cellStyle;
	}
	
	/**
	 * 给工作簿设置样式  普通（垂直，水平居中，字体）无加粗
	 */
	private static HSSFCellStyle createHSSFCellStypeNoBoldForBktj(HSSFWorkbook workbook,short fontSize){
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);//水平居左
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		//设置字体
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints(fontSize);//设置字体的大小
		cellStyle.setFont(font);//样式加载字体
		cellStyle.setWrapText(true);//设置字体换行
		
		//设置边框
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		return cellStyle;
	}
	
	/**
	 * 给工作簿设置样式  普通（垂直，水平居中，字体）无加粗
	 */
	private static HSSFCellStyle createNormalCellStyleWithColor(HSSFWorkbook workbook,short fontSize,short color){
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		//设置字体
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints(fontSize);//设置字体的大小
		cellStyle.setFont(font);//样式加载字体
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(color);
		
		//设置边框
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		return cellStyle;
	}
	
	/**
	 * 给工作薄  标题  设置样式（垂直，水平居中，字体加粗，设置背景颜色）
	 */
	private static HSSFCellStyle createTitleCellStyle(HSSFWorkbook workbook,short fontSize){
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
			//设置字体
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//设置字体加粗
		font.setFontHeightInPoints(fontSize);//设置字体的大小
		cellStyle.setFont(font);//样式加载字体
		cellStyle.setWrapText(true);//设置字体换行
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		
		//设置边框
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		return cellStyle;		
	}
	
	/**
	 * 给工作薄 备注  设置样式（垂直居中，字体大小）
	 */
	private static HSSFCellStyle createBeiZhuCellStyle(HSSFWorkbook workbook,short fontSize){
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
			//设置字体
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints(fontSize);//设置字体的大小
		cellStyle.setFont(font);//样式加载字体
		cellStyle.setWrapText(true);//设置字体换行
		
		//设置边框
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		return cellStyle;		
	}	
	
	private static void cellRangeAddressSetBorder(CellRangeAddress cellRangeAddress,HSSFSheet sheet,HSSFWorkbook workbook){
		RegionUtil.setBorderBottom(CellStyle.BORDER_THIN, cellRangeAddress, sheet, workbook);
		RegionUtil.setBorderLeft(CellStyle.BORDER_THIN, cellRangeAddress, sheet, workbook);
		RegionUtil.setBorderTop(CellStyle.BORDER_THIN, cellRangeAddress, sheet, workbook);
		RegionUtil.setBorderRight(CellStyle.BORDER_THIN, cellRangeAddress, sheet, workbook);
	}

	/**
	 * 高级分析，碰撞结果，导出excel
	 * @param list
	 * @param outputStream
	 * @throws IOException 
	 */
	public static void excelExportForDwpz(ExcelBeanForDwpz bean,
			ServletOutputStream outputStream) throws Exception {
		//创建工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		//合并单元格样式
		CellRangeAddress cellRangeAddress = new CellRangeAddress(1, 1, 0, 1);
		//头标题样式
		HSSFCellStyle head_CellStyle = createHSSFCellStyle(workbook, (short)16);
		//设置普通单元格样式
		HSSFCellStyle normal_CellStyle = createHSSFCellStypeNoBold(workbook,(short)12);
		//创建工作表
		HSSFSheet sheet = workbook.createSheet("碰撞结果情况");
		//加载..合并单元格对象
		sheet.addMergedRegion(cellRangeAddress);//标题
		//设置单元格默认宽度
		sheet.setDefaultColumnWidth(20);
		//创建  查询条件 行
		CellRangeAddress cellRangeAddress_queryCondition = new CellRangeAddress(0,0,0,2);
		sheet.addMergedRegion(cellRangeAddress_queryCondition);
		HSSFRow row1 = sheet.createRow(0);
		row1.setHeightInPoints(60);
		HSSFCell cell_row1_1 = row1.createCell(0);
		cell_row1_1.setCellStyle(normal_CellStyle);
		cell_row1_1.setCellValue(bean.getQueryCondition());
		cellRangeAddressSetBorder(cellRangeAddress_queryCondition, sheet, workbook);
		//头标题行,第二行第一列
		HSSFRow row2 = sheet.createRow(1);
		HSSFCell cell_2_1 = row2.createCell(0);
		cell_2_1.setCellStyle(head_CellStyle);
		cell_2_1.setCellValue("碰撞结果");
		List<ResDwpz> list = bean.getList();
		for(int i=0;i<list.size();i++){
			ResDwpz resDwpz = list.get(i);
			HSSFRow rowx = sheet.createRow(i+2);
			HSSFCell cell_rowx_1 = rowx.createCell(0);
			cell_rowx_1.setCellStyle(normal_CellStyle);
			cell_rowx_1.setCellValue(i+1);
			HSSFCell cell_rowx_2 = rowx.createCell(1);
			cell_rowx_2.setCellStyle(normal_CellStyle);
			cell_rowx_2.setCellValue(resDwpz.getHphm());
			
		}
		workbook.write(outputStream);
		workbook.close();
	}

	/**
	 * 布控撤控excel 导出
	 */
	public static void excelExportForBkck(BkckExcelBean excelBean, ServletOutputStream outputStream) {
		//创建工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		//合并单元格样式
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 13);
		//头标题样式
		HSSFCellStyle head_CellStyle = createHSSFCellStyle(workbook, (short)16);
		//设置普通单元格样式
		HSSFCellStyle normal_CellStyle = createHSSFCellStypeNoBold(workbook,(short)12);
		//创建工作表
		HSSFSheet sheet = workbook.createSheet("深圳市缉查布控系统布控撤控处置工作情况统计表");
		//title 单元格样式
		HSSFCellStyle title_CellStyle = createTitleCellStyle(workbook, (short)12);
		//加载..合并单元格对象
		sheet.addMergedRegion(cellRangeAddress);//标题
		//设置单元格默认宽度
		sheet.setDefaultColumnWidth(12);
		sheet.setColumnWidth(0, 256*8);
		sheet.setColumnWidth(1, 256*25);
		
		//创建行
		//头标题行,第一行第一列
		HSSFRow row1 = sheet.createRow(0);
		row1.setHeight((short)1000);
		HSSFCell cell_1_1 = row1.createCell(0);
		cell_1_1.setCellStyle(head_CellStyle);
		cell_1_1.setCellValue("深圳市缉查布控系统布控撤控处置工作情况统计表");
		cellRangeAddressSetBorder(cellRangeAddress, sheet, workbook);
		
		//创建第二行
		CellRangeAddress cellRangeAddress_row2_1 = new CellRangeAddress(1, 1, 0, 7);
		sheet.addMergedRegion(cellRangeAddress_row2_1);
		CellRangeAddress cellRangeAddress_row2_2 = new CellRangeAddress(1, 1, 8, 13);
		sheet.addMergedRegion(cellRangeAddress_row2_2);
		HSSFRow row2 = sheet.createRow(1);
		row2.setHeight((short)400);
		HSSFCell cell_2_1 = row2.createCell(0);
		cell_2_1.setCellStyle(normal_CellStyle);
		StringBuilder tjsd = new StringBuilder();
		tjsd.append("统计时段：").append(excelBean.getStartTime()).append(" ~ ").append(excelBean.getEndTime());
		cell_2_1.setCellValue(tjsd.toString());
		cellRangeAddressSetBorder(cellRangeAddress_row2_1, sheet, workbook);
		HSSFCell cell_2_2 = row2.createCell(8);
		cell_2_2.setCellStyle(normal_CellStyle);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		cell_2_2.setCellValue("统计时间：" + dateFormat.format(new Date()));
		cellRangeAddressSetBorder(cellRangeAddress_row2_2, sheet, workbook);
		
		//第 3 4 行
		CellRangeAddress cellRangeAddress_row3_1 = new CellRangeAddress(2, 3, 0, 0);
		CellRangeAddress cellRangeAddress_row3_2 = new CellRangeAddress(2, 3, 1, 1);
		CellRangeAddress cellRangeAddress_row3_3 = new CellRangeAddress(2, 2, 2, 7);
		CellRangeAddress cellRangeAddress_row3_4 = new CellRangeAddress(2, 2, 8, 13);
		sheet.addMergedRegion(cellRangeAddress_row3_1);
		sheet.addMergedRegion(cellRangeAddress_row3_2);
		sheet.addMergedRegion(cellRangeAddress_row3_3);
		sheet.addMergedRegion(cellRangeAddress_row3_4);
		HSSFRow row3 = sheet.createRow(2);
		row3.setHeight((short)400);
		HSSFCell cell_3_1 = row3.createCell(0);
		cell_3_1.setCellStyle(title_CellStyle);
		cell_3_1.setCellValue("序号");
		HSSFCell cell_3_2 = row3.createCell(1);
		cell_3_2.setCellStyle(title_CellStyle);
		cell_3_2.setCellValue("单位");
		HSSFCell cell_3_3 = row3.createCell(2);
		cell_3_3.setCellStyle(title_CellStyle);
		cell_3_3.setCellValue("布控通知");
		HSSFCell cell_3_4 = row3.createCell(8);
		cell_3_4.setCellStyle(title_CellStyle);
		cell_3_4.setCellValue("撤控通知");
		HSSFRow row4 = sheet.createRow(3);
		HSSFCell cell_4_3 = row4.createCell(2);
		cell_4_3.setCellStyle(title_CellStyle);
		cell_4_3.setCellValue("布控通知总数");
		HSSFCell cell_4_4 = row4.createCell(3);
		cell_4_4.setCellStyle(title_CellStyle);
		cell_4_4.setCellValue("按时签收数");
		HSSFCell cell_4_5 = row4.createCell(4);
		cell_4_5.setCellStyle(title_CellStyle);
		cell_4_5.setCellValue("超时签收数");
		HSSFCell cell_4_6 = row4.createCell(5);
		cell_4_6.setCellStyle(title_CellStyle);
		cell_4_6.setCellValue("未签收数");
		HSSFCell cell_4_7 = row4.createCell(6);
		cell_4_7.setCellStyle(title_CellStyle);
		cell_4_7.setCellValue("总签收率");
		HSSFCell cell_4_8 = row4.createCell(7);
		cell_4_8.setCellStyle(title_CellStyle);
		cell_4_8.setCellValue("按时签收率");
		HSSFCell cell_4_9 = row4.createCell(8);
		cell_4_9.setCellStyle(title_CellStyle);
		cell_4_9.setCellValue("撤控通知总数");
		HSSFCell cell_4_10 = row4.createCell(9);
		cell_4_10.setCellStyle(title_CellStyle);
		cell_4_10.setCellValue("按时签收数");
		HSSFCell cell_4_11 = row4.createCell(10);
		cell_4_11.setCellStyle(title_CellStyle);
		cell_4_11.setCellValue("超时签收数");
		HSSFCell cell_4_12 = row4.createCell(11);
		cell_4_12.setCellStyle(title_CellStyle);
		cell_4_12.setCellValue("未签收数");
		HSSFCell cell_4_13 = row4.createCell(12);
		cell_4_13.setCellStyle(title_CellStyle);
		cell_4_13.setCellValue("总签收率");
		HSSFCell cell_4_14 = row4.createCell(13);
		cell_4_14.setCellStyle(title_CellStyle);
		cell_4_14.setCellValue("按时签收率");
		cellRangeAddressSetBorder(cellRangeAddress_row3_1, sheet, workbook);
		cellRangeAddressSetBorder(cellRangeAddress_row3_2, sheet, workbook);
		cellRangeAddressSetBorder(cellRangeAddress_row3_3, sheet, workbook);
		cellRangeAddressSetBorder(cellRangeAddress_row3_4, sheet, workbook);
		List<BkckEntity> list = excelBean.getBean().getList();
		int height = 4;
		if(list != null && list.size() > 0){
			for(int i=0;i < list.size();i++){
				BkckEntity bkckEntity = list.get(i);
				HSSFRow rowx = sheet.createRow(height+i);
				HSSFCell cell_x_1 = rowx.createCell(0);
				cell_x_1.setCellStyle(normal_CellStyle);
				cell_x_1.setCellValue(i+1);
				HSSFCell cell_x_2 = rowx.createCell(1);
				cell_x_2.setCellStyle(normal_CellStyle);
				cell_x_2.setCellValue(bkckEntity.getDeptName());
				HSSFCell cell_x_3 = rowx.createCell(2);
				cell_x_3.setCellStyle(normal_CellStyle);
				cell_x_3.setCellValue(bkckEntity.getBktzzs());
				HSSFCell cell_x_4 = rowx.createCell(3);
				cell_x_4.setCellStyle(normal_CellStyle);
				cell_x_4.setCellValue(bkckEntity.getBktz_asqs());
				HSSFCell cell_x_5 = rowx.createCell(4);
				cell_x_5.setCellStyle(normal_CellStyle);
				cell_x_5.setCellValue(bkckEntity.getBktz_csqs());
				HSSFCell cell_x_6 = rowx.createCell(5);
				cell_x_6.setCellStyle(normal_CellStyle);
				cell_x_6.setCellValue(bkckEntity.getBktz_wqs());
				HSSFCell cell_x_7 = rowx.createCell(6);
				cell_x_7.setCellStyle(normal_CellStyle);
				cell_x_7.setCellValue(bkckEntity.getBktz_zqsl());
				HSSFCell cell_x_8 = rowx.createCell(7);
				cell_x_8.setCellStyle(normal_CellStyle);
				cell_x_8.setCellValue(bkckEntity.getBktz_asqsl());
				HSSFCell cell_x_9 = rowx.createCell(8);
				cell_x_9.setCellStyle(normal_CellStyle);
				cell_x_9.setCellValue(bkckEntity.getCktzzs());
				HSSFCell cell_x_10 = rowx.createCell(9);
				cell_x_10.setCellStyle(normal_CellStyle);
				cell_x_10.setCellValue(bkckEntity.getCktz_asqs());
				HSSFCell cell_x_11 = rowx.createCell(10);
				cell_x_11.setCellStyle(normal_CellStyle);
				cell_x_11.setCellValue(bkckEntity.getCktz_csqs());
				HSSFCell cell_x_12 = rowx.createCell(11);
				cell_x_12.setCellStyle(normal_CellStyle);
				cell_x_12.setCellValue(bkckEntity.getCktz_wqs());
				HSSFCell cell_x_13 = rowx.createCell(12);
				cell_x_13.setCellStyle(normal_CellStyle);
				cell_x_13.setCellValue(bkckEntity.getCktz_zqsl());
				HSSFCell cell_x_14 = rowx.createCell(13);
				cell_x_14.setCellStyle(normal_CellStyle);
				cell_x_14.setCellValue(bkckEntity.getCktz_asqsl());
			}
			//添加  总计
			HSSFCellStyle totalCellStyleWithColor = createNormalCellStyleWithColor(workbook, (short)14, HSSFColor.GREY_25_PERCENT.index);
			int height2 = height+list.size();
			HSSFRow row_total = sheet.createRow(height2);
			CellRangeAddress cellRangeAddress_total = new CellRangeAddress(height2, height2, 0, 1);
			sheet.addMergedRegion(cellRangeAddress_total);
			HSSFCell cell_total_1 = row_total.createCell(0);
			cell_total_1.setCellStyle(totalCellStyleWithColor);
			cell_total_1.setCellValue("总计");
			cellRangeAddressSetBorder(cellRangeAddress_total, sheet, workbook);
			Map<String, String> total = excelBean.getBean().getTotal();
			HSSFCell cell_total_3 = row_total.createCell(2);
			cell_total_3.setCellStyle(totalCellStyleWithColor);
			cell_total_3.setCellValue(total.get("bktz_zs"));
			HSSFCell cell_total_4 = row_total.createCell(3);
			cell_total_4.setCellStyle(totalCellStyleWithColor);
			cell_total_4.setCellValue(total.get("bktz_asqs"));
			HSSFCell cell_total_5 = row_total.createCell(4);
			cell_total_5.setCellStyle(totalCellStyleWithColor);
			cell_total_5.setCellValue(total.get("bktz_csqs"));
			HSSFCell cell_total_6 = row_total.createCell(5);
			cell_total_6.setCellStyle(totalCellStyleWithColor);
			cell_total_6.setCellValue(total.get("bktz_wqs"));
			HSSFCell cell_total_7 = row_total.createCell(6);
			cell_total_7.setCellStyle(totalCellStyleWithColor);
			cell_total_7.setCellValue(total.get("bktz_zqsl"));
			HSSFCell cell_total_8 = row_total.createCell(7);
			cell_total_8.setCellStyle(totalCellStyleWithColor);
			cell_total_8.setCellValue(total.get("bktz_asqsl"));
			HSSFCell cell_total_9 = row_total.createCell(8);
			cell_total_9.setCellStyle(totalCellStyleWithColor);
			cell_total_9.setCellValue(total.get("cktz_zs"));
			HSSFCell cell_total_10 = row_total.createCell(9);
			cell_total_10.setCellStyle(totalCellStyleWithColor);
			cell_total_10.setCellValue(total.get("cktz_asqs"));
			HSSFCell cell_total_11 = row_total.createCell(10);
			cell_total_11.setCellStyle(totalCellStyleWithColor);
			cell_total_11.setCellValue(total.get("cktz_csqs"));
			HSSFCell cell_total_12 = row_total.createCell(11);
			cell_total_12.setCellStyle(totalCellStyleWithColor);
			cell_total_12.setCellValue(total.get("cktz_wqs"));
			HSSFCell cell_total_13 = row_total.createCell(12);
			cell_total_13.setCellStyle(totalCellStyleWithColor);
			cell_total_13.setCellValue(total.get("cktz_zqsl"));
			HSSFCell cell_total_14 = row_total.createCell(13);
			cell_total_14.setCellStyle(totalCellStyleWithColor);
			cell_total_14.setCellValue(total.get("cktz_asqsl"));
			
			//添加备注行
			HSSFCellStyle beiZhuCellStyle = createBeiZhuCellStyle(workbook, (short)12);
			HSSFRow row_beizhu_1 = sheet.createRow(height2+1);
			HSSFCell cell_beizhu_1 = row_beizhu_1.createCell(0);
			cell_beizhu_1.setCellStyle(beiZhuCellStyle);
			cell_beizhu_1.setCellValue("备注：1. 布控按时签收率=按时签收布控总数/布控指令接收总数×100%, 按时签收时间以接收到指令时间10分钟内计算.");
			CellRangeAddress cellRangeAddress_beizhu_1 = new CellRangeAddress(height2+1, height2+1, 0, 13);
			sheet.addMergedRegion(cellRangeAddress_beizhu_1);
			cellRangeAddressSetBorder(cellRangeAddress_beizhu_1, sheet, workbook);
			HSSFRow row_beizhu_2 = sheet.createRow(height2+2);
			HSSFCell cell_row2_1 = row_beizhu_2.createCell(0);
			cell_row2_1.setCellStyle(beiZhuCellStyle);
			cell_row2_1.setCellValue("            2. 撤控按时签收率=按时签收撤控总数/撤控指令接收总数×100%, 按时签收时间以接收到指令时间10分钟内计算.");
			CellRangeAddress cellRangeAddress_beizhu_2 = new CellRangeAddress(height2+2, height2+2, 0, 13);
			sheet.addMergedRegion(cellRangeAddress_beizhu_2);
			cellRangeAddressSetBorder(cellRangeAddress_beizhu_2, sheet, workbook);
		}
		try {
			workbook.write(outputStream);
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 导出红名单到excel
	 * @param excelBeanList 红名单列表
	 * @param outputStream
	 * @throws Exception
	 */
	public static void exportExcelOfHmd(List<JjhomdExcelBean> excelBeanList,ServletOutputStream outputStream) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		HSSFWorkbook workbook = new HSSFWorkbook();//创建工作簿
		//合并单元格样式
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 5);
		//头标题样式
		HSSFCellStyle head_CellStyle = createHSSFCellStyle(workbook, (short)16);
		//行标题样式
//		HSSFCellStyle row_CellStyle = createHSSFCellStyle(workbook, (short)14);
		//设置列标题样式
		HSSFCellStyle col_CellStyle = createTitleCellStyle(workbook, (short)14);
		//普通单元格 带颜色
//		HSSFCellStyle normal_CellStyleWithColor = createNormalCellStyleWithColor(workbook, (short)14, HSSFColor.GREY_25_PERCENT.index);
		//设置普通单元格样式
		HSSFCellStyle normal_CellStyle = createHSSFCellStypeNoBold(workbook, (short)12);
		//创建工作表
		HSSFSheet sheet = workbook.createSheet("红名单查询");
			//加载合并单元格对象
		sheet.addMergedRegion(cellRangeAddress);
		sheet.setDefaultColumnWidth(20);
		//创建行
			//头标题行,第一行第一列
		HSSFRow row1 = sheet.createRow(0);
		HSSFCell cell_1_1 = row1.createCell(0);
		cell_1_1.setCellStyle(head_CellStyle);
		cell_1_1.setCellValue("红名单列表");
			//创建列标题,第二行
		HSSFRow row2 = sheet.createRow(1);
		String [] col_titles = {"车牌号码", "车辆类型", "车辆使用人", "申请人", "申请时间", "记录状态"};
		for(int i=0;i < col_titles.length;i++){
			HSSFCell cell_2_x = row2.createCell(i);
			cell_2_x.setCellStyle(col_CellStyle);
			cell_2_x.setCellValue(col_titles[i]);
		}
		//操作单元格，把hmd列表，遍历出来，插入到excel中
		if(excelBeanList!=null){
			for(int j=0;j < excelBeanList.size();j++){
				JjhomdExcelBean jjhomdExcelBean = excelBeanList.get(j);
				HSSFRow row = sheet.createRow(j+2);
				HSSFCell cell_x_0 = row.createCell(0);
				cell_x_0.setCellStyle(normal_CellStyle);
				cell_x_0.setCellValue(jjhomdExcelBean.getJjhomd().getCphid());
				
				HSSFCell cell_x_1 = row.createCell(1);
				cell_x_1.setCellStyle(normal_CellStyle);
				cell_x_1.setCellValue(jjhomdExcelBean.getCllx());//车辆类型
				
				HSSFCell cell_x_2 = row.createCell(2);
				cell_x_2.setCellStyle(normal_CellStyle);
				cell_x_2.setCellValue(jjhomdExcelBean.getJjhomd().getClsyz());
				
				HSSFCell cell_x_3 = row.createCell(3);
				cell_x_3.setCellStyle(normal_CellStyle);
				cell_x_3.setCellValue(jjhomdExcelBean.getJjhomd().getSqrxm());
				
				String sqsj = "";
				if(jjhomdExcelBean.getJjhomd() != null && jjhomdExcelBean.getJjhomd().getLrsj() != null){
					sqsj = sdf.format(jjhomdExcelBean.getJjhomd().getLrsj());
				}
				HSSFCell cell_x_4 = row.createCell(4);
				cell_x_4.setCellStyle(normal_CellStyle);
				cell_x_4.setCellValue(sqsj);
				
				HSSFCell cell_x_5 = row.createCell(5);
				cell_x_5.setCellStyle(normal_CellStyle);
				cell_x_5.setCellValue(jjhomdExcelBean.getJlzt());
			}
		}
		//输出
		workbook.write(outputStream);
		workbook.close();
	}
	
	/**
	 * 布控查询导出Excel
	 * @throws Exception 
	 */
	public static void excelExportForDispatched(BKQueryExcelBean bean,ServletOutputStream outputStream) throws Exception{
		//创建工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		//创建工作表
		HSSFSheet sheet = workbook.createSheet("布控查询列表");
		//合并单元格样式，excel 标题
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 10);
		//头标题样式
		HSSFCellStyle head_CellStyle = createHSSFCellStyle(workbook, (short)16);
		//设置列标题样式
		HSSFCellStyle col_CellStyle = createTitleCellStyle(workbook, (short)13);
		//设置普通单元格样式
		HSSFCellStyle normal_CellStyle = createHSSFCellStypeNoBold(workbook,(short)12);
		//普通单元格 带颜色
//		HSSFCellStyle normal_CellStyleWithColor = createNormalCellStyleWithColor(workbook, (short)14, HSSFColor.GREY_25_PERCENT.index);
		//加载..合并单元格对象
		sheet.addMergedRegion(cellRangeAddress);//标题
		//设置单元格默认宽度
		sheet.setDefaultColumnWidth(12);
		//默认单元格 高度
		sheet.setDefaultRowHeightInPoints(25);
		//设置时间列宽点
		
		HSSFRow row1 = sheet.createRow(0);
		HSSFCell cell_row1_1 = row1.createCell(0);
		cell_row1_1.setCellStyle(head_CellStyle);
		cell_row1_1.setCellValue("布控查询列表");
		cellRangeAddressSetBorder(cellRangeAddress, sheet, workbook);
		HSSFRow row2 = sheet.createRow(1);
		HSSFCell cell_row2_1 = row2.createCell(0);
		cell_row2_1.setCellStyle(col_CellStyle);
		cell_row2_1.setCellValue("号牌号码");
		HSSFCell cell_row2_2 = row2.createCell(1);
		cell_row2_2.setCellStyle(col_CellStyle);
		cell_row2_2.setCellValue("号牌种类");
		HSSFCell cell_row2_3 = row2.createCell(2);
		cell_row2_3.setCellStyle(col_CellStyle);
		cell_row2_3.setCellValue("布控大类");
		HSSFCell cell_row2_4 = row2.createCell(3);
		cell_row2_4.setCellStyle(col_CellStyle);
		cell_row2_4.setCellValue("布控类别");
		HSSFCell cell_row2_5 = row2.createCell(4);
		cell_row2_5.setCellStyle(col_CellStyle);
		cell_row2_5.setCellValue("布控申请人");
		HSSFCell cell_row2_6 = row2.createCell(5);
		cell_row2_6.setCellStyle(col_CellStyle);
		cell_row2_6.setCellValue("布控申请时间");
		HSSFCell cell_row2_7 = row2.createCell(6);
		cell_row2_7.setCellStyle(col_CellStyle);
		cell_row2_7.setCellValue("布控起始时间");
		HSSFCell cell_row2_8 = row2.createCell(7);
		cell_row2_8.setCellStyle(col_CellStyle);
		cell_row2_8.setCellValue("布控终止时间");
		HSSFCell cell_row2_9 = row2.createCell(8);
		cell_row2_9.setCellStyle(col_CellStyle);
		cell_row2_9.setCellValue("布控性质");
		HSSFCell cell_row2_10 = row2.createCell(9);
		cell_row2_10.setCellStyle(col_CellStyle);
		cell_row2_10.setCellValue("直接布控");
		HSSFCell cell_row2_11 = row2.createCell(10);
		cell_row2_11.setCellStyle(col_CellStyle);
		cell_row2_11.setCellValue("布控状态");
		
		int height = 2;
		List<BkItemBean> list = bean.getBklist();
		if(list!=null&&list.size()>=1){
			for(int i=0;i<list.size();i++){
				BkItemBean item = list.get(i);
				HSSFRow rowx = sheet.createRow(height+i);
				HSSFCell cell_rowx_1 = rowx.createCell(0);
				cell_rowx_1.setCellStyle(normal_CellStyle);
				cell_rowx_1.setCellValue(item.getCphm());//号牌号码
				HSSFCell cell_rowx_2 = rowx.createCell(1);
				cell_rowx_2.setCellStyle(normal_CellStyle);
				cell_rowx_2.setCellValue(item.getHpzl());//号牌种类
				HSSFCell cell_rowx_3 = rowx.createCell(2);
				cell_rowx_3.setCellStyle(normal_CellStyle);
				cell_rowx_3.setCellValue(item.getBkdl());//布控大类
				HSSFCell cell_rowx_4 = rowx.createCell(3);
				cell_rowx_4.setCellStyle(normal_CellStyle);
				cell_rowx_4.setCellValue(item.getBklb());
				HSSFCell cell_rowx_5 = rowx.createCell(4);
				cell_rowx_5.setCellStyle(normal_CellStyle);
				cell_rowx_5.setCellValue(item.getBksqr());
				HSSFCell cell_rowx_6 = rowx.createCell(5);
				cell_rowx_6.setCellStyle(normal_CellStyle);
				cell_rowx_6.setCellValue(item.getSqsj());
				HSSFCell cell_rowx_7 = rowx.createCell(6);
				cell_rowx_7.setCellStyle(normal_CellStyle);
				cell_rowx_7.setCellValue(item.getQssj());
				HSSFCell cell_rowx_8 = rowx.createCell(7);
				cell_rowx_8.setCellStyle(normal_CellStyle);
				cell_rowx_8.setCellValue(item.getJzsj());
				HSSFCell cell_rowx_9 = rowx.createCell(8);
				cell_rowx_9.setCellStyle(normal_CellStyle);
				cell_rowx_9.setCellValue(item.getBkxz());
				HSSFCell cell_rowx_10 = rowx.createCell(9);
				cell_rowx_10.setCellStyle(normal_CellStyle);
				cell_rowx_10.setCellValue(item.getZjbk());
				HSSFCell cell_rowx_11 = rowx.createCell(10);
				cell_rowx_11.setCellStyle(normal_CellStyle);
				cell_rowx_11.setCellValue(item.getBkzt());
			}
		}
		//输出
		workbook.write(outputStream);
		workbook.close();
	}
	
	/**
	 * 布控管理模块中   撤控导出excel
	 * @throws Exception 
	 */
	public static void exportExcelForCheKong(CKExcelBean bean,ServletOutputStream outputStream) throws Exception{
		//创建工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		//创建工作表
		HSSFSheet sheet = workbook.createSheet("撤控查询列表");
		//合并单元格样式，excel 标题
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 6);
		//头标题样式
		HSSFCellStyle head_CellStyle = createHSSFCellStyle(workbook, (short)16);
		//设置列标题样式
		HSSFCellStyle col_CellStyle = createTitleCellStyle(workbook, (short)14);
		//设置普通单元格样式
		HSSFCellStyle normal_CellStyle = createHSSFCellStypeNoBold(workbook,(short)12);
		//普通单元格 带颜色
//		HSSFCellStyle normal_CellStyleWithColor = createNormalCellStyleWithColor(workbook, (short)14, HSSFColor.GREY_25_PERCENT.index);
		//加载..合并单元格对象
		sheet.addMergedRegion(cellRangeAddress);//标题
		//设置单元格默认宽度
		sheet.setDefaultColumnWidth(12);
		//默认单元格 高度
		sheet.setDefaultRowHeightInPoints(25);
		//设置时间列宽点
		
		HSSFRow row1 = sheet.createRow(0);
		HSSFCell cell_row1_1 = row1.createCell(0);
		cell_row1_1.setCellStyle(head_CellStyle);
		cell_row1_1.setCellValue("撤控查询列表");
		cellRangeAddressSetBorder(cellRangeAddress, sheet, workbook);
		HSSFRow row2 = sheet.createRow(1);
		HSSFCell cell_row2_1 = row2.createCell(0);
		cell_row2_1.setCellStyle(col_CellStyle);
		cell_row2_1.setCellValue("号牌号码");
		HSSFCell cell_row2_2 = row2.createCell(1);
		cell_row2_2.setCellStyle(col_CellStyle);
		cell_row2_2.setCellValue("号牌种类");
		HSSFCell cell_row2_3 = row2.createCell(2);
		cell_row2_3.setCellStyle(col_CellStyle);
		cell_row2_3.setCellValue("布控性质");
		HSSFCell cell_row2_4 = row2.createCell(3);
		cell_row2_4.setCellStyle(col_CellStyle);
		cell_row2_4.setCellValue("撤销申请人");
		HSSFCell cell_row2_5 = row2.createCell(4);
		cell_row2_5.setCellStyle(col_CellStyle);
		cell_row2_5.setCellValue("撤销申请时间");
		HSSFCell cell_row2_6 = row2.createCell(5);
		cell_row2_6.setCellStyle(col_CellStyle);
		cell_row2_6.setCellValue("直接撤控");
		HSSFCell cell_row2_7 = row2.createCell(6);
		cell_row2_7.setCellStyle(col_CellStyle);
		cell_row2_7.setCellValue("撤销状态");
		int height = 2;
		List<CKItemBean> list = bean.getList();
		for(int i=0;i<list.size();i++){
			CKItemBean ckItemBean = list.get(i);
			HSSFRow rowx = sheet.createRow(height+i);
			HSSFCell cell_rowx_1 = rowx.createCell(0);
			cell_rowx_1.setCellStyle(normal_CellStyle);
			cell_rowx_1.setCellValue(ckItemBean.getHphm());
			HSSFCell cell_rowx_2 = rowx.createCell(1);
			cell_rowx_2.setCellStyle(normal_CellStyle);
			cell_rowx_2.setCellValue(ckItemBean.getHpzl());
			HSSFCell cell_rowx_3 = rowx.createCell(2);
			cell_rowx_3.setCellStyle(normal_CellStyle);
			cell_rowx_3.setCellValue(ckItemBean.getBkxz());
			HSSFCell cell_rowx_4 = rowx.createCell(3);
			cell_rowx_4.setCellStyle(normal_CellStyle);
			cell_rowx_4.setCellValue(ckItemBean.getCxsqr());
			HSSFCell cell_rowx_5 = rowx.createCell(4);
			cell_rowx_5.setCellStyle(normal_CellStyle);
			cell_rowx_5.setCellValue(ckItemBean.getCxsj());
			HSSFCell cell_rowx_6 = rowx.createCell(5);
			cell_rowx_6.setCellStyle(normal_CellStyle);
			cell_rowx_6.setCellValue(ckItemBean.getZjck());
			HSSFCell cell_rowx_7 = rowx.createCell(6);
			cell_rowx_7.setCellStyle(normal_CellStyle);
			cell_rowx_7.setCellValue(ckItemBean.getCxzt());
		}
		//输出
		workbook.write(outputStream);
		workbook.close();
	}
	
	/**
	 * 导出套牌
	 */
	public static void excelExportForFakeHphm(List<FakeHphmExcelBean> excelBeanList, ServletOutputStream outputStream)throws Exception{
		//创建工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		//创建工作表
		HSSFSheet sheet = workbook.createSheet("套牌车辆查询");
		//合并单元格样式，excel 标题
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 5);
		//头标题样式
		HSSFCellStyle head_CellStyle = createHSSFCellStyle(workbook, (short)16);
		//设置列标题样式
		HSSFCellStyle col_CellStyle = createTitleCellStyle(workbook, (short)13);
		//设置普通单元格样式
		HSSFCellStyle normal_CellStyle = createHSSFCellStypeNoBold(workbook,(short)12);
		//普通单元格 带颜色
//		HSSFCellStyle normal_CellStyleWithColor = createNormalCellStyleWithColor(workbook, (short)14, HSSFColor.GREY_25_PERCENT.index);
		//加载..合并单元格对象
		sheet.addMergedRegion(cellRangeAddress);//标题
		//设置单元格默认宽度
		sheet.setDefaultColumnWidth(12);
		//默认单元格 高度
		sheet.setDefaultRowHeightInPoints(40);
		HSSFRow row1 = sheet.createRow(0);
		HSSFCell cell_row1_1 = row1.createCell(0);
		cell_row1_1.setCellStyle(head_CellStyle);
		cell_row1_1.setCellValue("套牌车辆查询");
		HSSFRow row2 = sheet.createRow(1);
		HSSFCell cell_row2_1 = row2.createCell(0);
		cell_row2_1.setCellStyle(col_CellStyle);
		cell_row2_1.setCellValue("序号");
		HSSFCell cell_row2_2 = row2.createCell(1);
		cell_row2_2.setCellStyle(col_CellStyle);
		cell_row2_2.setCellValue("车牌号");
		HSSFCell cell_row2_3 = row2.createCell(2);
		cell_row2_3.setCellStyle(col_CellStyle);
		cell_row2_3.setCellValue("车牌颜色");
		HSSFCell cell_row2_4 = row2.createCell(3);
		cell_row2_4.setCellStyle(col_CellStyle);
		cell_row2_4.setCellValue("监测点");
		HSSFCell cell_row2_5 = row2.createCell(4);
		cell_row2_5.setCellStyle(col_CellStyle);
		cell_row2_5.setCellValue("识别时间");
		HSSFCell cell_row2_6 = row2.createCell(5);
		cell_row2_6.setCellStyle(col_CellStyle);
		cell_row2_6.setCellValue("行驶车道");
		
		for(int i=0;i<excelBeanList.size();i++){
			FakeHphmExcelBean bean = excelBeanList.get(i);
			HSSFRow rowx = sheet.createRow(2+i);
			HSSFCell cell_rowx_1 = rowx.createCell(0);
			cell_rowx_1.setCellStyle(normal_CellStyle);
			cell_rowx_1.setCellValue(i+1);
			HSSFCell cell_rowx_2 = rowx.createCell(1);
			cell_rowx_2.setCellStyle(normal_CellStyle);
			cell_rowx_2.setCellValue(bean.getHphm());
			HSSFCell cell_rowx_3 = rowx.createCell(2);
			cell_rowx_3.setCellStyle(normal_CellStyle);
			cell_rowx_3.setCellValue(bean.getCpys());
			HSSFCell cell_rowx_4 = rowx.createCell(3);
			cell_rowx_4.setCellStyle(normal_CellStyle);
			cell_rowx_4.setCellValue(bean.getJcd());
			HSSFCell cell_rowx_5 = rowx.createCell(4);
			cell_rowx_5.setCellStyle(normal_CellStyle);
			cell_rowx_5.setCellValue(bean.getSbsj());
			HSSFCell cell_rowx_6 = rowx.createCell(5);
			cell_rowx_6.setCellStyle(normal_CellStyle);
			cell_rowx_6.setCellValue(bean.getXscd());
		}
		//输出
		workbook.write(outputStream);
		workbook.close();
	}
	
	/**
	 * 导出假牌
	 * @throws IOException 
	 */
	public static void excelExportForFalseHphm(List<FalseHphmExcelBean> excelBeanList,
			ServletOutputStream outputStream) throws IOException{
		//创建工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		//创建工作表
		HSSFSheet sheet = workbook.createSheet("假牌车辆查询");
		//合并单元格样式，excel 标题
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 2);
		//头标题样式
		HSSFCellStyle head_CellStyle = createHSSFCellStyle(workbook, (short)16);
		//设置列标题样式
		HSSFCellStyle col_CellStyle = createTitleCellStyle(workbook, (short)13);
		//设置普通单元格样式
		HSSFCellStyle normal_CellStyle = createHSSFCellStypeNoBold(workbook,(short)12);
		//普通单元格 带颜色
//		HSSFCellStyle normal_CellStyleWithColor = createNormalCellStyleWithColor(workbook, (short)14, HSSFColor.GREY_25_PERCENT.index);
		//加载..合并单元格对象
		sheet.addMergedRegion(cellRangeAddress);//标题
		//设置单元格默认宽度
		sheet.setDefaultColumnWidth(12);
		//默认单元格 高度
		sheet.setDefaultRowHeightInPoints(25);
		HSSFRow row1 = sheet.createRow(0);
		HSSFCell cell_row1_1 = row1.createCell(0);
		cell_row1_1.setCellStyle(head_CellStyle);
		cell_row1_1.setCellValue("假牌车辆查询");
		HSSFRow row2 = sheet.createRow(1);
		HSSFCell cell_row2_1 = row2.createCell(0);
		cell_row2_1.setCellStyle(col_CellStyle);
		cell_row2_1.setCellValue("序号");
		HSSFCell cell_row2_2 = row2.createCell(1);
		cell_row2_2.setCellStyle(col_CellStyle);
		cell_row2_2.setCellValue("车牌号码");
		HSSFCell cell_row2_3 = row2.createCell(2);
		cell_row2_3.setCellStyle(col_CellStyle);
		cell_row2_3.setCellValue("录入时间");
		for(int i=0;i<excelBeanList.size();i++){
			FalseHphmExcelBean bean = excelBeanList.get(i);
			HSSFRow rowx = sheet.createRow(2+i);
			HSSFCell cell_rowx_1 = rowx.createCell(0);
			cell_rowx_1.setCellStyle(normal_CellStyle);
			cell_rowx_1.setCellValue(1+i);
			HSSFCell cell_rowx_2 = rowx.createCell(1);
			cell_rowx_2.setCellStyle(normal_CellStyle);
			cell_rowx_2.setCellValue(bean.getHphm());
			HSSFCell cell_rowx_3 = rowx.createCell(2);
			cell_rowx_3.setCellStyle(normal_CellStyle);
			cell_rowx_3.setCellValue(bean.getLrsh());
		}
		//输出
		workbook.write(outputStream);
		workbook.close();
	}
	/**
	 * 设备状态导出Excel
	 */
	public static void excelExportForDeviceStatus(
			List<ExcelBeanForJcdStatus> excelBeanList,
			ServletOutputStream outputStream) {
		//创建工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		//创建工作表
		HSSFSheet sheet = workbook.createSheet("设备状态查询");
		//合并单元格样式，excel 标题
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 9);
		//头标题样式
		HSSFCellStyle head_CellStyle = createHSSFCellStyle(workbook, (short)16);
		//设置列标题样式
//		HSSFCellStyle col_CellStyle = createTitleCellStyle(workbook, (short)13);
		//设置普通单元格样式
		HSSFCellStyle normal_CellStyle = createHSSFCellStypeNoBold(workbook,(short)12);
		//普通单元格 带颜色
//		HSSFCellStyle normal_CellStyleWithColor = createNormalCellStyleWithColor(workbook, (short)14, HSSFColor.GREY_25_PERCENT.index);
		//加载..合并单元格对象
		sheet.addMergedRegion(cellRangeAddress);//标题
		//设置单元格默认宽度
		sheet.setDefaultColumnWidth(12);
		//默认单元格 高度
		sheet.setDefaultRowHeightInPoints(25);
		HSSFRow row1 = sheet.createRow(0);
		HSSFCell cell_row1_1 = row1.createCell(0);
		cell_row1_1.setCellStyle(head_CellStyle);
		cell_row1_1.setCellValue("设备状态查询");
		HSSFRow row2 = sheet.createRow(1);
		HSSFCell cell_row2_1 = row2.createCell(0);
		cell_row2_1.setCellStyle(head_CellStyle);
		cell_row2_1.setCellValue("序号");
		HSSFCell cell_row2_2 = row2.createCell(1);
		cell_row2_2.setCellStyle(head_CellStyle);
		cell_row2_2.setCellValue("监测点ID");
		HSSFCell cell_row2_3 = row2.createCell(2);
		cell_row2_3.setCellStyle(head_CellStyle);
		cell_row2_3.setCellValue("监测点名称");
		HSSFCell cell_row2_4 = row2.createCell(3);
		cell_row2_4.setCellStyle(head_CellStyle);
		cell_row2_4.setCellValue("监测点类型");
		HSSFCell cell_row2_5 = row2.createCell(4);
		cell_row2_5.setCellStyle(head_CellStyle);
		cell_row2_5.setCellValue("行驶方向");
		HSSFCell cell_row2_6 = row2.createCell(5);
		cell_row2_6.setCellStyle(head_CellStyle);
		cell_row2_6.setCellValue("隶属城区");
		HSSFCell cell_row2_7 = row2.createCell(6);
		cell_row2_7.setCellStyle(head_CellStyle);
		cell_row2_7.setCellValue("隶属道路");
		HSSFCell cell_row2_8 = row2.createCell(7);
		cell_row2_8.setCellStyle(head_CellStyle);
		cell_row2_8.setCellValue("经度");
		HSSFCell cell_row2_9 = row2.createCell(8);
		cell_row2_9.setCellStyle(head_CellStyle);
		cell_row2_9.setCellValue("纬度");
		HSSFCell cell_row2_10 = row2.createCell(9);
		cell_row2_10.setCellStyle(head_CellStyle);
		cell_row2_10.setCellValue("状态");
		for(int i=0;i<excelBeanList.size();i++){
			ExcelBeanForJcdStatus bean = excelBeanList.get(i);
			HSSFRow rowx = sheet.createRow(i+2);
			HSSFCell cell_rowx_1 = rowx.createCell(0);
			cell_rowx_1.setCellStyle(normal_CellStyle);
			cell_rowx_1.setCellValue(i+1);
			HSSFCell cell_rowx_2 = rowx.createCell(1);
			cell_rowx_2.setCellStyle(normal_CellStyle);
			cell_rowx_2.setCellValue(bean.getJcdID());
			HSSFCell cell_rowx_3 = rowx.createCell(2);
			cell_rowx_3.setCellStyle(normal_CellStyle);
			cell_rowx_3.setCellValue(bean.getJcdName());
			HSSFCell cell_rowx_4 = rowx.createCell(3);
			cell_rowx_4.setCellStyle(normal_CellStyle);
			cell_rowx_4.setCellValue(bean.getJcdType());
			HSSFCell cell_rowx_5 = rowx.createCell(4);
			cell_rowx_5.setCellStyle(normal_CellStyle);
			cell_rowx_5.setCellValue(bean.getJcdDirection());
			HSSFCell cell_rowx_6 = rowx.createCell(5);
			cell_rowx_6.setCellStyle(normal_CellStyle);
			cell_rowx_6.setCellValue(bean.getJcdAtArea());
			HSSFCell cell_rowx_7 = rowx.createCell(6);
			cell_rowx_7.setCellStyle(normal_CellStyle);
			cell_rowx_7.setCellValue(bean.getJcdAtRoad());
			HSSFCell cell_rowx_8 = rowx.createCell(7);
			cell_rowx_8.setCellStyle(normal_CellStyle);
			cell_rowx_8.setCellValue(bean.getJcdJd());
			HSSFCell cell_rowx_9 = rowx.createCell(8);
			cell_rowx_9.setCellStyle(normal_CellStyle);
			cell_rowx_9.setCellValue(bean.getJcdWd());
			HSSFCell cell_rowx_10 = rowx.createCell(9);
			cell_rowx_10.setCellStyle(normal_CellStyle);
			cell_rowx_10.setCellValue(bean.getJcdZt());
		}
		//输出
		try {
			workbook.write(outputStream);
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 省厅月报统计报表生成方法
	 * @param list  数据集，包括实时拦截、事后拦截和交通违法数据
	 * @param bmmc  填报部门名称
	 * @param yhxm  填报人
	 * @param tel   电话号码
	 * @param tjsj  统计时间
	 * @return   excel表格
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public static void excelExportForYycx(User user, String kssj, String jssj, List<Dictionary> dicList, List listSslj, List listShch, List listJtwf, ServletOutputStream outputStream) throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook();// 生成excel报表
		HSSFSheet sheet = wb.createSheet("附表三");// 创建工作表

		HSSFRow row = sheet.createRow((short) 0);// 创建行
		HSSFCell cell = row.createCell((short) 0);// 创建单元格
		cell.setCellValue("治安卡口缉查布控系统应用成效统计月报表");// 赋值

		HSSFCellStyle style = wb.createCellStyle();// 设置表头样式
		// 设置样式
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中
		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short) 13);
		font.setFontName("仿宋_GB2312");
		style.setFont(font);
		sheet.addMergedRegion(new org.apache.poi.ss.util.Region(0, (short) 0, 0, (short) 11));// 合并单元格
		cell.setCellStyle(style);
		
		// 第二行
		HSSFRow row2 = sheet.createRow((short) 1);// 创建行
		HSSFCell cell2 = row2.createCell((short) 0);// 创建单元格
		cell2.setCellValue("（统计时间：" + kssj + "至" + jssj + "）");// 统计时间，应用是从前端把时间传过来
		HSSFCellStyle style2 = wb.createCellStyle();//
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中
		HSSFFont font2 = wb.createFont();
		font2.setFontHeightInPoints((short) 11);
		font2.setFontName("宋体");
		style2.setFont(font2);
		sheet.addMergedRegion(new org.apache.poi.ss.util.Region(1, (short) 0, 1, (short) 11));// 合并单元格
		cell2.setCellStyle(style2);

		// 第四行
		HSSFRow row3 = sheet.createRow((short) 3);// 创建行
		HSSFCell cell3 = row3.createCell((short) 0);// 创建单元格
		HSSFCellStyle styled = wb.createCellStyle();//
		styled.setAlignment(HSSFCellStyle.ALIGN_LEFT);// 居中
		HSSFFont fontd = wb.createFont();
		fontd.setFontHeightInPoints((short) 11);
		fontd.setFontName("宋体");
		styled.setFont(fontd);
		cell3.setCellValue("填报单位：" + user.getDeptName() + "            填报人：" + user.getUserName() + "          手机：" + user.getTelPhone());// 统计时间，应用是从前端把时间传过来
		sheet.addMergedRegion(new org.apache.poi.ss.util.Region(3, (short) 0, 3, (short) 11));// 合并单元格
		cell3.setCellStyle(styled);

		// 左边，成效类别15.625
		// 标题样式------------------------begin----------------
		HSSFCellStyle stylebt = wb.createCellStyle();//
		stylebt.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		stylebt.setWrapText(true);// 自动换行
		HSSFFont fontbt = wb.createFont();
		stylebt.setBorderBottom(HSSFCellStyle.BORDER_THIN);//设置边框
		stylebt.setBorderLeft(HSSFCellStyle.BORDER_THIN);//设置边框
		stylebt.setBorderRight(HSSFCellStyle.BORDER_THIN);//设置边框
		stylebt.setBorderTop(HSSFCellStyle.BORDER_THIN);//设置边框
		fontbt.setFontHeightInPoints((short) 12);
		fontbt.setFontName("仿宋_GB2312");
		stylebt.setFont(fontbt);
		sheet.setColumnWidth((short) 2, (short) (40 * 35.7));// 列宽35.7为一个像素
		sheet.setColumnWidth((short) 3, (short) (75 * 35.7));// 列宽
		sheet.setColumnWidth((short) 4, (short) (80 * 35.7));// 列宽
		sheet.setColumnWidth((short) 5, (short) (80 * 35.7));// 列宽
		sheet.setColumnWidth((short) 6, (short) (120 * 35.7));// 列宽
		sheet.setColumnWidth((short) 7, (short) (120 * 35.7));// 列宽
		sheet.setColumnWidth((short) 8, (short) (80 * 35.7));// 列宽
		sheet.setColumnWidth((short) 9, (short) (70 * 35.7));// 列宽
		sheet.setColumnWidth((short) 10, (short) (70 * 35.7));// 列宽
		sheet.setColumnWidth((short) 11, (short) (50 * 35.7));// 列宽
		// 标题样式------------------------begin----------------
		// 左边，成效类别15.625

		// 标题样式------------------------begin----------------
		HSSFCellStyle stylenr = wb.createCellStyle();//
		stylenr.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		stylenr.setWrapText(true);// 自动换行
		stylenr.setBorderBottom(HSSFCellStyle.BORDER_THIN);//设置边框
		stylenr.setBorderLeft(HSSFCellStyle.BORDER_THIN);//设置边框
		stylenr.setBorderRight(HSSFCellStyle.BORDER_THIN);//设置边框
		stylenr.setBorderTop(HSSFCellStyle.BORDER_THIN);//设置边框
		HSSFFont fontnr = wb.createFont();
		fontnr.setFontHeightInPoints((short) 9);
		fontnr.setFontName("宋体");
		stylenr.setFont(fontnr);

		// 标题样式------------------------begin----------------
		// 生成 ----实时拦截涉案车
		int insslj = (listSslj.size() > 4?listSslj.size():4);
		HSSFRow row4 = sheet.createRow((short) 4);// 创建行
		HSSFCell cell4 = row4.createCell((short) 1);// 创建单元格
		sheet.setColumnWidth((short) 1, (short) (35 * 35.7));// 列宽
		cell4.setCellValue("实时拦截涉案车");
		HSSFCellStyle style4 = wb.createCellStyle();//
		HSSFFont font4 = wb.createFont();
		font4.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font4.setFontHeightInPoints((short) 12);
		font4.setFontName("楷体_GB2312");
		style4.setFont(font4);
		style4.setWrapText(true);// 自动换行
		style4.setBorderBottom(HSSFCellStyle.BORDER_THIN);//设置边框
		style4.setBorderLeft(HSSFCellStyle.BORDER_THIN);//设置边框
		style4.setBorderRight(HSSFCellStyle.BORDER_THIN);//设置边框
		style4.setBorderTop(HSSFCellStyle.BORDER_THIN);//设置边框
		style4.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
		org.apache.poi.ss.util.Region reg = new org.apache.poi.ss.util.Region(4, (short) 1, 4 + insslj, (short) 1);
		addMergedRegionStyle(sheet, reg, style4);//设置合并单元格边框
		sheet.addMergedRegion(reg);// 合并单元格
		cell4.setCellStyle(style4);

		row4.setHeightInPoints(20);// 行高
		HSSFCell cellbt = row4.createCell((short) 2);// 创建单元格
		cellbt.setCellValue("序号");
		cellbt.setCellStyle(stylebt);// 样式

		cellbt = row4.createCell((short) 3);// 创建单元格
		cellbt.setCellValue("车辆牌号");
		cellbt.setCellStyle(stylebt);// 样式

		cellbt = row4.createCell((short) 4);// 创建单元格
		cellbt.setCellValue("布控时间");
		cellbt.setCellStyle(stylebt);// 样式

		cellbt = row4.createCell((short) 5);// 创建单元格
		cellbt.setCellValue("预警时间");
		cellbt.setCellStyle(stylebt);// 样式

		cellbt = row4.createCell((short) 6);// 创建单元格
		cellbt.setCellValue("预警卡口名称");
		cellbt.setCellStyle(stylebt);// 样式

		cellbt = row4.createCell((short) 7);// 创建单元格
		cellbt.setCellValue("成功拦截单位");
		cellbt.setCellStyle(stylebt);// 样式

		cellbt = row4.createCell((short) 8);// 创建单元格
		cellbt.setCellValue("目标车辆属性");
		cellbt.setCellStyle(stylebt);// 样式

		cellbt = row4.createCell((short) 9);// 创建单元格
		cellbt.setCellValue("抓获嫌疑人数");
		cellbt.setCellStyle(stylebt);// 样式

		cellbt = row4.createCell((short) 10);// 创建单元格
		cellbt.setCellValue("破获案件数");
		cellbt.setCellStyle(stylebt);// 样式

		cellbt = row4.createCell((short) 11);// 创建单元格
		cellbt.setCellValue("备注");
		cellbt.setCellStyle(stylebt);// 样式
		for (int i = 0; i < listSslj.size(); i++) {
			Object[] obj = (Object[])listSslj.get(i);
			Dispatched dispatched = (Dispatched)obj[0];
			InstructionSign inSign = (InstructionSign)obj[1];
				
			HSSFRow rownr = sheet.createRow((short) 5 + i);// 创建行
			rownr.setHeightInPoints(20);// 行高
			HSSFCell cellnr = rownr.createCell((short) 2);// 创建单元格
			cellnr.setCellValue("" + (i + 1));// 序号
			cellnr.setCellStyle(stylenr);// 样式
			cellnr = rownr.createCell((short) 3);// 车牌号
			cellnr.setCellValue(dispatched.getHphm());
			cellnr.setCellStyle(stylenr);// 样式

			cellnr = rownr.createCell((short) 4);// 布控时间
			cellnr.setCellValue(dispatched.getBksj());
			cellnr.setCellStyle(stylenr);// 样式

			cellnr = rownr.createCell((short) 5);// 报警时间
			cellnr.setCellValue(inSign.getInstruction().getEwrecieve().getBjsj());
			cellnr.setCellStyle(stylenr);// 样式
			cellnr = rownr.createCell((short) 6);// 报警卡口名称
			cellnr.setCellValue(inSign.getInstruction().getEwrecieve().getJcdmc());
			cellnr.setCellStyle(stylenr);// 样式

			cellnr = rownr.createCell((short) 7);// 成功拦截单位
			cellnr.setCellValue(inSign.getFkbmmc());
			cellnr.setCellStyle(stylenr);// 样式

			String bjlx = "";
			for(int j=0;j < dicList.size();j++){
				Dictionary dic = dicList.get(j);
				if(dic.getTypeCode() != null && dic.getTypeSerialNo() != null 
					&& "BKDL1".equals(dic.getTypeCode()) && dic.getTypeSerialNo().trim().equals(dispatched.getBjlx())){
					bjlx = dic.getTypeDesc();
				}
			}
			
			cellnr = rownr.createCell((short) 8);// 目标车属性
			cellnr.setCellValue(bjlx);
			cellnr.setCellStyle(stylenr);// 样式

			cellnr = rownr.createCell((short) 9);// 抓获人数
			cellnr.setCellValue(inSign.getZhrs());
			cellnr.setCellStyle(stylenr);// 样式

			cellnr = rownr.createCell((short) 10);// 案件数
			cellnr.setCellValue(inSign.getPhajs());
			cellnr.setCellStyle(stylenr);// 样式

			cellnr = rownr.createCell((short) 11);// 备注
			cellnr.setCellValue("");
			cellnr.setCellStyle(stylenr);// 样式
		}//for
		//空行
		if(listSslj.size() < 4){
			for (int i = listSslj.size(); i < 4; i++) {
				HSSFRow rownr = sheet.createRow((short) 5 + i);// 创建行
				rownr.setHeightInPoints(20);// 行高
				HSSFCell cellnr = rownr.createCell((short) 2);// 创建单元格
				cellnr.setCellValue("" + (i + 1));// 序号
				cellnr.setCellStyle(stylenr);// 样式
				cellnr = rownr.createCell((short) 3);// 车牌号
				cellnr.setCellValue("");
				cellnr.setCellStyle(stylenr);// 样式

				cellnr = rownr.createCell((short) 4);// 布控时间
				cellnr.setCellValue("");
				cellnr.setCellStyle(stylenr);// 样式

				cellnr = rownr.createCell((short) 5);// 报警时间
				cellnr.setCellValue("");
				cellnr.setCellStyle(stylenr);// 样式
				cellnr = rownr.createCell((short) 6);// 报警卡口名称
				cellnr.setCellValue("");
				cellnr.setCellStyle(stylenr);// 样式

				cellnr = rownr.createCell((short) 7);// 成功拦截单位
				cellnr.setCellValue("");
				cellnr.setCellStyle(stylenr);// 样式

				cellnr = rownr.createCell((short) 8);// 目标车属性
				cellnr.setCellValue("");
				cellnr.setCellStyle(stylenr);// 样式

				cellnr = rownr.createCell((short) 9);// 抓获人数
				cellnr.setCellValue("");
				cellnr.setCellStyle(stylenr);// 样式

				cellnr = rownr.createCell((short) 10);// 案件数
				cellnr.setCellValue("");
				cellnr.setCellStyle(stylenr);// 样式

				cellnr = rownr.createCell((short) 11);// 备注
				cellnr.setCellValue("");
				cellnr.setCellStyle(stylenr);// 样式
			}//for
		}
		
			
		int inshch = (listShch.size() > 4?listShch.size():4);;
		HSSFRow row5 = sheet.createRow((short) 5 + insslj);// 创建行
		HSSFCell cell5 = row5.createCell((short) 1);// 创建单元格
		sheet.setColumnWidth((short) 1, (short) (35 * 35.7));// 列宽
		cell5.setCellValue("事后查获涉案车");
		sheet.addMergedRegion(new org.apache.poi.ss.util.Region(5 + insslj, (short) 1, 5 + insslj + inshch, (short) 1));// 合并单元格
		cell5.setCellStyle(style4);

		row5.setHeightInPoints(30);// 行高
		cellbt = row5.createCell((short) 2);// 创建单元格
		cellbt.setCellValue("序号");
		cellbt.setCellStyle(stylebt);// 样式

		cellbt = row5.createCell((short) 3);// 创建单元格
		cellbt.setCellValue("车辆牌号");
		cellbt.setCellStyle(stylebt);// 样式

		cellbt = row5.createCell((short) 4);// 创建单元格
		cellbt.setCellValue("布控时间");
		cellbt.setCellStyle(stylebt);// 样式

		cellbt = row5.createCell((short) 5);// 创建单元格
		cellbt.setCellValue("查获时间");
		cellbt.setCellStyle(stylebt);// 样式

		cellbt = row5.createCell((short) 6);// 创建单元格
		cellbt.setCellValue("获取关键线索卡口名称");
		cellbt.setCellStyle(stylebt);// 样式

		cellbt = row5.createCell((short) 7);// 创建单元格
		cellbt.setCellValue("查获单位");
		cellbt.setCellStyle(stylebt);// 样式

		cellbt = row5.createCell((short) 8);// 创建单元格
		cellbt.setCellValue("目标车辆属性");
		cellbt.setCellStyle(stylebt);// 样式

		cellbt = row5.createCell((short) 9);// 创建单元格
		cellbt.setCellValue("抓获嫌疑人数");
		cellbt.setCellStyle(stylebt);// 样式

		cellbt = row5.createCell((short) 10);// 创建单元格
		cellbt.setCellValue("破获案件数");
		cellbt.setCellStyle(stylebt);// 样式

		cellbt = row5.createCell((short) 11);// 创建单元格
		cellbt.setCellValue("备注");
		cellbt.setCellStyle(stylebt);// 样式
		for (int i = 0; i < listShch.size(); i++) {
			Object[] obj = (Object[])listShch.get(i);
			Dispatched dispatched = (Dispatched)obj[0];
			InstructionSign inSign = (InstructionSign)obj[1];
			
			HSSFRow rownr = sheet.createRow((short) 6 + insslj + i);// 创建行
			rownr.setHeightInPoints(25);// 行高
			HSSFCell cellnr = rownr.createCell((short) 2);// 创建单元格
			cellnr.setCellValue("" + (i + 1));// 序号
			cellnr.setCellStyle(stylenr);// 样式

			cellnr = rownr.createCell((short) 3);// 车牌号
			cellnr.setCellValue(dispatched.getHphm());
			cellnr.setCellStyle(stylenr);// 样式

			cellnr = rownr.createCell((short) 4);// 布控时间
			cellnr.setCellValue(dispatched.getBksj());
			cellnr.setCellStyle(stylenr);// 样式

			cellnr = rownr.createCell((short) 5);// 报警时间
			cellnr.setCellValue(inSign.getChsj());
			cellnr.setCellStyle(stylenr);// 样式

			cellnr = rownr.createCell((short) 6);// 报警卡口名称
			cellnr.setCellValue(inSign.getChdd());
			cellnr.setCellStyle(stylenr);// 样式

			cellnr = rownr.createCell((short) 7);// 成功拦截单位
			cellnr.setCellValue(inSign.getFkbmmc());
			cellnr.setCellStyle(stylenr);// 样式

			String bjlx = "";
			for(int j=0;j < dicList.size();j++){
				Dictionary dic = dicList.get(j);
				if(dic.getTypeCode() != null && dic.getTypeSerialNo() != null 
					&& "BKDL1".equals(dic.getTypeCode()) && dic.getTypeSerialNo().trim().equals(dispatched.getBjlx())){
					bjlx = dic.getTypeDesc();
				}
			}
			
			cellnr = rownr.createCell((short) 8);// 目标车属性
			cellnr.setCellValue(bjlx);
			cellnr.setCellStyle(stylenr);// 样式

			cellnr = rownr.createCell((short) 9);// 抓获人数
			cellnr.setCellValue(inSign.getZhrs());
			cellnr.setCellStyle(stylenr);// 样式

			cellnr = rownr.createCell((short) 10);// 案件数
			cellnr.setCellValue(inSign.getPhajs());
			cellnr.setCellStyle(stylenr);// 样式

			cellnr = rownr.createCell((short) 11);// 备注
			cellnr.setCellValue("");
			cellnr.setCellStyle(stylenr);// 样式
		}//for
		// 空行
		if (listShch.size() < 4) {
			for (int i = listShch.size(); i < 4; i++) {
				HSSFRow rownr = sheet.createRow((short) 6 + insslj + i);// 创建行
				rownr.setHeightInPoints(20);// 行高
				HSSFCell cellnr = rownr.createCell((short) 2);// 创建单元格
				cellnr.setCellValue("" + (i + 1));// 序号
				cellnr.setCellStyle(stylenr);// 样式
				cellnr = rownr.createCell((short) 3);// 车牌号
				cellnr.setCellValue("");
				cellnr.setCellStyle(stylenr);// 样式

				cellnr = rownr.createCell((short) 4);// 布控时间
				cellnr.setCellValue("");
				cellnr.setCellStyle(stylenr);// 样式

				cellnr = rownr.createCell((short) 5);// 报警时间
				cellnr.setCellValue("");
				cellnr.setCellStyle(stylenr);// 样式
				cellnr = rownr.createCell((short) 6);// 报警卡口名称
				cellnr.setCellValue("");
				cellnr.setCellStyle(stylenr);// 样式

				cellnr = rownr.createCell((short) 7);// 成功拦截单位
				cellnr.setCellValue("");
				cellnr.setCellStyle(stylenr);// 样式

				cellnr = rownr.createCell((short) 8);// 目标车属性
				cellnr.setCellValue("");
				cellnr.setCellStyle(stylenr);// 样式

				cellnr = rownr.createCell((short) 9);// 抓获人数
				cellnr.setCellValue("");
				cellnr.setCellStyle(stylenr);// 样式

				cellnr = rownr.createCell((short) 10);// 案件数
				cellnr.setCellValue("");
				cellnr.setCellStyle(stylenr);// 样式

				cellnr = rownr.createCell((short) 11);// 备注
				cellnr.setCellValue("");
				cellnr.setCellStyle(stylenr);// 样式
			}// for
		}

//		// 生成 ----交通违法车-----------------begin-----------------
		int injtwf = (listJtwf.size() > 4?listJtwf.size():4);// 实时拦截文字占用行数
		HSSFRow row6 = sheet.createRow((short) 6 + insslj + inshch);// 创建行
		HSSFCell cell6 = row6.createCell((short) 1);// 创建单元格
		sheet.setColumnWidth((short) 1, (short) (35 * 35.7));// 列宽35.7为一个像素
		cell6.setCellValue("查获交通违法车");
		sheet.addMergedRegion(new org.apache.poi.ss.util.Region(6 + insslj + inshch, (short) 1, 6 + insslj + inshch + injtwf, (short) 1));// 合并单元格
		cell6.setCellStyle(style4);

		row6.setHeightInPoints(30);// 行高
		cellbt = row6.createCell((short) 2);// 创建单元格
		cellbt.setCellValue("序号");
		cellbt.setCellStyle(stylebt);// 样式

		cellbt = row6.createCell((short) 3);// 创建单元格
		cellbt.setCellValue("车辆牌号");
		cellbt.setCellStyle(stylebt);// 样式

		cellbt = row6.createCell((short) 4);// 创建单元格
		cellbt.setCellValue("布控时间");
		cellbt.setCellStyle(stylebt);// 样式
		cellbt = row6.createCell((short) 5);// 创建单元格
		cellbt.setCellValue("查获时间");
		cellbt.setCellStyle(stylebt);// 样式
		cellbt = row6.createCell((short) 6);// 创建单元格
		cellbt.setCellValue("获取关键线索卡口名称");
		cellbt.setCellStyle(stylebt);// 样式
		cellbt = row6.createCell((short) 7);// 创建单元格
		cellbt.setCellValue("查获单位");
		cellbt.setCellStyle(stylebt);// 样式

		cellbt = row6.createCell((short) 8);// 创建单元格
		cellbt.setCellValue("目标车辆属性");
		cellbt.setCellStyle(stylebt);// 样式
		cellbt = row6.createCell((short) 9);// 创建单元格
		cellbt.setCellValue("抓获嫌疑人数");
		cellbt.setCellStyle(stylebt);// 样式

		cellbt = row6.createCell((short) 10);// 创建单元格
		cellbt.setCellValue("破获案件数");
		cellbt.setCellStyle(stylebt);// 样式

		cellbt = row6.createCell((short) 11);// 创建单元格
		cellbt.setCellValue("备注");
		cellbt.setCellStyle(stylebt);// 样式
		for (int i = 0; i < listJtwf.size(); i++) {
			Object[] obj = (Object[])listSslj.get(i);
			Dispatched dispatched = (Dispatched)obj[0];
			InstructionSign inSign = (InstructionSign)obj[1];
			
			HSSFRow rownr = sheet.createRow((short) 7 + insslj + i+inshch);// 创建行
			rownr.setHeightInPoints(25);// 行高
			HSSFCell cellnr = rownr.createCell((short) 2);// 创建单元格
			cellnr.setCellValue("" + (i + 1));// 序号
			cellnr.setCellStyle(stylenr);// 样式
			cellnr = rownr.createCell((short) 3);// 车牌号
			cellnr.setCellValue(dispatched.getHphm());
			cellnr.setCellStyle(stylenr);// 样式

			cellnr = rownr.createCell((short) 4);// 布控时间
			cellnr.setCellValue(dispatched.getBksj());
			cellnr.setCellStyle(stylenr);// 样式

			cellnr = rownr.createCell((short) 5);// 报警时间
			cellnr.setCellValue(inSign.getChsj());
			cellnr.setCellStyle(stylenr);// 样式

			cellnr = rownr.createCell((short) 6);// 报警卡口名称
			cellnr.setCellValue(inSign.getChdd());
			cellnr.setCellStyle(stylenr);// 样式

			cellnr = rownr.createCell((short) 7);// 成功拦截单位
			cellnr.setCellValue(inSign.getFkbmmc());
			cellnr.setCellStyle(stylenr);// 样式

			String bjlx = "";
			for(int j=0;j < dicList.size();j++){
				Dictionary dic = dicList.get(j);
				if(dic.getTypeCode() != null && dic.getTypeSerialNo() != null 
					&& "BKDL2".equals(dic.getTypeCode()) && dic.getTypeSerialNo().trim().equals(dispatched.getBjlx())){
					bjlx = dic.getTypeDesc();
				}
			}
			
			cellnr = rownr.createCell((short) 8);// 目标车属性
			cellnr.setCellValue(bjlx);
			cellnr.setCellStyle(stylenr);// 样式

			cellnr = rownr.createCell((short) 9);// 抓获人数
			cellnr.setCellValue(inSign.getZhrs());
			cellnr.setCellStyle(stylenr);// 样式

			cellnr = rownr.createCell((short) 10);// 案件数
			cellnr.setCellValue(inSign.getPhajs());
			cellnr.setCellStyle(stylenr);// 样式

			cellnr = rownr.createCell((short) 11);// 备注
			cellnr.setCellValue("");
			cellnr.setCellStyle(stylenr);// 样式
		}//for
		// 空行
		if (listJtwf.size() < 4) {
			for (int i = listJtwf.size(); i < 4;i++) {
				HSSFRow rownr = sheet.createRow((short) 7 + insslj + inshch + i);// 创建行
				rownr.setHeightInPoints(20);// 行高
				HSSFCell cellnr = rownr.createCell((short) 2);// 创建单元格
				cellnr.setCellValue("" + (i + 1));// 序号
				cellnr.setCellStyle(stylenr);// 样式
				cellnr = rownr.createCell((short) 3);// 车牌号
				cellnr.setCellValue("");
				cellnr.setCellStyle(stylenr);// 样式

				cellnr = rownr.createCell((short) 4);// 布控时间
				cellnr.setCellValue("");
				cellnr.setCellStyle(stylenr);// 样式

				cellnr = rownr.createCell((short) 5);// 报警时间
				cellnr.setCellValue("");
				cellnr.setCellStyle(stylenr);// 样式
				cellnr = rownr.createCell((short) 6);// 报警卡口名称
				cellnr.setCellValue("");
				cellnr.setCellStyle(stylenr);// 样式

				cellnr = rownr.createCell((short) 7);// 成功拦截单位
				cellnr.setCellValue("");
				cellnr.setCellStyle(stylenr);// 样式

				cellnr = rownr.createCell((short) 8);// 目标车属性
				cellnr.setCellValue("");
				cellnr.setCellStyle(stylenr);// 样式

				cellnr = rownr.createCell((short) 9);// 抓获人数
				cellnr.setCellValue("");
				cellnr.setCellStyle(stylenr);// 样式

				cellnr = rownr.createCell((short) 10);// 案件数
				cellnr.setCellValue("");
				cellnr.setCellStyle(stylenr);// 样式

				cellnr = rownr.createCell((short) 11);// 备注
				cellnr.setCellValue("");
				cellnr.setCellStyle(stylenr);// 样式
			}// for
		}
					
		//左边第一栏标题
		HSSFCell cellb1 = row4.createCell((short) 0);// 创建单元格
		sheet.setColumnWidth((short) 0, (short) (25 * 35.7));// 列宽
		cellb1.setCellValue("成效类别");
		HSSFCellStyle styleb = wb.createCellStyle();//
		HSSFFont fontb = wb.createFont();
		fontb.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		fontb.setFontHeightInPoints((short) 12);
		fontb.setFontName("黑体");
		styleb.setFont(fontb);
		styleb.setWrapText(true);// 自动换行
		styleb.setBorderBottom(HSSFCellStyle.BORDER_THIN);//设置边框
		styleb.setBorderLeft(HSSFCellStyle.BORDER_THIN);//设置边框
		styleb.setBorderRight(HSSFCellStyle.BORDER_THIN);//设置边框
		styleb.setBorderTop(HSSFCellStyle.BORDER_THIN);//设置边框
		styleb.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
		sheet.addMergedRegion(new org.apache.poi.ss.util.Region(4, (short) 0, 6 + insslj+inshch+injtwf, (short) 0));// 合并单元格
		cellb1.setCellStyle(styleb);
								
		//表格下方注解
		//样式 
		HSSFCellStyle stylebj = wb.createCellStyle();// 设置表头样式
		// 设置样式
		stylebj.setAlignment(HSSFCellStyle.ALIGN_LEFT);// 居中
		HSSFFont fontbj = wb.createFont();
		fontbj.setFontHeightInPoints((short) 12);
		fontbj.setFontName("仿宋_GB2312");
		stylebj.setFont(fontbj);
		int megint = 7 + insslj+inshch+injtwf;
		sheet.addMergedRegion(new org.apache.poi.ss.util.Region(megint, (short) 0, megint, (short) 11));// 合并单元格
		sheet.addMergedRegion(new org.apache.poi.ss.util.Region(megint+1, (short) 0, megint+1, (short) 11));// 合并单元格
		sheet.addMergedRegion(new org.apache.poi.ss.util.Region(megint+2, (short) 0, megint+2, (short) 11));// 合并单元格
		sheet.addMergedRegion(new org.apache.poi.ss.util.Region(megint+3, (short) 0, megint+3, (short) 11));// 合并单元格
								
		row = sheet.createRow((short) megint);// 创建行
		cell = row.createCell((short) 0);// 创建单元格
		cell.setCellValue("  注：1、目标车辆属性指成功拦截车辆属被盗车、被抢车、交通肇事逃逸车、XX案涉案车、嫌疑车，或者属交通违法车。");// 赋值
		cell.setCellStyle(stylebj);
								
		row = sheet.createRow((short) megint+1);// 创建行
		cell = row.createCell((short) 0);// 创建单元格
		cell.setCellValue("      2、获取关键线索卡口名称可以是一个或多个，由查获单位确定。");// 赋值
		cell.setCellStyle(stylebj);
		
		row = sheet.createRow((short) megint+2);// 创建行
		cell = row.createCell((short) 0);// 创建单元格
		cell.setCellValue("      3、查获交通违法车分为布控预警后成功拦截和利用卡口查询功能获取线索事后查获两种情形；第一种选填带*项，第二种选填带Δ项。 ");// 赋值
		cell.setCellStyle(stylebj);
								
		row = sheet.createRow((short) megint+3);// 创建行
		cell = row.createCell((short) 0);// 创建单元格
		cell.setCellValue("      4、当月数据与上月数据相比没有改变的，可在备注栏中填明“没有更改”。");// 赋值
		cell.setCellStyle(stylebj);

		//写出
		wb.write(outputStream);
		wb.close();
	}
	
	/**
	 * 号牌识别情况监测excel 导出
	 */
	public static void exportHpsbqkjcExcel(List<TjEnitity> hpsbqkjcList, ServletOutputStream outputStream) {
		//创建工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		try {
			//合并单元格样式
			CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 6);
			//头标题样式
			HSSFCellStyle head_CellStyle = createHSSFCellStyle(workbook, (short)16);
			//设置普通单元格样式
			HSSFCellStyle normal_CellStyle = createHSSFCellStypeNoBold(workbook,(short)12);
			//创建工作表
			HSSFSheet sheet = workbook.createSheet("深圳市缉查布控系统号牌识别情况监测统计表");
			//title 单元格样式
			HSSFCellStyle title_CellStyle = createTitleCellStyle(workbook, (short)12);
			//加载..合并单元格对象
			sheet.addMergedRegion(cellRangeAddress);//标题
			//设置单元格默认宽度
			sheet.setDefaultColumnWidth(12);
			sheet.setColumnWidth(0, 256*8);
			sheet.setColumnWidth(1, 256*20);
			sheet.setColumnWidth(2, 256*40);
			sheet.setColumnWidth(3, 256*20);
			sheet.setColumnWidth(4, 256*20);
			
			//创建行
			//头标题行,第一行第一列
			HSSFRow row1 = sheet.createRow(0);
			row1.setHeight((short)1000);
			HSSFCell cell_1_1 = row1.createCell(0);
			cell_1_1.setCellStyle(head_CellStyle);
			cell_1_1.setCellValue("深圳市缉查布控系统号牌识别情况监测统计表");
			cellRangeAddressSetBorder(cellRangeAddress, sheet, workbook);
			
			//第2行
			HSSFRow row2 = sheet.createRow(1);
			row2.setHeight((short)400);
			HSSFCell cell_2_1 = row2.createCell(0);
			cell_2_1.setCellStyle(title_CellStyle);
			cell_2_1.setCellValue("序号");
			HSSFCell cell_2_2 = row2.createCell(1);
			cell_2_2.setCellStyle(title_CellStyle);
			cell_2_2.setCellValue("监测点编号");
			HSSFCell cell_2_3 = row2.createCell(2);
			cell_2_3.setCellStyle(title_CellStyle);
			cell_2_3.setCellValue("监测点名称");
			HSSFCell cell_2_4 = row2.createCell(3);
			cell_2_4.setCellStyle(title_CellStyle);
			cell_2_4.setCellValue("已识别量（条）");
			HSSFCell cell_2_5 = row2.createCell(4);
			cell_2_5.setCellStyle(title_CellStyle);
			cell_2_5.setCellValue("未识别量（条）");
			HSSFCell cell_2_6 = row2.createCell(5);
			cell_2_6.setCellStyle(title_CellStyle);
			cell_2_6.setCellValue("总量（条）");
			HSSFCell cell_2_7 = row2.createCell(6);
			cell_2_7.setCellStyle(title_CellStyle);
			cell_2_7.setCellValue("识别率");
			
			int rowHeight = 2;
			if(hpsbqkjcList != null && hpsbqkjcList.size() > 0){
				for(int i=0;i < hpsbqkjcList.size();i++){
					//获取信息
					TjEnitity tjEnitity = hpsbqkjcList.get(i);
					//创建行
					HSSFRow rowx = sheet.createRow(rowHeight++);
					//序号
					HSSFCell cell_x_1 = rowx.createCell(0);
					cell_x_1.setCellStyle(normal_CellStyle);
					cell_x_1.setCellValue(tjEnitity.getSn());
					//监测点ID
					HSSFCell cell_x_2 = rowx.createCell(1);
					cell_x_2.setCellStyle(normal_CellStyle);
					cell_x_2.setCellValue(tjEnitity.getJcdid());
					//监测点名称
					HSSFCell cell_x_3 = rowx.createCell(2);
					cell_x_3.setCellStyle(normal_CellStyle);
					cell_x_3.setCellValue(tjEnitity.getJcdmc());
					//已识别量
					HSSFCell cell_x_4 = rowx.createCell(3);
					cell_x_4.setCellStyle(normal_CellStyle);
					cell_x_4.setCellValue(tjEnitity.getIdentify());
					//未识别量
					HSSFCell cell_x_5 = rowx.createCell(4);
					cell_x_5.setCellStyle(normal_CellStyle);
					cell_x_5.setCellValue(tjEnitity.getNotIdentify());
					//总量
					HSSFCell cell_x_6 = rowx.createCell(5);
					cell_x_6.setCellStyle(normal_CellStyle);
					cell_x_6.setCellValue(tjEnitity.getTotalRst());
					//识别率
					HSSFCell cell_x_7 = rowx.createCell(6);
					cell_x_7.setCellStyle(normal_CellStyle);
					cell_x_7.setCellValue(tjEnitity.getSbl());
				}
			}
			
			//发送前端
			workbook.write(outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				workbook.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 卡口在线情况监测excel 导出
	 */
	public static void exportKkzxqkjcExcel(List<Object> kkzxqkjcList, String flag, 
			String zxl, int zs, int zxs, ServletOutputStream outputStream) {
		//创建工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		try {
			//合并单元格样式
			CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 3);
			//头标题样式
			HSSFCellStyle head_CellStyle = createHSSFCellStyle(workbook, (short)16);
			//设置普通单元格样式
			HSSFCellStyle normal_CellStyle = createHSSFCellStypeNoBold(workbook,(short)12);
			//创建工作表
			HSSFSheet sheet = workbook.createSheet("深圳市缉查布控系统卡口在线情况监测统计表");
			//title 单元格样式
			HSSFCellStyle title_CellStyle = createTitleCellStyle(workbook, (short)12);
			//加载..合并单元格对象
			sheet.addMergedRegion(cellRangeAddress);//标题
			//设置单元格默认宽度
			sheet.setDefaultColumnWidth(12);
			sheet.setColumnWidth(0, 256*10);
			sheet.setColumnWidth(1, 256*25);
			sheet.setColumnWidth(2, 256*40);
			sheet.setColumnWidth(3, 256*25);
			
			//创建行
			//头标题行,第一行第一列
			HSSFRow row1 = sheet.createRow(0);
			row1.setHeight((short)1000);
			HSSFCell cell_1_1 = row1.createCell(0);
			cell_1_1.setCellStyle(head_CellStyle);
			cell_1_1.setCellValue("深圳市缉查布控系统卡口在线情况监测统计表");
			cellRangeAddressSetBorder(cellRangeAddress, sheet, workbook);
			
			//行数
			int rowHeight = 1;
			
			//第二行 
			if("1".equals(flag) && !"".equals(zxl) && zs != 0){
				HSSFRow row2 = sheet.createRow(rowHeight++);
				HSSFCell cell_2_1 = row2.createCell(0);
				row2.setHeight((short)400);
				cell_2_1.setCellStyle(normal_CellStyle);
				cell_2_1.setCellValue(CommonUtils.getTimeOfBefore_N_Day_String("yyyy年MM月dd日", new Date(), 1) + "卡口在线情况：卡口总数 " + zs + " 个，在线卡口数 " + zxs + " 个，在线率为 " + zxl);
				CellRangeAddress cellRangeAddress2 = new CellRangeAddress(1, 1, 0, 3);
				sheet.addMergedRegion(cellRangeAddress2);
				cellRangeAddressSetBorder(cellRangeAddress2, sheet, workbook);
			}
			
			//表题行
			HSSFRow row3 = sheet.createRow(rowHeight++);
			row3.setHeight((short)400);
			HSSFCell cell_3_1 = row3.createCell(0);
			cell_3_1.setCellStyle(title_CellStyle);
			cell_3_1.setCellValue("序号");
			HSSFCell cell_3_2 = row3.createCell(1);
			cell_3_2.setCellStyle(title_CellStyle);
			cell_3_2.setCellValue("监测点编号");
			HSSFCell cell_3_3 = row3.createCell(2);
			cell_3_3.setCellStyle(title_CellStyle);
			cell_3_3.setCellValue("监测点名称");
			HSSFCell cell_3_4 = row3.createCell(3);
			cell_3_4.setCellStyle(title_CellStyle);
			cell_3_4.setCellValue("在线状态");
			
			if(kkzxqkjcList != null && kkzxqkjcList.size() > 0){
				for(int i=0;i < kkzxqkjcList.size();i++){
					//获取信息
					Object obj = kkzxqkjcList.get(i);
					if(obj instanceof Object[]){
						Jcd jcd = (Jcd)((Object[])obj)[0];
						JCDStatus jcdStatus = null;
						if(((Object[])obj).length > 1){
							jcdStatus = (JCDStatus)((Object[])obj)[1];
						}
						
						//创建行
						HSSFRow rowx = sheet.createRow(rowHeight++);
						//序号
						HSSFCell cell_x_1 = rowx.createCell(0);
						cell_x_1.setCellStyle(normal_CellStyle);
						cell_x_1.setCellValue((i+1));
						//监测点ID
						HSSFCell cell_x_2 = rowx.createCell(1);
						cell_x_2.setCellStyle(normal_CellStyle);
						cell_x_2.setCellValue(jcd.getId());
						//监测点名称
						HSSFCell cell_x_3 = rowx.createCell(2);
						cell_x_3.setCellStyle(normal_CellStyle);
						cell_x_3.setCellValue(jcd.getJcdmc());
						//在线状态
						HSSFCell cell_x_4 = rowx.createCell(3);
						cell_x_4.setCellStyle(normal_CellStyle);
						cell_x_4.setCellValue((jcdStatus == null?"在线":"不在线"));
					}
				}
			}
			
			//发送前端
			workbook.write(outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				workbook.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 数据传输情况监测excel 导出
	 */
	public static void exportSjcsqkjcExcel(List<TjEnitity> hpsbqkjcList, ServletOutputStream outputStream) {
		//创建工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		try {
			//合并单元格样式
			CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 6);
			//头标题样式
			HSSFCellStyle head_CellStyle = createHSSFCellStyle(workbook, (short)16);
			//设置普通单元格样式
			HSSFCellStyle normal_CellStyle = createHSSFCellStypeNoBold(workbook,(short)12);
			//创建工作表
			HSSFSheet sheet = workbook.createSheet("深圳市缉查布控系统数据传输情况监测统计表");
			//title 单元格样式
			HSSFCellStyle title_CellStyle = createTitleCellStyle(workbook, (short)12);
			//加载..合并单元格对象
			sheet.addMergedRegion(cellRangeAddress);//标题
			//设置单元格默认宽度
			sheet.setDefaultColumnWidth(25);
			sheet.setColumnWidth(0, 256*8);
			sheet.setColumnWidth(2, 256*40);
			
			//创建行
			//头标题行,第一行第一列
			HSSFRow row1 = sheet.createRow(0);
			row1.setHeight((short)1000);
			HSSFCell cell_1_1 = row1.createCell(0);
			cell_1_1.setCellStyle(head_CellStyle);
			cell_1_1.setCellValue("深圳市缉查布控系统数据传输情况监测统计表");
			cellRangeAddressSetBorder(cellRangeAddress, sheet, workbook);
			
			//第2行
			HSSFRow row2 = sheet.createRow(1);
			row2.setHeight((short)400);
			HSSFCell cell_2_1 = row2.createCell(0);
			cell_2_1.setCellStyle(title_CellStyle);
			cell_2_1.setCellValue("序号");
			HSSFCell cell_2_2 = row2.createCell(1);
			cell_2_2.setCellStyle(title_CellStyle);
			cell_2_2.setCellValue("监测点编号");
			HSSFCell cell_2_3 = row2.createCell(2);
			cell_2_3.setCellStyle(title_CellStyle);
			cell_2_3.setCellValue("监测点名称");
			HSSFCell cell_2_4 = row2.createCell(3);
			cell_2_4.setCellStyle(title_CellStyle);
			cell_2_4.setCellValue("及时传输量（条）");
			HSSFCell cell_2_5 = row2.createCell(4);
			cell_2_5.setCellStyle(title_CellStyle);
			cell_2_5.setCellValue("超时传输量（条）");
			HSSFCell cell_2_6 = row2.createCell(5);
			cell_2_6.setCellStyle(title_CellStyle);
			cell_2_6.setCellValue("总量（条）");
			HSSFCell cell_2_7 = row2.createCell(6);
			cell_2_7.setCellStyle(title_CellStyle);
			cell_2_7.setCellValue("及时传输率");
			
			int rowHeight = 2;
			if(hpsbqkjcList != null && hpsbqkjcList.size() > 0){
				for(int i=0;i < hpsbqkjcList.size();i++){
					//获取信息
					TjEnitity tjEnitity = hpsbqkjcList.get(i);
					//创建行
					HSSFRow rowx = sheet.createRow(rowHeight++);
					//序号
					HSSFCell cell_x_1 = rowx.createCell(0);
					cell_x_1.setCellStyle(normal_CellStyle);
					cell_x_1.setCellValue(tjEnitity.getSn());
					//监测点ID
					HSSFCell cell_x_2 = rowx.createCell(1);
					cell_x_2.setCellStyle(normal_CellStyle);
					cell_x_2.setCellValue(tjEnitity.getJcdid());
					//监测点名称
					HSSFCell cell_x_3 = rowx.createCell(2);
					cell_x_3.setCellStyle(normal_CellStyle);
					cell_x_3.setCellValue(tjEnitity.getJcdmc());
					//及时传输量（条）
					HSSFCell cell_x_4 = rowx.createCell(3);
					cell_x_4.setCellStyle(normal_CellStyle);
					cell_x_4.setCellValue(tjEnitity.getJss());
					//超时传输量（条）
					HSSFCell cell_x_5 = rowx.createCell(4);
					cell_x_5.setCellStyle(normal_CellStyle);
					cell_x_5.setCellValue(tjEnitity.getCss());
					//总量
					HSSFCell cell_x_6 = rowx.createCell(5);
					cell_x_6.setCellStyle(normal_CellStyle);
					cell_x_6.setCellValue(tjEnitity.getTotalRst());
					//及时传输率
					HSSFCell cell_x_7 = rowx.createCell(6);
					cell_x_7.setCellStyle(normal_CellStyle);
					cell_x_7.setCellValue(tjEnitity.getJsl());
				}
			}
			
			//发送前端
			workbook.write(outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				workbook.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 业务处置情况监测excel 导出
	 */
	public static void exportYwczqkjcExcel(List<YwtjEnitity> ywczqkjcList, ServletOutputStream outputStream) {
		//创建工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		try {
			//合并单元格样式
			CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 7);
			//头标题样式
			HSSFCellStyle head_CellStyle = createHSSFCellStyle(workbook, (short)16);
			//设置普通单元格样式
			HSSFCellStyle normal_CellStyle = createHSSFCellStypeNoBold(workbook,(short)12);
			//创建工作表
			HSSFSheet sheet = workbook.createSheet("深圳市缉查布控系统业务处置情况监测统计表");
			//title 单元格样式
			HSSFCellStyle title_CellStyle = createTitleCellStyle(workbook, (short)12);
			//加载..合并单元格对象
			sheet.addMergedRegion(cellRangeAddress);//标题
			//设置单元格默认宽度
			sheet.setDefaultColumnWidth(20);
			sheet.setColumnWidth(0, 256*8);
			sheet.setColumnWidth(2, 256*30);
			
			//创建行
			//头标题行,第一行第一列
			HSSFRow row1 = sheet.createRow(0);
			row1.setHeight((short)1000);
			HSSFCell cell_1_1 = row1.createCell(0);
			cell_1_1.setCellStyle(head_CellStyle);
			cell_1_1.setCellValue("深圳市缉查布控系统业务处置情况监测统计表");
			cellRangeAddressSetBorder(cellRangeAddress, sheet, workbook);
			
			//第2行
			HSSFRow row2 = sheet.createRow(1);
			row2.setHeight((short)400);
			HSSFCell cell_2_1 = row2.createCell(0);
			cell_2_1.setCellStyle(title_CellStyle);
			cell_2_1.setCellValue("序号");
//			HSSFCell cell_2_2 = row2.createCell(1);
//			cell_2_2.setCellStyle(title_CellStyle);
//			cell_2_2.setCellValue("部门编号");
			HSSFCell cell_2_3 = row2.createCell(1);
			cell_2_3.setCellStyle(title_CellStyle);
			cell_2_3.setCellValue("部门名称");
//			HSSFCell cell_2_4 = row2.createCell(3);
//			cell_2_4.setCellStyle(title_CellStyle);
//			cell_2_4.setCellValue("布控审批及时率");
//			HSSFCell cell_2_5 = row2.createCell(4);
//			cell_2_5.setCellStyle(title_CellStyle);
//			cell_2_5.setCellValue("撤控审批及时率");
			HSSFCell cell_2_6 = row2.createCell(2);
			cell_2_6.setCellStyle(title_CellStyle);
			cell_2_6.setCellValue("布控签收及时率");
			HSSFCell cell_2_7 = row2.createCell(3);
			cell_2_7.setCellStyle(title_CellStyle);
			cell_2_7.setCellValue("撤控签收及时率");
			HSSFCell cell_2_8 = row2.createCell(4);
			cell_2_8.setCellStyle(title_CellStyle);
			cell_2_8.setCellValue("预警签收及时率");
			HSSFCell cell_2_9 = row2.createCell(5);
			cell_2_9.setCellStyle(title_CellStyle);
			cell_2_9.setCellValue("指令下达及时率");
			HSSFCell cell_2_10 = row2.createCell(6);
			cell_2_10.setCellStyle(title_CellStyle);
			cell_2_10.setCellValue("指令签收及时率");
			HSSFCell cell_2_11 = row2.createCell(7);
			cell_2_11.setCellStyle(title_CellStyle);
			cell_2_11.setCellValue("指令反馈及时率");
			
			int rowHeight = 2;
			if(ywczqkjcList != null && ywczqkjcList.size() > 0){
				for(int i=0;i < ywczqkjcList.size();i++){
					//获取信息
					YwtjEnitity ywtjEnitity = ywczqkjcList.get(i);
					//创建行
					HSSFRow rowx = sheet.createRow(rowHeight++);
					//序号
					HSSFCell cell_x_1 = rowx.createCell(0);
					cell_x_1.setCellStyle(normal_CellStyle);
					cell_x_1.setCellValue(ywtjEnitity.getSn());
//					//部门编号
//					HSSFCell cell_x_2 = rowx.createCell(1);
//					cell_x_2.setCellStyle(normal_CellStyle);
//					cell_x_2.setCellValue(ywtjEnitity.getBmbh());
					//部门名称
					HSSFCell cell_x_3 = rowx.createCell(1);
					cell_x_3.setCellStyle(normal_CellStyle);
					cell_x_3.setCellValue(ywtjEnitity.getBmmc());
//					//布控审批及时率
//					HSSFCell cell_x_4 = rowx.createCell(3);
//					cell_x_4.setCellStyle(normal_CellStyle);
//					cell_x_4.setCellValue(ywtjEnitity.getBkspjsl());
//					//撤控审批及时率
//					HSSFCell cell_x_5 = rowx.createCell(4);
//					cell_x_5.setCellStyle(normal_CellStyle);
//					cell_x_5.setCellValue(ywtjEnitity.getCkspjsl());
					//布控签收及时率
					HSSFCell cell_x_6 = rowx.createCell(2);
					cell_x_6.setCellStyle(normal_CellStyle);
					String bkqsjsl = ywtjEnitity.getBkqsjsl() + "\n(总数:" + ywtjEnitity.getBkqszs() + ",及时:" + ywtjEnitity.getBkqsjss() + ",超时:" + ywtjEnitity.getBkqscss() + ",未签收:" + ywtjEnitity.getBkwqss() + ")";
					if("440300010100".equals(ywtjEnitity.getBmbh())){
						bkqsjsl = "-";
					}
					cell_x_6.setCellValue(bkqsjsl);
					//撤控签收及时率
					HSSFCell cell_x_7 = rowx.createCell(3);
					cell_x_7.setCellStyle(normal_CellStyle);
					String ckqsjsl = ywtjEnitity.getCkqsjsl() + "\n(总数:" + ywtjEnitity.getCkqszs() + ",及时:" + ywtjEnitity.getCkqsjss() + ",超时:" + ywtjEnitity.getCkqscss() + ",未签收:" + ywtjEnitity.getCkwqss() + ")";
					if("440300010100".equals(ywtjEnitity.getBmbh())){
						ckqsjsl = "-";
					}
					cell_x_7.setCellValue(ckqsjsl);
					//预警签收及时率
					HSSFCell cell_x_8 = rowx.createCell(4);
					cell_x_8.setCellStyle(normal_CellStyle);
					cell_x_8.setCellValue(ywtjEnitity.getYjqsjsl() + "\n(总数:" + ywtjEnitity.getYjqszs() + ",及时:" + ywtjEnitity.getYjqsjss() + ",超时:" + ywtjEnitity.getYjqscss() + ",未签收:" + ywtjEnitity.getYjwqss() + ")");
					//指令下达及时率
					HSSFCell cell_x_9 = rowx.createCell(5);
					cell_x_9.setCellStyle(normal_CellStyle);
					String zlxdjsl = ywtjEnitity.getZlxdjsl() + "\n(总数:" + ywtjEnitity.getZlxdzs() + ",及时:" + ywtjEnitity.getZlxdjss() + ",超时:" + ywtjEnitity.getZlxdcss() + ")";
					if(!"440300010100".equals(ywtjEnitity.getBmbh())){
						zlxdjsl = "-";
					}
					cell_x_9.setCellValue(zlxdjsl);
					//指令签收及时率
					HSSFCell cell_x_10 = rowx.createCell(6);
					cell_x_10.setCellStyle(normal_CellStyle);
					String zlqsjsl = ywtjEnitity.getZlqsjsl() + "\n(总数:" + ywtjEnitity.getZlqszs() + ",及时:" + ywtjEnitity.getZlqsjss() + ",超时:" + ywtjEnitity.getZlqscss() + ",未签收:" + ywtjEnitity.getZlwqss() + ")";
					if("440300010100".equals(ywtjEnitity.getBmbh())){
						zlqsjsl = "-";
					}
					cell_x_10.setCellValue(zlqsjsl);
					//指令反馈及时率
					HSSFCell cell_x_11 = rowx.createCell(7);
					cell_x_11.setCellStyle(normal_CellStyle);
					String qlfkjsl = ywtjEnitity.getZlfkjsl() + "\n(总数:" + ywtjEnitity.getZlfkzs() + ",及时:" + ywtjEnitity.getZlfkjss() + ",超时:" + ywtjEnitity.getZlfkcss() + ",未签收:" + ywtjEnitity.getZlwfks() + ")";
					if("440300010100".equals(ywtjEnitity.getBmbh())){
						qlfkjsl = "-";
					}
					cell_x_11.setCellValue(qlfkjsl);
				}
			}
			
			//发送前端
			workbook.write(outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				workbook.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	 /**
     * 给合并单元格添加边框
     * @param targetSheet 目标表格
     * @param region      合并单元格
     * @param style       单元格样式
     */
	public static void addMergedRegionStyle(HSSFSheet targetSheet, org.apache.poi.ss.util.Region region, HSSFCellStyle style) {
		int rowFrom = region.getRowFrom();
		int rowTo = region.getRowTo();
		int colFrom = region.getColumnFrom();
		int colTo = region.getColumnTo();
		for (int r = rowFrom; r <= rowTo; r++) {
			HSSFRow row = targetSheet.getRow(r);
			if (row != null) {
				for (int c = colFrom; c <= colTo; c++) {
					HSSFCell cell = row.getCell(c);
					if (cell == null) {
						cell = row.createCell(c);
					}
					cell.getCellStyle().setBorderLeft(style.getBorderLeft());
					cell.getCellStyle().setBorderRight(style.getBorderRight());
					cell.getCellStyle().setBorderTop(style.getBorderTop());
					cell.getCellStyle().setBorderBottom(style.getBorderBottom());
				}
			}
		}
	}
}