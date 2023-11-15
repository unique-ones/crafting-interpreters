package grupa.Expressions;

import grupa.Scanner.Token;
import grupa.Statements.Stmt;

import java.util.List;

public class Function extends Expr {
    private final List<Token> paramters;
    private final List<Stmt> body;

    public Function(List<Token> paramters, List<Stmt> body) {
        this.paramters = paramters;
        this.body = body;
    }

    public List<Token> getParamters() {
        return paramters;
    }

    public List<Stmt> getBody() {
        return body;
    }
    @Override
    public <R> R accept(ExprVisitor<R> exprVisitor) {
        return exprVisitor.visitFunctionExpression(this);
    }
}
