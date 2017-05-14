package com.manriqueweb.evrjiraaccess.Beans;

import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * Created by omar on 14/05/17.
 */

public class IssueSaveWorkLogBean  extends JiraCredentials  implements Serializable {
    private static final long serialVersionUID = -6135122857758550568L;

    private String issueKey;
    private String logdescription;
    private DateTime theDate;
    private int hours;
    private int minutes;

    public IssueSaveWorkLogBean(String issueKey, String logdescription, DateTime theDate, int hours, int minutes) {
        this.issueKey = issueKey;
        this.logdescription = logdescription;
        this.theDate = theDate;
        this.hours = hours;
        this.minutes = minutes;
    }

    public String getIssueKey() {
        return issueKey;
    }

    public void setIssueKey(String issueKey) {
        this.issueKey = issueKey;
    }

    public String getLogdescription() {
        return logdescription;
    }

    public void setLogdescription(String logdescription) {
        this.logdescription = logdescription;
    }

    public DateTime getTheDate() {
        return theDate;
    }

    public void setTheDate(DateTime theDate) {
        this.theDate = theDate;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("IssueSaveWorkLogBean{");
        sb.append("issueKey='").append(issueKey).append('\'');
        sb.append(", logdescription='").append(logdescription).append('\'');
        sb.append(", theDate=").append(theDate);
        sb.append(", hours=").append(hours);
        sb.append(", minutes=").append(minutes);
        sb.append(", getJiraurl()='").append(getJiraurl()).append('\'');
        sb.append(", getUsername='").append(getUsername()).append('\'');
        sb.append(", getPassword()='").append(getPassword()).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
