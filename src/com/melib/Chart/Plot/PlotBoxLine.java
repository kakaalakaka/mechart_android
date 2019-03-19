package com.melib.Chart.Plot;

import java.util.HashMap;
import com.melib.Base.POINT;
import com.melib.Base.RECT;
import com.melib.Base.SIZE;
import com.melib.Chart.CTableMe;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class PlotBoxLine extends PlotRect {
	public PlotBoxLine() {
		SetPlotType("BOXLINE");
	}

	private ActionType GetAction(POINT mp) {
		double[] param = GetCandleRange(this.m_marks);
		double nHigh = param[0];
		double nLow = param[1];
		if (param != null) {
			ActionType m_action = SelectRect(mp,
					new CPlotMarkMe(0, ((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0))).GetKey(), nHigh),
					new CPlotMarkMe(1, ((CPlotMarkMe) this.m_marks.get(Integer.valueOf(1))).GetKey(), nLow));
			return m_action;
		}
		return ActionType.NO;
	}

	public ActionType GetAction() {
		ActionType action = ActionType.NO;
		if (this.m_marks.isEmpty()) {
			return action;
		}
		POINT mp = GetMouseOverPoint();
		action = GetAction(mp);
		if (action == ActionType.AT4) {
			action = ActionType.AT2;
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
		int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetKey());
		int eIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetKey());
		double[] param = GetCandleRange(pList);
		double nHigh = param[0];
		double nLow = param[1];
		if (param != null) {
			RECT rect = GetRectangle(new CPlotMarkMe(0, ((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetKey(), nHigh),
					new CPlotMarkMe(1, ((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetKey(), nLow));
			int x1 = rect.left;
			int y1 = rect.top;
			int x2 = rect.right;
			int y2 = rect.bottom;
			if ((rect.right - rect.left > 0) && (rect.bottom - rect.top > 0)) {
				int rwidth = rect.right - rect.left;
				int rheight = rect.bottom - rect.top;
				DrawRect(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x1, y1, x1 + rwidth, y1 + rheight);
				int count = Math.abs(bIndex - eIndex) + 1;
				DrawText(paint, "COUNT:" + new Integer(count).toString(), lineColor, this.m_font, x1 + 2, y1 + 2);
				double diff = nLow - nHigh;
				double range = 0.0D;
				if (nHigh != 0.0D) {
					range = diff / nHigh;
				}
				String diffString = String.format("%.2f", new Object[] { Double.valueOf(diff) });
				String rangeString = String.format("%.2f", new Object[] { Double.valueOf(range) }) + "%";
				SIZE rangeSize = TextSize(paint, rangeString, this.m_font);
				DrawText(paint, diffString, lineColor, this.m_font, x1 + rwidth + 2, y1 + 2);
				DrawText(paint, rangeString, lineColor, this.m_font, x1 + rwidth + 2, y1 + rheight - rangeSize.cy - 2);
				if ((this.m_sourceFields != null) && (this.m_sourceFields.containsKey("CLOSE"))) {
					double[] array = this.m_dataSource.DATA_ARRAY(
							((Integer) this.m_sourceFields.get("CLOSE")).intValue(), eIndex, eIndex - bIndex + 1);
					double avg = com.melib.Base.CMathLibMe.M010(array, array.length);
					float yAvg = PY(avg);
					DrawLine(paint, lineColor, this.m_lineWidth, 1, x1, yAvg, x2, yAvg);
					String avgString = "AVG:" + String.format("%.2f", new Object[] { Double.valueOf(avg) });
					SIZE avgSize = TextSize(paint, avgString, this.m_font);
					DrawText(paint, avgString, lineColor, this.m_font, x1 + 2, yAvg - avgSize.cy - 2.0F);
				}
			}
			if (IsSelected()) {
				DrawSelect(paint, lineColor, x1, y1);
				DrawSelect(paint, lineColor, x2, y2);
			}
		}
	}
}
