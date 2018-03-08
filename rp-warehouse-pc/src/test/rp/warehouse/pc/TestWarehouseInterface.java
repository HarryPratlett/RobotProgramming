package rp.warehouse.pc;

import java.util.ArrayList;

import rp.warehouse.pc.data.Item;
import rp.warehouse.pc.gui.WarehouseInterface;
import rp.warehouse.pc.route.RobotsControl;
import rp.warehouse.pc.data.Location;

public class TestWarehouseInterface {

	public static void main(String[] args) {
		ArrayList<Item> i = new ArrayList<>();
		Item i1 = new Item (2.0f, 2.0f);
		i1.setLocation(new Location(0, 2));
		i.add(i1 );
		WarehouseInterface gui = new WarehouseInterface(RobotsControl.getRobots(), i);
		gui.run();
	}
	
}
