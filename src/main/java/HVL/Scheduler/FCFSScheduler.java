package HVL.Scheduler;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

public class FCFSScheduler implements Scheduler {

    private final Queue<Task> ready;
    private Task selected;

    FCFSScheduler() {
        this.ready = new ArrayDeque<>();
    }

    @Override
    public Optional<Integer> scheduled() {
        if(selected == null) return Optional.empty();
        return Optional.of(selected.getId());
    }

    @Override
    public List<Integer> ready() {
        return ready.stream().map(Task::getId).toList();
    }

    @Override
    public void addTask(Task task) {
        ready.add(task);
    }

    
    @Override
    public void schedule() {

//if the queue is not empty, select the first task in the queue
        if(!ready.isEmpty() && selected == null) {
            selected = ready.poll();
            if(selected == null) {
                return;
            }
            selected.start();
        } else {
            //if a task is already assigned, check if it is done
            if (selected.isDone()) {
                selected.stop();//if so, end the task
                selected = null;//then clear the task to be ready for a new to be assigned later on
                schedule();//call schedule again to check if there are more tasks in the queue
            }
        }
	
    }

}
