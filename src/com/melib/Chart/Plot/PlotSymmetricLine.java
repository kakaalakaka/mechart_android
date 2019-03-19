package com.melib.Chart.Plot;

import java.util.HashMap;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class PlotSymmetricLine extends CPlotBaseMe {
	public PlotSymmetricLine() {
		SetPlotType("SYMMETRICLINE");
	}

	public ActionType GetAction() {
		ActionType action = ActionType.NO;
		if (this.m_marks.isEmpty()) {
			return action;
		}
		com.melib.Base.POINT mp = GetMouseOverPoint();
		int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0))).GetKey());
		int eIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(1))).GetKey());
		float x1 = PX(bIndex);
		float x2 = PX(eIndex);
		float y1 = PY(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0))).GetValue());
		float y2 = PY(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(1))).GetValue());
		if (SelectPoint(mp, x1, y1)) {
			action = ActionType.AT1;
			return action;
		}
		if (SelectPoint(mp, x2, y2)) {
			action = ActionType.AT2;
			return action;
		}
		int cIndex = 0;
		if (x2 >= x1) {
			cIndex = bIndex - (eIndex - bIndex);
		} else {
			cIndex = bIndex + (bIndex - eIndex);
		}
		if (cIndex > this.m_dataSource.GetRowsCount() - 1) {
			cIndex = this.m_dataSource.GetRowsCount() - 1;
		} else if (cIndex < 0) {
			cIndex = 0;
		}
		float x3 = PX(cIndex);
		if (((mp.x >= x1 - this.m_lineWidth * 5) && (mp.x <= x1 + this.m_lineWidth * 5))
				|| ((mp.x >= x2 - this.m_lineWidth * 5) && (mp.x <= x2 + this.m_lineWidth * 5))
				|| ((mp.x >= x3 - this.m_lineWidth * 5) && (mp.x <= x3 + this.m_lineWidth * 5))) {
			action = ActionType.MOVE;
		}
		return action;
	}

	public boolean OnCreate(com.melib.Base.POINT mp) {
		return Create2PointsA(mp);
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
		int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetKey());
		int eIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetKey());
		float x1 = PX(bIndex);
		float x2 = PX(eIndex);
		float y1 = PY(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetValue());
		float y2 = PY(((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetValue());
		int cIndex = -1;
		if (x2 >= x1) {
			cIndex = bIndex - (eIndex - bIndex);
		} else {
			cIndex = bIndex + (bIndex - eIndex);
		}
		if (cIndex > this.m_dataSource.GetRowsCount() - 1) {
			cIndex = this.m_dataSource.GetRowsCount() - 1;
		} else if (cIndex < 0) {
			cIndex = 0;
		}
		float x3 = PX(cIndex);
		DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x1, 0.0F, x1, GetWorkingAreaHeight());
		DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x2, 0.0F, x2, GetWorkingAreaHeight());
		DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x3, 0.0F, x3, GetWorkingAreaHeight());
		if (IsSelected()) {
			DrawSelect(paint, lineColor, x1, y1);
			DrawSelect(paint, lineColor, x2, y2);
		}
	}
}
