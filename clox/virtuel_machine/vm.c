#include "vm.h"
#include "../common.h"
#include <stdio.h>
#include "../bytecode/debug/debug.h"

//one global VM so I dont haveto pass it too all functions
VM vm;

// I decided to not free stack
void resetStack(void) {
    vm.stackCount = 0;
}

void push(Value value) {
    if (vm.stackCapacity < vm.stackCount + 1) {
        int oldCapacity = vm.stackCapacity;
        vm.stackCapacity = GROW_CAPACITY(oldCapacity);
        vm.stack = GROW_ARRAY(Value, vm.stack, oldCapacity, vm.stackCapacity);
    }
    vm.stack[vm.stackCount] = value;
    vm.stackCount++;
}

Value pop() {
    return vm.stack[--vm.stackCount];
}

void initVM() {
    vm.stackCapacity = 0;
    vm.stack = NULL;
    resetStack();
}

void freeVM() {
}

//always points to the next instruction being executed
uint8_t read_byte() {
    return *vm.ip++;
}

//Get Value by following index
Value read_constant() {
    return vm.chunk->constants.values[read_byte()];
}

Value read_constant_long() {
    return vm.chunk->constants.values[read_byte() |
                                      (read_byte() << 8) |
                                      (read_byte() << 16)];
}

InterpretResult run(void) {
    //define macro for binary operations
#define BINARY_OP(op)\
do{\
   double b=pop(); \
   double a=pop(); \
   push(a op b);\
}while(false)

    for (;;) {
        //debug instruction right before execution
# ifdef DEBUG_TRACE_EXECUTION
        printf("        ");
        for (Value* slot = vm.stack; slot < vm.stack + vm.stackCount; slot++) {
            printf("[ ");
            printValue(*slot);
            printf(" ]");
        }
        printf("\n");
        dissembleInstruction(vm.chunk, (int) (vm.ip - vm.chunk->code));
#endif
        uint8_t instruction;
        //Given a numeric opcode is also called "dispatching" or "decoding" the instruction
        switch (instruction = read_byte()) {
            case OP_CONSTANT: {
                Value constant = read_constant();
                push(constant);
                break;
            }
            case OP_CONSTANT_LONG: {
                Value constant = read_constant_long();
                push(constant);
                break;
            }
            case OP_NEGATE: {
                push(-pop());
                break;
            }
            case OP_ADD: {
                BINARY_OP(+);
                break;
            }
            case OP_SUBTRACT: {
                BINARY_OP(-);
                break;
            }
            case OP_DIVIDE: {
                BINARY_OP(/);
                break;
            }
            case OP_MULTIPLY: {
                BINARY_OP(*);
                break;
            }
            case OP_RETURN: {
                printValue(pop());
                printf("\n");
                return INTERPRET_OK;
            }
        }
    }
#undef BINARY_OP
}

InterpretResult interpret(Chunk* chunk) {
    vm.chunk = chunk;
    vm.ip = vm.chunk->code;
    return run();
}
