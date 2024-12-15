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

... (complete content continues with emojis to maintain appeal).

