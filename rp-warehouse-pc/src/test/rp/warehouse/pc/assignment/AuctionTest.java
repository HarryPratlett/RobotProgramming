package rp.warehouse.pc.assignment;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import org.junit.Assert;
import org.junit.Test;

import rp.warehouse.pc.data.Item;
import rp.warehouse.pc.data.Job;
import rp.warehouse.pc.data.Location;
import rp.warehouse.pc.data.Task;

public class AuctionTest {
	
	@Test
	public void assignmentsAreCorrect() {
		ArrayList<Task> items = new ArrayList<Task>();
		items.add(new Task(new Item(0f, 0f) {{setLocation(new Location(2, 2));}}, 0, ""));
		items.add(new Task(new Item(0f, 0f) {{setLocation(new Location(0, 3));}}, 0, ""));
		items.add(new Task(new Item(0f, 0f) {{setLocation(new Location(1, 0));}}, 0, ""));
		items.add(new Task(new Item(0f, 0f) {{setLocation(new Location(4, 4));}}, 0, ""));
		Job job1 = new Job("", items);
		
		ArrayList<Task> items2 = new ArrayList<Task>();
		items2.add(new Task(new Item(0f, 0f) {{setLocation(new Location(6, 1));}}, 0, ""));
		items2.add(new Task(new Item(0f, 0f) {{setLocation(new Location(1, 2));}}, 0, ""));
		items2.add(new Task(new Item(0f, 0f) {{setLocation(new Location(4, 3));}}, 0, ""));
		items2.add(new Task(new Item(0f, 0f) {{setLocation(new Location(3, 5));}}, 0, ""));				
		Job job2 = new Job("", items2);
		
		ArrayList<Job> jobs = new ArrayList<Job>();
		jobs.add(job1);
		jobs.add(job2);
		
		ArrayList<Location> robos = new ArrayList<Location>();
		robos.add(new Location(0, 0));
		robos.add(new Location(0, 1));
		robos.add(new Location(0, 2));
		
		ArrayList<Queue<Task>> correct = new ArrayList<Queue<Task>>();
		LinkedList<Task> robo1 = new LinkedList<Task>();
		LinkedList<Task> robo2 = new LinkedList<Task>();
		LinkedList<Task> robo3 = new LinkedList<Task>();
		// Robot 1
		robo1.add(items.get(2)); //		1, 0
		robo1.add(items2.get(0)); //	6, 1
		// Robot 2
		robo2.add(items.get(0)); //		2, 2
		robo2.add(items2.get(1)); // 	1, 2
		// Robot 3
		robo3.add(items.get(1)); // 	0, 3
		robo3.add(items.get(3)); // 	4, 4
		robo3.add(items2.get(2)); // 	4, 3
		robo3.add(items2.get(3)); // 	3, 5
		correct.add(robo1);
		correct.add(robo2);
		correct.add(robo3);
		
		Auctioner a = new Auctioner(jobs, robos);
		ArrayList<Queue<Task>> actual = a.auction(); 
		
		Assert.assertEquals(correct, actual);
	}
}
