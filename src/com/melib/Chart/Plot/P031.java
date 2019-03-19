package com.melib.Chart.Plot;

import java.util.HashMap;
import com.melib.Base.POINT;
import com.melib.Base.RefObject;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class P031 extends CPlotBaseMe {
	public P031() {
		SetPlotType("RAY");
	}

	public ActionType GetAction() {
		ActionType action = ActionType.NO;
		if (this.m_marks.isEmpty()) {
			return action;
		}
		POINT mp = GetMouseOverPoint();
		int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0))).GetKey());
		int eIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(1))).GetKey());
		float x1 = PX(bIndex);
		float x2 = PX(eIndex);
		float y1 = PY(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0))).GetValue());
		float y2 = PY(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(1))).GetValue());
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
		if (SelectRay(mp, x1, y1, x2, y2)) {
			action = ActionType.MOVE;
		}
		return action;
	}

	public boolean OnCreate(POINT mp) {
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
		float k = 0.0F;
		float b = 0.0F;
		float x1 = PX(bIndex);
		float x2 = PX(eIndex);
		RefObject<Float> tempRef_k = new RefObject(Float.valueOf(k));
		RefObject<Float> tempRef_b = new RefObject(Float.valueOf(b));
		com.melib.Base.CMathLibMe.M107(x1, y1, x2, y2, 0.0F, 0.0F, tempRef_k, tempRef_b);
		k = ((Float) tempRef_k.argvalue).floatValue();
		b = ((Float) tempRef_b.argvalue).floatValue();
		DrawRay(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x1, y1, x2, y2, k, b);
		if (IsSelected()) {
			DrawSelect(paint, lineColor, x1, y1);
			DrawSelect(paint, lineColor, x2, y2);
		}
	}
}
