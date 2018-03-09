package rp.warehouse.pc.gui;

import java.util.Queue;

import lejos.robotics.RangeFinder;
import rp.robotics.mapping.GridMap;
import rp.robotics.navigation.GridPilot;
import rp.robotics.navigation.GridPose;
import rp.robotics.simulation.MovableRobot;
import rp.systems.StoppableRunnable;

public class GridWalk implements StoppableRunnable {
	
	private final GridPilot m_pilot;
	private final GridMap m_map;

	private final RangeFinder m_ranger;
	private final MovableRobot m_robot;
	private boolean m_running = true;
	
	private Queue<Integer> route;

	public GridWalk(MovableRobot _robot, GridMap _map, GridPose _start,
			RangeFinder _ranger, Queue<Integer> _route) {
		m_pilot = new GridPilot(_robot.getPilot(), _map, _start);
		m_map = _map;
		m_ranger = _ranger;
		m_robot = _robot;
		route = _route;
	}


	private boolean enoughSpace() {
		return m_ranger.getRange() > m_map.getCellSize()
				+ m_robot.getRobotLength() / 2f;
	}

	private boolean moveAheadClear() {
		GridPose current = m_pilot.getGridPose();
		GridPose moved = current.clone();
		moved.moveUpdate();
		return m_map.isValidTransition(current.getPosition(),
				moved.getPosition())
				&& enoughSpace();
	}
	
	@Override
	public void run() {

		while (m_running) {

			int turn = nextTurn();

			// choice of direction. choice == 0 is straight ahead
			if (turn == 1) {
				m_pilot.rotatePositive();
			} else if (turn == 2) {
				m_pilot.rotateNegative();
			} else if (turn == 3) {
				m_pilot.rotatePositive();
				m_pilot.rotatePositive();
			}
			
			if (moveAheadClear()) {
				m_pilot.moveForward();
			}
		}
	}

	// get the next turn of the route
	private int nextTurn() {
		int turn = route.peek();
		return turn;
	}

	@Override
	public void stop() {
		m_running = false;
	}

}
