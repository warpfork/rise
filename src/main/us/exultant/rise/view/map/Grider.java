package us.exultant.rise.view.map;

public class Grider {
	public Grider(int $width, int $height) {
		this.$width = $width;
		this.$height = $height;
	}

	private final int $width;
	private final int $height;
	
	public StringBuilder jotElementGenerator(StringBuilder $) {
		$.append("$('<div>')");
		$.append(".attr('id',		'rise-map')");
		$.append(".css('width',		'"+$width+"')");
		$.append(".css('height',	'"+$height+"')");
		$.append(".css('border',	'1px solid #999')");
		return $;
	}
}
