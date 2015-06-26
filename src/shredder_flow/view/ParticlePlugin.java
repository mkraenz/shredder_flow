package shredder_flow.view;

import java.awt.Color;
import java.awt.geom.Point2D.Double;
import java.util.List;

import shredder_flow.logic.Particle;
import shredder_flow.logic.ParticleCreator;
import shredder_flow.logic.ParticleList;
import shredder_flow.logic.Vertex;
import de.jtem.java2d.SceneComponent;
import de.jtem.java2dx.Point2DDouble;
import de.jtem.java2dx.beans.SceneComponentBeanInfo;
import de.jtem.jrworkspace.plugin.Plugin;

public class ParticlePlugin extends Plugin {

	private ParticleList particles;
	private ParticleCreator creator;
	private SceneComponent particleSceneComponent;

	public void draw() {
		clear();
		List<Double> scenePointSet = particleSceneComponent.getPoints();
		for (Particle particle : particles) {
			scenePointSet.add(new Point2DDouble(particle.getX(), particle
					.getY()));
		}
		particleSceneComponent.setPointPaint(Color.red); // TODO: proper color,
															// size...
	}

	private void clear() {
		particleSceneComponent.removeAllChildren();
		particleSceneComponent.getPoints().clear();
	}

	public ParticlePlugin(ParticleCreator creator, ParticleList particles) {
		this.creator = creator;
		this.particles = particles;
		this.particleSceneComponent = new SceneComponent();
	}

	public void addParticles() {
		/*
		 * TODO find a way to properly add particles using the datafield
		 * creator. It might even be better to have an own class to handle the
		 * adding. The ParticlePlugin would then only draw the particles. While
		 * the other one handles only adding.
		 */
	}

	public void addRandomParticle() {

	}
}
