package ru.edu.exchange;

import lombok.Getter;

@Getter
public class Deal {
    private final String code;
    private final Double volume;
    private final Long tradeTime;
    private final Double price;

    public Deal(String[] parameters) {
        code = parameters[3];
        volume = Double.parseDouble(parameters[8]);
        tradeTime = Long.parseLong(parameters[1]);
        price = Double.parseDouble(parameters[4]);
    }
}
