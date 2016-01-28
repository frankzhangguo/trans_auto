package com.yonyou.trans.auto;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.yonyou.trans.auto.model.SheetRowVO;
import com.yonyou.trans.auto.model.Trans_fileVO;
import com.yonyou.trans.auto.model.Trans_sheetsVO;

public class UploadExcel2DBEngine extends AbstractEngine {

	public UploadExcel2DBEngine(Trans_fileVO fileVO) {
		super(fileVO);
		// TODO Auto-generated constructor stub
	}

	public boolean exist() throws SQLException {
		// 判断是否已经存在这个文件， 如果不存在， 删除类似的文件(同一个路径下的， 同文件名）
		String sql = "select top 1 filemd5 from trans_file where filemd5 = ?";
		ResultSet rs = getSqlHelper().queryResultSet(sql, getFileVO().getFilemd5());
		if (rs.next()) {// 存在sheet
			return true;
		} else {// 删除类似的文件(同一个路径下的， 同文件名）
			sql = "delete from trans_sheets where filemd5 in(select filemd5 from trans_file where path = ? and filename = ? and model = ?)";
			getSqlHelper().update(sql, getFileVO().getPath(), getFileVO().getFilename(), getFileVO().getModel());
			sql = "delete from trans_file where path = ? and filename = ? and model = ?";
			getSqlHelper().update(sql, getFileVO().getPath(), getFileVO().getFilename(), getFileVO().getModel());
			return false;
		}
	}

	public List<Trans_sheetsVO> readWorkbook(Trans_fileVO fileVO) throws IOException {
		List<Trans_sheetsVO> list = new ArrayList<Trans_sheetsVO>();

		TransExcelReadProcessor excelReadProcessor = new TransExcelReadProcessor(fileVO.getFile(), false);
		HashMap<String, List<SheetRowVO>> hashMap = excelReadProcessor.getWorkBookMap();
		Iterator<String> iterator = hashMap.keySet().iterator();
		while (iterator.hasNext()) {
			String sheetName = iterator.next();
			Trans_sheetsVO workBookVO = new Trans_sheetsVO();
			workBookVO.setSheetname(sheetName);
			workBookVO.setFilemd5(fileVO.getFilemd5());

			List<SheetRowVO> rowVOs = hashMap.get(sheetName);
			workBookVO.setRowVOs(rowVOs);
			list.add(workBookVO);
		}

		return list;
	}

	private void saveSheetsVOs(List<Trans_sheetsVO> sheetsVOs) throws Exception {
		getSqlHelper().updateTrans_sheetsVO(getFileVO(), sheetsVOs);

	}

	@Override
	public boolean start() throws Exception {

		if (!exist()) {
			List<Trans_sheetsVO> sheetsVOs = readWorkbook(getFileVO());
			saveSheetsVOs(sheetsVOs);

		}
		getSqlHelper().close();

		return false;
	}

}
