package com.melib.Label;

import java.util.ArrayList;
import java.util.Arrays;
import com.melib.Base.COLOR;
import com.melib.Base.CPaintMe;
import com.melib.Base.CStrMe;
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

public class CLinkLabelMe extends CLabelMe {
	protected boolean m_visited = false;

	protected long m_activeLinkColor = COLOR.ARGB(255, 0, 0);

	public final long GetActiveLinkColor() {
		return this.m_activeLinkColor;
	}

	public final void SetActiveLinkColor(long value) {
		this.m_activeLinkColor = value;
	}

	protected long m_disabledLinkColor = COLOR.ARGB(133, 133, 133);

	public final long GetDisabledLinkColor() {
		return this.m_disabledLinkColor;
	}

	public final void SetDisabledLinkColor(long value) {
		this.m_disabledLinkColor = value;
	}

	protected LinkBehaviorA m_linkBehavior = LinkBehaviorA.AlwaysUnderLine;

	public final LinkBehaviorA GetLinkBehavior() {
		return this.m_linkBehavior;
	}

	public final void SetLinkBehavior(LinkBehaviorA value) {
		this.m_linkBehavior = value;
	}

	protected long m_linkColor = COLOR.ARGB(0, 0, 255);

	public final long GetLinkColor() {
		return this.m_linkColor;
	}

	public final void SetLinkColor(long value) {
		this.m_linkColor = value;
	}

	protected boolean m_linkVisited = false;

	public final boolean IsLinkVisited() {
		return this.m_linkVisited;
	}

	public final void SetLinkVisited(boolean value) {
		this.m_linkVisited = value;
	}

	protected long m_visitedLinkColor = COLOR.ARGB(128, 0, 128);

	public final long GetVisitedLinkColor() {
		return this.m_visitedLinkColor;
	}

	public final void SetVisitedLinkColor(long value) {
		this.m_visitedLinkColor = value;
	}

	public String GetControlType() {
		return "LinkLabel";
	}

	protected long GetPaintingLinkColor() {
		if (IsEnabled()) {
			INativeBaseMe nativeBase = GetNative();
			if (this == nativeBase.GetHoveredControl()) {
				return this.m_activeLinkColor;
			}
			if (this == nativeBase.GetPushedControl()) {
				return this.m_activeLinkColor;
			}

			if ((this.m_linkVisited) && (this.m_visited)) {
				return this.m_visitedLinkColor;
			}

			return this.m_linkColor;
		}
		return this.m_disabledLinkColor;
	}

	public void GetProperty(String name, RefObject<String> value, RefObject<String> type) {
		if (name.equals("activelinkcolor")) {
			type.argvalue = "color";
			value.argvalue = CStrMe.ConvertColorToStr(GetActiveLinkColor());
		} else if (name.equals("disabledlinkcolor")) {
			type.argvalue = "color";
			value.argvalue = CStrMe.ConvertColorToStr(GetDisabledLinkColor());
		} else if (name.equals("linkbehavior")) {
			type.argvalue = "enum:LinkBehaviorA";
			LinkBehaviorA linkBehavior = GetLinkBehavior();
			if (linkBehavior == LinkBehaviorA.AlwaysUnderLine) {
				value.argvalue = "AlwaysUnderLine";
			} else if (linkBehavior == LinkBehaviorA.HoverUnderLine) {
				value.argvalue = "HoverUnderLine";
			} else {
				value.argvalue = "NeverUnderLine";
			}
		} else if (name.equals("linkcolor")) {
			type.argvalue = "color";
			value.argvalue = CStrMe.ConvertColorToStr(GetLinkColor());
		} else if (name.equals("linkvisited")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(IsLinkVisited());
		} else if (name.equals("visitedlinkcolor")) {
			type.argvalue = "color";
			value.argvalue = CStrMe.ConvertColorToStr(GetVisitedLinkColor());
		} else {
			super.GetProperty(name, value, type);
		}
	}

	public ArrayList<String> GetPropertyNames() {
		ArrayList<String> propertyNames = super.GetPropertyNames();
		propertyNames.addAll(Arrays.asList(new String[] { "ActiveLinkColor", "DisabledLinkColor", "LinkBehavior",
				"LinkColor", "LinkVisited", "VisitedLinkColor" }));
		return propertyNames;
	}

	public void OnClick(POINT mp, MouseButtonsA button, int clicks, int delta) {
		super.OnClick(mp.Clone(), button, clicks, delta);
		if (this.m_linkVisited) {
			this.m_visited = true;
		}
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
				SIZE tSize = paint.TextSize(text, font);
				long paintingLinkColor = GetPaintingLinkColor();
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

				RECT rect = new RECT(point.x, point.y, point.x + tSize.cx, point.y + tSize.cy);
				if ((AutoEllipsis()) && ((rect.right > clipRect.right) || (rect.bottom > clipRect.bottom))) {
					if (rect.right > clipRect.right) {
						rect.right = clipRect.right;
					}
					if (rect.bottom > clipRect.bottom) {
						rect.bottom = clipRect.bottom;
					}
					paint.DrawTextAutoEllipsis(text, paintingLinkColor, font, rect);
				} else {
					paint.DrawText(text, paintingLinkColor, font, rect);
				}

				INativeBaseMe nativeBase = GetNative();
				if ((this.m_linkBehavior == LinkBehaviorA.AlwaysUnderLine)
						|| ((this.m_linkBehavior == LinkBehaviorA.HoverUnderLine)
								&& ((this == nativeBase.GetPushedControl())
										|| (this == nativeBase.GetHoveredControl())))) {
					paint.DrawLine(paintingLinkColor, 1.0F, 0, point.x, point.y + tSize.cy, point.x + tSize.cx,
							point.y + tSize.cy);
				}
			}
		}
	}

	public void SetProperty(String name, String value) {
		if (name.equals("activelinkcolor")) {
			SetActiveLinkColor(CStrMe.ConvertStrToColor(value));
		} else if (name.equals("disabledlinkcolor")) {
			SetDisabledLinkColor(CStrMe.ConvertStrToColor(value));
		} else if (name.equals("linkbehavior")) {
			value = value.toLowerCase();
			if (value.equals("alwaysunderline")) {
				SetLinkBehavior(LinkBehaviorA.AlwaysUnderLine);
			} else if (value.equals("hoverunderline")) {
				SetLinkBehavior(LinkBehaviorA.HoverUnderLine);
			} else {
				SetLinkBehavior(LinkBehaviorA.NeverUnderLine);
			}
		} else if (name.equals("linkcolor")) {
			SetLinkColor(CStrMe.ConvertStrToColor(value));
		} else if (name.equals("linkvisited")) {
			SetLinkVisited(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("visitedlinkcolor")) {
			SetVisitedLinkColor(CStrMe.ConvertStrToColor(value));
		} else {
			super.SetProperty(name, value);
		}
	}
}
