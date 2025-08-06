⏰ ClockApps

A full-featured Android clock application including Stopwatch, Timer, and Alarm functionality. Designed with a clean, intuitive UI and built to demonstrate core Android components, time-based logic, and activity navigation.

⭐ If you find this project helpful or interesting, consider giving it a star!


---

✨ Features

⏱ Stopwatch: Start, pause, and reset time tracking

⏲ Timer: Set countdowns and get notified when time is up

⏰ Alarm: Set alarms for specific times (basic UI-level implementation)

🎨 Simple and intuitive Material UI

🔄 Smooth transitions between screens

📱 Compatible with most Android devices



---

🚀 What's Included

MainActivity.java: Home screen to navigate to Stopwatch, Timer, or Alarm

StopwatchActivity.java: Stopwatch logic using Handler and Runnable

TimerActivity.java: Timer countdown using CountDownTimer

AlarmActivity.java: Alarm time selection using TimePicker (basic UI)

Beautiful and minimal XML layouts for each screen

Clean package structure

Custom styles for buttons, colors, and backgrounds



---

🛠 Built With

Java

XML

Android Studio

Handler & Runnable – For stopwatch logic

CountDownTimer – For implementing timer countdown

TimePicker – For alarm setup

LinearLayout / ConstraintLayout – For UI layout

Material Components



---

📂 Project Structure

ClockApps/
├── MainActivity.java
├── StopwatchActivity.java
├── TimerActivity.java
├── AlarmActivity.java
├── res/
│   ├── layout/
│   │   ├── activity_main.xml
│   │   ├── activity_stopwatch.xml
│   │   ├── activity_timer.xml
│   │   ├── activity_alarm.xml
│   ├── values/
│   │   ├── colors.xml
│   │   ├── strings.xml
│   │   ├── styles.xml


---

🔧 Logic Overview

✅ Stopwatch

Handler handler = new Handler();
Runnable runnable = new Runnable() {
    @Override
    public void run() {
        // update time
        handler.postDelayed(this, 1000);
    }
};

✅ Timer

new CountDownTimer(timeInMillis, 1000) {
    public void onTick(long millisUntilFinished) {
        // update timer
    }

    public void onFinish() {
        // timer completed
    }
}.start();

✅ Alarm (Basic)

TimePicker timePicker = findViewById(R.id.timePicker);
// Fetch time using getHour() and getMinute() and show confirmation


--


📄 License

This project is open-source and free to use for educational and personal purposes.
