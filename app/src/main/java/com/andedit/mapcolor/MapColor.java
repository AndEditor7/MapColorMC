package com.andedit.mapcolor;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class MapColor {

	private static int a;
	private static final MapColor[] COLORS = new MapColor[61];

	static {
		add("PALE_GREEN", 8368696);
		add("PALE_YELLOW", 16247203);
		add("WHITE_GRAY", 0xC7C7C7);
		add("BRIGHT_RED", 0xFF0000);
		add("PALE_PURPLE", 0xA0A0FF);
		add("IRON_GRAY", 0xA7A7A7);
		add("DARK_GREEN", 31744);
		add("WHITE", 0xFFFFFF);
		add("LIGHT_BLUE_GRAY", 10791096);
		add("DIRT_BROWN", 9923917);
		add("STONE_GRAY", 0x707070);
		add("WATER_BLUE", 0x4040FF);
		add("OAK_TAN", 9402184);
		add("OFF_WHITE", 0xFFFCF5);
		add("ORANGE", 14188339);
		add("MAGENTA", 11685080);
		add("LIGHT_BLUE", 6724056);
		add("YELLOW", 0xE5E533);
		add("LIME", 8375321);
		add("PINK", 15892389);
		add("GRAY", 0x4C4C4C);
		add("LIGHT_GRAY", 0x999999);
		add("CYAN", 5013401);
		add("PURPLE", 8339378);
		add("BLUE", 3361970);
		add("BROWN", 6704179);
		add("GREEN", 6717235);
		add("RED", 0x993333);
		add("BLACK", 0x191919);
		add("GOLD", 16445005);
		add("DIAMOND_BLUE", 6085589);
		add("LAPIS_BLUE", 4882687);
		add("EMERALD_GREEN", 55610);
		add("SPRUCE_BROWN", 8476209);
		add("DARK_RED", 0x700200);
		add("TERRACOTTA_WHITE", 13742497);
		add("TERRACOTTA_ORANGE", 10441252);
		add("TERRACOTTA_MAGENTA", 9787244);
		add("TERRACOTTA_LIGHT_BLUE", 7367818);
		add("TERRACOTTA_YELLOW", 12223780);
		add("TERRACOTTA_LIME", 6780213);
		add("TERRACOTTA_PINK", 10505550);
		add("TERRACOTTA_GRAY", 0x392923);
		add("TERRACOTTA_LIGHT_GRAY", 8874850);
		add("TERRACOTTA_CYAN", 0x575C5C);
		add("TERRACOTTA_PURPLE", 8014168);
		add("TERRACOTTA_BLUE", 4996700);
		add("TERRACOTTA_BROWN", 4993571);
		add("TERRACOTTA_GREEN", 5001770);
		add("TERRACOTTA_RED", 9321518);
		add("TERRACOTTA_BLACK", 2430480);
		add("DULL_RED", 12398641);
		add("DULL_PINK", 9715553);
		add("DARK_CRIMSON", 6035741);
		add("TEAL", 1474182);
		add("DARK_AQUA", 3837580);
		add("DARK_DULL_PINK", 5647422);
		add("BRIGHT_TEAL", 1356933);
		add("DEEPSLATE_GRAY", 0x646464);
		add("RAW_IRON_PINK", 14200723);
		add("LICHEN_GREEN", 8365974);
	}

	private static void add(String name, int color) {
		COLORS[a] = new MapColor(name, color, a++);
	}

	public final Color color;
	public final String name;
	public final int id;

	public MapColor(String name, int color, int id) {
		this.color = new Color(color);
		this.name = name;
		this.id = id;
	}

	// Color metric: https://www.compuphase.com/cmetric.htm
	public int dist(int r, int g, int b) {
		int rmean = (r + color.getRed()) >>> 1;
		r -= color.getRed();
		g -= color.getGreen();
		b -= color.getBlue();
		return (((512+rmean)*r*r)>>8) + 4*g*g + (((767-rmean)*b*b)>>8);
	}

	public String toHex() {
		return '#' + Integer.toHexString(color.getRGB() & 0xFFFFFFF);
	}

	public Color getInvertColor() {
		int val = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
		return val > 130 ? Color.BLACK : Color.WHITE;
	}

	public static MapColor getMapColor(BufferedImage img) {
		if (img == null) return null;

		// Averaging color from image
		long r=0, g=0, b=0, s=0;
		for (int x = 0; x < img.getWidth(); x++)
		for (int y = 0; y < img.getHeight(); y++) {
			int pix = img.getRGB(x, y);
			if ((pix >>> 24) > 0) {
				r += pow((pix >>> 16) & 0xFF);
				g += pow((pix >>> 8) & 0xFF);
				b += pow((pix >>> 0) & 0xFF);
				s++;
			}
		}

		r = (int)Math.sqrt(r/s);
		g = (int)Math.sqrt(g/s);
		b = (int)Math.sqrt(b/s);

		MapColor result = null;
		int num = Integer.MAX_VALUE;
		for (MapColor index : COLORS) {
			int dst = index.dist((int)r, (int)g, (int)b);
			if (dst < num) {
				num = dst;
				result = index;
			}
		}

		return result;
	}

	private static long pow(int num) {
		return num * num;
	}
}
