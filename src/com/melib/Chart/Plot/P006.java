package com.melib.Chart.Plot;

import java.util.HashMap;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class P006 extends CPlotBaseMe {
	public P006() {
		SetPlotType("DROPLINE");
	}

	public ActionType GetAction() {
		ActionType action = ActionType.NO;
		if (this.m_marks.isEmpty()) {
			return action;
		}
		com.melib.Base.POINT mp = GetMouseOverPoint();
		float[] param = GetDropLineParams(this.m_marks);
		if (param != null) {
			if ((mp.y - param[0] * mp.x - param[1] >= this.m_lineWidth * -5)
					&& (mp.y - param[0] * mp.x - param[1] <= this.m_lineWidth * 5)) {
				action = ActionType.MOVE;
			}
		}
		return action;
	}

	private float[] GetDropLineParams(HashMap<Integer, CPlotMarkMe> pList) {
		if (pList.isEmpty()) {
			return null;
		}
		float y1 = PY(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetValue());
		int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetKey());
		float x1 = PX(bIndex);
		float a = 1.0F;
		float b = y1 - x1;
		return new float[] { a, b };
	}

	public boolean OnCreate(com.melib.Base.POINT mp) {
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

	protected void Paint(com.melib.Base.CPaintMe paint, HashMap<Integer, CPlotMarkMe> pList, long lineColor) {
		if (pList.isEmpty()) {
			return;
		}
		float[] param = GetDropLineParams(pList);
		float a = param[0];
		float b = param[1];
		float leftX = 0.0F;
		float leftY = leftX * a + b;
		float rightX = GetWorkingAreaWidth();
		float rightY = rightX * a + b;
		DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, leftX, leftY, rightX, rightY);
	}
}
