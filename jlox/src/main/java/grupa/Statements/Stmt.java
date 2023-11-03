package grupa.Statements;

import grupa.Interpreter.RuntimeError;

public abstract class Stmt {
    public abstract <R> R accept(StmtVisitor<R> stmtVisitor) throws RuntimeError;
}
