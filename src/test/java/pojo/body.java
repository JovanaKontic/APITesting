package pojo;

public class body {
    public String userName;
    public String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        if(password==null) {
            return "123!@#Aaa";
        }
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
