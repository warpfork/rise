package us.exultant.rise.exe;

import us.exultant.ahs.core.*;
import us.exultant.ahs.thread.*;
import us.exultant.beard.*;
import us.exultant.rise.view.map.*;

public class Applet implements Beardlet {
	public void start(final Beard $beard) {
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
	
	private WorkScheduler $scheduler;
	
	public void stop() {
		$scheduler.stop(true);
	}
}
