package com.sys.dao;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DataBaseDAO
{
  private static boolean enableLocalDebug = Boolean.getBoolean("jdbc.enable_local_debug");

  private static Context ctx = null;

  private static DataSource ds = null;

  private static void initDataSource() throws Exception {
    if (ctx == null) {
      ctx = new InitialContext();
    }
    if (ds == null)
      ds = (DataSource)ctx.lookup("java:comp/env/jdbc/DataSource");
  }

  public static Connection getConnection()
    throws SQLException
  {
    try
    {
      initDataSource();
      return ds.getConnection();
    } catch (SQLException ex) {
      throw ex;
    } catch (Exception e) {
      if (enableLocalDebug) {
        return getTestConn();
      }
      throw new RuntimeException(e.toString());
    }
  }

  protected static Connection getTestConn()
  {
    try
    {
      String driver = System.getProperty("jdbc.driver", "oracle.jdbc.driver.OracleDriver");
      String url = System.getProperty("jdbc.url", "jdbc:oracle:thin:@10.2.33.1:1521:db9203");
      String userName = System.getProperty("jdbc.username", "taipinglifetest");
      String password = System.getProperty("jdbc.password", "tptest");

      Class.forName(driver).newInstance();
      return DriverManager.getConnection(url, userName, password);
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new RuntimeException(ex.getMessage());
    }
  }

  public static void close(ResultSet rs, Statement stmt, Connection conn)
  {
    if (rs != null) {
      try {
        rs.close();
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
    }
    if (stmt != null) {
      try {
        stmt.close();
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
    }
    if (conn != null)
      try {
        conn.close();
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
  }

  public static void close(ResultSet rs, Statement stmt)
  {
    close(rs, stmt, null);
  }

  public static void close(Connection conn)
  {
    close(null, null, conn);
  }

  public static List resultSetToList(ResultSet rs)
    throws SQLException
  {
    if (rs == null) {
      return Collections.EMPTY_LIST;
    }

    ResultSetMetaData md = rs.getMetaData();
    int columnCount = md.getColumnCount();

    List list = new ArrayList();

    while (rs.next()) {
      Map rowData = new HashMap(columnCount);
      for (int i = 1; i <= columnCount; i++) {
        rowData.put(convertFieldName(md.getColumnName(i)), rs.getObject(i));
      }
      list.add(rowData);
    }
    return list;
  }

  public static Map resultSetToMap(ResultSet rs)
    throws SQLException
  {
    if (rs == null) {
      return null;
    }

    ResultSetMetaData md = rs.getMetaData();
    int columnCount = md.getColumnCount();

    Map rowData = null;
    if ((rs.isFirst()) || (rs.next())) {
      rowData = new HashMap(columnCount);
      for (int i = 1; i <= columnCount; i++) {
        rowData.put(convertFieldName(md.getColumnName(i)), rs.getObject(i));
      }
    }
    return rowData;
  }

  public static String readClob(Clob clob)
    throws Exception
  {
    StringBuffer buff = new StringBuffer();
    try {
      long length = clob.length();

      for (long p = 1L; p <= length; p += 1024L) {
        buff.append(clob.getSubString(p, 1024));
      }
      return buff.toString();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  public static void writeClob(Reader reader, String sTableName, String sFieldName, String sClause, Connection conn)
    throws Exception
  {
    PreparedStatement pst = null;
    CallableStatement cst = null;
    String sql;
    try
    {
      sql = "update " + sTableName + " set " + sFieldName + "=EMPTY_CLOB() WHERE " + sClause;
      pst = conn.prepareStatement(sql);
      pst.executeUpdate();
      pst.close();

      char[] buff = new char[4096];

      sql = "select " + sFieldName + " from " + sTableName + " where " + sClause + " for update";
      cst = conn.prepareCall("{call p_write_clob(?,?,?)}");
      int size;
      while ((size = reader.read(buff)) > 0)
      {
        cst.setString(1, sql);
        cst.setInt(2, size);
        cst.setString(3, new String(buff, 0, size));
        cst.execute();
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      close(null, cst, null);
      close(null, pst, null);
    }
  }

  public static void readBlob(Blob blob, OutputStream out)
    throws Exception
  {
    try
    {
      InputStream reader = blob.getBinaryStream();

      byte[] buffer = new byte[1024];
      int nbytes = 0;
      while ((nbytes = reader.read(buffer)) != -1) {
        out.write(buffer, 0, nbytes);
      }
      reader.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  public static void writeBlob(InputStream in, String sTableName, String sFieldName, String sClause, Connection conn)
    throws Exception
  {
    PreparedStatement pst = null;
    CallableStatement cst = null;
    String sql;
    try
    {
      sql = "update " + sTableName + " set " + sFieldName + "=EMPTY_BLOB() WHERE " + sClause;
      pst = conn.prepareStatement(sql);
      pst.executeUpdate();
      pst.close();

      byte[] buff = new byte[15360];

      sql = "select " + sFieldName + " from " + sTableName + " where " + sClause + " for update";
      cst = conn.prepareCall("{call p_write_blob(?,?,?)}");
      int size;
      while ((size = in.read(buff)) > 0)
      {
        cst.setString(1, sql);
        cst.setInt(2, size);
        if (size == buff.length) {
          cst.setBytes(3, buff);
        } else {
          byte[] bytes = new byte[size];
          System.arraycopy(buff, 0, bytes, 0, size);
          cst.setBytes(3, bytes);
        }
        cst.execute();
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      close(null, cst, null);
      close(null, pst, null);
    }
  }

  public static String convertFieldName(String sName)
  {
    if (sName == null) {
      return null;
    }
    if (sName.length() <= 1) {
      return sName.toLowerCase();
    }
    StringBuffer dst = new StringBuffer();
    boolean beUpCase = false;
    for (int i = 0; i < sName.length(); i++) {
      String tmp = sName.substring(i, i + 1);
      if ("_".equals(tmp)) {
        beUpCase = true;
      }
      else {
        if (beUpCase)
          dst.append(tmp.toUpperCase());
        else {
          dst.append(tmp.toLowerCase());
        }
        beUpCase = false;
      }
    }
    return dst.toString();
  }
}