#include <stdio.h>
#include <stdlib.h>
#include "bytecode/chunk.h"
#include "bytecode/debug/debug.h"
#include "common.h"
#include "virtual_machine/vm.h"

void repl(void) {
    //@TODO change hardcoded line length limit
    char line[1024];
    for (;;) {
        printf("> ");
        if (!fgets(line, sizeof(line),stdin)) {
            printf("\n");
            break;
        }
        interpret(line);
    }
}

//trick to allcote big enough string of the file
char* readFile(const char* path) {
    FILE* file = fopen(path, "rb");
    if (file == NULL) {
        fprintf(stderr, "Couldn't open file \"%s\".\n", path);
        exit(74);
    }
    //goes to end of file
    fseek(file, 0L, SEEK_END);
    //returns how many bytes The pointer is away from file origin
    size_t fileSize = ftell(file);
    //puts filepointer back to start
    rewind(file);
    //allocates enough bytes + 1 (\0)
    char* buffer = (char *) malloc(fileSize + 1);
    if (buffer == NULL) {
        fprintf(stderr, "Not enough memory to read \"%s\".\n", path);
        exit(74);
    }
    //reads every char into buffer
    size_t bytesRead = fread(buffer, sizeof(char), fileSize, file);
    if (bytesRead < fileSize) {
        fprintf(stderr, "Couldn't read file \"%s\".\n", path);
        exit(74);
    }
    //adds terminating char at end
    buffer[bytesRead] = '\0';
    fclose(file);
    return buffer;
}

void runFile(const char* path) {
    char* source = readFile(path);
    InterpretResult interpret_result = interpret(source);
    if (interpret_result == INTERPRET_COMPILE_ERROR) exit(65);
    if (interpret_result == INTERPRET_RUNTIME_ERROR) exit(70);
}

int main(int argc, const char* argv[]) {
    initVM();
    if (argc == 1) {
        repl();
    } else if (argc == 2) {
        runFile(argv[1]);
    } else {
        fprintf(stderr, "Usage: clox [path]\n");
        exit(64);
    }
    freeVM();
    return 0;
}


// initVM();
// Chunk chunk;
// initChunk(&chunk);
// writeConstant(&chunk, 1.2, 123);
// writeConstant(&chunk, 3.4, 123);
// writeChunk(&chunk, OP_ADD, 123);
// writeConstant(&chunk, 5.6, 123);
// writeChunk(&chunk, OP_DIVIDE, 123);
// writeChunk(&chunk, OP_NEGATE, 123);
// writeChunk(&chunk, OP_RETURN, 123);
// // dissembleChunk(&chunk, "test chunk");
// interpret(&chunk);
// freeVM();
// freeChunk(&chunk);
