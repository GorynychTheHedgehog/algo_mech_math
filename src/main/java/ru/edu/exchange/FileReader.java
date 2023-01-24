package ru.edu.exchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;


public class FileReader {
    public List<Paper> readFile(String fileName) {
        try (
            var inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
            var inputStreamReader = new InputStreamReader(inputStream);
            var bufferedReader = new BufferedReader(inputStreamReader)
        ) {

            return bufferedReader
                    .lines()
                    .skip(1)
                    .map(rawString -> rawString.split("\t"))
                    .filter(this::prefilterMarkets)
                    .map(Deal::new)
                    .collect(
                        Collectors.groupingBy(Deal::getCode)
                    )
                    .values().stream()
                    .map(Paper::fromDeals)
                    .sorted()
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean prefilterMarkets(String[] row) {
        return row[2].equals("TQBR") || row[2].equals("FQBR");
    }
}
