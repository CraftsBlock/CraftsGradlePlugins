package de.craftsblock.gradle.publish;

import de.craftsblock.gradle.publish.data.ExtraArtifact;
import de.craftsblock.gradle.publish.data.PublishExtension;
import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.publish.PublishingExtension;
import org.gradle.api.publish.maven.MavenPublication;
import org.gradle.authentication.http.BasicAuthentication;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class PublishPlugin implements Plugin<@NotNull Project> {

    @Override
    public void apply(Project project) {
        project.getPlugins().apply("maven-publish");

        PublishExtension ext = project.getExtensions()
                .create("craftsPublish", PublishExtension.class, project);

        project.afterEvaluate(p -> {
            if (ext.getName() == null || ext.getName().isEmpty()) {
                return;
            }

            Path envFile = p.file(".env.repo").toPath();
            Properties env = getRepoEnv(envFile);

            String repoToDeploy = p.getVersion().toString().matches("^\\d+(?:\\.\\d+)*$")
                    ? "releases"
                    : "experimental";

            configurePublishing(p, repoToDeploy, env, ext);
        });
    }

    private @NotNull Properties getRepoEnv(Path envFile) {
        Properties env = new Properties();

        try {
            if (Files.notExists(envFile)) {
                Path parent = envFile.getParent();

                if (parent != null && Files.notExists(parent)) {
                    Files.createDirectories(parent);
                }

                Files.createFile(envFile);
            }

            try (InputStream inputStream = Files.newInputStream(envFile)) {
                env.load(inputStream);
            }
        } catch (IOException e) {
            throw new GradleException("Failed to load file " + envFile.toAbsolutePath().toFile().getPath(), e);
        }

        return env;
    }

    private void configurePublishing(Project project, String repoToDeploy, Properties env, PublishExtension ext) {
        PublishingExtension publishing = project.getExtensions().getByType(PublishingExtension.class);

        publishing.getPublications().create("mavenJava", MavenPublication.class, maven -> {
            maven.setArtifactId(ext.getArtifactId() != null ? ext.getArtifactId() : ext.getName());

            if (ext.getComponent() != null) {
                maven.from(ext.getComponent());
            }

            for (ExtraArtifact extra : ext.getExtraArtifacts()) {
                if (extra.getTask() == null || extra.getTask().isEmpty()) {
                    throw new GradleException("extraArtifact '" + extra.getName() + "' must define a task");
                }

                maven.artifact(project.getTasks().named(extra.getTask()), artifact -> {
                    if (extra.getClassifier() != null) {
                        artifact.setClassifier(extra.getClassifier());
                    }

                    if (extra.getExtension() != null) {
                        artifact.setExtension(extra.getExtension());
                    }
                });
            }

            if (ext.getPomAction() != null) {
                ext.getPomAction().execute(maven.getPom());
            }
        });

        publishing.getRepositories().maven(repo -> {
            repo.setUrl(project.uri(env.getProperty("repo." + repoToDeploy + ".url")));

            repo.getAuthentication().create("basic", BasicAuthentication.class);

            repo.getCredentials().setUsername(env.getProperty("repo." + repoToDeploy + ".username"));
            repo.getCredentials().setPassword(env.getProperty("repo." + repoToDeploy + ".password"));
        });
    }

}
