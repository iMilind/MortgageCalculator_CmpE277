package com.example.milindmahajan.mortgagecalculator;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.HashMap;


public class OutputFragment extends Fragment {

    View parentView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        parentView = inflater.inflate(R.layout.output_fragment, container, false);

        return parentView;
    }

    public void displayResult(HashMap <String, String>output) {

        EditText monthlyPayment = (EditText) parentView.findViewById(R.id.monthlyPaymentOutput);
        monthlyPayment.setText(output.get("monthlyPayment"));

        EditText totalInterest = (EditText) parentView.findViewById(R.id.totalInterestOutput);
        totalInterest.setText(output.get("totalInterest"));

        EditText propertyTax = (EditText) parentView.findViewById(R.id.propertyTaxOutput);
        propertyTax.setText(output.get("propertyTax"));

        EditText payOffDate = (EditText) parentView.findViewById(R.id.payOffDateOutput);
        payOffDate.setText(output.get("payOffDate"));
    }
}
