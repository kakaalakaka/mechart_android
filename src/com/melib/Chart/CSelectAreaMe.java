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

public class CSelectAreaMe implements CPropertyMe {
	protected void finalize() throws Throwable {
		Dispose();
	}

	protected boolean m_allowUserPaint = false;

	public boolean AllowUserPaint() {
		return this.m_allowUserPaint;
	}

	public void SetAllowUserPaint(boolean allowUserPaint) {
		this.m_allowUserPaint = allowUserPaint;
	}

	protected long m_backColor = COLOR.EMPTY;

	public long GetBackColor() {
		return this.m_backColor;
	}

	public void SetBackColor(long value) {
		this.m_backColor = value;
	}

	protected RECT m_bounds = new RECT();

	public RECT GetBounds() {
		return this.m_bounds.Clone();
	}

	public void SetBounds(RECT value) {
		this.m_bounds = value.Clone();
	}

	protected boolean m_canResize = false;

	public boolean CanResize() {
		return this.m_canResize;
	}

	public void SetCanResize(boolean value) {
		this.m_canResize = value;
	}

	protected boolean m_enabled = true;

	public boolean IsEnabled() {
		return this.m_enabled;
	}

	public void SetEnabled(boolean value) {
		this.m_enabled = value;
	}

	protected boolean m_isDisposed = false;

	public boolean IsDisposed() {
		return this.m_isDisposed;
	}

	protected long m_lineColor = COLOR.ARGB(255, 255, 255);
	protected boolean m_visible;

	public long GetLineColor() {
		return this.m_lineColor;
	}

	public void SetLineColor(long value) {
		this.m_lineColor = value;
	}

	public boolean IsVisible() {
		return this.m_visible;
	}

	public void SetVisible(boolean value) {
		this.m_visible = value;
	}

	public void Close() {
		this.m_visible = false;
		this.m_canResize = false;
	}

	public void Dispose() {
		this.m_isDisposed = true;
	}

	public void GetProperty(String name, RefObject<String> value, RefObject<String> type) {
		if (name.equals("allowuserpaint")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(AllowUserPaint());
		} else if (name.equals("enabled")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(IsEnabled());
		} else if (name.equals("linecolor")) {
			type.argvalue = "color";
			value.argvalue = CStrMe.ConvertColorToStr(GetLineColor());
		}
	}

	public ArrayList<String> GetPropertyNames() {
		ArrayList<String> propertyNames = new ArrayList();
		propertyNames.addAll(Arrays.asList(new String[] { "AllowUserPaint", "Enabled", "LineColor" }));
		return propertyNames;
	}

	public void OnPaint(CPaintMe paint, CDivMe div, RECT rect) {
	}

	public void SetProperty(String name, String value) {
		if (name.equals("allowuserpaint")) {
			SetAllowUserPaint(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("enabled")) {
			SetEnabled(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("linecolor")) {
			SetLineColor(CStrMe.ConvertStrToColor(value));
		}
	}
}
