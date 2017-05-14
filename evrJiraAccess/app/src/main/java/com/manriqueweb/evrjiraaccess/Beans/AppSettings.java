package com.manriqueweb.evrjiraaccess.Beans;

import java.io.Serializable;

/**
 * Created by omar on 14/05/17.
 */

public class AppSettings implements Serializable {
    private static final long serialVersionUID = -5035122857758550568L;

    private String username;
    private String password;
    private String jiraurl;
    private String qstring;

    public AppSettings(String username, String password, String jiraurl, String qstring) {
        this.username = username;
        this.password = password;
        this.jiraurl = jiraurl;
        this.qstring = qstring;
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

    public String getJiraurl() {
        return jiraurl;
    }

    public void setJiraurl(String jiraurl) {
        this.jiraurl = jiraurl;
    }

    public String getQstring() {
        return qstring;
    }

    public void setQstring(String qstring) {
        this.qstring = qstring;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AppSettings{");
        sb.append("username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", jiraurl='").append(jiraurl).append('\'');
        sb.append(", qstring='").append(qstring).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
