import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    kotlin("plugin.serialization") version "1.9.20"
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    jvm("desktop")
//
    sourceSets {
        val desktopMain by getting
        val desktopTest by getting
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            // put your Multiplatform dependencies here
//            implementation(projects.domain)
//            implementation(libs.kotlinx.coroutines.core)
//            implementation(libs.ktor.client.core)
//            implementation(libs.ktor.client.content.negotiation)
//            implementation(libs.ktor.serialization.kotlinx.json)
//            implementation(libs.koin.core)
//            implementation(libs.sql.coroutines.extensions)
        }
        androidMain.dependencies {
//            implementation(libs.ktor.client.android)
//            implementation(libs.sql.android.driver)
//            implementation(libs.koin.android)
        }
        androidInstrumentedTest.dependencies {
            implementation(libs.androidx.testExt.junit)
            implementation(libs.androidx.core)
            implementation(libs.kotlinx.coroutines.test)

            implementation(kotlin("test"))
            implementation(libs.androidx.runner)
            implementation(libs.androidx.junit.v115)
            implementation(libs.androidx.core)
            implementation(libs.kotlinx.coroutines.test.v181)
        }
        iosMain.dependencies {
//            implementation(libs.ktor.client.darwin)
//            implementation(libs.sql.native.driver)
//            implementation(libs.stately.common)
        }
        desktopMain.dependencies {
            implementation(libs.kotlinx.coroutinesSwing)
            implementation(libs.kotlinx.coroutines.core)
//            implementation("")// https://mvnrepository.com/artifact/net.java.dev.jna/jna
            implementation(libs.jna)
//            implementation(libs.sql.jvm.driver)
        }
        desktopTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.kotlinx.coroutines.test)
        }


//    }
    }

    android {

        namespace = "kmp.learn.copynews.data"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
//    val key: String = gradleLocalProperties(rootDir).getProperty("NEWS_API_KEY")

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }
        defaultConfig {
            minSdk = libs.versions.android.minSdk.get().toInt()
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        }


        buildFeatures {
            buildConfig = true
        }
//    buildTypes {
//        getByName("debug") {
//            buildConfigField("String", "key", key)
//        }
//    }
    }
}

//sqldelight {
//    databases {
//        create(name = "SampleDataBase") {
//            packageName.set("kmp.learn.copynews")
//        }
//    }
//}