package com.melib.Chart.Plot;

import java.util.HashMap;
import com.melib.Base.POINT;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class P046 extends CPlotBaseMe {
	public P046() {
		SetPlotType("TIRONELEVELS");
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
		double[] param = GetTironelLevelsParams(this.m_marks);
		double nHigh = param[0];
		double nLow = param[4];
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
		for (int i = 1; i < param.length - 1; i++) {
			float y = PY(param[i]);
			if ((mp.y >= y - this.m_lineWidth * 2.5F) && (mp.y <= y + this.m_lineWidth * 2.5F)) {
				action = ActionType.MOVE;
				return action;
			}
		}
		return action;
	}

	private double[] GetTironelLevelsParams(HashMap<Integer, CPlotMarkMe> pList) {
		double[] hl = GetCandleRange(pList);
		if (hl != null) {
			double nHigh = hl[0];
			double nLow = hl[1];
			return new double[] { nHigh, nHigh - (nHigh - nLow) / 3.0D, nHigh - (nHigh - nLow) / 2.0D,
					nHigh - 2.0D * (nHigh - nLow) / 3.0D, nLow };
		}

		return null;
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
		double[] param = GetTironelLevelsParams(pList);
		double nHigh = param[0];
		double nLow = param[4];
		float highY = PY(nHigh);
		float lowY = PY(nLow);
		float mid = x1 >= x2 ? x2 + (x1 - x2) / 2.0F : x1 + (x2 - x1) / 2.0F;
		DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x1, highY, x2, highY);
		DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x1, lowY, x2, lowY);
		DrawLine(paint, lineColor, this.m_lineWidth, 1, mid, lowY, mid, highY);
		for (int i = 1; i < param.length - 1; i++) {
			float y = PY(param[i]);
			DrawLine(paint, lineColor, this.m_lineWidth, 1, 0.0F, y, GetWorkingAreaWidth(), y);
			String str = new Integer(i).toString() + "/3";
			if (i == 2) {
				str = "1/2";
			}
			com.melib.Base.SIZE sizeF = TextSize(paint, str, this.m_font);
			DrawText(paint, str, lineColor, this.m_font, GetWorkingAreaWidth() - sizeF.cx, y - sizeF.cy);
		}
		if (IsSelected()) {
			DrawSelect(paint, lineColor, smallX, highY);
			DrawSelect(paint, lineColor, smallX, lowY);
			DrawSelect(paint, lineColor, bigX, highY);
			DrawSelect(paint, lineColor, bigX, lowY);
		}
	}
}
