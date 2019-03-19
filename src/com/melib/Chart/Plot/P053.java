package com.melib.Chart.Plot;

import java.util.HashMap;
import com.melib.Base.POINT;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class P053 extends CPlotBaseMe {
	public P053() {
		SetPlotType("GP");
	}

	public ActionType GetAction() {
		ActionType action = ActionType.NO;
		if (this.m_marks.isEmpty()) {
			return action;
		}
		POINT mp = GetMouseOverPoint();
		float y1 = PY(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0))).GetValue());
		if ((mp.y >= y1 - this.m_lineWidth * 2.5F) && (mp.y <= y1 + this.m_lineWidth * 2.5F)) {
			action = ActionType.MOVE;
		} else {
			double[] list = { 0.236D, 0.382D, 0.5D, 0.618D, 0.819D, 1.191D, 1.382D, 1.618D, 2.0D, 2.382D, 2.618D };
			for (int i = 0; i < list.length; i++) {
				float yP = PY(list[i] * ((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0))).GetValue());
				if ((mp.y >= yP - this.m_lineWidth * 2.5F) && (mp.y <= yP + this.m_lineWidth * 2.5F)) {
					action = ActionType.MOVE;
					break;
				}
			}
		}
		return action;
	}

	public boolean OnCreate(POINT mp) {
		return CreatePoint(mp);
	}

	public void OnMoveStart() {
		this.m_action = GetAction();
		this.m_startMarks.clear();
		this.m_startPoint = GetMouseOverPoint();
		if (this.m_action != ActionType.NO) {
			this.m_startMarks.put(Integer.valueOf(0), this.m_marks.get(Integer.valueOf(0)));
		}
	}

	public void OnMoving() {
		POINT mp = GetMovingPoint();
		switch (this.m_action) {
		case MOVE:
			Move(mp);
		}

	}

	protected void Paint(com.melib.Base.CPaintMe paint, HashMap<Integer, CPlotMarkMe> pList, long lineColor) {
		if (pList.isEmpty()) {
			return;
		}
		float y1 = PY(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetValue());
		int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetKey());
		float x1 = PX(bIndex);
		DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, 0.0F, y1, GetWorkingAreaWidth(), y1);
		double[] list = { 0.236D, 0.382D, 0.5D, 0.618D, 0.819D, 1.191D, 1.382D, 1.618D, 2.0D, 2.382D, 2.618D };
		for (int i = 0; i < list.length; i++) {
			float yP = PY(list[i] * ((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetValue());
			String str = String.format("%.2f", new Object[] { Double.valueOf(list[i] * 100.0D) }) + "%";
			com.melib.Base.SIZE sizeF = TextSize(paint, str, this.m_font);
			DrawLine(paint, lineColor, this.m_lineWidth, 1, 0.0F, yP, GetWorkingAreaWidth(), yP);
			DrawText(paint, str, lineColor, this.m_font, GetWorkingAreaWidth() - sizeF.cx, yP - sizeF.cy);
		}
		if (IsSelected()) {
			DrawSelect(paint, lineColor, x1, y1);
		}
	}
}
