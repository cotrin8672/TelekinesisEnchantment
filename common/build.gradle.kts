plugins {
    alias(libs.plugins.loom)
    alias(libs.plugins.architectury)
    alias(libs.plugins.kotlin)
}

architectury {
    common("neoforge", "fabric")
}

@Suppress("UnstableApiUsage")
dependencies {
    minecraft(libs.minecraft)
    mappings(loom.layered {
        mappings("net.fabricmc:yarn:${libs.versions.yarnFabric.get()}")
        mappings(libs.yarn.neoforge)
    })

    modImplementation(libs.fabric.loader)
    modImplementation(libs.architectury)
}

java {
    withSourcesJar()

    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(21)
}
