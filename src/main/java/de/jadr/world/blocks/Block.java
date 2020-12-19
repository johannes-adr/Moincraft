package de.jadr.world.blocks;

import java.awt.Color;
import java.awt.Graphics2D;

import de.j2d.core.Element;

public class Block extends Element{
	
	
	
	public static final int BLOCK_SIZE = 50;

	
	private Material type;
	
	public Block(int x, int y, Material m) {
		super(x,y*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
		this.type = m;
	}
	
	public Material getMaterial() {
		return type;
	}
	
	
	public void render(Graphics2D g, int x, int y) {
		g.drawImage(type.getImage(), x, y, null);
	}
	
	
//	public static int toBlockGrid(double raw) {
//		return (int) (Math.round(raw/BLOCK_SIZE)*BLOCK_SIZE);
//	}


}
