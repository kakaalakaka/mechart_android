package com.melib.Base;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class CStrMe {
	public static String ConvertAnchorToStr(ANCHOR anchor) {
		ArrayList<String> strs = new ArrayList();
		if (anchor.left) {
			strs.add("Left");
		}
		if (anchor.top) {
			strs.add("Top");
		}
		if (anchor.right) {
			strs.add("Right");
		}
		if (anchor.bottom) {
			strs.add("Bottom");
		}
		String anchorStr = "";
		int size = strs.size();
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				anchorStr = anchorStr + (String) strs.get(i);
				if (i != size - 1) {
					anchorStr = anchorStr + ",";
				}

			}

		} else {
			anchorStr = "None";
		}
		return anchorStr;
	}

	public static String ConvertBoolToStr(boolean value) {
		return value ? "True" : "False";
	}

	public static String ConvertColorToStr(long color) {
		if (color == COLOR.EMPTY) {
			return "Empty";
		}
		if (color == COLOR.CONTROL) {
			return "Control";
		}
		if (color == COLOR.CONTROLBORDER) {
			return "ControlBorder";
		}
		if (color == COLOR.CONTROLTEXT) {
			return "ControlText";
		}
		if (color == COLOR.DISABLEDCONTROL) {
			return "DisabledControl";
		}
		if (color == COLOR.DISABLEDCONTROLTEXT) {
			return "DisabledControlText";
		}
		int a = 0;
		int r = 0;
		int g = 0;
		int b = 0;
		RefObject<Integer> tempRef_a = new RefObject(Integer.valueOf(a));
		RefObject<Integer> tempRef_r = new RefObject(Integer.valueOf(r));
		RefObject<Integer> tempRef_g = new RefObject(Integer.valueOf(g));
		RefObject<Integer> tempRef_b = new RefObject(Integer.valueOf(b));
		COLOR.ToARGB(null, color, tempRef_a, tempRef_r, tempRef_g, tempRef_b);
		a = ((Integer) tempRef_a.argvalue).intValue();
		r = ((Integer) tempRef_r.argvalue).intValue();
		g = ((Integer) tempRef_g.argvalue).intValue();
		b = ((Integer) tempRef_b.argvalue).intValue();
		String str = "";
		if (a == 255) {
			str = String.format("%d,%d,%d",
					new Object[] { Integer.valueOf(r), Integer.valueOf(g), Integer.valueOf(b) });
		} else {
			str = String.format("%1$s,%2$s,%3$s,%4$s",
					new Object[] { Integer.valueOf(a), Integer.valueOf(r), Integer.valueOf(g), Integer.valueOf(b) });
		}
		return str;
	}

	public static String ConvertContentAlignmentToStr(ContentAlignmentA contentAlignment) {
		String str = "";
		if (contentAlignment == ContentAlignmentA.BottomCenter) {
			str = "BottomCenter";
		} else if (contentAlignment == ContentAlignmentA.BottomLeft) {
			str = "BottomLeft";
		} else if (contentAlignment == ContentAlignmentA.BottomRight) {
			str = "BottomRight";
		} else if (contentAlignment == ContentAlignmentA.MiddleCenter) {
			str = "MiddleCenter";
		} else if (contentAlignment == ContentAlignmentA.MiddleLeft) {
			str = "MiddleLeft";
		} else if (contentAlignment == ContentAlignmentA.MiddleRight) {
			str = "MiddleRight";
		} else if (contentAlignment == ContentAlignmentA.TopCenter) {
			str = "TopCenter";
		} else if (contentAlignment == ContentAlignmentA.TopLeft) {
			str = "TopLeft";
		} else if (contentAlignment == ContentAlignmentA.TopRight) {
			str = "TopRight";
		}
		return str;
	}

	public static double ConvertDateToNum(Calendar date) {
		int year = date.get(1);
		int month = date.get(2) + 1;
		int day = date.get(5);
		int hour = date.get(11);
		int minute = date.get(12);
		int second = date.get(13);
		return CMathLibMe.M129(year, month, day, hour, minute, second, 0);
	}

	public static String ConvertDockToStr(DockStyleA dock) {
		String str = "";
		if (dock == DockStyleA.Bottom) {
			str = "Bottom";
		} else if (dock == DockStyleA.Fill) {
			str = "Fill";
		} else if (dock == DockStyleA.Left) {
			str = "Left";
		} else if (dock == DockStyleA.None) {
			str = "None";
		} else if (dock == DockStyleA.Right) {
			str = "Right";
		} else if (dock == DockStyleA.Top) {
			str = "Top";
		}
		return str;
	}

	public static String ConvertDoubleToStr(double value) {
		try {
			return new Double(value).toString();
		} catch (Exception ex) {
		}

		return "";
	}

	public static String ConvertFloatToStr(float value) {
		try {
			return new Float(value).toString();
		} catch (Exception ex) {
		}

		return "";
	}

	public static String ConvertFontToStr(FONT font) {
		ArrayList<String> strs = new ArrayList();
		strs.add(font.m_fontFamily);
		strs.add(new Float(font.m_fontSize).toString());
		if (font.m_bold) {
			strs.add("Bold");
		}
		if (font.m_underline) {
			strs.add("UnderLine");
		}
		if (font.m_italic) {
			strs.add("Italic");
		}
		if (font.m_strikeout) {
			strs.add("Strikeout");
		}
		String fontStr = "";
		int size = strs.size();
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				fontStr = fontStr + (String) strs.get(i);
				if (i != size - 1) {
					fontStr = fontStr + ",";
				}
			}
		}
		return fontStr;
	}

	public static String ConvertHorizontalAlignToStr(HorizontalAlignA horizontalAlign) {
		String str = "";
		if (horizontalAlign == HorizontalAlignA.Center) {
			str = "Center";
		} else if (horizontalAlign == HorizontalAlignA.Right) {
			str = "Right";
		} else if (horizontalAlign == HorizontalAlignA.Inherit) {
			str = "Inherit";
		} else if (horizontalAlign == HorizontalAlignA.Left) {
			str = "Left";
		}
		return str;
	}

	public static String ConvertIntToStr(int value) {
		try {
			return new Integer(value).toString();
		} catch (Exception ex) {
		}

		return "";
	}

	public static String ConvertLayoutStyleToStr(LayoutStyleA layoutStyle) {
		String str = "";
		if (layoutStyle == LayoutStyleA.BottomToTop) {
			str = "BottomToTop";
		} else if (layoutStyle == LayoutStyleA.LeftToRight) {
			str = "LeftToRight";
		} else if (layoutStyle == LayoutStyleA.None) {
			str = "None";
		} else if (layoutStyle == LayoutStyleA.RightToLeft) {
			str = "RightToLeft";
		} else if (layoutStyle == LayoutStyleA.TopToBottom) {
			str = "TopToBottom";
		}
		return str;
	}

	public static String ConvertLongToStr(long value) {
		try {
			return new Long(value).toString();
		} catch (Exception ex) {
		}

		return "";
	}

	public static Calendar ConvertNumToDate(double num) {
		int year = 0;
		int month = 0;
		int day = 0;
		int hour = 0;
		int minute = 0;
		int second = 0;
		int ms = 0;
		RefObject<Integer> tempRef_year = new RefObject(Integer.valueOf(year));
		RefObject<Integer> tempRef_month = new RefObject(Integer.valueOf(month));
		RefObject<Integer> tempRef_day = new RefObject(Integer.valueOf(day));
		RefObject<Integer> tempRef_hour = new RefObject(Integer.valueOf(hour));
		RefObject<Integer> tempRef_minute = new RefObject(Integer.valueOf(minute));
		RefObject<Integer> tempRef_second = new RefObject(Integer.valueOf(second));
		RefObject<Integer> tempRef_ms = new RefObject(Integer.valueOf(ms));
		CMathLibMe.M130(num, tempRef_year, tempRef_month, tempRef_day, tempRef_hour, tempRef_minute, tempRef_second,
				tempRef_ms);
		year = ((Integer) tempRef_year.argvalue).intValue();
		month = ((Integer) tempRef_month.argvalue).intValue();
		day = ((Integer) tempRef_day.argvalue).intValue();
		hour = ((Integer) tempRef_hour.argvalue).intValue();
		minute = ((Integer) tempRef_minute.argvalue).intValue();
		second = ((Integer) tempRef_second.argvalue).intValue();
		ms = ((Integer) tempRef_ms.argvalue).intValue();
		Calendar calendar = new GregorianCalendar(year, month - 1, day, hour, minute, second);
		return calendar;
	}

	public static String ConvertPaddingToStr(PADDING padding) {
		return String.format("%1$s,%2$s,%3$s,%4$s", new Object[] { Integer.valueOf(padding.left),
				Integer.valueOf(padding.top), Integer.valueOf(padding.right), Integer.valueOf(padding.bottom) });
	}

	public static String ConvertPointToStr(POINT point) {
		return String.format("%1$s,%2$s", new Object[] { Integer.valueOf(point.x), Integer.valueOf(point.y) });
	}

	public static String ConvertRectToStr(RECT rect) {
		return String.format("%1$s,%2$s,%3$s,%4$s", new Object[] { Integer.valueOf(rect.left),
				Integer.valueOf(rect.top), Integer.valueOf(rect.right), Integer.valueOf(rect.bottom) });
	}

	public static String ConvertSizeToStr(SIZE size) {
		return String.format("%1$s,%2$s", new Object[] { Integer.valueOf(size.cx), Integer.valueOf(size.cy) });
	}

	public static ANCHOR ConvertStrToAnchor(String str) {
		str = str.toLowerCase();
		boolean left = false;
		boolean top = false;
		boolean right = false;
		boolean bottom = false;
		String[] strs = str.split("[,]");
		for (int i = 0; i < strs.length; i++) {
			String anchorStr = strs[i];
			if (anchorStr.equals("left")) {
				left = true;
			} else if (anchorStr.equals("top")) {
				top = true;
			} else if (anchorStr.equals("right")) {
				right = true;
			} else if (anchorStr.equals("bottom")) {
				bottom = true;
			}
		}
		ANCHOR anchor = new ANCHOR(left, top, right, bottom);
		return anchor;
	}

	public static boolean ConvertStrToBool(String str) {
		str = str.toLowerCase();
		return str.equals("true");
	}

	public static long ConvertStrToColor(String str) {
		str = str.toLowerCase();
		if (str.equals("empty")) {
			return COLOR.EMPTY;
		}
		if (str.equals("control")) {
			return COLOR.CONTROL;
		}
		if (str.equals("controlborder")) {
			return COLOR.CONTROLBORDER;
		}
		if (str.equals("controltext")) {
			return COLOR.CONTROLTEXT;
		}
		if (str.equals("disabledcontrol")) {
			return COLOR.DISABLEDCONTROL;
		}
		if (str.equals("disabledcontroltext")) {
			return COLOR.DISABLEDCONTROLTEXT;
		}

		String[] strs = str.split("[,]");
		int a = 255;
		int r = 255;
		int g = 255;
		int b = 255;
		if (strs.length == 3) {
			r = Integer.parseInt(strs[0]);
			g = Integer.parseInt(strs[1]);
			b = Integer.parseInt(strs[2]);
			return COLOR.ARGB(r, g, b);
		}
		if (strs.length == 4) {
			a = Integer.parseInt(strs[0]);
			r = Integer.parseInt(strs[1]);
			g = Integer.parseInt(strs[2]);
			b = Integer.parseInt(strs[3]);
			return COLOR.ARGB(a, r, g, b);
		}

		return COLOR.EMPTY;
	}

	public static ContentAlignmentA ConvertStrToContentAlignment(String str) {
		str = str.toLowerCase();
		ContentAlignmentA contentAlignment = ContentAlignmentA.MiddleCenter;
		if (str.equals("bottomcenter")) {
			contentAlignment = ContentAlignmentA.BottomCenter;
		} else if (str.equals("bottomleft")) {
			contentAlignment = ContentAlignmentA.BottomLeft;
		} else if (str.equals("bottomright")) {
			contentAlignment = ContentAlignmentA.BottomRight;
		} else if (str.equals("middleleft")) {
			contentAlignment = ContentAlignmentA.MiddleLeft;
		} else if (str.equals("middlecenter")) {
			contentAlignment = ContentAlignmentA.MiddleCenter;
		} else if (str.equals("middleright")) {
			contentAlignment = ContentAlignmentA.MiddleRight;
		} else if (str.equals("topcenter")) {
			contentAlignment = ContentAlignmentA.TopCenter;
		} else if (str.equals("topleft")) {
			contentAlignment = ContentAlignmentA.TopLeft;
		} else if (str.equals("topright")) {
			contentAlignment = ContentAlignmentA.TopRight;
		}
		return contentAlignment;
	}

	public static DockStyleA ConvertStrToDock(String str) {
		str = str.toLowerCase();
		DockStyleA dock = DockStyleA.None;
		if (str.equals("bottom")) {
			dock = DockStyleA.Bottom;
		} else if (str.equals("fill")) {
			dock = DockStyleA.Fill;
		} else if (str.equals("left")) {
			dock = DockStyleA.Left;
		} else if (str.equals("right")) {
			dock = DockStyleA.Right;
		} else if (str.equals("top")) {
			dock = DockStyleA.Top;
		}
		return dock;
	}

	public static double ConvertStrToDouble(String str) {
		try {
			return Double.parseDouble(str);
		} catch (Exception ex) {
		}

		return 0.0D;
	}

	public static float ConvertStrToFloat(String str) {
		try {
			return Float.parseFloat(str);
		} catch (Exception ex) {
		}

		return 0.0F;
	}

	public static FONT ConvertStrToFont(String str) {
		String[] strs = str.split("[,]");
		int size = strs.length;
		String fontFamily = "SimSun";
		float fontSize = 12.0F;
		boolean bold = false;
		boolean underline = false;
		boolean italic = false;
		boolean strikeout = false;
		if (size >= 1) {
			fontFamily = strs[0];
		}
		if (size >= 2) {
			fontSize = ConvertStrToFloat(strs[1]);
		}
		for (int i = 2; i < size; i++) {
			String subStr = strs[i].toLowerCase();
			if (subStr.equals("bold")) {
				bold = true;
			} else if (subStr.equals("underline")) {
				underline = true;
			} else if (subStr.equals("italic")) {
				italic = true;
			} else if (subStr.equals("")) {
				strikeout = true;
			}
		}
		return new FONT(fontFamily, fontSize, bold, underline, italic, strikeout);
	}

	public static HorizontalAlignA ConvertStrToHorizontalAlign(String str) {
		str = str.toLowerCase();
		HorizontalAlignA horizontalAlign = HorizontalAlignA.Center;
		if (str.equals("right")) {
			horizontalAlign = HorizontalAlignA.Right;
		} else if (str.equals("inherit")) {
			horizontalAlign = HorizontalAlignA.Inherit;
		} else if (str.equals("left")) {
			horizontalAlign = HorizontalAlignA.Left;
		}
		return horizontalAlign;
	}

	public static int ConvertStrToInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (Exception ex) {
		}

		return 0;
	}

	public static LayoutStyleA ConvertStrToLayoutStyle(String str) {
		str = str.toLowerCase();
		LayoutStyleA layoutStyle = LayoutStyleA.None;
		if (str.equals("bottomtotop")) {
			layoutStyle = LayoutStyleA.BottomToTop;
		} else if (str.equals("lefttoright")) {
			layoutStyle = LayoutStyleA.LeftToRight;
		} else if (str.equals("righttoleft")) {
			layoutStyle = LayoutStyleA.RightToLeft;
		} else if (str.equals("toptobottom")) {
			layoutStyle = LayoutStyleA.TopToBottom;
		}
		return layoutStyle;
	}

	public static long ConvertStrToLong(String str) {
		try {
			return Long.parseLong(str);
		} catch (Exception ex) {
		}

		return 0L;
	}

	public static PADDING ConvertStrToPadding(String str) {
		int left = 0;
		int top = 0;
		int right = 0;
		int bottom = 0;
		String[] strs = str.split("[,]");
		if (strs.length == 4) {
			left = Integer.parseInt(strs[0]);
			top = Integer.parseInt(strs[1]);
			right = Integer.parseInt(strs[2]);
			bottom = Integer.parseInt(strs[3]);
		}
		return new PADDING(left, top, right, bottom);
	}

	public static POINT ConvertStrToPoint(String str) {
		int x = 0;
		int y = 0;
		String[] strs = str.split("[,]");
		if (strs.length == 2) {
			x = Integer.parseInt(strs[0]);
			y = Integer.parseInt(strs[1]);
		}
		return new POINT(x, y);
	}

	public static RECT ConvertStrToRect(String str) {
		int left = 0;
		int top = 0;
		int right = 0;
		int bottom = 0;
		String[] strs = str.split("[,]");
		if (strs.length == 4) {
			left = Integer.parseInt(strs[0]);
			top = Integer.parseInt(strs[1]);
			right = Integer.parseInt(strs[2]);
			bottom = Integer.parseInt(strs[3]);
		}
		return new RECT(left, top, right, bottom);
	}

	public static SIZE ConvertStrToSize(String str) {
		int cx = 0;
		int cy = 0;
		String[] strs = str.split("[,]");
		if (strs.length == 2) {
			cx = Integer.parseInt(strs[0]);
			cy = Integer.parseInt(strs[1]);
		}
		return new SIZE(cx, cy);
	}

	public static VerticalAlignA ConvertStrToVerticalAlign(String str) {
		str = str.toLowerCase();
		VerticalAlignA verticalAlign = VerticalAlignA.Middle;
		if (str.equals("bottom")) {
			verticalAlign = VerticalAlignA.Bottom;
		} else if (str.equals("inherit")) {
			verticalAlign = VerticalAlignA.Inherit;
		} else if (str.equals("top")) {
			verticalAlign = VerticalAlignA.Top;
		}
		return verticalAlign;
	}

	public static String ConvertVerticalAlignToStr(VerticalAlignA verticalAlign) {
		String str = "";
		if (verticalAlign == VerticalAlignA.Bottom) {
			str = "Bottom";
		} else if (verticalAlign == VerticalAlignA.Inherit) {
			str = "Inherit";
		} else if (verticalAlign == VerticalAlignA.Middle) {
			str = "Middle";
		} else if (verticalAlign == VerticalAlignA.Top) {
			str = "Top";
		}
		return str;
	}

	public static String GetGuid() {
		return UUID.randomUUID().toString();
	}

	public static String GetValueByDigit(double value, int digit) {
		if (digit > 0) {
			String format = String.format("%d", new Object[] { Integer.valueOf(digit) });
			format = "%." + format + "f";
			String str = String.format(format, new Object[] { Double.valueOf(value) });
			return str;
		}

		return String.format("%d", new Object[] { Integer.valueOf((int) value) });
	}

	public static float SafeDoubleToFloat(double value, int digit) {
		String str = GetValueByDigit(value, digit);
		return ConvertStrToFloat(str);
	}
}
