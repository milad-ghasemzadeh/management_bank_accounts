package org.management.enums;

public enum CardType {

    cash("cash", 1),
    credit("credit", 2);

    private String name;
    private Integer value;

    CardType(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }

}
