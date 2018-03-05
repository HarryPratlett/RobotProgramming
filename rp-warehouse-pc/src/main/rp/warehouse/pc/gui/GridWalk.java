package rp.warehouse.pc.gui;

import rp.robotics.mapping.GridMap;
import rp.robotics.navigation.GridPilot;
import rp.robotics.navigation.GridPose;
import rp.robotics.simulation.MovableRobot;
import rp.systems.StoppableRunnable;

public class GridWalk implements StoppableRunnable {
	
	private final GridPilot m_pilot;

	private boolean m_running = true;

	public GridWalk(MovableRobot _robot, GridMap _map, GridPose _start) {
		m_pilot = new GridPilot(_robot.getPilot(), _map, _start);
	}

	@Override
	public void run() {

		while (m_running) {

			int choice = nextTurn();

			// choice of direction. choice == 0 is straight ahead
			if (choice == 1) {
				m_pilot.rotatePositive();
			} else if (choice == 2) {
				m_pilot.rotateNegative();
			} else if (choice == 3) {
				m_pilot.rotatePositive();
				m_pilot.rotatePositive();
			}

			m_pilot.moveForward();
		}
	}

	private int nextTurn() {
		// get the next turn of the route
		return 0;
	}

	@Override
	public void stop() {
		m_running = false;
	}

}
