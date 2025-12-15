# CraftsBlock Gradle Plugins

![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/CraftsBlock/CraftsGradlePlugins)
![GitHub](https://img.shields.io/github/license/CraftsBlock/CraftsGradlePlugins)
![GitHub all releases](https://img.shields.io/github/downloads/CraftsBlock/CraftsGradlePlugins/total)
![GitHub issues](https://img.shields.io/github/issues-raw/CraftsBlock/CraftsGradlePlugins)

A collection of Gradle plugins developed and maintained for internal use at **CraftsBlock**.

> ⚠️ **Developed for CraftsBlock**  
> This repository bundles multiple Gradle plugins that are **exclusively intended for CraftsBlock projects**.  
> The APIs are opinionated, may change without notice, and are **not designed for public use**.

---

## Overview

This repository serves as a **central place for all CraftsBlock Gradle plugins**.
The plugins focus on standardizing build logic across projects, such as:

- Publishing conventions
- Build and artifact metadata
- Internal infrastructure integration
- Shared build patterns

Each plugin is versioned and published from this repository.

---

## Included Plugins

| Plugin ID                       | Description                                | Latest Build                                                                                                                                   |
|---------------------------------|--------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------|
| `de.craftsblock.gradle.publish` | Opinionated Maven publishing configuration | ![Latest Build](https://repo.craftsblock.de/api/badge/latest/releases/de/craftsblock/gradle/publish?color=40c14a&name=Latest%20Build&prefix=v) |

> Additional plugins may be added over time.

---

## Usage

Plugins from this repository are intended to be used via an internal plugin repository.

settings.gradle:
```groovy
pluginManagement {
    repositories {
        maven {
            url = uri("https://repo.craftsblock.de/releases")
        }
        gradlePluginPortal()
    }
}
```

build.gradle:
```groovy
plugins {
    id "de.craftsblock.gradle.<plugin>" version "<version>"
}
```