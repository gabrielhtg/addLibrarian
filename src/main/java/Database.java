import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Database {
    Statement perintah;
    Connection koneksi;   
    
    public Database () {
        try {
            koneksi = DriverManager.getConnection("jdbc:mysql://sql6.freesqldatabase.com:3306/sql6581914", "sql6581914", "3wNAA68bjs");

            perintah = koneksi.createStatement();
        }

        catch (Exception args0) {
            // args0.printStackTrace();
        }
    }
}
