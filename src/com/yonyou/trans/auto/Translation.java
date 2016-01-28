package com.yonyou.trans.auto;

import java.io.File;
import java.io.IOException;

import com.yonyou.trans.auto.model.Model;
import com.yonyou.trans.auto.model.Project;
import com.yonyou.trans.auto.model.Trans_fileVO;
import com.yonyou.trans.db.MD5Utl;
import com.yonyou.trans.tools.Tools;

public class Translation {

	/**
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */

	public void doTrans(Model model) throws Exception {

		java.util.List<String> list = Tools.find(model.getLocalTranPath());
		String[] numfiles = new String[list.size()];
		numfiles = list.toArray(numfiles);

		for (String filePath : numfiles) {
			Trans_fileVO fileVO = getFileVO(filePath);
			fileVO.setModel(model.getName());
			if (fileVO != null) {
				// 上传EXCEL表
				AbstractEngine excel2dbEngine = new UploadExcel2DBEngine(fileVO);
				excel2dbEngine.start();

				// 翻译
				AbstractEngine engine = new TranslateSimpch2EnglishEngine(fileVO);
				engine.start();
			}
		}

	}

	/**
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	private Trans_fileVO getFileVO(String filePath) throws IOException {
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
	 * 
	 * @param project
	 * @throws Exception
	 */
	public void readNewTrans(Project project) throws Exception {

		java.util.List<String> list = Tools.find(project.getToDatabaseDir());
		String[] numfiles = new String[list.size()];
		numfiles = list.toArray(numfiles);

		for (String filePath : numfiles) {
			Trans_fileVO fileVO = getFileVO(filePath);
			if (fileVO != null) {
				// 上传EXCEL表
				AbstractEngine excel2dbEngine = new UploadNewTrans2DBEngine(fileVO);
				excel2dbEngine.start();
			}
		}
	}
}
