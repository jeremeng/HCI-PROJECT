package com.hci.project.model;


public class Work {

    private int id;
    private String User;
    private String name;
    private long rep;
    private long time;
    private long rest;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getRep() {
        return rep;
    }

    public void setRep(long rep) {
        this.rep = rep;
    }

    public long getRest() {
        return rest;
    }

    public void setRest(long rest) {
        this.rest = rest;
    }
    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        this.User = user;
    }
}
