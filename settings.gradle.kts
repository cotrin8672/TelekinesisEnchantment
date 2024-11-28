pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/")
        maven("https://maven.architectury.dev/")
        maven("https://files.minecraftforge.net/maven/")
        maven("https://maven.firstdark.dev/releases")
        gradlePluginPortal()
    }
}

val modId: String by settings
rootProject.name = modId

include(":common")
include(":fabric")
include(":neoforge")
