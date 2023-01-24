package ru.edu.exchange;

import lombok.Getter;

@Getter
public class Paper implements Comparable<Paper>{
    private String code;

    private int dealsCounter = 0;

    private double volume = 0.;

    private Long startTime = Long.MAX_VALUE;
    private Long finalTime = Long.MIN_VALUE;

    private Double startPrice;
    private Double finalPrice;

    public static Paper fromDeals(Iterable<Deal> deals) {
        var paper = new Paper();
        paper.addAll(deals);
        return paper;
    }

    public Double getPriceDelta() {
        return finalPrice / startPrice - 1;
    }

    private void addAll(Iterable<Deal> deals) {
        deals.forEach(this::add);
    }

    private void add(Deal deal) {
        if (code == null) {
            code = deal.getCode();
        } else if (!code.equals(deal.getCode())) {
            throw new IllegalArgumentException("deal with wrong code!");
        }

        dealsCounter++;

        volume += deal.getVolume();

        var dealTradeTime = deal.getTradeTime();
        if (dealTradeTime < startTime) {
            startTime = dealTradeTime;
            startPrice = deal.getPrice();
        }

        if (dealTradeTime > finalTime) {
            finalTime = dealTradeTime;
            finalPrice = deal.getPrice();
        }
    }

    @Override
    public String toString() {
        return "%s\t\t%.2f%%\t\t%6d\t\t%.1f".formatted(code, getPriceDelta() * 100, dealsCounter, volume);
    }

    @Override
    public int compareTo(Paper o) {
        return this.getPriceDelta().compareTo(o.getPriceDelta());
    }
}
