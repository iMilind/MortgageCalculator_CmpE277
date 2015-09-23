package com.example.milindmahajan.mortgagecalculator;

import java.util.HashMap;
import static java.lang.Math.pow;

/**
 * Created by milind.mahajan on 9/18/15.
 */
public class CalculationHelper {

    public static HashMap<String, String> calculateMortgageFrom(Double homeValue, Double downPay, Double terms, Double apr, Double propertyTax) {

        HashMap<String, String> calculationMap = new HashMap<String, String>();

        Double actualLoan = homeValue - downPay;
        Double totalTerms = terms * 12;

        Double totalPropertyTax = homeValue * propertyTax/100 * terms;
        calculationMap.put("propertyTax", String.format("%.2f", totalPropertyTax));

        Double monthlyPropertyTaxTemp = totalPropertyTax/totalTerms;
        Double monthlyPropertyTax = Double.parseDouble(String.format("%.2f", monthlyPropertyTaxTemp));

        Double monthlyInterestRate = apr/12;
        monthlyInterestRate /= 100;

        Double temp = pow((1+monthlyInterestRate), totalTerms);

        Double monthlyMortgagePaymentTemp = (actualLoan * monthlyInterestRate * temp)/(temp - 1);
        Double monthlyMortgagePayment = Double.parseDouble(String.format("%.2f", monthlyMortgagePaymentTemp));

        Double monthlyPayment = monthlyMortgagePayment + monthlyPropertyTax;
        calculationMap.put("monthlyPayment", String.format("%.2f", monthlyPayment));

        Double totalInterestPaid = (monthlyPayment * totalTerms) - (actualLoan + downPay);
        calculationMap.put("totalInterest", String.format("%.2f", totalInterestPaid));

        return calculationMap;
    }
}