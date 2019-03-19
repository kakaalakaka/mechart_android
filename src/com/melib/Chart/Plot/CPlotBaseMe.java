package com.melib.Chart.Plot;

import java.util.ArrayList;
import java.util.HashMap;
import com.melib.Base.CMathLibMe;
import com.melib.Base.COLOR;
import com.melib.Base.CPaintMe;
import com.melib.Base.FONT;
import com.melib.Base.INativeBaseMe;
import com.melib.Base.POINT;
import com.melib.Base.RECT;
import com.melib.Base.RefObject;
import com.melib.Base.SIZE;
import com.melib.Chart.CDivMe;
import com.melib.Chart.CTableMe;
import com.melib.Chart.CCandleShapeMe;
import com.melib.Chart.CChartMe;
import com.melib.Chart.SelectedPoint;
import com.melib.Chart.CTitleBarMe;
import com.melib.Chart.CVScaleMe;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class CPlotBaseMe extends com.melib.Chart.CPlotMe {
	public CPlotBaseMe() {
		this.m_color = COLOR.ARGB(255, 255, 255);
		this.m_selectedColor = COLOR.ARGB(255, 255, 255);
	}

	protected void finalize() throws Throwable {
		Dispose();
	}

	protected ActionType m_action = ActionType.NO;

	protected CTableMe m_dataSource = null;

	protected boolean m_isPaintingGhost = false;

	protected HashMap<Integer, CPlotMarkMe> m_marks = new HashMap();

	protected int m_moveTimes = 0;

	protected HashMap<String, Integer> m_sourceFields = new HashMap();

	protected HashMap<Integer, CPlotMarkMe> m_startMarks = new HashMap();

	protected POINT m_startPoint = new POINT();

	protected com.melib.Chart.AttachVScale m_attachVScale = com.melib.Chart.AttachVScale.Left;
	protected long m_color;

	public com.melib.Chart.AttachVScale GetAttachVScale() {
		return this.m_attachVScale;
	}

	public void SetAttachVScale(com.melib.Chart.AttachVScale value) {
		this.m_attachVScale = value;
	}

	public long GetColor() {
		return this.m_color;
	}

	public void SetColor(long value) {
		this.m_color = value;
	}

	protected CDivMe m_div = null;

	public CDivMe GetDiv() {
		return this.m_div;
	}

	public void SetDiv(CDivMe value) {
		this.m_div = value;
		this.m_dataSource = this.m_div.GetChart().GetDataSource();
	}

	protected boolean m_drawGhost = true;

	public boolean GetDrawGhost() {
		return this.m_drawGhost;
	}

	public void SetDrawGhost(boolean value) {
		this.m_drawGhost = value;
	}

	protected boolean m_enabled = true;

	public boolean IsEnabled() {
		return this.m_enabled;
	}

	public void SetEnabled(boolean value) {
		if (!value) {
			this.m_selected = false;
		}
		this.m_enabled = value;
	}

	protected FONT m_font = new FONT();

	public FONT GetFont() {
		return this.m_font;
	}

	public void SetFont(FONT value) {
		this.m_font = value;
	}

	protected boolean m_isDisposed = false;

	public boolean IsDisposed() {
		return this.m_isDisposed;
	}

	public CChartMe GetChart() {
		return this.m_div.GetChart();
	}

	protected int m_lineStyle = 0;

	public int GetLineStyle() {
		return this.m_lineStyle;
	}

	public void SetLineStyle(int value) {
		this.m_lineStyle = value;
	}

	protected int m_lineWidth = 1;

	public int GetLineWidth() {
		return this.m_lineWidth;
	}

	public void SetLineWidth(int value) {
		this.m_lineWidth = value;
	}

	protected INativeBaseMe GetNative() {
		return this.m_div.GetChart().GetNative();
	}

	protected String m_plotType = "LINE";

	public String GetPlotType() {
		return this.m_plotType;
	}

	public void SetPlotType(String value) {
		this.m_plotType = value;
	}

	protected boolean m_selected = false;
	protected long m_selectedColor;

	public boolean IsSelected() {
		return this.m_selected;
	}

	public void SetSelected(boolean value) {
		this.m_selected = value;
	}

	public long GetSelectedColor() {
		return this.m_selectedColor;
	}

	public void SetSelectedColor(long value) {
		this.m_selectedColor = value;
	}

	protected SelectedPoint m_selectedPoint = SelectedPoint.Rectangle;
	protected String m_text;

	public SelectedPoint GetSelectedPoint() {
		return this.m_selectedPoint;
	}

	public void SetSelectedPoint(SelectedPoint value) {
		this.m_selectedPoint = value;
	}

	public String GetText() {
		return this.m_text;
	}

	public void SetText(String value) {
		this.m_text = value;
	}

	protected boolean m_visible = true;

	public boolean IsVisible() {
		return this.m_visible;
	}

	public void SetVisible(boolean value) {
		if (!value) {
			this.m_selected = false;
		}
		this.m_visible = value;
	}

	protected int GetWorkingAreaWidth() {
		return GetChart().GetWorkingAreaWidth();
	}

	protected int GetWorkingAreaHeight() {
		return this.m_div.GetWorkingAreaHeight();
	}

	protected int m_zOrder = 0;

	public int GetZOrder() {
		return this.m_zOrder;
	}

	public void SetZOrder(int value) {
		this.m_zOrder = value;
	}

	protected boolean CreatePoint(POINT mp) {
		int rIndex = this.m_dataSource.GetRowsCount();
		if (rIndex > 0) {
			CChartMe chart = GetChart();
			int mouseIndex = chart.GetIndex(mp);
			if ((mouseIndex >= chart.GetFirstVisibleIndex()) && (mouseIndex <= chart.GetLastVisibleIndex())) {
				double sDate = this.m_dataSource.GetXValue(mouseIndex);
				this.m_marks.clear();
				double y = GetNumberValue(mp);
				this.m_marks.put(Integer.valueOf(0), new CPlotMarkMe(0, sDate, y));
				return true;
			}
		}
		return false;
	}

	protected boolean Create2PointsA(POINT mp) {
		int rIndex = this.m_dataSource.GetRowsCount();
		if (rIndex > 0) {
			CChartMe chart = GetChart();
			int mouseIndex = chart.GetIndex(mp);
			if ((mouseIndex >= chart.GetFirstVisibleIndex()) && (mouseIndex <= chart.GetLastVisibleIndex())) {
				int eIndex = mouseIndex;
				int bIndex = eIndex - 1;
				if (bIndex >= 0) {
					double fDate = this.m_dataSource.GetXValue(bIndex);
					double sDate = this.m_dataSource.GetXValue(eIndex);
					this.m_marks.clear();
					double y = GetNumberValue(mp);
					this.m_marks.put(Integer.valueOf(0), new CPlotMarkMe(0, fDate, y));
					this.m_marks.put(Integer.valueOf(1), new CPlotMarkMe(1, sDate, y));
					return true;
				}
			}
		}
		return false;
	}

	protected boolean Create2PointsB(POINT mp) {
		int rIndex = this.m_dataSource.GetRowsCount();
		if (rIndex > 0) {
			CChartMe chart = GetChart();
			int mouseIndex = chart.GetIndex(mp);
			if ((mouseIndex >= chart.GetFirstVisibleIndex()) && (mouseIndex <= chart.GetLastVisibleIndex())) {
				double date = this.m_dataSource.GetXValue(mouseIndex);
				this.m_marks.clear();
				double y = GetNumberValue(mp);
				this.m_marks.put(Integer.valueOf(0), new CPlotMarkMe(0, date, y));
				this.m_marks.put(Integer.valueOf(1), new CPlotMarkMe(1, date, y));
				return true;
			}
		}
		return false;
	}

	protected boolean Create2CandlePoints(POINT mp) {
		CChartMe chart = GetChart();
		int rIndex = this.m_dataSource.GetRowsCount();
		if (rIndex > 0) {
			ArrayList<com.melib.Chart.CBaseShapeMe> shapesCopy = this.m_div.GetShapes(com.melib.Chart.SortType.DESC);
			for (com.melib.Chart.CBaseShapeMe bs : shapesCopy) {
				if (bs.IsVisible()) {
					CCandleShapeMe cs = (CCandleShapeMe) ((bs instanceof CCandleShapeMe) ? bs : null);
					if (cs != null) {
						int mouseIndex = chart.GetIndex(mp);
						if ((mouseIndex >= chart.GetFirstVisibleIndex())
								&& (mouseIndex <= chart.GetLastVisibleIndex())) {
							int eIndex = mouseIndex;
							int bIndex = eIndex - 1;
							if (bIndex >= 0) {
								double fDate = this.m_dataSource.GetXValue(bIndex);
								double sDate = this.m_dataSource.GetXValue(eIndex);
								this.m_marks.clear();
								double y = GetNumberValue(mp);
								this.m_marks.put(Integer.valueOf(0), new CPlotMarkMe(0, fDate, y));
								this.m_marks.put(Integer.valueOf(1), new CPlotMarkMe(1, sDate, y));
								this.m_sourceFields.put("CLOSE", Integer.valueOf(cs.GetCloseField()));
								this.m_sourceFields.put("OPEN", Integer.valueOf(cs.GetOpenField()));
								this.m_sourceFields.put("HIGH", Integer.valueOf(cs.GetHighField()));
								this.m_sourceFields.put("LOW", Integer.valueOf(cs.GetLowField()));
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	protected boolean Create3Points(POINT mp) {
		int rIndex = this.m_dataSource.GetRowsCount();
		if (rIndex > 0) {
			CChartMe chart = GetChart();
			int mouseIndex = chart.GetIndex(mp);
			if ((mouseIndex >= chart.GetFirstVisibleIndex()) && (mouseIndex <= chart.GetLastVisibleIndex())) {
				int eIndex = mouseIndex;
				int bIndex = eIndex - 1;
				if (bIndex >= 0) {
					double fDate = this.m_dataSource.GetXValue(bIndex);
					double sDate = this.m_dataSource.GetXValue(eIndex);
					this.m_marks.clear();
					double y = GetNumberValue(mp);
					this.m_marks.put(Integer.valueOf(0), new CPlotMarkMe(0, fDate, y));
					this.m_marks.put(Integer.valueOf(1), new CPlotMarkMe(1, sDate, y));
					if ((this.m_div.GetVScale(this.m_attachVScale) != null)
							&& (this.m_div.GetVScale(this.m_attachVScale).GetVisibleMax() != this.m_div
									.GetVScale(this.m_attachVScale).GetVisibleMin())) {
						this.m_marks
								.put(Integer.valueOf(2),
										new CPlotMarkMe(2, fDate,
												y + (this.m_div.GetVScale(this.m_attachVScale).GetVisibleMax()
														- this.m_div.GetVScale(this.m_attachVScale).GetVisibleMin())
														/ 4.0D));
					} else {
						this.m_marks.put(Integer.valueOf(2), new CPlotMarkMe(2, fDate, y));
					}
					return true;
				}
			}
		}
		return false;
	}

	protected void CreateCandlePoint(int pos, int index, int close) {
		if (index >= 0) {
			if (index > this.m_dataSource.GetRowsCount() - 1) {
				index = this.m_dataSource.GetRowsCount() - 1;
			}
			double date = this.m_dataSource.GetXValue(index);
			double yValue = 0.0D;
			if (!Double.isNaN(this.m_dataSource.Get2(index, close))) {
				yValue = this.m_dataSource.Get2(index, close);
			}
			this.m_marks.put(Integer.valueOf(pos), new CPlotMarkMe(pos, date, yValue));
		}
	}

	protected boolean Create4CandlePoints(POINT mp) {
		CChartMe chart = GetChart();
		int rIndex = this.m_dataSource.GetRowsCount();
		if (rIndex > 0) {
			ArrayList<com.melib.Chart.CBaseShapeMe> shapesCopy = this.m_div.GetShapes(com.melib.Chart.SortType.ASC);
			for (com.melib.Chart.CBaseShapeMe bs : shapesCopy) {
				if ((bs.IsVisible()) && ((bs instanceof CCandleShapeMe))) {
					CCandleShapeMe cs = (CCandleShapeMe) ((bs instanceof CCandleShapeMe) ? bs : null);
					int mouseIndex = chart.GetIndex(mp);
					if ((mouseIndex >= chart.GetFirstVisibleIndex()) && (mouseIndex <= chart.GetLastVisibleIndex())) {
						int closeField = cs.GetCloseField();
						CreateCandlePoint(0, mouseIndex, closeField);
						CreateCandlePoint(1, mouseIndex + 1, closeField);
						CreateCandlePoint(2, mouseIndex + 2, closeField);
						CreateCandlePoint(3, mouseIndex + 3, closeField);
						this.m_sourceFields.put("CLOSE", Integer.valueOf(closeField));
						this.m_sourceFields.put("HIGH", Integer.valueOf(cs.GetHighField()));
						this.m_sourceFields.put("LOW", Integer.valueOf(cs.GetLowField()));
						this.m_sourceFields.put("OPEN", Integer.valueOf(cs.GetOpenField()));
						return true;
					}
				}
			}
		}
		return false;
	}

	public void Dispose() {
		if (!this.m_isDisposed) {
			if (this.m_marks != null) {
				this.m_marks.clear();
			}
			if (this.m_startMarks != null) {
				this.m_startMarks.clear();
			}
			this.m_isDisposed = true;
		}
	}

	protected void DrawEllipse(CPaintMe paint, long dwPenColor, int width, int style, float left, float top,
			float right, float bottom) {
		left += GetPx();
		top += GetPy();
		right += GetPx();
		bottom += GetPy();
		RECT rect = new RECT(left, top, right, bottom);
		paint.DrawEllipse(dwPenColor, width, style, rect);
		if (paint.SupportTransparent()) {
			CChartMe chart = GetChart();
			POINT mp = chart.GetMousePoint();
			if ((!this.m_isPaintingGhost) && (mp.y >= this.m_div.GetTop()) && (mp.y <= this.m_div.GetBottom())
					&& ((chart.GetMovingPlot() == this) || ((chart == GetNative().GetHoveredControl())
							&& (!chart.IsOperating()) && (OnSelect())))) {
				int a = 0;
				int r = 0;
				int g = 0;
				int b = 0;
				RefObject<Integer> tempRef_a = new RefObject(Integer.valueOf(a));
				RefObject<Integer> tempRef_r = new RefObject(Integer.valueOf(r));
				RefObject<Integer> tempRef_g = new RefObject(Integer.valueOf(g));
				RefObject<Integer> tempRef_b = new RefObject(Integer.valueOf(b));
				COLOR.ToARGB(paint, dwPenColor, tempRef_a, tempRef_r, tempRef_g, tempRef_b);
				a = ((Integer) tempRef_a.argvalue).intValue();
				r = ((Integer) tempRef_r.argvalue).intValue();
				g = ((Integer) tempRef_g.argvalue).intValue();
				b = ((Integer) tempRef_b.argvalue).intValue();
				if (a == 255) {
					a = 50;
				}
				dwPenColor = COLOR.ARGB(a, r, g, b);
				width += 10;
				paint.DrawEllipse(dwPenColor, width, 0, rect);
			}
		}
	}

	protected void DrawLine(CPaintMe paint, long dwPenColor, int width, int style, float x1, float y1, float x2,
			float y2) {
		x1 += GetPx();
		y1 += GetPy();
		x2 += GetPx();
		y2 += GetPy();
		paint.DrawLine(dwPenColor, width, style, (int) x1, (int) y1, (int) x2, (int) y2);
		if (paint.SupportTransparent()) {
			CChartMe chart = GetChart();
			POINT mp = chart.GetMousePoint();
			if ((!this.m_isPaintingGhost) && (mp.y >= this.m_div.GetTop()) && (mp.y <= this.m_div.GetBottom())
					&& ((chart.GetMovingPlot() == this) || ((chart == GetNative().GetHoveredControl())
							&& (!chart.IsOperating()) && (OnSelect())))) {
				int a = 0;
				int r = 0;
				int g = 0;
				int b = 0;
				RefObject<Integer> tempRef_a = new RefObject(Integer.valueOf(a));
				RefObject<Integer> tempRef_r = new RefObject(Integer.valueOf(r));
				RefObject<Integer> tempRef_g = new RefObject(Integer.valueOf(g));
				RefObject<Integer> tempRef_b = new RefObject(Integer.valueOf(b));
				COLOR.ToARGB(paint, dwPenColor, tempRef_a, tempRef_r, tempRef_g, tempRef_b);
				a = ((Integer) tempRef_a.argvalue).intValue();
				r = ((Integer) tempRef_r.argvalue).intValue();
				g = ((Integer) tempRef_g.argvalue).intValue();
				b = ((Integer) tempRef_b.argvalue).intValue();
				if (a == 255) {
					a = 50;
				}
				dwPenColor = COLOR.ARGB(a, r, g, b);
				width += 10;
				paint.DrawLine(dwPenColor, width, 0, (int) x1, (int) y1, (int) x2, (int) y2);
			}
		}
	}

	protected void DrawRay(CPaintMe paint, long dwPenColor, int width, int style, float x1, float y1, float x2,
			float y2, float k, float b) {
		if ((k != 0.0F) || (b != 0.0F)) {
			float leftX = 0.0F;
			float leftY = leftX * k + b;
			float rightX = GetWorkingAreaWidth();
			float rightY = rightX * k + b;
			if (x1 >= x2) {
				DrawLine(paint, dwPenColor, width, style, leftX, leftY, x1, y1);
			} else {
				DrawLine(paint, dwPenColor, width, style, x1, y1, rightX, rightY);
			}

		} else if (y1 >= y2) {
			DrawLine(paint, dwPenColor, width, style, x1, y1, x1, 0.0F);
		} else {
			DrawLine(paint, dwPenColor, width, style, x1, y1, x1, GetWorkingAreaHeight());
		}
	}

	protected void DrawRect(CPaintMe paint, long dwPenColor, int width, int style, int left, int top, int right,
			int bottom) {
		left += GetPx();
		top += GetPy();
		right += GetPx();
		bottom += GetPy();
		RECT rect = new RECT(left, top, right, bottom);
		if (paint.SupportTransparent()) {
			paint.DrawRect(dwPenColor, width, style, rect);
			CChartMe chart = GetChart();
			POINT mp = chart.GetMousePoint();
			if ((!this.m_isPaintingGhost) && (mp.y >= this.m_div.GetTop()) && (mp.y <= this.m_div.GetBottom())
					&& ((chart.GetMovingPlot() == this) || ((chart == GetNative().GetHoveredControl())
							&& (!chart.IsOperating()) && (OnSelect())))) {
				int a = 0;
				int r = 0;
				int g = 0;
				int b = 0;
				RefObject<Integer> tempRef_a = new RefObject(Integer.valueOf(a));
				RefObject<Integer> tempRef_r = new RefObject(Integer.valueOf(r));
				RefObject<Integer> tempRef_g = new RefObject(Integer.valueOf(g));
				RefObject<Integer> tempRef_b = new RefObject(Integer.valueOf(b));
				COLOR.ToARGB(paint, dwPenColor, tempRef_a, tempRef_r, tempRef_g, tempRef_b);
				a = ((Integer) tempRef_a.argvalue).intValue();
				r = ((Integer) tempRef_r.argvalue).intValue();
				g = ((Integer) tempRef_g.argvalue).intValue();
				b = ((Integer) tempRef_b.argvalue).intValue();
				if (a == 255) {
					a = 50;
				}
				dwPenColor = COLOR.ARGB(a, r, g, b);
				width += 10;
				paint.DrawRect(dwPenColor, width, 0, rect);
			}
		}
	}

	protected void DrawRect(CPaintMe paint, long dwPenColor, int width, int style, float left, float top, float right,
			float bottom) {
		left += GetPx();
		top += GetPy();
		right += GetPx();
		bottom += GetPy();
		RECT rect = new RECT(left, top, right, bottom);
		paint.DrawRect(dwPenColor, width, style, rect);
		if (paint.SupportTransparent()) {
			CChartMe chart = GetChart();
			POINT mp = chart.GetMousePoint();
			if ((!this.m_isPaintingGhost) && (mp.y >= this.m_div.GetTop()) && (mp.y <= this.m_div.GetBottom())
					&& ((chart.GetMovingPlot() == this) || ((chart == GetNative().GetHoveredControl())
							&& (!chart.IsOperating()) && (OnSelect())))) {
				int a = 0;
				int r = 0;
				int g = 0;
				int b = 0;
				RefObject<Integer> tempRef_a = new RefObject(Integer.valueOf(a));
				RefObject<Integer> tempRef_r = new RefObject(Integer.valueOf(r));
				RefObject<Integer> tempRef_g = new RefObject(Integer.valueOf(g));
				RefObject<Integer> tempRef_b = new RefObject(Integer.valueOf(b));
				COLOR.ToARGB(paint, dwPenColor, tempRef_a, tempRef_r, tempRef_g, tempRef_b);
				a = ((Integer) tempRef_a.argvalue).intValue();
				r = ((Integer) tempRef_r.argvalue).intValue();
				g = ((Integer) tempRef_g.argvalue).intValue();
				b = ((Integer) tempRef_b.argvalue).intValue();
				if (a == 255) {
					a = 50;
				}
				dwPenColor = COLOR.ARGB(a, r, g, b);
				width += 10;
				paint.DrawRect(dwPenColor, width, 0, rect);
			}
		}
	}

	protected void DrawPolyline(CPaintMe paint, long dwPenColor, int width, int style, POINT[] points) {
		int px = GetPx();
		int py = GetPy();
		for (int i = 0; i < points.length; i++) {
			POINT point = new POINT(px + points[i].x, py + points[i].y);
			points[i] = point;
		}
		paint.DrawPolyline(dwPenColor, width, style, points);
		if (paint.SupportTransparent()) {
			CChartMe chart = GetChart();
			POINT mp = chart.GetMousePoint();
			if ((!this.m_isPaintingGhost) && (mp.y >= this.m_div.GetTop()) && (mp.y <= this.m_div.GetBottom())
					&& ((chart.GetMovingPlot() == this) || ((chart == GetNative().GetHoveredControl())
							&& (!chart.IsOperating()) && (OnSelect())))) {
				int a = 0;
				int r = 0;
				int g = 0;
				int b = 0;
				RefObject<Integer> tempRef_a = new RefObject(Integer.valueOf(a));
				RefObject<Integer> tempRef_r = new RefObject(Integer.valueOf(r));
				RefObject<Integer> tempRef_g = new RefObject(Integer.valueOf(g));
				RefObject<Integer> tempRef_b = new RefObject(Integer.valueOf(b));
				COLOR.ToARGB(paint, dwPenColor, tempRef_a, tempRef_r, tempRef_g, tempRef_b);
				a = ((Integer) tempRef_a.argvalue).intValue();
				r = ((Integer) tempRef_r.argvalue).intValue();
				g = ((Integer) tempRef_g.argvalue).intValue();
				b = ((Integer) tempRef_b.argvalue).intValue();
				if (a == 255) {
					a = 50;
				}
				dwPenColor = COLOR.ARGB(a, r, g, b);
				width += 10;
				paint.DrawPolyline(dwPenColor, width, 0, points);
			}
		}
	}

	protected void DrawSelect(CPaintMe paint, long dwPenColor, float x, float y) {
		x += GetPx();
		y += GetPy();
		int sub = this.m_lineWidth * 3;
		RECT rect = new RECT(x - sub, y - sub, x + sub, y + sub);
		if (GetSelectedPoint() == SelectedPoint.Ellipse) {
			paint.FillEllipse(dwPenColor, rect);
		} else if (GetSelectedPoint() == SelectedPoint.Rectangle) {
			paint.FillRect(dwPenColor, rect);
		}
	}

	protected void DrawText(CPaintMe paint, String text, long dwPenColor, FONT font, int x, int y) {
		x += GetPx();
		y += GetPy();
		SIZE tSize = paint.TextSize(text, font);
		RECT tRect = new RECT(x, y, x + tSize.cx, y + tSize.cy);
		paint.DrawText(text, dwPenColor, font, tRect);
	}

	protected void DrawText(CPaintMe paint, String text, long dwPenColor, FONT font, float x, float y) {
		x += GetPx();
		y += GetPy();
		SIZE tSize = paint.TextSize(text, font);
		RECT tRect = new RECT(x, y, x + tSize.cx, y + tSize.cy);
		paint.DrawText(text, dwPenColor, font, tRect);
	}

	protected void FillPolygon(CPaintMe paint, long dwPenColor, POINT[] points) {
		int px = GetPx();
		int py = GetPy();
		for (int i = 0; i < points.length; i++) {
			POINT point = new POINT(px + points[i].x, py + points[i].y);
			points[i] = point;
		}
		paint.FillPolygon(dwPenColor, points);
	}

	protected double[] GetCandleRange(HashMap<Integer, CPlotMarkMe> pList) {
		if ((pList.isEmpty()) || (this.m_sourceFields.isEmpty())) {
			return null;
		}
		if ((!this.m_sourceFields.containsKey("HIGH")) || (!this.m_sourceFields.containsKey("LOW"))) {
			return null;
		}
		int bRecord = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetKey());
		int eRecord = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetKey());
		double[] highlist = null;
		double[] lowlist = null;
		if (eRecord >= bRecord) {
			highlist = this.m_dataSource.DATA_ARRAY(((Integer) this.m_sourceFields.get("HIGH")).intValue(), eRecord,
					eRecord - bRecord + 1);
			lowlist = this.m_dataSource.DATA_ARRAY(((Integer) this.m_sourceFields.get("LOW")).intValue(), eRecord,
					eRecord - bRecord + 1);
		} else {
			highlist = this.m_dataSource.DATA_ARRAY(((Integer) this.m_sourceFields.get("HIGH")).intValue(), bRecord,
					bRecord - eRecord + 1);
			lowlist = this.m_dataSource.DATA_ARRAY(((Integer) this.m_sourceFields.get("LOW")).intValue(), bRecord,
					bRecord - eRecord + 1);
		}
		double nHigh = 0.0D;
		double nLow = 0.0D;
		nHigh = CMathLibMe.GetMax(highlist, highlist.length);
		nLow = CMathLibMe.GetMin(lowlist, lowlist.length);
		return new double[] { nHigh, nLow };
	}

	public ActionType GetAction() {
		return ActionType.NO;
	}

	protected POINT GetMouseOverPoint() {
		CChartMe chart = GetChart();
		POINT mp = chart.GetMousePoint();
		mp.x -= chart.GetLeftVScaleWidth();
		mp.y = (mp.y - this.m_div.GetTop() - this.m_div.GetTitleBar().GetHeight());
		return mp;
	}

	protected int GetIndex(POINT mp) {
		CChartMe chart = GetChart();
		mp.x += chart.GetLeftVScaleWidth();
		mp.y += this.m_div.GetTop() + this.m_div.GetTitleBar().GetHeight();
		return chart.GetIndex(mp);
	}

	protected float[] GetLineParams(CPlotMarkMe markA, CPlotMarkMe markB) {
		float y1 = PY(markA.GetValue());
		float y2 = PY(markB.GetValue());
		int bIndex = this.m_dataSource.GetRowIndex(markA.GetKey());
		int eIndex = this.m_dataSource.GetRowIndex(markB.GetKey());
		float x1 = PX(bIndex);
		float x2 = PX(eIndex);
		float a = 0.0F;
		float b = 0.0F;
		if (x2 - x1 != 0.0F) {
			RefObject<Float> tempRef_a = new RefObject(Float.valueOf(a));
			RefObject<Float> tempRef_b = new RefObject(Float.valueOf(b));
			CMathLibMe.M107(x1, y1, x2, y2, 0.0F, 0.0F, tempRef_a, tempRef_b);
			a = ((Float) tempRef_a.argvalue).floatValue();
			b = ((Float) tempRef_b.argvalue).floatValue();
			return new float[] { a, b };
		}

		return null;
	}

	protected double[] GetLRBandRange(HashMap<Integer, CPlotMarkMe> pList, float[] param) {
		if ((this.m_sourceFields != null) && (this.m_sourceFields.size() > 0)
				&& (this.m_sourceFields.containsKey("HIGH")) && (this.m_sourceFields.containsKey("LOW"))) {
			float a = param[0];
			float b = param[1];
			ArrayList<Double> upList = new ArrayList();
			ArrayList<Double> downList = new ArrayList();
			int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetKey());
			int eIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetKey());
			for (int i = bIndex; i <= eIndex; i++) {
				double high = this.m_dataSource.Get2(i, ((Integer) this.m_sourceFields.get("HIGH")).intValue());
				double low = this.m_dataSource.Get2(i, ((Integer) this.m_sourceFields.get("LOW")).intValue());
				if ((!Double.isNaN(high)) && (!Double.isNaN(low))) {
					double midValue = (i - bIndex + 1) * a + b;
					upList.add(Double.valueOf(high - midValue));
					downList.add(Double.valueOf(midValue - low));
				}
			}
			int upListSize = upList.size();
			double[] upListArray = new double[upListSize];
			for (int i = 0; i < upListSize; i++) {
				upListArray[i] = ((Double) upList.get(i)).doubleValue();
			}
			double upSubValue = CMathLibMe.GetMax(upListArray, upListSize);
			int downListSize = downList.size();
			double[] downListArray = new double[downListSize];
			for (int i = 0; i < downListSize; i++) {
				downListArray[i] = ((Double) downList.get(i)).doubleValue();
			}
			double downSubValue = CMathLibMe.GetMax(downListArray, downList.size());
			return new double[] { upSubValue, downSubValue };
		}
		return null;
	}

	protected float[] GetLRParams(HashMap<Integer, CPlotMarkMe> marks) {
		if ((this.m_sourceFields != null) && (this.m_sourceFields.containsKey("CLOSE"))) {
			int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) marks.get(Integer.valueOf(0))).GetKey());
			int eIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) marks.get(Integer.valueOf(1))).GetKey());
			if ((bIndex != -1) && (eIndex != -1)) {
				ArrayList<Double> closeVList = new ArrayList();
				for (int i = bIndex; i <= eIndex; i++) {
					double value = this.m_dataSource.Get2(i, ((Integer) this.m_sourceFields.get("CLOSE")).intValue());
					if (!Double.isNaN(value)) {
						closeVList.add(Double.valueOf(value));
					}
				}
				if (closeVList.size() > 0) {
					float a = 0.0F;
					float b = 0.0F;
					RefObject<Float> tempRef_a = new RefObject(Float.valueOf(a));
					RefObject<Float> tempRef_b = new RefObject(Float.valueOf(b));
					int closeVListSize = closeVList.size();
					double[] list = new double[closeVListSize];
					for (int i = 0; i < closeVListSize; i++) {
						list[i] = ((Double) closeVList.get(i)).doubleValue();
					}
					CMathLibMe.M014(list, closeVListSize, tempRef_a, tempRef_b);
					a = ((Float) tempRef_a.argvalue).floatValue();
					b = ((Float) tempRef_b.argvalue).floatValue();
					return new float[] { a, b };
				}
			}
		}
		return null;
	}

	protected POINT GetMovingPoint() {
		POINT mp = GetMouseOverPoint();
		if (mp.x < 0) {
			mp.x = 0;
		} else if (mp.x > GetWorkingAreaWidth()) {
			mp.x = GetWorkingAreaWidth();
		}
		if (mp.y < 0) {
			mp.y = 0;
		} else if (mp.y > GetWorkingAreaHeight()) {
			mp.y = GetWorkingAreaHeight();
		}
		return mp;
	}

	protected double GetNumberValue(POINT mp) {
		CChartMe chart = GetChart();
		mp.x += chart.GetLeftVScaleWidth();
		mp.y += this.m_div.GetTop() + this.m_div.GetTitleBar().GetHeight();
		return chart.GetNumberValue(this.m_div, mp, this.m_attachVScale);
	}

	protected int GetPx() {
		CChartMe chart = GetChart();
		return chart.GetLeftVScaleWidth();
	}

	protected int GetPy() {
		return this.m_div.GetTitleBar().GetHeight();
	}

	protected RECT GetRectangle(CPlotMarkMe markA, CPlotMarkMe markB) {
		double aValue = markA.GetValue();
		double bValue = markB.GetValue();
		int aIndex = this.m_dataSource.GetRowIndex(markA.GetKey());
		int bIndex = this.m_dataSource.GetRowIndex(markB.GetKey());
		float x = PX(aIndex);
		float y = PY(aValue);
		float xS = PX(bIndex);
		float yS = PY(bValue);
		float width = Math.abs(xS - x);
		if (width < 4.0F) {
			width = 4.0F;
		}
		float height = Math.abs(yS - y);
		if (height < 4.0F) {
			height = 4.0F;
		}
		float rX = x <= xS ? x : xS;
		float rY = y <= yS ? y : yS;
		return new RECT(rX, rY, rX + width, rY + height);
	}

	protected float[] GoldenRatioParams(double value1, double value2) {
		float y1 = PY(value1);
		float y2 = PY(value2);
		float y0 = 0.0F;
		float yA = 0.0F;
		float yB = 0.0F;
		float yC = 0.0F;
		float yD = 0.0F;
		float y100 = 0.0F;
		y0 = y1;
		yA = y1 <= y2 ? y1 + (y2 - y1) * 0.236F : y2 + (y1 - y2) * 0.764F;
		yB = y1 <= y2 ? y1 + (y2 - y1) * 0.382F : y2 + (y1 - y2) * 0.61800003F;
		yC = y1 <= y2 ? y1 + (y2 - y1) * 0.5F : y2 + (y1 - y2) * 0.5F;
		yD = y1 <= y2 ? y1 + (y2 - y1) * 0.618F : y2 + (y1 - y2) * 0.38200003F;
		y100 = y2;
		return new float[] { y0, yA, yB, yC, yD, y100 };
	}

	protected boolean HLinesSelect(float[] param, int length) {
		POINT mp = GetMouseOverPoint();
		float top = 0.0F;
		float bottom = GetWorkingAreaHeight();
		if ((mp.y >= top) && (mp.y <= bottom)) {
			for (float p : param) {
				if ((mp.y >= p - this.m_lineWidth * 2.5F) && (mp.y <= p + this.m_lineWidth * 2.5F)) {
					return true;
				}
			}
		}
		return false;
	}

	protected void Move(POINT mp) {
		CVScaleMe vScale = this.m_div.GetVScale(this.m_attachVScale);
		int startMarkSize = this.m_startMarks.size();
		for (int i = 0; i < startMarkSize; i++) {
			int newIndex = 0;
			double yAddValue = 0.0D;
			int startIndex = this.m_dataSource
					.GetRowIndex(((CPlotMarkMe) this.m_startMarks.get(Integer.valueOf(i))).GetKey());
			RefObject<Double> tempRef_yAddValue = new RefObject(Double.valueOf(yAddValue));
			RefObject<Integer> tempRef_newIndex = new RefObject(Integer.valueOf(newIndex));
			MovePlot(mp.y, this.m_startPoint.y, startIndex, GetIndex(this.m_startPoint), GetIndex(mp),
					GetWorkingAreaHeight(), vScale.GetVisibleMax(), vScale.GetVisibleMin(),
					this.m_dataSource.GetRowsCount(), tempRef_yAddValue, tempRef_newIndex);
			yAddValue = ((Double) tempRef_yAddValue.argvalue).doubleValue();
			newIndex = ((Integer) tempRef_newIndex.argvalue).intValue();
			if (vScale.IsReverse()) {
				this.m_marks.put(Integer.valueOf(i), new CPlotMarkMe(i, this.m_dataSource.GetXValue(newIndex),
						((CPlotMarkMe) this.m_startMarks.get(Integer.valueOf(i))).GetValue() - yAddValue));
			} else {
				this.m_marks.put(Integer.valueOf(i), new CPlotMarkMe(i, this.m_dataSource.GetXValue(newIndex),
						((CPlotMarkMe) this.m_startMarks.get(Integer.valueOf(i))).GetValue() + yAddValue));
			}
		}
	}

	private void MovePlot(float mouseY, float startY, int startIndex, int mouseBeginIndex, int mouseEndIndex,
			float pureV, double max, double min, int dataCount, RefObject<Double> yAddValue,
			RefObject<Integer> newIndex) {
		float subY = mouseY - startY;
		yAddValue.argvalue = Double.valueOf((min - max) * subY / pureV);
		newIndex.argvalue = Integer.valueOf(startIndex + (mouseEndIndex - mouseBeginIndex));
		if (((Integer) newIndex.argvalue).intValue() < 0) {
			newIndex.argvalue = Integer.valueOf(0);
		} else if (((Integer) newIndex.argvalue).intValue() > dataCount - 1) {
			newIndex.argvalue = Integer.valueOf(dataCount - 1);
		}
	}

	public boolean OnCreate(POINT mp) {
		return false;
	}

	public void OnMoveEnd() {
	}

	public void OnMoveStart() {
	}

	public void OnMoving() {
		POINT mp = GetMovingPoint();
		switch (this.m_action) {
		case MOVE:
			Move(mp);
			break;
		case AT1:
			Resize(0);
			break;
		case AT2:
			Resize(1);
			break;
		case AT3:
			Resize(2);
			break;
		case AT4:
			Resize(3);
		}

	}

	public void OnPaint(CPaintMe paint) {
		CChartMe chart = GetChart();
		POINT mp = chart.GetMousePoint();
		if ((mp.y >= this.m_div.GetTop()) && (mp.y <= this.m_div.GetBottom()) && ((chart.GetMovingPlot() == this)
				|| ((chart == GetNative().GetHoveredControl()) && (!chart.IsOperating()) && (OnSelect())))) {
			Paint(paint, this.m_marks, this.m_selectedColor);
		} else {
			Paint(paint, this.m_marks, this.m_color);
		}
	}

	public void OnPaintGhost(CPaintMe paint) {
		this.m_isPaintingGhost = true;
		Paint(paint, this.m_startMarks, this.m_color);
		this.m_isPaintingGhost = false;
	}

	public boolean OnSelect() {
		ActionType action = GetAction();
		if (action != ActionType.NO) {
			return true;
		}

		return false;
	}

	protected void Paint(CPaintMe paint, HashMap<Integer, CPlotMarkMe> pList, long lineColor) {
	}

	protected float PX(int index) {
		CChartMe chart = GetChart();
		float x = chart.GetX(index);
		return x - chart.GetLeftVScaleWidth();
	}

	protected float PY(double value) {
		CChartMe chart = GetChart();
		float y = chart.GetY(this.m_div, value, this.m_attachVScale);
		return y - this.m_div.GetTitleBar().GetHeight();
	}

	protected float PX(float x) {
		CChartMe chart = GetChart();
		return x - chart.GetLeftVScaleWidth();
	}

	protected float PY(float y) {
		return y - this.m_div.GetTop() - this.m_div.GetTitleBar().GetHeight();
	}

	public void Render(CPaintMe paint) {
		CChartMe chart = GetChart();
		if ((this.m_drawGhost) && (chart.GetMovingPlot() != null) && (chart.GetMovingPlot() == this)) {
			OnPaintGhost(paint);
		}
		OnPaint(paint);
	}

	protected void Resize(int index) {
		CChartMe chart = GetChart();
		int mouseIndex = chart.GetMouseOverIndex();
		double y = GetNumberValue(GetMovingPoint());
		if (mouseIndex < 0) {
			mouseIndex = 0;
		}
		if (mouseIndex > chart.GetLastVisibleIndex()) {
			mouseIndex = chart.GetLastVisibleIndex();
		}
		this.m_marks.put(Integer.valueOf(index), new CPlotMarkMe(index, this.m_dataSource.GetXValue(mouseIndex), y));
	}

	protected void Resize(POINT mp, POINT oppositePoint) {
		double sValue = GetNumberValue(new POINT(oppositePoint.x, oppositePoint.y));
		double eValue = GetNumberValue(mp);
		int iS = GetIndex(new POINT(oppositePoint.x, oppositePoint.y));
		int iE = GetIndex(mp);
		double topValue = 0.0D;
		double bottomValue = 0.0D;
		CVScaleMe vScale = this.m_div.GetVScale(this.m_attachVScale);
		if (sValue >= eValue) {
			if (vScale.IsReverse()) {
				topValue = eValue;
				bottomValue = sValue;
			} else {
				topValue = sValue;
				bottomValue = eValue;
			}

		} else if (vScale.IsReverse()) {
			topValue = sValue;
			bottomValue = eValue;
		} else {
			topValue = eValue;
			bottomValue = sValue;
		}

		double sDate = 0.0D;
		double eDate = 0.0D;
		if (iS < 0) {
			iS = 0;
		} else if (iS > this.m_dataSource.GetRowsCount() - 1) {
			iS = this.m_dataSource.GetRowsCount() - 1;
		}
		if (iE < 0) {
			iE = 0;
		} else if (iE > this.m_dataSource.GetRowsCount() - 1) {
			iE = this.m_dataSource.GetRowsCount() - 1;
		}
		if (iS >= iE) {
			sDate = this.m_dataSource.GetXValue(iE);
			eDate = this.m_dataSource.GetXValue(iS);
		} else {
			sDate = this.m_dataSource.GetXValue(iS);
			eDate = this.m_dataSource.GetXValue(iE);
		}
		this.m_marks.put(Integer.valueOf(0), new CPlotMarkMe(0, sDate, topValue));
		this.m_marks.put(Integer.valueOf(1), new CPlotMarkMe(1, eDate, bottomValue));
	}

	protected boolean SelectPoint(POINT mp, float x, float y) {
		if ((mp.x >= x - this.m_lineWidth * 6) && (mp.x <= x + this.m_lineWidth * 6)
				&& (mp.y >= y - this.m_lineWidth * 6) && (mp.y <= y + this.m_lineWidth * 6)) {
			return true;
		}
		return false;
	}

	protected boolean SelectLine(POINT mp, float x, float k, float b) {
		if ((k != 0.0F) || (b != 0.0F)) {
			if ((mp.y / (mp.x * k + b) >= 0.95D) && (mp.y / (mp.x * k + b) <= 1.05D)) {
				return true;
			}

		} else if ((mp.x >= x - this.m_lineWidth * 5) && (mp.x <= x + this.m_lineWidth * 5)) {
			return true;
		}

		return false;
	}

	protected boolean SelectLine(POINT mp, float x1, float y1, float x2, float y2) {
		float k = 0.0F;
		float b = 0.0F;
		RefObject<Float> tempRef_k = new RefObject(Float.valueOf(k));
		RefObject<Float> tempRef_b = new RefObject(Float.valueOf(b));
		CMathLibMe.M107(x1, y1, x2, y2, 0.0F, 0.0F, tempRef_k, tempRef_b);
		k = ((Float) tempRef_k.argvalue).floatValue();
		b = ((Float) tempRef_b.argvalue).floatValue();
		return SelectLine(mp, x1, k, b);
	}

	protected boolean SelectRay(POINT mp, float x1, float y1, float x2, float y2, RefObject<Float> k,
			RefObject<Float> b) {
		CMathLibMe.M107(x1, y1, x2, y2, 0.0F, 0.0F, k, b);
		if ((((Float) k.argvalue).floatValue() != 0.0F) || (((Float) b.argvalue).floatValue() != 0.0F)) {
			if ((mp.y / (mp.x * ((Float) k.argvalue).floatValue() + ((Float) b.argvalue).floatValue()) >= 0.95D)
					&& (mp.y / (mp.x * ((Float) k.argvalue).floatValue()
							+ ((Float) b.argvalue).floatValue()) <= 1.05D)) {
				if (x1 >= x2) {
					if (mp.x > x1 + 5.0F) {
						return false;
					}
				} else if (x1 < x2) {
					if (mp.x < x1 - 5.0F) {
						return false;
					}
				}
				return true;
			}

		} else if ((mp.x >= x1 - this.m_lineWidth * 5) && (mp.x <= x1 + this.m_lineWidth * 5)) {
			if (y1 >= y2) {
				if (mp.y <= y1 - 5.0F) {
					return true;
				}

			} else if (mp.y >= y1 - 5.0F) {
				return true;
			}
		}

		return false;
	}

	protected boolean SelectRay(POINT mp, float x1, float y1, float x2, float y2) {
		float k = 0.0F;
		float b = 0.0F;
		RefObject<Float> tempRef_k = new RefObject(Float.valueOf(k));
		RefObject<Float> tempRef_b = new RefObject(Float.valueOf(b));
		boolean tempVar = SelectRay(mp, x1, y1, x2, y2, tempRef_k, tempRef_b);
		k = ((Float) tempRef_k.argvalue).floatValue();
		b = ((Float) tempRef_b.argvalue).floatValue();
		return tempVar;
	}

	protected ActionType SelectRect(POINT mp, CPlotMarkMe markA, CPlotMarkMe markB) {
		RECT rect = GetRectangle(markA, markB);
		int x1 = rect.left;
		int y1 = rect.top;
		int x2 = rect.right;
		int y2 = rect.top;
		int x3 = rect.left;
		int y3 = rect.bottom;
		int x4 = rect.right;
		int y4 = rect.bottom;
		if (SelectPoint(mp, x1, y1)) {
			return ActionType.AT1;
		}
		if (SelectPoint(mp, x2, y2)) {
			return ActionType.AT2;
		}
		if (SelectPoint(mp, x3, y3)) {
			return ActionType.AT3;
		}
		if (SelectPoint(mp, x4, y4)) {
			return ActionType.AT4;
		}

		int sub = (int) (this.m_lineWidth * 2.5D);
		RECT bigRect = new RECT(rect.left - sub, rect.top - sub, rect.right + sub, rect.bottom + sub);
		if ((mp.x >= bigRect.left) && (mp.x <= bigRect.right) && (mp.y >= bigRect.top) && (mp.y <= bigRect.bottom)) {
			if ((rect.right - rect.left <= 4) || (rect.bottom - rect.top <= 4)) {
				return ActionType.MOVE;
			}

			RECT smallRect = new RECT(rect.left + sub, rect.top + sub, rect.right - sub, rect.bottom - sub);
			if ((mp.x < smallRect.left) || (mp.x > smallRect.right) || (mp.y < smallRect.top)
					|| (mp.y > smallRect.bottom)) {
				return ActionType.MOVE;
			}
		}

		return ActionType.NO;
	}

	protected boolean SelectSegment(POINT mp, float x1, float y1, float x2, float y2) {
		float k = 0.0F;
		float b = 0.0F;
		RefObject<Float> tempRef_k = new RefObject(Float.valueOf(k));
		RefObject<Float> tempRef_b = new RefObject(Float.valueOf(b));
		CMathLibMe.M107(x1, y1, x2, y2, 0.0F, 0.0F, tempRef_k, tempRef_b);
		k = ((Float) tempRef_k.argvalue).floatValue();
		b = ((Float) tempRef_b.argvalue).floatValue();
		float smallX = x1 <= x2 ? x1 : x2;
		float smallY = y1 <= y2 ? y1 : y2;
		float bigX = x1 > x2 ? x1 : x2;
		float bigY = y1 > y2 ? y1 : y2;
		if ((mp.x >= smallX - 2.0F) && (mp.x <= bigX + 2.0F) && (mp.y >= smallY - 2.0F) && (mp.y <= bigY + 2.0F)) {
			if ((k != 0.0F) || (b != 0.0F)) {
				if ((mp.y / (mp.x * k + b) >= 0.95D) && (mp.y / (mp.x * k + b) <= 1.05D)) {
					return true;
				}

			} else if ((mp.x >= x1 - this.m_lineWidth * 5) && (mp.x <= x1 + this.m_lineWidth * 5)) {
				return true;
			}
		}

		return false;
	}

	protected boolean SelectSine(POINT mp, float x1, float y1, float x2, float y2) {
		double f = 6.283185307179586D / ((x2 - x1) * 4.0F);
		int len = GetWorkingAreaWidth();
		if (len > 0) {
			float lastX = 0.0F;
			float lastY = 0.0F;
			for (int i = 0; i < len; i++) {
				float x = -x1 + i;
				float y = (float) (0.5D * (y2 - y1) * Math.sin(x * f) * 2.0D);
				float px = x + x1;
				float py = y + y1;
				if (i == 0) {
					if (SelectPoint(mp, px, py)) {
						return true;
					}
				} else {
					float rectLeft = lastX - 2.0F;
					float rectTop = lastY <= py ? lastY : py - 2.0F;
					float rectRight = rectLeft + Math.abs(px - lastX) + 4.0F;
					float rectBottom = rectTop + Math.abs(py - lastY) + 4.0F;
					if ((mp.x >= rectLeft) && (mp.x <= rectRight) && (mp.y >= rectTop) && (mp.y <= rectBottom)) {
						return true;
					}
				}
				lastX = px;
				lastY = py;
			}
		}
		return false;
	}

	protected boolean SelectTriangle(POINT mp, float x1, float y1, float x2, float y2, float x3, float y3) {
		boolean selected = SelectSegment(mp, x1, y1, x2, y2);
		if (selected) {
			return true;
		}
		selected = SelectSegment(mp, x1, y1, x3, y3);
		if (selected) {
			return true;
		}
		selected = SelectSegment(mp, x2, y2, x3, y3);
		if (selected) {
			return true;
		}
		return false;
	}

	protected SIZE TextSize(CPaintMe paint, String text, FONT font) {
		return paint.TextSize(text, font);
	}
}
