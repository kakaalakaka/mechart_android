package com.melib.Chart.Plot;

import java.util.HashMap;
import com.melib.Base.POINT;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class P010 extends CPlotBaseMe {
	public P010() {
		SetPlotType("FIBOELLIPSE");
	}

	private float[] FibonacciEllipseParam(HashMap<Integer, CPlotMarkMe> pList) {
		if (pList.isEmpty()) {
			return null;
		}
		int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetKey());
		int eIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetKey());
		float x1 = PX(bIndex);
		float x2 = PX(eIndex);
		float y1 = PY(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetValue());
		float y2 = PY(((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetValue());
		float r1 = (float) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
		float r2 = r1 * 0.236F;
		float r3 = r1 * 0.382F;
		float r4 = r1 * 0.5F;
		float r5 = r1 * 0.618F;
		return new float[] { x1, y1, x2, y2, r1, r2, r3, r4, r5 };
	}

	public ActionType GetAction() {
		ActionType action = ActionType.NO;
		if (this.m_marks.isEmpty()) {
			return action;
		}
		POINT mp = GetMouseOverPoint();
		float[] param = FibonacciEllipseParam(this.m_marks);
		float x1 = param[0];
		float y1 = param[1];
		float x2 = param[2];
		float y2 = param[3];
		if ((SelectPoint(mp, x1, y1)) || (this.m_moveTimes == 1)) {
			action = ActionType.AT1;
			return action;
		}
		if (SelectPoint(mp, x2, y2)) {
			action = ActionType.AT2;
			return action;
		}
		if (SelectSegment(mp, x1, y1, x2, y2)) {
			action = ActionType.MOVE;
			return action;
		}
		POINT p = new POINT(mp.x - x1, mp.y - y1);
		float round = p.x * p.x + p.y * p.y;
		for (int i = 4; i < 9; i++) {
			float r = param[i];
			if ((round / (r * r) >= 0.9D) && (round / (r * r) <= 1.1D)) {
				action = ActionType.MOVE;
				return action;
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
		float[] param = FibonacciEllipseParam(pList);
		float x1 = param[0];
		float y1 = param[1];
		float x2 = param[2];
		float y2 = param[3];
		DrawLine(paint, lineColor, this.m_lineWidth, 1, x1, y1, x2, y2);
		float r1 = param[4] >= 4.0F ? param[4] : 4.0F;
		float r2 = param[5] >= 4.0F ? param[5] : 4.0F;
		float r3 = param[6] >= 4.0F ? param[6] : 4.0F;
		float r4 = param[7] >= 4.0F ? param[7] : 4.0F;
		float r5 = param[8] >= 4.0F ? param[8] : 4.0F;
		DrawEllipse(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x1 - r1, y1 - r1, x1 + r1, y1 + r1);
		DrawEllipse(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x1 - r2, y1 - r2, x1 + r2, y1 + r2);
		DrawEllipse(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x1 - r3, y1 - r3, x1 + r3, y1 + r3);
		DrawEllipse(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x1 - r4, y1 - r4, x1 + r4, y1 + r4);
		DrawEllipse(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x1 - r5, y1 - r5, x1 + r5, y1 + r5);
		if (IsSelected()) {
			DrawSelect(paint, lineColor, x1, y1);
			DrawSelect(paint, lineColor, x2, y2);
		}
		if (r5 > 20.0F) {
			com.melib.Base.SIZE sizeF = TextSize(paint, "23.6%", this.m_font);
			DrawText(paint, "23.6%", lineColor, this.m_font, x1 - sizeF.cx / 2, y1 - r1 - sizeF.cy);
			sizeF = TextSize(paint, "38.2%", this.m_font);
			DrawText(paint, "38.2%", lineColor, this.m_font, x1 - sizeF.cx / 2, y1 - r2 - sizeF.cy);
			sizeF = TextSize(paint, "50.0%", this.m_font);
			DrawText(paint, "50.0%", lineColor, this.m_font, x1 - sizeF.cx / 2, y1 - r3 - sizeF.cy);
			sizeF = TextSize(paint, "61.8%", this.m_font);
			DrawText(paint, "61.8%", lineColor, this.m_font, x1 - sizeF.cx / 2, y1 - r4 - sizeF.cy);
			sizeF = TextSize(paint, "100%", this.m_font);
			DrawText(paint, "100%", lineColor, this.m_font, x1 - sizeF.cx / 2, y1 - r5 - sizeF.cy);
		}
	}
}
