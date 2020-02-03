package jwk.minecraft.garden.client.currency;

import java.text.DecimalFormat;

import jwk.minecraft.garden.client.font.Fonts;

public class CurrencyString {
	
	private static DecimalFormat defaultFormatter = new DecimalFormat("#,###");
	private static DecimalFormat specialFormatter = new DecimalFormat("#,##0.0");

	private long value = 0L;
	private String displayString = "0";
	
	public CurrencyString setValue(long value) {
		value = value < 0L? 0L : value;
		
		this.value = value;
		this.displayString = convert(value);
		
		return this;
	}
	
	public void reset() {
		value = 0L;
		displayString = "0";
	}
	
	private String convert(long value) {
		Suffix[] values = Suffix.values();
		
		double d = 0.D;
		Suffix sf = null;
		for (int i=0; i < values.length; i++) {
			
			if (value >= values[i].Value) {
				d = value / values[i].Value;
				sf = values[i];
				break;
			}
		}
		
		if (sf == null)
			return defaultFormatter.format(value);
		else {
			String str = specialFormatter.format(d) + sf.DisplayString;
			int idx = str.indexOf('.');
			
			if (idx == -1)
				return str;
			
			else if (str.charAt(idx + 1) == '0')
				str = str.replace(".0", "");
			
			return str;
		}
	}
	
	public long getValue() { return value; }
	
	public String getDisplayString() { return displayString; }
	
	public int getFontWidth() {
		return Fonts.fontDohyeon.getWidth(displayString);
	}
	
	public static String toDefaultFormat(long value) {
		return defaultFormatter.format(value);
	}
	
	private static enum Suffix {
		
		QR(10000000000000000L, "경"),
		TR(1000000000000L, "조"),
		M(100000000L, "억"),
		TT(10000L, "만");
		
		public final long Value;
		public final String DisplayString;
		
		private Suffix(long value, String displayString) {
			Value = value;
			DisplayString = displayString;
		}
		
	}
	
}
