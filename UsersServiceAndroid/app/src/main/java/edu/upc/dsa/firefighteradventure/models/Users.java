package edu.upc.dsa.firefighteradventure.models;

public class Users {

    private String id;
    private String username;
    private String pwd;

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPwd(String password) {
        this.pwd = password;
    }

    public String getId(){
        return id;
    }

    public String getUsername(){
        return username;
    }

    public String getPwd(){
        return pwd;
    }

}
