package grupa.Statements;

import grupa.Expressions.ExprVisitor;
import grupa.Parser.RuntimeError;

public abstract class Stmt {
    public abstract <R> R accept(StmtVisitor<R> stmtVisitor) throws RuntimeError;
}
