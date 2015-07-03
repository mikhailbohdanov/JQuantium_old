package com.quantium.web.service.common;

import com.quantium.web.bean.core.localization.Language;
import com.quantium.web.bean.core.localization.LanguageKey;
import com.quantium.web.service.Core;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 10.05.2015.
 */
public class Localization {
    private static final HashMap<Integer, Language> languageById            = new HashMap<Integer, Language>();
    private static final HashMap<String, Language> languageByCode           = new HashMap<String, Language>();
    private static final ArrayList<Language> languageList                   = new ArrayList<Language>();

    private static final HashMap<Integer, LanguageKey> keyById              = new HashMap<Integer, LanguageKey>();
    private static final HashMap<String, LanguageKey> keyByKey              = new HashMap<String, LanguageKey>();
    private static final ArrayList<LanguageKey> keyList                     = new ArrayList<LanguageKey>();

    // - Set languages
    public static void setLanguages(ArrayList<Language> languageList) {
        languageList.clear();
        languageById.clear();
        languageByCode.clear();

        for (Language language : languageList)
            addLanguage(language);

    }
    public static void addLanguage(Language language) {
        languageList.add(language);
        languageById.put(language.getLanguageId(), language);
        languageByCode.put(language.getCode(), language);
    }

    // - Get language
    public static ArrayList<Language> getLanguages() {
        return languageList;
    }
    public static boolean hasLanguage(int languageId) {
        return languageById.containsKey(languageId);
    }
    public static boolean hasLanguage(String code) {
        return languageByCode.containsKey(code);
    }
    public static Language getLanguage(int languageId) {
        return languageById.get(languageId);
    }
    public static Language getLanguage(String code) {
        return languageByCode.get(code);
    }

    // - Remove language
    public static void clearLanguages() {
        languageById.clear();
        languageByCode.clear();
        languageList.clear();

        keyById.clear();
        keyByKey.clear();
        keyList.clear();
    }
    public static void removeLanguage(int languageId) {
        removeLanguage(languageById.get(languageId));
    }
    public static void removeLanguage(String code) {
        removeLanguage(languageByCode.get(code));
    }
    public static void removeLanguage(Language language) {
        languageList.remove(language);
        languageById.remove(language.getLanguageId());
        languageByCode.remove(language.getCode());
    }

    // - Set keys
    public static void setKeys(ArrayList<LanguageKey> keys) {
        keyById.clear();
        keyById.clear();
        keyByKey.clear();

        for (LanguageKey key : keys)
            addKey(key);
    }
    public static void addKey(LanguageKey key) {
        keyList.add(key);
        keyById.put(key.getKeyId(), key);
        keyByKey.put(key.getKey(), key);
    }

    // - Get keys
    public static ArrayList<LanguageKey> getKeys() {
        return keyList;
    }
    public static LanguageKey getKey(int keyId) {
        return keyById.get(keyId);
    }
    public static LanguageKey getKey(String key) {
        LanguageKey _key = keyByKey.get(key);

        if (_key == null)
            return core.languageKeyCreate(key, "");

        return _key;
    }

    // - Remove key
    public static void removeKey(int keyId) {
        removeKey(keyById.get(keyId));
    }
    public static void removeKey(String code) {
        removeKey(keyByKey.get(code));
    }
    public static void removeKey(LanguageKey key) {
        String _key = key.getKey();

        for (Language language : languageList)
            language.removeValue(_key);

        keyList.remove(key);
        keyById.remove(key.getKeyId());
        keyByKey.remove(_key);
    }

    private static Core core;
    @Autowired
    public void setCore(Core core) {
        Localization.core = core;
    }

}
