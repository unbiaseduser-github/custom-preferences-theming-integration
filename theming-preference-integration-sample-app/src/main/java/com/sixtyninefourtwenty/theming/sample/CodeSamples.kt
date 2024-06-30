package com.sixtyninefourtwenty.theming.sample

val INBUILT_FRAGMENT_KOTLIN_SAMPLE = """
    //YourActivity.kt
    class YourActivity : AppCompatActivity() {
        
        override fun onCreate(savedInstanceState: Bundle?) {
            //Use the default value for preferencesSupplier parameter. Make sure you have `preference-integration` extension installed.
            applyThemingWithPreferences(
                ...
            )
        }
        
    }
""".trimIndent()

val INBUILT_FRAGMENT_JAVA_SAMPLE = """
    //YourActivity.java
    public class YourActivity extends AppCompatActivity {
       
       protected void onCreate(Bundle savedInstanceState) {
           //Use the default value for preferencesSupplier parameter. Make sure you have `preference-integration` extension installed.
           ActivityThemingWithPreferences.applyThemingWithPreferences(
               this,
               ...
           )
       }
       
    }
""".trimIndent()

val INBUILT_PREFS_KOTLIN_SAMPLE = """
    //If you want to use the library's default ThemingPreferencesSupplier implementations
    //NOTE: Make sure you have `preference-integration` extension installed.
    
    //YourPreferenceDataStore.kt
    class YourPreferenceDataStore : PreferenceDataStore() {
        //Your data storage implementation
    }
    
    //YourActivity.kt
    class YourActivity : AppCompatActivity() {
        
        override fun onCreate(savedInstanceState: Bundle?) {
            val dataStore: YourPreferenceDataStore = ...
            val prefs: ThemingPreferencesSupplier = dataStore.toThemingPreferencesSupplier(this)
            applyTheming(
                ...,
                preferencesSupplier = prefs
            )
        }
        
    }
    
    //YourSettingsFragment.kt
    class YourSettingsFragment : PreferenceFragmentCompat() {
        
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            val dataStore: YourPreferenceDataStore = ...
            preferenceManager.preferenceDataStore = dataStore
            val prefs: ThemingPreferencesSupplier = dataStore.toThemingPreferencesSupplier(requireContext())
            val category = findPreference<PreferenceCategory>("your_key")!!
            category.addThemingPreferences(
                requireActivity(),
                prefs.lightDarkMode,
                prefs.md3,
                prefs.useM3CustomColorThemeOnAndroid12
                /*
                Since you're using a default ThemingPreferencesSupplier implementation, 
                there's no need to supply any of the optional parameters
                */
            )
        }
        
    }
    
    //If you DON'T want to use the library's default ThemingPreferencesSupplier implementations
    
    //YourPreferenceDataStore.kt
    const val M3_KEY = "md3"
    const val THEME_COLOR_KEY = "theme_color"
    const val LIGHT_DARK_MODE_KEY = "light_dark_mode"
    const val USE_M3_CUSTOM_COLOR_A12_KEY = "m3cca12"
    
    class YourPreferenceDataStore : PreferenceDataStore(), ThemingPreferencesSupplier {
    
        //Your data storage implementation
        
        private val values: Array<CharSequence> = arrayOf( /*Your char sequences here. Just remember to have one for each LightDarkMode.*/)
        
        private val lightDarkModeValues: Map<LightDarkMode, String> = mapOf(
            LightDarkMode.LIGHT to values[0], //other values
        )
        
        override var lightDarkMode: LightDarkMode
            get() {
                val prefValue = getString(LIGHT_DARK_MODE_KEY, lightDarkModeValues[LightDarkMode.LIGHT]!!)
                return lightDarkModeValues.entries.first { it.value == prefValue }.key
            }
            set(value) = putString(LIGHT_DARK_MODE_KEY, lightDarkModeValues[value]!!)
            
        private val colors: IntArray = intArrayOf( /*Your color ints here. Just remember to have one for each ThemeColor.*/ )    
            
        private val themeColorValues: Map<ThemeColor, Int> = mapOf(
            ThemeColor.BLUE to colors[0], //other values
        )
        
        //themeColor implementation is similar to lightDarkMode, just with getInt/putInt instead.
           
        //md3 and useM3CustomColorThemeOnAndroid12 are boolean values, not much to say here
    }
    
    //YourActivity.kt
    class YourActivity : AppCompatActivity() {
        
        override fun onCreate(savedInstanceState: Bundle?) {
            val dataStore: YourPreferenceDataStore = ...
            applyTheming(
                ...,
                preferencesSupplier = dataStore
            )
        }
        
    }
    
    //YourSettingsFragment.kt
    class YourSettingsFragment : PreferenceFragmentCompat() {
        
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            val dataStore: YourPreferenceDataStore = ...
            preferenceManager.preferenceDataStore = dataStore
            val category = findPreference<PreferenceCategory>("your_key")!!
            category.addThemingPreferences(
                requireActivity(),
                dataStore.lightDarkMode,
                dataStore.md3,
                dataStore.useM3CustomColorThemeOnAndroid12,
                m3PrefKey = M3_KEY,
                lightDarkModePrefKey = LIGHT_DARK_MODE_KEY,
                lightDarkModePrefEntries = null, //Use library's resources. As long as you have a value for each LightDarkMode it would be fine.
                lightDarkModePrefEntryValues = dataStore.values,
                useMD3CustomColorsThemeOnAndroid12PrefKey = USE_M3_CUSTOM_COLOR_A12_KEY,
                themeColorPrefKey = THEME_COLOR_KEY,
                themeColorPrefColors = dataStore.colors
            )
        }
        
    }
    
""".trimIndent()

