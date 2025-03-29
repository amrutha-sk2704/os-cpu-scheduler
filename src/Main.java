import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("-----------------FCFS----------------");
        fcfs();
        System.out.println("-----------------SJF-----------------");
        sjf();
        System.out.println("-----------------PSN-----------------");
        psn();
        System.out.println("-----------------RR------------------");
        rr();
        System.out.println("-----------------Hybrid Scheduler----------------");
        hybrid();
    }

    public static void fcfs() {
        CPUScheduler fcfs = new FirstComeFirstServe();
        fcfs.add(new Row("P1", 0, 5));
        fcfs.add(new Row("P2", 2, 3));
        fcfs.add(new Row("P3", 4, 2));
        fcfs.add(new Row("P4", 6, 4));
        fcfs.add(new Row("P5", 7, 1));
        fcfs.process();
        display(fcfs);
    }

    public static void sjf() {
        CPUScheduler sjf = new ShortestJobFirst();
        sjf.add(new Row("P1", 0, 5));
        sjf.add(new Row("P2", 2, 3));
        sjf.add(new Row("P3", 4, 2));
        sjf.add(new Row("P4", 6, 4));
        sjf.add(new Row("P5", 7, 1));
        sjf.process();
        display(sjf);
    }

    public static void psn() {
        CPUScheduler psn = new PriorityNonPreemptive();
        psn.add(new Row("P1", 0, 5));
        psn.add(new Row("P2", 2, 3));
        psn.add(new Row("P3", 4, 2));
        psn.add(new Row("P4", 6, 4));
        psn.add(new Row("P5", 7, 1));
        psn.process();
        display(psn);
    }

    public static void rr() {
        CPUScheduler rr = new RoundRobin();
        rr.setTimeQuantum(2);
        rr.add(new Row("P1", 0, 5));
        rr.add(new Row("P2", 2, 3));
        rr.add(new Row("P3", 4, 2));
        rr.add(new Row("P4", 6, 4));
        rr.add(new Row("P5", 7, 1));
        rr.process();
        display(rr);
    }

    public static void hybrid() {
        HybridScheduler hybrid = new HybridScheduler(2); // Example time quantum for Round Robin
        hybrid.add(new Row("P1", 0, 5));
        hybrid.add(new Row("P2", 2, 3));
        hybrid.add(new Row("P3", 4, 2));
        hybrid.add(new Row("P4", 6, 4));
        hybrid.add(new Row("P5", 7, 1));
        hybrid.process();
        display(hybrid);
    }

    public static void display(CPUScheduler object) {
        // Display the results of the scheduler
        System.out.println("Process\tAT\tBT\tWT\tTAT");

        for (Row row : object.getRows()) {
            System.out.println(row.getProcessName() + "\t" + row.getArrivalTime() + "\t" + row.getBurstTime() + "\t" + row.getWaitingTime() + "\t" + row.getTurnaroundTime());
        }

        System.out.println();

        for (int i = 0; i < object.getTimeline().size(); i++) {
            List<Event> timeline = object.getTimeline();
            System.out.print(timeline.get(i).getStartTime() + "(" + timeline.get(i).getProcessName() + ")");

            if (i == object.getTimeline().size() - 1) {
                System.out.print(timeline.get(i).getFinishTime());
            }
        }

        System.out.println("\n\nAverage WT: " + object.getAverageWaitingTime() + "\nAverage TAT: " + object.getAverageTurnAroundTime());
    }
}
