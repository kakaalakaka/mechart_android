package com.melib.Chart.Plot;

import java.util.HashMap;
import com.melib.Chart.CTableMe;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class P034 extends CPlotBaseMe {
	public P034() {
		SetPlotType("SINE");
	}

	public ActionType GetAction() {
		ActionType action = ActionType.NO;
		if (this.m_marks.isEmpty()) {
			return action;
		}
		com.melib.Base.POINT mp = GetMouseOverPoint();
		float y1 = PY(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0))).GetValue());
		float y2 = PY(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(1))).GetValue());
		int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0))).GetKey());
		int eIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(1))).GetKey());
		float x1 = PX(bIndex);
		float x2 = PX(eIndex);
		if (SelectPoint(mp, x1, y1)) {
			action = ActionType.AT1;
			return action;
		}
		if (SelectPoint(mp, x2, y2)) {
			action = ActionType.AT2;
			return action;
		}
		if (SelectSine(mp, x1, y1, x2, y2)) {
			action = ActionType.MOVE;
		}
		return action;
	}

	public boolean OnCreate(com.melib.Base.POINT mp) {
		int rIndex = this.m_dataSource.GetRowsCount();
		if (rIndex > 0) {
			com.melib.Chart.CChartMe chart = GetChart();
			int mouseIndex = chart.GetMouseOverIndex();
			if ((mouseIndex >= 0) && (mouseIndex <= chart.GetLastVisibleIndex())) {
				int eIndex = mouseIndex;
				int bIndex = eIndex - chart.GetMaxVisibleRecord() / 10;
				if ((bIndex >= 0) && (eIndex != bIndex)) {
					double fDate = this.m_dataSource.GetXValue(bIndex);
					double sDate = this.m_dataSource.GetXValue(eIndex);
					this.m_marks.clear();
					double y = GetNumberValue(mp);
					this.m_marks
							.put(Integer.valueOf(0),
									new CPlotMarkMe(0, fDate,
											y + (this.m_div.GetVScale(this.m_attachVScale).GetVisibleMax()
													- this.m_div.GetVScale(this.m_attachVScale).GetVisibleMin())
													/ 4.0D));
					this.m_marks.put(Integer.valueOf(1), new CPlotMarkMe(1, sDate, y));
					return true;
				}
			}
		}
		return false;
	}

	public void OnMoveStart() {
		this.m_action = GetAction();
		this.m_startMarks.clear();
		this.m_startPoint = GetMouseOverPoint();
		if (this.m_action != ActionType.NO) {
			this.m_startMarks.put(Integer.valueOf(0), this.m_marks.get(Integer.valueOf(0)));
			this.m_startMarks.put(Integer.valueOf(1), this.m_marks.get(Integer.valueOf(1)));
		}
	}

	public void OnMoving() {
		com.melib.Base.POINT mp = GetMovingPoint();
		com.melib.Chart.CChartMe chart = GetChart();
		int mouseIndex = chart.GetMouseOverIndex();
		if (mouseIndex < 0) {
			mouseIndex = 0;
		}
		if (mouseIndex > chart.GetLastVisibleIndex()) {
			mouseIndex = chart.GetLastVisibleIndex();
		}
		int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0))).GetKey());
		int eIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(1))).GetKey());
		switch (this.m_action) {
		case MOVE:
			Move(mp);
			break;
		case AT1:
			if (mouseIndex < eIndex) {
				Resize(0);
			}
			break;
		case AT2:
			if (mouseIndex > bIndex) {
				Resize(1);
			}
			break;
		}

	}

	protected void Paint(com.melib.Base.CPaintMe paint, HashMap<Integer, CPlotMarkMe> pList, long lineColor) {
		if (pList.isEmpty()) {
			return;
		}
		double fValue = ((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetValue();
		double eValue = ((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetValue();
		int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetKey());
		int eIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetKey());
		int x1 = (int) PX(bIndex);
		float x2 = PX(eIndex);
		float y1 = PY(fValue);
		float y2 = PY(eValue);
		double f = 6.283185307179586D / ((x2 - x1) * 4.0F);
		if (x1 != x2) {
			int len = GetWorkingAreaWidth();
			if (len > 0) {
				com.melib.Base.POINT[] pf = new com.melib.Base.POINT[len];
				for (int i = 0; i < len; i++) {
					int x = -x1 + i;
					float y = (float) (0.5D * (y2 - y1) * Math.sin(x * f) * 2.0D);
					com.melib.Base.POINT pt = new com.melib.Base.POINT(x + x1, (int) (y + y1));
					pf[i] = pt;
				}
				DrawPolyline(paint, lineColor, this.m_lineWidth, this.m_lineStyle, pf);
			}
		}
		if (IsSelected()) {
			DrawSelect(paint, lineColor, x1, y1);
			DrawSelect(paint, lineColor, x2, y2);
		}
	}
}
