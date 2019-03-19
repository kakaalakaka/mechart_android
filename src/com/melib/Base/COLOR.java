package com.melib.Base;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class COLOR {
	public static long CONTROL = -200000000001L;
	public static long CONTROLBORDER = -200000000002L;
	public static long CONTROLTEXT = -200000000003L;
	public static long DISABLEDCONTROL = -200000000004L;
	public static long DISABLEDCONTROLTEXT = -200000000005L;
	public static long HOVEREDCONTROL = -200000000006L;
	public static long PUSHEDCONTROL = -200000000007L;
	public static long EMPTY = -200000000000L;

	public static long ARGB(int r, int g, int b) {
		return r | g << 8 | b << 16;
	}

	public static long ARGB(int a, int r, int g, int b) {
		if (a == 255) {
			return ARGB(r, g, b);
		}
		if (a == 0) {
			return EMPTY;
		}

		int rgb = (int) ARGB(r, g, b);
		long argb = -(rgb * 1000L + a);
		return argb;
	}

	public static void ToARGB(CPaintMe paint, long color, RefObject<Integer> a, RefObject<Integer> r,
			RefObject<Integer> g, RefObject<Integer> b) {
		if (paint != null) {
			color = paint.GetColor(color);
		}
		a.argvalue = Integer.valueOf(255);
		if (color < 0L) {
			color = -color;
			if (color < 1L) {
				a.argvalue = Integer.valueOf(255);
			} else {
				a.argvalue = Integer.valueOf((int) (color - color / 1000L * 1000L));
			}
			color /= 1000L;
		}
		r.argvalue = Integer.valueOf((int) (color & 0xFF));
		g.argvalue = Integer.valueOf((int) (color >> 8 & 0xFF));
		b.argvalue = Integer.valueOf((int) (color >> 16 & 0xFF));
	}

	public static long RatioColor(CPaintMe paint, long originalColor, double ratio) {
		int a = 0;
		int r = 0;
		int g = 0;
		int b = 0;
		RefObject<Integer> tempRef_a = new RefObject(Integer.valueOf(a));
		RefObject<Integer> tempRef_r = new RefObject(Integer.valueOf(r));
		RefObject<Integer> tempRef_g = new RefObject(Integer.valueOf(g));
		RefObject<Integer> tempRef_b = new RefObject(Integer.valueOf(b));
		ToARGB(paint, originalColor, tempRef_a, tempRef_r, tempRef_g, tempRef_b);
		a = ((Integer) tempRef_a.argvalue).intValue();
		r = ((Integer) tempRef_a.argvalue).intValue();
		g = ((Integer) tempRef_a.argvalue).intValue();
		b = ((Integer) tempRef_a.argvalue).intValue();
		r = (int) (r * ratio);
		g = (int) (g * ratio);
		b = (int) (b * ratio);
		if (r > 255) {
			r = 255;
		}
		if (g > 255) {
			g = 255;
		}
		if (b > 255) {
			b = 255;
		}
		return ARGB(a, r, g, b);
	}

	public static long Reverse(CPaintMe paint, long originalColor) {
		int a = 0;
		int r = 0;
		int g = 0;
		int b = 0;
		RefObject<Integer> tempRef_a = new RefObject(Integer.valueOf(a));
		RefObject<Integer> tempRef_r = new RefObject(Integer.valueOf(r));
		RefObject<Integer> tempRef_g = new RefObject(Integer.valueOf(g));
		RefObject<Integer> tempRef_b = new RefObject(Integer.valueOf(b));
		ToARGB(paint, originalColor, tempRef_a, tempRef_r, tempRef_g, tempRef_b);
		a = ((Integer) tempRef_a.argvalue).intValue();
		r = ((Integer) tempRef_a.argvalue).intValue();
		g = ((Integer) tempRef_a.argvalue).intValue();
		b = ((Integer) tempRef_a.argvalue).intValue();
		return ARGB(a, 255 - r, 255 - g, 255 - b);
	}
}
