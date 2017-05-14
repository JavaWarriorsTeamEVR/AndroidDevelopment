package com.manriqueweb.evrjiraaccess.Screens;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.manriqueweb.evrjiraaccess.Beans.AppSettings;
import com.manriqueweb.evrjiraaccess.Helpers.PreferenceManagerHelper;
import com.manriqueweb.evrjiraaccess.R;
import com.manriqueweb.evrjiraaccess.Utils.AsyncTaskMW;
import com.manriqueweb.evrjiraaccess.Utils.Constants;

import java.util.List;

import static com.manriqueweb.evrjiraaccess.Utils.Constants.isDebbud;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String LOG_TAG = Constants.STR_LOG_TAG.concat("SettingsActivity");

    private LinearLayout llWait = null;
    private ProgressBar pbwait = null;

    private EditText edt_username, edt_pass, edt_url, edt_qstring;
    private Button btn_save;

    private AppSettings appSettings = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        llWait = (LinearLayout) findViewById(R.id.llWait);
        llWait.setVisibility(View.GONE);

        edt_username = (EditText) findViewById(R.id.edt_username);
        edt_pass = (EditText) findViewById(R.id.edt_pass);
        edt_url = (EditText) findViewById(R.id.edt_url);
        edt_qstring = (EditText) findViewById(R.id.edt_qstring);

        btn_save = (Button) findViewById(R.id.btn_save);
        btn_save.setOnClickListener(this);

        getSettings();
    }

    private void getSettings(){
        setWait(true);

        new AsyncTaskMW<Context, AppSettings, String>(getApplicationContext(), Constants.STR_NOTAVAILABLE){

            @Override
            protected AppSettings process(Context input) {
                String val1 = PreferenceManagerHelper.getInstance(input).getPreferenceValue(Constants.STR_PREF_USR);
                String val2 = PreferenceManagerHelper.getInstance(input).getPreferenceValue(Constants.STR_PREF_PAS);
                String val3 = PreferenceManagerHelper.getInstance(input).getPreferenceValue(Constants.STR_PREF_URL);
                String val4 = PreferenceManagerHelper.getInstance(input).getPreferenceValue(Constants.STR_PREF_QST);

                return new AppSettings(val1, val2, val3, val4);
            }

            @Override
            protected void applyOutputToTarget(AppSettings output, String target) {
                if (isDebbud) {
                    StringBuffer msgLog = new StringBuffer("applyOutputToTarget");
                    Log.d(LOG_TAG, msgLog.toString());
                }

                appSettings = output;
                showVariables(false);

            }
        }.execute();

    }

    private void saveSettings(){
        setWait(true);

        appSettings.setUsername(edt_username.getText().toString());
        appSettings.setPassword(edt_pass.getText().toString());
        appSettings.setJiraurl(edt_url.getText().toString());
        appSettings.setQstring(edt_qstring.getText().toString());

        new AsyncTaskMW<Context, AppSettings, String>(getApplicationContext(), Constants.STR_NOTAVAILABLE){

            @Override
            protected AppSettings process(Context input) {
                StringBuffer msgLog = new StringBuffer("process: ");
                msgLog.append(" appSettings: ");
                msgLog.append(appSettings.toString());
                Log.d(LOG_TAG, appSettings.toString());

                PreferenceManagerHelper.getInstance(input).updatePreferenceValue(Constants.STR_PREF_USR, appSettings.getUsername());
                PreferenceManagerHelper.getInstance(input).updatePreferenceValue(Constants.STR_PREF_PAS, appSettings.getPassword());
                PreferenceManagerHelper.getInstance(input).updatePreferenceValue(Constants.STR_PREF_URL, appSettings.getJiraurl());
                PreferenceManagerHelper.getInstance(input).updatePreferenceValue(Constants.STR_PREF_QST, appSettings.getQstring());

                return appSettings;
            }

            @Override
            protected void applyOutputToTarget(AppSettings output, String target) {
                if (isDebbud) {
                    StringBuffer msgLog = new StringBuffer("applyOutputToTarget");
                    Log.d(LOG_TAG, msgLog.toString());
                }

                appSettings = output;
                showVariables(true);

            }
        }.execute();

    }

    private void showVariables(boolean isUpdate){
        setWait(false);

        if(appSettings!=null){
            edt_username.setText((appSettings.getUsername()==null || appSettings.getUsername().equals(Constants.STR_NOTAVAILABLE)?Constants.STR_EMPTY:appSettings.getUsername()));
            edt_pass.setText((appSettings.getPassword()==null || appSettings.getPassword().equals(Constants.STR_NOTAVAILABLE)?Constants.STR_EMPTY:appSettings.getPassword()));
            edt_url.setText((appSettings.getJiraurl()==null || appSettings.getJiraurl().equals(Constants.STR_NOTAVAILABLE)?Constants.STR_EMPTY:appSettings.getJiraurl()));
            edt_qstring.setText((appSettings.getQstring()==null || appSettings.getQstring().equals(Constants.STR_NOTAVAILABLE)?getString(R.string.set_querystringini):appSettings.getQstring()));
        }

        if(isUpdate){
            Toast.makeText(this, getString(R.string.msg_settingsgood), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                saveSettings();
                break;
            default:
                break;
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
