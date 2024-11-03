
import java.beans.Statement;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Random;



// import java.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
// import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class signupcontroller {

    @FXML
    private PasswordField cpassword;

    @FXML
    private Label accno;

    @FXML
    private DatePicker dob;

    @FXML
    private RadioButton female;

    @FXML
    private RadioButton male;

    @FXML
    private TextField name;

    @FXML
    private PasswordField password;

    @FXML
    private TextField phno;

    @FXML
    private ToggleGroup togg;

    @FXML
    private Label warning;

    @FXML
    private Label phonwarn;

    @FXML
    private Label notmatch;

    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    void redsignin(ActionEvent event) {
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

    public class DatabaseConnector {
        private static final String URL = "jdbc:mysql://localhost:3306/bank";
        private static final String USERNAME = "root";
        private static final String PASSWORD = "326910";

        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        
    }

    @FXML
    void signup(ActionEvent event) {
        // int genacc;
        // Random r=new Random();
        // genacc=r. nextInt(99999999, 1000000000);
        // System.out.println(genacc);
       

     try {
            String uname=name.getText();
            LocalDate udob=dob.getValue();

            String um=male.getText();
            String uf=female.getText();
            String pwd=password.getText();
            String cpwd=cpassword.getText();
            try {
                long pno =Long.parseLong(phno.getText());
                

            } catch (NumberFormatException e) {
                phonwarn.setText("Enter only phone number");
            // TODO: handle exception
            }
            
           

            if (uname.isEmpty() || udob==null || (male.isSelected()==false && female.isSelected()==false) ||pwd.isEmpty() || cpwd.isEmpty()) {
                warning.setText("Plese fill all the fields ");
            }
            else{
                    if (pwd.equals(cpwd)) {
                        long genacc;
                        Random r=new Random();
                        genacc=r.nextLong(99999999, 1000000000);
                        String ac=Long.toString(genacc);
                        accno.setText("Your Account no: "+ac);
                        long po =Long.parseLong(phno.getText());
                        if(male.isSelected()==false)
                            DataInsertion.insertUser(uname, udob, uf, po, pwd,genacc);
                        else
                            DataInsertion.insertUser(uname, udob, um, po, pwd,genacc);
                        
                        
                        name.setText(null);
                        dob.setValue(null);
                        //male.disableProperty();
                        phno.setText(null);
                        password.setText(null);
                        cpassword.setText(null);
                        return;
                        
                        
                    } else {
                        notmatch.setText("Password not matching");
                    }
                   
                }

        } catch (Exception e) {
        
        // TODO: handle exception
        }
    }

    



    public class DataInsertion {
        public static void insertUser(String name, LocalDate dob,String gender,long phoneNumber,String password,long accno) {
            String sql1 = "INSERT INTO user (name, dob, gender, phno, password, accno) VALUES (?, ?, ?, ?, ?, ?)";
            String sql2="INSERT INTO statement (accno,balance) VALUES(?,?)";
            try (Connection connection = DatabaseConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql1)) {
                statement.setString(1, name);
                statement.setDate(2, java.sql.Date.valueOf(dob));
                statement.setString(3, gender);
                statement.setLong(4, phoneNumber);
                statement.setString(5, password);
                statement.setLong(6, accno);
                statement.executeUpdate();
            } 
            catch (SQLException e) {
                e.printStackTrace();
            }

            try (Connection connection = DatabaseConnector.getConnection();
                PreparedStatement prst1 = connection.prepareStatement(sql2)) {
                prst1.setLong(1, accno);
                prst1.setLong(2, 0);
                prst1.executeUpdate();
                
            } 
            catch (SQLException e) {
                e.printStackTrace();
            }

        }
        
    }
    

}
