package test.sealedclass;

public class SealedClassDemo {
    public static void main(String[] args) {
        Animal cat = new Cat("Whiskers");
        System.out.println(cat.getName());
        cat.speak();

        Animal dog = new Dog("Fido");
        System.out.println(dog.getName());
        dog.speak();

        Animal bird = new Bird("Tweety");
        System.out.println(bird.getName());
        bird.speak();
    }
}
