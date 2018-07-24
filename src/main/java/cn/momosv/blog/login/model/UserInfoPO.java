package cn.momosv.blog.login.model;

import java.io.Serializable;

public class UserInfoPO implements Serializable {
    private String id;
    private String userName;
    private String passwd;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
