# World Souls (Minecraft 1.20.1, Forge)

[Project overview](https://github.com/angychikngames/WorldSouls#readme) and [contribution guide](https://github.com/angychikngames/WorldSouls/blob/main/CONTRIBUTING.md).

## Installation

For Minecraft Java Edition 1.20.1 with Forge.

Put `worldsouls-forge-1.20.1-1.0.0.jar` in your instance's `mods` folder. The dice button appears under **Singleplayer > Create New World**.

World Souls runs on the client and adds world naming and name displays.

## Building from source

The project uses Java 17 and includes a Gradle wrapper.

On Windows:

```powershell
.\gradlew.bat build
```

On Linux or macOS:

```sh
bash ./gradlew build
```

The built JAR is in `build/libs/`.

To launch a development client, use `runClient` in place of `build`.

## Credits

Mod, icon, and dice artwork by angychikngames.

Name-filter fingerprints are derived from [David Sojevic's Profanity List](https://github.com/dsojevic/profanity-list). Its MIT license is included in [THIRD-PARTY-NOTICES](THIRD-PARTY-NOTICES).

## License

Copyright (c) 2026 angychikngames.

World Souls, including its code and artwork, is licensed under [CC BY-NC-SA 4.0](https://creativecommons.org/licenses/by-nc-sa/4.0/). You can study, share, remix, and port it without asking. Give credit, keep it noncommercial, and share adaptations under the same license.

The full license is in [LICENSE](LICENSE).
