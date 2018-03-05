package rp.warehouse.pc;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.plaf.basic.BasicScrollBarUI;

import lejos.robotics.RangeFinder;
import rp.robotics.MobileRobotWrapper;
import rp.robotics.control.RandomGridWalk;
import rp.robotics.mapping.*;
import rp.robotics.navigation.*;
import rp.robotics.simulation.*;
import rp.robotics.visualisation.*;

public class WarehouseInterface{
	
	private Robot robot;
	
	public WarehouseInterface(Robot r){
		this.robot = r;
	}
	
	public void run() {

		GridMap gridMap = getMap();
		
		MapBasedSimulation sim = new MapBasedSimulation(gridMap);
		GridPose robotStart = getPose(robot);
		
		MobileRobotWrapper<MovableRobot> wrapper = sim.addRobot(SimulatedRobots.makeConfiguration(false, true), gridMap.toPose(robotStart));
		
		//maybe won't need
		RangeFinder ranger = sim.getRanger(wrapper);
		
		//will be replaced by our controller
		RandomGridWalk controller = new RandomGridWalk(wrapper.getRobot(),
				gridMap, robotStart, ranger);
		new Thread(controller).start();
		
		WarehouseMapVisualisation mapVis = new WarehouseMapVisualisation(gridMap, gridMap, 150f);
		MapVisualisationComponent.populateVisualisation(mapVis, sim);

		displayVisualisation(mapVis);
	}

	private GridMap getMap() {
		GridMap map;
		
		// map = JobInput.getMap()
		map = MapUtils.createRealWarehouse();
		
		return map;
	}
	
	private GridPose getPose(Robot robot){
		Location location = robot.getLocation();
		GridPose pose = locationToPose(location);
		
		return pose;
	}

	private GridPose locationToPose(Location location) {
		int x = location.getX();
		int y = location.getY();
		Heading h = Heading.PLUS_Y;
		if(location.getDirection() == 1){
			h = Heading.PLUS_Y;
		}
		if(location.getDirection() == 2){
			h = Heading.PLUS_X;
		}
		if(location.getDirection() == 3){
			h = Heading.MINUS_Y;
		}
		if(location.getDirection() == 4){
			h = Heading.MINUS_X;
		}
		return new GridPose(x-1, y-1, h);
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
		
		//will be changed to create JLabels for each of the jobs
		JLabel job1 = new JLabel("Job 1");
		jobList.add(job1);
		for(JLabel job: jobList){
			jobPanel.add(job);
		}

		// Add visualization to frame
		viz.setPreferredSize(new Dimension (650, 420));
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
