package com.jenkinsmobile.model;
import com.google.gson.annotations.SerializedName;
import java.util.List;
public class JenkinsRoot {
    public String displayName;
    public String description;
    public List<View> views;
    public List<Job> jobs;
    public String mode;
    public int numExecutors;
}
public class View {
    public String name;
    public String url;
    public List<Job> jobs;
    public String description;
}
public class Job {
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
    public boolean _class;
    public int nextBuildNumber;
    public List<ParameterDefinition> property;
}
public class Build {
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
    public String executor;
    public int estimatedDuration;
    public List<Artifact> artifacts;
    public List<Cause> causes;
}
public class BuildHistory {
    public List<Build> builds;
}
public class Action {
    public String _class;
    public List<Parameter> parameters;
    public List<Cause> causes;
    public String text;
}
public class ParameterDefinition {
    public String _class;
    public String name;
    public String description;
    public String type;
    public boolean defaultValue;
    public Object defaultParameterValue;
    public List<String> choices;
    public boolean required;
}
public class Parameter {
    public String _class;
    public String name;
    public String value;
}
public class Cause {
    public String _class;
    public String shortDescription;
    public String userName;
}
public class ChangeSet {
    public String _class;
    public List<Item> items;
    public String kind;
}
public class Item {
    public String _class;
    public String msg;
    public String author;
    public String commitId;
    public String date;
}
public class Artifact {
    public String displayPath;
    public String fileName;
    public String relativePath;
}
public class Queue {
    public List<QueueItem> items;
    public int discoveredItems;
}
public class QueueItem {
    public long id;
    public String taskName;
    public String why;
    public boolean blocked;
    public boolean buildable;
    public long inQueueSince;
    public boolean stuck;
    public String url;
    public int buildableStartMilliseconds;
    public Job task;
}
public class Nodes {
    public List<Node> computer;
}
public class Node {
    public String displayName;
    public String name;
    public int numExecutors;
    public boolean offline;
    public String offlineCauseReason;
    public boolean idle;
    public String url;
}
