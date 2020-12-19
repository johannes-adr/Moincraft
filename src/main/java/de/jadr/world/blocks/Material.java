package de.jadr.world.blocks;

import java.awt.image.BufferedImage;

import de.j2d.resources.Assetspicture;

public enum Material {
	GRASS(1, "Grass2.png"), STONE(2,"stone2.png");

	private int i;
	private BufferedImage image;
	
	public int getID() {
		return i;
	}
	
	public BufferedImage getImage() {
		return image;
	}

	Material(int i, String imagePath) {
		this.i = i;
		image = new Assetspicture(imagePath).setSize(Block.BLOCK_SIZE, Block.BLOCK_SIZE).getImage();
	}

}
