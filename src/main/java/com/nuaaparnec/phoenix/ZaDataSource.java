package com.nuaaparnec.phoenix;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.dbcp.AbandonedConfig;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.SQLNestedException;
import org.apache.commons.pool.KeyedObjectPoolFactory;

import com.nuaaparnec.phoenix.util.DemoConnectionFactory;


public class ZaDataSource extends BasicDataSource {

    @Override
    public ConnectionFactory createConnectionFactory() throws SQLException {

        return super.createConnectionFactory();
    }

    @Override
    protected void createPoolableConnectionFactory(ConnectionFactory driverConnectionFactory,
                                                   KeyedObjectPoolFactory statementPoolFactory,
                                                   AbandonedConfig configuration)
            throws SQLException {
        PoolableConnectionFactory connectionFactory = null;
        try {
            connectionFactory = new PoolableConnectionFactory(driverConnectionFactory, connectionPool,
                    statementPoolFactory, validationQuery, validationQueryTimeout, connectionInitSqls, defaultReadOnly,
                    defaultAutoCommit, defaultTransactionIsolation, defaultCatalog, configuration);
            ZaDataSource.validateConnectionFactory(connectionFactory);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new SQLNestedException("Cannot create PoolableConnectionFactory (" + e.getMessage() + ")", e);
        }
    }

    public static void validateConnectionFactory(PoolableConnectionFactory connectionFactory) throws Exception {
        Connection conn = null;
        try {
            DemoConnectionFactory.init("100.253.9.80", "admin@NUAAPARNEC.NET", "D://alidata1//admin//kerberos//admin_test.keytab");
            conn = DriverManager.getConnection("jdbc:phoenix:10.253.11.207,10.139.113.47,10.253.12.4:2181");
            connectionFactory.activateObject(conn);
            connectionFactory.validateConnection(conn);
            connectionFactory.passivateObject(conn);
        } finally {
            connectionFactory.destroyObject(conn);
        }
    }

}
