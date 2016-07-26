package com.yonyou.trans.auto;

import java.io.File;
import java.io.IOException;

import com.yonyou.trans.auto.model.Trans_fileVO;
import com.yonyou.trans.db.MD5Utl;
import com.yonyou.trans.tools.Tools;

public class UploadSepcialMain {

	private static Trans_fileVO getFileVO(String filePath) throws IOException {
		File file = new File(filePath);
		if (!file.exists()) {
			System.err.println("文件不存在");
			return null;
		}
		Trans_fileVO fileVO = new Trans_fileVO();

		String filemd5 = MD5Utl.fileMD5(filePath);
		fileVO.setFilemd5(filemd5);
		fileVO.setFilename(file.getName());

		fileVO.setPath(filePath);
		fileVO.setFile(file);
		return fileVO;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			readSpecial();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void readSpecial() throws Exception {

		java.util.List<String> list = Tools.find("D:/uaptrans/special");
		String[] numfiles = new String[list.size()];
		numfiles = list.toArray(numfiles);

		for (String filePath : numfiles) {
			Trans_fileVO fileVO = getFileVO(filePath);
			if (fileVO != null) {
				// 上传EXCEL表
				AbstractEngine excel2dbEngine = new UploadSpecial2DBEngine(fileVO);
				excel2dbEngine.start();
			}
		}
	}
}
