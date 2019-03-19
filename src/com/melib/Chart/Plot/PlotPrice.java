package com.melib.Chart.Plot;

import java.util.HashMap;
import com.melib.Base.POINT;
import com.melib.Base.SIZE;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class PlotPrice extends CPlotBaseMe {
	private SIZE m_textSize;

	public PlotPrice() {
		SetPlotType("PRICE");
	}

	public ActionType GetAction() {
		ActionType action = ActionType.NO;
		if (this.m_marks.isEmpty()) {
			return action;
		}
		POINT mp = GetMouseOverPoint();
		double fValue = ((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0))).GetValue();
		int aIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0))).GetKey());
		float x1 = PX(aIndex);
		float y1 = PY(fValue);
		com.melib.Base.RECT rect = new com.melib.Base.RECT(x1, y1, x1 + this.m_textSize.cx, y1 + this.m_textSize.cy);
		if ((mp.x >= rect.left) && (mp.x <= rect.right) && (mp.y >= rect.top) && (mp.y <= rect.bottom)) {
			action = ActionType.MOVE;
		}
		return action;
	}

	public boolean OnCreate(POINT mp) {
		return CreatePoint(mp);
	}

	public void OnMoveStart() {
		this.m_action = GetAction();
		this.m_startMarks.clear();
		this.m_startPoint = GetMouseOverPoint();
		if (this.m_action != ActionType.NO) {
			this.m_startMarks.put(Integer.valueOf(0), this.m_marks.get(Integer.valueOf(0)));
		}
	}

	protected void Paint(com.melib.Base.CPaintMe paint, HashMap<Integer, CPlotMarkMe> pList, long lineColor) {
		if (pList.isEmpty()) {
			return;
		}
		int wX = GetWorkingAreaWidth();
		int wY = GetWorkingAreaHeight();
		if ((wX > 0) && (wY > 0)) {
			double fValue = ((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetValue();
			int aIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetKey());
			float x1 = PX(aIndex);
			float y1 = PY(fValue);
			com.melib.Chart.CChartMe chart = GetChart();
			String word = com.melib.Base.CStrMe.GetValueByDigit(fValue,
					chart.GetLeftVScaleWidth() > 0 ? this.m_div.GetLeftVScale().GetDigit()
							: this.m_div.GetRightVScale().GetDigit());
			DrawText(paint, word, lineColor, this.m_font, x1, y1);
			this.m_textSize = TextSize(paint, word, this.m_font);
			if (IsSelected()) {
				if ((this.m_textSize.cx > 0) && (this.m_textSize.cy > 0)) {
					DrawRect(paint, lineColor, this.m_lineWidth, this.m_lineStyle, (int) x1, (int) y1,
							(int) x1 + this.m_textSize.cx, (int) y1 + this.m_textSize.cy);
				}
			}
		}
	}
}
