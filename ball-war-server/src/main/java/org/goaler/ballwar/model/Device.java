package org.goaler.ballwar.model;

import java.io.Serializable;

public class Device implements Serializable{
  
  private static final long serialVersionUID = 6598825580378261667L;
  
  private String type;
  private String ip;
  private String mac;
  private int width;
  private int height;
  private String sysVersion;
  
  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }
  public String getIp() {
    return ip;
  }
  public void setIp(String ip) {
    this.ip = ip;
  }
  public String getMac() {
    return mac;
  }
  public void setMac(String mac) {
    this.mac = mac;
  }
  public int getWidth() {
    return width;
  }
  public void setWidth(int width) {
    this.width = width;
  }
  public int getHeight() {
    return height;
  }
  public void setHeight(int height) {
    this.height = height;
  }
  public String getSysVersion() {
    return sysVersion;
  }
  public void setSysVersion(String sysVersion) {
    this.sysVersion = sysVersion;
  }
  
}
