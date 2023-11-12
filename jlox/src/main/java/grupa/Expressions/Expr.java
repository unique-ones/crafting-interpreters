package grupa.Expressions;

abstract public class Expr {
    public abstract <R> R accept(ExprVisitor<R> exprVisitor) ;

}
