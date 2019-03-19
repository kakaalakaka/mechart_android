package com.melib.Layout;

import java.util.ArrayList;
import java.util.Arrays;
import com.melib.Base.CStrMe;
import com.melib.Base.CControlMe;
import com.melib.Base.LayoutStyleA;
import com.melib.Base.PADDING;
import com.melib.Base.RECT;
import com.melib.Base.RefObject;
import com.melib.Base.SIZE;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class CLayoutDivMe extends CDivMe {
	protected boolean m_autoWrap = false;

	public boolean AutoWrap() {
		return this.m_autoWrap;
	}

	public void SetAutoWrap(boolean autoWrap) {
		this.m_autoWrap = autoWrap;
	}

	protected LayoutStyleA m_layoutStyle = LayoutStyleA.LeftToRight;

	public LayoutStyleA GetLayoutStyle() {
		return this.m_layoutStyle;
	}

	public void SetLayoutStyle(LayoutStyleA value) {
		this.m_layoutStyle = value;
	}

	public String GetControlType() {
		return "LayoutDiv";
	}

	public void GetProperty(String name, RefObject<String> value, RefObject<String> type) {
		if (name.equals("autowrap")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(AutoWrap());
		} else if (name.equals("layoutstyle")) {
			type.argvalue = "enum:LayoutStyleA";
			value.argvalue = CStrMe.ConvertLayoutStyleToStr(GetLayoutStyle());
		} else {
			super.GetProperty(name, value, type);
		}
	}

	public ArrayList<String> GetPropertyNames() {
		ArrayList<String> propertyNames = super.GetPropertyNames();
		propertyNames.addAll(Arrays.asList(new String[] { "AutoWrap", "LayoutStyle" }));
		return propertyNames;
	}

	public boolean OnResetLayout() {
		boolean reset = false;
		if (GetNative() != null) {
			PADDING padding = GetPadding();
			int left = padding.left;
			int top = padding.top;
			int width = GetWidth() - padding.left - padding.right;
			int height = GetHeight() - padding.top - padding.bottom;
			int count = this.m_controls.size();
			for (int i = 0; i < count; i++) {
				CControlMe control = (CControlMe) this.m_controls.get(i);
				if ((control.IsVisible()) && (control != GetHScrollBar()) && (control != GetVScrollBar())) {
					SIZE size = control.GetSize();
					int cLeft = control.GetLeft();
					int cTop = control.GetTop();
					int cWidth = size.cx;
					int cHeight = size.cy;
					int nLeft = cLeft;
					int nTop = cTop;
					int nWidth = cWidth;
					int nHeight = cHeight;
					PADDING margin = control.GetMargin();
					if (this.m_layoutStyle == LayoutStyleA.BottomToTop) {
						if (i == 0) {
							top = padding.top + height;
						}
						int lWidth = 0;
						if (this.m_autoWrap) {
							lWidth = size.cx;
							int lTop = top - margin.top - cHeight - margin.bottom;
							if (lTop < padding.top) {
								left += cWidth + margin.left;
								top = height - padding.top;
							}
						} else {
							lWidth = width - margin.left - margin.right;
						}
						top -= cHeight + margin.bottom;
						nLeft = left + margin.left;
						nWidth = lWidth;
						nTop = top;
					} else if (this.m_layoutStyle == LayoutStyleA.LeftToRight) {
						int lHeight = 0;
						if (this.m_autoWrap) {
							lHeight = size.cy;
							int lRight = left + margin.left + cWidth + margin.right;
							if (lRight > width) {
								left = padding.left;
								top += cHeight + margin.top;
							}
						} else {
							lHeight = height - margin.top - margin.bottom;
						}
						left += margin.left;
						nLeft = left;
						nTop = top + margin.top;
						nHeight = lHeight;
						left += cWidth + margin.right;
					} else if (this.m_layoutStyle == LayoutStyleA.RightToLeft) {
						if (i == 0) {
							left = width - padding.left;
						}
						int lHeight = 0;
						if (this.m_autoWrap) {
							lHeight = size.cy;
							int lLeft = left - margin.left - cWidth - margin.right;
							if (lLeft < padding.left) {
								left = width - padding.left;
								top += cHeight + margin.top;
							}
						} else {
							lHeight = height - margin.top - margin.bottom;
						}
						left -= cWidth + margin.left;
						nLeft = left;
						nTop = top + margin.top;
						nHeight = lHeight;
					} else if (this.m_layoutStyle == LayoutStyleA.TopToBottom) {
						int lWidth = 0;
						if (this.m_autoWrap) {
							lWidth = size.cx;
							int lBottom = top + margin.top + cHeight + margin.bottom;
							if (lBottom > height) {
								left += cWidth + margin.left + margin.right;
								top = padding.top;
							}
						} else {
							lWidth = width - margin.left - margin.right;
						}
						top += margin.top;
						nTop = top;
						nLeft = left + margin.left;
						nWidth = lWidth;
						top += cHeight + margin.bottom;
					}
					if ((cLeft != nLeft) || (cTop != nTop) || (cWidth != nWidth) || (cHeight != nHeight)) {
						RECT rect = new RECT(nLeft, nTop, nLeft + nWidth, nTop + nHeight);
						control.SetBounds(rect);
						reset = true;
					}
				}
			}
		}
		return reset;
	}

	public void SetProperty(String name, String value) {
		if (name.equals("autowrap")) {
			SetAutoWrap(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("layoutstyle")) {
			SetLayoutStyle(CStrMe.ConvertStrToLayoutStyle(value));
		} else {
			super.SetProperty(name, value);
		}
	}

	public void Update() {
		OnResetLayout();
		int count = this.m_controls.size();
		for (int i = 0; i < count; i++) {
			((CControlMe) this.m_controls.get(i)).Update();
		}
		UpdateScrollBar();
	}
}
