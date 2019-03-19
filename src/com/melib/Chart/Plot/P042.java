package com.melib.Chart.Plot;

import java.util.HashMap;
import com.melib.Chart.CTableMe;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class P042 extends CPlotBaseMe {
	public P042() {
		SetPlotType("TRIANGLE");
	}

	public ActionType GetAction() {
		ActionType action = ActionType.NO;
		if (this.m_marks.isEmpty()) {
			return action;
		}
		float y1 = PY(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0))).GetValue());
		float y2 = PY(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(1))).GetValue());
		float y3 = PY(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(2))).GetValue());
		int aIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0))).GetKey());
		int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(1))).GetKey());
		int cIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(2))).GetKey());
		float x1 = PX(aIndex);
		float x2 = PX(bIndex);
		float x3 = PX(cIndex);
		com.melib.Base.POINT mp = GetMouseOverPoint();
		if (this.m_moveTimes == 1) {
			action = ActionType.AT3;
			return action;
		}

		if ((SelectPoint(mp, x1, y1)) || (this.m_moveTimes == 1)) {
			action = ActionType.AT1;
			return action;
		}
		if (SelectPoint(mp, x2, y2)) {
			action = ActionType.AT2;
			return action;
		}
		if (SelectPoint(mp, x3, y3)) {
			action = ActionType.AT3;
			return action;
		}

		if (SelectTriangle(mp, x1, y1, x2, y2, x3, y3)) {
			action = ActionType.MOVE;
		}
		return action;
	}

	public boolean OnCreate(com.melib.Base.POINT mp) {
		int rIndex = this.m_dataSource.GetRowsCount();
		if (rIndex > 0) {
			int currentIndex = GetIndex(mp);
			double y = GetNumberValue(mp);
			double date = this.m_dataSource.GetXValue(currentIndex);
			this.m_marks.clear();
			this.m_marks.put(Integer.valueOf(0), new CPlotMarkMe(0, date, y));
			int si = currentIndex + 10;
			com.melib.Chart.CChartMe chart = GetChart();
			if (si > chart.GetLastVisibleIndex()) {
				si = chart.GetLastVisibleIndex();
			}
			this.m_marks.put(Integer.valueOf(1), new CPlotMarkMe(1, this.m_dataSource.GetXValue(si), y));
			this.m_marks.put(Integer.valueOf(2), new CPlotMarkMe(2, date, y));
			return true;
		}
		return false;
	}

	public void OnMoveStart() {
		this.m_moveTimes += 1;
		this.m_action = GetAction();
		this.m_startMarks.clear();
		this.m_startPoint = GetMouseOverPoint();
		if (this.m_action != ActionType.NO) {
			this.m_startMarks.put(Integer.valueOf(0), this.m_marks.get(Integer.valueOf(0)));
			this.m_startMarks.put(Integer.valueOf(1), this.m_marks.get(Integer.valueOf(1)));
			this.m_startMarks.put(Integer.valueOf(2), this.m_marks.get(Integer.valueOf(2)));
		}
	}

	public void OnPaintGhost(com.melib.Base.CPaintMe paint) {
		if (this.m_moveTimes > 1) {
			Paint(paint, this.m_startMarks, GetSelectedColor());
		}
	}

	protected void Paint(com.melib.Base.CPaintMe paint, HashMap<Integer, CPlotMarkMe> pList, long lineColor) {
		if (pList.isEmpty()) {
			return;
		}
		float y1 = PY(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetValue());
		float y2 = PY(((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetValue());
		float y3 = PY(((CPlotMarkMe) pList.get(Integer.valueOf(2))).GetValue());
		int aIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetKey());
		int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetKey());
		int cIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(2))).GetKey());
		float x1 = PX(aIndex);
		float x2 = PX(bIndex);
		float x3 = PX(cIndex);
		DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x1, y1, x2, y2);
		DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x1, y1, x3, y3);
		DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x2, y2, x3, y3);
		if ((IsSelected()) || ((x1 == x2) && (x2 == x3) && (y1 == y2) && (y2 == y3))) {
			DrawSelect(paint, lineColor, x1, y1);
			DrawSelect(paint, lineColor, x2, y2);
			DrawSelect(paint, lineColor, x3, y3);
		}
	}
}
