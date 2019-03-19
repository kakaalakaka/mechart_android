package com.melib.Chart.Plot;

import java.util.ArrayList;
import java.util.HashMap;
import com.melib.Base.POINT;
import com.melib.Base.POINTF;
import com.melib.Base.RefObject;
import com.melib.Chart.CTableMe;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class P016 extends CPlotBaseMe {
	public P016() {
		SetPlotType("GANNLINE");
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
		if (SelectPoint(mp, x1, y1)) {
			action = ActionType.AT1;
			return action;
		}
		if (SelectPoint(mp, x2, y2)) {
			action = ActionType.AT2;
			return action;
		}
		boolean selected = SelectSegment(mp, x1, y1, x2, y2);
		if (selected) {
			action = ActionType.MOVE;
			return action;
		}
		POINTF firstP = new POINTF(x2, y2 - (y2 - y1) * 0.875F);
		POINTF secondP = new POINTF(x2, y2 - (y2 - y1) * 0.75F);
		POINTF thirdP = new POINTF(x2, y2 - (y2 - y1) * 0.67F);
		POINTF forthP = new POINTF(x2, y2 - (y2 - y1) * 0.5F);
		POINTF fifthP = new POINTF(x2 - (x2 - x1) * 0.875F, y2);
		POINTF sixthP = new POINTF(x2 - (x2 - x1) * 0.75F, y2);
		POINTF seventhP = new POINTF(x2 - (x2 - x1) * 0.67F, y2);
		POINTF eighthP = new POINTF(x2 - (x2 - x1) * 0.5F, y2);
		POINTF startP = new POINTF(x1, y1);
		ArrayList<POINTF> listP = new ArrayList();
		listP.addAll(java.util.Arrays
				.asList(new POINTF[] { firstP, secondP, thirdP, forthP, fifthP, sixthP, seventhP, eighthP }));
		if (((x2 > x1) && (mp.x >= x1 - 2.0F)) || ((mp.x <= x1 + 2.0F) && (x2 < x1))) {
			int listSize = listP.size();
			for (int i = 0; i < listSize; i++) {
				selected = SelectLine(mp, startP.x, startP.y, ((POINTF) listP.get(i)).x, ((POINTF) listP.get(i)).y);
				if (selected) {
					action = ActionType.MOVE;
					return action;
				}
			}
		}
		return action;
	}

	public boolean OnCreate(POINT mp) {
		return Create2PointsB(mp);
	}

	public void OnMoveStart() {
		this.m_moveTimes += 1;
		this.m_action = GetAction();
		this.m_startMarks.clear();
		this.m_startPoint = GetMouseOverPoint();
		if (this.m_action != ActionType.NO) {
			this.m_startMarks.put(Integer.valueOf(0), this.m_marks.get(Integer.valueOf(0)));
			this.m_startMarks.put(Integer.valueOf(1), this.m_marks.get(Integer.valueOf(1)));
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
		float y1 = PY(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetValue());
		float y2 = PY(((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetValue());
		int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetKey());
		int eIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetKey());
		float x1 = PX(bIndex);
		float x2 = PX(eIndex);
		DrawLine(paint, lineColor, this.m_lineWidth, 1, x1, y1, x2, y2);
		if ((IsSelected()) || (x1 == x2)) {
			DrawSelect(paint, lineColor, x1, y1);
			DrawSelect(paint, lineColor, x2, y2);
		}
		if ((x1 != x2) && (y1 != y2)) {
			POINTF firstP = new POINTF(x2, y2 - (y2 - y1) * 0.875F);
			POINTF secondP = new POINTF(x2, y2 - (y2 - y1) * 0.75F);
			POINTF thirdP = new POINTF(x2, y2 - (y2 - y1) * 0.67F);
			POINTF forthP = new POINTF(x2, y2 - (y2 - y1) * 0.5F);
			POINTF fifthP = new POINTF(x2 - (x2 - x1) * 0.875F, y2);
			POINTF sixthP = new POINTF(x2 - (x2 - x1) * 0.75F, y2);
			POINTF seventhP = new POINTF(x2 - (x2 - x1) * 0.67F, y2);
			POINTF eighthP = new POINTF(x2 - (x2 - x1) * 0.5F, y2);
			POINTF startP = new POINTF(x1, y1);
			ArrayList<POINTF> listP = new ArrayList();
			listP.addAll(java.util.Arrays
					.asList(new POINTF[] { firstP, secondP, thirdP, forthP, fifthP, sixthP, seventhP, eighthP }));
			int listSize = listP.size();
			for (int i = 0; i < listSize; i++) {
				float k = 0.0F;
				float b = 0.0F;
				RefObject<Float> tempRef_k = new RefObject(Float.valueOf(k));
				RefObject<Float> tempRef_b = new RefObject(Float.valueOf(b));
				com.melib.Base.CMathLibMe.M107(startP.x, startP.y, ((POINTF) listP.get(i)).x, ((POINTF) listP.get(i)).y,
						0.0F, 0.0F, tempRef_k, tempRef_b);
				k = ((Float) tempRef_k.argvalue).floatValue();
				b = ((Float) tempRef_b.argvalue).floatValue();
				float newX = 0.0F;
				float newY = 0.0F;
				if (x2 > x1) {
					newY = k * GetWorkingAreaWidth() + b;
					newX = GetWorkingAreaWidth();
				} else {
					newY = b;
					newX = 0.0F;
				}
				DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, startP.x, startP.y, newX, newY);
			}
		}
	}
}
