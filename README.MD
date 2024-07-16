# Once Human Helper

<div style="text-align:center">
    <img src="readme/logo_header.png" style="display:block; margin:auto;">
</div>

## Compose Multiplatform Application (Android / Ios)

<div style="text-align:center">
    <img src="readme/preview.gif">
</div>

# Stack
|    Feature    |                                   Lib                                    |
|:-------------:|:------------------------------------------------------------------------:|
|      DI       |               [Koin](https://github.com/InsertKoinIO/koin)               |
|  Navigation   |           [Decompose](https://github.com/arkivanov/Decompose)            |
| Design System | [Compose Cupertino](https://github.com/alexzhirkevich/compose-cupertino) |
|    Network    |            [Ktor](https://github.com/ktorio/ktor)            |
|      Map      |           [MapComposeMP](https://github.com/p-lr/MapComposeMP)           |
|      IO       |            [kotlinx-io](https://github.com/Kotlin/kotlinx-io)            |

Other libs: 
- Kotlin 
  - Coroutines
  - Serialization
- Coil
- Napier

## Before running!

- check your system with [KDoctor](https://github.com/Kotlin/kdoctor)
- install JDK 17 or higher on your machine
- add `local.properties` file to the project root and set a path to Android SDK there

### Android

To run the application on android device/emulator:

- open project in Android Studio and run imported android run configuration

To build the application bundle:

- run `./gradlew :composeApp:assembleDebug`
- find `.apk` file in `composeApp/build/outputs/apk/debug/composeApp-debug.apk`
  Run android simulator UI tests: `./gradlew :composeApp:pixel5Check`

### iOS

To run the application on iPhone device/simulator:

- Open `iosApp/iosApp.xcproject` in Xcode and run standard configuration
- Or use [Kotlin Multiplatform Mobile plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform-mobile)
  for Android Studio
  Run iOS simulator UI tests: `./gradlew :composeApp:iosSimulatorArm64Test`

