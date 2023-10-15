package grupa.Expressions;

import grupa.Token;

public class Unary {
    final Token operator;
    final Expression right;

    Unary(Token operator, Expression right) {
        this.operator = operator;
        this.right = right;
    }


}
