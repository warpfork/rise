package us.exultant.rise.view.map;

import us.exultant.beard.*;

public class Grider {
	public Grider(int $width, int $height) {
		this.$width = $width;
		this.$height = $height;
	}

	private final int $width;
	private final int $height;
	
	public void jotElementGenerator(Beard $beard, String $parent) {
		$beard.eval("$('"+$parent+"').append("
			+"$('<div>')"
			+".attr('id',		'rise-map')"
			+".css('width',		'"+$width+"')"
			+".css('height',	'"+$height+"')"
			+".css('border',	'1px solid #999')"
		+");");
	}
}
