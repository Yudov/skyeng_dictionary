buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        val kotlinVersion = "1.5.30"
        classpath("com.android.tools.build:gradle:7.0.1")
        classpath(kotlin("gradle-plugin", version = kotlinVersion))
        classpath(kotlin("serialization", version = kotlinVersion))
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.38.1")
        classpath("io.realm:realm-gradle-plugin:10.7.0")
    }
}