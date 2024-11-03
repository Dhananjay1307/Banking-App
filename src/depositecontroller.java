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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class depositecontroller {

    @FXML
    private TextField amount;

    @FXML
    private PasswordField pwd;

    @FXML
    private Label succ;

    @FXML
    private Label succes;

    @FXML
    private Label remaining;

    private Stage stage;
    private Scene scene;
    private Parent root;
    public static long accn;
    public static long balance;
    public static String pas;
    Connection con;
    PreparedStatement pst,updateStatement;
    ResultSet rst,exc;


    public void getdepo(long accno,String pass){
        accn=accno;
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
    void ok(ActionEvent event) {

        if (amount.getText().isEmpty() || pwd.getText().isEmpty()) {
            succ.setText("Please enter correct details");
            succes.setText("");
            remaining.setText("");
        } else {
            long am =Long.parseLong(amount.getText());
            String pass=pwd.getText();
            if ( !pass.equals(pas)) {
                succ.setText("Please enter correct details");
                succes.setText("");
                remaining.setText("");
            }
            else{
                succ.setText("");
                try  {
                    con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "326910");
                    pst=con.prepareStatement("SELECT balance FROM statement WHERE accno = ?");
                    pst.setLong(1, accn);
                    rst=pst.executeQuery();
                    if (rst.next()) {
                        long bal = rst.getLong("balance");
                        long newValue = bal + am;
                        updateStatement = con.prepareStatement("UPDATE statement SET balance = ? WHERE accno = ?"); 
                        updateStatement.setLong(1, newValue);
                        updateStatement.setLong(2, accn);
                        updateStatement.executeUpdate();
                        succ.setText("");
                        succes.setText( "Amount deposited to your account");
                        remaining.setText("Current balance is: ₹"+newValue);
                        amount.setText("");
                        pwd.setText("");
                    } 
                }catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        
    }
        
}



