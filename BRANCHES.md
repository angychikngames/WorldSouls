# Version branches

`main` contains the overview, license, credits, contribution templates, and shared documentation.

Each version branch contains one complete mod project at the root:

- `forge-1.12.2`
- `forge-1.16.5`
- `forge-1.20.1`
- `neoforge-26.3`

To work on a version:

```sh
git switch forge-1.20.1
```

`build.gradle`, the Gradle wrapper, and `src/` will appear at the root. Java requirements and build commands are in each version's README.

For a contribution, create a topic branch from the matching version branch and target that version branch in the pull request:

```sh
git switch forge-1.20.1
git switch -c fix/pause-menu-spacing
```

Changes to the naming generator may apply to several versions. Mention the affected versions in the pull request. Keep each version's API-specific interface code in its own branch.

