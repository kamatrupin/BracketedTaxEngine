package test.com.tax;

import test.com.tax.manager.BracketManager;
import test.com.tax.manager.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class TaxEngine {

    public static void main(String[] args) {

        // Set up the Manager - this will fetch brackets from DB and crate a BracketManager
        BracketManager manager = SetUp.setUp(args);

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
