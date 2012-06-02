package us.exultant.rise.exe;

import java.awt.*;
import us.exultant.beard.*;
import us.exultant.rise.view.map.*;
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
	}
	private Beard $beard;
}
