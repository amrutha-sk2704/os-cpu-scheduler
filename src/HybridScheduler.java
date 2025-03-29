import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class HybridScheduler extends CPUScheduler {
    private final int timeQuantum;

    public HybridScheduler(int timeQuantum) {
        this.timeQuantum = timeQuantum;
    }

    @Override
    public void process() {
        Collections.sort(getRows(), (o1, o2) -> Integer.compare(o1.getArrivalTime(), o2.getArrivalTime()));

        List<Event> timeline = getTimeline();
        Iterator<Row> iterator = getRows().iterator();

        LinkedList<Row> rrQueue = new LinkedList<>();

        boolean sjfEnabled = false;
        int lastSJFArrival = 0;
        int shortestJobBurst = Integer.MAX_VALUE;

        while (iterator.hasNext()) {
            Row row = iterator.next();
            if (sjfEnabled && row.getArrivalTime() > lastSJFArrival) {
                sjfEnabled = false;
            }
            if (row.getArrivalTime() >= lastSJFArrival && !sjfEnabled) {

                shortestJobBurst = Integer.MAX_VALUE;
                sjfEnabled = true;
            }
            if (sjfEnabled) {
                if (row.getBurstTime() < shortestJobBurst) {
                    shortestJobBurst = row.getBurstTime();
                    lastSJFArrival = row.getArrivalTime();
                }
            }
            rrQueue.add(row);
        }

        while (!rrQueue.isEmpty()) {
            Row currentRow = rrQueue.poll();
            if (timeline.isEmpty()) {
                timeline.add(new Event(currentRow.getProcessName(), currentRow.getArrivalTime(), currentRow.getArrivalTime() + currentRow.getBurstTime()));
            } else {
                Event lastEvent = timeline.get(timeline.size() - 1);
                timeline.add(new Event(currentRow.getProcessName(), lastEvent.getFinishTime(), lastEvent.getFinishTime() + currentRow.getBurstTime()));
            }
            currentRow.setWaitingTime(getEvent(currentRow).getStartTime() - currentRow.getArrivalTime());
            currentRow.setTurnaroundTime(currentRow.getWaitingTime() + currentRow.getBurstTime());
        }
    }
}
