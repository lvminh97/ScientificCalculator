package com.example.kumail.scientificcalculator.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.kumail.scientificcalculator.R;
import com.example.kumail.scientificcalculator.model.History;

import java.util.ArrayList;

public class HistoryAdapter extends ArrayAdapter<History> {

    private Activity context;

    private ArrayList<History> historyList;

    public HistoryAdapter(Activity context, ArrayList<History> historyList) {
        super(context, R.layout.view_history, historyList);
        this.context = context;
        this.historyList = historyList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.view_history, null, true);

        TextView expressionTextview = rowView.findViewById(R.id.textview_expression);
        TextView resultTextview = rowView.findViewById(R.id.textview_result);

        expressionTextview.setText(historyList.get(position).getExpression());
        resultTextview.setText(historyList.get(position).getResult());

        return rowView;
    }
}
