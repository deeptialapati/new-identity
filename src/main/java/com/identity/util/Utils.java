package com.identity.util;


import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Component
public class Utils {
    public Set<String> extractRegistrationNumber(String input) {
        Set<String> phoneNumbersSet = new HashSet<>();
        try {
            Matcher matcher = Pattern.compile("[A-Z]{2}[0-9]{2} [A-Z]{3}|[A-Z]{2}[0-9]{2}[A-Z]{3}").matcher(input);

            while (matcher.find()) {
                phoneNumbersSet.add(matcher.group());
            }
        } catch (Exception e) {
        }
        return phoneNumbersSet;
    }

    public String readFileGetAsString(String file) {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(file), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return contentBuilder.toString();
    }


}
