# trans_auto
用友公司国际化翻译工具
3、	工具使用说明

3.1 工具设置

从以下位置打开 setting.xml：E:\uaptran\自动化翻译软件\bin

打开后的界面如下所示：
 
图 3.1 setting.xml 设置


3.11 设置分析后导出的新词条excel文件位置

<fromExcelSimp>E:/uaptran/fromexcel</fromExcelSimp>

3.12 设置翻译后文件的读取地址

<toDatabaseDir>E:/uaptran/totrans</toDatabaseDir>

3.13 设置消息接收人邮箱
   在<mailReceivers>  </mailReceivers> 中设置消息接收人邮箱。
每个邮箱之间以分号分隔。

例如：
chexz@yonyou.com;zhangg@yonyou.com;

3.14 设置模块名称

在<Model name="   "> 中设置模块名称。

例如：develop、ria_rianc、develop_nc、develop_uap636、mdm32

3.15 设置要从中读取需处理词条的服务器地址

<remoteFromPath>//20.12.6.166/ci/depend_modules_6.5/develop/genmullang/langres_toexcel</remoteFromPath>

3.16 设置要放置翻译完之后文件的服务器地址（本工具自动放过去）

<remoteToPath>//20.12.6.166/ci/depend_modules_6.5/develop/genmullang/langres_fromexcel</remoteToPath>

3. 17 设置本地翻译路径（在此进行翻译、修改工作）
<localTranPath>C:/uap/local/develop</localTranPath>

3.18 设置本地备份路径
<localBackupPath>E:/uaptran/backup/develop</localBackupPath>

3.19 设置自动执行任务的日期（星期几）

<weekday>2</weekday>

注：时间默认为 14:00

