package com.example.kumail.scientificcalculator;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.kumail.scientificcalculator.adapter.HistoryAdapter;
import com.example.kumail.scientificcalculator.handler.DatabaseHandler;
import com.example.kumail.scientificcalculator.model.History;

import org.mariuszgromada.math.mxparser.Expression;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private DatabaseHandler databaseHandler;

    private TextView previousCalculation;
    private EditText display;

    private boolean showHistory = false;
    private ConstraintLayout historyLayout;
    private ListView historyListView;
    private HistoryAdapter historyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        previousCalculation = findViewById(R.id.previousCalculationView);
        display = findViewById(R.id.displayEditText);

        display.setShowSoftInputOnFocus(false);

        historyLayout = findViewById(R.id.layout_history);
        historyListView = findViewById(R.id.listview_history);
        historyListView.setOnItemClickListener(this);

        databaseHandler = new DatabaseHandler(MainActivity.this);
    }

    private void updateText(String strToAdd){
        String oldStr = display.getText().toString();
        int cursorPos = display.getSelectionStart();
        String leftStr = oldStr.substring(0, cursorPos);
        String rightStr = oldStr.substring(cursorPos);

        display.setText(String.format("%s%s%s", leftStr, strToAdd, rightStr));
        display.setSelection(cursorPos + strToAdd.length());
    }

    public void zeroBTNPush(View view){
        updateText(getResources().getString(R.string.zeroText));
    }

    public void oneBTNPush(View view){
        updateText(getResources().getString(R.string.oneText));
    }

    public void twoBTNPush(View view){
        updateText(getResources().getString(R.string.twoText));
    }

    public void threeBTNPush(View view){
        updateText(getResources().getString(R.string.threeText));
    }

    public void fourBTNPush(View view){
        updateText(getResources().getString(R.string.fourText));
    }

    public void fiveBTNPush(View view){
        updateText(getResources().getString(R.string.fiveText));
    }

    public void sixBTNPush(View view){
        updateText(getResources().getString(R.string.sixText));
    }

    public void sevenBTNPush(View view){
        updateText(getResources().getString(R.string.sevenText));
    }

    public void eightBTNPush(View view){
        updateText(getResources().getString(R.string.eightText));
    }

    public void nineBTNPush(View view){
        updateText(getResources().getString(R.string.nineText));
    }

    public void multiplyBTNPush(View view){
        updateText(getResources().getString(R.string.multiplyText));
    }

    public void divideBTNPush(View view){
        updateText(getResources().getString(R.string.divideText));
    }

    public void subtractBTNPush(View view){
        updateText(getResources().getString(R.string.subtractText));
    }

    public void addBTNPush(View view){
        updateText(getResources().getString(R.string.addText));
    }

    public void clearBTNPush(View view){
        display.setText("");
        previousCalculation.setText("");
    }

    public void parOpenBTNPush(View view){
        updateText(getResources().getString(R.string.parenthesesOpenText));
    }

    public void parCloseBTNPush(View view){
        updateText(getResources().getString(R.string.parenthesesCloseText));
    }

    public void decimalBTNPush(View view){
        updateText(getResources().getString(R.string.decimalText));
    }

    public void equalBTNPush(View view){
        String userExp = display.getText().toString();

        String finalExp = userExp.replaceAll(getResources().getString(R.string.divideText), "/");
        finalExp = finalExp.replaceAll(getResources().getString(R.string.multiplyText), "*");
        finalExp = finalExp.replaceAll("log[(]", "(1/ln(10))*ln(");

        Expression exp = new Expression(finalExp);
        double result = Double.parseDouble(String.valueOf(exp.calculate()));
        if(Double.isNaN(result)) {
            Toast.makeText(getBaseContext(), "The expression is not valid", Toast.LENGTH_SHORT).show();
            return;
        }

        String resultStr = (new DecimalFormat("#.#########", new DecimalFormatSymbols(Locale.US))).format(result);

        previousCalculation.setText(userExp);
        display.setText(resultStr);
        display.setSelection(resultStr.length());
        databaseHandler.addHistory(new History(0, userExp, "=" + resultStr));
    }

    public void backspaceBTNPush(View view){
        int cursorPos = display.getSelectionStart();
        int textLen = display.getText().length();

        if (cursorPos != 0 && textLen != 0){
            SpannableStringBuilder selection = (SpannableStringBuilder) display.getText();
            selection.replace(cursorPos-1, cursorPos, "");
            display.setText(selection);
            display.setSelection(cursorPos-1);
        }
    }

    public void trigSinBTNPush(View view){
        updateText("sin(");
    }

    public void trigCosBTNPush(View view){
        updateText("cos(");
    }

    public void trigTanBTNPush(View view){
        updateText("tan(");
    }

    public void trigArcSinBTNPush(View view){
        updateText("arcsin(");
    }

    public void trigArcCosBTNPush(View view){
        updateText("arccos(");
    }

    public void trigArcTanBTNPush(View view){
        updateText("arctan(");
    }

    public void naturalLogBTNPush(View view){
        updateText("ln(");
    }

    public void logBTNPush(View view){
        updateText("log(");
    }

    public void sqrtBTNPush(View view){
        updateText("sqrt(");
    }

    public void absBTNPush(View view){
        updateText("abs(");
    }

    public void piBTNPush(View view){
        updateText("pi");
    }

    public void eBTNPush(View view){
        updateText("e");
    }

    public void xSquaredBTNPush(View view){
        updateText("^(2)");
    }

    public void xPowerYBTNPush(View view){
        updateText("^(");
    }

    public void primeBTNPush(View view){
        updateText("ispr(");
    }

    public void historyBTNPush(View view) {
        if(showHistory ==false) {
            ArrayList<History> historyList = databaseHandler.getAllHistory();
            historyAdapter = new HistoryAdapter(MainActivity.this, historyList);
            historyListView.setAdapter(historyAdapter);
            showHistory = true;
            historyLayout.setVisibility(View.VISIBLE);
        }
        else {
            showHistory = false;
            historyLayout.setVisibility(View.INVISIBLE);
        }
    }

    public void clearHistoryBTNPush(View view) {
        databaseHandler.deleteAllHistory();
        historyAdapter = new HistoryAdapter(MainActivity.this, new ArrayList<History>());
        historyListView.setAdapter(historyAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        History history = historyAdapter.getItem(i);
        previousCalculation.setText(history.getExpression());
        display.setText(history.getResult().substring(1));
        display.setSelection(history.getResult().length() - 1);
    }
}