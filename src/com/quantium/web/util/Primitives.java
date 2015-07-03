package com.quantium.web.util;

import com.quantium.web.bean.Url;
import com.quantium.web.bean.view.clientObjects.VideoServer;

import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Михаил on 03.09.14.
 */
public class Primitives {

    private static String charMap               = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ0123456789";
    private static int charMapLength            = 0;
    private static SimpleDateFormat date        = null;
    private static SimpleDateFormat time        = null;
    private static Random random                = null;

    static {
        charMapLength       = charMap.length();
        date                = new SimpleDateFormat("dd.MM.yyyy");
        time                = new SimpleDateFormat("HH:mm");
        random              = new Random(System.nanoTime());
    }

    // - - - Random data generators
    public static int randomInt() {
        return randomInt(0, Integer.MAX_VALUE);
    }
    public static int randomInt(int min, int max) {
        if (min >= max)
            return 0;

        return min + random.nextInt(Math.abs(max - min) + 1);
    }
    public static int randomInt(int length) {
        return randomInt((int) Math.pow(10, length - 1), (int) Math.pow(10, length));
    }

    public static String randomString() {
        return randomString(32);
    }
    public static String randomString(int length) {
        StringBuilder output = new StringBuilder();

        int charMapLength = Primitives.charMapLength - 1;
        for (int i = 0; i < length; i++)
            output.append(charMap.charAt(randomInt(0, charMapLength)));

        return output.toString();
    }


    // - - - String replacing
    public static String replaceAll(Object in, String p, String r) {
        if (in == null)
            return null;

        return replaceAll(String.valueOf(in), p, r);
    }
    public static String replaceAll(String in, String p, String r) {
        if (p.isEmpty())
            return in;

        return replaceAll(new StringBuilder(in), p, r).toString();
    }
    public static StringBuilder replaceAll(StringBuilder in, String p, String r) {
        int pl = p.length(), rl = r.length(), s = in.indexOf(p, 0);

        if (pl == 0)
            return in;

        while (s >= 0){
            in.replace(s, s + pl, r);
            s = in.indexOf(p, s + rl);
        }

        return in;
    }


    // - - - HTML escapes
    public static String htmlEncode(String in) {
        StringBuilder s = new StringBuilder(in);

        replaceAll(s, "<", "&lt;");
        replaceAll(s, ">", "&gt;");
//        replaceAll(s, "&", "&amp;");
        replaceAll(s, "\"", "&quot;");

        return s.toString();
    }
    public static String htmlDecode(String in) {
        StringBuilder s = new StringBuilder(in);

        replaceAll(s, "&lt;", "<");
        replaceAll(s, "&gt;", ">");
//        replaceAll(s, "&", "&amp;");
        replaceAll(s, "&quot;", "\"");

        return s.toString();
    }

    // - - - Url
    public static String toUrl(String in) {
        StringBuilder out = new StringBuilder(in.toLowerCase());

        replaceAll(out, "а", "a");
        replaceAll(out, "б", "b");
        replaceAll(out, "в", "v");
        replaceAll(out, "г", "g");
        replaceAll(out, "д", "d");
        replaceAll(out, "е", "ye");
        replaceAll(out, "ё", "yo");
        replaceAll(out, "ж", "zh");
        replaceAll(out, "з", "z");
        replaceAll(out, "и", "i");
        replaceAll(out, "й", "y");
        replaceAll(out, "к", "k");
        replaceAll(out, "л", "l");
        replaceAll(out, "м", "m");
        replaceAll(out, "н", "n");
        replaceAll(out, "о", "o");
        replaceAll(out, "п", "p");
        replaceAll(out, "р", "r");
        replaceAll(out, "с", "s");
        replaceAll(out, "т", "t");
        replaceAll(out, "у", "u");
        replaceAll(out, "ф", "f");
        replaceAll(out, "х", "kh");
        replaceAll(out, "ц", "ts");
        replaceAll(out, "ч", "ch");
        replaceAll(out, "ш", "sh");
        replaceAll(out, "щ", "shch");
        replaceAll(out, "ь", "");
        replaceAll(out, "ы", "i");
        replaceAll(out, "ъ", "");
        replaceAll(out, "э", "e");
        replaceAll(out, "ю", "yu");
        replaceAll(out, "я", "ya");

        in  = out.toString();
        in
            .replaceAll("[^a-z0-9]", "-")
            .replaceAll("[-]+", "-")
            .replaceAll("^-|-$", "");

        return in;
    }

    // - - - Arrays < - > primitives
    public static <T> String slice(String separator, T[] list) {
        return slice(separator, list, null, null);
    }
    public static <T> String slice(String separator, T[] list, String wrapLeft, String wrapRight) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < list.length; i++){
            sb
                    .append(wrapLeft)
                    .append(list[i])
                    .append(wrapRight);

            if (i + 1 < list.length)
                sb.append(separator);
        }

        return sb.toString();
    }

    public static <T> String slice(String separator, Collection<T> list) {
        return slice(separator, list, null, null);
    }
    public static <T> String slice(String separator, Collection<T> list, String wrapLeft, String wrapRight) {
        StringBuilder sb = new StringBuilder();

        int i = list.size();
        for (T str : list){
            sb
                    .append(wrapLeft)
                    .append(str)
                    .append(wrapRight);

            if (--i > 0)
                sb.append(separator);
        }

        return sb.toString();
    }

    public static <T> T[] push(T[] array, T element) {
        T[] out = (T[]) Array.newInstance(array.getClass().getComponentType(), array.length + 1);

        for (int i = 0; i < array.length; i++)
            out[i] = (T) array[i];
        out[out.length - 1] = element;

        return out;
    }
    public static <T> ArrayList<T> asArrayList(T[] array) {
        return new ArrayList<T>(Arrays.asList(array));
    }

    public static <T> T randomItem(Map<Integer, T> map) {
        int c = 0, i = randomInt(0, map.size() - 1);
        for (Map.Entry<Integer, T> entry : map.entrySet())
            if (c++ == i)
                return entry.getValue();

        return null;
    }

    // - - - Time date format
    public static String getDate(Timestamp timestamp) {
        return date.format(timestamp);
    }
    public static String getTime(Timestamp timestamp) {
        return time.format(timestamp);
    }
    public static String timestampFormat(Timestamp timestamp, String format) {
        return new SimpleDateFormat(format).format(timestamp);
    }

    // - - - Mathematic algoritms
    public static int pageCount(int elements, int limit) {
        if (elements == 0 || limit == 0)
            return 0;

        return elements / limit + (elements % limit > 0 ? 1 : 0);
    }
    public static int offsetByPage(int page, int perPage) {
        return (page * perPage) - perPage;
    }
}
