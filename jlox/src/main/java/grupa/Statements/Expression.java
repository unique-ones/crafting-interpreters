package grupa.Statements;

import grupa.Expressions.Expr;
import grupa.Expressions.ExprVisitor;
import grupa.Parser.RuntimeError;

public class Expression extends Stmt {
    private final Expr expr;

    public Expression(Expr expr) {
        this.expr = expr;
    }

    public Expr getExpression() {
        return expr;
    }
    @Override
    public <R> R accept(StmtVisitor<R> stmtVisitor) throws RuntimeError {
        return stmtVisitor.visitExpressionStatement(this);
    }
}
