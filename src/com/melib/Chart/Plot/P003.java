package com.melib.Chart.Plot;

import java.util.HashMap;
import com.melib.Base.RefObject;
import com.melib.Chart.CTableMe;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class P003 extends CPlotBaseMe {
	public P003() {
		SetPlotType("CIRCUMCIRCLE");
	}

	public ActionType GetAction() {
		ActionType action = ActionType.NO;
		if (this.m_marks.isEmpty()) {
			return action;
		}
		com.melib.Base.POINT mp = GetMouseOverPoint();
		float y1 = PY(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0))).GetValue());
		float y2 = PY(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(1))).GetValue());
		float y3 = PY(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(2))).GetValue());
		int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0))).GetKey());
		int eIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(1))).GetKey());
		int pIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(2))).GetKey());
		float x1 = PX(bIndex);
		float x2 = PX(eIndex);
		float x3 = PX(pIndex);
		float Ox = 0.0F;
		float Oy = 0.0F;
		float r = 0.0F;
		RefObject<Float> tempRef_Ox = new RefObject(Float.valueOf(Ox));
		RefObject<Float> tempRef_Oy = new RefObject(Float.valueOf(Oy));
		RefObject<Float> tempRef_r = new RefObject(Float.valueOf(r));
		com.melib.Base.CMathLibMe.M110(x1, y1, x2, y2, x3, y3, tempRef_Ox, tempRef_Oy, tempRef_r);
		Ox = ((Float) tempRef_Ox.argvalue).floatValue();
		Oy = ((Float) tempRef_Oy.argvalue).floatValue();
		r = ((Float) tempRef_r.argvalue).floatValue();
		float clickX = mp.x - Ox;
		float clickY = mp.y - Oy;
		double ellipseValue = clickX * clickX + clickY * clickY;
		if ((ellipseValue >= r * r * 0.8D) && (ellipseValue <= r * r * 1.2D)) {
			if (SelectPoint(mp, x1, y1)) {
				action = ActionType.AT1;
			} else if (SelectPoint(mp, x2, y2)) {
				action = ActionType.AT2;
			} else if (SelectPoint(mp, x3, y3)) {
				action = ActionType.AT3;
			} else {
				action = ActionType.MOVE;
			}
		}
		return action;
	}

	public boolean OnCreate(com.melib.Base.POINT mp) {
		return Create3Points(mp);
	}

	public void OnMoveStart() {
		this.m_action = GetAction();
		this.m_startMarks.clear();
		this.m_startPoint = GetMouseOverPoint();
		if (this.m_action != ActionType.NO) {
			this.m_startMarks.put(Integer.valueOf(0), this.m_marks.get(Integer.valueOf(0)));
			this.m_startMarks.put(Integer.valueOf(1), this.m_marks.get(Integer.valueOf(1)));
			this.m_startMarks.put(Integer.valueOf(2), this.m_marks.get(Integer.valueOf(2)));
		}
	}

	protected void Paint(com.melib.Base.CPaintMe paint, HashMap<Integer, CPlotMarkMe> pList, long lineColor) {
		if (pList.isEmpty()) {
			return;
		}
		float y1 = PY(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetValue());
		float y2 = PY(((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetValue());
		float y3 = PY(((CPlotMarkMe) pList.get(Integer.valueOf(2))).GetValue());
		int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetKey());
		int eIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetKey());
		int pIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(2))).GetKey());
		float x1 = PX(bIndex);
		float x2 = PX(eIndex);
		float x3 = PX(pIndex);
		float Ox = 0.0F;
		float Oy = 0.0F;
		float r = 0.0F;
		RefObject<Float> tempRef_Ox = new RefObject(Float.valueOf(Ox));
		RefObject<Float> tempRef_Oy = new RefObject(Float.valueOf(Oy));
		RefObject<Float> tempRef_r = new RefObject(Float.valueOf(r));
		com.melib.Base.CMathLibMe.M110(x1, y1, x2, y2, x3, y3, tempRef_Ox, tempRef_Oy, tempRef_r);
		Ox = ((Float) tempRef_Ox.argvalue).floatValue();
		Oy = ((Float) tempRef_Oy.argvalue).floatValue();
		r = ((Float) tempRef_r.argvalue).floatValue();
		DrawEllipse(paint, lineColor, this.m_lineWidth, this.m_lineStyle, Ox - r, Oy - r, Ox + r, Oy + r);
		if (IsSelected()) {
			DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x1, y1, x2, y2);
			DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x2, y2, x3, y3);
			DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x3, y3, x1, y1);
			DrawSelect(paint, lineColor, x1, y1);
			DrawSelect(paint, lineColor, x2, y2);
			DrawSelect(paint, lineColor, x3, y3);
		}
	}
}
