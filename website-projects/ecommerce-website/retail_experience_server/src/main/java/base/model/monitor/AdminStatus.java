package base.model.monitor;

public class AdminStatus {

    private int numberOfConnectedClients;
    private int numberOfOrdersWaiting;
    private int numberOfOrdersProcessing;
    private int numberOfOrdersDone;
    private String memoryUsage;

    public int getNumberOfConnectedClients() {
        return numberOfConnectedClients;
    }

    public void setNumberOfConnectedClients(int numberOfConnectedClients) {
        this.numberOfConnectedClients = numberOfConnectedClients;
    }

    public int getNumberOfOrdersWaiting() {
        return numberOfOrdersWaiting;
    }

    public void setNumberOfOrdersWaiting(int numberOfOrdersWaiting) {
        this.numberOfOrdersWaiting = numberOfOrdersWaiting;
    }

    public int getNumberOfOrdersProcessing() {
        return numberOfOrdersProcessing;
    }

    public void setNumberOfOrdersProcessing(int numberOfOrdersProcessing) {
        this.numberOfOrdersProcessing = numberOfOrdersProcessing;
    }

    public int getNumberOfOrdersDone() {
        return numberOfOrdersDone;
    }

    public void setNumberOfOrdersDone(int numberOfOrdersDone) {
        this.numberOfOrdersDone = numberOfOrdersDone;
    }

    public String getMemoryUsage() {
        return memoryUsage;
    }

    public void setMemoryUsage(String memoryUsage) {
        this.memoryUsage = memoryUsage;
    }

    @Override
    public String toString() {
        return "Monitor Information: \n" +
                "1. numberOfConnectedClients=" + numberOfConnectedClients + "\n" +
                "2. numberOfOrdersWaiting=" + numberOfOrdersWaiting + "\n" +
                "3. numberOfOrdersProcessing=" + numberOfOrdersProcessing + "\n" +
                "4. numberOfOrdersDone=" + numberOfOrdersDone + "\n" +
                "5. memoryUsage=" + memoryUsage;
    }
}
