package com.manriqueweb.evrjiraaccess.Beans;

/**
 * Created by omar on 14/05/17.
 */

public class JiraCredentials {
    private String jiraurl;
    private String username;
    private String password;

    public JiraCredentials() {
        this.jiraurl = null;
        this.username = null;
        this.password = null;
    }

    public JiraCredentials(String jiraurl, String username, String password) {
        this.jiraurl = jiraurl;
        this.username = username;
        this.password = password;
    }

    public String getJiraurl() {
        return jiraurl;
    }

    public void setJiraurl(String jiraurl) {
        this.jiraurl = jiraurl;
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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("JiraCredentials{");
        sb.append("jiraurl='").append(jiraurl).append('\'');
        sb.append(", username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
