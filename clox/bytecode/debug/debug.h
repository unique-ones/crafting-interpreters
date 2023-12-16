#ifndef clox_debug_h
#define clox_debug_h
#include "../chunk.h"

int dissembleInstruction(Chunk* chunk, int offset);
void dissembleChunk(Chunk* chunk, const char* name);

#endif //DEBUG_H
