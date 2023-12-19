#include <stdio.h>

#include "bytecode/chunk.h"
#include "bytecode/debug/debug.h"
#include "common.h"

int main(int argc, const char* argv[]) {
    Chunk chunk;
    initChunk(&chunk);
    for (int i = 300; i < 600; ++i) {
        writeConstant(&chunk, i, i);
    }
    writeChunk(&chunk, OP_RETURN, 123);
    writeChunk(&chunk, OP_RETURN, 123);
    dissembleChunk(&chunk, "test chunk");
    freeChunk(&chunk);
    return 0;
}
