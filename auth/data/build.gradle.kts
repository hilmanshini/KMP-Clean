import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.sqlDelight)
    kotlin("plugin.serialization") version "1.9.20"
//    alias(libs.plugins.kotlinxSerialization)
//    alias(libs.plugins.sqldelight)
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
        commonTest.dependencies {

        }
        commonMain.dependencies {
            implementation(project(":shared"))
//            implementation(libs.kotlinx.coroutines.core)
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
//            implementation(libs.androidx.testExt.junit)
//            implementation(libs.androidx.core)
//            implementation(libs.kotlinx.coroutines.test)
//
//            implementation(kotlin("test"))
//            implementation(libs.androidx.runner)
//            implementation(libs.androidx.junit.v115)
//            implementation(libs.androidx.core)
//            implementation(libs.kotlinx.coroutines.test.v181)
        }
        iosMain.dependencies {
//            implementation(libs.ktor.client.darwin)
//            implementation(libs.sql.native.driver)
//            implementation(libs.stately.common)
        }
        desktopMain.dependencies {
            implementation(libs.ktor.client.core)// https://mvnrepository.com/artifact/io.ktor/ktor-client-cio-jvm
            implementation(libs.ktor.client.cio)// https://mvnrepository.com/artifact/io.ktor/ktor-client-content-negotiation
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.koin.core)
        }
        desktopTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.kotlinx.coroutines.test)
            //
//            implementation(kotlin("test"))
//            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.koin.test)
// Needed JUnit version
//            implementation(libs.koin.test.junit4)
            // https://mvnrepository.com/artifact/io.insert-koin/koin-test-junit4
            implementation(libs.koin.test.junit4)
            implementation(project(":shared"))

        }


//    }
    }

    android {

        namespace = "kmp.learn.copynews.shared"
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