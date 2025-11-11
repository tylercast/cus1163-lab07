import java.util.*;

public class RoundRobinLab {

    static class Process {
        int id;
        int arrivalTime;
        int burstTime;
        int remainingTime;
        int completionTime;
        int turnaroundTime;
        int waitingTime;

        public Process(int id, int arrivalTime, int burstTime) {
            this.id = id;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
            this.remainingTime = burstTime;
        }
    }

    // ✅ Completed Round Robin algorithm
    public static void scheduleRoundRobin(List<Process> processes, int timeQuantum) {
        int currentTime = 0;
        ArrayList<Process> readyQueue = new ArrayList<>();

        // Add all processes to queue
        for (Process p : processes) readyQueue.add(p);

        // Main scheduling loop
        while (!readyQueue.isEmpty()) {
            Process current = readyQueue.remove(0);
            int executeTime = Math.min(timeQuantum, current.remainingTime);
            currentTime += executeTime;
            current.remainingTime -= executeTime;

            if (current.remainingTime == 0)
                current.completionTime = currentTime;
            else
                readyQueue.add(current);
        }

        // Compute metrics
        for (Process p : processes) {
            p.turnaroundTime = p.completionTime - p.arrivalTime;
            p.waitingTime = p.turnaroundTime - p.burstTime;
        }
    }

    // Provided
    public static void calculateMetrics(List<Process> processes, int timeQuantum) {
        System.out.println("========================================");
        System.out.println("Round Robin Scheduling Simulator");
        System.out.println("========================================\n");
        System.out.println("Time Quantum: " + timeQuantum + "ms");
        System.out.println("----------------------------------------");
        System.out.println("Process | Arrival | Burst | Completion | Turnaround | Waiting");

        double totalTurnaround = 0;
        double totalWaiting = 0;

        for (Process p : processes) {
            System.out.printf("   %d    |    %d    |   %d   |     %d     |     %d     |    %d\n",
                    p.id, p.arrivalTime, p.burstTime, p.completionTime,
                    p.turnaroundTime, p.waitingTime);
            totalTurnaround += p.turnaroundTime;
            totalWaiting += p.waitingTime;
        }

        System.out.println();
        System.out.printf("Average Turnaround Time: %.2fms\n", totalTurnaround / processes.size());
        System.out.printf("Average Waiting Time: %.2fms\n", totalWaiting / processes.size());
        System.out.println("========================================\n\n");
    }

    // Provided
    public static void main(String[] args) {
        List<Process> p1 = new ArrayList<>();
        p1.add(new Process(1, 0, 7));
        p1.add(new Process(2, 0, 4));
        p1.add(new Process(3, 0, 2));

        scheduleRoundRobin(p1, 3);
        calculateMetrics(p1, 3);

        List<Process> p2 = new ArrayList<>();
        p2.add(new Process(1, 0, 7));
        p2.add(new Process(2, 0, 4));
        p2.add(new Process(3, 0, 2));

        scheduleRoundRobin(p2, 5);
        calculateMetrics(p2, 5);
    }
}
