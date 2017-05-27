package com.manriqueweb.evrjiraaccess.Dialogs;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import com.manriqueweb.evrjiraaccess.Beans.IssueBean;
import com.manriqueweb.evrjiraaccess.Beans.IssueSaveWorkLogBean;
import com.manriqueweb.evrjiraaccess.Helpers.JIraClientAccessHelper;
import com.manriqueweb.evrjiraaccess.Helpers.PreferenceManagerHelper;
import com.manriqueweb.evrjiraaccess.R;
import com.manriqueweb.evrjiraaccess.Utils.AsyncTaskMW;
import com.manriqueweb.evrjiraaccess.Utils.Constants;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import static com.manriqueweb.evrjiraaccess.Utils.Constants.isDebbud;

/**
 * Created by omar on 12/05/17.
 */

public class AddWorkLogDialog extends Dialog implements View.OnClickListener {
    private static final String LOG_TAG = Constants.STR_LOG_TAG.concat("MainActivity");

    private LinearLayout llWait = null;
    private ProgressBar pbwait = null;

    private Activity activity;
    private Button btn_save, btn_cancel, btn_dateselect;
    private TextView txtIssueKey, txtIssueSummary,txt_dateselected;
    private NumberPicker numberHourTime, numberMinutTime;
    private EditText edt_worklog;

    private IssueBean itemIssueBean;
    private int mYear, mMonth, mDay;

    private final Calendar c = Calendar.getInstance();

    private String[] aminutes = null;

    public AddWorkLogDialog(Activity activity, IssueBean itemIssueBean) {
        super(activity);
        this.activity = activity;
        this.itemIssueBean = itemIssueBean;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add_worklog);

        llWait = (LinearLayout) findViewById(R.id.llWait);

        btn_save = (Button) findViewById(R.id.btn_save);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_dateselect = (Button) findViewById(R.id.btn_dateselect);

        txtIssueKey = (TextView) findViewById(R.id.txtIssueKey);
        txtIssueSummary = (TextView) findViewById(R.id.txtIssueSummary);
        txt_dateselected = (TextView) findViewById(R.id.txt_dateselected);

        edt_worklog = (EditText) findViewById(R.id.edt_worklog);

        btn_save.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        btn_dateselect.setOnClickListener(this);

        aminutes = getContext().getResources().getStringArray(R.array.aminutes);

        numberHourTime = (NumberPicker) findViewById(R.id.hourTime);
        numberHourTime.setMinValue(0);
        numberHourTime.setMaxValue(8);
        numberHourTime.setWrapSelectorWheel(false);

        numberMinutTime = (NumberPicker) findViewById(R.id.minutTime);
        numberMinutTime.setMinValue(0);
        numberMinutTime.setMaxValue(aminutes.length-1);
        numberMinutTime.setDisplayedValues(aminutes);
        numberMinutTime.setWrapSelectorWheel(false);

        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH)+1;
        mDay = c.get(Calendar.DAY_OF_MONTH);

        txt_dateselected.setText(getDateFormate(mYear, mMonth, mDay));

        if(this.itemIssueBean!=null){
            txtIssueKey.setText(itemIssueBean.getKeyissue());
            txtIssueSummary.setText(itemIssueBean.getSumary());

            if (isDebbud) {
                StringBuffer msgLog = new StringBuffer();
                msgLog.append("this.itemIssueBean: ");
                msgLog.append(itemIssueBean.toString());
                Log.d(LOG_TAG, msgLog.toString());
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                boolean validateDataValue = true;
                if(edt_worklog.getText().toString()==null || edt_worklog.getText().toString().isEmpty() || edt_worklog.getText().toString().length()<2){
                    Toast.makeText(getContext(), getContext().getString(R.string.msg_worklogmandatory), Toast.LENGTH_LONG).show();
                    validateDataValue = false;
                }
                if(validateDataValue && (numberHourTime.getValue()==0 && numberMinutTime.getValue()==0)){
                    Toast.makeText(getContext(), getContext().getString(R.string.msg_worklogtimemandatory), Toast.LENGTH_LONG).show();
                    validateDataValue = false;
                }

                if(validateDataValue){
                    saveWorkLog();
                }

                break;
            case R.id.btn_cancel:
                dismiss();

                break;
            case R.id.btn_dateselect:
                showDatePickerDialog();

                break;
            default:
                dismiss();
                break;
        }
    }

    private void showDatePickerDialog(){

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        txt_dateselected.setText(getDateFormate(year, monthOfYear+1, dayOfMonth));

                    }
                }, mYear, mMonth-1, mDay);
        datePickerDialog.show();
    }

    private void saveWorkLog(){
        setWait(true);

        DateTimeFormatter formatter = DateTimeFormat.forPattern(Constants.STR_FORMATED_SHORT_DATE);
        DateTime theDate =  formatter.parseDateTime(txt_dateselected.getText().toString());

        IssueSaveWorkLogBean issueSaveWorkLogBean = new IssueSaveWorkLogBean(
                itemIssueBean.getKeyissue(),
                edt_worklog.getText().toString(),
                theDate,
                numberHourTime.getValue(),
                numberMinutTime.getValue()
        );

        String val1 = PreferenceManagerHelper.getInstance(getContext()).getPreferenceValue(Constants.STR_PREF_USR);
        String val2 = PreferenceManagerHelper.getInstance(getContext()).getPreferenceValue(Constants.STR_PREF_PAS);
        String val3 = PreferenceManagerHelper.getInstance(getContext()).getPreferenceValue(Constants.STR_PREF_URL);

        issueSaveWorkLogBean.setUsername(val1);
        issueSaveWorkLogBean.setPassword(val2);
        issueSaveWorkLogBean.setJiraurl(val3);

        new AsyncTaskMW<IssueSaveWorkLogBean, Boolean, String>(issueSaveWorkLogBean, Constants.STR_NOTAVAILABLE){

            @Override
            protected Boolean process(IssueSaveWorkLogBean input) {
                StringBuffer msgLog = new StringBuffer("process: ");
                msgLog.append(input);
                Log.d(LOG_TAG, msgLog.toString());

                return JIraClientAccessHelper.getInstance().saveWorkLog(input);
            }

            @Override
            protected void applyOutputToTarget(Boolean output, String target) {
                if (isDebbud) {
                    StringBuffer msgLog = new StringBuffer("applyOutputToTarget");
                    Log.d(LOG_TAG, msgLog.toString());
                }

                saveWorkLogResult(output);

            }
        }.execute();

    }

    private void saveWorkLogResult(Boolean result){
        setWait(false);

        String msgResult = getContext().getString(R.string.msg_workloggood);

        if(!result){
            msgResult = getContext().getString(R.string.msg_worklogbad);
        }

        Toast.makeText(getContext(), msgResult, Toast.LENGTH_LONG).show();
        dismiss();
    }


    private String getDateFormate(int mYear, int mMonth, int mDay){
        StringBuffer response = new StringBuffer();
        response.append(mDay);
        response.append(Constants.STR_DASH);
        response.append(mMonth);
        response.append(Constants.STR_DASH);
        response.append(mYear);

        return response.toString();
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

        ProgressBar response = new ProgressBar(getContext());
        response.setLayoutParams(params);

        return response;
    }
}
