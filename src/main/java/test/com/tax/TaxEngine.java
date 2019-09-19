package test.com.tax;

import test.com.tax.bracket.Bracket;
import test.com.tax.bracket.BracketInterval;
import test.com.tax.manager.BracketManager;
import test.com.tax.manager.Customer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class TaxEngine {

    public static void main(String[] args) {

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

        try {
            System.out.println("------------- Single calculations ------------");
            Stream.of(args[4].split(",")).forEach(input -> CompletableFuture.supplyAsync(() -> manager.calculateTaxAmount(Double.valueOf(input))).thenAccept(System.out::println));

            System.out.println("------------- Bulk calculations ------------");
            // Bulk calculations
            List<Customer> customers = new ArrayList<>();
            for(String input : args[5].split(",")) {
                customers.add(new Customer(UUID.randomUUID().toString(), Double.valueOf(input)));
            }
            manager.calculateTaxAmountForBatch(customers);
            customers.stream().forEach(System.out::println);
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
        }
    }
}
