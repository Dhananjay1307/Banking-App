import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class transfercontroller {

    @FXML
    private TextField acc2;

    @FXML
    private TextField amtto;

    @FXML
    private TextField pwd;

    @FXML
    private Label ts;

    @FXML
    private Label remain;

    @FXML
    private Label succes;

    private Stage stage;
    private Scene scene;
    private Parent root;
    public static long accn1;
    public static long balance1;
    public static String pas;
    Connection con;
    PreparedStatement pst,pst2,updateStatement,updateStatement2;
    ResultSet rst,rst2,exc,exc2;

    public void gettranc(long accno,String pass){
        accn1=accno;
        pas=pass;
       
    }

    @FXML
    void back(ActionEvent event) {
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("dash.fxml"));
            root=loader.load();
            stage=(Stage)((Node)event.getSource()).getScene().getWindow();
            scene=new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    void confirm(ActionEvent event) {


        if (acc2.getText().isEmpty() || pwd.getText().isEmpty()) {
            ts.setText("Please enter correct details");
            succes.setText("");
            remain.setText("");
        } else {
            String accn2=acc2.getText();
            String pass=pwd.getText();
            long am =Long.parseLong(amtto.getText());
            if (!pass.equals(pas)) {
                ts.setText("Please enter correct password");
                succes.setText("");
                remain.setText("");
            }
            else {
                long accnum2=Long.parseLong(accn2);
                try {
                    con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "326910");
                    pst2=con.prepareStatement("SELECT balance FROM statement WHERE accno = ?");
                    pst2.setLong(1, accnum2);
                    rst2=pst2.executeQuery();
                    if (rst2.next()) {
                        long bal2 = rst2.getLong("balance");
                        con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "326910");
                        pst=con.prepareStatement("SELECT balance FROM statement WHERE accno = ?");
                        pst.setLong(1, accn1);
                        rst=pst.executeQuery();
                        if (rst.next()) {
                            long bal1 = rst.getLong("balance");
                            long newValue = bal1 - am;
                            if(bal1>am){
                                
                                updateStatement = con.prepareStatement("UPDATE statement SET balance = ? WHERE accno = ?"); 
                                updateStatement.setLong(1, newValue);
                                updateStatement.setLong(2, accn1);
                                updateStatement.executeUpdate();
                                long newValue2 = bal2 + am;
                                updateStatement2 = con.prepareStatement("UPDATE statement SET balance = ? WHERE accno = ?"); 
                                updateStatement2.setLong(1, newValue2);
                                updateStatement2.setLong(2, accnum2);
                                updateStatement2.executeUpdate();
                                ts.setText("");
                                succes.setText("Transction Successful!");
                                remain.setText("Remaining balance is: ₹"+newValue);
                            } else {
                                ts.setText("");
                                ts.setText("Insufficient Balance");
                                remain.setText("Current balance is: ₹"+bal1);
                            }
                        }
                    }
                    else{
                        ts.setText("Entered Account does not exsist");

                    }
                }catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

                      
    }
}
