package grupa.Expressions;

import grupa.Interpreter.RuntimeError;

abstract public class Expr {
    public abstract <R> R accept(ExprVisitor<R> exprVisitor) throws RuntimeError;

}
