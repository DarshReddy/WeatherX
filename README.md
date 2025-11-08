# WeatherX 🌤️

WeatherX is a modern Android weather application that provides current weather information and 4-day weather forecasts for cities. Built with Kotlin, it follows clean architecture principles and modern Android development best practices.

## Features ✨

- **Current Weather**: View real-time weather information including temperature and city name
- **4-Day Forecast**: Get weather forecasts for the next 4 days with daily average temperatures
- **Clean UI**: Modern, user-friendly interface with Material Design components
- **Offline Handling**: Error handling with retry functionality when network issues occur
- **Edge-to-Edge Display**: Optimized for modern Android devices with full-screen support

## Screenshots 📱

*Coming Soon*

## Technologies & Architecture 🏗️

### Tech Stack

- **Language**: Kotlin
- **Minimum SDK**: 28 (Android 9.0)
- **Target SDK**: 34 (Android 14)
- **Build System**: Gradle with Kotlin DSL

### Architecture & Libraries

- **Architecture Pattern**: MVVM (Model-View-ViewModel) with Clean Architecture
- **Dependency Injection**: Dagger Hilt
- **Networking**: 
  - Retrofit 2.9.0
  - OkHttp3
  - Moshi for JSON parsing
- **Async Operations**: Kotlin Coroutines
- **UI Components**: 
  - ViewBinding
  - Material Design Components
  - RecyclerView for forecast list
- **Testing**: 
  - JUnit 4
  - Mockito (Kotlin & Inline)
  - AndroidX Test (Espresso & JUnit)

### Project Structure

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/apps10x/weatherx/
│   │   │   ├── data/              # Data models and repository
│   │   │   ├── di/                # Dependency injection modules
│   │   │   ├── domain/            # Business logic and use cases
│   │   │   ├── network/           # API service interfaces
│   │   │   ├── ui/                # Activities, ViewModels, Adapters
│   │   │   ├── utils/             # Utility classes and extensions
│   │   │   └── WeatherXApplication.kt
│   │   └── res/                   # Resources (layouts, drawables, etc.)
│   ├── test/                      # Unit tests
│   └── androidTest/               # Instrumented tests
```

## Prerequisites 📋

Before you begin, ensure you have the following installed:

- Android Studio (Hedgehog | 2023.1.1 or newer recommended)
- JDK 8 or higher
- Android SDK with API level 34
- Gradle 8.4.0+ (included via wrapper)

## Setup & Configuration ⚙️

### 1. Clone the Repository

```bash
git clone https://github.com/DarshReddy/WeatherX.git
cd WeatherX
```

### 2. Configure API Key

WeatherX uses the OpenWeatherMap API. You'll need to obtain an API key:

1. Sign up at [OpenWeatherMap](https://openweathermap.org/api) to get a free API key
2. Create a `gradle.properties` file in the root directory (if it doesn't exist)
3. Add the following properties:

```properties
baseUrl="https://api.openweathermap.org/data/2.5/"
appId="YOUR_API_KEY_HERE"
```

**Important**: Make sure `gradle.properties` is listed in `.gitignore` to keep your API key secure.

### 3. Sync Project

Open the project in Android Studio and let Gradle sync all dependencies.

## Building the Project 🔨

### Using Android Studio

1. Open the project in Android Studio
2. Build > Make Project (or press `Ctrl+F9` / `Cmd+F9`)
3. Run the app on an emulator or physical device

### Using Command Line

```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease

# Install on connected device
./gradlew installDebug
```

## Running Tests 🧪

### Unit Tests

```bash
./gradlew test
```

### Instrumented Tests

```bash
./gradlew connectedAndroidTest
```

## How It Works 🔄

1. **Data Layer**: The `WeatherRepository` handles API calls through Retrofit
2. **Domain Layer**: `WeatherForecastsUseCase` processes forecast data and groups by day
3. **Presentation Layer**: 
   - `WeatherViewModel` manages UI state using LiveData
   - `WeatherActivity` observes ViewModel and updates UI
   - `WeatherForecastAdapter` displays the forecast list

### API Endpoints Used

- `GET /weather` - Current weather data for a city
- `GET /forecast` - 5-day weather forecast with 3-hour intervals

## Configuration 🔧

### Changing the Default City

The app currently defaults to "Bengaluru". To change this:

1. Open `app/src/main/java/com/apps10x/weatherx/ui/WeatherActivity.kt`
2. Modify the `CITY` constant in the companion object:

```kotlin
companion object {
    private const val CITY = "YourCityName"
}
```

### Network Timeout Configuration

Network timeouts can be adjusted in `ApplicationModule.kt`:

```kotlin
.connectTimeout(10, TimeUnit.SECONDS)
.writeTimeout(10, TimeUnit.SECONDS)
.readTimeout(30, TimeUnit.SECONDS)
```

## Permissions 📱

The app requires the following permission:

- `INTERNET` - To fetch weather data from the API

## Contributing 🤝

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## Known Issues 🐛

- The app currently supports only a single hardcoded city
- No location-based weather detection yet
- Limited error message customization

## Future Enhancements 🚀

- [ ] Add location-based weather detection
- [ ] Support for multiple cities with search functionality
- [ ] Detailed weather information (humidity, wind speed, pressure)
- [ ] Weather maps and radar
- [ ] Dark mode theme support
- [ ] Widgets for home screen
- [ ] Weather alerts and notifications

## License 📄

This project is open source and available for educational purposes.

## Acknowledgments 🙏

- Weather data provided by [OpenWeatherMap API](https://openweathermap.org/)
- Built with modern Android development tools and libraries
- Icons and design inspired by Material Design guidelines

---

**Made with ❤️ by the WeatherX Team**
