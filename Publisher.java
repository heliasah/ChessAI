import java.util.ArrayList;

public class Publisher {
    private ArrayList<Subscriber> subscribers = new ArrayList<>();

    void addSubscriber(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    void publish(Object obj) {
        for (Subscriber subscriber : this.subscribers) {
            subscriber.onEvent(obj);
        }
    }
}
