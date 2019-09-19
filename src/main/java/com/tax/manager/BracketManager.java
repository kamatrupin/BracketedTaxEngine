package com.tax.manager;

import com.tax.bracket.Bracket;
import com.tax.bracket.BracketInterval;

import java.util.*;

public class BracketManager {

    private static final BracketManager bracketManager = new BracketManager();
    private final Map<String, Bracket> bracketsCache = new HashMap();
    private String activeBracket;

    private BracketManager() {
    }

    private static void setUp() {
        // Fetch the bracketes from DB and setup the manager
        String uuid = UUID.randomUUID().toString();
        bracketManager.addABraket(uuid, new Bracket(Arrays.asList(new BracketInterval(50000.0, null, 30.0),
                new BracketInterval(20000.0, 50000.0, 20.0),
                new BracketInterval(10000.0, 20000.0, 10.0),
                new BracketInterval(0.0, 10000.0, 0.0))));

        bracketManager.setActiveBracket(uuid);
    }

    public static BracketManager getInstance() {
        setUp();
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

    public double calculateTaxAmount(double income) {
        return bracketsCache.get(activeBracket).calculateTaxAmount(income);
    }
}
