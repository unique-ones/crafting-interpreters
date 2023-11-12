package grupa.Statements;

import grupa.Expressions.Expr;

public class Expression extends Stmt {
    private final Expr expr;

    public Expression(Expr expr) {
        this.expr = expr;
    }

    public Expr getExpression() {
        return expr;
    }
    @Override
    public <R> R accept(StmtVisitor<R> stmtVisitor)  {
        return stmtVisitor.visitExpressionStatement(this);
    }
}
