plugins {
    id("java")
    id("application")
}

group = "dev.bogdanjovanovic"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("com.theokanning.openai-gpt3-java:service:0.18.1")
    implementation("com.microsoft.playwright:playwright:1.40.0")
}

application {
    mainClass.set("dev.bogdanjovanovic.Application")
}

// Usage: ./gradlew playwright --args="help"
tasks.register<JavaExec>("playwright") {
    classpath(sourceSets["test"].runtimeClasspath)
    mainClass.set("com.microsoft.playwright.CLI")
}

tasks.test {
    useJUnitPlatform()
}