val INBUILT_PREFS_JAVA_SAMPLE = """
    //If you want to use the library's default ThemingPreferencesSupplier implementations
    //NOTE: Make sure you have `preference-integration` extension installed.
    
    //YourPreferenceDataStore.java
    public class YourPreferenceDataStore extends PreferenceDataStore {
        //Your data storage implementation
    }
    
    //YourActivity.java
    public class YourActivity extends AppCompatActivity {
    
        protected void onCreate(Bundle savedInstanceState) {
            YourPreferenceDataStore dataStore = ...;
            var prefs = ThemingPreferencesSuppliers.create(dataStore, this);
            ActivityTheming.applyTheming(
                this,
                ...,
                prefs
            )    
        }
    
    }
    
    //YourSettingsFragment.java
    public class YourSettingsFragment extends PreferenceFragmentCompat {
    
         public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
             YourPreferenceDataStore dataStore = ...;
             getPreferenceManager().setPreferenceDataStore(dataStore);
             var prefs = ThemingPreferencesSuppliers.create(dataStore, requireContext());
             PreferenceCategory category = Objects.requireNonNull(findPreference("your_key"));
             ThemingPreferenceUI.addThemingPreferences(
                 category,
                 requireActivity(),
                 prefs.getLightDarkMode(),
                 prefs.getMd3(),
                 prefs.getUseM3CustomColorThemeOnAndroid12(),
                 /*
                 Since you're using a default ThemingPreferencesSupplier implementation, 
                 there's no need to supply any of the optional parameters
                 */
             )
         }
    
    }
    
    //If you DON'T want to use the library's default ThemingPreferencesSupplier implementations
    
    //YourPreferenceDataStore.java
    public class YourPreferenceDataStore extends PreferenceDataStore implements ThemingPreferencesSupplier {
    
        public static final String M3_KEY = "md3";
        public static final String THEME_COLOR_KEY = "theme_color";
        public static final String LIGHT_DARK_MODE_KEY = "light_dark_mode";
        public static final String USE_M3_CUSTOM_COLOR_A12_KEY = "m3cca12";
    
        //Your data storage implementation
        
        private final CharSequence[] values = {/*Your char sequences here. Just remember to have one for each LightDarkMode.*/};
        
        public CharSequence[] getValues() {
            return values.clone();
        }
        
        private final Map<LightDarkMode, String> lightDarkModeValues = Map.of(
            LightDarkMode.LIGHT, values[0], //other values
        );
        
        public LightDarkMode getLightDarkMode() {
            var prefValue = getString(LIGHT_DARK_MODE_KEY, Objects.requireNonNull(lightDarkModeValues.get(LightDarkMode.LIGHT)));
            return lightDarkModeValues.entrySet().stream()
                .filter(entry -> entry.getValue().equals(prefValue))
                .findFirst()
                .orElseThrow();
        }
        
        public void setLightDarkMode(LightDarkMode value) {
            putString(LIGHT_DARK_MODE_KEY, Objects.requireNonNull(lightDarkModeValues.get(value)));
        }
        
        private final int[] colors = {/*Your color ints here. Just remember to have one for each ThemeColor.*/};
        
        public int[] getColors() {
            return colors.clone();
        }
        
        private final Map<ThemeColor, Integer> themeColorValues = Map.of(
            ThemeColor.BLUE, colors[0], //other values
        );
        
        //themeColor is similar to lightDarkMode, just with getInt/putInt instead.
        
        //md3 and useM3CustomColorThemeOnAndroid12 are boolean values, not much to say here
    }
    
    //YourActivity.java
    public class YourActivity extends AppCompatActivity {
    
        protected void onCreate(Bundle savedInstanceState) {
            YourPreferenceDataStore dataStore = ...;
            ActivityTheming.applyTheming(
                this,
                ...,
                dataStore
            )    
        }
    
    }
    
    //YourSettingsFragment.java
    public class YourSettingsFragment extends PreferenceFragmentCompat {
        
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            YourPreferenceDataStore dataStore = ...;
            getPreferenceManager.setPreferenceDataStore(dataStore);
            PreferenceCategory category = Objects.requireNonNull(findPreference("your_key"));
            ThemingPreferenceUI.addThemingPreferences(
                category,
                requireActivity(),
                dataStore.getLightDarkMode(),
                dataStore.getMd3(),
                dataStore.getUseM3CustomColorThemeOnAndroid12(),
                YourPreferenceDataStore.M3_KEY,
                YourPreferenceDataStore.LIGHT_DARK_MODE_KEY,
                null, //Use library's resources. As long as you have a value for each LightDarkMode it would be fine.
                dataStore.getValues(),
                YourPreferenceDataStore.USE_M3_CUSTOM_COLOR_A12_KEY,
                YourPreferenceDataStore.THEME_COLOR_KEY,
                dataStore.getColors()
            );
        }
        
    }
    
""".trimIndent()

