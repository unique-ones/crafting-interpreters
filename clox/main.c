#include <stdio.h>

#include "bytecode/chunk.h"
#include "bytecode/debug/debug.h"
#include "common.h"

int main(int argc, const char* argv[]) {
    Chunk chunk;
    initChunk(&chunk);
    int constant = addConstant(&chunk, 1.2);
    writeChunk(&chunk, OP_CONSTANT, 123);
    writeChunk(&chunk, constant, 123);
    writeChunk(&chunk, OP_RETURN, 123);
    writeChunk(&chunk, OP_RETURN, 124);
    dissembleChunk(&chunk, "test chunk");
    freeChunk(&chunk);
    return 0;
}
