# GPKiosk

GPKiosk is an open-source kiosk app for a custom **Android Open Source Project (AOSP) 16** device.

> **Status:** bootstrap — the app currently displays a single "Hello, World !" screen.

- **Application ID:** `org.gpfister.aosp.kiosk`
- **Stack:** Kotlin, Jetpack Compose, Material 3
- **Theme:** follows the system light/dark mode automatically, using dynamic (wallpaper-based) colors when the device provides them
- **Target display:** [Corsair Xeneon Edge 14.5"](https://www.corsair.com/us/en/p/monitors/cc-9011306-ww/xeneon-edge-14-5-lcd-touchscreen-cc-9011306-ww) (2560×720, ~183 PPI) in **landscape or portrait**. The app follows the display orientation, runs full screen (system bars hidden, swipe to reveal) and every screen must fit without scrolling in both orientations

## Requirements

| Tool | Version |
|---|---|
| JDK | 21 (Gradle provisions it automatically if missing) |
| Android SDK | Platform 37.2 (compile), device running Android 16 / API 36 |
| Gradle | 9.8.0 via the included wrapper — no local install needed |

Point Gradle at your SDK with `ANDROID_HOME` or a `local.properties` file (`sdk.dir=/path/to/sdk`).

## Build and run

```sh
./gradlew assembleDebug          # build app/build/outputs/apk/debug/app-debug.apk
./gradlew installDebug           # install on the connected device (adb)
./gradlew lint test              # static checks + unit tests
./gradlew connectedAndroidTest   # UI tests on the connected device
```

To check theming on a device: `adb shell cmd uimode night yes` (or `no`).

## Roadmap

- Lock task mode / device-owner provisioning
- Kiosk content and configuration

## Contributing

Contributions are welcome. See [AGENTS.md](AGENTS.md) for project conventions.

## License

[MIT](LICENSE) © 2026 Greg PFISTER
