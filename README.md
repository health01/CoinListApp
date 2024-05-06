# CoinApp

CoinApp is an Android application dedicated to displaying a list of cryptocurrency coins. It
combines modern Android development practices with a clean, interactive user interface, providing
users with up-to-date information on various cryptocurrencies.

## Features

- **Display Coin List**: Automatically loads and displays a list of coins upon app startup.
- **Alphabetical Ordering**: Lists coins in alphabetical order for easy browsing.
- **Interactive Entries**: Users can tap on a coin to navigate to a detailed `CoinDetails` screen
  where additional information about the coin is displayed.
- **Refresh Option**: Includes a manual refresh option for reloading the list of coins.
- **Error Handling**: Implements detailed error handling for network-related issues, specifically
  handling `IOException`, `HttpException`, and other throwable instances by displaying their
  messages to the user.

## Tech Stack

### Core Components

- **AndroidX and Jetpack Compose**: Utilizes the latest suite of AndroidX libraries and Jetpack
  Compose for building the UI.
- **Material Design 3**: Adopts Material Design 3 for a modern and cohesive user interface design.

### Network Management

- **Retrofit and OkHttp**: Handles network requests and responses efficiently.
- **GSON**: Facilitates the serialization and deserialization of JSON data.

### Dependency Injection

- **Dagger-Hilt**: Manages dependency injection, simplifying the architecture and promoting
  scalability.

### Testing

- **JUnit**: Provides a robust framework for unit testing to ensure app reliability.
- **Mockk**: Kotlin-based mocking library used for effective unit testing.
- **Coroutines Test**: Provides tools for testing Kotlin coroutines.

## Installation

1. **Clone the Repository**: `git clone https://github.com/yourgithub/CoinApp.git`
2. **Open in Android Studio**: Start Android Studio and open the cloned project.
3. **Build the Project**: Use the 'Build' option in Android Studio to compile the project.
4. **Run the App**: Deploy the app to an Android emulator or a physical device.

## Running Tests

To run the unit tests for the application, execute the following command in your terminal:

```bash
./gradlew :app:testDebugUnitTest
