package com.yuyiyun.YYdata.core.schedue.quartz;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.yuyiyun.YYdata.modular.newspaper.entity.DataNews;
import com.yuyiyun.YYdata.modular.newspaper.service.DataNewsService;

public class PaperDataPushJob extends QuartzJobBean {
	@Autowired
	DataNewsService dataNewsService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		System.err
				.println("<<<<<调试信息,注释掉SchedulingConfig类中的内容来关闭这个定时任务！>>>>> quartz task执行 >>>>> PaperDataPushJob执行了！");
		List<Map<String, Object>> pushDatas = dataNewsService.getPushDatas(1252046381513875458L, 20);
		for (Map<String, Object> dataNews : pushDatas) {
			System.out.println(dataNews);
		}
	}

	private static Connection conn = null;
	private static Statement sm = null;
	private static String schema = "yydata";// 模式名
	private static String select = "SELECT * FROM";// 查询sql
	private static String where = " where data_source = '1242621332017037314'";// 查询sql
	private static String insert = "INSERT INTO";// 插入sql
	private static String values = "VALUES";// values关键字
	private static String[] table = { "data_news" };// table数组
	private static List<String> insertList = new ArrayList<String>();// 全局存放insertsql文件的数据
	private static String filePath = "D:\\mysql\\sql.sql";// 绝对路径导出数据的文件

	public static void main(String[] args) throws SQLException {
		List<String> listSQL = new ArrayList<String>();
		connectSQL("com.mysql.jdbc.Driver",
				"jdbc:mysql://192.168.0.100:3306/yydata?useUnicode=true&characterEncoding=utf8&useSSL=false&tinyInt1isBit=true",
				"root", "1212");// 连接数据库
		System.out.println(conn);
		System.out.println(sm);
		listSQL = createSQL();// 创建查询语句
		System.out.println(listSQL);
		executeSQL(conn, sm, listSQL);// 执行sql并拼装
		System.out.println(">>" + insertList);
        createFile();//创建文件
	}

	/**
	 * 创建insertsql.txt并导出数据
	 * 
	 * @author duhao
	 */
	private static void createFile() {
		File file = new File(filePath);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.out.println("创建文件名失败！！");
				e.printStackTrace();
			}
		}
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			if (insertList.size() > 0) {
				for (int i = 0; i < insertList.size(); i++) {
					bw.append(insertList.get(i));
					bw.append("\n");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bw.close();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 拼装查询语句
	 * 
	 * @author duhao
	 * @return 返回select集合
	 */
	private static List<String> createSQL() {
		List<String> listSQL = new ArrayList<String>();
		for (int i = 0; i < table.length; i++) {
			StringBuffer sb = new StringBuffer();
			sb.append(select).append(" ").append(schema).append(".").append(table[i]).append(where);
			listSQL.add(sb.toString());
		}
		return listSQL;
	}

	/**
	 * 连接数据库创建statement对象
	 * 
	 * @author duhao
	 * @param driver
	 * @param url
	 * @param UserName
	 * @param Password
	 */
	public static void connectSQL(String driver, String url, String UserName, String Password) {
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url, UserName, Password);
			sm = conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 执行sql并返回插入sql
	 * 
	 * @author duhao
	 * @param conn
	 * @param sm
	 * @param listSQL
	 * @throws SQLException
	 */
	public static void executeSQL(Connection conn, Statement sm, List<String> listSQL) throws SQLException {
		List<String> insertSQL = new ArrayList<String>();
		ResultSet rs = null;
		try {
			rs = getColumnNameAndColumeValue(sm, listSQL, rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			rs.close();
			sm.close();
			conn.close();
		}
	}

	/**
	 * 获取列名和列值
	 * 
	 * @author duhao
	 * @param sm
	 * @param listSQL
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private static ResultSet getColumnNameAndColumeValue(Statement sm, List<String> listSQL, ResultSet rs)
			throws SQLException {
		if (listSQL.size() > 0) {
			for (int j = 0; j < listSQL.size(); j++) {
				String sql = String.valueOf(listSQL.get(j));
				rs = sm.executeQuery(sql);
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				while (rs.next()) {
					StringBuffer ColumnName = new StringBuffer();
					StringBuffer ColumnValue = new StringBuffer();
					for (int i = 1; i <= columnCount; i++) {
						Object object = rs.getObject(i);
//						String value = rs.getString(i).trim();
						if (null == object) {
							continue;
						}
						String value = object.toString().trim();
						if ("".equals(value)) {
							value = "";
						}
						if (i == 1 || i == columnCount) {
							if (i == columnCount) {
								ColumnName.append(",");
							}
							ColumnName.append(rsmd.getColumnName(i));
							if (i == 1) {
								if (Types.CHAR == rsmd.getColumnType(i) || Types.VARCHAR == rsmd.getColumnType(i)
										|| Types.LONGVARCHAR == rsmd.getColumnType(i)) {
									ColumnValue.append("'").append(value).append("',");
								} else if (Types.SMALLINT == rsmd.getColumnType(i)
										|| Types.INTEGER == rsmd.getColumnType(i)
										|| Types.BIGINT == rsmd.getColumnType(i) || Types.FLOAT == rsmd.getColumnType(i)
										|| Types.DOUBLE == rsmd.getColumnType(i)
										|| Types.NUMERIC == rsmd.getColumnType(i)
										|| Types.DECIMAL == rsmd.getColumnType(i)
										|| Types.TINYINT == rsmd.getColumnType(i)) {
									ColumnValue.append(value).append(",");
								} else if (Types.DATE == rsmd.getColumnType(i) || Types.TIME == rsmd.getColumnType(i)
										|| Types.TIMESTAMP == rsmd.getColumnType(i)) {
									ColumnValue.append("timestamp'").append(value).append("',");
								} else {
									ColumnValue.append(value).append(",");

								}
							} else {
								if (Types.CHAR == rsmd.getColumnType(i) || Types.VARCHAR == rsmd.getColumnType(i)
										|| Types.LONGVARCHAR == rsmd.getColumnType(i)) {
									ColumnValue.append("'").append(value);
								} else if (Types.SMALLINT == rsmd.getColumnType(i)
										|| Types.INTEGER == rsmd.getColumnType(i)
										|| Types.BIGINT == rsmd.getColumnType(i) || Types.FLOAT == rsmd.getColumnType(i)
										|| Types.DOUBLE == rsmd.getColumnType(i)
										|| Types.NUMERIC == rsmd.getColumnType(i)
										|| Types.DECIMAL == rsmd.getColumnType(i)
										|| Types.TINYINT == rsmd.getColumnType(i)) {
									ColumnValue.append(value);
								} else if (Types.DATE == rsmd.getColumnType(i) || Types.TIME == rsmd.getColumnType(i)
										|| Types.TIMESTAMP == rsmd.getColumnType(i)) {
									ColumnValue.append("timestamp'").append(value);
								} else {
									ColumnValue.append(value);

								}
							}

						} else {
							ColumnName.append("," + rsmd.getColumnName(i));
							if (Types.CHAR == rsmd.getColumnType(i) || Types.VARCHAR == rsmd.getColumnType(i)
									|| Types.LONGVARCHAR == rsmd.getColumnType(i)) {
								ColumnValue.append("'").append(value).append("'").append(",");
							} else if (Types.SMALLINT == rsmd.getColumnType(i) || Types.INTEGER == rsmd.getColumnType(i)
									|| Types.BIGINT == rsmd.getColumnType(i) || Types.FLOAT == rsmd.getColumnType(i)
									|| Types.DOUBLE == rsmd.getColumnType(i) || Types.NUMERIC == rsmd.getColumnType(i)
									|| Types.DECIMAL == rsmd.getColumnType(i)
									|| Types.TINYINT == rsmd.getColumnType(i)) {
								ColumnValue.append(value).append(",");
							} else if (Types.DATE == rsmd.getColumnType(i) || Types.TIME == rsmd.getColumnType(i)
									|| Types.TIMESTAMP == rsmd.getColumnType(i)) {
								ColumnValue.append("timestamp'").append(value).append("',");
							} else {
								ColumnValue.append(value).append(",");
							}
						}
					}
					System.out.println(ColumnName.toString());
					System.out.println(ColumnValue.toString());
					insertSQL(ColumnName, ColumnValue, table[j]);
				}
			}
		}
		return rs;
	}

	/**
	 * 拼装insertsql放到全局list里面
	 * 
	 * @author duhao
	 * @param ColumnName
	 * @param ColumnValue
	 * @param tableName
	 */
	private static void insertSQL(StringBuffer ColumnName, StringBuffer ColumnValue, String tableName) {
		StringBuffer insertSQL = new StringBuffer();
		insertSQL.append(insert).append(" ").append(schema).append(".").append(tableName).append("(")
				.append(ColumnName.toString()).append(")").append(values).append("(").append(ColumnValue.toString())
				.append(");");
		insertList.add(insertSQL.toString());
		System.out.println(insertSQL.toString());

	}
}
