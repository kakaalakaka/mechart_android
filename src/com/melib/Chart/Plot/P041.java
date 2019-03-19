package com.melib.Chart.Plot;

import java.util.HashMap;
import com.melib.Base.POINT;
import com.melib.Chart.CChartMe;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class P041 extends CPlotBaseMe {
	public P041() {
		SetPlotType("TIMERULER");
	}

	public ActionType GetAction() {
		ActionType action = ActionType.NO;
		if (this.m_marks.isEmpty()) {
			return action;
		}
		POINT mp = GetMouseOverPoint();
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
		double[] param = GetTimeRulerParams(this.m_marks);
		float yBHigh = PY(param[0]);
		float yBLow = PY(param[1]);
		float yEHigh = PY(param[2]);
		float yELow = PY(param[3]);
		if (y1 < yBHigh) {
			yBHigh = y1;
		}
		if (y1 > yBLow) {
			yBLow = y1;
		}
		if (y2 < yEHigh) {
			yEHigh = y2;
		}
		if (y2 > yELow) {
			yELow = y2;
		}
		if ((mp.x >= x1 - this.m_lineWidth * 2.5F) && (mp.x <= x1 + this.m_lineWidth * 2.5F)) {
			if ((mp.y >= yBHigh - 2.0F) && (mp.y <= yBLow + 2.0F)) {
				action = ActionType.MOVE;
				return action;
			}
		}
		if ((mp.x >= x2 - this.m_lineWidth * 2.5F) && (mp.x <= x2 + this.m_lineWidth * 2.5F)) {
			if ((mp.y >= yEHigh - this.m_lineWidth * 2.5F) && (mp.y <= yELow + this.m_lineWidth * 2.5F)) {
				action = ActionType.MOVE;
				return action;
			}
		}
		if ((mp.y >= y1 - this.m_lineWidth * 2.5F) && (mp.y <= y1 + this.m_lineWidth * 2.5F)) {
			float bigX = x1 >= x2 ? x1 : x2;
			float smallX = x1 < x2 ? x1 : x2;
			if ((mp.x >= smallX - this.m_lineWidth * 2.5F) && (mp.x <= bigX + this.m_lineWidth * 2.5F)) {
				action = ActionType.MOVE;
				return action;
			}
		}
		return action;
	}

	private double[] GetTimeRulerParams(HashMap<Integer, CPlotMarkMe> pList) {
		if (pList.isEmpty()) {
			return null;
		}
		CChartMe chart = GetChart();
		int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetKey());
		int eIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetKey());
		double bHigh = chart.DivMaxOrMin(bIndex, this.m_div, 0);
		double bLow = chart.DivMaxOrMin(bIndex, this.m_div, 1);
		double eHigh = chart.DivMaxOrMin(eIndex, this.m_div, 0);
		double eLow = chart.DivMaxOrMin(eIndex, this.m_div, 1);
		return new double[] { bHigh, bLow, eHigh, eLow };
	}

	public boolean OnCreate(POINT mp) {
		return Create2PointsB(mp);
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
		POINT mp = GetMovingPoint();
		CChartMe chart = GetChart();
		int mouseIndex = chart.GetMouseOverIndex();
		double y = GetNumberValue(mp);
		if (mouseIndex < 0) {
			mouseIndex = 0;
		}
		if (mouseIndex > chart.GetLastVisibleIndex()) {
			mouseIndex = chart.GetLastVisibleIndex();
		}
		switch (this.m_action) {
		case MOVE:
			Move(mp);
			break;
		case AT1:
			this.m_marks.put(Integer.valueOf(0), new CPlotMarkMe(0, this.m_dataSource.GetXValue(mouseIndex), y));
			this.m_marks.put(Integer.valueOf(1),
					new CPlotMarkMe(1, ((CPlotMarkMe) this.m_marks.get(Integer.valueOf(1))).GetKey(), y));
			break;
		case AT2:
			this.m_marks.put(Integer.valueOf(1), new CPlotMarkMe(1, this.m_dataSource.GetXValue(mouseIndex), y));
			this.m_marks.put(Integer.valueOf(0),
					new CPlotMarkMe(0, ((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0))).GetKey(), y));
		}

	}

	protected void Paint(com.melib.Base.CPaintMe paint, HashMap<Integer, CPlotMarkMe> pList, long lineColor) {
		if (pList.isEmpty()) {
			return;
		}
		float y1 = PY(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetValue());
		float y2 = PY(((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetValue());
		int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetKey());
		int eIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetKey());
		float x1 = PX(bIndex);
		float x2 = PX(eIndex);
		double[] param = GetTimeRulerParams(pList);
		float yBHigh = PY(param[0]);
		float yBLow = PY(param[1]);
		float yEHigh = PY(param[2]);
		float yELow = PY(param[3]);
		if (y1 < yBHigh) {
			yBHigh = y1;
		}
		if (y1 > yBLow) {
			yBLow = y1;
		}
		if (y2 < yEHigh) {
			yEHigh = y2;
		}
		if (y2 > yELow) {
			yELow = y2;
		}
		DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x1, yBHigh, x1, yBLow);
		DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x2, yEHigh, x2, yELow);
		int subRecord = Math.abs(eIndex - bIndex) + 1;
		String recordStr = new Integer(subRecord).toString() + "(T)";
		com.melib.Base.SIZE sizeF = TextSize(paint, recordStr, this.m_font);
		DrawText(paint, recordStr, lineColor, this.m_font, (x2 + x1) / 2.0F - sizeF.cx / 2, y1 - sizeF.cy / 2);
		if (Math.abs(x1 - x2) > sizeF.cx) {
			if (x2 >= x1) {
				DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x1, y1, (x2 + x1) / 2.0F - sizeF.cx / 2,
						y1);
				DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, (x2 + x1) / 2.0F + sizeF.cx / 2, y1, x2,
						y1);
			} else {
				DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x2, y1, (x2 + x1) / 2.0F - sizeF.cx / 2,
						y1);
				DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, (x2 + x1) / 2.0F + sizeF.cx / 2, y1, x1,
						y1);
			}
		}
		if (IsSelected()) {
			DrawSelect(paint, lineColor, x1, y1);
			DrawSelect(paint, lineColor, x2, y2);
		}
	}
}
