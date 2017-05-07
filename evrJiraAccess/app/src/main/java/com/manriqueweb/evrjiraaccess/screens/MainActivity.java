package com.manriqueweb.evrjiraaccess.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.manriqueweb.evrjiraaccess.R;
import com.manriqueweb.evrjiraaccess.Services.JIraClientAccessService;
import com.manriqueweb.evrjiraaccess.Utils.AsyncTaskMW;
import com.manriqueweb.evrjiraaccess.Utils.Constants;
import com.manriqueweb.evrjiraaccess.beans.IssueBean;
import com.manriqueweb.evrjiraaccess.beans.IssueFilterBean;

import java.util.List;

import static com.manriqueweb.evrjiraaccess.Utils.Constants.isDebbud;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = Constants.STR_LOG_TAG.concat("MainActivity");
    private static final boolean IS_DEBBUG = Constants.isDebbud;

    private LinearLayout llWait = null;
    protected ProgressBar pbwait = null;

    List<IssueBean> issueBeanList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llWait = (LinearLayout) findViewById(R.id.llWait);

        searchIssues();
    }


    private void searchIssues(){
        setWait(true);
        issueBeanList = JIraClientAccessService.getInstance().searchIssues(null);
        showIssues();

        /*

        new AsyncTaskMW<IssueFilterBean, List<IssueBean>, String>(new IssueFilterBean(), Constants.STR_NOTAVAILABLE){

            @Override
            protected List<IssueBean> process(IssueFilterBean input) {
                StringBuffer msgLog = new StringBuffer("process: ");
                msgLog.append(input);
                Log.d(LOG_TAG, msgLog.toString());

                return JIraClientAccessService.getInstance().searchIssues(input);
            }

            @Override
            protected void applyOutputToTarget(List<IssueBean> output, String target) {
                if (isDebbud) {
                    StringBuffer msgLog = new StringBuffer("applyOutputToTarget");
                    Log.d(LOG_TAG, msgLog.toString());
                }

                issueBeanList = output;

                showIssues();
            }
        }.execute();
        */

    }

    private void showIssues(){
        setWait(false);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (isDebbud) Log.d(LOG_TAG, "requestCode: " + requestCode);
        if (isDebbud) Log.d(LOG_TAG, "resultCode:  " + resultCode);

    }

    protected void setWait(boolean isSet){
        if(pbwait==null)
            pbwait = getProgressBar();

        if(isSet){
            pbwait.setVisibility(View.VISIBLE);
            llWait.addView(pbwait);
            llWait.setVisibility(View.VISIBLE);
        }else{
            pbwait.setVisibility(View.GONE);
            llWait.removeView(pbwait);
            llWait.setVisibility(View.GONE);
        }
        llWait.refreshDrawableState();
    }

    protected ProgressBar getProgressBar(){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(2, 2, 2, 2);

        ProgressBar response = new ProgressBar(this);
        response.setLayoutParams(params);

        return response;
    }
}
