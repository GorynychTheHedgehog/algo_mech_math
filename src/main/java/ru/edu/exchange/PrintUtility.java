package ru.edu.exchange;

public class PrintUtility {
    public static void print(String name, Iterable<Paper> papers) {
        System.out.println(name + ":");
        System.out.printf("%s\t\t%s\t%s\t%6s\t%n", "Paper", "PriceDelta", "DealsCount", "Volume");
        papers.forEach(System.out::println);
        System.out.println();
    }
}
