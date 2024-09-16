# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
-renamesourcefileattribute SourceFile

-keep class android.content.res.XmlResourceParser
-keep class org.xmlpull.v1.XmlPullParser { *; }
-dontwarn android.content.res.**

-keep class **.R
-keep class **.R$* {*;}
-keep class **.BuildConfig {*;}
-keep class **.Manifest {*;}

# Исключаем из обфускации весь пакет fb
-keep class su.librox.model.fictionbook.** { *; }
# Исключаем из обфускации класс PublishInfo
#-keep class ru.librox.model.PublishInfo { *; }

# Сохраняем классы и методы библиотеки Simple XML
-keep class org.simpleframework.xml.** { *; }

# Gson uses generic type information stored in a class file when working with
# fields. Proguard removes such information by default, keep it.
-keepattributes Signature

# This is also needed for R8 in compat mode since multiple
# optimizations will remove the generic signature such as class
# merging and argument removal. See:
# https://r8.googlesource.com/r8/+/refs/heads/main/compatibility-faq.md#troubleshooting-gson-gson
#-keep class com.google.gson.reflect.TypeToken { *; }
#-keep class * extends com.google.gson.reflect.TypeToken

# Optional. For using GSON @Expose annotation
#-keepattributes AnnotationDefault,RuntimeVisibleAnnotations
#-keep class com.google.gson.reflect.TypeToken { <fields>; }
#-keepclassmembers class **$TypeAdapterFactory { <fields>; }
