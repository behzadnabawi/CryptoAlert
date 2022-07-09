abstract class Indicator {
    float value;

    Indicator() {
    }

    public float getValue() {
        return value;
    }

    abstract public void initializeValue();

    abstract public void updateValue();
}
