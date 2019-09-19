package test.com.tax;

import test.com.tax.bracket.Bracket;
import test.com.tax.bracket.BracketInterval;
import test.com.tax.manager.BracketManager;

import java.util.Arrays;
import java.util.UUID;

public class SetUp {

    public static BracketManager setUp(String [] args) {
        // Fetch the Brackets from DB and build BracketManager

        String [] int1 = args[0].split(",");
        String [] int2 = args[1].split(",");
        String [] int3 = args[2].split(",");
        String [] int4 = args[3].split(",");

        BracketManager manager = BracketManager.getInstance();

        String uuid = UUID.randomUUID().toString();
        manager.addABraket(uuid, new Bracket(Arrays.asList(new BracketInterval(Double.valueOf(int1[0]), null, Double.valueOf(int1[1])),
                new BracketInterval(Double.valueOf(int2[0]), Double.valueOf(int2[1]), Double.valueOf(int2[2])),
                new BracketInterval(Double.valueOf(int3[0]), Double.valueOf(int3[1]), Double.valueOf(int3[2])),
                new BracketInterval(Double.valueOf(int4[0]), Double.valueOf(int4[1]), Double.valueOf(int4[2])))));

        manager.setActiveBracket(uuid);

        return manager;
    }
}
