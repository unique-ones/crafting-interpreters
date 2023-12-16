//this file represent the constant pool for clox
//this gets stored in each chunk and not in binary executable (intermediate representation), as its not good for big values eg. strings
#include "values.h"

#include <stdio.h>

void initValueArray(ValueArray* array) {
    array->capacity = 0;
    array->count = 0;
    array->values = NULL;
}

void writeValueArray(ValueArray* array, Value value) {
    if (array->capacity < array->count + 1) {
        int oldCapacity = array->capacity;
        array->capacity = GROW_CAPACITY(array->capacity);
        array->values = GROW_ARRAY(Value, array->values, oldCapacity, array->capacity);
    }
    array->values[array->count] = value;
    array->count++;
}

void freeValueArray(ValueArray* array) {
    array->values = FREE_ARRAY(Value, array->values, array->capacity);
    initValueArray(array);
}

void printValue(Value* value) {
    printf("%g",value);
}
