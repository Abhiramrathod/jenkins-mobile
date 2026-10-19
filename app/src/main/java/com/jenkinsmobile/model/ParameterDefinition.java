package com.jenkinsmobile.model;
import com.google.gson.JsonObject;
import java.util.List;
public class ParameterDefinition {
    public String _class;
    public String name;
    public String description;
    public String type;
    public JsonObject defaultParameterValue;
    public List<String> choices;
}
