package com.quantium.web.bean;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Михаил on 26.08.14.
 */
public final class Url {
    public static String URLElementDecode(String element) {
        try {
            return URLDecoder.decode(element, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
    public static String URLEncodeEncode(String element) {
        try {
            return URLEncoder.encode(element, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static HashMap<String, ArrayList<String>> parseSearch(String search) {
        HashMap<String, ArrayList<String>> out = new HashMap<String, ArrayList<String>>();

        String[] parts = search.split("&"), keyValue;
        String key;
        ArrayList<String> values;
        for (int i = 0; i < parts.length; i++) {
            keyValue = parts[i].split("=");

            key = keyValue[0].replaceAll("\\[.*\\]$", "");

            if (!out.containsKey(key)) {
                values  = new ArrayList<String>();
                out.put(key, values);
            } else
                values  = out.get(key);

            values.add(URLElementDecode(keyValue[1]));
        }

        return out;
    }

    private String protocol;
    private String userName;
    private String password;
    private String host;
    private int port;
    private String path;
    private HashMap<String, ArrayList<String>> search;
    private String hash;

    public Url() {}
    public Url(String pattern) {
        parse(pattern, this);
    }
    public Url(HttpServletRequest request) {
        this.path = request.getRequestURI().replaceAll("/$", "");
        if (this.path.isEmpty())
            this.path = "/";

        ArrayList<String> _values;
        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            _values = new ArrayList<String>();

            for (String value : entry.getValue())
                _values.add(value);

            this.setSearch(entry.getKey(), _values);
        }
    }

    public static Url parse(String pattern) {
        return parse(pattern, new Url());
    }
    public static Url parse(String pattern, Url context) {
        String tmp;
        int index;
        String[] vars;

        if (context == null)
            context = new Url();

        URL url = null;
        try {
            url = new URL(pattern);
        } catch (MalformedURLException e) {}

        if (url != null) {
            if ((tmp = url.getProtocol()) != null)
                context.protocol    = tmp;

            if ((tmp = url.getUserInfo()) != null) {
                vars    = tmp.split(":");

                if (vars.length > 1) {
                    context.userName    = vars[0];
                    context.password    = vars[1];
                } else
                    context.userName    = vars[0];
            }

            if ((tmp = url.getHost()) != null)
                context.host    = tmp;

            if ((index = url.getPort()) > 0)
                context.port    = index;

            if ((tmp = url.getPath()) != null)
                context.path    = tmp;

            if ((tmp = url.getQuery()) != null)
                context.parseSearch(tmp);

            if ((tmp = url.getRef()) != null)
                context.hash    = tmp;
        }

        return context;
    }

    public Url copy() {
        Url url = new Url();

        url.protocol    = this.protocol;
        url.userName    = this.userName;
        url.password    = this.password;
        url.host        = this.host;
        url.port        = this.port;
        url.path        = this.path;
        url.copySearch(this.search);
        url.hash        = this.hash;

        return url;
    }

    public String getProtocol() {
        return protocol;
    }
    public Url setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public String getUserName() {
        return userName;
    }
    public Url setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }
    public Url setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getHost() {
        return host;
    }
    public Url setHost(String host) {
        this.host = host;
        return this;
    }

    public int getPort() {
        return port;
    }
    public Url setPort(int port) {
        this.port = port;
        return this;
    }

    public String getPath() {
        return path;
    }
    public Url setPath(String path) {
        this.path = path;
        return this;
    }

    public ArrayList<String> getSearchAll(String key) {
        if (key == null || search == null)
            return null;

        return search.get(key);
    }
    public String getSearch(String key) {
        return getSearch(key, 0);
    }
    public String getSearch(String key, int index) {
        if (key == null || this.search == null || !this.search.containsKey(key))
            return null;

        return this.search.get(key).get(index);
    }
    public Url addSearch(String key, String value) {
        return setSearch(key, -1, value);
    }
    public Url setSearch(String key, String value) {
        return setSearch(key, -2, value);
    }
    public Url setSearch(String key, int index, String value) {
        if (search == null)
            search  = new HashMap<String, ArrayList<String>>();

        ArrayList<String> values;
        if (search.containsKey(key))
            values  = search.get(key);
        else
            values  = search.put(key, new ArrayList<String>());

        if (index >= 0)
            values.set(index, value);
        else if (index == -1)
            values.add(value);
        else {
            values.clear();
            values.add(value);
        }

        return this;
    }
    public Url setSearch(String key, ArrayList<String> values) {
        if (search == null)
            search  = new HashMap<String, ArrayList<String>>();

        search.put(key, values);

        return this;
    }
    public Url setSearch(Map<String, ArrayList<String>> search) {
        this.search = (HashMap<String, ArrayList<String>>) search;
        return this;
    }
    public Url copySearch(Map<String, ArrayList<String>> search) {
        this.search = new HashMap<String, ArrayList<String>>();

        for (Map.Entry<String, ArrayList<String>> entry : search.entrySet())
            this.search.put(entry.getKey(), new ArrayList<String>(entry.getValue()));

        return this;
    }
    public Url setSearch(String search) {
        this.search = parseSearch(search);
        return this;
    }
    public Url deleteSearch(String key) {
        if (search != null)
            search.remove(key);

        return this;
    }
    public Url deleteSearch(String key, int index) {
        if (search != null && search.containsKey(key))
            search.get(key).remove(index);

        return this;
    }
    public boolean matchSearch(String search) {
        if (this.search == null)
            return false;

        String key;

        for (Map.Entry<String, ArrayList<String>> entry : parseSearch(search).entrySet()) {
            key = entry.getKey();

            if (!this.search.containsKey(key))
                return false;

            if (!this.search.get(key).containsAll(entry.getValue()))
                return false;
        }

        return true;
    }

    public String getHash() {
        return hash;
    }
    public Url setHash(String hash) {
        this.hash = hash;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();

        _host(out);
        _path(out);
        _search(out);
        _hash(out);

        return out.toString();
    }

    public String toString(String... data) {
        StringBuilder out = new StringBuilder();

        int i = 0, length = (int) Math.floor(data.length / 2);

        HashMap<String, String> replace     = new HashMap<String, String>(length);

        for (; i < length; i++)
            replace.put(data[i * 2], data[(i * 2) + 1]);

        _host(out);
        _path(out);
        _search(out, replace);
        _hash(out);

        return out.toString();
    }

    private void _host(StringBuilder out) {
        if (host != null) {
            if (protocol != null)
                out
                        .append(protocol.toLowerCase())
                        .append("://");

            if (userName != null) {
                out.append(userName);

                if (password != null)
                    out
                            .append(":")
                            .append(password);

                out.append("@");
            }

            if (host != null)
                out.append(host);

            if (port > 0)
                out
                        .append(":")
                        .append(port);
        }
    }
    private void _path(StringBuilder out) {
        if (path != null)
            out
                    .append(path);
    }
    private void _search(StringBuilder out) {
        _search(out, null);
    }
    private void _search(StringBuilder out, HashMap<String, String> replace) {
        int i, j;
        if (search != null && search.size() > 0) {
            out.append("?");

            i = search.size();
            for (Map.Entry<String, ArrayList<String>> entry : search.entrySet()) {
                if (replace == null || !replace.containsKey(entry.getKey())) {
                    if (entry.getValue().size() == 1)
                        out
                                .append(entry.getKey())
                                .append("=")
                                .append(URLEncodeEncode(entry.getValue().get(0)));
                    else {
                        j = entry.getValue().size();
                        for (String _value : entry.getValue()) {
                            out
                                    .append(entry.getKey())
                                    .append("[]=")
                                    .append(URLElementDecode(_value));

                            if (j-- > 1)
                                out.append("&");
                        }
                    }

                    if (i-- > 1)
                        out.append("&");
                }
            }
        }

        if (replace != null && replace.size() > 0) {
            if (search == null || search.size() == 0)
                out.append("?");

            String lastChar = String.valueOf(out.charAt(out.length()-1));

            if (!"?".equals(lastChar) && !"&".equals(lastChar))
                out.append("&");

            i = replace.size();

            for (Map.Entry<String, String> entry : replace.entrySet()) {
                out
                        .append(entry.getKey())
                        .append("=")
                        .append(URLEncodeEncode(entry.getValue()));

                if (i-- > 1)
                    out.append("&");
            }
        }
    }
    private void _hash(StringBuilder out) {
        if (hash != null)
            out
                    .append("#")
                    .append(hash);
    }
}
