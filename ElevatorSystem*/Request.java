public class Request {
    private final int fromFloor;
    private final int toFloor;
    private final int passengerWeight;

    public Request(int fromFloor, int toFloor, int passengerWeight) {
        this.fromFloor = fromFloor;
        this.toFloor = toFloor;
        this.passengerWeight = passengerWeight;
    }

    public int getFromFloor() {
        return fromFloor;
    }

    public int getToFloor() {
        return toFloor;
    }

    public int getPassengerWeight() {
        return passengerWeight;
    }
}
