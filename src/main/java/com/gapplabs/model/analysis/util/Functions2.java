package com.gapplabs.model.analysis.util;

import java.util.Arrays;
import java.util.regex.Matcher;

public class Functions2 {
    
    private String identify;
    private String [] params;
    private String body;
    private int start, end;
    private Matcher matcher;
    
    private String code;
    private int startCode, endCode;
    private Matcher matcherCode;

    public Functions2(String identify, String[] params, int start, int end, Matcher matcher) {
        this.identify = identify;
        this.params = params;
        this.start = start;
        this.end = end;
        this.matcher = matcher;
    }
    
    public Functions2(String identify, String[] params, String body, int start, int end, Matcher matcher) {
        this(identify, params, start, end, matcher);
        this.body = body;
    }
    
    public Functions2(String identify, String[] params, String body, int start, int end, Matcher matcher, String code, int startCode, int endCode, Matcher matcherCode) {
        this(identify, params, body, start, end, matcher);
        this.code = code;
        this.startCode = startCode;
        this.endCode = endCode;
        this.matcherCode = matcherCode;
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

    public Matcher getMatcher() {
        return matcher;
    }

    public void setMatcher(Matcher matcher) {
        this.matcher = matcher;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getStartCode() {
        return startCode;
    }

    public void setStartCode(int startCode) {
        this.startCode = startCode;
    }

    public int getEndCode() {
        return endCode;
    }

    public void setEndCode(int endCode) {
        this.endCode = endCode;
    }

    public Matcher getMatcherCode() {
        return matcherCode;
    }

    public void setMatcherCode(Matcher matcherCode) {
        this.matcherCode = matcherCode;
    }
    
    public void printAttributes() {
        System.out.println("identify: " + identify);
        System.out.println("params: " + Arrays.toString(params));
        System.out.println("body: " + body);
        System.out.println("start: " + start);
        System.out.println("end: " + end);
        System.out.println("matcher: " + matcher);
        System.out.println("code: " + code);
        System.out.println("startCode: " + startCode);
        System.out.println("endCode: " + endCode);
        System.out.println("matcherCode: " + matcherCode);
    }
}
