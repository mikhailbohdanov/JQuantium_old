package com.quantium.web.bean.core.localization;

import com.quantium.web.dao.annotation.Row;

/**
 * Created by user on 10.05.2015.
 */
public class LanguageValue {

    @Row(primary = true, notNull = true, unsigned = true, unique = "valueId")
    private int languageId;

    @Row(primary = true, notNull = true, unsigned = true, unique = "valueId")
    private int keyId;

    @Row(length = 4095)
    private String value;

    public LanguageValue(int languageId, int keyId, String value) {
        this.languageId = languageId;
        this.keyId = keyId;
        this.value = value;
    }

    public int getLanguageId() {
        return languageId;
    }

    public int getKeyId() {
        return keyId;
    }

    public String getValue() {
        return value;
    }
    public LanguageValue setValue(String value) {
        this.value = value;
        return this;
    }
}
