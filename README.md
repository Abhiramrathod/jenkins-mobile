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
