# CareCircle App Documentation

---

## 👥 Contributors

- Ghalia Azam
- Afrid Choudhury
- Emmanuel Adjei Danso
- Esther Bilenkin
- Franklin Pirozzolo
- Udhav Agarwal

---

## 📖 User Guide

### Public URL Link
[CareCircle App Download](https://drive.google.com/drive/folders/1CGu9cWKGXkRsJhyghF-Xv8tuuhKyTdJK?usp=drive_link)

### 1. Introduction
Welcome to the **CareCircle App**! This guide will walk you through the steps to install, set up, and use the app effectively.

---

### 2. Installation

#### For Android Devices:

1. **Download the APK File:**
   - Visit the [public URL link](https://drive.google.com/drive/folders/1CGu9cWKGXkRsJhyghF-Xv8tuuhKyTdJK?usp=drive_link).
   - Download the latest APK file (e.g., `CareCircle_v1.0.apk`).

2. **Allow App Installation from Unknown Sources:**
   - Go to **Settings > Security > Install Unknown Apps**.
   - Enable the option to allow installations from your browser or file manager.

3. **Install the App:**
   - Open the APK file.
   - Tap **Install** when prompted.
   - Once installed, tap **Open** to launch the app.

---

### 3. First-Time Setup

#### Step 1: Account Creation

1. Launch the app.
2. Tap **Sign Up** on the main screen.
3. Enter the following details:
   - **Full Name:** Your first and last name.
   - **Email Address:** A valid email address.
   - **Phone Number:** Your contact number.
   - **Password:** A secure password for your account.
4. Select your role (either **Parent** or **Child**) from the dropdown menu.
5. Tap **Create Account** to complete the process.

#### Step 2: Login

1. After signing up, return to the login screen.
2. Enter your registered **Email Address** and **Password**.
3. Tap **Login** to access your dashboard.

---

### 4. Features and How to Use Them

#### 4.1 Parent Dashboard

- **Add a Child:**
  1. Tap the **Add Child** button.
  2. Enter the child’s name and phone number.
  3. Tap **Save** to add the child to your account.
  4. Sample flagged messages will be generated for the child.

- **View Child Details:**
  1. Select a child card from the dashboard.
  2. View flagged messages, recent activity, or other details about the child.

- **Settings:**
  - Manage your notification preferences, edit your profile, or change your password.

- **Logout:**
  - Tap the **Logout** button to exit your account securely.

#### 4.2 Child Dashboard

- **Monitoring Status:**
  - Toggle the **Monitoring Switch** to activate or deactivate monitoring.
  - Status will reflect as either active or inactive.

- **Permissions:**
  - Use the **Enable Permissions** switch to allow or restrict app permissions.

- **Settings and Logout:**
  - Access settings to edit your profile or change your password.
  - Logout using the **Logout** button.

#### 4.3 Flagged Messages

1. In the Child Details page, tap **View** to see flagged messages.
2. A pop-up will display the flagged messages:
   - If there are flagged messages, the pop-up will show details like the sender’s phone number, message date, and content.
   - Use the **Next** button to navigate through flagged messages.
   - If no flagged messages exist, the pop-up will display "No Flagged Messages."

---

### 5. Troubleshooting

#### Login Issues
- Ensure your email and password are correct.
- Tap **Forgot Password** on the login screen if needed.

#### App Crashes or Errors
- Ensure your device meets the minimum requirements:
  - **Android version:** 6.0 (Marshmallow) or higher.
  - **Storage:** At least 50 MB of free space.
- Restart the app or reinstall if issues persist.

---

## 🧪 Testing Strategies

### 1. Unit Testing
- **Goal:** Verify the correctness of individual units of code (e.g., functions, classes, or modules).
- **Tools:** JUnit, AndroidX Test Library

#### Sample Test Case
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

### 2. Integration Testing
- **Goal:** Ensure various modules of the app interact seamlessly.
- **Tools:** Firebase Test Lab, AndroidX Integration Testing

### 3. User Interface (UI) Testing
- **Goal:** Validate the user interface elements and flows.
- **Tools:** Espresso (Android Testing Framework)

### 4. Functional Testing
- **Goal:** Ensure that the app's features function according to requirements.

### 5. Performance Testing
- **Goal:** Assess the app’s performance under normal and extreme conditions.
- **Tools:** Android Profiler

### 6. Security Testing
- **Goal:** Ensure the app handles sensitive user data securely.

---

## 🔧 Technology Stack

### 1. Hardware Prerequisites
- **Development Machine:**
  - Processor: Intel Core i5 or equivalent
  - RAM: Minimum 8GB (16GB recommended)
  - Storage: At least 20GB free disk space

- **Test Device:**
  - Android smartphone with Android 6.0 (Marshmallow) or higher

### 2. Software Prerequisites
- **Operating System:** Windows 10/11, macOS 11+, or Linux
- **Android Development Tools:** Android Studio
- **Firebase Tools:** Firebase Authentication, Realtime Database
- **Git:** For version control

---

## 🎨 Features and Technical Implementation

1. **User Authentication:**
   - Secure login and registration.
   - Firebase Authentication for role-based navigation.

2. **Parent Dashboard:**
   - Manage child details and view flagged messages.

3. **Child Dashboard:**
   - Toggle monitoring status and permissions.

4. **Add Child Functionality:**
   - Collect and store child details securely in Firebase.

5. **Notifications:**
   - Configurable notification settings for real-time alerts.

6. **Settings Page:**
   - Edit profile information and update passwords.

7. **Secure Logout:**
   - Ensures sensitive data is inaccessible post-logout.

---

## 📚 Summary

The CareCircle app integrates advanced Firebase features with user-friendly UI components to deliver a secure and seamless experience. Leveraging Firebase's real-time capabilities and robust error handling, the app provides functionality, security, and reliability for parents and children alike.

---

Feel free to share feedback or report issues via our [Support Page](#). 🙌

