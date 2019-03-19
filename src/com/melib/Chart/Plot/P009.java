package com.melib.Chart.Plot;

import java.util.HashMap;
import com.melib.Base.POINT;
import com.melib.Base.RefObject;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class P009 extends CPlotBaseMe {
	public P009() {
		SetPlotType("ELLIPSE");
	}

	public ActionType GetAction() {
		ActionType action = ActionType.NO;
		if (this.m_marks.isEmpty()) {
			return action;
		}
		POINT mp = GetMouseOverPoint();
		double fValue = ((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0))).GetValue();
		double eValue = ((CPlotMarkMe) this.m_marks.get(Integer.valueOf(1))).GetValue();
		int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0))).GetKey());
		int eIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(1))).GetKey());
		float x1 = PX(bIndex);
		float x2 = PX(eIndex);
		float y1 = PY(fValue);
		float y2 = PY(eValue);
		float x = 0.0F;
		float y = 0.0F;
		if (x1 >= x2) {
			x = x2;
		} else {
			x = x2 - (x2 - x1) * 2.0F;
		}
		if (y1 >= y2) {
			y = y1 - (y1 - y2) * 2.0F;
		} else {
			y = y1;
		}
		if (SelectPoint(mp, x1, y1)) {
			action = ActionType.AT1;
			return action;
		}
		if (SelectPoint(mp, x2, y2)) {
			action = ActionType.AT2;
			return action;
		}
		float width = Math.abs((x1 - x2) * 2.0F);
		float height = Math.abs((y1 - y2) * 2.0F);
		float oX = x + width / 2.0F;
		float oY = y + height / 2.0F;
		float a = 0.0F;
		float b = 0.0F;
		RefObject<Float> tempRef_a = new RefObject(Float.valueOf(a));
		RefObject<Float> tempRef_b = new RefObject(Float.valueOf(b));
		com.melib.Base.CMathLibMe.M108(width, height, tempRef_a, tempRef_b);
		a = ((Float) tempRef_a.argvalue).floatValue();
		b = ((Float) tempRef_b.argvalue).floatValue();
		if ((a != 0.0F) && (b != 0.0F)) {
			float clickX = mp.x - oX;
			float clickY = mp.y - oY;
			double ellipseValue = clickX * clickX / (a * a) + clickY * clickY / (b * b);
			if ((ellipseValue >= 0.8D) && (ellipseValue <= 1.2D)) {
				action = ActionType.MOVE;
			}
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

	protected void Paint(com.melib.Base.CPaintMe paint, HashMap<Integer, CPlotMarkMe> pList, long lineColor) {
		if (pList.isEmpty()) {
			return;
		}
		double fValue = ((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetValue();
		double eValue = ((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetValue();
		int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetKey());
		int eIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetKey());
		float x1 = PX(bIndex);
		float x2 = PX(eIndex);
		float y1 = PY(fValue);
		float y2 = PY(eValue);
		float x = x1 - (x1 - x2);
		float y = 0.0F;
		float width = (x1 - x2) * 2.0F;
		float height = 0.0F;
		if (y1 >= y2) {
			height = (y1 - y2) * 2.0F;
		} else {
			height = (y2 - y1) * 2.0F;
		}
		y = y2 - height / 2.0F;
		if (width == 0.0F) {
			width = 1.0F;
		}
		if (height == 0.0F) {
			height = 1.0F;
		}
		if ((width == 1.0F) && (height == 1.0F)) {
			DrawEllipse(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x - 2.0F, y - 2.0F, x + 2.0F, y + 2.0F);
		} else {
			DrawEllipse(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x, y, x + width, y + height);
		}
		if (IsSelected()) {
			DrawSelect(paint, lineColor, x1, y1);
			DrawSelect(paint, lineColor, x2, y2);
		}
	}
}
