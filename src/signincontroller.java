
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

public class signincontroller {

    @FXML
    private TextField accountno;

    @FXML
    private PasswordField password;

    @FXML
    private Label warnl;

    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;
    Connection con;
    PreparedStatement pst;
    ResultSet rst;

    @FXML
    void redsignup(ActionEvent event) {
           try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("signup.fxml"));
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
    void signin(ActionEvent event) throws IOException, SQLException {
        String acc=accountno.getText();
        String pass=password.getText();

        if (acc.isEmpty() || pass.isEmpty()) {
            warnl.setText("Please enter correct details");
        } else {
            long accnum=Long.parseLong(acc);
            try {
                //Class.forName("com.mysql.cj.jdbc.Driver");
                con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "326910");
                pst=con.prepareStatement("SELECT accno,password,name FROM user WHERE accno = ? AND password = ?");
                pst.setLong(1, accnum);
                pst.setString(2, pass);
                

                rst=pst.executeQuery();
                

                if (rst.next()) {
                    
                    String myname=rst.getString("name");
                   
                    FXMLLoader loader=new FXMLLoader(getClass().getResource("dash.fxml"));
                    root=loader.load();
                    dashcontroller dc=loader.getController();
                    dc.getvalue(accnum,myname,pass);
                    // dashcontroller dco=new dashcontroller(accnum, myname, bal);
                    stage=(Stage)((Node)event.getSource()).getScene().getWindow();
                    scene=new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                   
                }else{
                    warnl.setText("Please enter correct details");
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            
        }
        
    }

}
