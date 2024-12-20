public class Elevator implements Runnable {
    private final int id;
    private final ElevatorSystem system;
    private int currentFloor = 1;
    private int currentWeight = 0; // Текущий вес в лифте
    private final int maxWeight; // Максимальный вес

    public Elevator(int id, ElevatorSystem system, int maxWeight) {
        this.id = id;
        this.system = system;
        this.maxWeight = maxWeight;
    }

    @Override
    public void run() {
        while (true) {
            Request request = system.getRequest();
            serviceRequest(request);
        }
    }

    private void serviceRequest(Request request) {
        log("Request received: " + request.getFromFloor() + " -> " + request.getToFloor());
        
        if (currentWeight + request.getPassengerWeight() > maxWeight) {
            log("Cannot fulfill request: weight limit exceeded. Current weight: " + currentWeight + ", request weight: " + request.getPassengerWeight());
            return; // Не можем выполнить запрос из-за превышения веса
        }

        currentWeight += request.getPassengerWeight(); // Добавляем вес пассажира
        
        moveToFloor(request.getFromFloor());
        log("Elevator " + id + " picked up a passenger on the floor " + request.getFromFloor());
        moveToFloor(request.getToFloor());
        log("Elevator " + id + " dropped off a passenger on the floor " + request.getToFloor());

        currentWeight -= request.getPassengerWeight(); // Убираем вес пассажира после высадки
    }

    private void moveToFloor(int floor) {
        while (currentFloor != floor) {
            if (currentFloor < floor) {
                currentFloor++;
            } else {
                currentFloor--;
            }
            log("Elevator " + id + " moves to the floor " + currentFloor);
            try {
                Thread.sleep(500); // Simulate time to move between floors
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void log(String message) {
        System.out.println("Elevator " + id + ": " + message);
    }
}
