package ua.com.foxminded.racing.data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

public class Visualiser {
    
    int longestNameLength;
    int longestTeamNameLength;
    
    

    
    @Test
    void test2() {
        String input = "DRR_Daniel Ricciardo_RED BULL RACING TAG HEUER";
        Pattern pattern = Pattern.compile("_(.*)_(.*)");
        Matcher matcher = pattern.matcher(input);
        matcher.find();
        String output = matcher.group(1);
        System.out.println(output);
    }
}
