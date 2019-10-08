package com.MeMez.CloneBots;

public class ChatObject {

    private String userName;
    private String message;
    private String ip;
    private String origin;
    private String x;
    private String y;

    public ChatObject() {
    }

    public ChatObject(String userName, String message, String x, String y, String ip, String origin) {
        super();
        this.userName = userName;
        this.message = message;
        this.x = x;
        this.y = y;
        this.ip = ip;
        this.origin = origin;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getx()
    {
      return this.x;
    }
    
    public void getx(String x)
    {
      this.x = x;
    }
    
    public String gety()
    {
      return this.y;
    }
    
    public void gety(String y)
    {
      this.y = y;
    }
    
    public String getIp()
    {
      return this.ip;
    }
    
    public void setIp(String ip)
    {
      this.ip = ip;
    }
    
    public String getOrigin()
    {
      return this.origin;
    }
    
    public void setOrigin(String origin)
    {
      this.origin = origin;
    }
    

}