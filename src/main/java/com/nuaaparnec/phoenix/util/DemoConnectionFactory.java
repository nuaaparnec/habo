
package com.nuaaparnec.phoenix.util;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.phoenix.jdbc.PhoenixConnection;
import org.apache.phoenix.query.ConfigurationFactory;
import org.apache.phoenix.util.InstanceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class DemoConnectionFactory {
    private static final Logger log = LoggerFactory.getLogger(DemoConnectionFactory.class);

    private static volatile boolean isInit = false;

    public static void init(String kerberosIp, String user, String keytabPath) {
      if ((StringUtils.isBlank(kerberosIp)) || (StringUtils.isBlank(user)) || (StringUtils.isBlank(keytabPath))) {
        log.error("please input the kerberos IpAddr、user and keytabPath");
        throw new RuntimeException("please input the kerberos IpAddr、user and keytabPath");
      }
      if (!isInit)
        synchronized (DemoConnectionFactory.class) {
          if (!isInit) {
            System.setProperty("java.security.krb5.realm", "NUAAPARNEC.NET");
            System.setProperty("java.security.krb5.kdc", kerberosIp);
            final Configuration conf = HBaseConfiguration.create();
            conf.set("hbase.zookeeper.quorum", "master,slave1,slave2");
            conf.set("hbase.zookeeper.property.clientport", "2181");
            conf.set("hadoop.security.authentication", "Kerberos");
            conf.set("hbase.security.authentication", "Kerberos");
            conf.set("hbase.master.kerberos.principal", "hbase/_HOST@NUAAPARNEC.NET");
            conf.set("hbase.regionserver.kerberos.principal", "hbase/_HOST@NUAAPARNEC.NET");
            try {
              UserGroupInformation.setConfiguration(conf);
              InstanceResolver.getSingleton(ConfigurationFactory.class, new ConfigurationFactory()
              {
                public Configuration getConfiguration() {
                  return conf;
                }

                public Configuration getConfiguration(Configuration confToClone)
                {
                  Configuration copy = new Configuration(conf);
                  copy.addResource(confToClone);
                  return copy;
                }
              });
              UserGroupInformation.loginUserFromKeytab(user, keytabPath);
              isInit = true;
            } catch (Exception e) {
              log.error("init kerberos error", e);
            }
          }
        }
    }

    public static DemoConnection getConn(String url, int numPerCommit, Properties info)
      throws SQLException
    {
      if (!isInit) {
        throw new RuntimeException("please init kerberos token,call the method init(...) ");
      }
      PhoenixConnection conn = (PhoenixConnection)DriverManager.getConnection(url, info == null ? new Properties() : info);

      return new DemoConnection(conn, numPerCommit);
    }

    public static DemoConnection getConn(String url)
      throws SQLException
    {
      return getConn(url, 10, null);
    }

    public static DemoConnection getConn(String url, int numPerCommit)
      throws SQLException
    {
      return getConn(url, numPerCommit, null);
    }
}
