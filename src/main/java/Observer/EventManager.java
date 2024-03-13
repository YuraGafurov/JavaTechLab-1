package Observer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager {
    Map<String, List<Observer>> observers = new HashMap<>();

    public void subscribe(String eventType, Observer observer) {
        observers.get(eventType).add(observer);
    }

    public void notify(String eventType) {
        observers.get(eventType).forEach(observer -> observer.update(eventType));
    }
}
