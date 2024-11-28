plugins {
    alias(libs.plugins.loom) apply false
    alias(libs.plugins.architectury)
    alias(libs.plugins.shadow) apply false
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.modPublisher) apply false
    java
}

val modGroupId: String by project
val modVersion: String by project

architectury {
    minecraft = libs.versions.minecraft.get()
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

allprojects {
    group = modGroupId
    version = modVersion
}
