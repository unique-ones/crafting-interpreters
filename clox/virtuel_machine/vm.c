#include "vm.h"
#include "../common.h"
#include <stdio.h>

//one global VM so I dont haveto pass it too all functions
VM vm;

void initVM() {
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
    for (;;) {
        uint8_t instruction;
        //Given a numeric opcode is also called "dispatching" or "decoding" the instruction
        switch (instruction = read_byte()) {
            case OP_RETURN: {
                return INTERPRET_OK;
            }
            case OP_CONSTANT: {
                Value constant = read_constant();
                printValue(constant);
                printf("\n");
                break;
            }
            case OP_CONSTANT_LONG: {
                Value constant = read_constant_long();
                printValue(constant);
                printf("\n");
                break;
            }
        }
    }
}

InterpretResult interpret(Chunk* chunk) {
    vm.chunk = chunk;
    vm.ip = vm.chunk->code;
    return run();
}
