#ifndef clox_vm_h
#define clox_vm_h
#include "../bytecode/chunk.h"
#define STACK_MAX 256


typedef struct {
    Chunk* chunk;
    //we is pointer as it i faster than look up an array with integer index
    uint8_t* ip; //refers to "instruction pointer"
    //Stack of values beeing executed
    Value stack[STACK_MAX];
    Value* stackTop;
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
