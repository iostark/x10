#
# Keep general classes
#

# Keep function param names as is
-keepparameternames

# Producing useful obfuscated stack traces
-renamesourcefileattribute SourceFile
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,
                SourceFile,LineNumberTable,*Annotation*,EnclosingMethod

# Keep native methods
-keepclasseswithmembernames,includedescriptorclasses class * {
    native <methods>;
}

# Keep Enums
-keepclassmembers,allowoptimization enum * {
    public static **[] values(); public static ** valueOf(java.lang.String);
}

# Keep Serializable classes
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

#
# Keep X10 Library entry points
#

-dontwarn com.iostark.x10.base.annotation.**
-keep class com.iostark.x10.base.annotation.**

# @KeepEntryPoint specifies not to shrink, optimize, or obfuscate the annotated class
# or class member (fields and methods) as an entry point.
-keep @com.iostark.x10.base.annotation.visibility.KeepEntryPoint class *
-keepclassmembers class * {
    @com.iostark.x10.base.annotation.visibility.KeepEntryPoint *;
}

# @KeepEntryPointName specifies not to shrink, optimize, or obfuscate the annotated class
# or class member (fields and methods) as an entry point.
-keepnames @com.iostark.x10.base.annotation.visibility.KeepEntryPointName class *
-keepclassmembernames class * {
    @com.iostark.x10.base.annotation.visibility.KeepEntryPointName *;
}

# The following annotations can only be specified with classes.

# @KeepEntryPoint specify to keep all / all public, implementations or extensions of the annotated class as
# entry points. Note the extension of the java-like syntax, adding annotations
# before the (wild-carded) interface name.

-keep        class * implements @com.iostark.x10.base.annotation.visibility.KeepEntryPoint *
-keep public class * implements @com.iostark.x10.base.annotation.visibility.KeepEntryPoint *

# @KeepEntryPointPublicMembers / @KeepEntryPointPublicProtectedMembers specify to keep all public /
# all public or protected, class members of the annotated class from being
# shrunk, optimized, or obfuscated as entry points.

-keepclassmembers @com.iostark.x10.base.annotation.visibility.KeepEntryPointPublicMembers class * {
    public *;
}

-keepclassmembers @com.iostark.x10.base.annotation.visibility.KeepEntryPointPublicProtectedMembers class * {
    public protected *;
}

# @KeepEntryPointPublicProtectedGettersSetters specify to keep all, resp.
# all public, getters and setters of the annotated class from being shrunk,
# optimized, or obfuscated as entry points.

-keepclassmembers @com.iostark.x10.base.annotation.visibility.KeepEntryPointPublicProtectedGettersSetters class * {
    public protected void set*(***);
    public protected void set*(int, ***);

    public protected boolean is*();
    public protected boolean is*(int);

    public protected *** get*();
    public protected *** get*(int);
}