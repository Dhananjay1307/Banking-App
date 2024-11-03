import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class transhistory {
    public static long acc;
    //public static long bal;
    public static long diducted;
    public static String details;

    Connection con;
    PreparedStatement pst,updateStatement;
    ResultSet rst,exc;

    public void details(long accnum,long newbalance,String det){
        acc=accnum;
        //bal=balance;
        diducted=newbalance;

        try {
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mybank", "root", "Naik@123");
            pst=con.prepareStatement("UPDATE users SET transdet WHERE accno = ?");
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}