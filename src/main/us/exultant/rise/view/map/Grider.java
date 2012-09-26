package us.exultant.rise.view.map;

import us.exultant.ahs.core.*;
import us.exultant.ahs.util.*;
import us.exultant.ahs.iob.*;
import us.exultant.ahs.thread.*;
import us.exultant.beard.*;
import java.io.*;
import java.util.regex.*;

public class Grider {
	public Grider(int $width, int $height) {
		this.$width = $width;
		this.$height = $height;
		this.$cols = $width/48+2;
		this.$rows = $height/54+2;
	}
	
	private final int $width;
	private final int $height;
	private final int $rows;
	private final int $cols;
	private final String ID = "rise-map";
	
	public void jotAttachImages(Beard $beard) {
		try {
			String $png_hex = BeardUtil.formatDataForEmbed("image/png", IOForge.readResourceRaw("res/rise/map/hex.png"));
			$beard.eval("$('head').append($('<style>').html('."+ID+"-img-hex {background-image: url("+$png_hex+");}'));");
		} catch (IOException $e) {
			throw new MajorBug("malformed jar, missing resource", $e);
		}
	}
	
	public void jotElementGenerator(Beard $beard, String $parent) {
		String $png_trans;
		try {
			$png_trans =	BeardUtil.formatDataForEmbed("image/png", IOForge.readResourceRaw("res/rise/trans.png"));
		} catch (IOException $e) {
			throw new MajorBug("malformed jar, missing resource", $e);
		}
		
		// the outline / viewport
		$beard.eval("$('"+$parent+"').append("
				+"$('<div>')"
				+".attr('id',		'"+ID+"')"
				+".css('width',		'"+$width+"')"
				+".css('height',	'"+$height+"')"
				+".css('margin',	'auto')"
				+".css('border',	'1px solid #999')"
				+".css('overflow',	'hidden')"
		+");");
		
		// the sliding background area ("plate")
		$beard.eval("$('#"+ID+"').append("
				+"$('<div>')"
				+".attr('id',		'"+ID+"-plate')"
				+".css('position',	'relative')"
				+".css('left',		'-70')"
				+".css('top',		'-68')"
		+");");
		
		// the clear overlay that gathers mouse events ("hot")
		$beard.eval("$('#"+ID+"-plate').append("
				+"$('<map>')"
				+".attr('id',		'"+ID+"-hotmap')"
				+".attr('name',		'"+ID+"-hotmap')"
		+");");
		$beard.eval("$('#"+ID+"-plate').append("
				+"$('<img>')"
				+".attr('id',		'"+ID+"-hot')"
				+".attr('usemap',	'#"+ID+"-hotmap')"
				+".css('position',	'relative')"
				+".css('z-index',	'10')"
				+".attr('src',		'"+$png_trans+"')"
				+".attr('width',	'"+($width+200)+"')"
				+".attr('height',	'"+($height+200)+"')"
		+");");
		
		// hexagons!
		String $did;
		for (int $r = 0; $r < $rows; $r++) {
			for (int $c = 0; $c < $cols; $c+=2) {
				// alt-0 column
				$did = "-d-"+$c+"-"+$r;
				$beard.eval("$('#"+ID+"-plate').append("
						+"$('<div>')"
						+".attr('id',		'"+ID+$did+"')"
						+".attr('class',	'"+ID+"-img-hex')"
						+".css('position',	'absolute')"
						+".css('left',		'"+(48*$c)+"')"
						+".css('top',		'"+(54*$r)+"')"
						+".css('width',		'64')"
						+".css('height',	'64')"
				+");");
				$beard.eval("$('#"+ID+"-hotmap').append("
						+"$('<area>')"
						+".attr('id',		'"+ID+"-hot"+$did+"')"
						+".attr('class',	'"+ID+"-hotarea')"
						+".attr('shape',	'polygon')"
						+".attr('coords',	'"
							+( 16+48*$c)+","+(  4+54*$r)+", "
							+( 47+48*$c)+","+(  4+54*$r)+", "
							+( 63+48*$c)+","+( 30+54*$r)+", "
							+( 47+48*$c)+","+( 57+54*$r)+", "
							+( 16+48*$c)+","+( 57+54*$r)+", "
							+(    48*$c)+","+( 30+54*$r)+"')"
				+");");
				
				// alt-1 column
				$did = "-d-"+($c+1)+"-"+$r;
				$beard.eval("$('#"+ID+"-plate').append("
						+"$('<div>')"
						+".attr('id',		'"+ID+$did+"')"
						+".attr('class',	'"+ID+"-img-hex')"
						+".css('position',	'absolute')"
						+".css('left',		'"+(48+48*$c)+"')"
						+".css('top',		'"+(27+54*$r)+"')"
						+".css('width',		'64')"
						+".css('height',	'64')"
				+");");
				$beard.eval("$('#"+ID+"-hotmap').append("
						+"$('<area>')"
						+".attr('id',		'"+ID+"-hot"+$did+"')"
						+".attr('class',	'"+ID+"-hotarea')"
						+".attr('shape',	'polygon')"
						+".attr('coords',	'"
							+( 64+48*$c)+","+( 31+54*$r)+", "
							+( 95+48*$c)+","+( 31+54*$r)+", "
							+(111+48*$c)+","+( 57+54*$r)+", "
							+( 95+48*$c)+","+( 84+54*$r)+", "
							+( 64+48*$c)+","+( 84+54*$r)+", "
							+( 48+48*$c)+","+( 57+54*$r)+"')"
				+");");
			}
		}
	}
	
	private String mapHotareaIdToTileId(String $hotid) {
		Matcher $m = $p.matcher($hotid);
		return $m.find() ? $m.group(1) : null;
	}
	private final Pattern $p = Pattern.compile(ID+"-hot-(.*)");
	
	
	public void jotReactions(final Beard $beard) {
		SimpleReactor.bind(
				$beard.bus().bind("."+ID+"-hotarea", DomEvent.Type.MOUSEOVER),
				new Listener<DomEvent>() { public void hear(DomEvent $evt) {
					String $tileid = ID+"-"+mapHotareaIdToTileId($evt.srcElementId);
					$beard.eval("$('#"+$tileid+"')"
							+".css('border',	'#831 2px solid')"
							+".css('left',		'-=2')"
							+".css('top',		'-=2')"
					);
				}}
		);
		
		SimpleReactor.bind(
				$beard.bus().bind("."+ID+"-hotarea", DomEvent.Type.MOUSEOUT),
				new Listener<DomEvent>() { public void hear(DomEvent $evt) {
					String $tileid = ID+"-"+mapHotareaIdToTileId($evt.srcElementId);
					$beard.eval("$('#"+$tileid+"')"
							+".css('border',	'')"
							+".css('left',		'+=2')"
							+".css('top',		'+=2')"
					);
				}}
		);
	}
}
