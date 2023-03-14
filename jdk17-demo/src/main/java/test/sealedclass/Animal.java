package test.sealedclass;

public sealed class Animal permits Cat, Dog, Bird {
    private String name;

    public Animal(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void speak() {
        System.out.println("I am an animal.");
    }
}
