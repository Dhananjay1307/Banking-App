import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class dashcontroller {

    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label wname;

    public static long acc;
    public static String uname;
    public static long balance;
    public static String passw;

      Connection con;
    PreparedStatement pst;
    ResultSet rst;

    // dashcontroller(long accno,String myname,long bal){
    //     acc=accno;
    //     uname=myname;
    //     balance=bal;
    //     getvalue(acc, uname, balance);
    // }

    public void getvalue(long accno,String myname,String pass){
        acc=accno;
        uname=myname;
        passw=pass;
        //System.out.println(balance);
        wname.setText("Welcome "+uname);
    }

    @FXML
    void deposit(ActionEvent event) {
          try {
            //System.out.println("kdjfhksj");
            FXMLLoader loader=new FXMLLoader(getClass().getResource("deposite.fxml"));
            root=loader.load();
            depositecontroller dec=loader.getController();
            dec.getdepo(acc,passw);
            stage=(Stage)((Node)event.getSource()).getScene().getWindow();
            scene=new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @FXML
    void history(ActionEvent event) {
        try {
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "326910");
            pst=con.prepareStatement("SELECT balance FROM statement WHERE accno=?");
            pst.setLong(1, acc);
            rst=pst.executeQuery();
            if (rst.next()) {
                balance=rst.getLong("balance");
            }
            FXMLLoader loader=new FXMLLoader(getClass().getResource("history.fxml"));
            root=loader.load();
            historycontroller hc=loader.getController();
            hc.getbal(balance,acc);
            stage=(Stage)((Node)event.getSource()).getScene().getWindow();
            scene=new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @FXML
    void logout(ActionEvent event) {
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("signin.fxml"));
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
    void transfer(ActionEvent event) {
        try {
            //System.out.println("kdjfhksj");
            FXMLLoader loader=new FXMLLoader(getClass().getResource("transfer.fxml"));
            root=loader.load();
            transfercontroller trc=loader.getController();
            trc.gettranc(acc,passw);
            stage=(Stage)((Node)event.getSource()).getScene().getWindow();
            scene=new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @FXML
    void withdraw(ActionEvent event) {
         try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("withdraw.fxml"));
            root=loader.load();
            withdrawcontroller wc=new withdrawcontroller();
            wc.getacpass(passw,acc);
            stage=(Stage)((Node)event.getSource()).getScene().getWindow();
            scene=new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
   
}
