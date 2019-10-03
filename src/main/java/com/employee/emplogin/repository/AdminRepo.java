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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.employee.emplogin.model.Admin;
import com.employee.emplogin.model.Employee;

@Repository("adminRepo")
public class AdminRepo {

	@Autowired
	private EmployeeRepo employeeRepo;

	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private int rownum;
	private int cellnum;
	File file;
	String filePath = "./src/main/resources/excell/AdminUser.xlsx";

	public String addInfoIntoExcel(Admin ad) {
		String response = "";
		try {
			Workbook workbook = new XSSFWorkbook(Files.newInputStream(Paths.get(filePath)));
			Sheet sheet = workbook.getSheetAt(0);
			rownum = sheet.getLastRowNum();
			cellnum = 0;
			Row row = sheet.createRow(++rownum);
			Cell cell = row.createCell(cellnum++);
			cell.setCellValue((ad.getAdminId()));
			Cell cell2 = row.createCell(cellnum++);
			cell2.setCellValue(ad.getAdminPwd());

			FileOutputStream out = new FileOutputStream(new File(filePath));
			workbook.write(out);
			out.close();
			response = "Congrats!!! Admin " + ad.getAdminId() + " You have registered successfully";
		} catch (FileNotFoundException fne) {
			LOGGER.log(Level.INFO, fne.getMessage());
			response = "Internal server error";
		} catch (IOException io) {
			LOGGER.log(Level.INFO, io.getMessage());
		}
		return response;
	}

	@SuppressWarnings("unused")
	public String identifyAdminUser(String admId) {
		String res = null;
		Admin adm = new Admin();
		adm.setAdminId(admId);
		Workbook workbook = null;
		FileInputStream fileInputStream = null;
		DataFormatter df = new DataFormatter();

		try {
			fileInputStream = new FileInputStream(filePath);
			workbook = new XSSFWorkbook(Files.newInputStream(Paths.get(filePath)));
			Sheet sheet = workbook.getSheetAt(0);
			Iterator rows = sheet.rowIterator();
			while (rows.hasNext()) {
				Row row = (Row) rows.next();
				Iterator cells = row.cellIterator();

				while (cells.hasNext()) {
					Cell cell = (Cell) cells.next();
					cell.setCellType(Cell.CELL_TYPE_STRING);
					String asItLooksInExcel = df.formatCellValue(cell);
					String val = (String) cell.getStringCellValue();
					if (val.equals(admId)) {
						res = admId;
						break;
					}
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
		return res;

	}

	@SuppressWarnings("deprecation")
	public String delByAdmin(String empId) {
		String filePath = "./src/main/resources/excell/employee.xlsx";
		String removedEmpId = null;
		DataFormatter df = new DataFormatter();

		int removedRowIndex = 0;
		try {
			Workbook workbook = new XSSFWorkbook(Files.newInputStream(Paths.get(filePath)));
			Sheet dataSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = dataSheet.iterator();

			while (iterator.hasNext()) {

				Row currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();

				while (cellIterator.hasNext()) {

					Cell currentCell = cellIterator.next();

					int columnIndex = currentCell.getColumnIndex();
					int rowIndex = currentCell.getRowIndex();
					if (rowIndex >= 0) {
						currentCell.setCellType(Cell.CELL_TYPE_STRING);
						String asItLooksInExcel = df.formatCellValue(currentCell);
						String val = (String) currentCell.getStringCellValue();

						if (columnIndex == 0 && val.equals(empId)) {
							removedRowIndex = rowIndex;
							removedEmpId = empId;
						}
					}
				}
			}
			removeRow(dataSheet, removedRowIndex);
			File file = new File(filePath);
			workbook.write(Files.newOutputStream((Paths.get(filePath))));
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return removedEmpId;
	}

	public String removeRow(Sheet sheet, int rowIndex) {

		int lastRowNum = sheet.getLastRowNum();
		if (rowIndex >= 0 && rowIndex < lastRowNum) {
			sheet.shiftRows(rowIndex + 1, lastRowNum, -1);
		}
		if (rowIndex == lastRowNum) {
			Row removingRow = sheet.getRow(rowIndex);
			if (removingRow != null) {
				sheet.removeRow(removingRow);
			}
		}
		return "Deleted successfully";
	}

	public List<Employee> readbyAdmin() {

		String sourceFilePath = "./src/main/resources/excell/employee.xlsx";
		Workbook workbook = null;
		FileInputStream fileInputStream = null;
		List excelData = new ArrayList();
		try {
			fileInputStream = new FileInputStream(sourceFilePath);
			workbook = new XSSFWorkbook(Files.newInputStream(Paths.get(sourceFilePath)));
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
