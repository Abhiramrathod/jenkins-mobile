# Jenkins Mobile (Java)

An Android (Java) client for Jenkins covering its core REST API features.

## Features
- Connect to any Jenkins server (HTTP/HTTPS)
- Basic auth with username + API Token (or password)
- Automatic CSRF crumb handling
- Dashboard: Jobs, Queue, Nodes tabs
- Job list with status colors
- Job detail: build history, trigger build, build with parameters
- Build detail: status, cause, duration, built-on node
- Console log viewer with progressive (streamed) refresh + copy
- Queue view (items, reasons, stuck/blocked)
- Nodes view (online/offline, executors)
- Dynamic build parameters (text/choice/default values)
- Settings to edit connection + logout

## Tech
- Java 17
- Retrofit2 + OkHttp + Gson
- RecyclerView, SwipeRefreshLayout, Material Components, ViewBinding

## Project layout
- api/ - Retrofit client, basic auth + crumb interceptors
- model/ - Gson models (Job, Build, Queue, Nodes, ...)
- ui/ - Activities
- adapter/ - RecyclerView adapters

## Build
Open in Android Studio (Hedgehog+), then Run. Or:
  ./gradlew assembleDebug

## CI / Release pipeline
GitHub Actions workflow at `.github/workflows/android.yml` builds a release APK.

Triggers:
- push to `main`
- pull request to `main`
- tag push `v*` (creates a GitHub Release with the APK attached)
- manual `workflow_dispatch`

The workflow uses JDK 17 + Gradle 8.5, runs `gradle assembleRelease`, uploads the APK as a build artifact (retained 30 days), and publishes a GitHub Release when a `v*` tag is pushed.

### Release signing (optional)
If no signing secrets are set, CI builds an **unsigned** release APK. To sign releases, add these repository secrets:

- `KEYSTORE_BASE64` - base64 of your `release.keystore`
  (`base64 -w0 release.keystore` on Linux/macOS)
- `KEYSTORE_PASSWORD`
- `KEY_ALIAS`
- `KEY_PASSWORD`

`app/build.gradle` reads these as env vars and signs only when present. Keep the keystore file out of git (it is in `.gitignore`).
