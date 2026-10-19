package com.jenkinsmobile.model;
import java.util.List;
public class Job {
    public String _class;
    public String name;
    public String fullName;
    public String url;
    public String color;
    public String description;
    public boolean buildable;
    public List<Build> builds;
    public Build lastBuild;
    public Build lastSuccessfulBuild;
    public Build lastFailedBuild;
    public Build lastCompletedBuild;
    public List<Action> actions;
    public List<Job> jobs;
    public int nextBuildNumber;
    public List<ParameterDefinition> property;
}
