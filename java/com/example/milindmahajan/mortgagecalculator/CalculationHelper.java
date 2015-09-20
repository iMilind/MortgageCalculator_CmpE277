package com.example.milindmahajan.mortgagecalculator;

import java.util.HashMap;
import static java.lang.Math.pow;

/**
 * Created by milind.mahajan on 9/18/15.
 */
public class CalculationHelper {

    public static HashMap<String, String> calculateMortgageFrom(Float homeValue, Float downPay, float terms, float apr, Float propertyTax) {

        HashMap<String, String> calculationMap = new HashMap<String, String>();

        float actualLoan = homeValue - downPay;
        float totalTerms = terms * 12;

        float totalPropertyTax = homeValue * propertyTax/100 * terms;
        calculationMap.put("propertyTax", String.format("%.2f", totalPropertyTax));

        float monthlyPropertyTaxTemp = totalPropertyTax/totalTerms;
        float monthlyPropertyTax = Float.parseFloat(String.format("%.2f", monthlyPropertyTaxTemp));

        float monthlyInterestRate = apr/12;
        monthlyInterestRate /= 100;

        double temp = pow((1+monthlyInterestRate), totalTerms);

        double monthlyMortgagePaymentTemp = (actualLoan * monthlyInterestRate * temp)/(temp - 1);
        double monthlyMortgagePayment = Double.parseDouble(String.format("%.2f", monthlyMortgagePaymentTemp));

        double monthlyPayment = monthlyMortgagePayment + monthlyPropertyTax;
        calculationMap.put("monthlyPayment", String.format("%.2f", monthlyPayment));

        double totalInterestPaid = (monthlyPayment * totalTerms) - (actualLoan + downPay);
        calculationMap.put("totalInterest", String.format("%.2f", totalInterestPaid));

        return calculationMap;
    }
}