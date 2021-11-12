package ru.alexanna.lesson_1;

import java.util.ArrayList;
import java.util.List;

public class Box <T extends Fruit> {
    private T fruit;
    private List<T> container;

    public Box(T fruit) {
        this.fruit = fruit;
        container = new ArrayList<>();
    }

    public void add(T fruit) {
        container.add(fruit);
    }

    public float getWeight() {
        return fruit.getWeight() * container.size();
    }

    public <T extends Fruit> boolean compare(Box<? extends Fruit> box) {
        return this.getWeight() == box.getWeight();
    }

    public boolean pourFruitInto(Box<T> box) {
            if (box.container.addAll(this.container)) {
                container.clear();
                return true;
            }
            return false;
    }
}
