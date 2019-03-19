package com.melib.Chart.Plot;

import java.util.ArrayList;
import java.util.HashMap;
import com.melib.Base.POINT;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class P002 extends CPlotBaseMe {
	public P002() {
		SetPlotType("ANGLELINE");
	}

	public ActionType GetAction() {
		ActionType action = ActionType.NO;
		if (this.m_marks.isEmpty()) {
			return action;
		}
		POINT mp = GetMouseOverPoint();
		ArrayList<CPlotMarkMe[]> mList = new ArrayList();
		mList.add(new CPlotMarkMe[] { (CPlotMarkMe) this.m_marks.get(Integer.valueOf(0)),
				(CPlotMarkMe) this.m_marks.get(Integer.valueOf(1)) });
		mList.add(new CPlotMarkMe[] { (CPlotMarkMe) this.m_marks.get(Integer.valueOf(0)),
				(CPlotMarkMe) this.m_marks.get(Integer.valueOf(2)) });
		int listSize = mList.size();
		for (int i = 0; i < listSize; i++) {
			CPlotMarkMe markA = ((CPlotMarkMe[]) mList.get(i))[0];
			CPlotMarkMe markB = ((CPlotMarkMe[]) mList.get(i))[1];
			int bIndex = this.m_dataSource.GetRowIndex(markA.GetKey());
			int eIndex = this.m_dataSource.GetRowIndex(markB.GetKey());
			float x1 = PX(bIndex);
			float x2 = PX(eIndex);
			float y1 = PY(markA.GetValue());
			float y2 = PY(markB.GetValue());
			if (SelectPoint(mp, x1, y1)) {
				action = ActionType.AT1;
				return action;
			}
			if (SelectPoint(mp, x2, y2)) {
				if (i == 0) {
					action = ActionType.AT2;
					return action;
				}

				action = ActionType.AT3;
				return action;
			}

			float k = 0.0F;
			float b = 0.0F;
			com.melib.Base.RefObject<Float> tempRef_k = new com.melib.Base.RefObject(Float.valueOf(k));
			com.melib.Base.RefObject<Float> tempRef_b = new com.melib.Base.RefObject(Float.valueOf(b));
			com.melib.Base.CMathLibMe.M107(x1, y1, x2, y2, 0.0F, 0.0F, tempRef_k, tempRef_b);
			k = ((Float) tempRef_k.argvalue).floatValue();
			b = ((Float) tempRef_b.argvalue).floatValue();
			if ((k != 0.0F) || (b != 0.0F)) {
				if ((mp.y / (mp.x * k + b) >= 0.9D) && (mp.y / (mp.x * k + b) <= 1.1D)) {
					if (x1 >= x2) {
						if (mp.x > x1 + 5.0F) {
							action = ActionType.NO;
							return action;
						}
					} else if (x1 < x2) {
						if (mp.x < x1 - 5.0F) {
							action = ActionType.NO;
							return action;
						}
					}
					action = ActionType.MOVE;
					return action;
				}

			} else if ((mp.x >= x1 - this.m_lineWidth * 5) && (mp.x <= x1 + this.m_lineWidth * 5)) {
				if (y1 >= y2) {
					if (mp.y <= y1 - 5.0F) {
						action = ActionType.MOVE;
						return action;
					}

				} else if (mp.y >= y1 - 5.0F) {
					action = ActionType.MOVE;
					return action;
				}
			}
		}

		return action;
	}

	public boolean OnCreate(POINT mp) {
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
		ArrayList<CPlotMarkMe[]> marks = new ArrayList();
		marks.add(
				new CPlotMarkMe[] { (CPlotMarkMe) pList.get(Integer.valueOf(0)), (CPlotMarkMe) pList.get(Integer.valueOf(1)) });
		marks.add(
				new CPlotMarkMe[] { (CPlotMarkMe) pList.get(Integer.valueOf(0)), (CPlotMarkMe) pList.get(Integer.valueOf(2)) });
		int markSize = marks.size();
		for (int i = 0; i < markSize; i++) {
			CPlotMarkMe markA = ((CPlotMarkMe[]) marks.get(i))[0];
			CPlotMarkMe markB = ((CPlotMarkMe[]) marks.get(i))[1];
			float y1 = PY(markA.GetValue());
			float y2 = PY(markB.GetValue());
			int bIndex = this.m_dataSource.GetRowIndex(markA.GetKey());
			int eIndex = this.m_dataSource.GetRowIndex(markB.GetKey());
			float[] param = GetLineParams(markA, markB);
			float a = 0.0F;
			float b = 0.0F;
			float x1 = PX(bIndex);
			float x2 = PX(eIndex);
			if (param != null) {
				a = param[0];
				b = param[1];
				float leftX = 0.0F;
				float leftY = leftX * a + b;
				float rightX = GetWorkingAreaWidth();
				float rightY = rightX * a + b;
				if (x1 >= x2) {
					DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, leftX, leftY, x2, y2);
				} else {
					DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x1, y1, rightX, rightY);
				}
				DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x1, y1, x2, y2);

			} else if (y1 >= y2) {
				DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x1, y1, x2, 0.0F);
			} else {
				DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x1, y1, x2, GetWorkingAreaHeight());
			}

			if (IsSelected()) {
				DrawSelect(paint, lineColor, x1, y1);
				DrawSelect(paint, lineColor, x2, y2);
			}
		}
	}
}
