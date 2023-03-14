package test.sealedclass;

public final class Bird extends Animal {
    public Bird(String name) {
        super(name);
    }

    public void speak() {
        System.out.println("Chirp.");
    }
}