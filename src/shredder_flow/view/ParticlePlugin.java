package shredder_flow.view;

import java.awt.Color;
import java.awt.geom.Point2D.Double;
import java.util.List;

import shredder_flow.logic.Particle;
import shredder_flow.logic.ParticleCreator;
import shredder_flow.logic.ParticleList;
import de.jtem.java2d.SceneComponent;
import de.jtem.java2dx.Point2DDouble;
import de.jtem.java2dx.plugin.Java2DView;
import de.jtem.jrworkspace.plugin.Controller;
import de.jtem.jrworkspace.plugin.Plugin;

public class ParticlePlugin extends Plugin {

	private ParticleList particles;
	private ParticleCreator creator;
	private SceneComponent particleSceneComponent;
	private SceneComponent parentSceneComponent;

	public ParticlePlugin(ParticleCreator creator, ParticleList particles) {
		this.creator = creator;
		this.particles = particles;
		this.particleSceneComponent = new SceneComponent();
	}

	public void draw() {
		clear();
		drawParticles();
		parentSceneComponent.fireAppearanceChange();
//TODO: maybe better to use
//		particleSceneComponent.fireAppearanceChange();
	}

	private void drawParticles() {
		List<Double> scenePointSet = particleSceneComponent.getPoints();
		for (Particle particle : particles) {
			scenePointSet.add(new Point2DDouble(particle.getX(), particle
					.getY()));
		}
		particleSceneComponent.setPointPaint(Color.green); // TODO: proper color,
	}
	
	private void clear() {
		particleSceneComponent.removeAllChildren();
		particleSceneComponent.getPoints().clear();
	}

	public void addParticles() {
		/*
		 * TODO find a way to properly add particles using the datafield
		 * creator. It might even be better to have an own class to handle the
		 * adding. The ParticlePlugin would then only draw the particles. While
		 * the other one handles only adding.
		 */
	}
	
	@Override
	public void install(Controller c) throws Exception {
		super.install(c);
		this.parentSceneComponent = c.getPlugin(Java2DView.class).getViewer2D()
				.getRoot();
		parentSceneComponent.addChild(particleSceneComponent);
	}
}
