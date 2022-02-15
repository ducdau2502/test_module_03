package Regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate {
    public Validate() {
    }

    public boolean validateNameProduct(String keyword, String regex) {
        keyword = keyword.toLowerCase();
        String NAME_REGEX = ".*" + keyword + ".*";
        Pattern pattern = Pattern.compile(NAME_REGEX);
        Matcher matcher = pattern.matcher(regex.toLowerCase());
        return matcher.matches();
    }
}