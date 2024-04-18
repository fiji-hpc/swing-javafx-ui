plugins {
    java
    id("org.openjfx.javafxplugin") version "0.1.0"
}

repositories {
    mavenCentral()
}

javafx {
    version = "22.0.1"
    modules("javafx.base",
            "javafx.controls",
            "javafx.fxml",
            "javafx.graphics",
            "javafx.media",
            "javafx.swing",
            "javafx.web")
}

dependencies {
    testImplementation("junit:junit:3.8.1")
    implementation("org.projectlombok:lombok:1.18.6")
    implementation("org.slf4j:slf4j-api:1.7.26")
    implementation("org.slf4j:slf4j-simple:1.7.26")
    // This section is used for the font icons
    implementation("org.kordamp.ikonli:ikonli-core:2.6.0")
    implementation("org.kordamp.ikonli:ikonli-javafx:2.6.0")
    implementation("org.kordamp.ikonli:ikonli-materialdesign-pack:2.6.0")
}