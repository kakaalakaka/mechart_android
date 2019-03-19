package com.melib.Chart.Plot;

import java.util.HashMap;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class PlotSymmetricTriangle extends CPlotBaseMe {
	public PlotSymmetricTriangle() {
		SetPlotType("SYMMETRICTRIANGLE");
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
		float[] param = GetSymmetricTriangleParams(this.m_marks);
		if (param != null) {
			if (SelectPoint(mp, x1, y1)) {
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
			if ((mp.y - param[0] * mp.x - param[1] >= this.m_lineWidth * -5)
					&& (mp.y - param[0] * mp.x - param[1] <= this.m_lineWidth * 5)) {
				action = ActionType.MOVE;
				return action;
			}
			if ((mp.y - param[2] * mp.x - param[3] >= this.m_lineWidth * -5)
					&& (mp.y - param[2] * mp.x - param[3] <= this.m_lineWidth * 5)) {
				action = ActionType.MOVE;
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
			if ((mp.y >= y3 - this.m_lineWidth * 5) && (mp.y <= y3 + this.m_lineWidth * 5)) {
				action = ActionType.AT3;
				return action;
			}
			if ((mp.x >= x1 - this.m_lineWidth * 5) && (mp.x <= x1 + this.m_lineWidth * 5)) {
				action = ActionType.MOVE;
				return action;
			}
			if ((mp.x >= x3 - this.m_lineWidth * 5) && (mp.x <= x3 + this.m_lineWidth * 5)) {
				action = ActionType.MOVE;
				return action;
			}
		}
		return action;
	}

	private float[] GetSymmetricTriangleParams(HashMap<Integer, CPlotMarkMe> pList) {
		if (pList.isEmpty()) {
			return null;
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
		float a = 0.0F;
		if (x2 - x1 != 0.0F) {
			a = (y2 - y1) / (x2 - x1);
			float b = y1 - a * x1;
			float c = -a;
			float d = y3 - c * x3;
			return new float[] { a, b, c, d };
		}

		return null;
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
		float[] param = GetSymmetricTriangleParams(pList);
		float a = 0.0F;
		float b = 0.0F;
		float c = 0.0F;
		float d = 0.0F;
		float x1 = PX(bIndex);
		float x2 = PX(eIndex);
		float x3 = PX(pIndex);
		if (param != null) {
			a = param[0];
			b = param[1];
			c = param[2];
			d = param[3];
			float leftX = 0.0F;
			float leftY = leftX * a + b;
			float rightX = GetWorkingAreaWidth();
			float rightY = rightX * a + b;
			DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, leftX, leftY, rightX, rightY);
			leftY = leftX * c + d;
			rightY = rightX * c + d;
			DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, leftX, leftY, rightX, rightY);
		} else {
			DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x1, 0.0F, x1, GetWorkingAreaHeight());
			DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x3, 0.0F, x3, GetWorkingAreaHeight());
		}
		if (IsSelected()) {
			DrawSelect(paint, lineColor, x1, y1);
			DrawSelect(paint, lineColor, x2, y2);
			DrawSelect(paint, lineColor, x3, y3);
		}
	}
}
