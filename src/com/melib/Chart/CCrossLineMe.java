package com.melib.Chart;

import java.util.ArrayList;
import java.util.Arrays;
import com.melib.Base.COLOR;
import com.melib.Base.CPaintMe;
import com.melib.Base.CPropertyMe;
import com.melib.Base.CStrMe;
import com.melib.Base.RECT;
import com.melib.Base.RefObject;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class CCrossLineMe implements CPropertyMe {
	protected void finalize() throws Throwable {
		Dispose();
	}

	protected boolean m_allowDoubleClick = true;

	public boolean AllowDoubleClick() {
		return this.m_allowDoubleClick;
	}

	public void SetAllowDoubleClick(boolean value) {
		this.m_allowDoubleClick = value;
	}

	protected boolean m_allowUserPaint = false;

	public boolean AllowUserPaint() {
		return this.m_allowUserPaint;
	}

	public void SetAllowUserPaint(boolean allowUserPaint) {
		this.m_allowUserPaint = allowUserPaint;
	}

	protected AttachVScale m_attachVScale = AttachVScale.Left;

	public AttachVScale GetAttachVScale() {
		return this.m_attachVScale;
	}

	public void SetAttachVScale(AttachVScale value) {
		this.m_attachVScale = value;
	}

	protected boolean m_isDisposed = false;

	public boolean IsDisposed() {
		return this.m_isDisposed;
	}

	protected long m_lineColor = COLOR.ARGB(100, 100, 100);

	public long GetLineColor() {
		return this.m_lineColor;
	}

	public void SetLineColor(long value) {
		this.m_lineColor = value;
	}

	public void Dispose() {
		this.m_isDisposed = true;
	}

	public void GetProperty(String name, RefObject<String> value, RefObject<String> type) {
		if (name.equals("allowdoubleclick")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(AllowDoubleClick());
		} else if (name.equals("allowuserpaint")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(AllowUserPaint());
		} else if (name.equals("attachvscale")) {
			type.argvalue = "enum:AttachVScale";
			if (GetAttachVScale() == AttachVScale.Left) {
				value.argvalue = "Left";
			} else {
				value.argvalue = "Right";
			}
		} else if (name.equals("linecolor")) {
			type.argvalue = "color";
			value.argvalue = CStrMe.ConvertColorToStr(GetLineColor());
		}
	}

	public ArrayList<String> GetPropertyNames() {
		ArrayList<String> propertyNames = new ArrayList();
		propertyNames.addAll(
				Arrays.asList(new String[] { "AllowDoubleClick", "AllowUserPaint", "AttachVScale", "LineColor" }));
		return propertyNames;
	}

	public void OnPaint(CPaintMe paint, CDivMe div, RECT rect) {
	}

	public void SetProperty(String name, String value) {
		if (name.equals("allowdoubleclick")) {
			SetAllowDoubleClick(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("allowuserpaint")) {
			SetAllowUserPaint(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("attachvscale")) {
			value = value.toLowerCase();
			if (value.equals("left")) {
				SetAttachVScale(AttachVScale.Left);
			} else {
				SetAttachVScale(AttachVScale.Right);
			}
		} else if (name.equals("linecolor")) {
			SetLineColor(CStrMe.ConvertStrToColor(value));
		}
	}
}
