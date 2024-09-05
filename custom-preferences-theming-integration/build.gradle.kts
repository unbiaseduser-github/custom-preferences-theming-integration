plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    namespace = "com.sixtyninefourtwenty.theming.preferences"
    compileSdk = 34

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

tasks.withType<Test>().configureEach {
    jvmArgs("-Xmx1024m")
}

afterEvaluate {
    publishing {
        publications {
            register<MavenPublication>("release") {
                groupId = "com.sixtyninefourtwenty"
                artifactId = "custom-preferences-theming-integration"
                version = "2.0.1"

                from(components["release"])

                pom {
                    name.set("custom-preferences-theming-integration")
                    description.set("Integration of theming library with custom-preferences")
                    url.set("https://github.com/unbiaseduser-github/library-integrations")

                    licenses {
                        license {
                            name.set("The Apache Software License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }

                    developers {
                        developer {
                            id.set("unbiaseduser")
                            name.set("Dang Quang Trung")
                            email.set("quangtrung02hn16@gmail.com")
                            url.set("https://github.com/unbiaseduser")
                        }
                    }

                    scm {
                        connection.set("scm:git:git://github.com/unbiaseduser-github/library-integrations.git")
                        developerConnection.set("scm:git:ssh://github.com:unbiaseduser-github/library-integrations.git")
                        url.set("https://github.com/unbiaseduser-github/library-integrations/tree/master")
                    }
                }
            }
        }
    }
}

dependencies {

    api("com.github.unbiaseduser-github:theming:2.3.1")
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    api("androidx.preference:preference-ktx:1.2.1")
    api("com.github.unbiaseduser-github:custom-preferences:2.2.4")
    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.fragment:fragment-testing:1.8.2")
    testImplementation("androidx.test.ext:junit:1.2.1")
    testImplementation("org.robolectric:robolectric:4.13")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}