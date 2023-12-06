package grupa.Runtime.Environment;

import grupa.Runtime.Exceptions.RuntimeError;
import grupa.Scanner.Token;

import java.util.HashMap;
import java.util.Map;

public class LoxInstance {
    private LoxClass klass;
    private final Map<String, Object> fields = new HashMap<>();

    public LoxInstance(LoxClass klass) {
        this.klass = klass;
    }

    public Object get(Token name) {
        if (fields.containsKey(name.getLexeme())) {
            return fields.get(name.getLexeme());
        }
        LoxFunction method = klass.findMethod(name.getLexeme());
        //late binding happens here
        //only bind method to instance when its needed
        if (method != null) return method.bind(this);
        throw new RuntimeError(name, "Undefined property '" + name.getLexeme() + "'.");
    }

    public void set(Token name, Object value) {
        fields.put(name.getLexeme(), value);
    }

    @Override
    public String toString() {
        return "LoxInstance{" +
                "klass=" + klass +
                ", fields=" + fields +
                '}';
    }
}
