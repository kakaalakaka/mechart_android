package com.melib.Window;

import java.util.ArrayList;
import com.melib.Base.COLOR;
import com.melib.Base.CPaintMe;
import com.melib.Base.CControlMe;
import com.melib.Base.DockStyleA;
import com.melib.Base.INativeBaseMe;
import com.melib.Base.POINT;
import com.melib.Base.RECT;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class CWindowFrameMe extends CControlMe {
	public CWindowFrameMe() {
		SetBackColor(COLOR.EMPTY);
		SetBorderColor(COLOR.EMPTY);
		SetDock(DockStyleA.Fill);
	}

	public boolean ContainsPoint(POINT point) {
		ArrayList<CControlMe> controls = GetControls();
		int count = controls.size();
		for (int i = 0; i < count; i++) {
			CWindowMe window = (CWindowMe) ((controls.get(i) instanceof CWindowMe) ? (CControlMe) controls.get(i) : null);
			if ((window != null) && (window.GetFrame() == this)) {
				if (window.IsDialog()) {
					return true;
				}

				return window.ContainsPoint(point);
			}
		}

		return false;
	}

	public String GetControlType() {
		return "WindowFrame";
	}

	public void Invalidate() {
		if (this.m_native != null) {
			ArrayList<CControlMe> controls = GetControls();
			int count = controls.size();
			for (int i = 0; i < count; i++) {
				CWindowMe window = (CWindowMe) ((controls.get(i) instanceof CWindowMe) ? (CControlMe) controls.get(i) : null);
				if (window != null) {
					this.m_native.Invalidate(window.GetDynamicPaintRect());
					break;
				}
			}
		}
	}

	public void OnPaintBackground(CPaintMe paint, RECT clipRect) {
		super.OnPaintBackground(paint, clipRect);
		if (paint.SupportTransparent()) {
			ArrayList<CControlMe> controls = GetControls();
			int count = controls.size();
			for (int i = 0; i < count; i++) {
				CWindowMe window = (CWindowMe) ((controls.get(i) instanceof CWindowMe) ? (CControlMe) controls.get(i) : null);
				if (window != null) {
					long shadowColor = window.GetShadowColor();
					int shadowSize = window.GetShadowSize();
					if ((shadowColor != COLOR.EMPTY) && (shadowSize > 0) && (window.IsDialog())
							&& (window.GetFrame() == this)) {
						RECT bounds = window.GetBounds();
						RECT rect = new RECT(bounds.left - shadowSize, bounds.top - shadowSize, bounds.left,
								bounds.bottom + shadowSize);
						paint.FillRect(shadowColor, rect);
						RECT rect1 = new RECT(bounds.right, bounds.top - shadowSize, bounds.right + shadowSize,
								bounds.bottom + shadowSize);
						paint.FillRect(shadowColor, rect1);
						RECT rect2 = new RECT(bounds.left, bounds.top - shadowSize, bounds.right, bounds.top);
						paint.FillRect(shadowColor, rect2);
						RECT rect3 = new RECT(bounds.left, bounds.bottom, bounds.right, bounds.bottom + shadowSize);
						paint.FillRect(shadowColor, rect3);
					}
				}
			}
		}
	}
}
