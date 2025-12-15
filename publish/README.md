# Publish Plugin

![Latest Build](https://repo.craftsblock.de/api/badge/latest/releases/de/craftsblock/gradle/publish?color=40c14a&name=Latest%20Build&prefix=v)

A minimal Gradle plugin to standardize Maven publishing across CraftsBlock projects.

> ⚠️ **Developed for CraftsBlock**    
> It is **not intended to be a general-purpose publishing solution** and may change without notice.

---

## Purpose

The publish plugin provides a small, opinionated DSL for configuring Maven publishing
(artifacts, components, POM metadata, and additional artifacts) in a consistent way across
CraftsBlock projects.

It intentionally avoids excessive configuration options and follows CraftsBlock’s internal
conventions.

---

## Usage

```groovy
plugins {
    id "de.craftsblock.gradle.<plugin>" version "<version>"
}

craftsPublish {

    fromComponents(components.java)

    artifactId = "example-artifact"
    name = "Example Project"
    description = "Example project published using the Crafts Publish plugin."

    scm {
        url = "https://github.com/CraftsBlock/example"
        connection = "scm:git:git://github.com/CraftsBlock/example.git"
        developerConnection = "scm:git@github.com:CraftsBlock/example.git"
    }

    issueManagement {
        url = "https://github.com/CraftsBlock/example/issues"
    }

    license {
        name = "Apache License 2.0"
        url = "https://www.apache.org/licenses/LICENSE-2.0"
    }

    extraArtifacts {
        sources {
            task = "sourcesJar"
            classifier = "sources"
        }
    }
}
