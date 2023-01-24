package ru.edu.exchange;

import com.google.common.collect.Lists;

public class Main {

    public static void main(String[] args) {
        var fileName = "trades.txt";
        var fileReader = new FileReader();

        var papers = fileReader.readFile(fileName);

        var worstPapers = papers.subList(0, 10);
        var bestPapers = Lists.reverse(papers.subList(papers.size() - 10, papers.size()));

        PrintUtility.print("Best papers", bestPapers);
        PrintUtility.print("Worst papers", worstPapers);
    }
}
