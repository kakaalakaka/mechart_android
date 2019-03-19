package com.melib.Chart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import com.melib.Base.COLOR;
import com.melib.Base.CPaintMe;
import com.melib.Base.CPropertyMe;
import com.melib.Base.CStrMe;
import com.melib.Base.FONT;
import com.melib.Base.RECT;
import com.melib.Base.RefObject;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class CDivMe implements CPropertyMe {
	public CDivMe() {
		this.m_vGrid.SetVisible(false);
	}

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

	protected ArrayList<CPlotMe> m_plots = new ArrayList();

	protected ArrayList<CBaseShapeMe> m_shapes = new ArrayList();

	protected long m_backColor = COLOR.ARGB(0, 0, 0);

	public long GetBackColor() {
		return this.m_backColor;
	}

	public void SetBackColor(long value) {
		this.m_backColor = value;
	}

	protected long m_borderColor = COLOR.EMPTY;

	public long GetBorderColor() {
		return this.m_borderColor;
	}

	public void SetBorderColor(long value) {
		this.m_borderColor = value;
	}

	public int GetBottom() {
		return this.m_bounds.bottom;
	}

	protected RECT m_bounds = new RECT();

	public RECT GetBounds() {
		return this.m_bounds.Clone();
	}

	public void SetBounds(RECT value) {
		this.m_bounds = value.Clone();
	}

	protected CChartMe m_chart = null;

	public CChartMe GetChart() {
		return this.m_chart;
	}

	public void SetChart(CChartMe value) {
		this.m_chart = value;
	}

	protected CCrossLineMe m_crossLine = new CCrossLineMe();

	public CCrossLineMe GetCrossLine() {
		return this.m_crossLine;
	}

	public void SetCrossLine(CCrossLineMe value) {
		if (this.m_crossLine != null) {
			this.m_crossLine.Dispose();
		}
		this.m_crossLine = value;
	}

	protected FONT m_font = new FONT();

	public FONT GetFont() {
		return this.m_font;
	}

	public void SetFont(FONT value) {
		this.m_font = value;
	}

	public int GetHeight() {
		return this.m_bounds.bottom - this.m_bounds.top;
	}

	protected CScaleGridMe m_hGrid = new CScaleGridMe();

	public CScaleGridMe GetHGrid() {
		return this.m_hGrid;
	}

	public void SetHGrid(CScaleGridMe value) {
		if (this.m_hGrid != null) {
			this.m_hGrid.Dispose();
		}
		this.m_hGrid = value;
	}

	protected CHScaleMe m_hScale = new CHScaleMe();

	public CHScaleMe GetHScale() {
		return this.m_hScale;
	}

	public void SetHScale(CHScaleMe value) {
		if (this.m_hScale != null) {
			this.m_hScale.Dispose();
		}
		this.m_hScale = value;
	}

	protected boolean m_isDisposed = false;

	public boolean IsDisposed() {
		return this.m_isDisposed;
	}

	public int GetLeft() {
		return this.m_bounds.left;
	}

	public void SetLeft(int value) {
		this.m_bounds.left = value;
	}

	protected CVScaleMe m_leftVScale = new CVScaleMe();

	public CVScaleMe GetLeftVScale() {
		return this.m_leftVScale;
	}

	public void SetLeftVScale(CVScaleMe value) {
		if (this.m_leftVScale != null) {
			this.m_leftVScale.Dispose();
		}
		this.m_leftVScale = value;
	}

	public int GetRight() {
		return this.m_bounds.right;
	}

	protected CVScaleMe m_rightVScale = new CVScaleMe();

	public CVScaleMe GetRightVScale() {
		return this.m_rightVScale;
	}

	public void SetRightVScale(CVScaleMe value) {
		if (this.m_rightVScale != null) {
			this.m_rightVScale.Dispose();
		}
		this.m_rightVScale = value;
	}

	protected CSelectAreaMe m_selectArea = new CSelectAreaMe();

	public CSelectAreaMe GetSelectArea() {
		return this.m_selectArea;
	}

	public void SetSelectArea(CSelectAreaMe value) {
		if (this.m_selectArea != null) {
			this.m_selectArea.Dispose();
		}
		this.m_selectArea = value;
	}

	protected boolean m_selected = false;

	public boolean IsSelected() {
		return this.m_selected;
	}

	public void SetSelected(boolean value) {
		this.m_selected = value;
	}

	protected boolean m_showSelect = true;

	public boolean ShowSelect() {
		return this.m_showSelect;
	}

	public void SetShowSelect(boolean value) {
		this.m_showSelect = value;
	}

	protected CTitleBarMe m_titleBar = new CTitleBarMe();

	public CTitleBarMe GetTitleBar() {
		return this.m_titleBar;
	}

	public void SetTitleBar(CTitleBarMe value) {
		if (this.m_titleBar != null) {
			this.m_titleBar.Dispose();
		}
		this.m_titleBar = value;
	}

	protected CToolTipMe m_toolTip = new CToolTipMe();

	public CToolTipMe GetToolTip() {
		return this.m_toolTip;
	}

	public void SetToolTip(CToolTipMe value) {
		if (this.m_toolTip != null) {
			this.m_toolTip.Dispose();
		}
		this.m_toolTip = value;
	}

	public int GetTop() {
		return this.m_bounds.top;
	}

	public void SetTop(int value) {
		this.m_bounds.top = value;
	}

	protected float m_verticalPercent = 0.0F;

	public float GetVerticalPercent() {
		return this.m_verticalPercent;
	}

	public void SetVerticalPercent(float value) {
		this.m_verticalPercent = value;
	}

	protected CScaleGridMe m_vGrid = new CScaleGridMe();
	protected int m_workingAreaHeight;

	public CScaleGridMe GetVGrid() {
		return this.m_vGrid;
	}

	public void SetVGrid(CScaleGridMe value) {
		if (this.m_vGrid != null) {
			this.m_vGrid.Dispose();
		}
		this.m_vGrid = value;
	}

	public int GetWidth() {
		return this.m_bounds.right - this.m_bounds.left;
	}

	public int GetWorkingAreaHeight() {
		return this.m_workingAreaHeight;
	}

	public void SetWorkingAreaHeight(int value) {
		if (value >= 0) {
			this.m_workingAreaHeight = value;
		}
	}

	public void AddPlot(CPlotMe plot) {
		this.m_plots.add(plot);
	}

	public void AddShape(CBaseShapeMe shape) {
		this.m_shapes.add(shape);
	}

	public boolean ContainsShape(CBaseShapeMe shape) {
		ArrayList<CBaseShapeMe> shapesCopy = GetShapes(SortType.NONE);
		for (CBaseShapeMe bs : shapesCopy) {
			if (bs == shape) {
				return true;
			}
		}
		return false;
	}

	public void Dispose() {
		if (!this.m_isDisposed) {
			try {
				if (this.m_shapes != null) {
					for (CBaseShapeMe shape : this.m_shapes) {
						shape.Dispose();
					}
					this.m_shapes.clear();
				}
				if (this.m_plots != null) {
					for (CPlotMe plot : this.m_plots) {
						plot.Dispose();
					}
					this.m_plots.clear();
				}
				if (this.m_leftVScale != null) {
					this.m_leftVScale.Dispose();
				}
				if (this.m_rightVScale != null) {
					this.m_rightVScale.Dispose();
				}
				if (this.m_hScale != null) {
					this.m_hScale.Dispose();
				}
				if (this.m_hGrid != null) {
					this.m_hGrid.Dispose();
				}
				if (this.m_vGrid != null) {
					this.m_vGrid.Dispose();
				}
				if (this.m_crossLine != null) {
					this.m_crossLine.Dispose();
				}
				if (this.m_titleBar != null) {
					this.m_titleBar.Dispose();
				}
				if (this.m_selectArea != null) {
					this.m_selectArea.Dispose();
				}
				if (this.m_toolTip != null) {
					this.m_toolTip.Dispose();
				}
			} finally {
				this.m_isDisposed = true;
			}
		}
	}

	public ArrayList<CPlotMe> GetPlots(SortType sortType) {
		ArrayList<CPlotMe> plist = new ArrayList();
		int plotsSize = this.m_plots.size();
		for (int i = 0; i < plotsSize; i++) {
			plist.add(this.m_plots.get(i));
		}
		if (sortType == SortType.ASC) {
			Collections.sort(plist, new PlotZOrderCompareASC());
		} else if (sortType == SortType.DESC) {
			Collections.sort(plist, new PlotZOrderCompareDESC());
		}
		return plist;
	}

	public void GetProperty(String name, RefObject<String> value, RefObject<String> type) {
		if (name.equals("allowuserpaint")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(AllowUserPaint());
		} else if (name.equals("backcolor")) {
			type.argvalue = "color";
			value.argvalue = CStrMe.ConvertColorToStr(GetBackColor());
		} else if (name.equals("bordercolor")) {
			type.argvalue = "color";
			value.argvalue = CStrMe.ConvertColorToStr(GetBorderColor());
		} else if (name.equals("showselect")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(ShowSelect());
		}
	}

	public ArrayList<String> GetPropertyNames() {
		ArrayList<String> propertyNames = new ArrayList();
		propertyNames
				.addAll(Arrays.asList(new String[] { "AllowUserPaint", "BackColor", "BorderColor", "ShowSelect" }));
		return propertyNames;
	}

	public void OnPaint(CPaintMe paint, RECT rect) {
	}

	public void SetProperty(String name, String value) {
		if (name.equals("allowuserpaint")) {
			SetAllowUserPaint(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("backcolor")) {
			SetBackColor(CStrMe.ConvertStrToColor(value));
		} else if (name.equals("bordercolor")) {
			SetBorderColor(CStrMe.ConvertStrToColor(value));
		} else if (name.equals("showselect")) {
			SetShowSelect(CStrMe.ConvertStrToBool(value));
		}
	}

	public void RemovePlot(CPlotMe plot) {
		this.m_plots.remove(plot);
	}

	public void RemoveShape(CBaseShapeMe shape) {
		if (this.m_shapes.contains(shape)) {
			this.m_shapes.remove(shape);
		}
	}

	public ArrayList<CBaseShapeMe> GetShapes(SortType sortType) {
		ArrayList<CBaseShapeMe> slist = new ArrayList();
		int shapesSize = this.m_shapes.size();
		for (int i = 0; i < shapesSize; i++) {
			slist.add(this.m_shapes.get(i));
		}
		if (sortType == SortType.ASC) {
			Collections.sort(slist, new BaseShapeZOrderASC());
		} else if (sortType == SortType.DESC) {
			Collections.sort(slist, new BaseShapeZOrderDESC());
		}
		return slist;
	}

	public CVScaleMe GetVScale(AttachVScale attachVScale) {
		if (attachVScale == AttachVScale.Left) {
			return this.m_leftVScale;
		}

		return this.m_rightVScale;
	}
}
