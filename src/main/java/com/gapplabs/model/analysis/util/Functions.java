package com.gapplabs.model.analysis.util;

import java.util.regex.Matcher;

public class Functions {
    
    private String identify;
    private String [] params;
    private String body;
    private int start, end;
    private boolean function;
    private Matcher matcher;

    public Functions(String identify, String[] params, String body, int start, int end, boolean function, Matcher matcher) {
        this.identify = identify;
        this.params = params;
        this.body = body;
        this.start = start;
        this.end = end;
        this.function = function;
        this.matcher = matcher;
    }
    
    

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public boolean isFunction() {
        return function;
    }

    public void setFunction(boolean function) {
        this.function = function;
    }

    public Matcher getMatcher() {
        return matcher;
    }

    public void setMatcher(Matcher matcher) {
        this.matcher = matcher;
    }

    
}
