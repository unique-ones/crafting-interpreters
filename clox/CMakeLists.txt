cmake_minimum_required(VERSION 3.27)
project(clox C)

set(CMAKE_C_STANDARD 17)

add_executable(clox main.c
        common.h
        bytecode/chunk.h
        bytecode/chunk.c
        memory_operations/memory.c
        memory_operations/memory.h
        bytecode/debug/debug.c
        bytecode/debug/debug.h
        instructions/values.c
        instructions/values.h
)
