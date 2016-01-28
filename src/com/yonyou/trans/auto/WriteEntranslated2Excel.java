package com.yonyou.trans.auto;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;

import com.yonyou.trans.auto.model.Project;
import com.yonyou.trans.auto.model.Trans_simpchnVO;
import com.yonyou.trans.db.DBHelper;
import com.yonyou.trans.tools.MailUtil;

public class WriteEntranslated2Excel {

	Project project = null;
	private DBHelper sqlHelper = null;

	public WriteEntranslated2Excel(Project project) {
		super();
		this.project = project;
	}

	/**
	 * @return sqlHelper
	 */
	public DBHelper getSqlHelper() {
		if (sqlHelper == null) {
			sqlHelper = new DBHelper();
		}
		return sqlHelper;
	}

	public void write2Excel() throws IOException, SQLException {
		Trans_simpchnVO[] sheetVOs = getSqlHelper().getSimpchnVOs();
		HSSFWorkbook wb = new HSSFWorkbook();
		// Workbook wb = new XSSFWorkbook();
		CreationHelper createHelper = wb.getCreationHelper();
		HSSFSheet sheet = wb.createSheet("new sheet");
		// Create a row and put some cells in it. Rows are 0 based.
		Row row = sheet.createRow(0);
		// Create a cell and put a value in it.
		row.createCell(0).setCellValue(createHelper.createRichTextString("待翻译的中文"));
		// Or do it on one line.
		row.createCell(1).setCellValue(createHelper.createRichTextString("英文"));
		if (sheetVOs != null && sheetVOs.length > 0) {
			for (int i = 0; i < sheetVOs.length; i++) {
				Trans_simpchnVO simpchnVO = sheetVOs[i];
				row = sheet.createRow(i + 1);
				// Create a cell and put a value in it.
				row.createCell(0).setCellValue(createHelper.createRichTextString(simpchnVO.getSimpchn()));
				// Or do it on one line.
				row.createCell(1).setCellValue(createHelper.createRichTextString(""));
			}
		}
		// Write the output to a file
		String filename = project.getFromExcelSimp() + "/simpchn.xls";
		FileOutputStream fileOut = new FileOutputStream(filename);
		wb.write(fileOut);
		fileOut.close();
		// send mail
		MailUtil mailUtil = new MailUtil();
		mailUtil.send(sheetVOs);

	}

}
