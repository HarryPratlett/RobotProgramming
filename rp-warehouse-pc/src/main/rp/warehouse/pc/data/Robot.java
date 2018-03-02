package rp.warehouse.pc.data;

import lejos.util.Delay;
import rp.warehouse.pc.communication.Communication;
import rp.warehouse.pc.route.RobotController;

import java.util.Queue;

public class Robot {
    public enum Response {
        WAITING, OK, FAIL
    }

    private final String ID;
    private final String name;
    private final Object lock = new Object();
    private Queue<Integer> route;
    private Location location;
    private int currentJob;
    private final Communication comms;
    private Response response = Response.WAITING;
    private boolean fail = false;
    private RobotController controller;

    public Robot(String ID, String name) {
        this.ID = ID;
        this.name = name;
        setLocation();
        this.comms = new Communication(ID, name, this);
    }
    
    public void addController(RobotController _controller) {
        controller=_controller;
    }

    private void setLocation() {
        this.location = new Location(1, 1, 1);
    }

    public String getID() {
        return ID;
    }

    public Location getLocation() {
        return location;
    }

    public void updateLocation(int directionTravelled) {

    }

    public int getCurrentJob() {
        return currentJob;
    }

    public void setCurrentJob(int currentJob) {
        this.currentJob = currentJob;
    }

    public void move() {

    }

    public Queue<Integer> getRoute() {
        return route;
    }

    public void setRoute(Queue<Integer> route) {
        this.route = route;
    }

    public Response getResponse() {
        while (response == Response.WAITING) {
            Delay.msDelay(30);
        }
        synchronized (lock) {
            return response;
        }
    }

    public void setResponse(Response response) {
        synchronized (lock) {
            this.response = response;
        }
    }

    public void cancelJob() {

    }
}
