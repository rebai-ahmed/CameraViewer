package com.rebaiahmed.application;

/**
 * Created by Rebai Ahmed on 05/09/2018.
 */

public class CameraInfos {

    private String id;
    private String name;
    private String ip;
    private String port;
    private String password;

    public CameraInfos(String port, String password, String name, String ip, String id) {
        this.name = name;
        this.ip = ip;
        this.port = port;
        this.password = password;
        this.id = id;
    }

    public CameraInfos() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
