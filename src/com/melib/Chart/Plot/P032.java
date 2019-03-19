package com.melib.Chart.Plot;

import java.util.HashMap;
import com.melib.Base.RECT;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class P032 extends CPlotBaseMe {
	public P032() {
		SetPlotType("RECT");
	}

	private com.melib.Base.POINT oppositePoint = new com.melib.Base.POINT();

	public ActionType GetAction() {
		ActionType action = ActionType.NO;
		if (this.m_marks.isEmpty()) {
			return action;
		}
		com.melib.Base.POINT mp = GetMouseOverPoint();
		action = SelectRect(mp, (CPlotMarkMe) this.m_marks.get(Integer.valueOf(0)),
				(CPlotMarkMe) this.m_marks.get(Integer.valueOf(1)));
		return action;
	}

	public boolean OnCreate(com.melib.Base.POINT mp) {
		return Create2PointsB(mp);
	}

	public void OnMoveStart() {
		this.m_action = GetAction();
		if ((this.m_action != ActionType.MOVE) && (this.m_action != ActionType.NO)) {
			RECT rect = GetRectangle((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0)),
					(CPlotMarkMe) this.m_marks.get(Integer.valueOf(1)));
			int x1 = rect.left;
			int y1 = rect.top;
			int x2 = rect.right;
			int y2 = rect.top;
			int x3 = rect.left;
			int y3 = rect.bottom;
			int x4 = rect.right;
			int y4 = rect.bottom;
			switch (this.m_action) {
			case AT1:
				this.oppositePoint = new com.melib.Base.POINT(x4, y4);
				break;
			case AT2:
				this.oppositePoint = new com.melib.Base.POINT(x3, y3);
				break;
			case AT3:
				this.oppositePoint = new com.melib.Base.POINT(x2, y2);
				break;
			case AT4:
				this.oppositePoint = new com.melib.Base.POINT(x1, y1);
			}

		}
		this.m_moveTimes += 1;
		this.m_startMarks.clear();
		this.m_startPoint = GetMouseOverPoint();
		if (this.m_action != ActionType.NO) {
			this.m_startMarks.put(Integer.valueOf(0), this.m_marks.get(Integer.valueOf(0)));
			this.m_startMarks.put(Integer.valueOf(1), this.m_marks.get(Integer.valueOf(1)));
		}
	}

	public void OnMoving() {
		com.melib.Base.POINT mp = GetMovingPoint();
		switch (this.m_action) {
		case MOVE:
			Move(mp);
			break;
		case AT1:
		case AT2:
		case AT3:
		case AT4:
			Resize(mp, this.oppositePoint);
		}

	}

	public void OnPaintGhost(com.melib.Base.CPaintMe paint) {
		if (this.m_moveTimes > 1) {
			Paint(paint, this.m_startMarks, GetSelectedColor());
		}
	}

	protected void Paint(com.melib.Base.CPaintMe paint, HashMap<Integer, CPlotMarkMe> pList, long lineColor) {
		if (pList.isEmpty()) {
			return;
		}
		RECT rect = GetRectangle((CPlotMarkMe) pList.get(Integer.valueOf(0)), (CPlotMarkMe) pList.get(Integer.valueOf(1)));
		if ((rect.right - rect.left > 0) && (rect.bottom - rect.top > 0)) {
			DrawRect(paint, lineColor, this.m_lineWidth, this.m_lineStyle, rect.left, rect.top, rect.right,
					rect.bottom);
		}
		if (IsSelected()) {
			DrawSelect(paint, lineColor, rect.left, rect.top);
			DrawSelect(paint, lineColor, rect.right, rect.top);
			DrawSelect(paint, lineColor, rect.left, rect.bottom);
			DrawSelect(paint, lineColor, rect.right, rect.bottom);
		}
	}
}
