package com.melib.Chart.Plot;

import java.util.HashMap;
import com.melib.Base.POINT;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class PlotQuadrantLines extends PlotPercent {
	public PlotQuadrantLines() {
		SetPlotType("QUADRANTLINES");
	}

	public ActionType GetAction() {
		ActionType action = ActionType.NO;
		if (this.m_marks.isEmpty()) {
			return action;
		}
		POINT mp = GetMouseOverPoint();
		int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0))).GetKey());
		int eIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(1))).GetKey());
		float x1 = PX(bIndex);
		float x2 = PX(eIndex);
		float[] param = GetLRParams(this.m_marks);
		if (param != null) {
			float a = param[0];
			float b = param[1];
			double leftValue = a + b;
			double rightValue = (eIndex - bIndex + 1) * a + b;
			float y1 = PY(leftValue);
			float y2 = PY(rightValue);
			if (SelectPoint(mp, x1, y1)) {
				action = ActionType.AT1;
				return action;
			}
			if (SelectPoint(mp, x2, y2)) {
				action = ActionType.AT2;
				return action;
			}
			com.melib.Chart.CChartMe chart = GetChart();
			int mouseIndex = chart.GetMouseOverIndex();
			if ((mouseIndex >= bIndex) && (mouseIndex <= eIndex)) {
				double yValue = a * (mouseIndex - bIndex + 1) + b;
				float y = PY(yValue);
				float x = PX(mouseIndex);
				if ((mp.x >= x - 5.0F) && (mp.x <= x + 5.0F) && (mp.y >= y - 5.0F) && (mp.y <= y + 5.0F)) {
					action = ActionType.MOVE;
					return action;
				}
				double[] candleRegion = GetCandleRange(this.m_marks);
				if (candleRegion != null) {
					float[] percents = GetPercentParams(candleRegion[0], candleRegion[1]);
					for (int i = 0; i < percents.length; i++) {
						if (SelectRay(mp, x1, percents[i], x2, percents[i])) {
							action = ActionType.MOVE;
							return action;
						}
					}
				}
			}
		}
		return action;
	}

	public boolean OnCreate(POINT mp) {
		return Create2CandlePoints(mp);
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
		float[] param = GetLRParams(pList);
		if (param != null) {
			int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetKey());
			int eIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetKey());
			float x1 = PX(bIndex);
			float x2 = PX(eIndex);
			float a = param[0];
			float b = param[1];
			double leftValue = a + b;
			double rightValue = (eIndex - bIndex + 1) * a + b;
			float y1 = PY(leftValue);
			float y2 = PY(rightValue);
			DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x1, y1, x2, y2);
			double[] candleRegion = GetCandleRange(pList);
			if (candleRegion != null) {
				float[] percents = GetPercentParams(candleRegion[0], candleRegion[1]);
				for (int i = 0; i < percents.length; i++) {
					float yp = percents[i];
					if (i == 2) {
						DrawLine(paint, lineColor, this.m_lineWidth, 1, x1, yp, x2, yp);
					} else {
						DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x1, yp, x2, yp);
					}
				}
			}
			if (IsSelected()) {
				DrawSelect(paint, lineColor, x1, y1);
				DrawSelect(paint, lineColor, x2, y2);
			}
		}
	}
}
