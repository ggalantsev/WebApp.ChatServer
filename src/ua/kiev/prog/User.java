package ua.kiev.prog;

import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Calendar;

public class User implements Serializable{

    private String name;
    private String pass;
    private HttpSession session;

    public User(String name, String pass, HttpSession session) {
        this.name = name;
        this.pass = pass;
        this.session = session;
    }

    public boolean checkPass(String pass){
        return this.pass.equals(pass);
    }

    public boolean isOnline(){
        if (Calendar.getInstance().getTimeInMillis()-this.session.getLastAccessedTime()<120000){ // 2 min delay
            return true;
        } return false;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public String getName() {
        return name;
    }
}
