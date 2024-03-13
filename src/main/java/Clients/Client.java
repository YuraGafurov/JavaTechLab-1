package Clients;

import Observer.Observer;
import lombok.Builder;

import java.util.UUID;

@Builder
public class Client implements Observer {
    private UUID id;
    private String name;
    private String surname;
    private String address;
    private String passportNumber;

    public Client(UUID id, String name, String surname, String address, String passportNumber) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.passportNumber = passportNumber;
    }

    public boolean isSuspicious() {
        return address == null || passportNumber == null;
    }

    @Override
    public void update(String eventType) {
        System.out.println("Smth happened");
    }
}
