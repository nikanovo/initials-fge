import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class ElevatorSystem {
    private final List<Elevator> elevators;
    private final Queue<Request> requests;

    public ElevatorSystem(int numElevators) {
        elevators = new ArrayList<>();
        requests = new LinkedList<>();

        for (int i = 0; i < numElevators; i++) {
            elevators.add(new Elevator(i + 1, this, 1000)); // Максимальный вес 1000 кг
        }
    }

    public void addRequest(Request request) {
        synchronized (requests) {
            requests.offer(request);
            requests.notifyAll();
        }
    }

    public Request getRequest() {
        synchronized (requests) {
            while (requests.isEmpty()) {
                try {
                    requests.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            return requests.poll();
        }
    }

    public void start() {
        for (Elevator elevator : elevators) {
            new Thread(elevator).start();
        }
    }

    public static void main(String[] args) {
        ElevatorSystem system = new ElevatorSystem(3);
        system.start();

        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int floorFrom = random.nextInt(10) + 1; // Floors from 1 to 10
            int floorTo = random.nextInt(10) + 1;
            while (floorTo == floorFrom) {
                floorTo = random.nextInt(10) + 1; // Ensure different floors
            }
            int passengerWeight = random.nextInt(200) + 50; // Вес пассажира от 50 до 250 кг
            system.addRequest(new Request(floorFrom, floorTo, passengerWeight));
            try {
                Thread.sleep(1000); // Simulate time between requests
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
