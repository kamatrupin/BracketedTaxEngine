package test.com.tax.manager;

import lombok.Data;

@Data
public class Customer {
    String id;
    double income;
    double taxAmount;
    String fName;
    String lName;

    public Customer(String id, double income) {
        this.id = id;
        this.income = income;
    }
}

