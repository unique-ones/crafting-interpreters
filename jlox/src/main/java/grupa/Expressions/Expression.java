package grupa.Expressions;

import grupa.Parser.RuntimeError;

abstract public class Expression {
    public abstract <R> R accept(Visitor<R> visitor) throws RuntimeError;

}
