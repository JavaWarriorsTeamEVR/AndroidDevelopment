package com.manriqueweb.evrjiraaccess.Services;

import android.util.Log;

import com.manriqueweb.evrjiraaccess.Utils.Constants;
import com.manriqueweb.evrjiraaccess.beans.IssueBean;
import com.manriqueweb.evrjiraaccess.beans.IssueFilterBean;

import java.util.List;

import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.IssueType;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;
import net.rcarz.jiraclient.Project;
import net.rcarz.jiraclient.User;
import net.rcarz.jiraclient.WorkLog;
import net.sf.json.JSONObject;
import net.rcarz.jiraclient.Field;

import org.joda.time.DateTime;

/**
 * Created by omar on 7/05/17.
 */

public class JIraClientAccessService {
    private static final JIraClientAccessService ourInstance = new JIraClientAccessService();

    private static final String LOG_TAG = Constants.STR_LOG_TAG.concat("JIraClientAccessService");
    private static final boolean isDebbud = Constants.isDebbud;

    public static JIraClientAccessService getInstance() {
        return ourInstance;
    }

    private JIraClientAccessService() {
    }

    public List<IssueBean> searchIssues(IssueFilterBean issueFilterBean){
        List<IssueBean> response = null;

        try {
            StringBuffer urlJira = new StringBuffer();
            BasicCredentials creds = new BasicCredentials("admin", "papiChulo2017");
            JiraClient jira = new JiraClient("https://javawarriorsteamevr.atlassian.net", creds);

            StringBuffer strQuery = new StringBuffer();
            strQuery.append("assignee=");
            strQuery.append("admin"); //TODO cambiar a everisSITE
            strQuery.append(" and status = Open");

            Issue.SearchResult sr = jira.searchIssues(strQuery.toString());
            if(sr!=null){
                Log.d(LOG_TAG, "sr.total: ");
                Log.d(LOG_TAG, "Total: " + sr.total);
                for (Issue issue : sr.issues){
                    Log.d(LOG_TAG, "Result: " + issue);
                    Log.d(LOG_TAG, ", getSummary: " + issue.getSummary());
                    Log.d(LOG_TAG, ", status: " + issue);
                    Log.d(LOG_TAG, ", getStatus: " + issue.getStatus());

                    if(issue.getWorkLogs()!=null){
                        Log.d(LOG_TAG, "issue.getWorkLogs().size(): ");
                        Log.d(LOG_TAG, String.valueOf(issue.getWorkLogs().size()));

                        for(WorkLog item : issue.getWorkLogs()){
                            Log.d(LOG_TAG, "item: ");
                            Log.d(LOG_TAG, item.getComment());

                            Log.d(LOG_TAG, ", item.toString(): ");
                            Log.d(LOG_TAG, item.toString());
                        }
                    }

                    if(issue.getAllWorkLogs()!=null){
                        Log.d(LOG_TAG, "issue.getAllWorkLogs().size(): ");
                        Log.d(LOG_TAG, String.valueOf( issue.getAllWorkLogs().size()));

                        for(WorkLog item : issue.getAllWorkLogs()){
                            Log.d(LOG_TAG, "item: ");
                            Log.d(LOG_TAG, item.getComment());

                            Log.d(LOG_TAG, ", item.toString(): ");
                            Log.d(LOG_TAG, item.toString());

                        }
                        

                        /*
                        User user = issue.getAssignee();
                        
                        WorkLog newWorkLog = (WorkLog) new WorkLogBean();
                        newWorkLog.set
                       
                        
                        /* Assign the issue */

                    }

                    DateTime dateTime = new DateTime();

                    dateTime.now();

                    issue.addWorkLog("SECOVIVO IV from android", dateTime, (long) (60*60*0.25));
                    issue.update();

                    issue.refresh();
                }
            }

        } catch (JiraException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }

        return response;
    }

}
