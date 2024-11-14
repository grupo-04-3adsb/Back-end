package tcatelie.microservice.auth.observer;

public interface Observable {

        void addObserver(Observer observer);

        void removeObserver(Observer observer);

        void notifyObservers(String message, Object object);

        void notifyObservers(String message);

}
