package test.sealedclass;

public final class Cat extends Animal {
    public Cat(String name) {
        super(name);
    }

    public void speak() {
        System.out.println("Meow.");
    }
}
