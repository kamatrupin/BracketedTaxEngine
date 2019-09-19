package com.tax.bracket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bracket {

    private final Map<Double, Double> bracketCalculations = new HashMap();
    private List<BracketInterval> bracketIntervals = null;

    public Bracket() {}

    public Bracket(List<BracketInterval> bracketIntervals) {
        this.bracketIntervals = bracketIntervals;
        preCalculateBrackets();
    }

    /*
     *  Calculate and cache the tax amount for each bracket
     */
    private void preCalculateBrackets() {
        double taxAmount = 0.0;
        for(int i=bracketIntervals.size() - 1; i>=0; i--) {
            BracketInterval interval = bracketIntervals.get(i);

            if(interval.getHigh() == null) continue;

            taxAmount += (interval.getHigh() - interval.getLow()) * (interval.getPercent() / 100);
            bracketCalculations.put(interval.getHigh(), taxAmount);
        }
    }

    public double calculateTaxAmount(double income) {
        if(income == 0.0) return 0.0;

        double taxAmount = 0.0;
        // Check if the income falls under the highest bracket
        if(income > bracketIntervals.get(0).getLow()) {
            double portion = income - bracketIntervals.get(0).getLow();
            return taxAmount + portion * (bracketIntervals.get(0).getPercent() / 100) + bracketCalculations.get(bracketIntervals.get(0).getLow());
        } else {
            // Optimize this - Avoid iterating the list of intervals use binary search to find the start interval instead.
            for(int i=1; i<bracketIntervals.size(); i++) {
                BracketInterval bracket = bracketIntervals.get(i);
                if(income > bracket.getLow())
                    return taxAmount + ((income - bracket.getLow()) * (bracket.getPercent() / 100)) + (bracketCalculations.get(bracket.getLow()) == null ? 0.0 : bracketCalculations.get(bracket.getLow()));
            }
        }

        return taxAmount;
    }

    public List<BracketInterval> getBracketIntervals() {
        return bracketIntervals;
    }
}
