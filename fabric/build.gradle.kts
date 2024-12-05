import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import com.hypherionmc.modpublisher.properties.CurseEnvironment
import com.hypherionmc.modpublisher.properties.ModLoader
import net.fabricmc.loom.task.RemapJarTask

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

base {
    val modId: String by project
    val modVersion: String by project
    archivesName = modId
    version = "$modVersion-mc${libs.versions.minecraft.get()}-${project.name}"
}

publisher {
    val modVersion: String by project
    val modId: String by project
    val modName: String by project

    apiKeys {
        curseforge(System.getenv("CURSE_FORGE_API_KEY"))
        modrinth(System.getenv("MODRINTH_API_KEY"))
    }

    curseID.set("1153921")
    modrinthID.set("4FNR0Qmb")

    versionType.set("release")
    changelog.set(file("../changelog.md"))
    version.set(project.version.toString())
    displayName.set("$modName ${project.version}")
    gameVersions.set(listOf(libs.versions.minecraft.get()))
    setLoaders(ModLoader.FABRIC)
    setCurseEnvironment(CurseEnvironment.BOTH)
    artifact.set("build/libs/${base.archivesName}-${project.version}.jar")

    curseDepends {
        required("fabric-api", "fabric-language-kotlin")
    }

    modrinthDepends {
        required("fabric-api", "fabric-language-kotlin")
    }
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

sourceSets {
    main {
        resources.srcDir(project(":common").file("src/main/resources"))
    }
}

@Suppress("UnstableApiUsage")
dependencies {
    minecraft(libs.minecraft)
    mappings(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-${libs.versions.minecraft.get()}:2024.11.10@zip")
    })

    modImplementation(libs.fabric.loader)
    modImplementation(libs.fabric.api)
    modImplementation(libs.fabric.kotlin)

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
