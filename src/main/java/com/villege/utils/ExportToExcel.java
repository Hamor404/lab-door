package com.villege.utils;

import com.villege.entity.sys.PersonInfoVo;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.BorderStyle;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExportToExcel {
	public static String getFilename(){
		Date d = new Date();
		int year = d.getYear()+1900;
		int month = d.getMonth()+1;
		int day = d.getDate();
		int hour = d.getHours();
		int minute = d.getMinutes();
		int second = d.getSeconds();
		String sdate = year+"";
		if(month<10)
			sdate += "0"+month;
		else
			sdate += month;
		if(day<10){
			sdate += "0"+day;
		}else{
			sdate += day;
		}
		if(hour<10){
			sdate += "0"+hour;
		}else{
			sdate += hour;
		}
		if(minute<10){
			sdate += "0"+minute;
		}else{
			sdate += minute;
		}
		if(second<10){
			sdate += "0"+second;
		}else{
			sdate += second;
		}
		return sdate;
	}
	public String copyFile(long testId,String path,int code){
		String srcfile = "",descfile="";
		switch(code){
			case 1://导出居民信息
				descfile= getFilename()+".xls";
				srcfile = path +"personInfo.xls";
				break;
		}
		try {
			FileInputStream fis = new FileInputStream(srcfile);
			FileOutputStream fos = new FileOutputStream(path+descfile);
			byte [] buffer = new byte[1024*4];
			while(fis.read(buffer) != -1){
				fos.write(buffer);
			}
			fis.close();
			fos.close();
		} catch (Exception e) {

			e.printStackTrace();
		}
		return descfile;
		
	}

	//导出人员信息
	public String ExpPersonInfo(List<PersonInfoVo> info, String path, int code){
		POIFSFileSystem fs = null;
		String filename="";
		int headRow = 2;
		try {
			filename=copyFile(0,path,code);

			fs = new POIFSFileSystem(new FileInputStream(path+filename));
			FileOutputStream fos = new FileOutputStream(path+filename);
			HSSFWorkbook wb1 = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb1.getSheetAt(0);
			int size = info.size();
			int col=0;
			HSSFCellStyle style = wb1.createCellStyle();
			style.setBorderLeft(BorderStyle.THIN);
			style.setBorderRight(BorderStyle.THIN);
			style.setBorderTop(BorderStyle.THIN);
			style.setBorderBottom(BorderStyle.THIN);
			for(int i=0;i<size;i++){
				col=0;
				PersonInfoVo p = info.get(i);
				HSSFRow row = sheet.createRow(i+headRow);
				HSSFCell cell = null;
				cell = row.createCell(col++);
				cell.setCellStyle(style);
				cell.setCellValue(p.getPersonId());

				cell = row.createCell(col++);
				cell.setCellStyle(style);
				cell.setCellValue(p.getCommunityName());

				cell = row.createCell(col++);
				cell.setCellStyle(style);
				cell.setCellValue(p.getTermName());

				cell = row.createCell(col++);
				cell.setCellStyle(style);
				cell.setCellValue(p.getHouseNo());
				cell = row.createCell(col++);
				cell.setCellStyle(style);
				cell.setCellValue(p.getUserName());

				cell = row.createCell(col++);
				cell.setCellStyle(style);
				cell.setCellValue(p.getSex());

				cell = row.createCell(col++);
				cell.setCellStyle(style);
				cell.setCellValue(p.getMobile());

				cell = row.createCell(col++);
				cell.setCellStyle(style);
				cell.setCellValue(p.getPersonType());

				cell = row.createCell(col++);
				cell.setCellStyle(style);
				cell.setCellValue(p.getRemark());

			}
			wb1.write(fos);
			fos.close();
			wb1=null;
			fs=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filename;
	}

	public static String toDate(Date date){
		String str = "";
		if(date==null)
			str="";
		else
		{
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			str= df.format(date);
		}
		return str;
	}
}
