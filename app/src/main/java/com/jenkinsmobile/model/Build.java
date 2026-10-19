package com.jenkinsmobile.model;
import java.util.List;
public class Build {
    public String _class;
    public int number;
    public String url;
    public String result;
    public long duration;
    public long timestamp;
    public String displayName;
    public String fullDisplayName;
    public String id;
    public boolean building;
    public List<Action> actions;
    public List<ChangeSet> changeSets;
    public String builtOn;
    public int estimatedDuration;
    public List<Artifact> artifacts;
    public List<Cause> causes;
}
