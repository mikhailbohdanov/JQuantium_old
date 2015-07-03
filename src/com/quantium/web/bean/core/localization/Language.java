package com.quantium.web.bean.core.localization;

import com.quantium.web.dao.annotation.Row;
import com.quantium.web.dao.annotation.Table;
import com.quantium.web.service.common.Localization;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by user on 10.05.2015.
 */

@Table("core_languages")
public class Language {

    @Row(primary = true, unsigned = true, notNull = true, autoIncrement = true)
    private int languageId;

    @Row(length = 2, unique = "languageCode", notNull = true)
    private String code;

    @Row(length = 63)
    private String name;

    @Row(defaultValue = "0")
    private boolean enabled;

    private TreeMap<String, LanguageValue> values       = new TreeMap<>();
    private ArrayList<LanguageValue> valueList = new ArrayList<>();

    public Language(int languageId, String code, String name, boolean enabled) {
        this.languageId = languageId;
        this.code = code;
        this.name = name;
        this.enabled = enabled;
    }
    public Language(String code, String name, boolean enabled) {
        this.code = code;
        this.name = name;
        this.enabled = enabled;
    }

    public int getLanguageId() {
        return languageId;
    }

    public String getCode() {
        return code;
    }
    @Deprecated
    public Language setCode(String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }
    public Language setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }
    public Language setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public ArrayList<LanguageValue> getValueList() {
        return valueList;
    }
    public LanguageValue getValue(String key) {
        if (!values.containsKey(key))
            Localization.getKey(key);

        return values.get(key);
    }
    public String get(String key) {
        LanguageValue value = getValue(key);

        if (value != null)
            return value.getValue();

        return null;
    }
    public Language setValues(ArrayList<LanguageValue> values) {
        if (values != null)
            for (LanguageValue value : values)
                addValue(value);

        return this;
    }
    public Language addValue(LanguageValue value) {
        if (value != null) {
            LanguageKey key = Localization.getKey(value.getKeyId());

            valueList.add(value);
            values.put(key.getKey(), value);
        }

        return this;
    }

    public Language updateKey(int keyId, String newKey) {
        LanguageValue value;

        for (Iterator<Map.Entry<String, LanguageValue>> it = values.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, LanguageValue> entry = it.next();

            value = entry.getValue();
            if (value.getKeyId() == keyId) {
                it.remove();
                values.put(newKey, value);
                break;
            }
        }

        return this;
    }

    public Language removeValue(String key) {
        LanguageValue value = values.get(key);

        if (value != null) {
            values.remove(key);
            valueList.remove(value);
        }

        return this;
    }
}
