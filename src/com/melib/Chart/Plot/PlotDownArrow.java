package com.melib.Chart.Plot;

import java.util.HashMap;
import com.melib.Base.POINT;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class PlotDownArrow extends CPlotBaseMe {
	public PlotDownArrow() {
		SetPlotType("DOWNARROW");
	}

	public ActionType GetAction() {
		ActionType action = ActionType.NO;
		if (this.m_marks.isEmpty()) {
			return action;
		}
		double fValue = ((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0))).GetValue();
		int aIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0))).GetKey());
		float x1 = PX(aIndex);
		float y1 = PY(fValue);
		int width = 10;
		com.melib.Base.RECT rect = new com.melib.Base.RECT(x1 - width / 2, y1 - width * 3 / 2, x1 + width / 2, y1);
		POINT mp = GetMouseOverPoint();
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
		double fValue = ((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetValue();
		int aIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetKey());
		int x1 = (int) PX(aIndex);
		int y1 = (int) PY(fValue);
		int width = 10;
		POINT point1 = new POINT(x1, y1);
		POINT point2 = new POINT(x1 + width / 2, y1 - width);
		POINT point3 = new POINT(x1 + width / 4, y1 - width);
		POINT point4 = new POINT(x1 + width / 4, y1 - width * 3 / 2);
		POINT point5 = new POINT(x1 - width / 4, y1 - width * 3 / 2);
		POINT point6 = new POINT(x1 - width / 4, y1 - width);
		POINT point7 = new POINT(x1 - width / 2, y1 - width);
		POINT[] points = { point1, point2, point3, point4, point5, point6, point7 };
		FillPolygon(paint, lineColor, points);
		if (IsSelected()) {
			DrawSelect(paint, lineColor, x1 - width / 2, y1 - width * 3 / 2);
		}
	}
}
