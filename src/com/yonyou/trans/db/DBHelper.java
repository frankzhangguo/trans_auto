package com.yonyou.trans.db;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.yonyou.trans.auto.model.Project;
import com.yonyou.trans.auto.model.SheetRowVO;
import com.yonyou.trans.auto.model.Trans_fileVO;
import com.yonyou.trans.auto.model.Trans_sheetsVO;
import com.yonyou.trans.auto.model.Trans_simpchnVO;
import com.yonyou.trans.auto.model.Trans_specialVO;
import com.yonyou.trans.auto.model.Trans_wordsVO;

public class DBHelper {
	// 定义需要的对象
	Connection ct = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	// 连接数据库需要的字符串
	String driver = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
	String url = Project.getProjectConfig().getDatabaseURL();
	String user = Project.getProjectConfig().getDatabaseUser();
	String passwd = Project.getProjectConfig().getDatabasePassword();

	// 构造函数，初始化ct
	public DBHelper() {
		try {
			// 加载驱动
			Class.forName(driver);
			// 得到连接
			ct = DriverManager.getConnection(url, user, passwd);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("驱动没有加载成功,原因是没有导入驱动！请检查");
		} catch (NullPointerException e) {
			// TODO Auto-generated catch bloc
			e.printStackTrace();
			System.out.println("数据库服务没有开启，请打开数据库服务，再重试");
		} catch (SQLException e) {
			// TODO Auto-generated catch bloc
			e.printStackTrace();
		}
	}

	public void clearDB() throws SQLException {
		String sql = "truncate table trans_sheets";
		ps = ct.prepareStatement(sql);
		ps.execute();
		sql = "truncate table trans_file";
		ps = ct.prepareStatement(sql);
		ps.execute();

		sql = "truncate table trans_simpchn";
		ps = ct.prepareStatement(sql);
		ps.execute();
	}

