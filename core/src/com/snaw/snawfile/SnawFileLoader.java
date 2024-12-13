package com.snaw.snawfile;

import com.snaw.logging.LogLevel;
import com.snaw.logging.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SnawFileLoader {
    private Pattern tagParse = Pattern.compile("[ \t]*\\[(.*)\\](.*)");
    private String endTagParseBase = "[ \t]*\\[END ";

    private Pattern commentParse = Pattern.compile("[ \t]*%.*");

    public SnawTagset load(String file) throws IOException {
        List<String> fullFile = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            while (line != null) {
                Matcher matcher = commentParse.matcher(line);
                if (!matcher.find()) {
                    fullFile.add(line);
                }
                line = reader.readLine();
            }
        }

        SnawTagset tagset = loadTagset(fullFile, null, null);
        Logger.log("Successfully loaded file.", LogLevel.DEBUG_LOAD);
        return tagset;
    }

    SnawTagset loadTagset(List<String> tagsetLines, SnawTagset parentTagset, String tagValue) {
        SnawTagset tagset = new SnawTagset(parentTagset, tagValue);
        for (int i = 0; i < tagsetLines.size(); i++) {
            String line = tagsetLines.get(i);
            if (line.trim().isEmpty()) {
                continue;
            }
            Matcher matcher = tagParse.matcher(line);
            matcher.find();
            if (matcher.groupCount() < 1) {
                Logger.log("Failed to find tag in line reading: " + line, LogLevel.DEBUG_LOAD);
                continue;
            } else {
                Logger.log("Found tag with groups: " + matcher.groupCount(), LogLevel.MAX_LOG);
            }
            String key = matcher.group(1);
            if (key.startsWith("BEGIN ")) {
                key = key.replace("BEGIN ", "");
                Pattern endTagParse = Pattern.compile(endTagParseBase + key);
                List<String> subTagset = new LinkedList<>();
                i += 1;
                while (i < tagsetLines.size()) {
                    String subLine = tagsetLines.get(i);
                    if (endTagParse.matcher(subLine).find()) {
                        break;
                    }
                    subTagset.add(subLine);
                    i += 1;
                }
                loadTagset(subTagset, tagset, key);
            } else {
                String elementValue = matcher.groupCount() >= 2 ? matcher.group(2) : null;
                tagset.addElements(new SnawTagElement(key, elementValue));
            }
        }

        return tagset;
    }
}
