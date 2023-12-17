#ifndef clox_values_h
#define clox_values_h
#include "../common.h"
#include "../memory_operations/memory.h"
typedef double Value;

typedef struct {
    int capacity;
    int count;
    Value* values;
} ValueArray;

void initValueArray(ValueArray* array);

void writeValueArray(ValueArray* array, Value);

void freeValueArray(ValueArray* array);
void printValue(Value value);

#endif
