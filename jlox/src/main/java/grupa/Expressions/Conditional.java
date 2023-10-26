package grupa.Expressions;

import grupa.Parser.RuntimeError;
import grupa.Scanner.Token;
import grupa.Scanner.TokenType;

public class Conditional extends Expression {
    private final Expression condition;
    private final Token question;
    private final Token colon;

    private final Expression trueBranch;
    private final Expression falseBranch;

    public Conditional(Expression condition, Expression trueBranch, Expression falseBranch, Token question,Token colon) {
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

    public Expression getCondition() {
        return condition;
    }

    public Expression getTrueBranch() {
        return trueBranch;
    }

    public Expression getFalseBranch() {
        return falseBranch;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) throws RuntimeError {
        return visitor.visitConditionalExpression(this);
    }
}
