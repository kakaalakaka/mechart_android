package com.melib.Chart.Plot;

import java.util.HashMap;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class PlotHline extends CPlotBaseMe {
	public PlotHline() {
		SetPlotType("HLINE");
	}

	public ActionType GetAction() {
		ActionType action = ActionType.NO;
		if (this.m_marks.isEmpty()) {
			return action;
		}
		com.melib.Base.POINT mp = GetMouseOverPoint();
		float y1 = PY(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0))).GetValue());
		if ((mp.y >= y1 - this.m_lineWidth * 2.5F) && (mp.y <= y1 + this.m_lineWidth * 2.5F)) {
			action = ActionType.MOVE;
		}
		return action;
	}

	public boolean OnCreate(com.melib.Base.POINT mp) {
		int rIndex = this.m_dataSource.GetRowsCount();
		if (rIndex > 0) {
			this.m_marks.clear();
			double y = GetNumberValue(mp);
			this.m_marks.put(Integer.valueOf(0), new CPlotMarkMe(0, 0.0D, y));
			return true;
		}
		return false;
	}

	public void OnMoveStart() {
		this.m_action = GetAction();
		this.m_startMarks.clear();
		this.m_startPoint = GetMouseOverPoint();
		if (this.m_action != ActionType.NO) {
			this.m_startMarks.put(Integer.valueOf(0), this.m_marks.get(Integer.valueOf(0)));
		}
	}

	public void OnMoving() {
		com.melib.Base.POINT mp = GetMovingPoint();
		this.m_marks.put(Integer.valueOf(0), new CPlotMarkMe(0, 0.0D, GetNumberValue(mp)));
	}

	protected void Paint(com.melib.Base.CPaintMe paint, HashMap<Integer, CPlotMarkMe> pList, long lineColor) {
		if (pList.isEmpty()) {
			return;
		}
		float y1 = PY(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetValue());
		DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, 0.0F, y1, GetWorkingAreaWidth(), y1);
	}
}
