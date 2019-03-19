package com.melib.Layout;

import com.melib.Base.CPaintMe;
import com.melib.Base.EVENTID;
import com.melib.Base.FONT;
import com.melib.Base.POINT;
import com.melib.Base.RECT;
import com.melib.Base.SIZE;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class CGroupBoxMe extends CDivMe {
	public String GetControlType() {
		return "GroupBox";
	}

	public void OnPaintBorder(CPaintMe paint, RECT clipRect) {
		FONT font = GetFont();
		int width = GetWidth();
		int height = GetHeight();
		String text = GetText();
		SIZE tSize = new SIZE();
		if (text.length() > 0) {
			tSize = paint.TextSize(text, font);
		} else {
			tSize = paint.TextSize("0", font);
			tSize.cx = 0;
		}

		POINT[] points = new POINT[6];
		int x = tSize.cy / 2;
		int y = 2;
		points[0] = new POINT(10, x);
		points[1] = new POINT(y, x);
		points[2] = new POINT(y, height - y);
		points[3] = new POINT(width - y, height - y);
		points[4] = new POINT(width - y, x);
		points[5] = new POINT(14 + tSize.cx, x);
		paint.DrawPolyline(GetPaintingBorderColor(), 1.0F, 0, points);
		CallPaintEvents(EVENTID.PAINTBORDER, paint, clipRect);
	}

	public void OnPaintForeground(CPaintMe paint, RECT clipRect) {
		String text = GetText();
		if (text.length() > 0) {
			FONT font = GetFont();
			SIZE tSize = paint.TextSize(text, font);
			RECT rect = new RECT(12, 0, 12 + tSize.cx, tSize.cy);
			paint.DrawText(text, GetPaintingForeColor(), font, rect);
		}
	}
}
