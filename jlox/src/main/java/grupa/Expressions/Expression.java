package grupa.Expressions;

abstract public class Expression {
    public abstract <R> R accept(Visitor<R> visitor);

}
