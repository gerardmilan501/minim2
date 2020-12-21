package edu.upc.dsa.firefighteradventure.models;

public class RegisterCredentials {

    String username;
    String password;
    String email;
    String birthdate;

    public RegisterCredentials(String username, String password, String email, String birthdate) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.birthdate = birthdate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

}