package org.bmx.parcial;

import java.util.List;
import java.util.stream.Collectors;

public class CompetitorService {
    public List<Competitor> sortByScore(List<Competitor> competitors) {
        return competitors.stream()
                .sorted((c1, c2) -> Integer.compare(c2.getTotalScore(), c1.getTotalScore()))
                .collect(Collectors.toList());
    }

    public List<Competitor> sortByName(List<Competitor> competitors) {
        return competitors.stream()
                .sorted((c1, c2) -> c1.getFirstName().compareTo(c2.getFirstName()))
                .collect(Collectors.toList());
    }

    public List<Competitor> search(List<Competitor> competitors, String name, String country, Integer minScore) {
        return competitors.stream()
                .filter(c -> (name == null || c.getFirstName().contains(name)) &&
                        (country == null || c.getCountry().equalsIgnoreCase(country)) &&
                        (minScore == null || c.getTotalScore() >= minScore))
                .collect(Collectors.toList());
    }
}
