package com.melib.Label;

import java.util.ArrayList;
import java.util.Arrays;
import com.melib.Base.COLOR;
import com.melib.Base.CPaintMe;
import com.melib.Base.CStrMe;
import com.melib.Base.ContentAlignmentA;
import com.melib.Base.CControlMe;
import com.melib.Base.FONT;
import com.melib.Base.PADDING;
import com.melib.Base.POINT;
import com.melib.Base.RECT;
import com.melib.Base.RefObject;
import com.melib.Base.SIZE;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class CLabelMe extends CControlMe {
	public CLabelMe() {
		SetAutoSize(true);
		SetBackColor(COLOR.EMPTY);
		SetBorderColor(COLOR.EMPTY);
		SIZE size = new SIZE(100, 20);
		SetSize(size);
	}

	protected ContentAlignmentA m_textAlign = ContentAlignmentA.TopLeft;

	public final ContentAlignmentA GetTextAlign() {
		return this.m_textAlign;
	}

	public final void SetTextAlign(ContentAlignmentA value) {
		this.m_textAlign = value;
	}

	public String GetControlType() {
		return "Label";
	}

	public void GetProperty(String name, RefObject<String> value, RefObject<String> type) {
		if (name.equals("textalign")) {
			type.argvalue = "enum:ContentAlignmentA";
			value.argvalue = CStrMe.ConvertContentAlignmentToStr(GetTextAlign());
		} else {
			super.GetProperty(name, value, type);
		}
	}

	public ArrayList<String> GetPropertyNames() {
		ArrayList<String> propertyNames = super.GetPropertyNames();
		propertyNames.addAll(Arrays.asList(new String[] { "TextAlign" }));
		return propertyNames;
	}

	public void OnPaintForeground(CPaintMe paint, RECT clipRect) {
		String text = GetText();
		if ((text != null) && (text.length() > 0)) {
			int width = GetWidth();
			int height = GetHeight();
			if ((width > 0) && (height > 0)) {
				FONT font = GetFont();
				SIZE tSize = paint.TextSize(text, font);
				POINT point = new POINT((width - tSize.cx) / 2, (height - tSize.cy) / 2);
				PADDING padding = GetPadding();
				switch (this.m_textAlign) {
				case BottomCenter:
					point.y = (height - tSize.cy);
					break;
				case BottomLeft:
					point.x = padding.left;
					point.y = (height - tSize.cy - padding.bottom);
					break;
				case BottomRight:
					point.x = (width - tSize.cx - padding.right);
					point.y = (height - tSize.cy - padding.bottom);
					break;
				case MiddleLeft:
					point.x = padding.left;
					break;
				case MiddleRight:
					point.x = (width - tSize.cx - padding.right);
					break;
				case TopCenter:
					point.y = padding.top;
					break;
				case TopLeft:
					point.x = padding.left;
					point.y = padding.top;
					break;
				case TopRight:
					point.x = (width - tSize.cx - padding.right);
					point.y = padding.top;
				}

				RECT newRect = new RECT(point.x, point.y, point.x + tSize.cx, point.y + tSize.cy);
				long paintingForeColor = GetPaintingForeColor();
				if ((AutoEllipsis()) && ((newRect.right > clipRect.right) || (newRect.bottom > clipRect.bottom))) {
					if (newRect.right > clipRect.right) {
						newRect.right = clipRect.right;
					}
					if (newRect.bottom > clipRect.bottom) {
						newRect.bottom = clipRect.bottom;
					}
					paint.DrawTextAutoEllipsis(text, paintingForeColor, font, newRect);
				} else {
					paint.DrawText(text, paintingForeColor, font, newRect);
				}
			}
		}
	}

	public void OnPrePaint(CPaintMe paint, RECT clipRect) {
		if (AutoSize()) {
			String text = GetText();
			FONT font = GetFont();
			SIZE tSize = paint.TextSize(text, font);
			int cy = tSize.cx + 4;
			int cx = tSize.cy + 4;
			int width = GetWidth();
			int height = GetHeight();
			if ((cy != width) || (cx != height)) {
				SetSize(new SIZE(cy, cx));
				width = cy;
				height = cx;
			}
		}
	}

	public void SetProperty(String name, String value) {
		if (name.equals("textalign")) {
			SetTextAlign(CStrMe.ConvertStrToContentAlignment(value));
		} else {
			super.SetProperty(name, value);
		}
	}
}
