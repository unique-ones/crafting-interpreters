package grupa.Statements;

import grupa.Expressions.Environment;
import grupa.Interpreter.Exceptions.ReturnException;
import grupa.Interpreter.Interpreter;
import grupa.Interpreter.LoxCallable;

import java.util.List;

public class LoxFunction implements LoxCallable {
    private final Function function;

    public LoxFunction(Function function) {
        this.function = function;
    }

    public Function getFunction() {
        return function;
    }

    @Override
    public int getArity() {
        return this.function.getParams().size();
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> args) {
        try {
            Environment environment = new Environment(interpreter.getGlobals());

            for (int i = 0; i < this.function.getParams().size(); i++) {
                environment.define(function.getParams().get(i).getLexeme(), args.get(i));
            }
            interpreter.executeBlock(function.getBody(), environment);
        } catch (ReturnException e) {
            return e.getValue();
        }
        return null;
    }

    @Override
    public String toString() {
        return "LoxFunction{" +
                "function=" + function +
                '}';
    }
}
