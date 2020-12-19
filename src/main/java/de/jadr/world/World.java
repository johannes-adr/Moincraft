package de.jadr.world;

import java.awt.Graphics2D;
import java.awt.Point;
import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;

import de.j2d.extern.PerlinNoise;
import de.jadr.Moincraft;
import de.jadr.world.blocks.Block;

public class World {

	private static final int CHUNKS_LOADED = 5;

	private PerlinNoise noise;

	private ArrayList<Chunk> chunks = new ArrayList<>();
	private Moincraft m;

	private World(int seed, Moincraft m) {

		this.m = m;
		noise = new PerlinNoise(seed);

	}

	public static World generateNew(int seed, Moincraft m) {
		return new World(seed, m);
	}

	public ArrayList<Chunk> getChunks() {
		return chunks;
	}

	public Chunk genAt(float f) {
			Chunk c = null;	
			for(Chunk cc : getChunks()) {
				if(f < cc.getMinX()) {
					c = Chunk.generateNew(getNoise(), Chunk.toChunkGrid(f)-Chunk.CHUNK_SIZE*Block.BLOCK_SIZE);
				}else if(f > cc.getMaxX()) {
					c = Chunk.generateNew(getNoise(), Chunk.toChunkGrid(f));
				}
			}
			getChunks().add(c);
			return c;
	}

	public void update(float f) {
		Chunk c = getChunkIntersectingX(f);

		if (c == null) {
			c = genAt(f);
		}

		Chunk rightest = null;
		Chunk leftest = null;

		for (Chunk pRight : chunks) {
			if (rightest == null) {
				rightest = pRight;
				continue;
			}
			if (pRight.getMaxX() > rightest.getMaxX())
				rightest = pRight;
		}

		for (Chunk pLeft : chunks) {
			if (leftest == null) {
				leftest = pLeft;
				continue;
			}
			if (pLeft.getMinX() < leftest.getMinX())
				leftest = pLeft;
		}

		int leftDiff = diff(leftest.getMinX(), c.getMinX());

		int rightDiff = diff(rightest.getMinX(), c.getMinX());
		System.out.println("leftDiff: " + leftDiff + " | rightDiff: " + rightDiff);
		int chunkDist = Chunk.CHUNK_SIZE * Block.BLOCK_SIZE * CHUNKS_LOADED;

		if (leftDiff > chunkDist) {
			chunks.remove(leftest);
		}

		if (rightDiff > chunkDist) {
			chunks.remove(rightDiff);
		}
	}

	private int diff(int i1, int i2) {
		int bigger = 0;
		int smaller = 0;
		if (i1 > i2) {
			bigger = i1;
			smaller = i2;
		} else {
			bigger = i2;
			smaller = i1;
		}
		return bigger - smaller;
	}

	public Chunk getChunkIntersectingX(double x) {
		for (Chunk c : getChunks()) {
			if (x >= c.getMinX() && x <= c.getMaxX()) {
				return c;
			}
		}
		return null;
	}

	public PerlinNoise getNoise() {
		return noise;
	}
}
