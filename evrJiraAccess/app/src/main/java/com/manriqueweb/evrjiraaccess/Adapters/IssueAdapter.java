package com.manriqueweb.evrjiraaccess.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.manriqueweb.evrjiraaccess.Beans.IssueBean;
import com.manriqueweb.evrjiraaccess.R;
import com.manriqueweb.evrjiraaccess.Utils.Constants;

import java.util.List;

/**
 * Created by omar on 9/05/17.
 */

public class IssueAdapter extends BaseAdapter {
    private static final String LOG_TAG = Constants.STR_LOG_TAG.concat("GeneralBeanAdapter");
    private static final boolean isDebbud = Constants.isDebbud;

    private Context context = null;
    private List<IssueBean> listIssueBean = null;

    private IssueAdapter(){

    }

    public IssueAdapter(Context context, List<IssueBean> listData) {
        super();
        this.listIssueBean = listData;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listIssueBean==null ? 0 : listIssueBean.size();
    }

    @Override
    public Object getItem(int position) {
        return getCount()==0 ? null : listIssueBean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getCount()==0 ? (long) 0 : (long) 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        IssueBean itemIssue = listIssueBean.get(position);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.issue_item, null);
        }

        TextView theKeyIssue = (TextView) convertView.findViewById(R.id.keyIssue);
        TextView summaryIssue = (TextView) convertView.findViewById(R.id.summaryIssue);

        theKeyIssue.setText(itemIssue.getKeyissue());
        summaryIssue.setText(itemIssue.getSumary());

        return convertView;
    }
}
