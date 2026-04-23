plugins {
    java
}

group = "org.rainbowhunter"
version = "1.0.0"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.essentialsx.net/releases/")
    maven("https://repo.essentialsx.net/snapshots/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
    compileOnly("net.luckperms:api:5.4")
    compileOnly("net.essentialsx:EssentialsX:2.21.2") {
        exclude(group = "io.papermc.paper")
        exclude(group = "org.spigotmc")
    }
    compileOnly("me.clip:placeholderapi:2.11.6") {
        exclude(group = "io.papermc.paper")
        exclude(group = "org.spigotmc")
    }
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}

tasks.jar {
    archiveFileName.set("ViewDistanceControl-${version}.jar")
}
