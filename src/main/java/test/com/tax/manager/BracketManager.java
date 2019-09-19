package test.com.tax.manager;

import test.com.tax.bracket.Bracket;
import test.com.tax.bracket.BracketInterval;

import java.util.*;

public class BracketManager {

    private static final BracketManager bracketManager = new BracketManager();
    private final Map<String, Bracket> bracketsCache = new HashMap();
    private String activeBracket;

    private BracketManager() {
    }

    public static BracketManager getInstance() {
        return bracketManager;
    }

    public void addABraket(String uuid, Bracket bracket) {
        bracketsCache.put(uuid, bracket);
    }

    public Map<String, List<BracketInterval>> getBrackets() {
        Map<String, List<BracketInterval>> map = new HashMap<>();
        bracketsCache.entrySet().forEach(entry -> map.put(entry.getKey(), entry.getValue().getBracketIntervals()));
        return map;
    }

    public void refreshCache() {
        //TODO: Pull brackets from the database and refresh the cache
    }

    public void setActiveBracket(String activeBracket) {
        this.activeBracket = activeBracket;
    }

    public double calculateTaxAmount(double income) throws Exception {
        return bracketsCache.get(activeBracket).calculateTaxAmount(income);
    }

    public void calculateTaxAmountForBatch(List<Customer> customers) throws Exception {
        for(Customer customer : customers) {
            customer.setTaxAmount(bracketsCache.get(activeBracket).calculateTaxAmount(customer.getIncome()));
        }
    }
}
