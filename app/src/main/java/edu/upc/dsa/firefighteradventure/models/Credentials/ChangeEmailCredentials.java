package edu.upc.dsa.firefighteradventure.models.Credentials;

public class ChangeEmailCredentials {

    private String username;
    private String password;
    private String newEmail;

    public ChangeEmailCredentials(String username, String password, String newEmail) {
        this.username = username;
        this.password = password;
        this.newEmail = newEmail;
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

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newPassword) {
        this.newEmail = newPassword;
    }

}
