Library that integrates [custom-preferences](https://gitlab.com/unbiaseduser/custom-preferences) with [theming](https://gitlab.com/unbiaseduser/theming).

It provides Material 3 themes for the preferences introduced in `custom-preferences`.

# Usage
Define your themes like you would with the base `theming` library, but for the Material 3 themes, instead of inheriting from `AppTheme.Material3.*`, change it to `AppTheme.Material3.*.WithCustomPreferences` instead.