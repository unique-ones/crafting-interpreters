package grupa.Expressions;

import grupa.Interpreter.RuntimeError;
import grupa.Scanner.Token;

public class Conditional extends Expr {
    private final Expr condition;
    private final Token question;
    private final Token colon;

    private final Expr trueBranch;
    private final Expr falseBranch;

    public Conditional(Expr condition, Expr trueBranch, Expr falseBranch, Token question, Token colon) {
        this.condition = condition;
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
        this.question=question;
        this.colon=colon;
    }

    public Token getQuestion() {
        return question;
    }

    public Token getColon() {
        return colon;
    }

    public Expr getCondition() {
        return condition;
    }

    public Expr getTrueBranch() {
        return trueBranch;
    }

    public Expr getFalseBranch() {
        return falseBranch;
    }

    @Override
    public <R> R accept(ExprVisitor<R> exprVisitor) throws RuntimeError {
        return exprVisitor.visitConditionalExpression(this);
    }
}
