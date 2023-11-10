package grupa.Expressions;

import grupa.Scanner.Token;

import java.util.List;

public class Call extends Expr {
    private final Expr callee;
    private final List<Expr> arguments;

    private final Token parent;


    public Call(Expr callee, List<Expr> arguments, Token parent) {
        this.callee = callee;
        this.arguments = arguments;
        this.parent = parent;
    }

    public Expr getCallee() {
        return callee;
    }

    public List<Expr> getArguments() {
        return arguments;
    }

    public Token getParent() {
        return parent;
    }

    @Override
    public <R> R accept(ExprVisitor<R> exprVisitor) {
        return exprVisitor.visitCallExpression(this);
    }
}
