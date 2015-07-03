package com.quantium.web.bean.core;

import com.quantium.web.util.Primitives;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by FREEMAN on 10.02.15.
 */
public class StringPattern extends StringTemplate {
    private Pattern pattern;

    private StringPattern() {
        super();
    }
    public StringPattern(String original) {
        super(original);

        if (super.keys != null && super.keys.size() > 0) {
            StringBuilder _pattern      = new StringBuilder(original.replaceAll("([\\(\\)\\[\\]\\/.^$|?+]{1,1})", "\\\\$0"));

            for (Map.Entry<String, Integer> entry : super.keys.entrySet())
                Primitives.replaceAll(_pattern, super.keyStart + entry.getKey() + super.keyEnd, "(.*)");

            _pattern.insert(0, "^").append("$");
            this.pattern    = Pattern.compile(_pattern.toString());
        }
    }

    public boolean match(Object object) {
        if (object == null)
            return false;

        if (!super.isPattern)
            return original.equals(object);

        return this.pattern.matcher(object.toString()).matches();
    }

    public HashMap<String, String> getValues(Object source) {
        if (source == null)
            return null;

        Matcher matcher = this.pattern.matcher(source.toString());


        HashMap<String, String> keyValues = null;
        if (matcher.matches()){
            int i = 1;

            keyValues = new HashMap<String, String>();

            for (Map.Entry<String, Integer> entry : super.keys.entrySet())
                keyValues.put(entry.getKey(), matcher.group(i++));
        }

        return keyValues;
    }
}
