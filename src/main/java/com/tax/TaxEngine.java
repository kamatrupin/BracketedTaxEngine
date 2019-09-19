package com.tax;

import com.tax.manager.BracketManager;
import com.tax.bracket.Bracket;
import com.tax.bracket.BracketInterval;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class TaxEngine {

    private static final String ADMIN_ROLE = "Admin";
    private static final String OPERATION_GET_BRACKETS = "getBrackets";
    private static final String OPERATION_CREATE_A_BRACKET = "createABracket";
    private static final String OUTPUT_DELIMITER = " : ";

    public static void main(String[] args) {

        try {
            if(args.length == 0) System.out.println("Please provide valid inputs");

            // Set up the Manager - this will fetch brackets from DB and crate a BracketManager
            BracketManager manager = BracketManager.getInstance();

            if(ADMIN_ROLE.equalsIgnoreCase(args[0]) && OPERATION_GET_BRACKETS.equalsIgnoreCase(args[1])) {
                manager.getBrackets().entrySet().stream().forEach(System.out::println);
            } else if(ADMIN_ROLE.equalsIgnoreCase(args[0]) && OPERATION_CREATE_A_BRACKET.equalsIgnoreCase(args[1])) {
                double [] intervals = Stream.of(args[2].split(",")).mapToDouble(Double::parseDouble).toArray();
                String uuid = UUID.randomUUID().toString();
                manager.addABraket(uuid, new Bracket(Arrays.asList(new BracketInterval(intervals[0], null, intervals[1]),
                        new BracketInterval(intervals[2], intervals[3], intervals[4]),
                        new BracketInterval(intervals[5], intervals[6], intervals[7]),
                        new BracketInterval(intervals[8], intervals[9], intervals[10]))));
                manager.getBrackets().entrySet().stream().forEach(System.out::println);
            } else if(args[0].split(",").length == 1) {
                System.out.println("------------- Single calculations ------------");
                System.out.println(args[0] + OUTPUT_DELIMITER + manager.calculateTaxAmount(Double.valueOf(args[0])));;
            } else {
                System.out.println("------------- Bulk calculations ------------");
                Stream.of(args[0].split(",")).mapToDouble(Double::parseDouble)
                        .forEach(income -> CompletableFuture.supplyAsync(() -> manager.calculateTaxAmount(income))
                        .thenAccept(taxAmount -> System.out.println(income + OUTPUT_DELIMITER + taxAmount)));
            }
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
        }
    }
}
