package grupa.Expressions;

import grupa.Scanner.Token;

public class Super extends Expr {
    private final Token keyword;
    private final Token method;

    public Super(Token keyword, Token method) {
        this.keyword = keyword;
        this.method = method;
    }

    public Token getMethod() {
        return method;
    }

    public Token getKeyword() {
        return keyword;
    }

    @Override
    public <R> R accept(ExprVisitor<R> exprVisitor) {
        return exprVisitor.visitSuperExpression(this);
    }
}
