package us.exultant.rise.exe;

import us.exultant.ahs.core.*;
import us.exultant.ahs.util.*;
import us.exultant.ahs.thread.*;
import us.exultant.beard.*;
import us.exultant.rise.view.map.*;
import java.awt.*;
import javax.swing.*;

public class Applet extends JApplet {
	public void init() {
		// flash some signs that we're alive at all
		add(new JLabel("j!"), BorderLayout.CENTER);
		
		// set up beard
		$beard = new Beard(this);
		$beard.normalizePage();
		
		// prove beard works and can touch js at all
		$beard.eval("$('#main').html('ohai!');");
		$beard.eval("$('#main').attr('tabindex', 1);");
		
		
		
		// set up a bunch of demo event listeners that just log really loudly.
		for (DomEvent.Type $type : DomEvent.Type.values())
			SimpleReactor.bind(
					$beard.bus().bind("#main", $type),
					new Listener<DomEvent>() { public void hear(DomEvent $evt) {
						$beard.console_log($evt.toString());
					}}
			);
		$scheduler = new WorkSchedulerFlexiblePriority(1).start();
		$scheduler.schedule($beard.bus().getWorkTarget(), ScheduleParams.makeFixedRate(1));
		
		
		
		// draw a big neato map thingy
		$beard.eval("$('#main').append("
				+"$('<div>')"
				+".attr('id',		'rise-map-placer')"
				+".css('margin',	'5em')"
		+");");
		Grider $grider = new Grider(1200,800);
		$grider.jotAttachImages($beard);
		$grider.jotElementGenerator($beard, "#rise-map-placer");
		
	}
	
	private Beard $beard;
	private WorkScheduler $scheduler;
	
	public void start() {
		/* Fun fact about applet threading models:
		 * if you don't return from this method, you'll never get a stop or a destroy event.
		 * Which means you'll get a violent ThreadDeath thrown instead.
		 * Which will almost certainly cause something very bad to happen...
		 * monitors in inconsistent state, the works.
		 * Basically your applet will crash and the entire vm will probably die.
		 * 
		 * So return from the start method.  And all the lifecycle methods.
		 * Start a thread somewhere.  Do your work in that.
		 * Make sure it's stopped by the destroy event.
		 * 
		 * And do NOT use WorkManager.getDefaultScheduler() in an Applet.  (Or any other static thread pool.)
		 * If you stop it, you're messing things up; if you don't stop it, you're crashing.
		 * It's a no-win situation.
		 * Make your own WorkScheduler per Applet.
		 */
	}
	
	public void stop() {}
	
	public void destroy() {
		$scheduler.stop(true);
	}
}
