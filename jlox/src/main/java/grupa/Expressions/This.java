package grupa.Expressions;

import grupa.Scanner.Token;

public class This extends Expr {

    private final Token keyword;

    public This(Token keyword) {
        this.keyword = keyword;
    }

    public Token getKeyword() {
        return keyword;
    }

    @Override
    public <R> R accept(ExprVisitor<R> exprVisitor) {
        return exprVisitor.visitThisExpression(this);
    }
}
