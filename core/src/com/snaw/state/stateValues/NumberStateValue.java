package com.snaw.state.stateValues;

import com.snaw.state.Condition;

public class NumberStateValue {
    //States freely mix ints and doubles and operate them against one another.
    //This is used because functional interfaces, specifically, don't really have good support for this.
    Integer intValue;
    Double doubleValue;

    public NumberStateValue(Integer intValue) {
        this.intValue = intValue;
        this.doubleValue = null;
    }

    public NumberStateValue(Double doubleValue) {
        this.intValue = null;
        this.doubleValue = doubleValue;
    }

    public NumberStateValue() {
        this.intValue = 0;
        this.doubleValue = null;
    }

    public static Condition<NumberStateValue> parse(String key, String numberString) {
        return new Condition<>(key, parse(numberString));
    }

    public static NumberStateValue parse(String numberString) {
        if (numberString.contains(".")) {
            return new NumberStateValue(Double.parseDouble(numberString));
        } else {
            return new NumberStateValue(Integer.parseInt(numberString));
        }
    }

    public NumberStateValue copy() {
        if (this.intValue != null) {
            return new NumberStateValue(intValue);
        }
        return new NumberStateValue(doubleValue);
    }

    //Getters/setters
    public void set(Integer intValue) {
        this.intValue = intValue;
        this.doubleValue = null;
    }

    public void set(Double doubleValue) {
        this.doubleValue = doubleValue;
        this.intValue = null;
    }

    public int getInt() {
        if (intValue != null) {
            return intValue;
        }
        return (int) (Math.floor(doubleValue));
    }

    public Double getDouble() {
        if (doubleValue != null) {
            return doubleValue;
        }

        return intValue.doubleValue();
    }

    //Comparors.
    public boolean isLessThan(NumberStateValue nsv) {
        if (this.intValue != null && nsv.intValue != null) {
            return this.intValue < nsv.intValue;
        }

        return getDouble() < nsv.getDouble();
    }

    public boolean isLessThanOrEqual(NumberStateValue nsv) {
        if (this.intValue != null && nsv.intValue != null) {
            return this.intValue <= nsv.intValue;
        }

        return getDouble() <= nsv.getDouble();
    }

    public boolean isGreaterThan(NumberStateValue nsv) {
        if (this.intValue != null && nsv.intValue != null) {
            return this.intValue > nsv.intValue;
        }

        return getDouble() > nsv.getDouble();
    }

    public boolean isGreaterThanOrEqual(NumberStateValue nsv) {
        if (this.intValue != null && nsv.intValue != null) {
            return this.intValue >= nsv.intValue;
        }

        return getDouble() >= nsv.getDouble();
    }

    public boolean isNotEqual(NumberStateValue nsv) {
        return !isEqual(nsv);
    }

    @Override
    public boolean equals(Object object) {
        if (object.getClass().equals(NumberStateValue.class)) {
            return isEqual((NumberStateValue) object);
        }
        if (object.getClass().equals(Double.class)) {
            return isEqual(new NumberStateValue((Double) object));
        }
        if (object.getClass().equals(Integer.class)) {
            return isEqual(new NumberStateValue((Integer) object));
        }
        return false;
    }

    public boolean isEqual(NumberStateValue nsv) {
        if (this.intValue != null && nsv.intValue != null) {
            return this.intValue == nsv.intValue;
        }

        return getDouble() == nsv.getDouble();
    }

    //Operators, no-arg
    public void increment() {
        if (this.intValue != null) {
            this.intValue++;
        }
        if (this.doubleValue != null) {
            this.doubleValue++;
        }
    }

    public void floor() {
        if (this.intValue == null) {
            set(((Double) Math.floor(this.doubleValue)).intValue());
        }
    }

    public void ceiling() {
        if (this.intValue == null) {
            set(((Double) Math.ceil(this.doubleValue)).intValue());
        }
    }

    //Operators, one-arg
    public void set(NumberStateValue numberStateValue) {
        if (numberStateValue.doubleValue != null) {
            set(numberStateValue.doubleValue);
        } else {
            set(numberStateValue.intValue);
        }
    }

    public void add(NumberStateValue numberStateValue) {
        if (this.doubleValue != null || numberStateValue.doubleValue != null) {
            set(this.getDouble() + numberStateValue.getDouble());
        } else {
            set(this.getInt() + numberStateValue.getInt());
        }
    }

    public void subtract(NumberStateValue numberStateValue) {
        if (this.doubleValue != null || numberStateValue.doubleValue != null) {
            set(this.getDouble() - numberStateValue.getDouble());
        } else {
            set(this.getInt() - numberStateValue.getInt());
        }
    }

    public void multiply(NumberStateValue numberStateValue) {
        if (this.doubleValue != null || numberStateValue.doubleValue != null) {
            set(this.getDouble() * numberStateValue.getDouble());
        } else {
            set(this.getInt() * numberStateValue.getInt());
        }
    }

    public void divide(NumberStateValue numberStateValue) {
        if (this.doubleValue != null || numberStateValue.doubleValue != null) {
            set(this.getDouble() / numberStateValue.getDouble());
        } else {
            set(this.getInt() / numberStateValue.getInt());
        }
    }

    public void exponentiate(NumberStateValue numberStateValue) {
        //ints only.
        set(this.getInt() ^ numberStateValue.getInt());
    }

    public void mod(NumberStateValue numberStateValue) {
        set(this.getInt() % numberStateValue.getInt());
    }

    public void lowerLimit(NumberStateValue numberStateValue) {
        if (this.isLessThan(numberStateValue)) {
            this.set(numberStateValue);
        }
    }

    public void upperLimit(NumberStateValue numberStateValue) {
        if (this.isGreaterThan(numberStateValue)) {
            this.set(numberStateValue);
        }
    }
}