package animations.beams;

import objects.entity.Entity;
import org.newdawn.slick.Color;

public class AnimBeamBurst extends AnimBeam {

	public AnimBeamBurst(Entity origin, Entity target, int width, int duration) {
		super(origin, target, width, duration);
	}
	
	public AnimBeamBurst(Entity origin, Entity target, int width, int duration, Color color) {
		super(origin, target, width, duration, color);
	}
	
	int getWidth()
	{
		return (int) (width * (1.0 - percentComplete()));
	}

}
