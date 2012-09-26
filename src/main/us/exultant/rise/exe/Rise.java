package us.exultant.rise.exe;

import us.exultant.beard.*;
import us.exultant.rise.view.map.*;

public class Rise extends Beardlet {
	public static void main(String... $args) { LaunchStandalone.main(Rise.class.getName()); }
	
	public void start(final Beard $beard) {
		// prove beard works and can touch js at all
		$beard.eval("$('#main').html('ohai!');");
		
		
		// draw a big neato map thingy
		$beard.eval("$('#main').append("
				+"$('<div>')"
				+".attr('id',		'rise-map-placer')"
				+".css('margin',	'5em')"
		+");");
		Grider $grider = new Grider(1200,800);
		$grider.jotAttachImages($beard);
		$grider.jotElementGenerator($beard, "#rise-map-placer");
		$grider.jotReactions($beard);
	}
	
	public void stop() {
		
	}
}
