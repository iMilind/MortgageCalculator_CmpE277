package com.example.milindmahajan.mortgagecalculator;

import android.app.*;
import android.os.Bundle;
import android.graphics.drawable.ColorDrawable;
import android.content.*;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class MortgageActivity extends Activity implements InputFragment.InputFragmentProtocol {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mortgage);

        ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(0xff0099cc));
        actionBar.setTitle("Loan Calculator");
        actionBar.setDisplayShowTitleEnabled(true);
    }

    private void calculate (HashMap <String, String>loanInfo) {

        Double homeValue = Double.parseDouble(loanInfo.get("homeValue").toString());
        Double downPay = Double.parseDouble(loanInfo.get("downPay").toString());
        Double interestRate = Double.parseDouble(loanInfo.get("interestRate").toString());
        Double terms = Double.parseDouble(loanInfo.get("terms").toString());
        Double taxRate = Double.parseDouble(loanInfo.get("taxRate").toString());

        String payOffDate = getPayOffDate(Integer.parseInt(loanInfo.get("terms").toString()));

        HashMap <String, String> calculationMap = CalculationHelper.calculateMortgageFrom(homeValue, downPay, terms, interestRate, taxRate);
        calculationMap.put("payOffDate", payOffDate);

        OutputFragment outputFragment = (OutputFragment) getFragmentManager().findFragmentById(R.id.outputFragment);
        outputFragment.displayResult(calculationMap);
    }

    private String getPayOffDate(int terms) {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, terms);

        DateFormatSymbols dateFormatSymbols = new DateFormatSymbols();
        String[] months = dateFormatSymbols.getMonths();

        String payOffDate = "";

        if (calendar.get(Calendar.MONTH)-1 >= 0 && calendar.get(Calendar.MONTH)-1 <= 11) {

            payOffDate = months[calendar.get(Calendar.MONTH)-1];
        }
        payOffDate = payOffDate + " " + calendar.get(Calendar.YEAR);

        return payOffDate;
    }

    private void showAlertFor(String message) {

        AlertDialog.Builder errorAlertBuilder = new AlertDialog.Builder(this);

        errorAlertBuilder.setTitle("Error!");
        errorAlertBuilder
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        AlertDialog errorAlert = errorAlertBuilder.create();

        errorAlert.show();
    }

    @Override
    public void calculateButtonTouched(HashMap<String, String> loanParam) {

        System.out.println("delegate didTouchCalculateButton");
        calculate(loanParam);
    }

    @Override
    public void resetButtonTouched() {

        System.out.println("delegate didTouchResetButton");
    }

    @Override
    public void invalidInput(String message) {

        System.out.println("delegate invalidInput");
        showAlertFor(message);
    }
}