package com.employee.emplogin.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Repository;

import com.employee.emplogin.model.Employee;

@Repository("emprepo")
public class EmployeeRepo {
	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private int rownum;
	private int cellnum;
	File file;
	String filePath = "./src/main/resources/excell/employee.xlsx";

	@SuppressWarnings("resource")
	public String addInfoIntoExcel(Employee emp) {
		String response = "";
		try {
			Workbook workbook = new XSSFWorkbook(Files.newInputStream(Paths.get(filePath)));
			Sheet sheet = workbook.getSheetAt(0);
			rownum = sheet.getLastRowNum();
			cellnum = 0;
			Row row = sheet.createRow(++rownum);
			Cell cell = row.createCell(cellnum++);
			cell.setCellValue((emp.getEmpId()));
			Cell cell2 = row.createCell(cellnum++);
			cell2.setCellValue(emp.getPassword());

			FileOutputStream out = new FileOutputStream(new File(filePath));
			workbook.write(out);
			out.close();
			response = "Congrats!!!" + emp.getEmpId() + "You have registered successfully";
		} catch (FileNotFoundException fne) {
			LOGGER.log(Level.INFO, fne.getMessage());
			response = "Internal server error";
		} catch (IOException io) {
			LOGGER.log(Level.INFO, io.getMessage());
		}
		return response;
	}

	@SuppressWarnings("unused")
	public List<Employee> getAllInfo(Employee emp1) {
		String sourceFilePath = "./src/main/resources/excell/employee.xlsx";
		Workbook workbook = null;
		FileInputStream fileInputStream = null;
		List excelData = new ArrayList();
		try {
			fileInputStream = new FileInputStream(sourceFilePath);
			workbook = new XSSFWorkbook(Files.newInputStream(Paths.get(filePath)));
			Sheet sheet = workbook.getSheetAt(0);
			Iterator rows = sheet.rowIterator();
			while (rows.hasNext()) {
				Row row = (Row) rows.next();
				Iterator cells = row.cellIterator();
				List cellData = new ArrayList();
				while (cells.hasNext()) {
					Cell cell = (Cell) cells.next();
					cellData.add(cell.getStringCellValue());
				}
				if (!cellData.isEmpty()) {
					excelData.add(cellData);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return excelData;
	}
	
}
