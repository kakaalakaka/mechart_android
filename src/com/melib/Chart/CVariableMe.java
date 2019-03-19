package com.melib.Chart;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class CVariableMe {
	public CBarShapeMe m_barShape;
	public CCandleShapeMe m_candleShape;
	public String m_expression;

	public CVariableMe(CIndicatorMe indicator) {
		this.m_indicator = indicator;
	}

	public int m_field = CTableMe.NULLFIELD;

	public int m_fieldIndex = -1;

	public String m_fieldText;

	public int m_functionID = -1;

	public String m_funcName;

	public CIndicatorMe m_indicator;

	public int m_line = -1;

	public CPolylineShapeMe m_polylineShape;

	public String m_name;

	public CVariableMe[] m_parameters;

	public CMathElementMe[] m_splitExpression;

	public int[] m_tempFields;

	public int[] m_tempFieldsIndex;

	public CTextShapeMe m_textShape;

	public int m_type = 0;

	public double m_value;

	public void CreateTempFields(int count) {
		this.m_tempFields = new int[count];
		this.m_tempFieldsIndex = new int[count];
		for (int i = 0; i < count; i++) {
			int field = CTableMe.GetAutoField();
			this.m_tempFields[i] = field;
			this.m_indicator.GetDataSource().AddColumn(field);
			this.m_tempFieldsIndex[i] = this.m_indicator.GetDataSource().GetColumnIndex(field);
		}
	}
}
