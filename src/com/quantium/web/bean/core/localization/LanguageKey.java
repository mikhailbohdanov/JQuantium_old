package com.quantium.web.bean.core.localization;

import com.quantium.web.dao.annotation.Row;

/**
 * Created by user on 10.05.2015.
 */
public class LanguageKey {

    @Row(primary = true, unsigned = true, notNull = true, autoIncrement = true)
    private int keyId;

    @Row(length = 255, unique = "code", notNull = true)
    private String key;

    @Row(length = 1023)
    private String info;

    public LanguageKey(String key, String info) {
        this.key = key;
        this.info = info;
    }

    public LanguageKey(int keyId, String key, String info) {
        this.keyId = keyId;
        this.key = key;
        this.info = info;
    }

    public int getKeyId() {
        return keyId;
    }

    public String getKey() {
        return key;
    }
    @Deprecated
    public LanguageKey setKey(String key) {
        this.key = key;
        return this;
    }

    public String getInfo() {
        return info;
    }
    public LanguageKey setInfo(String info) {
        this.info = info;
        return this;
    }
}
