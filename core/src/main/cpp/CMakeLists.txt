
cmake_minimum_required(VERSION 3.22.1) #Your CMAKE downloaded version

# Declares and names the project.
project("myapplication")

add_library( # Sets the name of the library.
        myapplication

        SHARED
        native-lib.cpp)


find_library( # Sets the name of the path variable.
        log-lib
        log)


target_link_libraries( # Specifies the target library.
        myapplication
        ${log-lib})
