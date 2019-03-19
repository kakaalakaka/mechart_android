package com.melib.Chart.Plot;

import com.melib.Base.POINT;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class PlotArrowSegment extends CPlotBaseMe {
	public PlotArrowSegment() {
		SetPlotType("ARROWSEGMENT");
	}

	private static int ARROW_SIZE = 10;

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

	public boolean OnCreate(POINT mp) {
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

	protected void Paint(com.melib.Base.CPaintMe paint, java.util.HashMap<Integer, CPlotMarkMe> pList, long lineColor) {
		if (pList.isEmpty()) {
			return;
		}
		int y1 = (int) PY(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetValue());
		int y2 = (int) PY(((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetValue());
		int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetKey());
		int eIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetKey());
		int x1 = (int) PX(bIndex);
		int x2 = (int) PX(eIndex);
		DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x1, y1, x2, y2);
		double slopy = 0.0D;
		double cosy = 0.0D;
		double siny = 0.0D;
		slopy = Math.atan2(y1 - y2, x1 - x2);
		cosy = Math.cos(slopy);
		siny = Math.sin(slopy);
		POINT pt1 = new POINT(x2, y2);
		POINT pt2 = new POINT(pt1.x + (int) (ARROW_SIZE * cosy - ARROW_SIZE / 2.0D * siny + 0.5D),
				pt1.y + (int) (ARROW_SIZE * siny + ARROW_SIZE / 2.0D * cosy + 0.5D));
		POINT pt3 = new POINT(pt1.x + (int) (ARROW_SIZE * cosy + ARROW_SIZE / 2.0D * siny + 0.5D),
				pt1.y - (int) (ARROW_SIZE / 2.0D * cosy - ARROW_SIZE * siny + 0.5D));
		POINT[] points = { pt1, pt2, pt3 };
		FillPolygon(paint, lineColor, points);
		if (IsSelected()) {
			DrawSelect(paint, lineColor, x1, y1);
			DrawSelect(paint, lineColor, x2, y2);
		}
	}
}
