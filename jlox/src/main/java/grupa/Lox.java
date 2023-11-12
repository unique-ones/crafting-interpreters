package grupa;

import grupa.Expressions.Expr;
import grupa.Parser.Ast;
import grupa.Interpreter.Interpreter;
import grupa.Interpreter.Exceptions.RuntimeError;
import grupa.Scanner.Scanner;
import grupa.Scanner.Token;
import grupa.Scanner.TokenType;
import grupa.Statements.Stmt;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Lox {
    static boolean hadError = false;
    static boolean hadRunTimeError = false;
    static Interpreter interpreter = new Interpreter();

    public static void main(String[] args) throws IOException {

        //P:\_repos\crafting-interpreters\jlox\src\main\examples\test1.jlox
        if (args.length > 1) {
            System.out.println("Usage: jlox [script]");
            System.exit(64);
        } else if (args.length == 1) {
            runFile(args[0]);
        } else {
            runPrompt();
        }
    }

    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
        if (hadError) System.exit(65);
        if (hadRunTimeError) System.exit(70);

    }

    private static void runPrompt() throws IOException {

        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);
        for (; ; ) {
            hadError = false;
            hadError = false;
            System.out.println("> ");
            String line = reader.readLine();
            if (line == null) break;

            Scanner scanner = new Scanner(line);
            List<Token> tokens = scanner.scanTokens();
            Object syntax = new Ast(tokens).parseRepl();
            if (hadError) continue;

            if (syntax instanceof Expr) {
                String result = interpreter.interpret((Expr) syntax);
                if (result != null) {
                    System.out.println("=" + result);
                }
            } else {
                interpreter.interpret((List<Stmt>) syntax);
            }
        }
    }

    //@TODO add better "boilerplate" for REPL and File run
    private static void run(String source) {
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();
        List<Stmt> stmts = new Ast(tokens).parse();
        if (hadError) return;
        interpreter.interpret(stmts);
    }

    /*@TODO
        - Add better error handling. E.g Column, Argument list, etc
        - Fix: Each Unexpected character gets reported separately -> Couple them together to one error message
    */
    public static void error(int line, String message) {
        report(line, "", message);
    }

    public static void error(Token token, String message) {
        if (token.getType() == TokenType.EOF) {
            report(token.getLine(), " at end", message);
        } else {
            report(token.getLine(), " at '" + token.getLexeme() + "'", message);
        }
    }

    private static void report(int line, String where, String message) {
        System.err.println("[line " + line + "] Error" + where + ": " + message);
        hadError = true;
    }

    public static void runtimeError(RuntimeError error) {
        System.err.println(error.getMessage() + "\n [line " + error.getToken().getLine() + "]");
        hadRunTimeError = true;
    }
}
