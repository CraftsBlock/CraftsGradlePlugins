package de.craftsblock.gradle.publish.data;

public class ExtraArtifact {

    private final String name;
    private String task;
    private String classifier;
    private String extension = "jar";

    public ExtraArtifact(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getClassifier() {
        return classifier;
    }

    public void setClassifier(String classifier) {
        this.classifier = classifier;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
