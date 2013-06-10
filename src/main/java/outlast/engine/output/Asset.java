package outlast.engine.output;

public class Asset<T> {
    T value;

    public Asset() {}

    public Asset(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    void setValue(T value) {
        this.value = value;
    }
}