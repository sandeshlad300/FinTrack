// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("com.google.gms.google-services") version "4.4.4" apply false
    id("com.google.firebase.crashlytics") version "2.9.9" apply false
    id("org.sonarqube") version "7.2.0.6526"

}

sonarqube {
    properties {
        property("sonar.projectKey", "FinTrack")
        property("sonar.projectName", "FinTrack")
        property("sonar.host.url", "http://localhost:9000")
        property("sonar.login", providers.gradleProperty("SONAR_TOKEN").get())

        // Android specifics
        property("sonar.sources", "src/main/java,src/main/kotlin")
        property("sonar.tests", "src/test/java,src/androidTest/java")
        property("sonar.java.binaries", "build/intermediates/javac")
    }
}
