package com.melib.Chart.Plot;

import java.util.HashMap;
import com.melib.Base.POINT;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class PlotWavRuler extends CPlotBaseMe {
	public PlotWavRuler() {
		SetPlotType("WAVERULER");
	}

	public ActionType GetAction() {
		ActionType action = ActionType.NO;
		if (this.m_marks.isEmpty()) {
			return action;
		}
		POINT mp = GetMouseOverPoint();
		float[] param = GetWaveRulerParams(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0))).GetValue(),
				((CPlotMarkMe) this.m_marks.get(Integer.valueOf(1))).GetValue());
		int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0))).GetKey());
		int eIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(1))).GetKey());
		float x1 = PX(bIndex);
		float x2 = PX(eIndex);
		float y1 = PY(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0))).GetValue());
		float y2 = param[(param.length - 1)];
		if ((SelectPoint(mp, x1, y1)) || (this.m_moveTimes == 1)) {
			action = ActionType.AT1;
			return action;
		}
		if (SelectPoint(mp, x2, y2)) {
			action = ActionType.AT2;
			return action;
		}
		float smallY = param[0] < param[(param.length - 1)] ? param[0] : param[(param.length - 1)];
		float bigY = param[0] >= param[(param.length - 1)] ? param[0] : param[(param.length - 1)];
		float mid = x1 >= x2 ? x2 + (x1 - x2) / 2.0F : x1 + (x2 - x1) / 2.0F;
		if ((mp.x >= mid - this.m_lineWidth * 2.5F) && (mp.x <= mid + this.m_lineWidth * 2.5F)
				&& (mp.y >= smallY - this.m_lineWidth * 2.5F) && (mp.y <= bigY + this.m_lineWidth * 2.5F)) {
			action = ActionType.MOVE;
			return action;
		}
		float top = 0.0F;
		float bottom = GetWorkingAreaWidth();
		if ((mp.y >= top) && (mp.y <= bottom)) {
			for (float p : param) {
				if ((mp.x >= 0) && (mp.x <= GetWorkingAreaWidth()) && (mp.y >= p - this.m_lineWidth * 2.5F)
						&& (mp.y <= p + this.m_lineWidth * 2.5F)) {
					action = ActionType.MOVE;
					return action;
				}
			}
		}
		return action;
	}

	private float[] GetWaveRulerParams(double value1, double value2) {
		float y1 = PY(value1);
		float y2 = PY(value2);
		float y0 = 0.0F;
		float yA = 0.0F;
		float yB = 0.0F;
		float yC = 0.0F;
		float yD = 0.0F;
		float yE = 0.0F;
		float yF = 0.0F;
		float yG = 0.0F;
		float yH = 0.0F;
		float yI = 0.0F;
		float yMax = 0.0F;
		y0 = y1;
		yA = y1 <= y2 ? y1 + (y2 - y1) * 0.09014515F : y2 + (y1 - y2) * 0.9098548F;
		yB = y1 <= y2 ? y1 + (y2 - y1) * 0.13827349F : y2 + (y1 - y2) * 0.8617265F;
		yC = y1 <= y2 ? y1 + (y2 - y1) * 0.19098549F : y2 + (y1 - y2) * 0.8090145F;
		yD = y1 <= y2 ? y1 + (y2 - y1) * 0.23605804F : y2 + (y1 - y2) * 0.76394194F;
		yE = y1 <= y2 ? y1 + (y2 - y1) * 0.38197097F : y2 + (y1 - y2) * 0.618029F;
		yF = y1 <= y2 ? y1 + (y2 - y1) * 0.5278839F : y2 + (y1 - y2) * 0.4721161F;
		yG = y1 <= y2 ? y1 + (y2 - y1) * 0.61802906F : y2 + (y1 - y2) * 0.38197094F;
		yH = y1 <= y2 ? y1 + (y2 - y1) * 0.76394194F : y2 + (y1 - y2) * 0.23605806F;
		yI = y1 <= y2 ? y1 + (y2 - y1) * 0.9098548F : y2 + (y1 - y2) * 0.09014517F;
		yMax = y2;
		return new float[] { y0, yA, yB, yC, yD, yE, yF, yG, yH, yI, yMax };
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
		float y1 = PY(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetValue());
		float y2 = PY(((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetValue());
		int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetKey());
		int eIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetKey());
		float x1 = PX(bIndex);
		float x2 = PX(eIndex);
		float[] lineParam = GetWaveRulerParams(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetValue(),
				((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetValue());
		String[] str = { "0.00%", "23.60%", "38.20%", "50.00%", "61.80%", "100.00%", "138.20%", "161.80%", "200%",
				"238.20%", "261.80%" };
		float mid = x1 >= x2 ? x2 + (x1 - x2) / 2.0F : x1 + (x2 - x1) / 2.0F;
		DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, mid, lineParam[0], mid,
				lineParam[(lineParam.length - 1)]);
		for (int i = 0; i < lineParam.length; i++) {
			com.melib.Base.SIZE sizeF = TextSize(paint, str[i], this.m_font);
			float yP = lineParam[i];
			DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x1, yP, x2, yP);

			DrawText(paint, str[i], lineColor, this.m_font, mid, yP - sizeF.cy);
		}
		if (IsSelected()) {
			DrawSelect(paint, lineColor, x1, y1);
			DrawSelect(paint, lineColor, x2, y2);
		}
	}
}
