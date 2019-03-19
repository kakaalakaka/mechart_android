package com.melib.Chart.Plot;

import java.util.HashMap;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class P033 extends CPlotBaseMe {
	public P033() {
		SetPlotType("SEGMENT");
	}

	public ActionType GetAction() {
		ActionType action = ActionType.NO;
		if (this.m_marks.isEmpty()) {
			return action;
		}
		com.melib.Base.POINT mp = GetMouseOverPoint();
		float y1 = PY(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0))).GetValue());
		float y2 = PY(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(1))).GetValue());
		int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0))).GetKey());
		int eIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(1))).GetKey());
		float x1 = PX(bIndex);
		float x2 = PX(eIndex);
		float[] param = GetLineParams((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0)),
				(CPlotMarkMe) this.m_marks.get(Integer.valueOf(1)));
		if (param != null) {
			if ((mp.x >= x1 - this.m_lineWidth * 2.5F) && (mp.x <= x1 + this.m_lineWidth * 2.5F)) {
				action = ActionType.AT1;
				return action;
			}
			if ((mp.x >= x2 - this.m_lineWidth * 2.5F) && (mp.x <= x2 + this.m_lineWidth * 2.5F)) {
				action = ActionType.AT2;
				return action;
			}
		} else {
			if ((mp.y >= y1 - this.m_lineWidth * 5) && (mp.y <= y1 + this.m_lineWidth * 5)) {
				action = ActionType.AT1;
				return action;
			}
			if ((mp.y >= y2 - this.m_lineWidth * 5) && (mp.y <= y2 + this.m_lineWidth * 5)) {
				action = ActionType.AT2;
				return action;
			}
		}
		if (SelectSegment(mp, x1, y1, x2, y2)) {
			action = ActionType.MOVE;
		}
		return action;
	}

	public boolean OnCreate(com.melib.Base.POINT mp) {
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
		DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x1, y1, x2, y2);
		if (IsSelected()) {
			DrawSelect(paint, lineColor, x1, y1);
			DrawSelect(paint, lineColor, x2, y2);
		}
	}
}
