package com.melib.Grid;

import com.melib.Base.COLOR;
import com.melib.Base.FONT;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class GridRowStyle {
	protected long m_backColor = COLOR.CONTROL;

	public long GetBackColor() {
		return this.m_backColor;
	}

	public void SetBackColor(long value) {
		this.m_backColor = value;
	}

	protected FONT m_font = new FONT("Simsun", 14.0F, false, false, false);

	public FONT GetFont() {
		return this.m_font;
	}

	public void SetFont(FONT value) {
		this.m_font = value;
	}

	protected long m_foreColor = COLOR.CONTROLTEXT;

	public long GetForeColor() {
		return this.m_foreColor;
	}

	public void SetForeColor(long value) {
		this.m_foreColor = value;
	}

	protected long m_hoveredBackColor = COLOR.ARGB(150, 150, 150);

	public long GetHoveredBackColor() {
		return this.m_hoveredBackColor;
	}

	public void SetHoveredBackColor(long value) {
		this.m_hoveredBackColor = value;
	}

	protected long m_hoveredForeColor = COLOR.CONTROLTEXT;

	public long GetHoveredForeColor() {
		return this.m_hoveredForeColor;
	}

	public void SetHoveredForeColor(long value) {
		this.m_hoveredForeColor = value;
	}

	protected long m_selectedBackColor = COLOR.ARGB(100, 100, 100);

	public long GetSelectedBackColor() {
		return this.m_selectedBackColor;
	}

	public void SetSelectedBackColor(long value) {
		this.m_selectedBackColor = value;
	}

	protected long m_selectedForeColor = COLOR.CONTROLTEXT;

	public long GetSelectedForeColor() {
		return this.m_selectedForeColor;
	}

	public void SetSelectedForeColor(long value) {
		this.m_selectedForeColor = value;
	}
}
