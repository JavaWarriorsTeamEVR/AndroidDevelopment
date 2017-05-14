package com.manriqueweb.evrjiraaccess.Beans;

import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * Created by omar on 7/05/17.
 */

public class IssueBean implements Serializable {
    private static final long serialVersionUID = -7235122857758550568L;

    private String idissue;
    private String keyissue;
    private String sumary;
    private String description;
    private String status;
    private DateTime createdate;

    public IssueBean() {
        super();
    }

    public IssueBean(String idissue, String keyissue, String sumary, String description, String status, DateTime createdate) {
        this.idissue = idissue;
        this.keyissue = keyissue;
        this.sumary = sumary;
        this.description = description;
        this.status = status;
        this.createdate = createdate;
    }

    public String getIdissue() {
        return idissue;
    }

    public long getIdissueLong() {
        return ((this.idissue==null || this.idissue.length()==0) ? 0 : Long.getLong(this.idissue));
    }

    public void setIdissue(String idissue) {
        this.idissue = idissue;
    }

    public String getKeyissue() {
        return keyissue;
    }

    public void setKeyissue(String keyissue) {
        this.keyissue = keyissue;
    }

    public String getSumary() {
        return sumary;
    }

    public void setSumary(String sumary) {
        this.sumary = sumary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DateTime getCreatedate() {
        return createdate;
    }

    public void setCreatedate(DateTime createdate) {
        this.createdate = createdate;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("IssueBean{");
        sb.append("idissue='").append(idissue).append('\'');
        sb.append(", keyissue='").append(keyissue).append('\'');
        sb.append(", sumary='").append(sumary).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", createdate=").append(createdate);
        sb.append('}');
        return sb.toString();
    }
}
