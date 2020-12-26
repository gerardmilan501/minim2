package edu.upc.dsa.firefighteradventure.models;

public class User {

    private String id;
    private String username;
    private String pwd;
    private String email;
    private String birthdate;
    private int score;

    public User(String id, String username, String pwd, String email, String birthdate, int score) {
        this.id = id;
        this.username = username;
        this.pwd = pwd;
        this.email = email;
        this.birthdate = birthdate;
        this.score = score;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
