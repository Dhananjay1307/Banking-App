import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class historycontroller {

    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;
    Connection con;
    PreparedStatement pst;
    ResultSet rst;

    @FXML
    private Label bal;

    @FXML
    private Label trns;

    public static String gbalance;
    public static long accn;


 

    // @Override
    // public void initialize(URL location, ResourceBundle resources) {
    //     details.setCellValueFactory(new PropertyValueFactory<getset,String>("trans"));
        
        
    //     //throw new UnsupportedOperationException("Unimplemented method 'initialize'");
    // }

    
    public void getbal(long balan,long acc) {
        gbalance=balan+"";
        bal.setText(gbalance);
        accn=acc;
        //System.out.println(accn);
    }


    @FXML
    void showtr(ActionEvent event){
        String det;
        String trd;
        try {
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "326910");
            pst=con.prepareStatement("SELECT accno,tdetails,trdate FROM users WHERE accno = ?");
            pst.setLong(1, accn);
        
            //System.out.println(accn);
            rst=pst.executeQuery();


            while (rst.next()) {
                det=rst.getString("tdetails");
                trd=rst.getString("trdate");
                // System.out.println(det);
                
                // for (int i = 1; i <=10; i++) {
                //     trns.setText(det+" "+i);
                //     //root.getChildrenUnmodifiable().add(trns);
                // }
                




            // getset gt=new getset(det);
            // System.out.println(det);
            // System.out.println(trd);
            // ObservableList<getset> lst=FXCollections.observableArrayList(
            //     new getset(det,trd)
            // );
            // getset gt=new getset(det,trd);
            
            // details.setCellValueFactory(new PropertyValueFactory<getset,String>("trans"));
            // trandate.setCellValueFactory(new PropertyValueFactory<getset,String>("trdate"));
            // table.setItems(lst);
            //System.out.println(rst.getString("transact"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
    }


    @FXML
    void back(ActionEvent event) {
        try {
            //System.out.println("hi");
            FXMLLoader loader=new FXMLLoader(getClass().getResource("dash.fxml"));
            root=loader.load();
            stage=(Stage)((Node)event.getSource()).getScene().getWindow();
            scene=new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }




}
