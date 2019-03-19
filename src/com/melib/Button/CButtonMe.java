package com.melib.Button;

import java.util.ArrayList;
import java.util.Arrays;
import com.melib.Base.COLOR;
import com.melib.Base.CPaintMe;
import com.melib.Base.CStrMe;
import com.melib.Base.ContentAlignmentA;
import com.melib.Base.CControlMe;
import com.melib.Base.FONT;
import com.melib.Base.INativeBaseMe;
import com.melib.Base.MouseButtonsA;
import com.melib.Base.PADDING;
import com.melib.Base.POINT;
import com.melib.Base.RECT;
import com.melib.Base.RefObject;
import com.melib.Base.SIZE;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class CButtonMe extends CControlMe {
	protected String m_disabledBackImage;
	protected String m_hoveredBackImage;
	protected String m_pushedBackImage;

	public CButtonMe() {
		SIZE size = new SIZE(60, 20);
		SetSize(size);
	}

	public final String GetDisabledBackImage() {
		return this.m_disabledBackImage;
	}

	public final void SetDisabledBackImage(String value) {
		this.m_disabledBackImage = value;
	}

	public final String GetHoveredBackImage() {
		return this.m_hoveredBackImage;
	}

	public final void SetHoveredBackImage(String value) {
		this.m_hoveredBackImage = value;
	}

	public final String GetPushedBackImage() {
		return this.m_pushedBackImage;
	}

	public final void SetPushedBackImage(String value) {
		this.m_pushedBackImage = value;
	}

	protected ContentAlignmentA m_textAlign = ContentAlignmentA.MiddleCenter;

	public final ContentAlignmentA GetTextAlign() {
		return this.m_textAlign;
	}

	public final void SetTextAlign(ContentAlignmentA value) {
		this.m_textAlign = value;
	}

	public String GetControlType() {
		return "Button";
	}

	protected long GetPaintingBackColor() {
		long backcolor = super.GetPaintingBackColor();
		if ((backcolor != COLOR.EMPTY) && (IsPaintEnabled(this))) {
			INativeBaseMe nativeBase = GetNative();
			if (this == nativeBase.GetPushedControl()) {
				backcolor = COLOR.PUSHEDCONTROL;
			} else if (this == nativeBase.GetHoveredControl()) {
				backcolor = COLOR.HOVEREDCONTROL;
			}
		}
		return backcolor;
	}

	protected String GetPaintingBackImage() {
		String backimage = null;
		if (IsPaintEnabled(this)) {
			INativeBaseMe nativeBase = GetNative();
			if (this == nativeBase.GetPushedControl()) {
				backimage = this.m_pushedBackImage;
			} else if (this == nativeBase.GetHoveredControl()) {
				backimage = this.m_hoveredBackImage;
			}
		} else {
			backimage = this.m_disabledBackImage;
		}
		if (backimage != null) {
			return backimage;
		}

		return super.GetPaintingBackImage();
	}

	public void GetProperty(String name, RefObject<String> value, RefObject<String> type) {
		if (name.equals("disabledbackimage")) {
			type.argvalue = "text";
			value.argvalue = GetDisabledBackImage();
		} else if (name.equals("hoveredbackimage")) {
			type.argvalue = "text";
			value.argvalue = GetHoveredBackImage();
		} else if (name.equals("pushedbackimage")) {
			type.argvalue = "text";
			value.argvalue = GetPushedBackImage();
		} else if (name.equals("textalign")) {
			type.argvalue = "enum:ContentAlignmentA";
			value.argvalue = CStrMe.ConvertContentAlignmentToStr(GetTextAlign());
		} else {
			super.GetProperty(name, value, type);
		}
	}

	public ArrayList<String> GetPropertyNames() {
		ArrayList<String> propertyNames = super.GetPropertyNames();
		propertyNames.addAll(Arrays
				.asList(new String[] { "DisabledBackImage", "HoveredBackImage", "PushedBackImage", "TextAlign" }));
		return propertyNames;
	}

	public void OnMouseDown(POINT mp, MouseButtonsA button, int clicks, int delta) {
		super.OnMouseDown(mp.Clone(), button, clicks, delta);
		Invalidate();
	}

	public void OnMouseEnter(POINT mp, MouseButtonsA button, int clicks, int delta) {
		super.OnMouseEnter(mp.Clone(), button, clicks, delta);
		Invalidate();
	}

	public void OnMouseLeave(POINT mp, MouseButtonsA button, int clicks, int delta) {
		super.OnMouseLeave(mp.Clone(), button, clicks, delta);
		Invalidate();
	}

	public void OnMouseMove(POINT mp, MouseButtonsA button, int clicks, int delta) {
		super.OnMouseMove(mp.Clone(), button, clicks, delta);
		Invalidate();
	}

	public void OnMouseUp(POINT mp, MouseButtonsA button, int clicks, int delta) {
		super.OnMouseUp(mp.Clone(), button, clicks, delta);
		Invalidate();
	}

	public void OnPaintForeground(CPaintMe paint, RECT clipRect) {
		String text = GetText();
		if ((text != null) && (text.length() > 0)) {
			int width = GetWidth();
			int height = GetHeight();
			if ((width > 0) && (height > 0)) {
				FONT font = GetFont();
				SIZE textSize = paint.TextSize(text, font);
				POINT point = new POINT((width - textSize.cx) / 2, (height - textSize.cy) / 2);
				PADDING padding = GetPadding();
				switch (this.m_textAlign) {
				case BottomCenter:
					point.y = (height - textSize.cy);
					break;
				case BottomLeft:
					point.x = padding.left;
					point.y = (height - textSize.cy - padding.bottom);
					break;
				case BottomRight:
					point.x = (width - textSize.cx - padding.right);
					point.y = (height - textSize.cy - padding.bottom);
					break;
				case MiddleLeft:
					point.x = padding.left;
					break;
				case MiddleRight:
					point.x = (width - textSize.cx - padding.right);
					break;
				case TopCenter:
					point.y = padding.top;
					break;
				case TopLeft:
					point.x = padding.left;
					point.y = padding.top;
					break;
				case TopRight:
					point.x = (width - textSize.cx - padding.right);
					point.y = padding.top;
				}

				RECT rect = new RECT(point.x, point.y, point.x + textSize.cx, point.y + textSize.cy);
				long foreColor = GetPaintingForeColor();
				if ((AutoEllipsis()) && ((rect.right > clipRect.right) || (rect.bottom > clipRect.bottom))) {
					if (rect.right > clipRect.right) {
						rect.right = clipRect.right;
					}
					if (rect.bottom > clipRect.bottom) {
						rect.bottom = clipRect.bottom;
					}
					paint.DrawTextAutoEllipsis(text, foreColor, font, rect);
				} else {
					paint.DrawText(text, foreColor, font, rect);
				}
			}
		}
	}

	public void SetProperty(String name, String value) {
		if (name.equals("disabledbackimage")) {
			SetDisabledBackImage(value);
		} else if (name.equals("hoveredbackimage")) {
			SetHoveredBackImage(value);
		} else if (name.equals("pushedbackimage")) {
			SetPushedBackImage(value);
		} else if (name.equals("textalign")) {
			SetTextAlign(CStrMe.ConvertStrToContentAlignment(value));
		} else {
			super.SetProperty(name, value);
		}
	}
}
