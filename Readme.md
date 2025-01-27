# ICUTimetableApp

ICUTimetableApp is an Android application designed to help Information and Communications University (ICU) students manage their academic schedules efficiently. The app provides role-based access for students, lecturers, and administrators to manage timetables and courses.

![App Screenshot](screenshots/main_screen.png)

## Features

### For Students
- View personal timetables and enrolled courses
- Receive notifications for class schedules
- Access course information
- Dark/Light mode support (coming soon)

### For Lecturers
- View assigned courses
- Manage course schedules
- Access teaching timetables

### For Administrators
- Manage user accounts
- Create and assign courses
- Configure program details
- Manage school-wide timetables
- Handle course enrollments

## Technical Stack

### Frontend
- Language: Kotlin
- UI Framework: Material Design 3
- Architecture: MVVM (Model-View-ViewModel)

### Backend Integration
- Firebase Authentication
- Firebase Cloud Messaging
- RESTful API integration using Retrofit
- JSON data handling with GSON

### Development Tools
- Android Studio 2022.1+
- Gradle Build System
- Firebase Console

## System Requirements

### For Users
- Android 6.0 (Marshmallow) or higher
- Google Play Services
- 50MB free storage
- 2GB RAM minimum

### For Developers
- Android Studio 2022.1+
- Android SDK 33
- Android Build Tools 33.0.0
- JDK 11

## Project Structure

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/isaacphiri2208310810/icutimetableapp/
│   │   │   ├── activities/       # UI Activities
│   │   │   ├── adapters/        # RecyclerView Adapters
│   │   │   ├── models/          # Data Models
│   │   │   ├── network/         # API Services
│   │   │   └── services/        # Firebase Services
│   │   ├── res/                 # Resources
│   │   └── AndroidManifest.xml
│   ├── test/                    # Unit Tests
│   └── androidTest/             # Instrumentation Tests
```

## Key Components

### Activities
- `MainActivity`: Entry point with splash screen
- `LoginActivity`: User authentication
- Role-specific dashboards:
  - `AdminDashboardActivity`
  - `StudentDashboardActivity`
  - `LecturerDashboardActivity`

### Data Models
- `Timetable`: Schedule information
- `Course`: Course details
- `Program`: Academic program data

### Network Layer
- `ApiService`: REST API endpoints
- `RetrofitInstance`: API client configuration

## Installation

### For Users
1. Download from Google Play Store (Coming Soon)
2. Or download the latest APK from [Releases](https://github.com/IsaacPhiri/ICUTimetableApp/releases)

### For Developers
1. Clone the repository:
```bash
git clone https://github.com/IsaacPhiri/ICUTimetableApp.git
```

2. Add Firebase configuration:
   - Create a Firebase project
   - Add `google-services.json` to app/
   - Enable Authentication and Cloud Messaging

3. Configure API endpoint:
   - Update `Constants.BASE_URL` with your backend URL

4. Build and run:
   - Open in Android Studio
   - Sync Gradle
   - Run on emulator or device

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Open a Pull Request

### Development Guidelines
- Follow Kotlin coding conventions
- Add appropriate unit tests
- Update documentation
- Test on multiple Android versions

## Testing

```bash
# Run unit tests
./gradlew test

# Run instrumentation tests
./gradlew connectedAndroidTest
```

### Test Accounts

For testing purposes, you can use the following credentials:

```
Admin Account:
Email: admin@email.com
Password: adminUser

Student Account:
Email: student@email.com
Password: studentUser

Lecturer Account:
Email: lecturer@email.com
Password: lecturerUser
```

**Note**: These are test accounts only. For production use, please create your own account or contact your institution's administrator.

## Known Issues

1. Build Issues
   - Clean and rebuild project
   - Update Gradle version
   - Check SDK installation

2. Runtime Issues
   - Verify Google Play Services
   - Check Firebase configuration
   - Ensure proper permissions

## License

This project is licensed under the MIT License - see [LICENSE](LICENSE) for details.

## Contact

- Issues and Features: [GitHub Issues](https://github.com/IsaacPhiri/ICUTimetableApp/issues)
- Security Concerns: security@icutimetableapp.com
- General Inquiries: support@icutimetableapp.com

## Acknowledgments

- Firebase for authentication and messaging
- Material Design for UI components
- ICU Academic Affairs Office for requirements and data

---

[Website](https://icutimetableapp.com) • [API Documentation](https://docs.icutimetableapp.com) • [Report Bug](https://github.com/yourusername/ICUTimetableApp/issues) - (coming soon)
