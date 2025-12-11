package com.viraj.sample.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class VersionCompatibilityService {

    /**
     * Example method that will break with different Spring Boot versions
     * Spring Boot 2.x vs 3.x has different StringUtils API
     */
    public List<String> processItems(List<String> items) {
        // FIXED for Spring Boot 3.x: StringUtils.isEmpty() → StringUtils.hasText()
        return items.stream()
                .filter(item -> StringUtils.hasText(item)) // NEW API: hasText() is opposite of isEmpty()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }

    /**
     * Example method using standard Java collections
     * While less likely to change, these patterns might need updates
     */
    public Map<String, Integer> processCollections(List<String> items) {
        List<String> processedItems = new ArrayList<>();
        processedItems.addAll(items);
        
        // FIXED for Spring Boot 3.x: StringUtils.isEmpty() → StringUtils.hasText()
        List<String> filteredItems = processedItems.stream()
                .filter(item -> StringUtils.hasText(item)) // NEW API: hasText() is opposite of isEmpty()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        
        Map<String, Integer> counts = new HashMap<>();
        for (String item : filteredItems) {
            counts.put(item, item.length());
        }
        
        System.out.println("Item lengths: " + counts);
        return counts;
    }

    /**
     * Method that demonstrates Bean Validation differences
     * Between Spring Boot versions
     */
    public boolean validateData(String data) {
        // FIXED for Spring Boot 3.x: StringUtils.isEmpty() → !StringUtils.hasText()
        if (!StringUtils.hasText(data)) { // NEW API: hasText() is opposite of isEmpty()
            throw new IllegalArgumentException("Data cannot be emptyyy");
        }
        return true;
    }
}
