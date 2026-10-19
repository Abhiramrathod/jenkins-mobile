package com.jenkinsmobile.model;
public class QueueItem {
    public long id;
    public String taskName;
    public String why;
    public boolean blocked;
    public boolean buildable;
    public long inQueueSince;
    public boolean stuck;
    public String url;
    public Job task;
}
