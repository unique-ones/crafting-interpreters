package grupa.Statements;

import grupa.Expressions.Expr;
import grupa.Scanner.Token;

public class Return extends Stmt {
    private final Expr expr;
    private final Token keyword;

    public Return(Token keyword, Expr expr) {
        this.expr = expr;
        this.keyword = keyword;
    }

    public Expr getExpr() {
        return expr;
    }

    @Override
    public <R> R accept(StmtVisitor<R> stmtVisitor) {
        return stmtVisitor.visitReturnStatement(this);
    }
}
