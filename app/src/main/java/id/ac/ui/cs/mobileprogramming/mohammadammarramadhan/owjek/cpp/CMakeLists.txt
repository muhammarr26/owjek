# Sets the minimum version of CMake required to build your native library.
# This ensures that a certain set of CMake features is available to
# your build.

cmake_minimum_required(VERSION 3.4.1)

# Specifies a library name, specifies whether the library is STATIC or
# SHARED, and provides relative paths to the source code. You can
# define multiple libraries by adding multiple add_library() commands,
# and CMake builds them for you. When you build your app, Gradle
# automatically packages shared libraries with your APK.

add_library( # Specifies the name of the library.
             owride

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             owride.cpp )
include_directories(.include/)

# GLESv2
find_path(GLES2_INCLUDE_DIR GLES2/gl2.h
        HINTS ${ANDROID_NDK})
find_library(GLES2_LIBRARY libGLESv2.so
        HINTS ${GLES2_INCLUDE_DIR}/../lib)
target_link_libraries(owride ${GLES2_LIBRARY})

