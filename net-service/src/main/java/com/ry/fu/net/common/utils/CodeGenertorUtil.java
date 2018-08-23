package com.ry.fu.net.common.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * @Description Model代码生成器
 * @Date 2018/4/29 12:07
 * @Since v1.7
 * @Autor Nick
 */
public class CodeGenertorUtil implements Serializable {


	//-------------------------- 参数设置 start --------------------------------

	//Model包路径
	public static String modelPackageName = "com.ry.fu.esb.test.model";
	public static String mapperPackageName = "com.ry.fu.esb.test.mapper";
	//作者
	public static String author = "Nick";
	//版本
	public static String version = "V1.0.0";
	//表明，多个用逗号隔开
	public static String tables = "MP_IMG,MP_IMG_TYPE,MP_PATIENT";
	//表前缀
	public static String tablePrefix = "MP_";
	public static String modelFilePath = modelPackageName.replace(".", "\\");
	public static String startPath = "D:\\temp\\";

	//-------------------------- 参数设置 end  -------------------------------


	private static Connection con = null;

	static class Ora {
		static final String DRIVER_CLASS = "oracle.jdbc.driver.OracleDriver";
		static final String DATABASE_URL = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
		static final String DATABASE_USER = "alo";
		static final String DATABASE_PASSWORD = "alo";
	}

	static class MySql {
		static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
		static final String DATABASE_URL = "jdbc:mysql://localhost/plusoft_test?useUnicode=true&characterEncoding=GBK";
		static final String DATABASE_USER = "root";
		static final String DATABASE_PASSWORD = "1234";
	}

	public static Connection getOracleConnection() {
		try {
			Class.forName(Ora.DRIVER_CLASS);
			con = DriverManager.getConnection(Ora.DATABASE_URL, Ora.DATABASE_USER, Ora.DATABASE_PASSWORD);
			return con;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return con;
	}

	public static Connection getMySqlConnection() {
		try {
			Class.forName(MySql.DRIVER_CLASS);
			con = DriverManager.getConnection(MySql.DATABASE_URL, MySql.DATABASE_USER, MySql.DATABASE_PASSWORD);
			return con;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return con;
	}

	public static String underlineToCamel(String param) {
		char UNDERLINE = '_';
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (c == UNDERLINE) {
				if (++i < len) {
					sb.append(Character.toUpperCase(param.charAt(i)));
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static List<Map<String, String>> getOracleTable(String Table) throws SQLException {
		getOracleConnection();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		PreparedStatement ps = null;
		try {
			DatabaseMetaData m_DBMetaData = con.getMetaData();
			String sql = "select * from user_col_comments where table_name = ?";
			//getColumns(java.lang.String catalog,  java.lang.String schema,java.lang.String table, java.lang.String col)
			ResultSet colrs = m_DBMetaData.getColumns(null, Ora.DATABASE_USER.toUpperCase(), Table, "%");
			while (colrs.next()) {
				Map map = new HashMap();
				String columnName = colrs.getString("COLUMN_NAME");
				String columnType = colrs.getString("TYPE_NAME");
				int datasize = colrs.getInt("COLUMN_SIZE");
				int nullable = colrs.getInt("NULLABLE");
				map.put("nullable", nullable);
				map.put("columnName", columnName);
				map.put("columnType", columnType);
				map.put("dataSize", datasize);
				list.add(map);
			}

			ps = con.prepareStatement(sql);
			ps.setString(1, Table);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String columnName = rs.getString("COLUMN_NAME");
				String comments = rs.getString("COMMENTS");
				for (Map<String, String> map : list) {
					if (map.get("columnName").equals(columnName)) {
						map.put("remarks", comments);
					}
				}
			}
			System.out.println(JsonUtils.toJSon(list));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ps != null) ps.close();
		}
		return list;
	}

	public static String getTableCommnet(String tableName) {
		String sql = "SELECT * FROM USER_TAB_COMMENTS WHERE TABLE_NAME = ?";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, tableName);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("COMMENTS");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static void closeConn() {
		try {
			if(con != null)con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 把Oracle字段类型 转化为 java类型
	 *
	 * @param sqlType 字段类型
	 * @param size    字段大小
	 * @param scale   默认=0
	 * @return
	 */
	public static String oracleSqlType2JavaType(String sqlType, int size, int scale) {
		if (sqlType.equals("integer")) {
			return "Integer";
		} else if (sqlType.equals("long")) {
			return "Long";
		} else if (sqlType.equals("float")
				|| sqlType.equals("float precision")
				|| sqlType.equals("double")
				|| sqlType.equals("double precision")
				) {
			return "BigDecimal";
		} else if (sqlType.equals("number")
				|| sqlType.equals("decimal")
				|| sqlType.equals("numeric")
				|| sqlType.equals("real")) {
			return scale == 0 ? (size < 10 ? "Integer" : "Long") : "BigDecimal";
		} else if (sqlType.equals("varchar")
				|| sqlType.equals("varchar2")
				|| sqlType.equals("char")
				|| sqlType.equals("nvarchar")
				|| sqlType.equals("nchar")) {
			return "String";
		} else if (sqlType.equals("datetime")
				|| sqlType.equals("date")
				|| sqlType.equals("timestamp")) {
			return "Date";
		}
		return "String";
	}

	public static String getItems(List<Map<String, String>> map, String tableName, String description) {
		//记得转化成小写
		StringBuffer sb = new StringBuffer();
		sb.append("package " + modelPackageName + ";\n\n");

		sb.append("import java.util.Date;\n");
		sb.append("import lombok.Data;\n");
		sb.append("import lombok.EqualsAndHashCode;\n");

		sb.append("import javax.persistence.Column;\n");
		sb.append("import javax.persistence.Id;\n");
		sb.append("import javax.persistence.Table;\n");

		sb.append("\n\n");

		sb.append("/**\n" +
				" * @author " + author + "\n" +
				" * @version " + version + "\n" +
				" * @Date " + DateFormatUtils.format(new Date(), "yyyy/MM/dd HH:mm") + "\n" +
				" * @description " + description + " Model类\n" +
				" */");
		sb.append("\r\n");
		sb.append("@Data\n");
		sb.append("@EqualsAndHashCode(callSuper = false)\n");
		sb.append("@Table(name = \"" + tableName.toUpperCase() + "\")\n");
		String removePrefix = StringUtils.remove(tableName, tablePrefix);
		sb.append("public class " + getUpperOne(underlineToCamel(removePrefix.toLowerCase())) + "  implements Serializable {\r\n\n");
		//得到私有属性
		for (Map map0 : map) {
			String columnname = map0.get("columnName").toString();
			String columntype = map0.get("columnType").toString();
			String columnsize = String.valueOf(map0.get("dataSize"));
			String remarks = map0.get("remarks") == null ? "" : map0.get("remarks").toString();
			String javaType = oracleSqlType2JavaType(columntype.toLowerCase(), Integer.parseInt(columnsize), 0);
			String anno = "\t@Column(name = \"" + columnname.toUpperCase() + "\")\r\n";
			String filed = "\tprivate " + javaType + " " + underlineToCamel(columnname.toLowerCase()) + ";\n\n";

			sb.append("\t//" + remarks + "\n");
			if (columnname.toUpperCase().equals("ID")) {
				sb.append("\t@Id\n");
			}
			sb.append(anno);
			sb.append(filed);
		}
		sb.append("}\n");
		return sb.toString();
	}

	/**
	 * 把输入字符串的首字母改成大写
	 *
	 * @param str
	 * @return
	 */
	public static String getUpperOne(String str) {
		char[] ch = str.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z') {
			ch[0] = (char) (ch[0] - 32);
		}
		return new String(ch);
	}

	public static void mkDir(File file) {
		if (file.getParentFile().exists()) {
			file.mkdir();
		} else {
			mkDir(file.getParentFile());
			file.mkdir();
		}
	}


	public static void main(String[] args) throws IOException {
		try {
			String[] tablenNames = tables.split(",");
			for (String tableName : tablenNames) {
				List<Map<String, String>> map = getOracleTable(tableName);
				String descr = getTableCommnet(tableName);

				String a = getItems(map, tableName, descr);

				String removePrefix = StringUtils.remove(tableName, tablePrefix);
				String fileName = getUpperOne(underlineToCamel(removePrefix.toLowerCase()));
				File dirPath = new File(startPath + modelFilePath + File.separator);
				mkDir(dirPath);
				File javaFile = new File(startPath + modelFilePath + File.separator + fileName + ".java");
				FileOutputStream fos = new FileOutputStream(javaFile, false);//true表示在文件末尾追加
				fos.write(a.getBytes());
				fos.close();//流要及时关闭
			}
			System.out.println("生成java完成");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
