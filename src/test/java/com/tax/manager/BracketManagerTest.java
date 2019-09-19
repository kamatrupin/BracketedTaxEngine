package com.tax.manager;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.tax.bracket.Bracket;
import com.tax.bracket.BracketInterval;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BracketManagerTest {

    private BracketManager bracketManager = null;

    @BeforeClass
    public void before() {
        bracketManager = BracketManager.getInstance();
    }

    @Test
    public void testBracketFlow() {
        Map<String, List<BracketInterval>> brackets = bracketManager.getBrackets();
        Assert.assertEquals(brackets.size(), 1);

        // Add a bracket
        String uuid = UUID.randomUUID().toString();
        bracketManager.addABraket(uuid, new Bracket(Arrays.asList(new BracketInterval(50000.0, null, 30.0),
                new BracketInterval(20000.0, 50000.0, 20.0),
                new BracketInterval(10000.0, 20000.0, 10.0),
                new BracketInterval(0.0, 10000.0, 0.0))));
        brackets = bracketManager.getBrackets();
        Assert.assertEquals(brackets.size(), 2);
        Assert.assertTrue(brackets.keySet().contains(uuid));
    }
}
