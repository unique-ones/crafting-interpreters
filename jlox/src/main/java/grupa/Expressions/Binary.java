package grupa.Expressions;

import grupa.Token;


public class Binary {
    final Expression left;
    final Token operator;
    final Expression right;

    Binary(Expression left, Token operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }


}
