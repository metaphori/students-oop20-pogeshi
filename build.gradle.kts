import de.aaschmid.gradle.plugins.cpd.Cpd

plugins {
    java
    checkstyle
    pmd
    id("de.aaschmid.cpd")
    id("com.github.spotbugs")
    `build-dashboard`
    application
}

sourceSets {
    main {
        java {
            srcDirs("src")
        }
        resources {
            srcDirs("res")
        }
    }
    test {
        java {
            srcDirs("src")
        }
    }
}

repositories {
    mavenCentral()
    flatDir {
        dirs("lib")
    }
}

val javaFXModules = listOf(
  "base",
  "controls",
  "fxml",
  "swing",
  "graphics"
)

val supportedPlatforms = listOf("linux", "mac", "win") // All required for OOP

val javaFxVersion = 13

dependencies {
    implementation("junit:junit:_")
    implementation("org.junit.jupiter:junit-jupiter-api:_")
    implementation("com.google.code.gson:gson:2.8.6")
    implementation("com.google.inject:guice:5.0.0-BETA-1")
    testImplementation(platform("org.junit:junit-bom:5.7.1"))
    runtimeOnly("org.junit.jupiter:junit-jupiter-engine")
    runtimeOnly("org.junit.vintage:junit-vintage-engine")
    for (platform in supportedPlatforms) {
      for (module in javaFXModules) {
          implementation("org.openjfx:javafx-$module:$javaFxVersion:$platform")
      }
    }
    File("lib")
        .takeIf { it.exists() }
        ?.takeIf { it.isDirectory }
        ?.listFiles()
        ?.filter { it.extension == "jar" }
        ?.forEach { implementation(files("lib/${it.name}")) }

}

tasks.withType<Test>() {
    useJUnitPlatform()
    testLogging {
      events("passed", "skipped", "failed")
    }
}

spotbugs {
    setEffort("max")
    setReportLevel("low")
    showProgress.set(true)
    val excludeFile = File("${project.rootProject.projectDir}/config/spotbugs/excludes.xml")
    if (excludeFile.exists()) {
        excludeFilter.set(excludeFile)
    }
}

tasks.withType<com.github.spotbugs.snom.SpotBugsTask> {
    ignoreFailures = true
    reports {
        create("html") {
            enabled = true
        }
    }
}

pmd {
    ruleSets = listOf()
    ruleSetConfig = resources.text.fromFile("${project.rootProject.projectDir}/config/pmd/pmd.xml")
    isIgnoreFailures = true
}

cpd {
    isIgnoreFailures = true
}

tasks.withType<Cpd> {
    reports {
        xml.setEnabled(false)
        text.setEnabled(true)
    }
    language = "java"
    minimumTokenCount = 50
    ignoreFailures = true
    source = sourceSets["main"].allJava
}

checkstyle {
    isIgnoreFailures = true
}

application {
    // Define the main class for the application
    mainClassName = "application.Launcher"
}
