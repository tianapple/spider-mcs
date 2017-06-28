package org.spider.mcs.core;

/**
 * Created by wow on 2017/3/21.
 */
public class ResultMessage {

    public static final String SUCCESS = "0000";
    public static final String FAILE = "9999";

    // 状态码
    private String retnCode;

    // 错误信息
    private String retnMessage;

    public ResultMessage() {
    }

    public String getRetnCode() {
        return retnCode;
    }

    public void setRetnCode(String retnCode) {
        this.retnCode = retnCode;
    }

    public String getRetnMessage() {
        return retnMessage;
    }

    public void setRetnMessage(String retnMessage) {
        this.retnMessage = retnMessage;
    }

    public ResultMessage(String retnCode, String retnMessage) {
        this.retnCode = retnCode;
        this.retnMessage = retnMessage;
    }
}
