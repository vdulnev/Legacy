package com.example.legacy;

import android.app.Application;
import dagger.hilt.android.HiltAndroidApp;

// @HiltAndroidApp triggers Hilt's code generation and creates the
// application-level DI component that is the parent of all other components.
@HiltAndroidApp
public class LegacyApp extends Application {
}
