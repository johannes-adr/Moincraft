package de.jadr.world;

import java.util.ArrayList;
import java.util.Random;

import de.j2d.extern.PerlinNoise;
import de.jadr.world.blocks.Block;
import de.jadr.world.blocks.Material;

public class Chunk {

	public static final int CHUNK_SIZE = 2, CHUNK_HEIGHT = 50;

	private int startX;
	private int endX;
	private Block[][] blocks;
	private ArrayList<Block> blocksArrayList;

	public static Chunk generateNew(PerlinNoise p, int x) {
		return new Chunk(x, p);
	}

	public int getMinX() {
		return startX;
	}

	public int getMaxX() {
		return endX;
	}

	private Chunk(int startX, PerlinNoise p) {

		this.startX = startX;
		this.endX = startX + CHUNK_SIZE*Block.BLOCK_SIZE;
		
		blocks = new Block[CHUNK_SIZE][CHUNK_HEIGHT];
		int[] surface = new int[CHUNK_SIZE];
		for (int x = 0; x < CHUNK_SIZE; x++) {

			// SurfaceWave
			double yNoise = p.noise((double) (startX/Block.BLOCK_SIZE + x) / 50d);
			int y = (int) (Math.round((yNoise * Block.BLOCK_SIZE)) + 35);
			if (y < 0)
				y = 0;
			if(y >= CHUNK_HEIGHT)y = CHUNK_HEIGHT-1;
			
			
			blocks[x][y] = createBlock(x, y, Material.GRASS);
			surface[x] = y;

		}

		for (int x = 0; x < CHUNK_SIZE; x++) {

			for (int y = surface[x] + 1; y < CHUNK_HEIGHT; y++) {
				blocks[x][y] = createBlock(x, y, Material.STONE);
			}

		}
		blocksArrayList = new ArrayList<>(blocks.length * blocks[0].length);
		for (Block[] bs : blocks) {
			for (Block b : bs) {
				if (b == null)
					continue;
				blocksArrayList.add(b);
			}
		}

	}
	
	private Block createBlock(int x, int y, Material m) {
		return new Block(startX + x*Block.BLOCK_SIZE, y, m);
	}

	public Block[][] getBlocks2D() {
		return blocks;
	}

	public ArrayList<Block> getBlocksArray() {
		return blocksArrayList;
	}

	public static int toChunkGrid(double raw) {
		int cTotal = CHUNK_SIZE * Block.BLOCK_SIZE;
		return (int) (Math.round(raw / cTotal) * cTotal);
	}

}
