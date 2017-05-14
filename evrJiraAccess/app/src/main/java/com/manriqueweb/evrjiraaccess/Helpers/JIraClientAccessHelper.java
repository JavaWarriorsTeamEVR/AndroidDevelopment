package com.manriqueweb.evrjiraaccess.Helpers;

import android.util.Log;

import com.manriqueweb.evrjiraaccess.Beans.IssueSaveWorkLogBean;
import com.manriqueweb.evrjiraaccess.Utils.Constants;
import com.manriqueweb.evrjiraaccess.Beans.IssueBean;
import com.manriqueweb.evrjiraaccess.Beans.IssueFilterBean;

import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by omar on 7/05/17.
 */

public class JIraClientAccessHelper {
    private static final JIraClientAccessHelper ourInstance = new JIraClientAccessHelper();

    private static final String LOG_TAG = Constants.STR_LOG_TAG.concat("JIraClientAccessHelper");
    private static final boolean isDebbud = Constants.isDebbud;

    public static JIraClientAccessHelper getInstance() {
        return ourInstance;
    }

    private JIraClientAccessHelper() {
    }

    public List<IssueBean> searchIssues(IssueFilterBean issueFilterBean){
        List<IssueBean> response = null;
        if (isDebbud) {
            StringBuffer msgLog = new StringBuffer();
            msgLog.append("issueFilterBean: ");
            msgLog.append(issueFilterBean.toString());
            Log.d(LOG_TAG, msgLog.toString());
        }

        try {
            StringBuffer urlJira = new StringBuffer();
            BasicCredentials creds = new BasicCredentials(issueFilterBean.getUsername(), issueFilterBean.getPassword());
            JiraClient jira = new JiraClient(issueFilterBean.getJiraurl(), creds);

            Issue.SearchResult sr = jira.searchIssues(issueFilterBean.getQuery());
            if(sr!=null){
                if (isDebbud) {
                    StringBuffer msgLog = new StringBuffer();
                    msgLog.append("sr.total: ");
                    msgLog.append("Total: ");
                    msgLog.append(sr.total);
                    Log.d(LOG_TAG, msgLog.toString());
                }
                response = new ArrayList<IssueBean>();
                for (Issue issue : sr.issues){
                    IssueBean issueBean = new IssueBean(
                            issue.getId(),
                            issue.getKey(),
                            issue.getSummary(),
                            issue.getDescription(),
                            issue.getStatus().getDescription(),
                            new DateTime(issue.getCreatedDate())
                    );

                    response.add(issueBean);

                    if (isDebbud) {
                        StringBuffer msgLog = new StringBuffer();
                        msgLog.append("getId(): ");
                        msgLog.append(issue.getId());
                        msgLog.append(", getDescription: ");
                        msgLog.append(issue.getDescription());
                        msgLog.append(", getSummary: ");
                        msgLog.append(issue.getSummary());
                        msgLog.append(", getStatus: ");
                        msgLog.append(issue.getStatus());
                        msgLog.append(", getCreatedDate: ");
                        msgLog.append(issue.getCreatedDate());
                        msgLog.append(", getKey: ");
                        msgLog.append(issue.getKey());
                        msgLog.append(", issueBean: ");
                        msgLog.append(issueBean.toString());
                        Log.d(LOG_TAG, msgLog.toString());
                    }
                }
            }

        } catch (JiraException ex) {
            Log.e(LOG_TAG, ex.getMessage(), ex);
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }

        return response;
    }

    public Boolean saveWorkLog(IssueSaveWorkLogBean issueSaveWorkLogBean){
        Boolean response = false;
        if (isDebbud) {
            StringBuffer msgLog = new StringBuffer();
            msgLog.append("issueSaveWorkLogBean: ");
            msgLog.append(issueSaveWorkLogBean.toString());
            Log.d(LOG_TAG, msgLog.toString());
        }

        try {
            BasicCredentials creds = new BasicCredentials(issueSaveWorkLogBean.getUsername(), issueSaveWorkLogBean.getPassword());
            JiraClient jira = new JiraClient(issueSaveWorkLogBean.getJiraurl(), creds);

            Issue issue = jira.getIssue(issueSaveWorkLogBean.getIssueKey());

            issue.addWorkLog(
                    issueSaveWorkLogBean.getLogdescription(),
                    issueSaveWorkLogBean.getTheDate(),
                    (long) getTimeOnSeconds(issueSaveWorkLogBean.getHours(), issueSaveWorkLogBean.getMinutes())
            );
            issue.update();

            response = true;

        } catch (JiraException ex) {
            Log.e(LOG_TAG, ex.getMessage(), ex);
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }
        return response;
    }

    private float getTimeOnSeconds(int hours, int minutes){
        float response = 0;
        float secondsByhour = 3600;
        float fMinute = 0;
        float longHours = (float) hours;

        if(minutes==1)
            fMinute = (float) .25;
        else if(minutes==2)
            fMinute = (float) .5;
        else if(minutes==3)
            fMinute = (float) .75;

        if (isDebbud) {
            StringBuffer msgLog = new StringBuffer();
            msgLog.append("hours: ");
            msgLog.append(hours);
            msgLog.append(", minutes: ");
            msgLog.append(minutes);
            msgLog.append(", longHours: ");
            msgLog.append(longHours);
            msgLog.append(", fMinute: ");
            msgLog.append(fMinute);
            Log.d(LOG_TAG, msgLog.toString());
        }

        response = secondsByhour * (longHours + fMinute);

        if (isDebbud) {
            StringBuffer msgLog = new StringBuffer();
            msgLog.append("response: ");
            msgLog.append(response);
            Log.d(LOG_TAG, msgLog.toString());
        }
        return response;
    }

}
