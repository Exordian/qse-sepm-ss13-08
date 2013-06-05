package at.ac.tuwien.sepm;

import org.hsqldb.persist.HsqlProperties;
import org.hsqldb.server.Server;
import org.hsqldb.server.ServerConfiguration;

public class DBHelper {
    private static Server server;

    public static void startDB() {
        try {
            String[] options = new String[] { "-database.0", "file:./.db", "-dbname.0", "xdb" };
            HsqlProperties hsqlProperties = HsqlProperties.argArrayToProps(options, "server");
            ServerConfiguration.translateDefaultDatabaseProperty(hsqlProperties);
            ServerConfiguration.translateDefaultNoSystemExitProperty(hsqlProperties);
            server = new Server();
            server.setProperties(hsqlProperties);
            server.start();
        } catch (Exception e) {
            System.err.println("DB Startup failure");
            System.exit(0);
        }
    }

    public static void stopDB() {
        server.stop();
    }
}
