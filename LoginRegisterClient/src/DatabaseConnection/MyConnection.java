package DatabaseConnection;

//import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author mayur
 */
public class MyConnection {
//  localhost
//    private static final String SERVER_NAME = "Mayuresh";   // Mayuresh -> 192.168.43.218
//    private static final String USER_NAME = "root";
//    private static final String DB_NAME = "users_details";
//    private static final int PORT_NUMBER = 3306;
//    private static final String PASSWORD = "mk7767mk";
    
//    Online database
    private static final String SERVER_NAME = "remotemysql.com";
    private static final String USER_NAME = "234FmavvTJ";
    private static final String DB_NAME = "234FmavvTJ";
    private static final int PORT_NUMBER = 3306;
    private static final String PASSWORD = "9dgCdf3rLM";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
//            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/users_details",USER_NAME,PASSWORD);
            conn = DriverManager.getConnection("jdbc:mysql://" + SERVER_NAME + ":" + PORT_NUMBER + "/" + DB_NAME,
                    USER_NAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MyConnection.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error occurred while connecting...\nPlease check network connection!",
                        "Connection Error", JOptionPane.CANCEL_OPTION);
        }
        return conn;
    }// end - of getConnection() method

//    readymade methods are used in following method
//    public static Connection getConnection() {
//        Connection con = null;
//        MysqlDataSource datasource = new MysqlDataSource();
//
//        datasource.setServerName(SERVER_NAME);
//        datasource.setUser(USER_NAME);
//        datasource.setPassword(PASSWORD);
//        datasource.setDatabaseName(DB_NAME);
//        datasource.setPortNumber(PORT_NUMBER);
//
//        try {
//            con = datasource.getConnection();
//        } catch (SQLException ex) {
//            Logger.getLogger(MyConnection.class.getName()).log(Level.SEVERE, null, ex);
//            JOptionPane.showMessageDialog(null, "Error occurred while connecting...\nPlease check network connection!",
//                        "Connection Error", JOptionPane.CANCEL_OPTION);
//        }
//        return con;
//    }// end - of getConnection() method
    
}//end - MyConnection class
