import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import com.hypherionmc.modpublisher.properties.CurseEnvironment
import com.hypherionmc.modpublisher.properties.ModLoader
import net.fabricmc.loom.task.RemapJarTask
import kotlin.Suppress

plugins {
    alias(libs.plugins.loom)
    alias(libs.plugins.architectury)
    alias(libs.plugins.shadow)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.modPublisher)
}

architectury {
    platformSetupLoomIde()
    fabric()
}

publisher {
    val modVersion: String by project
    val modId: String by project
    val modName: String by project

    apiKeys {
        curseforge(System.getenv("CURSE_FORGE_API_KEY"))
        modrinth(System.getenv("MODRINTH_API_KEY"))
    }

    // TODO: set project id
    curseID.set("")
    modrinthID.set("")

    versionType.set("release")
    changelog.set(file("../changelog.md"))
    version.set(modVersion)
    displayName.set("$modName-${project.name}-$modVersion")
    gameVersions.set(listOf(libs.versions.minecraft.get()))
    setLoaders(ModLoader.FABRIC)
    setCurseEnvironment(CurseEnvironment.BOTH)
    artifact.set("build/libs/$modId-${project.name}-$modVersion.jar")

    curseDepends {
        required("fabric-api", "fabric-language-kotlin")
    }

    modrinthDepends {
        required("fabric-api", "fabric-language-kotlin")
    }
}

base {
    val modId: String by project

    archivesName = "$modId-${project.name}"
}

configurations {
    val common by creating {
        isCanBeResolved = true
        isCanBeConsumed = false
    }
    val compileClasspath by getting {
        extendsFrom(common)
    }

    val runtimeClasspath by getting {
        extendsFrom(common)
    }

    val developmentFabric by getting {
        extendsFrom(common)
    }

    val shadowBundle by creating {
        isCanBeResolved = true
        isCanBeConsumed = false
    }
}

@Suppress("UnstableApiUsage")
        dependencies {
    minecraft(libs.minecraft)
    mappings(loom.layered {
        mappings("net.fabricmc:yarn:${libs.versions.yarnFabric.get()}")
        mappings(libs.yarn.neoforge)
    })

    modImplementation(libs.fabric.loader)
    modImplementation(libs.fabric.api)
    modImplementation(libs.fabric.kotlin)
    modImplementation(libs.architectury.fabric)

    "common"(project(path = ":common", configuration = "namedElements")) { isTransitive = false }
    "shadowBundle"(project(path = ":common", configuration = "transformProductionFabric"))
}

tasks.withType<ProcessResources>().configureEach {
    val modId: String by project
    val modName: String by project
    val modLicense: String by project
    val modVersion: String by project
    val modAuthors: String by project
    val modDescription: String by project

    val replaceProperties = mapOf(
            "minecraftVersion" to libs.versions.minecraft.get(),
            "fabricVersion" to libs.versions.fabricLoader.get(),
            "architecturyVersion" to libs.versions.architecturyApi.get(),
            "modId" to modId,
            "modName" to modName,
            "modLicense" to modLicense,
            "modVersion" to modVersion,
            "modAuthors" to modAuthors,
            "modDescription" to modDescription,
    )
    inputs.properties(replaceProperties)

    filesMatching("fabric.mod.json") {
        expand(replaceProperties)
    }

    val commonResourcesDir = project(":common").layout.projectDirectory.dir("src/main/resources")
    from(commonResourcesDir) {
        include("logo.png")
    }
    into("src/main/resources")
}

java {
    withSourcesJar()

    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.named<ShadowJar>("shadowJar") {
    configurations = listOf(project.configurations.getByName("shadowBundle"))
    archiveClassifier.set("dev-shadow")
}

tasks.named<RemapJarTask>("remapJar") {
    input.set(tasks.named<ShadowJar>("shadowJar").get().archiveFile)
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(21)
}
