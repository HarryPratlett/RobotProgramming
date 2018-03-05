package rp.warehouse.pc;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import lejos.geom.Point;
import rp.robotics.mapping.IGridMap;
import rp.robotics.mapping.LineMap;
import rp.robotics.visualisation.GridMapVisualisation;

public class WarehouseMapVisualisation extends GridMapVisualisation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// in world units
	private static final float ITEM_RADIUS = 0.04f;

	public WarehouseMapVisualisation(IGridMap _gridMap, LineMap _lineMap, float _scaleFactor) {
		super(_gridMap, _lineMap, _scaleFactor);
	}
	
	public boolean hasItem(int x, int y) {
		// will be changed to check if there is any item at coordinates (x,y)
		if (x == 0 && y == 2)
			return true;
		return false;
	}
	
	public IGridMap getMap(){
		return m_gridMap;
	}

	protected void renderItem(Point _point, Graphics2D _g2) {
		Ellipse2D ell =
				// first 2 coords are upper left corner of framing rectangle
				new Ellipse2D.Double(scale(_point.getX() - ITEM_RADIUS) + X_MARGIN,
						scale(flipY(_point.getY() + ITEM_RADIUS)) + Y_MARGIN, scale(ITEM_RADIUS * 2),
						scale(ITEM_RADIUS * 2));
		_g2.draw(ell);
		_g2.fill(ell);
	}

	private void connectToNeighbour(Graphics2D _g2, int _x, int _y, int _dx, int _dy) {

		if (m_gridMap.isValidTransition(_x, _y, _x + _dx, _y + _dy)) {
			Point p1 = m_gridMap.getCoordinatesOfGridPosition(_x, _y);
			Point p2 = m_gridMap.getCoordinatesOfGridPosition(_x + _dx, _y + _dy);
			renderLine(p1, p2, _g2);
		}

	}

	@Override
	protected void renderMap(Graphics2D _g2) {
		// render lines first
		super.renderMap(_g2);

		_g2.setStroke(new BasicStroke(1));
		_g2.setPaint(Color.BLUE);

		// add grid
		for (int x = 0; x < m_gridMap.getXSize(); x++) {
			for (int y = 0; y < m_gridMap.getYSize(); y++) {
				if (!m_gridMap.isObstructed(x, y)) {
					Point gridPoint = m_gridMap.getCoordinatesOfGridPosition(x, y);
					renderPoint(gridPoint, _g2, 0.02);
				}
				if (hasItem(x, y)) {
					Point gridPoint = m_gridMap.getCoordinatesOfGridPosition(x, y);
					_g2.setPaint(Color.YELLOW);
					renderItem(gridPoint, _g2);
					_g2.setPaint(Color.BLUE);
				}
			}
		}

		// and visualise valid connections
		for (int x = 0; x < m_gridMap.getXSize(); x++) {
			for (int y = 0; y < m_gridMap.getYSize(); y++) {

				if (m_gridMap.isValidGridPosition(x, y)) {
					connectToNeighbour(_g2, x, y, 1, 0);
					connectToNeighbour(_g2, x, y, 0, 1);
					connectToNeighbour(_g2, x, y, -1, 0);
					connectToNeighbour(_g2, x, y, 0, -1);
				}
			}
		}

	}

}
