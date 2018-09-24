Steps

1. You need to download android SDK in your machine.
2. Set an Environment variable called ANDROID_HOME with the path to the Android SDK you just downloaded.
3. Create an google map API key by going to "https://console.developers.google.com". And add you SHA-1 Certificate Fingerprint.
4. Replace your google map API key in google_maps_api.xml file instead of YOUR_GOOGLE_MAPS_API_KEY.
5. Open terminal, go to project location, run './gradlew installDebug' command.
