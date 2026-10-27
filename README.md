# World Souls

A little world name generator by angychikngames.

Tired of starting a new Minecraft world and getting attached to a place called *My World (1)*? Me too.

World Souls takes inspiration from ConcernedApe's wonderfully odd name generators and the ancient, living worlds of World of Warcraft. A handful of syllables come together, and suddenly, your world has a name.

World Souls adds a dice button beside the World Name field on the Create World screen. Keep rolling until a name feels right, or use one as a starting point and make it your own.

Your world's name also appears briefly when you load in and stays visible in the pause menu. Writing lore with a book and quill but forgot what eldritch abomination the generator came up with? Press Escape. Saves you quitting out just to check.

Names are assembled from randomized syllables, blending soft vowels with older, stranger sounds. They range from 3 to 16 characters long, with over 181 million possible names.

And, in accordance with Sturgeon's Law, 90% of everything is crap. Some rolls will be ancient and mysterious. Some will be beautiful accidents. Some will be Bellolooszy.

Hopefully, one of them suits your world.

Available for Minecraft Java Edition 1.12.2, 1.16.5, and 1.20.1 on Forge, and 26.3 on NeoForge.

## World names in game

Your saved world name fades in when you arrive, then fades out after about 3.5 seconds. It waits until loading is finished and the player camera is active. The name also appears at the top of the pause menu. Generated and custom names work the same way, including renamed worlds. This display uses the local saved world name in singleplayer or when hosting a LAN world.

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

World Souls, including its code and artwork, is licensed under [CC BY-NC-SA 3.0](https://creativecommons.org/licenses/by-nc-sa/3.0/). You can study, share, remix, and port it without asking. Give credit, keep it noncommercial, and share adaptations under the same license.

The full license is in [LICENSE](LICENSE).

