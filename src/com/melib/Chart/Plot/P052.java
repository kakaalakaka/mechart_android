package com.melib.Chart.Plot;

import java.util.HashMap;
import com.melib.Base.RefObject;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class P052 extends P025 {
	public P052() {
		SetPlotType("PRICECHANNEL");
	}

	public ActionType GetAction() {
		ActionType action = super.GetAction();
		if (action != ActionType.NO) {
			return action;
		}
		com.melib.Base.POINT mp = GetMouseOverPoint();
		float k = 0.0F;
		float d = 0.0F;
		float x4 = 0.0F;
		RefObject<Float> tempRef_k = new RefObject(Float.valueOf(k));
		RefObject<Float> tempRef_d = new RefObject(Float.valueOf(d));
		RefObject<Float> tempRef_x4 = new RefObject(Float.valueOf(x4));
		GetLine3Params(this.m_marks, tempRef_k, tempRef_d, tempRef_x4);
		k = ((Float) tempRef_k.argvalue).floatValue();
		d = ((Float) tempRef_d.argvalue).floatValue();
		x4 = ((Float) tempRef_x4.argvalue).floatValue();
		if ((k == 0.0F) && (d == 0.0F)) {
			if ((mp.x >= x4 - this.m_lineWidth * 5) && (mp.x <= x4 + this.m_lineWidth * 5)) {
				action = ActionType.MOVE;
			}

		} else if (SelectLine(mp, PX(mp.x), k, d)) {
			action = ActionType.MOVE;
		}

		return action;
	}

	private void GetLine3Params(HashMap<Integer, CPlotMarkMe> marks, RefObject<Float> k, RefObject<Float> d,
			RefObject<Float> x4) {
		int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0))).GetKey());
		int pIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(2))).GetKey());
		float x1 = PX(bIndex);
		float x3 = PX(pIndex);
		float[] param = GetParallelParams(this.m_marks);
		if (param != null) {
			k.argvalue = Float.valueOf(param[0]);
			float b = param[1];
			float c = param[2];
			d.argvalue = Float.valueOf(b >= c ? b + (b - c) : b - (c - b));
		} else {
			x4.argvalue = Float.valueOf(0.0F);
			if (x3 > x1) {
				x4.argvalue = Float.valueOf(x1 - (x3 - x1));
			} else {
				x4.argvalue = Float.valueOf(x1 + (x1 - x3));
			}
		}
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

	public void OnPaint(com.melib.Base.CPaintMe paint) {
		PaintEx(paint, this.m_marks, GetColor());
		super.OnPaint(paint);
	}

	public void OnPaintGhost(com.melib.Base.CPaintMe paint) {
		PaintEx(paint, this.m_startMarks, GetSelectedColor());
		super.OnPaintGhost(paint);
	}

	private void PaintEx(com.melib.Base.CPaintMe paint, HashMap<Integer, CPlotMarkMe> pList, long lineColor) {
		if (pList.isEmpty()) {
			return;
		}
		float k = 0.0F;
		float d = 0.0F;
		float x4 = 0.0F;
		RefObject<Float> tempRef_k = new RefObject(Float.valueOf(k));
		RefObject<Float> tempRef_d = new RefObject(Float.valueOf(d));
		RefObject<Float> tempRef_x4 = new RefObject(Float.valueOf(x4));
		GetLine3Params(this.m_marks, tempRef_k, tempRef_d, tempRef_x4);
		k = ((Float) tempRef_k.argvalue).floatValue();
		d = ((Float) tempRef_d.argvalue).floatValue();
		x4 = ((Float) tempRef_x4.argvalue).floatValue();
		if ((k == 0.0F) && (d == 0.0F)) {
			DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x4, 0.0F, x4, GetWorkingAreaHeight());

		} else {
			float leftX = 0.0F;
			float leftY = leftX * k + d;
			float rightX = GetWorkingAreaWidth();
			float rightY = rightX * k + d;
			DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, leftX, leftY, rightX, rightY);
		}
	}
}
