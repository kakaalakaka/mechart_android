package com.melib.Grid;

import com.melib.Base.COLOR;
import com.melib.Base.FONT;
import com.melib.Base.HorizontalAlignA;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class GridCellStyle {
	protected HorizontalAlignA m_align = HorizontalAlignA.Inherit;
	protected boolean m_autoEllipsis;

	public HorizontalAlignA GetAlign() {
		return this.m_align;
	}

	public void SetAlign(HorizontalAlignA value) {
		this.m_align = value;
	}

	public boolean AutoEllipsis() {
		return this.m_autoEllipsis;
	}

	public void SetAutoEllipsis(boolean value) {
		this.m_autoEllipsis = value;
	}

	protected long m_backColor = COLOR.EMPTY;
	protected FONT m_font;

	public long GetBackColor() {
		return this.m_backColor;
	}

	public void SetBackColor(long value) {
		this.m_backColor = value;
	}

	public FONT GetFont() {
		return this.m_font;
	}

	public void SetFont(FONT value) {
		this.m_font = value;
	}

	protected long m_foreColor = COLOR.EMPTY;

	public long GetForeColor() {
		return this.m_foreColor;
	}

	public void SetForeColor(long value) {
		this.m_foreColor = value;
	}
}
