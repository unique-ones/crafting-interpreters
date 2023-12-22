#include <stdio.h>

#include "bytecode/chunk.h"
#include "bytecode/debug/debug.h"
#include "common.h"
#include "virtuel_machine/vm.h"

int main(int argc, const char* argv[]) {
    initVM();
    Chunk chunk;
    initChunk(&chunk);
    for (int i = 300; i < 600; ++i) {
        writeConstant(&chunk, i, i);
    }
    writeChunk(&chunk, OP_RETURN, 123);
    writeChunk(&chunk, OP_RETURN, 123);
   // dissembleChunk(&chunk, "test chunk");
    interpret(&chunk);
    freeVM();
    freeChunk(&chunk);
    return 0;
}
