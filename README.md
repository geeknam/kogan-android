Kogan Android client
======================

Kogan Android app.

Configuring
------------

  You must have a PATH variable $ANDROID_HOME set to your Android SDK directory.  
  The app is built on API 15 but is compatible with as low as Android 2.2.

        export ANDROID_HOME=/my/sdk/path
        git clone git://github.com/geeknam/kogan-android.git

  Otherwise run Maven with an extra flag -Dandroid.sdk.path

        mvn goal -Dandroid.sdk.path=/path/to/android-sdk/

Building from source
--------------------

  On a Unix-like system you can build from source using the following
  command:

        mvn clean package

  Run the app on Android device/emulator:

        mvn android:deploy android:run

  Run tests with:

        mvn test

  Requirements:

  * [Android SDK](http://developer.android.com/sdk/index.html)
  * [Maven 3.0.4](http://maven.apache.org/download.html)
  * [Embedded Maven Runtime 3.0.4](https://repository.sonatype.org/content/repositories/forge-sites/m2e/1.1.0/N/LATEST/)

Continuous integration
----------------------

  [![Build Status](https://buildhive.cloudbees.com/job/geeknam/job/kogan-android/badge/icon)](https://buildhive.cloudbees.com/job/geeknam/job/kogan-android/)

  Cloud Based Continuous Integration provided by [Buildhive CloudBees](https://buildhive.cloudbees.com/job/geeknam/job/kogan-android/)

Acknowledgements
----------------

  * Base template from [Basedroid](http://basedroid.com)
  * UI components including [ActionBarSherlock](https://github.com/JakeWharton/ActionBarSherlock) and [ViewPagerIndicator](https://github.com/JakeWharton/Android-ViewPagerIndicator) from [JakeWharton](https://github.com/JakeWharton/)
  * Inspired by [Gaug.es for Android](https://github.com/github/gauges-android)
