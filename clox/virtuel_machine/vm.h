#ifndef clox_vm_h
#define clox_vm_h
#include "../bytecode/chunk.h"

typedef struct {
    Chunk* chunk;
    //we is pointer as it i faster than look up an array with integer index
    uint8_t* ip; //refers to "instruction pointer"
} VM;


typedef enum {
    INTERPRET_OK,
    INTERPRET_COMPILE_ERROR,
    INTERPRET_RUNTIME_ERROR
} InterpretResult;


void initVM();

void freeVM();

InterpretResult interpret(Chunk* chunk);

#endif
