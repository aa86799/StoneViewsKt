# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

#add_library(
#        libturbojpeg STATIC IMPORTED)
#set_target_properties(
#        libturbojpeg PROPERTIES IMPORTED_LOCATION ${CMAKE_SOURCE_DIR}/libs/${CMAKE_ANDROID_ARCH_ABI}/libturbojpeg.a
#
#)
#include_directories(include)

# 静态库体积太大，改用动态库。 libturbojpeg.so 链接总是失败；libjpeg.so正常的，压缩函数调用也没问题
add_library(
        libturbojpeg SHARED IMPORTED)
set_target_properties(
        libturbojpeg PROPERTIES IMPORTED_LOCATION ${CMAKE_SOURCE_DIR}/libs/${CMAKE_ANDROID_ARCH_ABI}/libjpeg.so
)
include_directories(${CMAKE_SOURCE_DIR}/include)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

add_library( # Sets the name of the library.
        native-lib

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        smbPitchShift.cpp
        image_black_white_filter.cpp
        image_black_white_reverse_filter.cpp
        floyd_steinberg_dithering.cpp
        stucki_dithering.cpp
        native-lib.cpp )

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
                       native-lib
        libturbojpeg
        jnigraphics
                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )