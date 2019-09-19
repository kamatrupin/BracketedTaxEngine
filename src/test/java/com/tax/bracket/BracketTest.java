package com.tax.bracket;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;

public class BracketTest {

    private Bracket bracket = null;

    @BeforeClass
    public void before() {
    }

    @Test
    public void testCalculateTaxAmount() throws Exception {
        bracket = new Bracket(Arrays.asList(new BracketInterval(50000.0, null, 30.0),
                new BracketInterval(20000.0, 50000.0, 20.0),
                new BracketInterval(10000.0, 20000.0, 10.0),
                new BracketInterval(0.0, 10000.0, 0.0)));

        Assert.assertEquals(bracket.calculateTaxAmount(120000), 28000.0);
    }
}
