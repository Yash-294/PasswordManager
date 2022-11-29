import javafx.application.Application;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.sql.*;

import javax.xml.namespace.QName;


public class PassManager2 extends Application{
    //Inserting password to Database
    public static boolean insertPass(String uname,String pass){
        String query;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbcConnection","root","root");
            Statement stmt=con.createStatement();
            query="SELECT ACCOUNT_IN FROM PASSWORDS WHERE ACCOUNT_IN LIKE '"+uname+"';";
            ResultSet r=stmt.executeQuery(query);
            if(r.next()==false){
                query="INSERT INTO PASSWORDS(ACCOUNT_IN,PASSWORD) VALUES('"+uname+"','"+pass+"');";
                stmt.executeUpdate(query);
                con.close();
                return true;
            }
            con.close();
            return false;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }

    }
    public static void updatePass(String uname,String pass){
        String query;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbcConnection","root","root");
            Statement stmt=con.createStatement();
            query="UPDATE PASSWORDS SET PASSWORD='"+pass+"' WHERE ACCOUNT_IN LIKE '"+uname+"';";
            stmt.executeUpdate(query);
            con.close();
            return;
        }
        catch(Exception e){
            e.printStackTrace();
            return;
        }
    }
    public static int getCount(){
        String query;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbcConnection","root","root");
            Statement stmt=con.createStatement();
            query="SELECT COUNT(1) FROM PASSWORDS;";
            ResultSet r=stmt.executeQuery(query);
            while(r.next()){
                return r.getInt(1);
            }
            return -1;
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }
    public static String[] fetchAccounts(){
        String query;
        String[] s=new String[getCount()];
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbcConncetion","root","root");
            Statement stmt=con.createStatement();
            query="SELECT ACCOUNT_IN FROM PASSWORDS ORDER BY ACCOUNT_IN;";
            ResultSet r=stmt.executeQuery(query);
            int i=0;
            while(r.next()){
                s[i]=r.getString(1);
                i++;
            }
            return s;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public static String[] fetchPass(){
        String query;
        String[] s=new String[getCount()];
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbcConnection","root","root");
            Statement stmt=con.createStatement();
            query="SELECT PASSWORD FROM PASSWORDS;";
            ResultSet r=stmt.executeQuery(query);
            int i=0;
            while(r.next()){
                s[i]=r.getString(1);
                i++;
            }
            return s;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public void start(Stage st)throws Exception{
        Insets in1=new Insets(150,0,15,200);
        Insets in2=new Insets(0,0,15,250);
        String error="-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 5;";
        String success="-fx-border-color: #A9A9A9; -fx-border-width: 2; -fx-border-radius: 5;";
        //Menu
        Text t=new Text();
        t.setText("Password Manager");
        t.setFont(Font.font("Cambria",32));
        Button i=new Button("Add Passwords");
        Button s=new Button("Show Passwords");
        Button u=new Button("Update Passwords");
        VBox v1=new VBox();
        VBox.setMargin(t,in1);
        VBox.setMargin(i,in2);
        VBox.setMargin(s,in2);
        VBox.setMargin(u,in2);
        Scene s1=new Scene(v1,600,600);
        v1.setBackground(new Background(new BackgroundFill(new RadialGradient(0,0,0,0,1,true,CycleMethod.NO_CYCLE,new Stop(0,Color.web("81c483")),new Stop(1,Color.web("#fcc200"))),CornerRadii.EMPTY,Insets.EMPTY)));
        v1.getChildren().add(t);
        v1.getChildren().add(i);
        v1.getChildren().add(s);
        v1.getChildren().add(u);
        //Insert Passwords
        GridPane g1=new GridPane();
        Scene s2=new Scene(g1,600,600);
        Label l1=new Label();
        TextField tf1=new TextField();
        TextField tf2=new TextField();
        Button b1=new Button("Back");
        Button ss1=new Button("Store in vault");
        tf1.setPromptText("Account Name");
        tf2.setPromptText("Password");
        tf1.setFocusTraversable(false);
        tf2.setFocusTraversable(false);
        g1.setBackground(new Background(new BackgroundFill(new RadialGradient(0,0,0,0,1,true,CycleMethod.NO_CYCLE,new Stop(0,Color.web("81c483")),new Stop(1,Color.web("#fcc200"))),CornerRadii.EMPTY,Insets.EMPTY)));
        g1.addRow(0,tf1);
        g1.addRow(1,tf2);
        g1.addRow(2,l1);
        g1.addRow(3,ss1);
        g1.addRow(4,b1);
        GridPane.setMargin(tf1,new Insets(150,0,15,250));
        GridPane.setMargin(tf2,in2);
        GridPane.setMargin(l1,new Insets(0,0,0,250));
        GridPane.setMargin(ss1,in2);
        GridPane.setMargin(b1,in2);
        //Show Passwords
        VBox v2=new VBox();
        Scene s3=new Scene(v2,600,600);
        Button b2=new Button("Back");
        TableView<Records> tb=new TableView<>();
        tb.setEditable(true);
        TableColumn<Records,String> c1=new TableColumn<>("Account");
        TableColumn<Records,String> c2=new TableColumn<>("Password");
        tb.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tb.getColumns().addAll(c1,c2);
        v2.setBackground(new Background(new BackgroundFill(new RadialGradient(0,0,0,0,1,true,CycleMethod.NO_CYCLE,new Stop(0,Color.web("81c483")),new Stop(1,Color.web("#fcc200"))),CornerRadii.EMPTY,Insets.EMPTY)));
        c1.setCellValueFactory(new PropertyValueFactory<Records,String>("Account"));
        c2.setCellValueFactory(new PropertyValueFactory<Records,String>("Password"));
        tb.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        v2.setSpacing(5);
        v2.getChildren().add(tb);
        v2.getChildren().add(b2);
        //Update Passwords
        GridPane g2=new GridPane();
        Scene s4=new Scene(g2,600,600);
        Label l4=new Label();
        TextField tf4=new TextField();
        TextField tf5=new TextField();
        Button up=new Button("Update Password");
        Button b3=new Button("Back");
        g2.setBackground(new Background(new BackgroundFill(new RadialGradient(0,0,0,0,1,true,CycleMethod.NO_CYCLE,new Stop(0,Color.web("81c483")),new Stop(1,Color.web("#fcc200"))),CornerRadii.EMPTY,Insets.EMPTY)));
        tf4.setPromptText("Account Name");
        tf5.setPromptText("New Password");
        tf4.setFocusTraversable(false);
        tf5.setFocusTraversable(false);
        g2.addRow(0,tf4);
        g2.addRow(1,tf5);
        g2.addRow(2,l4);
        g2.addRow(3,up);
        g2.addRow(4,b3);
        GridPane.setMargin(tf4,new Insets(150,0,15,250));
        GridPane.setMargin(tf5,in2);
        GridPane.setMargin(l4,new Insets(0,0,0,250));
        GridPane.setMargin(up,in2);
        GridPane.setMargin(b3,in2);
        //Button Actions
        i.setOnAction(e->st.setScene(s2));
        b1.setOnAction(e->st.setScene(s1));
        b2.setOnAction(e->st.setScene(s1));
        b3.setOnAction(e->st.setScene(s1));
        u.setOnAction(e->st.setScene(s4));
        s.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
                int n=getCount();
                String[] u=fetchAccounts();
                String[] p=fetchPass();
                ObservableList<Records> data=FXCollections.observableArrayList();
                int i=0;
                while(i<n){
                    Records r=new Records(u[i],p[i]);
                    i++;
                    data.add(r);
                }
                tb.setItems(data);
                st.setScene(s3);
            }
        });
        ss1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
                String pfname;
                String pass;
                tf1.setStyle(success);
                tf2.setStyle(success);
                pfname=tf1.getText();
                pass=tf2.getText();
                if((pfname.equals("")) && (pass.equals(""))){
                    l1.setText("Empty Field 'Account Name' & 'Password'");
                    l1.setStyle("-fx-text-fill: RED;");
                    tf1.setStyle(error);
                    tf2.setStyle(error);
                    return;
                }
                else{
                    if(pfname.equals("") ){
                        l1.setText("Empty Field 'Account Name'");
                        l1.setStyle("-fx-text-fill: RED;");
                        tf1.setStyle(error);
                        return;
                    }
                    else{
                        if(pass.equals("")){
                            l1.setText("Empty Field 'Password'");
                            l1.setStyle("-fx-text-fill: RED;");
                            tf2.setStyle(error);
                            return;
                        }
                    }
                }
                if(insertPass(pfname, pass)){
                    l1.setText("Password added successfully");
                    l1.setStyle("-fx-text-fill: GREEN;");
                    tf1.setStyle(success);
                    tf2.setStyle(success);
                    return;
                }
                else{
                    l1.setText("Account information already present!! Update password instead");
                    l1.setStyle("-fx-text-fill: RED;");
                    return;
                }
            }
        });
        up.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                String pfname;
                String pass;
                tf4.setStyle(success);
                tf5.setStyle(success);
                pfname=tf4.getText();
                pass=tf5.getText();
                if((pfname.equals("")) && (pass.equals(""))){
                    l4.setText("Empty Field 'Account Name' & 'Password'");
                    l4.setStyle("-fx-text-fill: RED;");
                    tf4.setStyle(error);
                    tf5.setStyle(error);
                    return;
                }
                else{
                    if(pfname.equals("") ){
                        l4.setText("Empty Field 'Account Name'");
                        l4.setStyle("-fx-text-fill: RED;");
                        tf4.setStyle(error);
                        return;
                    }
                    else{
                        if(pass.equals("")){
                            l4.setText("Empty Field 'Password'");
                            l4.setStyle("-fx-text-fill: RED;");
                            tf5.setStyle(error);
                            return;
                        }
                    }
                }
                if(insertPass(pfname, pass)){
                    l4.setText("Password added successfully");
                    l4.setStyle("-fx-text-fill: GREEN;");
                    tf4.setStyle(success);
                    tf5.setStyle(success);
                    return;
                }
                else{
                    l4.setText("Password Updated Successfully");
                    l4.setStyle("-fx-text-fill: GREEN;");
                    tf4.setStyle(success);
                    tf5.setStyle(success);
                    return;
                }
            }
        });
        st.setScene(s1);
        st.setTitle("Password Manager");
        st.show();
    }
    public static void main(String[] args){
        launch(args);
    }
}