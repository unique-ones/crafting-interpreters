package grupa.Interpreter;

import java.util.List;

public interface LoxCallable {
    int getArity();
    Object call(Interpreter interpreter, List<Object> args);
}
