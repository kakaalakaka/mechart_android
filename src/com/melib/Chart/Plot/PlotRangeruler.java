package com.melib.Chart.Plot;

import java.util.HashMap;
import com.melib.Base.POINT;
import com.melib.Base.SIZE;
import com.melib.Chart.CTableMe;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class PlotRangeruler extends CPlotBaseMe {
	public PlotRangeruler() {
		SetPlotType("RANGERULER");
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
		double[] param = GetCandleRange(this.m_marks);
		double nHigh = param[0];
		double nLow = param[1];
		float highY = PY(nHigh);
		float lowY = PY(nLow);
		float smallX = x1 > x2 ? x2 : x1;
		float bigX = x1 > x2 ? x1 : x2;
		if (((mp.y >= highY - this.m_lineWidth * 2.5F) && (mp.y <= highY + this.m_lineWidth * 2.5F))
				|| ((mp.y >= lowY - this.m_lineWidth * 2.5F) && (mp.y <= lowY + this.m_lineWidth * 2.5F))) {
			if ((mp.x >= x1 - this.m_lineWidth * 2.5F) && (mp.x <= x1 + this.m_lineWidth * 2.5F)) {
				action = ActionType.AT1;
				return action;
			}
			if ((mp.x >= x2 - this.m_lineWidth * 2.5F) && (mp.x <= x2 + this.m_lineWidth * 2.5F)) {
				action = ActionType.AT2;
				return action;
			}
		}
		if ((mp.y >= highY - this.m_lineWidth * 2.5F) && (mp.y <= highY + this.m_lineWidth * 2.5F)) {
			if ((mp.x >= smallX - this.m_lineWidth * 2.5F) && (mp.x <= bigX + this.m_lineWidth * 2.5F)) {
				action = ActionType.MOVE;
				return action;
			}
		} else if ((mp.y >= lowY - this.m_lineWidth * 2.5F) && (mp.y <= lowY + this.m_lineWidth * 2.5F)) {
			if ((mp.x >= smallX - this.m_lineWidth * 2.5F) && (mp.x <= bigX + this.m_lineWidth * 2.5F)) {
				action = ActionType.MOVE;
				return action;
			}
		}
		float mid = x1 >= x2 ? x2 + (x1 - x2) / 2.0F : x1 + (x2 - x1) / 2.0F;
		if ((mp.x >= mid - this.m_lineWidth * 2.5F) && (mp.x <= mid + this.m_lineWidth * 2.5F)) {
			if ((mp.y >= highY - this.m_lineWidth * 2.5F) && (mp.y <= lowY + this.m_lineWidth * 2.5F)) {
				action = ActionType.MOVE;
				return action;
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
		float smallX = x1 > x2 ? x2 : x1;
		float bigX = x1 > x2 ? x1 : x2;
		double[] param = GetCandleRange(pList);
		double nHigh = param[0];
		double nLow = param[1];
		float highY = PY(nHigh);
		float lowY = PY(nLow);
		float mid = x1 >= x2 ? x2 + (x1 - x2) / 2.0F : x1 + (x2 - x1) / 2.0F;
		DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x1, highY, x2, highY);
		DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x1, lowY, x2, lowY);
		DrawLine(paint, lineColor, this.m_lineWidth, 1, mid, lowY, mid, highY);
		if (nHigh != nLow) {
			double diff = Math.abs(nLow - nHigh);
			double range = 0.0D;
			if (nHigh != 0.0D) {
				range = diff / nHigh;
			}
			com.melib.Chart.CChartMe chart = GetChart();
			String diffString = com.melib.Base.CStrMe.GetValueByDigit(diff,
					this.m_div.GetVScale(this.m_attachVScale).GetDigit());
			String rangeString = String.format("%.2f", new Object[] { Double.valueOf(range) });
			SIZE diffSize = TextSize(paint, diffString, this.m_font);
			SIZE rangeSize = TextSize(paint, rangeString, this.m_font);
			DrawText(paint, diffString, lineColor, this.m_font, bigX - diffSize.cx, highY + 2.0F);
			DrawText(paint, rangeString, lineColor, this.m_font, bigX - rangeSize.cx, lowY - rangeSize.cy);
		}
		if (IsSelected()) {
			DrawSelect(paint, lineColor, smallX, highY);
			DrawSelect(paint, lineColor, smallX, lowY);
			DrawSelect(paint, lineColor, bigX, highY);
			DrawSelect(paint, lineColor, bigX, lowY);
		}
	}
}
