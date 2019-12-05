package cn.zhaiyy.firesword.bean;

/**
 * Controller返回
 */
public class ControllerResult {
    public static int CODE_SUCC = 0;
    public static int CODE_FAIL = 500;

    private int st;
    private String msg;
    private Object data;

    public ControllerResult(int code, String msg, Object data) {
        this.st = code;
        this.msg = msg;
        this.data = data;
    }

    public static ControllerResult succ(String msg,Object data) {
        return new ControllerResult(CODE_SUCC, msg, data);
    }

    public static ControllerResult fail(String msg, Object data) {
        return new ControllerResult(CODE_FAIL, msg, data);
    }

    public int getSt() {
        return st;
    }

    public void setSt(int st) {
        this.st = st;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
