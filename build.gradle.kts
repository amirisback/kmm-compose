import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm")
    id("org.jetbrains.compose")
    `maven-publish`
}

/** Artifact groupId. */
group = "com.frogobox"

/** Artifact version. Note that "SNAPSHOT" in the version is not supported by bintray. */
version = "1.0.0"

/** This is from settings.gradle.kts. */
val myArtifactId: String = rootProject.name

/** This is defined above as `group`. */
val myArtifactGroup: String = project.group.toString()

/** This is defined above as `version`. */
val myArtifactVersion: String = project.version.toString()

/** My GitHub username. */
val myGithubUsername = "amirisback"
val myGithubDescription = "Jetpack Compose KMM UI Experimental"
val myGithubHttpUrl = "https://github.com/${myGithubUsername}/${myArtifactId}"
val myGithubIssueTrackerUrl = "https://github.com/${myGithubUsername}/${myArtifactId}/issues"
val myLicense = "Apache-2.0"
val myLicenseUrl = "http://www.apache.org/licenses/LICENSE-2.0.txt"

val myDeveloperName = "Muhammad Faisal Amir"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven("https://jitpack.io")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(compose.desktop.currentOs)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "kmm-compose"
            packageVersion = "1.0.0"
        }
    }
}

val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.getByName("main").allSource)
    from("LICENCE.md") {
        into("META-INF")
    }
}

publishing {

    publications {

        register("release", MavenPublication::class) {

            from(components["java"])
            groupId = myArtifactGroup
            artifactId = myArtifactId
            version = myArtifactVersion
            artifact(sourcesJar)

            pom {
                packaging = "jar"
                name.set(myArtifactId)
                description.set(myGithubDescription)
            }

        }
    }

    repositories {
        maven("https://jitpack.io")
    }

}