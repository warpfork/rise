package us.exultant.rise.view.map;

import us.exultant.ahs.util.*;
import us.exultant.ahs.iob.*;
import us.exultant.beard.*;
import java.io.*;

public class Grider {
	public Grider(int $width, int $height) {
		this.$width = $width;
		this.$height = $height;
	}

	private final int $width;
	private final int $height;
	private final String ID = "rise-map";
	
	public void jotElementGenerator(Beard $beard, String $parent) {
		String $png_trans, $png_hex;
		try {
			$png_trans =	BeardUtil.formatDataForEmbed("image/png", IOForge.readResourceRaw("res/rise/trans.png"));
			$png_hex =	BeardUtil.formatDataForEmbed("image/png", IOForge.readResourceRaw("res/rise/map/hex.png"));
		} catch (IOException $e) {
			throw new MajorBug("malformed jar, missing resource", $e);
		}
		
		// the outline / viewport
		$beard.eval("$('"+$parent+"').append("
				+"$('<div>')"
				+".attr('id',		'"+ID+"')"
				+".css('width',		'"+$width+"')"
				+".css('height',	'"+$height+"')"
				+".css('border',	'1px solid #999')"
				+".css('overflow',	'hidden')"
		+");");
		
		// the sliding background area ("plate")
		$beard.eval("$('#"+ID+"').append("
				+"$('<div>')"
				+".attr('id',		'"+ID+"-plate')"
				+".css('position',	'relative')"
				+".css('top',		'-100')"
				+".css('left',		'-100')"
		+");");
		
		// the clear overlay that gathers mouse events ("hot")
		$beard.eval("$('#"+ID+"-plate').append("
				+"$('<img>')"
				+".attr('id',		'"+ID+"-hot')"
				+".attr('src',		'"+$png_trans+"')"
				+".attr('width',	'"+$width+"')"
				+".attr('height',	'"+$height+"')"
		+");");	//TODO: a crapload of work with map and area tags
		
		// hexagons!
		for (int $r = 0; $r < 9; $r++) {
			for (int $c = 0; $c < 12; $c+=2) {
				$beard.eval("$('#"+ID+"-plate').append("
						+"$('<img>')"
						+".attr('id',		'"+ID+"-d-"+$c+"-"+$r+"')"
						+".attr('src',		'"+$png_hex+"')"
						+".css('position',	'absolute')"
						+".css('top',		'"+(54*$r)+"')"
						+".css('left',		'"+(48*$c)+"')"
				+");");
				
				$beard.eval("$('#"+ID+"-plate').append("
						+"$('<img>')"
						+".attr('id',		'"+ID+"-d-"+$c+1+"-"+$r+"')"
						+".attr('src',		'"+$png_hex+"')"
						+".css('position',	'absolute')"
						+".css('top',		'"+(27+54*$r)+"')"
						+".css('left',		'"+(48+48*$c)+"')"
				+");");
			}
		}
	}
}
