package com.example.milindmahajan.mortgagecalculator;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class InputFragment extends Fragment {

    InputFragmentProtocol delegate;

    public interface  InputFragmentProtocol {

        public void calculateButtonTouched(HashMap <String, String> loanParam);
        public void resetButtonTouched();
        public void invalidInput(String message);
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

        try {
            delegate = (InputFragmentProtocol) context;
        }
        catch (ClassCastException exception) {

            throw new ClassCastException(context.toString());
        }
    }

    private View parentView;
     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

         parentView = inflater.inflate(R.layout.input_fragment, container, false);

         addOnClickListenerForCalculateButton();
         addOnClickListenerForResetButton();

         addOnItemSelectedListenerToSpinner();
         return parentView;
     }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    List<String> list;
    private void addOnItemSelectedListenerToSpinner () {

        Spinner spinner = (Spinner) parentView.findViewById(R.id.spinner);

        spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                hideSoftKeyboard(getActivity(), v);
                return false;
            }
        });

        list = new ArrayList<String>();

        for (int i =0; i <=10; i++) {

            list.add(String.valueOf(i*5));
        }

        ArrayAdapter<String> adp = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, list);

        adp.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        spinner.setAdapter(adp);

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }

    private void addOnClickListenerForCalculateButton () {

        Button calculateButton = (Button) parentView.findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                hideSoftKeyboard(getActivity(), v);

                if (isValidForCalculation()) {

                    HashMap<String, String> loanInfo = new HashMap<String, String>();

                    EditText homeValueEditText = (EditText) parentView.findViewById(R.id.homeValueInput);
                    loanInfo.put("homeValue", homeValueEditText.getText().toString());

                    EditText downPayEditText = (EditText) parentView.findViewById(R.id.downPayInput);

                    if (downPayEditText.getText().length() != 0) {

                        loanInfo.put("downPay", downPayEditText.getText().toString());
                    } else {

                        loanInfo.put("downPay", "0");
                    }

                    EditText interestRateEditText = (EditText) parentView.findViewById(R.id.interestRateInput);
                    loanInfo.put("interestRate", interestRateEditText.getText().toString());

                    Spinner terms = (Spinner) parentView.findViewById(R.id.spinner);
                    loanInfo.put("terms", terms.getSelectedItem().toString());

                    EditText taxRateEditText = (EditText) parentView.findViewById(R.id.taxRateInput);
                    loanInfo.put("taxRate", taxRateEditText.getText().toString());

                    delegate.calculateButtonTouched(loanInfo);
                }
            }
        });
    }

    private void addOnClickListenerForResetButton () {

        Button resetButton = (Button) parentView.findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideSoftKeyboard(getActivity(), v);
                System.out.println("didTouchResetButton");

                EditText homeValueEditText = (EditText) parentView.findViewById(R.id.homeValueInput);
                homeValueEditText.setText("");

                EditText downPayEditText = (EditText) parentView.findViewById(R.id.downPayInput);
                downPayEditText.setText("");

                EditText interestRateEditText = (EditText) parentView.findViewById(R.id.interestRateInput);
                interestRateEditText.setText("");

                Spinner terms = (Spinner) parentView.findViewById(R.id.spinner);
                terms.setSelection(0);

                EditText taxRateEditText = (EditText) parentView.findViewById(R.id.taxRateInput);
                taxRateEditText.setText("");

                delegate.resetButtonTouched();
            }
        });
    }

    private boolean isValidForCalculation() {

        EditText homeValueEditText = (EditText) parentView.findViewById(R.id.homeValueInput);
        if (homeValueEditText.getText().length() == 0) {

            delegate.invalidInput("Enter price for house");
            return false;
        }

        EditText interestRateEditText = (EditText) parentView.findViewById(R.id.interestRateInput);
        if (interestRateEditText.getText().length() == 0) {

            delegate.invalidInput("Enter interest rate");
            return false;
        }

        Spinner terms = (Spinner) parentView.findViewById(R.id.spinner);
        if (Integer.parseInt(terms.getSelectedItem().toString()) == 0) {

            delegate.invalidInput("Enter Number of terms");
            return false;
        }

        EditText taxRateEditText = (EditText) parentView.findViewById(R.id.taxRateInput);
        if (taxRateEditText.getText().length() == 0) {

            delegate.invalidInput("Enter property tax");
            return false;
        }

        return true;
    }

    public static void hideSoftKeyboard (Activity activity, View view)
    {
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }
}