	// 关闭资源的方法
	public void close() {
		try {
			if (rs != null) {
				rs.close();
			}

			if (ps != null) {
				ps.close();
			}

			if (ct != null) {
				ct.close();
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param fileMd5
	 * @return
	 * @throws Exception
	 */
	private HashMap<String, Trans_wordsVO> getPreTrans(String fileMd5) throws Exception {
		String sql = "select distinct trans_simpchn.md5, trans_simpchn.simpchn, trans_words.cTranslation from trans_simpchn, trans_words where trans_simpchn.simpchn = trans_words.simpchn and trans_simpchn.filemd5 = ?";
		HashMap<String, Trans_wordsVO> hashMap = new HashMap<>();
		ResultSet resultSet = queryResultSet(sql, fileMd5);

		while (resultSet.next()) {
			String md5 = resultSet.getString(1);
			Trans_wordsVO trans_wordsVO = new Trans_wordsVO();
			trans_wordsVO.setSimpchn(resultSet.getString(2));
			trans_wordsVO.setCTranslation(resultSet.getString(3));
			trans_wordsVO.setMd5(md5);
			hashMap.put(md5, trans_wordsVO);
		}
		if (resultSet != null) {
			resultSet.close();
		}
		return hashMap;
	}

	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Trans_simpchnVO[] getSimpchnVOs() throws SQLException {
		String sql = "select  distinct trans_simpchn.simpchn, trans_file.model from trans_simpchn join trans_file on trans_simpchn.filemd5 =  trans_file.filemd5";
		ps = ct.prepareStatement(sql);

		List<Trans_simpchnVO> resultSet = new ArrayList<Trans_simpchnVO>();
		// 执行查询
		rs = ps.executeQuery();
		while (rs.next()) {
			Trans_simpchnVO simpchnVO = new Trans_simpchnVO();

			simpchnVO.setSimpchn(rs.getString(1));
			simpchnVO.setSheetname(rs.getString(2));// 借用sheetname
			resultSet.add(simpchnVO);
		}
		return resultSet.toArray(new Trans_simpchnVO[resultSet.size()]);
	}

	public HashMap<Integer, String> getTranslateValue(Trans_sheetsVO workBookVO) throws SQLException {
		String sql = "select rowid, english from trans_sheets where filemd5 = ? and sheetname = ?";
		HashMap<Integer, String> hashMap = new HashMap<>();
		ResultSet resultSet = queryResultSet(sql, workBookVO.getFilemd5(), workBookVO.getSheetname());

		while (resultSet.next()) {
			hashMap.put(resultSet.getInt(1), resultSet.getString(2));
		}
		if (resultSet != null) {
			resultSet.close();
		}
		return hashMap;
	}

	public void insert(String sql, Serializable... paras) throws SQLException {

		ps = ct.prepareStatement(sql);
		// 对sql的参数赋值
		setValues(ps, paras);
		ps.executeUpdate();
		if (ps != null) {
			ps.close();
		}

	}

	public void insertNewTrans(HashMap<String, List<Trans_simpchnVO>> hashMap) throws Exception {
		Iterator<String> iterator = hashMap.keySet().iterator();
		List<Trans_simpchnVO> bList = new ArrayList<Trans_simpchnVO>();
		while (iterator.hasNext()) {
			String sheetname = iterator.next();
			bList.addAll(hashMap.get(sheetname));
		}
		HashMap<String, Trans_simpchnVO> hashMap2 = new HashMap<>();

		for (Trans_simpchnVO trans_simpchnVO : bList) {
			hashMap2.put(trans_simpchnVO.getSimpchn(), trans_simpchnVO);
		}

		Trans_simpchnVO[] bigList = hashMap2.values().toArray(new Trans_simpchnVO[hashMap2.size()]);

		String sql = "delete from trans_file where filemd5 in (select filemd5 from trans_simpchn)";
		ps = ct.prepareStatement(sql);
		ps.execute();

		sql = "delete from trans_sheets where filemd5 not in (select filemd5 from trans_file)";
		ps = ct.prepareStatement(sql);
		ps.execute();

		sql = "delete from trans_simpchn";
		ps = ct.prepareStatement(sql);
		ps.execute();

		sql = "delete from trans_words where md5 = ?";
		ps = ct.prepareStatement(sql);
		for (Trans_simpchnVO trans_simpchnVO : bigList) {
			String md5 = MD5Utl.Bit16(trans_simpchnVO.getSimpchn());
			setValues(ps, md5);
			ps.addBatch();
		}
		ps.executeBatch();

		sql = "insert trans_words(md5, simpchn,cTranslation) values(?,?,?)";
		ps = ct.prepareStatement(sql);
		for (Trans_simpchnVO trans_simpchnVO : bigList) {
			String md5 = MD5Utl.Bit16(trans_simpchnVO.getSimpchn());
			setValues(ps, md5, trans_simpchnVO.getSimpchn(), trans_simpchnVO.getEnglish());
			ps.addBatch();
		}
		ps.executeBatch();

	}

	public void insertSpecialTrans(HashMap<String, List<Trans_specialVO>> hashMap) throws Exception {
		Iterator<String> iterator = hashMap.keySet().iterator();
		List<Trans_specialVO> bList = new ArrayList<Trans_specialVO>();
		while (iterator.hasNext()) {
			String sheetname = iterator.next();
			bList.addAll(hashMap.get(sheetname));
		}

		String sql = "insert trans_special(module , location , graphic , filename , resid , sheet , source , correct_translation , old_translation , reported_by ) values(?,?,?,?,?,?,?,?,?,?)";
		ps = ct.prepareStatement(sql);
		for (Trans_specialVO trans_simpchnVO : bList) {
			setValues(ps, trans_simpchnVO.getModule(), trans_simpchnVO.getLocation(), trans_simpchnVO.getGraphic(),
					trans_simpchnVO.getFile(), trans_simpchnVO.getResid(), trans_simpchnVO.getSheet(),
					trans_simpchnVO.getSource(), trans_simpchnVO.getCorrect_translation(),
					trans_simpchnVO.getOld_translation(), trans_simpchnVO.getReported_by());
			ps.addBatch();
		}
		ps.executeBatch();

	}

	public ResultSet queryResultSet(String sql, Serializable... paras) throws SQLException {

		ps = ct.prepareStatement(sql);
		// 对sql的参数赋值
		setValues(ps, paras);
		// 执行查询
		rs = ps.executeQuery();

		// 返回结果集
		return rs;
	}

	public void setValues(PreparedStatement ps, Serializable... paras) throws SQLException {
		for (int i = 0; i < paras.length; i++) {
			Serializable value = paras[i];
			if (value instanceof String) {
				ps.setString(i + 1, String.valueOf(value));
			} else if (value instanceof Boolean) {
				ps.setBoolean(i + 1, ((Boolean) value).booleanValue());
			} else if (value instanceof Integer) {
				ps.setInt(i + 1, ((Integer) value).intValue());
			} else if (value instanceof Date) {
				ps.setDate(i + 1, (Date) value);
			} else if (value instanceof Long) {
				ps.setLong(i + 1, (Long) value);
			} else {
				ps.setString(i + 1, String.valueOf(value));
			}
		}
	}

	public void update(String sql, Serializable... paras) throws SQLException {
		ps = ct.prepareStatement(sql);
		// 对sql的参数赋值
		setValues(ps, paras);
		// 执行查询
		ps.executeUpdate();

	}

	public void updateTrans_sheetsVO(Trans_fileVO fileVO, List<Trans_sheetsVO> sheetsVOs) throws Exception {

		String sql = "insert into trans_file (filemd5, filename, path, model) values (?,?,?,?)";
		ps = ct.prepareStatement(sql);
		setValues(ps, fileVO.getFilemd5(), fileVO.getFilename(), fileVO.getPath(), fileVO.getModel().getName());
		ps.execute();

		sql = "insert into trans_sheets (filemd5, sheetname, rowid, simpchn, english, md5, resid) values (?,?,?,?,?,?,?)";
		ps = ct.prepareStatement(sql);

		for (Trans_sheetsVO trans_sheetsVO : sheetsVOs) {
			List<SheetRowVO> rowVOs = trans_sheetsVO.getRowVOs();
			if (rowVOs != null && rowVOs.size() > 0) {
				for (SheetRowVO sheetRowVO : rowVOs) {
					String md5 = MD5Utl.Bit16(sheetRowVO.getSimpchn());
					setValues(ps, trans_sheetsVO.getFilemd5(), trans_sheetsVO.getSheetname(), sheetRowVO.getRowNum(),
							sheetRowVO.getSimpchn(), sheetRowVO.getEnglish(), md5, sheetRowVO.getResId());
					ps.addBatch();
				}
			}
		}
		ps.executeBatch();

		//
		sql = "Delete trans_sheets where simpchn is null";
		ps = ct.prepareStatement(sql);
		ps.execute();

		sql = "delete trans_sheets where len(simpchn)-datalength(simpchn)+datalength(cast(simpchn as varchar(2000))) = 0 and filemd5 = '"
				+ fileVO.getFilemd5() + "'";
		ps = ct.prepareStatement(sql);
		ps.execute();

		// 删除， 重新加
		sql = "delete from trans_simpchn where  filemd5 = '" + fileVO.getFilemd5() + "'";
		ps = ct.prepareStatement(sql);
		ps.execute();

		sql = "Insert into trans_simpchn(filemd5, sheetname, simpchn, rowid, md5) select filemd5, sheetname, simpchn, rowid, md5 from trans_sheets where md5 not in (select md5 from trans_words) and filemd5 = '"
				+ fileVO.getFilemd5() + "'";
		ps = ct.prepareStatement(sql);
		ps.execute();

		// 预翻译
		sql = "update trans_simpchn set  SimpChn=replace(SimpChn,N'　',N' ') collate Chinese_PRC_CI_AS_WS where filemd5 = '"
				+ fileVO.getFilemd5() + "'";
		ps = ct.prepareStatement(sql);
		ps.execute();

		sql = "update trans_simpchn set SimpChn=ltrim(rtrim(SimpChn)) where filemd5 = '" + fileVO.getFilemd5() + "'";
		ps = ct.prepareStatement(sql);
		ps.execute();

		sql = "select distinct trans_simpchn.md5, trans_simpchn.simpchn, trans_words.cTranslation from trans_simpchn, trans_words where trans_simpchn.simpchn = trans_words.simpchn and trans_simpchn.filemd5 = '"
				+ fileVO.getFilemd5() + "'";
		ps = ct.prepareStatement(sql);
		ps.execute();

		HashMap<String, Trans_wordsVO> hashMap = getPreTrans(fileVO.getFilemd5());
		Iterator<String> iterator = hashMap.keySet().iterator();
		sql = "insert into trans_words(md5,simpchn,cTranslation) values(?,?,?)";
		ps = ct.prepareStatement(sql);
		while (iterator.hasNext()) {
			String md5 = iterator.next();
			Trans_wordsVO trans_wordsVO = hashMap.get(md5);
			setValues(ps, md5, trans_wordsVO.getSimpchn(), trans_wordsVO.getCTranslation());
			ps.addBatch();
		}
		ps.executeBatch();

		sql = "delete from trans_simpchn where md5 in (select trans_words.md5 from trans_words) and filemd5 = '"
				+ fileVO.getFilemd5() + "'";
		ps = ct.prepareStatement(sql);
		ps.execute();

		// 翻译
		sql = "delete from trans_sheets where md5 not in (select md5 from trans_words) and filemd5 = '"
				+ fileVO.getFilemd5() + "'";
		ps = ct.prepareStatement(sql);
		ps.execute();

		sql = "update trans_sheets set english = (select top 1 cTranslation from trans_words where trans_sheets.md5 = trans_words.md5) where filemd5 = '"
				+ fileVO.getFilemd5() + "'";
		ps = ct.prepareStatement(sql);
		ps.execute();

		// 特殊翻译
		sql = "	update trans_sheets	";
		sql = sql + "	   set english =	";
		sql = sql + "	       (select top 1 correct_translation	";
		sql = sql + "	          from trans_special	";
		sql = sql + "	         where trans_sheets.resid = trans_special.resid	";
		sql = sql + "	           and trans_sheets.simpchn = trans_special.source)	";
		sql = sql + "	 where trans_sheets.resid in	";
		sql = sql + "	       (select trans_special.resid	";
		sql = sql + "	          from trans_special	";
		sql = sql + "	         where trans_sheets.resid = trans_special.resid		";
		sql = sql + "	           and trans_sheets.simpchn = trans_special.source)	";
		sql = sql + "	   and trans_sheets.filemd5 = '" + fileVO.getFilemd5() + "'";
		ps = ct.prepareStatement(sql);
		ps.execute();

		if (ps != null) {
			ps.close();
		}

	}
}
