package com.manriqueweb.evrjiraaccess.Beans;

import java.io.Serializable;

/**
 * Created by omar on 7/05/17.
 */

public class IssueFilterBean extends JiraCredentials implements Serializable {
    private static final long serialVersionUID = 8799656478674716638L;

    private String query;

    public IssueFilterBean() {
        super();
    }

    public IssueFilterBean(String query) {
        super();
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("IssueFilterBean{");
        sb.append("query='").append(query).append('\'');
        sb.append(", getJiraurl()='").append(getJiraurl()).append('\'');
        sb.append(", getUsername='").append(getUsername()).append('\'');
        sb.append(", getPassword()='").append(getPassword()).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
