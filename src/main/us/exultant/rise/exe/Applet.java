package us.exultant.rise.exe;

import java.awt.*;
import us.exultant.beard.*;
import javax.swing.*;

public class Applet extends JApplet {
	public void init() {
		add(new JLabel("j!"), BorderLayout.CENTER);
		
		Beard $beard = new Beard(this);
		$beard.eval("document.getElementById('main').innerHTML='ohai!';");
	}
}
