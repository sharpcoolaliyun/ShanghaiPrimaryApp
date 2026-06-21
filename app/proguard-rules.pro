# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in the SDK tools.

# Keep Room entities
-keep class com.shanghai.primary.data.model.** { *; }

# Keep generated data binding/KSP classes
-keep class com.shanghai.primary.data.db.** { *; }

# General Android rules for Compose
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**
