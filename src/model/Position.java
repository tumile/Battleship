package model;

import model.constant.Orientation;

public class Position {

	public int row;

	public int col;

	public Orientation orient;

	public Position(int row, int col, Orientation orient) {
		this.row = row;
		this.col = col;
		this.orient = orient;
	}
}
