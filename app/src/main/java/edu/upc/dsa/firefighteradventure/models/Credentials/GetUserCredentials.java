package edu.upc.dsa.firefighteradventure.models.Credentials;

public class GetUserCredentials {

    private String username;

    public GetUserCredentials(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

}
