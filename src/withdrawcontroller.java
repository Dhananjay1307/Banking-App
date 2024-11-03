import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

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

public class withdrawcontroller {

    static String password;
    static long balance;
    static long accnum;

    @FXML
    private Label amountwarn;

    @FXML
    private PasswordField passwo;

    @FXML
    private Label passwarn;

    @FXML
    private TextField wamount;

    @FXML
    private Label remainbal;

    @FXML
    private Label wsuccess;

    Connection con;
    PreparedStatement pst;
    ResultSet rst;

    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void getacpass (String pass,long acc) {
        
        password=pass;
        accnum=acc;
        //System.out.println(balance+" "+password);
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
    void withdraw(ActionEvent event) {
        int count=0;

        if (wamount.getText().isEmpty() || passwo.getText().isEmpty()) {
            wsuccess.setText("Enter correct details");
        } else {
            try {
                
                con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "326910");
                pst=con.prepareStatement("SELECT balance FROM statement WHERE accno=?");
                pst.setLong(1, accnum);
                rst=pst.executeQuery();
                if (rst.next()) {
                    balance=rst.getLong("balance");
                }
            } catch (Exception e) {
                // TODO: handle exception 
            }

            System.out.println(accnum);

            wsuccess.setText("");
            long getamount=Long.parseLong(wamount.getText());
            String getpass=passwo.getText();
            if (getamount > balance) {
            amountwarn.setText("Insufficient balance");
            wamount.setText("");
            passwo.setText("");
            //System.exit(0);
            }else {
                if (getpass.equals(password)) {
                
                long diff=balance-getamount;
                try {
                    con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "326910");
                    pst=con.prepareStatement("UPDATE statement SET balance=? WHERE accno=? ");
                    pst.setLong(1, diff);
                    pst.setLong(2, accnum);
                    //System.out.println(accn);
                    //((Statement) rst).executeUpdate(pst);
                    pst.executeUpdate();

                } catch (Exception e) {
                    e.printStackTrace();
                    // TODO: handle exception
                }

                    wsuccess.setText("Amount withdrawn successfully");
                    remainbal.setText("Remaining balance is ₹"+diff);
                    String det="withdrawn amount "+getamount;
                    transhistory tr=new transhistory();
                    tr.details(accnum,getamount,det);
                    wamount.setText("");
                    passwo.setText("");
                } else {
                    
                    passwarn.setText("Wrong password!");
                    wamount.setText("");
                    passwo.setText("");
                       
                } 
                    //else {
                    //     try {
                    //         FXMLLoader loader=new FXMLLoader(getClass().getResource("signin.fxml"));
                    //         root=loader.load();
                    //         stage=(Stage)((Node)event.getSource()).getScene().getWindow();
                    //         scene=new Scene(root);
                    //         stage.setScene(scene);
                    //         stage.show();
                    //     } catch (IOException e) {
                    //         // TODO Auto-generated catch block
                    //         e.printStackTrace();
                    //     }
                    // }
            }
        }
    
    }
      
}

