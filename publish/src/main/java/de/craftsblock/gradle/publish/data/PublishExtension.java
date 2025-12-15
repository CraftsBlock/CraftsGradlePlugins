package de.craftsblock.gradle.publish.data;

import org.gradle.api.Action;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.Project;
import org.gradle.api.component.SoftwareComponent;
import org.gradle.api.publish.maven.MavenPom;
import org.jetbrains.annotations.NotNull;

public class PublishExtension {

    private String artifactId;
    private String name;

    private SoftwareComponent component;
    private Action<@NotNull MavenPom> pom;

    private final NamedDomainObjectContainer<@NotNull ExtraArtifact> extraArtifacts;

    public PublishExtension(Project project) {
        this.extraArtifacts = project.getObjects().domainObjectContainer(ExtraArtifact.class);
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SoftwareComponent getComponent() {
        return component;
    }

    public void setComponent(SoftwareComponent component) {
        this.component = component;
    }

    public Action<@NotNull MavenPom> getPomAction() {
        return pom;
    }

    public NamedDomainObjectContainer<@NotNull ExtraArtifact> getExtraArtifacts() {
        return extraArtifacts;
    }

    public void extraArtifacts(Action<? super NamedDomainObjectContainer<@NotNull ExtraArtifact>> action) {
        action.execute(extraArtifacts);
    }

    public void pom(Action<@NotNull MavenPom> action) {
        this.pom = action;
    }
}
