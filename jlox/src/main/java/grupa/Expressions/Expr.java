package grupa.Expressions;

import grupa.Parser.RuntimeError;

abstract public class Expr {
    public abstract <R> R accept(ExprVisitor<R> exprVisitor) throws RuntimeError;

}
