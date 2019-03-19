package com.melib.Chart.Plot;

import java.util.HashMap;
import com.melib.Base.RefObject;
import com.melib.Chart.CTableMe;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class P001 extends CPlotBaseMe {
	public P001() {
		SetPlotType("ANDREWSPITCHFORK");
	}

	public ActionType GetAction() {
		ActionType action = ActionType.NO;
		if (this.m_marks.isEmpty()) {
			return action;
		}
		if ((this.m_sourceFields == null) || (!this.m_sourceFields.containsKey("CLOSE"))) {
			return action;
		}
		com.melib.Base.POINT mp = GetMouseOverPoint();
		int aIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0))).GetKey());
		int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(1))).GetKey());
		int cIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(2))).GetKey());
		int dIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(3))).GetKey());
		float x1 = PX(aIndex);
		float x2 = PX(bIndex);
		float x3 = PX(cIndex);
		float x4 = PX(dIndex);
		float y1 = PY(this.m_dataSource.Get2(aIndex, ((Integer) this.m_sourceFields.get("CLOSE")).intValue()));
		float y2 = PY(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(1))).GetValue());
		float y3 = PY(this.m_dataSource.Get2(cIndex, ((Integer) this.m_sourceFields.get("CLOSE")).intValue()));
		float y4 = PY(this.m_dataSource.Get2(dIndex, ((Integer) this.m_sourceFields.get("CLOSE")).intValue()));
		boolean selected = SelectPoint(mp, x1, y1);
		if (selected) {
			action = ActionType.AT1;
			return action;
		}
		selected = SelectPoint(mp, x2, y2);
		if (selected) {
			action = ActionType.AT2;
			return action;
		}
		selected = SelectPoint(mp, x3, y3);
		if (selected) {
			action = ActionType.AT3;
			return action;
		}
		selected = SelectPoint(mp, x4, y4);
		if (selected) {
			action = ActionType.AT4;
			return action;
		}
		float k = 0.0F;
		float b = 0.0F;
		RefObject<Float> tempRef_k = new RefObject(Float.valueOf(k));
		RefObject<Float> tempRef_b = new RefObject(Float.valueOf(b));
		selected = SelectRay(mp, x1, y1, x2, y2, tempRef_k, tempRef_b);
		k = ((Float) tempRef_k.argvalue).floatValue();
		b = ((Float) tempRef_b.argvalue).floatValue();
		if (selected) {
			action = ActionType.MOVE;
			return action;
		}
		int wWidth = GetWorkingAreaWidth();
		if ((k != 0.0F) || (b != 0.0F)) {
			float x3_newx = wWidth;
			if (bIndex < aIndex) {
				x3_newx = 0.0F;
			}
			b = y3 - x3 * k;
			float x3_newy = k * x3_newx + b;
			selected = SelectRay(mp, x3, y3, x3_newx, x3_newy);
			if (selected) {
				action = ActionType.MOVE;
				return action;
			}
			float x4_newx = wWidth;
			if (bIndex < aIndex) {
				x4_newx = 0.0F;
			}
			b = y4 - x4 * k;
			float x4_newy = k * x4_newx + b;
			selected = SelectRay(mp, x4, y4, x4_newx, x4_newy);
			if (selected) {
				action = ActionType.MOVE;
				return action;
			}

		} else if (y1 >= y2) {
			selected = SelectRay(mp, x3, y3, x3, 0.0F);
			if (selected) {
				action = ActionType.MOVE;
				return action;
			}
			selected = SelectRay(mp, x4, y4, x4, 0.0F);
			if (selected) {
				action = ActionType.MOVE;
				return action;
			}
		} else {
			int wHeight = GetWorkingAreaHeight();
			selected = SelectRay(mp, x3, y3, x3, wHeight);
			if (selected) {
				action = ActionType.MOVE;
				return action;
			}
			selected = SelectRay(mp, x4, y4, x4, wHeight);
			if (selected) {
				action = ActionType.MOVE;
				return action;
			}
		}

		return action;
	}

	public boolean OnCreate(com.melib.Base.POINT mp) {
		boolean create = Create4CandlePoints(mp);
		if (create) {
			this.m_marks.put(Integer.valueOf(1),
					new CPlotMarkMe(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(1))).GetIndex(),
							((CPlotMarkMe) this.m_marks.get(Integer.valueOf(1))).GetKey(), GetNumberValue(mp)));
		}
		return create;
	}

	public void OnMoveStart() {
		this.m_action = GetAction();
		this.m_startMarks.clear();
		this.m_startPoint = GetMouseOverPoint();
		if (this.m_action != ActionType.NO) {
			this.m_startMarks.put(Integer.valueOf(0), this.m_marks.get(Integer.valueOf(0)));
			this.m_startMarks.put(Integer.valueOf(1), this.m_marks.get(Integer.valueOf(1)));
			this.m_startMarks.put(Integer.valueOf(2), this.m_marks.get(Integer.valueOf(2)));
			this.m_startMarks.put(Integer.valueOf(3), this.m_marks.get(Integer.valueOf(3)));
		}
	}

	protected void Paint(com.melib.Base.CPaintMe paint, HashMap<Integer, CPlotMarkMe> pList, long lineColor) {
		if (pList.isEmpty()) {
			return;
		}
		if ((this.m_sourceFields == null) || (!this.m_sourceFields.containsKey("CLOSE"))) {
			return;
		}
		int aIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0))).GetKey());
		int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(1))).GetKey());
		int cIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(2))).GetKey());
		int dIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(3))).GetKey());
		float x1 = PX(aIndex);
		float x2 = PX(bIndex);
		float x3 = PX(cIndex);
		float x4 = PX(dIndex);
		float y1 = PY(this.m_dataSource.Get2(aIndex, ((Integer) this.m_sourceFields.get("CLOSE")).intValue()));
		float y2 = PY(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(1))).GetValue());
		float y3 = PY(this.m_dataSource.Get2(cIndex, ((Integer) this.m_sourceFields.get("CLOSE")).intValue()));
		float y4 = PY(this.m_dataSource.Get2(dIndex, ((Integer) this.m_sourceFields.get("CLOSE")).intValue()));
		float k = 0.0F;
		float b = 0.0F;
		RefObject<Float> tempRef_k = new RefObject(Float.valueOf(k));
		RefObject<Float> tempRef_b = new RefObject(Float.valueOf(b));
		com.melib.Base.CMathLibMe.M107(x1, y1, x2, y2, 0.0F, 0.0F, tempRef_k, tempRef_b);
		k = ((Float) tempRef_k.argvalue).floatValue();
		b = ((Float) tempRef_b.argvalue).floatValue();
		DrawRay(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x1, y1, x2, y2, k, b);
		if ((k != 0.0F) || (b != 0.0F)) {
			float x3_newx = GetWorkingAreaWidth();
			if (bIndex < aIndex) {
				x3_newx = 0.0F;
			}
			b = y3 - x3 * k;
			float x3_newy = k * x3_newx + b;
			DrawRay(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x3, y3, x3_newx, x3_newy, k, b);
			float x4_newx = GetWorkingAreaWidth();
			if (bIndex < aIndex) {
				x4_newx = 0.0F;
			}
			b = y4 - x4 * k;
			float x4_newy = k * x4_newx + b;
			DrawRay(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x4, y4, x4_newx, x4_newy, k, b);

		} else if (y1 >= y2) {
			DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x3, y3, x3, 0.0F);
			DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x4, y4, x4, 0.0F);
		} else {
			DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x3, y3, x3, GetWorkingAreaHeight());
			DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x4, y4, x4, GetWorkingAreaHeight());
		}

		if (IsSelected()) {
			DrawSelect(paint, lineColor, x1, y1);
			DrawSelect(paint, lineColor, x2, y2);
			DrawSelect(paint, lineColor, x3, y3);
			DrawSelect(paint, lineColor, x4, y4);
		}
	}
}
