# AGENTS.md

Guidance for AI coding agents (and humans) working on this repository.

## Project

GPKiosk (`org.gpfister.aosp.kiosk`) is an MIT-licensed kiosk app for a **single custom AOSP 16 device (API 36)**.
The target screen is a **Corsair Xeneon Edge 14.5" (2560×720 px, ~183 PPI)**, used in **landscape (2560×720 px) or
portrait (720×2560 px)**.
There is no need to support older Android versions or Google Mobile Services: do not add Play Services/Firebase
dependencies, and do not add backward-compatibility code for API < 36.

## Stack

- Kotlin + Jetpack Compose + Material 3, single `:app` module.
- Android Gradle Plugin 9 with built-in Kotlin support: do **not** apply `org.jetbrains.kotlin.android`.
- `compileSdk` 37.2 (required by current AndroidX), `targetSdk` and `minSdk` 36. Keep `targetSdk` at the device's
  API level; the `OldTargetApi` lint check is disabled on purpose in `app/lint.xml`.
- JDK 21: the Gradle daemon is pinned in `gradle/gradle-daemon-jvm.properties`, and Kotlin uses `jvmToolchain(21)`.
- All dependency and plugin versions live in `gradle/libs.versions.toml`. Never hardcode versions in build scripts.
  Compose libraries are versioned by the Compose BOM.

## Commands

```sh
./gradlew assembleDebug          # build
./gradlew lint test              # must pass with zero lint issues before you finish
./gradlew connectedAndroidTest   # needs a device or emulator (adb devices)
./gradlew installDebug           # install on the connected device
```

## Layout

```
app/src/main/java/org/gpfister/aosp/kiosk/
├── MainActivity.kt          # entry point, Compose content
├── ui/preview/              # XeneonEdgePreviews: preview annotation for the target display
└── ui/theme/                # Color.kt, Type.kt, Theme.kt (GPKioskTheme)
app/src/main/res/            # strings, window themes (values / values-night), launcher icon
app/src/androidTest/         # Compose UI tests
```

## Conventions

- UI is Compose-only; no XML layouts or Fragments.
- **No scrolling:** every screen must fit both the 2560×720 px landscape and the 720×2560 px portrait viewports.
  Do not use `verticalScroll`, `LazyColumn` or other scrollable containers for screen content; size content to the
  available space instead (weights, `BoxWithConstraints`, `TextAutoSize`). `MainActivityTest` asserts there are no
  scrollable nodes.
- Do not lock the orientation (`screenOrientation`): the app must follow the display. Rotation recreates the
  activity, so keep UI state in `rememberSaveable` or a ViewModel. The app is immersive (system bars hidden).
- Use Material 3 components and `MaterialTheme.colorScheme` / `MaterialTheme.typography`. Do not hardcode colors in
  composables; add them to `ui/theme` instead.
- Everything must look right in **both light and dark mode**. The theme follows `isSystemInDarkTheme()` and uses dynamic
  colors when available. Keep `values/themes.xml` and `values-night/themes.xml` in sync to avoid a flash at launch.
- Do not set `android:configChanges` for `uiMode`. The activity must be recreated so the theme switches.
- All user-visible text goes in `res/values/strings.xml`.
- Developer/test tools (e.g. the "Force app crash" button) are debug-only: gate them on `BuildConfig.DEBUG` in the
  activity and pass `null` handlers to composables so the UI is not shown in release builds.
- Annotate new screens' previews with `@XeneonEdgePreviews` (landscape + portrait, light + dark, on the target display), and add a Compose UI test for new behavior.
- Follow the official Kotlin code style (`.editorconfig`).

## Rules

- Do not commit `local.properties`, keystores, or signing credentials.
- Contributions are licensed under MIT (see `LICENSE`). Only add dependencies with MIT-compatible licenses.
- Keep `README.md` and this file up to date when changing the toolchain, commands, or structure.
