package rp.warehouse.pc.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import lejos.robotics.RangeFinder;
import rp.robotics.MobileRobotWrapper;
import rp.robotics.mapping.*;
import rp.robotics.navigation.*;
import rp.robotics.simulation.*;
import rp.robotics.visualisation.*;
import rp.warehouse.pc.communication.Protocol;
import rp.warehouse.pc.data.*;

public class WarehouseInterface {

	private ArrayList<Robot> robots = new ArrayList<>();
	private ArrayList<GridPose> robotStart = new ArrayList<>();
	private ArrayList<Item> items = new ArrayList<>();

	public WarehouseInterface(ArrayList<Robot> robotsList, ArrayList<Item> itemsList) {
		this.robots = robotsList;
		this.items = itemsList;
	}

	public void run() {

		GridMap gridMap = getMap();
		MapBasedSimulation sim = new MapBasedSimulation(gridMap);

		for (Robot robot : robots) {
			robotStart.add(getPose(robot));
		}

		for (Robot robot : robots) {
			GridPose start = getPose(robot);
			
			MobileRobotWrapper<MovableRobot> wrapper = sim.addRobot(SimulatedRobots.makeConfiguration(false, true),
					gridMap.toPose(start));
			
			RangeFinder ranger = sim.getRanger(wrapper);

			GridWalk controller = new GridWalk(wrapper.getRobot(), gridMap, start, ranger, robot.getRoute());

			new Thread(controller).start();
		}

		ArrayList<Location> locations = new ArrayList<>();
		for(Item i: items){
			Location l = i.getLocation();
			locations.add(l);
		}
		WarehouseMapVisualisation mapVis = new WarehouseMapVisualisation(gridMap, gridMap, 150f, locations);
		MapVisualisationComponent.populateVisualisation(mapVis, sim);

		displayVisualisation(mapVis);
	}

	private GridMap getMap() {
		GridMap map;

		map = Warehouse.build();

		return map;
	}

	private GridPose getPose(rp.warehouse.pc.data.Robot robot) {
		RobotLocation location = robot.getLocation();

		GridPose pose = locationToPose(location);

		return pose;
	}

	private GridPose locationToPose(RobotLocation location) {
		int x = location.getX();
		int y = location.getY();
		int d = location.getDirection();
		Heading h;
		switch (d) {
		case Protocol.NORTH:
			h = Heading.PLUS_Y;
			break;
		case Protocol.EAST:
			h = Heading.PLUS_X;
			break;
		case Protocol.SOUTH:
			h = Heading.MINUS_Y;
			break;
		case Protocol.WEST:
			h = Heading.MINUS_X;
		default:
			h = Heading.PLUS_Y;
		}
		return new GridPose(x - 1, y - 1, h);
	}

	public static JFrame displayVisualisation(WarehouseMapVisualisation viz) {
		// Create a frame to contain the viewer
		JFrame frame = new JFrame("Warehouse GUI");

		JPanel panel = new JPanel();

		JPanel jobPanel = new JPanel();
		JLabel jobHeader = new JLabel("Job List");
		jobPanel.setBackground(Color.WHITE);
		jobPanel.setPreferredSize(new Dimension(100, 300));
		jobPanel.add(jobHeader);

		ArrayList<JLabel> jobList = new ArrayList<>();

		// will be changed to create JLabels for each of the jobs
		JLabel job1 = new JLabel("Job 1");
		jobList.add(job1);
		for (JLabel job : jobList) {
			jobPanel.add(job);
		}

		// Add visualization to frame
		viz.setPreferredSize(new Dimension(650, 420));
		panel.add(viz);
		panel.add(jobPanel);
		frame.add(panel);
		frame.addWindowListener(new KillMeNow());

		frame.pack();
		frame.setSize(viz.getMinimumSize());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		return frame;
	}
}
