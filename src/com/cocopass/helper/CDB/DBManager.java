package com.cocopass.helper.CDB;

import java.sql.*;
import java.util.*;
import java.lang.reflect.*; 
import javax.sql.DataSource; 
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cocopass.helper.CDB.DBException;
 
/**
 * ���ݿ����
 * @author Winter Lau
 * @date 2010-2-2 ����10:18:50
 */
public class DBManager {
 
    private final static Log log = LogFactory.getLog(DBManager.class);
    private final static ThreadLocal<Connection> conns = new ThreadLocal<Connection>();
    private static DataSource dataSource;
    private static boolean show_sql = false;
     
    static {
        try {
			initDataSource(null);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
 
    /**
     * ��ʼ�����ӳ�
     * @param props
     * @param show_sql
     * @throws DBException 
     */
    private final static void initDataSource(Properties dbProperties) throws DBException {
        try {
            if(dbProperties == null){
                dbProperties = new Properties();
                dbProperties.load(DBManager.class.getResourceAsStream("db.properties"));
            }
            Properties cp_props = new Properties();
            for(Object key : dbProperties.keySet()) {
                String skey = (String)key;
                if(skey.startsWith("jdbc.")){
                    String name = skey.substring(5);
                    cp_props.put(name, dbProperties.getProperty(skey));
                    if("show_sql".equalsIgnoreCase(name)){
                        show_sql = "true".equalsIgnoreCase(dbProperties.getProperty(skey));
                    }
                }
            }
            dataSource = (DataSource)Class.forName(cp_props.getProperty("datasource")).newInstance();
            if(dataSource.getClass().getName().indexOf("c3p0")>0){
                //Disable JMX in C3P0
                System.setProperty("com.mchange.v2.c3p0.management.ManagementCoordinator", 
                        "com.mchange.v2.c3p0.management.NullManagementCoordinator");
            }
            log.info("Using DataSource : " + dataSource.getClass().getName());
            BeanUtils.populate(dataSource, cp_props);
 
            Connection conn = getConnection();
            DatabaseMetaData mdm = conn.getMetaData();
            log.info("Connected to " + mdm.getDatabaseProductName() + 
                              " " + mdm.getDatabaseProductVersion());
            closeConnection();
        } catch (Exception e) {
            throw new DBException(e);
        }
    }
     
    /**
     * �Ͽ����ӳ�
     */
    public final static void closeDataSource(){
        try {
            dataSource.getClass().getMethod("close").invoke(dataSource);
        } catch (NoSuchMethodException e){ 
        } catch (Exception e) {
            log.error("Unabled to destroy DataSource!!! ", e);
        }
    }
 
    public final static Connection getConnection() throws SQLException {
        Connection conn = conns.get();
        if(conn ==null || conn.isClosed()){
            conn = dataSource.getConnection();
            conns.set(conn);
        }
        return (show_sql && !Proxy.isProxyClass(conn.getClass()))?
                      new _DebugConnection(conn).getConnection():conn;
    }
     
    /**
     * �ر�����
     */
    public final static void closeConnection() {
        Connection conn = conns.get();
        try {
            if(conn != null && !conn.isClosed()){
                conn.setAutoCommit(true);
                conn.close();
            }
        } catch (SQLException e) {
            log.error("Unabled to close connection!!! ", e);
        }
        conns.set(null);
    }
 
    /**
     * ���ڸ���ִ�е�SQL���
     * @author Winter Lau
     */
    static class _DebugConnection implements InvocationHandler {
         
        private final static Log log = LogFactory.getLog(_DebugConnection.class);
         
        private Connection conn = null;
 
        public _DebugConnection(Connection conn) {
            this.conn = conn;
        }
 
        /**
         * Returns the conn.
         * @return Connection
         */
        public Connection getConnection() {
            return (Connection) Proxy.newProxyInstance(conn.getClass().getClassLoader(), 
                             conn.getClass().getInterfaces(), this);
        }
         
        public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
            try {
                String method = m.getName();
                if("prepareStatement".equals(method) || "createStatement".equals(method))
                    log.info("[SQL] >>> " + args[0]);              
                return m.invoke(conn, args);
            } catch (InvocationTargetException e) {
                throw e.getTargetException();
            }
        }
 
    }
    
    
 

}