package com.yonyou.trans.auto;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.yonyou.trans.auto.model.Trans_fileVO;
import com.yonyou.trans.auto.model.Trans_sheetsVO;

public class TranslateSimpch2EnglishEngine extends AbstractEngine {

	public TranslateSimpch2EnglishEngine(Trans_fileVO fileVO) {
		super(fileVO);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 得到fileVO
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */

	@Override
	public boolean start() throws IOException, SQLException {
		// TODO Auto-generated method stub
		writeWorkbook();

		return false;
	}

	public void write2File(String filePath, HSSFWorkbook hssfWorkbook) {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(filePath);
			hssfWorkbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private Integer writeVO2Sheet(HashMap<Integer, String> trans, HSSFSheet hssfSheet) {
		Integer rowCount = 0;
		if (hssfSheet == null) {
			return rowCount;
		}
		// 循环行Row
		for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
			HSSFRow hssfRow = hssfSheet.getRow(rowNum);
			if (hssfRow != null) {
				int englishrownumber = 4;
				// HSSFCell filePah = hssfRow.getCell(0);
				// HSSFCell fileName = hssfRow.getCell(1);
				// HSSFCell resId = hssfRow.getCell(2);
				// HSSFCell simpchn = hssfRow.getCell(3);
				HSSFCell english = hssfRow.getCell(englishrownumber);
				if (english == null) {
					english = hssfRow.createCell(englishrownumber);
				}
				// HSSFCell tradchn = hssfRow.getCell(5);
				String word = trans.get(rowNum);
				if (word != null) {
					english.setCellValue(trans.get(rowNum));
				}
				rowCount++;
			}
		}
		return rowCount;
	}

	public List<Trans_sheetsVO> writeWorkbook() throws IOException, SQLException {

		List<Trans_sheetsVO> bookSheetVOs = new ArrayList<>();
		InputStream is = new FileInputStream(getFileVO().getFile());
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);

		String filemd5 = getFileVO().getFilemd5();

		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			Trans_sheetsVO workBookVO = new Trans_sheetsVO();
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			String sheetName = hssfSheet.getSheetName();
			workBookVO.setFilemd5(filemd5);
			workBookVO.setSheetname(sheetName);

			writeVO2Sheet(getSqlHelper().getTranslateValue(workBookVO), hssfSheet);
			bookSheetVOs.add(workBookVO);
		}

		write2File(getFileVO().getPath(), hssfWorkbook);
		hssfWorkbook.close();
		return bookSheetVOs;
	}
}
