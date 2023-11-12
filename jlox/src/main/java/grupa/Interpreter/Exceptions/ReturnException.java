package grupa.Interpreter.Exceptions;

import grupa.Expressions.Expr;

public class ReturnException extends RuntimeException {
    private final Object value;

    public ReturnException(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }
}
