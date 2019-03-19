package com.melib.Chart.Plot;

import java.util.HashMap;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class P020 extends CPlotBaseMe {
	public P020() {
		SetPlotType("LINE");
	}

	public ActionType GetAction() {
		ActionType action = ActionType.NO;
		if (this.m_marks.isEmpty()) {
			return action;
		}
		com.melib.Base.POINT mp = GetMouseOverPoint();
		int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0))).GetKey());
		int eIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(1))).GetKey());
		float y1 = PY(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0))).GetValue());
		float y2 = PY(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(1))).GetValue());
		float x1 = PX(bIndex);
		float x2 = PX(eIndex);
		if (SelectPoint(mp, x1, y1)) {
			action = ActionType.AT1;
		} else if (SelectPoint(mp, x2, y2)) {
			action = ActionType.AT2;
		} else if (SelectLine(mp, x1, y1, x2, y2)) {
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
		float y1 = PY(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetValue());
		float y2 = PY(((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetValue());
		int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetKey());
		int eIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetKey());
		float[] param = GetLineParams((CPlotMarkMe) pList.get(Integer.valueOf(0)),
				(CPlotMarkMe) pList.get(Integer.valueOf(1)));
		float a = 0.0F;
		float b = 0.0F;
		float x1 = PX(bIndex);
		float x2 = PX(eIndex);
		if (param != null) {
			a = param[0];
			b = param[1];
			float leftX = 0.0F;
			float leftY = leftX * a + b;
			float rightX = GetWorkingAreaWidth();
			float rightY = rightX * a + b;
			DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, leftX, leftY, rightX, rightY);
		} else {
			DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x1, 0.0F, x1, GetWorkingAreaHeight());
		}
		if (IsSelected()) {
			DrawSelect(paint, lineColor, x1, y1);
			DrawSelect(paint, lineColor, x2, y2);
		}
	}
}
