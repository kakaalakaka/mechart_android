package com.melib.Chart.Plot;

import java.util.HashMap;
import com.melib.Base.POINT;
import com.melib.Base.RefObject;
import com.melib.Chart.CTableMe;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class P050 extends CPlotBaseMe {
	public P050() {
		SetPlotType("PARALLELOGRAM");
	}

	public ActionType GetAction() {
		ActionType action = ActionType.NO;
		if (this.m_marks.isEmpty()) {
			return action;
		}
		POINT mp = GetMouseOverPoint();
		if (this.m_moveTimes == 1) {
			action = ActionType.AT3;
			return action;
		}

		float y1 = PY(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0))).GetValue());
		float y2 = PY(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(1))).GetValue());
		float y3 = PY(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(2))).GetValue());
		int aIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0))).GetKey());
		int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(1))).GetKey());
		int cIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(2))).GetKey());
		float x1 = PX(aIndex);
		float x2 = PX(bIndex);
		float x3 = PX(cIndex);
		if ((SelectPoint(mp, x1, y1)) || (this.m_moveTimes == 1)) {
			action = ActionType.AT1;
			return action;
		}
		if (SelectPoint(mp, x2, y2)) {
			action = ActionType.AT2;
			return action;
		}
		if (SelectPoint(mp, x3, y3)) {
			action = ActionType.AT3;
			return action;
		}

		POINT[] points = GetPLPoints(this.m_marks);
		for (int i = 0; i < points.length; i++) {
			int start = i;
			int end = i + 1;
			if (start == 3) {
				end = 0;
			}
			if (SelectRay(mp, points[start].x, points[start].y, points[end].x, points[end].y)) {
				action = ActionType.MOVE;
				return action;
			}
		}
		return action;
	}

	private POINT[] GetPLPoints(HashMap<Integer, CPlotMarkMe> marks) {
		POINT point1 = new POINT(PX(this.m_dataSource.GetRowIndex(((CPlotMarkMe) marks.get(Integer.valueOf(0))).GetKey())),
				PY(((CPlotMarkMe) marks.get(Integer.valueOf(0))).GetValue()));
		POINT point2 = new POINT(PX(this.m_dataSource.GetRowIndex(((CPlotMarkMe) marks.get(Integer.valueOf(1))).GetKey())),
				PY(((CPlotMarkMe) marks.get(Integer.valueOf(1))).GetValue()));
		POINT point3 = new POINT(PX(this.m_dataSource.GetRowIndex(((CPlotMarkMe) marks.get(Integer.valueOf(2))).GetKey())),
				PY(((CPlotMarkMe) marks.get(Integer.valueOf(2))).GetValue()));
		float x1 = 0.0F;
		float y1 = 0.0F;
		float x2 = 0.0F;
		float y2 = 0.0F;
		float x3 = 0.0F;
		float y3 = 0.0F;
		float x4 = 0.0F;
		float y4 = 0.0F;
		x1 = point1.x;
		y1 = point1.y;
		x2 = point2.x;
		y2 = point2.y;
		x3 = point3.x;
		y3 = point3.y;
		RefObject<Float> tempRef_x4 = new RefObject(Float.valueOf(x4));
		RefObject<Float> tempRef_y4 = new RefObject(Float.valueOf(y4));
		com.melib.Base.CMathLibMe.M124(x1, y1, x2, y2, x3, y3, tempRef_x4, tempRef_y4);
		x4 = ((Float) tempRef_x4.argvalue).floatValue();
		y4 = ((Float) tempRef_y4.argvalue).floatValue();
		POINT point4 = new POINT(x4, y4);
		return new POINT[] { point1, point2, point3, point4 };
	}

	public boolean OnCreate(POINT mp) {
		int rIndex = this.m_dataSource.GetRowsCount();
		if (rIndex > 0) {
			int currentIndex = GetIndex(mp);
			double y = GetNumberValue(mp);
			double date = this.m_dataSource.GetXValue(currentIndex);
			this.m_marks.clear();
			this.m_marks.put(Integer.valueOf(0), new CPlotMarkMe(0, date, y));
			int si = currentIndex + 10;
			com.melib.Chart.CChartMe chart = GetChart();
			if (si > chart.GetLastVisibleIndex()) {
				si = chart.GetLastVisibleIndex();
			}
			this.m_marks.put(Integer.valueOf(1), new CPlotMarkMe(1, this.m_dataSource.GetXValue(si), y));
			this.m_marks.put(Integer.valueOf(2), new CPlotMarkMe(2, date, y));
			return true;
		}
		return false;
	}

	public void OnMoveStart() {
		this.m_moveTimes += 1;
		this.m_action = GetAction();
		this.m_startMarks.clear();
		this.m_startPoint = GetMouseOverPoint();
		if (this.m_action != ActionType.NO) {
			this.m_startMarks.put(Integer.valueOf(0), this.m_marks.get(Integer.valueOf(0)));
			this.m_startMarks.put(Integer.valueOf(1), this.m_marks.get(Integer.valueOf(1)));
			this.m_startMarks.put(Integer.valueOf(2), this.m_marks.get(Integer.valueOf(2)));
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
		POINT[] points = GetPLPoints(pList);
		for (int i = 0; i < points.length; i++) {
			int start = i;
			int end = i + 1;
			if (start == 3) {
				end = 0;
			}
			float x1 = points[start].x;
			float y1 = points[start].y;
			float x2 = points[end].x;
			float y2 = points[end].y;
			DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x1, y1, x2, y2);
			if ((IsSelected()) && (i < 3)) {
				DrawSelect(paint, lineColor, x1, y1);
			}
		}
	}
}
