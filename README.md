[![jitpack badge](https://jitpack.io/v/unbiaseduser-github/custom-preferences-theming-integration.svg)](https://jitpack.io/#unbiaseduser-github/custom-preferences-theming-integration)

Library that integrates [theming](https://gitlab.com/unbiaseduser/theming) and
[custom-preferences](https://gitlab.com/unbiaseduser/custom-preferences).

# Features
- Material 3 theme for both AndroidX Preference's and custom-preferences' preferences
- A no-frills prebuilt preference fragment
- Add theme-controlling preferences into custom preference fragments
- Convenience methods to adapt `SharedPreferences` and `PreferenceDataStore` into `ThemingPreferencesSupplier` to use the above preferences

# Usage
Define your themes like you would with the base `theming` library, but for the Material 3 themes, instead of inheriting from `AppTheme.Material3.*`, change it to `AppTheme.Material3.*.WithPreferences` instead.

See [code samples](https://gitlab.com/unbiaseduser/custom-preferences-theming-integration/-/blob/master/sample-app/src/main/java/com/sixtyninefourtwenty/theming/sample/CodeSamples.kt?ref_type=heads).

# History
This library used to be divided into 2 modules: `custom-preferences-theming-integration` and `theming-preference-integration`.
This split was fundamentally flawed since `theming-preference-integration` uses `custom-preferences`
under the hood which makes the `custom-preferences` preferences not use Material 3 theme, so starting
from version 2.0.0 there's now only the unified `custom-preferences-theming-integration` left.

It's best to not bother with v1 due to flawed APIs and regressions, which is mostly alleviated with v2.