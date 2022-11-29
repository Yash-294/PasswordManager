import javafx.beans.property.*;
public class Records {
    private SimpleStringProperty Account;
    private SimpleStringProperty Password;
    public Records(String u,String p){
        this.Account=new SimpleStringProperty(u);
        this.Password=new SimpleStringProperty(p);
    }
    public final StringProperty AccountProperty(){
        return Account;
    }
    public final StringProperty PasswordProperty(){
        return Password;
    }
}
