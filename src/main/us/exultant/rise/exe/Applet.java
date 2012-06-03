package us.exultant.rise.exe;

import us.exultant.ahs.util.*;
import us.exultant.beard.*;
import us.exultant.rise.view.map.*;
import java.awt.*;
import javax.swing.*;

public class Applet extends JApplet {
	public void init() {
		add(new JLabel("j!"), BorderLayout.CENTER);
		
		$beard = new Beard(this);
		$beard.normalizePage();
		
		$beard.eval("$('#main').html('ohai!');");
		
		$beard.eval("$('#main').append("
				+"$('<div>')"
				+".attr('id',		'rise-map-placer')"
				+".css('margin',	'5em')"
		+");");
		
		Grider $grider = new Grider(1200,800);
		$grider.jotElementGenerator($beard, "#rise-map-placer");
		
		while (true) {
			for (int $i = 0; $i < 10; $i++)
				$grider.derp($beard);
			X.chill(1000);
			for (int $i = 0; $i < 10; $i++)
				$grider.derp2($beard);
			X.chill(1000);
			for (int $i = 0; $i < 10; $i++)
				$grider.derp3($beard);
			X.chill(1000);
			for (int $i = 0; $i < 10; $i++)
				$grider.derp4($beard);
			X.chill(1000);
		}
	}
	private Beard $beard;
}
