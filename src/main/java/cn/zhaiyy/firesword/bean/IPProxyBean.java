package cn.zhaiyy.firesword.bean;

/**
 * IP代理
 */
public class IPProxyBean {
    private String ip;
    private int port;
    private String type;
    private String address;
    private int speed;
    private String lastValidTime;

    public IPProxyBean(String ip, int port, String type, String address, int speed, String lastValidTime) {
        this.ip = ip;
        this.port = port;
        this.type = type;
        this.address = address;
        this.speed = speed;
        this.lastValidTime = lastValidTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
