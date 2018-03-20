package rp.warehouse.pc.input;

import rp.warehouse.pc.data.Task;

import java.util.ArrayList;

public class Job {

    //has an item name and the count
    private String name;
    private boolean cancelled = false;
    private ArrayList<Task> tasks;

    public Job(String name, ArrayList<Task> tasks) {

        this.name = name;
        this.tasks = tasks;

    }

    public Job(String name, ArrayList<Task> tasks, boolean cancelled) {

        this.name = name;
        this.tasks = tasks;
        this.cancelled = cancelled;

    }

    public String getName() {
        return name;
    }

    public ArrayList<Task> getItems() {
        return tasks;
    }

    public int numOfTasks() {
        return tasks.size();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void setCancelled (int cancelled) {
        if (cancelled == 0) {
            this.cancelled = false;
        } else
            this.cancelled = true;

    }


}
