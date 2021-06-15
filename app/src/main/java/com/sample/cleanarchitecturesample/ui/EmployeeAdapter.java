package com.sample.cleanarchitecturesample.ui;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sample.cleanarchitecturesample.R;

import java.util.HashMap;
import java.util.List;

public class EmployeeAdapter extends BaseExpandableListAdapter {

    private final Context context;
    private final List<List<String>> listDataGroup;
    private final HashMap<String, List<Object>> listDataChild;

    public EmployeeAdapter(Context context, List<List<String>> listDataGroup,
                           HashMap<String, List<Object>> listDataChild) {
        this.context = context;
        this.listDataGroup = listDataGroup;
        this.listDataChild = listDataChild;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.listDataChild.get(this.listDataGroup.get(groupPosition).get(0)).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        Object childText = getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.layout_employee_list_child_item, null);
        }
        TextView textViewChild = convertView.findViewById(R.id.textViewChild);
        textViewChild.setText(Html.fromHtml(String.valueOf(childText)));
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listDataChild.get(this.listDataGroup.get(groupPosition).get(0)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listDataGroup.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listDataGroup.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = listDataGroup.get(groupPosition).get(0);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.layout_employee_list_group_item, null);
        }
        TextView txtViewEmployeeName = convertView.findViewById(R.id.tv_Header);
        ImageButton indicator = convertView.findViewById(R.id.img_btn_arrow);
        ImageView imgProfile = convertView.findViewById(R.id.iv_profile_image);
        Glide.with(context).load(listDataGroup.get(groupPosition).get(1)).into(imgProfile);
        indicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpanded) {
                    ((ExpandableListView) parent).collapseGroup(groupPosition);
                    indicator.setImageResource(R.drawable.ic_arrow_down);
                } else {
                    ((ExpandableListView) parent).expandGroup(groupPosition, true);
                    indicator.setImageResource(R.drawable.ic_arrow_up);
                }
            }
        });
        txtViewEmployeeName.setText(headerTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}