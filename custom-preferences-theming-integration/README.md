Library that integrates [theming](https://gitlab.com/unbiaseduser/theming) with the [AndroidX Preference](https://developer.android.com/jetpack/androidx/releases/preference?hl=en) library.
 
# Features
- Material 3 theme for AndroidX Preference's preferences
- A no-frills prebuilt preference fragment
- Add theme-controlling preferences into custom preference fragments
- Convenience methods to adapt `SharedPreferences` and `PreferenceDataStore` into `ThemingPreferencesSupplier` to use the above preferences

# Usage
Define your themes like you would with the base `theming` library, but for the Material 3 themes, instead of inheriting from `AppTheme.Material3.*`, change it to `AppTheme.Material3.*.WithPreferences` instead.

See [code samples](https://gitlab.com/unbiaseduser/library-integrations/-/blob/master/theming-preference-integration-sample-app/src/main/java/com/sixtyninefourtwenty/theming/sample/CodeSamples.kt?ref_type=heads).