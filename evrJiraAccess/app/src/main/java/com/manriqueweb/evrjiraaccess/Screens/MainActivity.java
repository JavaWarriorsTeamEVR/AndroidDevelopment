package com.manriqueweb.evrjiraaccess.Screens;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.manriqueweb.evrjiraaccess.Adapters.IssueAdapter;
import com.manriqueweb.evrjiraaccess.Dialogs.AddWorkLogDialog;
import com.manriqueweb.evrjiraaccess.Helpers.JIraClientAccessHelper;
import com.manriqueweb.evrjiraaccess.Helpers.PreferenceManagerHelper;
import com.manriqueweb.evrjiraaccess.R;
import com.manriqueweb.evrjiraaccess.Utils.AsyncTaskMW;
import com.manriqueweb.evrjiraaccess.Utils.Constants;
import com.manriqueweb.evrjiraaccess.Beans.IssueBean;
import com.manriqueweb.evrjiraaccess.Beans.IssueFilterBean;

import java.util.List;

import static com.manriqueweb.evrjiraaccess.Utils.Constants.isDebbud;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = Constants.STR_LOG_TAG.concat("MainActivity");

    private LinearLayout llWait = null;
    private ProgressBar pbwait = null;
    private ListView lllvIssue = null;

    private TextView tvSettingsMSG = null;

    private IssueAdapter mIssueAdapter = null;

    private List<IssueBean> issueBeanList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llWait = (LinearLayout) findViewById(R.id.llWait);
        llWait.setVisibility(View.GONE);
        lllvIssue = (ListView) findViewById(R.id.lvIssue);
        lllvIssue.setVisibility(View.GONE);

        tvSettingsMSG = (TextView) findViewById(R.id.tvSettingsMSG);

        searchIssues();
    }


    private void searchIssues(){
        setWait(true);

        IssueFilterBean issueFilterBean = new IssueFilterBean();

        String val1 = PreferenceManagerHelper.getInstance(this).getPreferenceValue(Constants.STR_PREF_USR);
        String val2 = PreferenceManagerHelper.getInstance(this).getPreferenceValue(Constants.STR_PREF_PAS);
        String val3 = PreferenceManagerHelper.getInstance(this).getPreferenceValue(Constants.STR_PREF_URL);
        String val4 = PreferenceManagerHelper.getInstance(this).getPreferenceValue(Constants.STR_PREF_QST);

        if(
            (val1==null || val1.equals(Constants.STR_NOTAVAILABLE)) ||
            (val2==null || val2.equals(Constants.STR_NOTAVAILABLE)) ||
            (val3==null || val3.equals(Constants.STR_NOTAVAILABLE)) ||
            (val4==null || val4.equals(Constants.STR_NOTAVAILABLE))
        ){
            setWait(false);
            Toast.makeText(this, getString(R.string.msg_settingblank), Toast.LENGTH_LONG).show();
            setWait(false);
            llWait.setVisibility(View.VISIBLE);
            tvSettingsMSG.setVisibility(View.VISIBLE);
            return;
        }else{
            tvSettingsMSG.setVisibility(View.GONE);
        }

        issueFilterBean.setUsername(val1);
        issueFilterBean.setPassword(val2);
        issueFilterBean.setJiraurl(val3);
        issueFilterBean.setQuery(val4);

        new AsyncTaskMW<IssueFilterBean, List<IssueBean>, String>(issueFilterBean, Constants.STR_NOTAVAILABLE){

            @Override
            protected List<IssueBean> process(IssueFilterBean input) {
                StringBuffer msgLog = new StringBuffer("process: ");
                msgLog.append(input);
                Log.d(LOG_TAG, msgLog.toString());

                return JIraClientAccessHelper.getInstance().searchIssues(input);
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

    }

    private void showIssues(){
        mIssueAdapter = new IssueAdapter(getApplicationContext(), issueBeanList);

        lllvIssue.setVisibility(View.VISIBLE);

        lllvIssue.setAdapter(mIssueAdapter);
        lllvIssue.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IssueBean itemIssueBean = issueBeanList.get(position);

                if (isDebbud) {
                    StringBuffer msgLog = new StringBuffer();
                    msgLog.append("position: ");
                    msgLog.append(position);
                    msgLog.append(", id: ");
                    msgLog.append(id);
                    msgLog.append(", match: ");
                    msgLog.append(itemIssueBean.toString());
                    Log.d(LOG_TAG, msgLog.toString());
                }

                if(itemIssueBean!=null && itemIssueBean.getKeyissue()!=null){
                    callAddWorklogDialog(itemIssueBean);
                }

            }
        });

        setWait(false);
    }

    private void callAddWorklogDialog(IssueBean itemIssueBean){
        AddWorkLogDialog addWorkLogDialog = new AddWorkLogDialog(MainActivity.this, itemIssueBean);
        addWorkLogDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addWorkLogDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (isDebbud) Log.d(LOG_TAG, "requestCode: " + requestCode);
        if (isDebbud) Log.d(LOG_TAG, "resultCode:  " + resultCode);

        if(requestCode==Constants.INT_REQUESTCODE_SETTINGS){
            searchIssues();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_menu_settings:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivityForResult(intent, Constants.INT_REQUESTCODE_SETTINGS);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setWait(boolean isSet){
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

    private ProgressBar getProgressBar(){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(2, 2, 2, 2);

        ProgressBar response = new ProgressBar(this);
        response.setLayoutParams(params);

        return response;
    }
}
