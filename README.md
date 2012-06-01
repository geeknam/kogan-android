Kogan Android client
======================

Kogan Android app.

Configuring
------------

  You must have a PATH variable $ANDROID_HOME set to your Android SDK directory.  
  Basedroid is built on API 15 but is compatible with as low as Android 2.2.

        export ANDROID_HOME=/my/sdk/path
        git clone git://github.com/geeknam/kogan-android.git

Building from source
--------------------

  On a Unix-like system you can build Basedroid from source using the following
  command:

        mvn clean package

  To build Basedroid you will need:

  * [Android SDK](http://developer.android.com/sdk/index.html)
  * [Maven 3.0.4](http://maven.apache.org/download.html)