val CUSTOM_PREFERENCE_SUPPLIER_KOTLIN_SAMPLE = """
    //YourPreferenceSupplier.kt
    object YourPreferenceSupplier : ThemingPreferencesSupplier {
        override var md3: Boolean = false
        override var lightDarkMode: LightDarkMode = LightDarkMode.SYSTEM
        override var themeColor: ThemeColor = ThemeColor.BLUE
        override var useM3CustomColorThemeOnAndroid12: Boolean = false
    }
    
    //YourActivity.kt
    class YourActivity : AppCompatActivity() {
        
        private val prefs: ThemingPreferenceSupplier = YourPreferenceSupplier
        
        override fun onCreate(savedInstanceState: Bundle?) {
            applyTheming(
                ...,
                preferencesSupplier = prefs
            )
        }
        
    }
""".trimIndent()

val CUSTOM_PREFERENCE_SUPPLIER_JAVA_SAMPLE = """
    //YourPreferenceSupplier.java
    public class YourPreferenceSupplier implements ThemingPreferencesSupplier {
    
        private YourPreferenceSupplier() {}
    
        public static final YourPreferenceSupplier INSTANCE = new YourPreferenceSupplier();
    
        private boolean md3 = false;
        private LightDarkMode lightDarkMode = LightDarkMode.SYSTEM;
        private ThemeColor themeColor = ThemeColor.BLUE;
        private boolean useM3CustomColorThemeOnAndroid12 = false;
        
        //getters and setters to satisfy the interface
    }
    
    //YourActivity.java
    public class YourActivity extends AppCompatActivity {
       
       private final ThemingPreferenceSupplier prefs = YourPreferenceSupplier.INSTANCE;
       
       protected void onCreate(Bundle savedInstanceState) {
           ActivityTheming.applyTheming(
               this,
               ...,
               prefs
           )
       }
       
    }
""".trimIndent()
