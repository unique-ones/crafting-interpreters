package grupa.Expressions;

import grupa.Interpreter.RuntimeError;

public class Grouping extends Expr {
    final Expr expr;
    public Grouping(Expr expr) {
        this.expr = expr;
    }

    @Override
    public <R> R accept(ExprVisitor<R> exprVisitor)  {
        return exprVisitor.visitGroupingExpression(this);
    }

    public Expr getExpression() {
        return expr;
    }
}
