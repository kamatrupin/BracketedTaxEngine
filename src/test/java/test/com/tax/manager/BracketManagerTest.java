package test.com.tax.manager;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import test.com.tax.bracket.Bracket;
import test.com.tax.bracket.BracketInterval;

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
        // No brackets
        Map<String, List<BracketInterval>> brackets = bracketManager.getBrackets();
        Assert.assertEquals(brackets.size(), 0);

        // Add brackets
        String uuid = UUID.randomUUID().toString();
        bracketManager.addABraket(uuid, new Bracket(Arrays.asList(new BracketInterval(50000.0, null, 30.0),
                new BracketInterval(20000.0, 50000.0, 20.0),
                new BracketInterval(10000.0, 20000.0, 10.0),
                new BracketInterval(0.0, 10000.0, 0.0))));
        brackets = bracketManager.getBrackets();
        Assert.assertEquals(brackets.size(), 1);
        Assert.assertEquals(uuid, brackets.keySet().iterator().next());
    }
}
