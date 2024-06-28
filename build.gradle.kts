@file:OptIn(ExperimentalKotlinGradlePluginApi::class)
@file:Suppress("PropertyName")

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose.compiler).apply(false)
    alias(libs.plugins.compose).apply(false)
    alias(libs.plugins.android.library)
    alias(libs.plugins.android.application).apply(false)
    alias(libs.plugins.buildConfig).apply(false)
    alias(libs.plugins.kotlinx.serialization).apply(false)
}

val _group = findProperty("group") as String
val _jvmTarget = findProperty("jvmTarget") as String

val _minSdk = (findProperty("android.minSdk") as String).toInt()
val _compileSdk = (findProperty("android.compileSdk") as String).toInt()

subprojects {
    val libs = listOf("domain")
    if (!libs.contains(name)) return@subprojects

    plugins.apply("com.android.library")
    plugins.apply("org.jetbrains.kotlin.multiplatform")

    kotlin {
        androidTarget {
            compilerOptions {
                jvmTarget = JvmTarget.fromTarget(_jvmTarget)
            }
        }

        listOf(
            iosX64(),
            iosArm64(),
            iosSimulatorArm64()
        ).forEach {
            it.binaries.framework {
                baseName = name
                isStatic = true
            }
        }

        sourceSets {
            commonTest.dependencies {
                implementation(kotlin("test"))
            }
        }
    }

    android {
        namespace = "$_group.$name"
        compileSdk = _compileSdk
        defaultConfig { minSdk = _minSdk }
        compileOptions {
            val version = JavaVersion.toVersion(_jvmTarget)
            sourceCompatibility = version
            targetCompatibility = version
        }
    }

}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget = JvmTarget.fromTarget(_jvmTarget)
        }
    }
}

subprojects {
    tasks.register("testClasses")
}

android {
    namespace = (findProperty("group") as String)
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
}