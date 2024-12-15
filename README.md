# CareCircle App 📱💡

### Contributors: 🙌  
Ghalia Azam, Afrid Choudhury, Emmanuel Adjei Danso, Esther Bilenkin, Franklin Pirozzolo, Udhav Agarwal  

---

## USER GUIDE 📝  
**Public URL Link:** 🔗 [CareCircle App Files](https://drive.google.com/drive/folders/1CGu9cWKGXkRsJhyghF-Xv8tuuhKyTdJK?usp=drive_link)  

---

### **User Guide for Installing, Setting Up, and Using the CareCircle App**  

---

### 1. Introduction 🌟  
Welcome to the **CareCircle** app! 🎉 This guide will walk you through the steps to install, set up, and use the app effectively. 🚀  

---

### 2. Installation ⚙️  
**For Android Devices:**  
1. **Download the APK File** 📥  
   - Visit the official public URL link: [CareCircle APK](https://drive.google.com/drive/folders/1CGu9cWKGXkRsJhyghF-Xv8tuuhKyTdJK?usp=drive_link)  
   - Download the latest APK file (e.g., `CareCircle_v1.0.apk`).  

2. **Allow App Installation from Unknown Sources** 🔓  
   - Go to **Settings > Security > Install Unknown Apps**.  
   - Enable the option to allow installations from your browser or file manager.  

3. **Install the App** 📱  
   - Open the APK file.  
   - Tap **Install** when prompted.  
   - Once installed, tap **Open** to launch the app.  

---

### 3. First-Time Setup 🛠️  
**Step 1: Account Creation**  
1. Launch the app.  
2. Tap **Sign Up** on the main screen.  
3. Enter the following details:  
   - 🗃️ **Full Name**: Your first and last name.  
   - ✉️ **Email Address**: A valid email address.  
   - 📞 **Phone Number**: Your contact number.  
   - 🔒 **Password**: A secure password for your account.  
4. Select your Role (either "Parent" or "Child") from the dropdown menu.  
5. Tap **Create Account** to complete the process.  

**Step 2: Login**  
1. After signing up, return to the login screen.  
2. Enter your registered **Email Address** and **Password**.  
3. Tap **Login** to access your dashboard.  

---

### 4. Features and How to Use Them 💡  

#### 4.1 Parent Dashboard 👨‍👩‍👦  
- **Add a Child**:  
  1. Tap the **Add Child** button.  
  2. Enter the child’s name and phone number.  
  3. Tap **Save** to add the child to your account.  
  4. Sample flagged messages will be generated for the child.  

- **View Child Details**:  
  1. Select a child card from the dashboard.  
  2. View flagged messages, recent activity, or other details about the child.  

- **Settings** ⚙️:  
  Manage your notification preferences, edit your profile, or change your password.  

- **Logout** 🔐:  
  Tap the **Logout** button to exit your account securely.  

#### 4.2 Child Dashboard 🎈  
- **Monitoring Status**:  
  - Toggle the **Monitoring Switch** to activate or deactivate monitoring.  
  - Status will reflect as either active or inactive.  

- **Permissions**:  
  Use the **Enable Permissions** switch to allow or restrict app permissions.  

- **Settings and Logout**:  
  Access settings to edit your profile or change your password.  
  Logout using the **Logout** button.  

#### 4.3 Flagged Messages 🚨  
1. In the **Child Details** page, tap **View** to see flagged messages.  
2. A pop-up will display the flagged messages:  
   - If there are flagged messages, the pop-up will show details like the sender’s phone number, message date, and content.  
   - Use the **Next** button to navigate through flagged messages.  
   - If no flagged messages exist, the pop-up will display **"No Flagged Messages."**  

---

### 5. Troubleshooting 🛠️  

#### Login Issues 🔑  
- Ensure your email and password are correct.  
- Tap **Forgot Password** on the login screen if needed.  

#### App Crashes or Errors ❌  
- Ensure your device meets the minimum requirements:  
  - **Android version**: 6.0 (Marshmallow) or higher.  
  - **At least 50 MB of free storage space**.  
- Restart the app or reinstall if issues persist.  

---

## Testing Strategies for the CareCircle App 🧪  

To ensure the **CareCircle** app works as expected, the following testing strategies were implemented:  

---

### 1. Unit Testing 🔎  
- **Goal**: Verify the correctness of individual units of code (e.g., functions, classes, or modules).  
- **Tools**: `JUnit`, `AndroidX Test Library`  
- **Example**:  
  ```kotlin
  @Test
  fun testEmailValidation() {
      val email = "test@example.com"
      assertTrue(isValidEmail(email))
  }

  @Test
  fun testInvalidEmail() {
      val email = "invalid-email"
      assertFalse(isValidEmail(email))
  }
  ```  
- **Results**:  
  - Valid email (`test@example.com`) passed.  
  - Invalid email (`invalid-email`) failed as expected.  

---

### 2. Integration Testing 🔗  
- **Goal**: Ensure various modules of the app interact seamlessly.  
- **Tools**: `Firebase Test Lab`, `AndroidX Integration Testing`  
- **Example**:  
  - **Scenario**: User logs in with valid credentials.  
    - **Steps**:  
      1. Input a valid email and password.  
      2. Click the "Login" button.  
      3. Verify redirection to the correct dashboard (Parent or Child).  
    - **Expected Result**: User is redirected to the appropriate dashboard.  
  - **Result**: Success! 🎉  

---

### 3. User Interface (UI) Testing 🎨  
- **Goal**: Validate the user interface elements and flows.  
- **Tools**: `Espresso (Android Testing Framework)`  
- **Example**:  
  ```kotlin
  @get:Rule
  val activityRule = ActivityScenarioRule(MainActivity::class.java)

  @Test
  fun testSignUpButtonClick() {
      onView(withId(R.id.signUpButton)).perform(click())
      onView(withId(R.id.signupForm)).check(matches(isDisplayed()))
  }
  ```  
- **Results**:  
  - Button clicked successfully.  
  - Signup form displayed as expected.  

---

### 4. Functional Testing ⚡  
- **Goal**: Ensure that the app's features function according to requirements.  
- **Example**:  
  - **Scenario**: Add a child to the parent’s dashboard.  
    - **Steps**:  
      1. Input child's name and phone number.  
      2. Click "Add Child."  
      3. Verify that the child appears in the list.  
    - **Expected Result**: Child is added and displayed with a "Click for Details" message.  
  - **Result**: Success! ✅  

---

### 5. Performance Testing 🏃️  
- **Goal**: Assess the app’s performance under normal and extreme conditions.  
- **Tools**: `Android Profiler`  
- **Example**:  
  - **Scenario**: Retrieve flagged messages for a child with 50 flagged entries.  
    - **Steps**:  
      1. Click "View" for a specific child.  
      2. Measure the time taken to load messages.  
    - **Expected Result**: Messages load within 2 seconds.  
  - **Result**: Success! Loaded in under 1.5 seconds.  

---

### 6. Security Testing 🔒  
- **Goal**: Ensure the app handles sensitive user data securely.  
- **Example**:  
  - **Scenario**: Attempt unauthorized access to another user’s flagged messages.  
    - **Steps**:  
      1. Simulate an unauthorized user trying to access another user's data.  
      2. Verify access is denied by Firebase rules.  
    - **Expected Result**: Access is denied.  
  - **Result**: Success! Firebase denied unauthorized access.  

---

# Technology Stack for the CareCircle App 💡🔧

## 1. Hardware Prerequisites 🔧
- **Device Specifications:**
  - **Development Machine:**
    - Processor: Intel Core i5 or equivalent
    - RAM: Minimum 8GB (16GB recommended)
    - Storage: At least 20GB free disk space
    - Display: 1920x1080 resolution or higher
  - **Test Device (Optional for physical device testing):**
    - Android smartphone with Android 6.0 (Marshmallow) or higher
    - Minimum 2GB RAM
    - At least 500MB free space

---

## 2. Software Prerequisites 🔧
- **Operating System:**
  - Windows 10/11, macOS 11+ (Big Sur or later), or a Linux-based system.
- **Android Development Tools:**
  - Android Studio (latest stable version):
    - Includes Android SDK, Emulator, and Gradle build tools.
  - **Java Development Kit (JDK):**
    - JDK 11+ is required for Android development.
  - **Firebase Tools:**
    - A Firebase project configured for Authentication, Realtime Database, and Analytics.
  - **Git:**
    - For version control and pushing the project to GitHub.

---

## 3. Software Dependencies 🛠️
All dependencies are managed in the `build.gradle.kts` file. Ensure the following packages are installed:

- **AndroidX Libraries:**
  - Core dependencies for Android development:
    - `androidx.core:core-ktx`
    - `androidx.lifecycle:lifecycle-runtime-ktx`
    - `androidx.navigation:navigation-fragment-ktx`
    - `androidx.appcompat:appcompat`

- **Firebase SDKs:**
  - Add the Firebase BOM (Bill of Materials) to ensure consistent versions:

    ```kotlin
    implementation(platform("com.google.firebase:firebase-bom:33.6.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-database:20.2.0")
    implementation("com.google.firebase:firebase-auth:22.1.0")
    ```

- **UI Frameworks:**
  - Material Design: `androidx.compose.material3`
  - RecyclerView: `androidx.recyclerview:recyclerview`
  - View Binding: Enable for XML-based layouts.

- **Testing Libraries:**
  - `junit:junit`
  - `androidx.test.ext:junit`
  - `androidx.test.espresso:espresso-core`

- **Other Dependencies:**
  - Google Services Plugin: `com.google.gms.google-services`
  - Kotlin Extensions: `kotlinx.coroutines`

---

## 4. Required Packages 📚
Install required SDKs and tools using Android Studio's SDK Manager:

- Android SDK Platform-Tools
- Android Emulator
- Android SDK Build-Tools
- Command-line Tools

---

## 5. Development Environment Setup 🚀
- **Clone the Repository:**
  1. Install Git.
  2. Clone the CareCircle repository:
     ```bash
     git clone https://github.com/your-repo/CareCircle.git
     cd CareCircle
     ```

- **Configure Firebase:**
  1. Download the `google-services.json` file from your Firebase console.
  2. Place it in the app-level directory (`/app`).

- **Install Dependencies:**
  1. Open the project in Android Studio.
  2. Sync Gradle to download all dependencies.

---

## 6. Testing Environment 🧰
- **Android Virtual Device (AVD):**
  - Use Android Studio to create an emulator with:
    - Android 11 (API Level 30) or higher
    - Device: Pixel 4 or equivalent
    - RAM: 2GB
    - Internal Storage: 16GB

- **Firebase Test Lab:**
  - Test the app on real devices in the cloud.

---

# Setup Guide: Development and Production Environments for CareCircle App

## 1. Development Environment Setup

### Step 1: Install Required Software
1. **Install Android Studio:**
   - Download the latest version from the official Android Studio website.
   - Follow the installation instructions for your operating system (Windows, macOS, or Linux).
2. **Install Java Development Kit (JDK):**
   - JDK 11+ is required.
   - Download from AdoptOpenJDK or use your system's package manager.
3. **Install Git:**
4. **Install Firebase CLI (Optional):**
   - To interact with Firebase services from the command line:

### Step 2: Clone the Repository
1. Open a terminal or command prompt.
2. Run the following commands:
   ```bash
   git clone https://github.com/your-repo/CareCircle.git
   cd CareCircle
   ```

### Step 3: Set Up Android Studio
1. **Open Project:**
   - Launch Android Studio.
   - Click on "Open" and select the cloned CareCircle directory.
2. **Sync Gradle:**
   - Gradle will automatically sync when you open the project.
   - If not, click on "Sync Project with Gradle Files" in the toolbar.
3. **Install Required SDKs:**
   - Go to File > Settings > Appearance & Behavior > System Settings > Android SDK.
   - Ensure the following SDKs are installed:
     - Android 11 (API Level 30) or higher
     - Android SDK Build-Tools
     - Android Emulator

### Step 4: Configure Firebase
1. **Create a Firebase Project:**
   - Go to the Firebase Console.
   - Click "Add Project" and follow the setup wizard.
2. **Add an Android App:**
   - Register the app with the package name: `com.example.carecircle`.
   - Download the `google-services.json` file and place it in the `/app` directory of your project.
3. **Enable Firebase Features:**
   - In the Firebase Console, enable:
     - Authentication (Email/Password provider).
     - Realtime Database.
4. **Sync Firebase Configuration:**
   - Ensure the `google-services.json` file is present.
   - Sync Gradle in Android Studio:
     - Apply the Google Services plugin in the app-level Gradle file:
       ```kotlin
       apply(plugin = "com.google.gms.google-services")
       ```

### Step 5: Set Up Emulator (Optional)
1. **Create an Android Virtual Device (AVD):**
   - Open AVD Manager in Android Studio.
   - Select a Pixel device (e.g., Pixel 4) with Android 11 (API Level 30) or higher.
2. **Configure Emulator:**
   - RAM: At least 2GB.
   - Internal Storage: At least 16GB.

---

## 2. Production Environment Setup

### Step 1: Prepare for Release
1. **Update `build.gradle.kts`:**
   - Ensure release build type is configured:
     ```kotlin
     buildTypes {
         release {
             isMinifyEnabled = false
             proguardFiles(
                 getDefaultProguardFile("proguard-android-optimize.txt"),
                 "proguard-rules.pro"
             )
         }
     }
     ```

2. **Generate a Keystore:**
   - Create a keystore for signing the APK:
     ```bash
     keytool -genkey -v -keystore release-key.jks -keyalg RSA -keysize 2048 -validity 10000 -alias carecircle
     ```
   - Save the `release-key.jks` file securely.

3. **Sign the APK:**
   - Configure signing in the `build.gradle.kts` file:
     ```kotlin
     android {
         signingConfigs {
             release {
                 storeFile = file("path/to/release-key.jks")
                 storePassword = "your-store-password"
                 keyAlias = "carecircle"
                 keyPassword = "your-key-password"
             }
         }
         buildTypes {
             release {
                 signingConfig = signingConfigs.release
             }
         }
     }
     ```

### Step 2: Build the Release APK
1. Open Android Studio.
2. Go to Build > Build Bundle(s)/APK(s) > Build APK(s).
3. The signed APK will be available in the `/app/release` directory.

### Step 3: Test the Release APK
1. Install the APK on a physical or virtual Android device:
   ```bash
   adb install app-release.apk
   ```
2. Test the app functionality and ensure it runs without issues.

