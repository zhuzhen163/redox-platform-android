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
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
# glide
#指定代码的压缩级别
-optimizationpasses 5
#表明不混合大小写
-dontusemixedcaseclassnames
#不去忽略非公共的库类
-dontskipnonpubliclibraryclasses
#预校验
-dontpreverify
#忽略警告
-ignorewarnings
#混淆时是否记录日志
-verbose
#混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#保持哪些类不被混淆
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService


-keep public class * implements com.bumptech.glide.module.GlideModule

-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {

  **[] $VALUES;

  public *;

}
-dontwarn com.bumptech.glide.load.resource.bitmap.VideoDecoder

### greenDAO 3
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties

# If you do not use SQLCipher:
-dontwarn org.greenrobot.greendao.database.**
# If you do not use RxJava:
-dontwarn rx.**
#okhttp
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}

#okio
-dontwarn okio.**
-keep class okio.**{*;}

# 百度地图

-keep class com.baidu.** {*;}
-keep class com.sinovoice.** {*;}
-keep class pvi.com.** {*;}
-dontwarn com.baidu.**
-keep class mapsdkvi.com.** {*;}
#-dontwarn vi.com.**
#-dontwarn pvi.com.**
#-keep class mapsdkvi.com.** {*;}
#-keep class vi.com.gdi.bgl.android.**{*;}


#下拉刷新
-dontwarn com.handmark.**
-keep class org.openudid.** { *; }

-keep public class  com.sxw.**

-keep public class  com.bdt.**



-keepattributes *Annotation*

-keepclassmembers class * {

    @org.greenrobot.eventbus.Subscribe <methods>;

}

-keep enum org.greenrobot.eventbus.ThreadMode { *; }



# Only required if you use AsyncExecutor

-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {

    <init>(java.lang.Throwable);

}
#bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}

-dontwarn com.hdgq.**
-keep class com.hdgq.**{*;}

#LRecyclerview
-dontwarn com.github.jdsjlzx.**
-keep class com.github.jdsjlzx.progressindicator.indicators.** { *; }

#闪验
-dontwarn com.cmic.gen.sdk.**
-keep class com.cmic.gen.sdk.**{*;}
-dontwarn com.sdk.**
-keep class com.sdk.** { *;}
-dontwarn com.unikuwei.mianmi.account.shield.**
-keep class com.unikuwei.mianmi.account.shield.** {*;}
-keep class cn.com.chinatelecom.account.api.**{*;}
#支付
-keep class com.tencent.mm.opensdk.** {
*;
}
-keep class com.tencent.wxop.** {
*;
}
-keep class com.tencent.mm.sdk.** {
*;
}




