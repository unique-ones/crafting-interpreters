package grupa.Runtime.Environment;

import grupa.Expressions.Function;
import grupa.Runtime.Exceptions.ReturnException;
import grupa.Runtime.Interpreter;

import java.util.List;

public class LoxFunction implements LoxCallable {
    private final String name;
    private final grupa.Expressions.Function declaration;
    private final Environment closure;

    public LoxFunction(String name, grupa.Expressions.Function declaration, Environment closure) {
        this.name = name;
        this.declaration = declaration;
        this.closure = closure;
    }

    public String getName() {
        return name;
    }

    public Function getDeclaration() {
        return declaration;
    }

    public Environment getClosure() {
        return closure;
    }

    @Override
    public int getArity() {
        return this.declaration.getParamters().size();
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> args) {
        try {
            Environment environment = new Environment(this.closure);

            for (int i = 0; i < this.declaration.getParamters().size(); i++) {
                environment.define(this.declaration.getParamters().get(i).getLexeme(), args.get(i));
            }
            interpreter.executeBlock(this.declaration.getBody(), environment);
        } catch (ReturnException e) {
            return e.getValue();
        }
        return null;
    }

    @Override
    public String toString() {
        return "LoxFunction{" +
                "name='" + name + '\'' +
                ", declaration=" + declaration +
                ", closure=" + closure +
                '}';
    }

    public Object bind(LoxInstance instance) {
        Environment environment = new Environment(closure);
        environment.define("this", instance);
        //Maybe this crashes, we will see ;)
        return new LoxFunction(this.name, this.declaration, environment);
    }
}
