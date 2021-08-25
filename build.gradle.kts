buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        val kotlinVersion = "1.5.30"
        classpath("com.android.tools.build:gradle:7.0.0")
        classpath(kotlin("gradle-plugin", version = kotlinVersion))
        classpath(kotlin("serialization", version = kotlinVersion))
    }
}