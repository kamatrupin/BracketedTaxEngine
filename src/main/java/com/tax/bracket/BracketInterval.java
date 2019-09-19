package com.tax.bracket;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BracketInterval {

    private Double low;
    private Double high;
    private Double percent;
}
