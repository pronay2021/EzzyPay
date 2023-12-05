
package DatabaseConnection;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;





/**
 *
 * @author FUJITSU
 */
public class BDConnector {
    public String username,password,serverAddress,dbName;
    public Connection dbCon;

    public BDConnector(String username, String password, String serverAddress, String dbName) {
        this.username = username;
        this.password = password;
        this.serverAddress = serverAddress;
        this.dbName = dbName;
    }
    public void Connection() throws SQLException{
        String dbAddress="jdbc:mysql://localhost:3306/"+this.dbName;
        
        this.dbCon= (Connection) DriverManager.getConnection(dbAddress,username,password);
    }
}
