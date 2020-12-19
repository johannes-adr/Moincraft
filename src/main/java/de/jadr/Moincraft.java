package de.jadr;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import de.j2d.core.Element;
import de.j2d.core.Game;
import de.j2d.core.GameCam;
import de.jadr.world.Chunk;
import de.jadr.world.World;
import de.jadr.world.blocks.Block;

public class Moincraft extends Game {
	public static void main(String args[]) {
		new Moincraft("Moincraft", 1500, 800).start();
	}

	private World world;
	public GameCam gc;

	public Element player;

	public Moincraft(String title, int width, int height) {
		super(title, width, height);
		gc = new GameCam(this);
		player = new Element(0,0, 30, 50);

		gc.foc = player;
		world = World.generateNew(new Random().nextInt(Integer.MAX_VALUE), this);
		
		Chunk c = world.getChunkIntersectingX(player.getX());
		if(c == null) {
			c = Chunk.generateNew(world.getNoise(), Chunk.toChunkGrid(player.getX()));
			world.getChunks().add(c);
		}
		
		Block first = c.getBlocksArray().get(0);
		player.setX(first.getX()+30);
		player.setY(first.getY()-50);
	}

	@Override
	protected void onUpdate() {
		float speed = 10f;
		if(isKeyPressed('w'))player.setY(player.getY()-speed);
		if(isKeyPressed('s'))player.setY(player.getY()+speed);
		if(isKeyPressed('a'))player.setX(player.getX()-speed);
		if(isKeyPressed('d'))player.setX(player.getX()+speed);
		
		
		
		world.update(player.getX());	
	}

	@Override
	protected void onStart() {

	}

	@Override
	protected void render(Graphics2D g) {
		ArrayList<Element> toRender = new ArrayList<Element>();
		for(Chunk c : world.getChunks()) {
			toRender.addAll(c.getBlocksArray());
		}
		toRender.add(player);
		
		if(!gc.render(g, toRender))for(Element e : toRender)e.render(g,e.getXRound(), e.getYRound());
	}

}
