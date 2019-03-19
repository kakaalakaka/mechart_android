package com.melib.Chart;

import java.util.HashMap;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class CVarMe {
	public java.util.ArrayList<String> m_list;
	public HashMap<String, String> m_map;
	public double m_num;
	public String m_str;
	public int m_type;
	public CVarMe m_parent;

	protected void finalize() throws Throwable {
		Dispose();
	}

	public void Dispose() {
		if (this.m_list != null) {
			this.m_list.clear();
		}
		if (this.m_map != null) {
			this.m_map.clear();
		}
		this.m_parent = null;
	}

	public String GetText(CIndicatorMe indicator, CVariableMe name) {
		if (this.m_type == 1) {
			if ((this.m_str.length() > 0) && (this.m_str.startsWith("'"))) {
				return this.m_str.substring(1, this.m_str.length() - 1);
			}

			return this.m_str;
		}

		return com.melib.Base.CStrMe.ConvertDoubleToStr(this.m_num);
	}

	public double GetValue(CIndicatorMe indicator, CVariableMe name) {
		if (this.m_type == 1) {
			return com.melib.Base.CStrMe.ConvertStrToDouble(this.m_str.replace("'", ""));
		}

		return this.m_num;
	}

	public double OnCreate(CIndicatorMe indicator, CVariableMe name, CVariableMe value) {
		double result = 0.0D;
		int id = name.m_field;
		if ((value.m_expression.length() > 0) && (value.m_expression.startsWith("'"))) {
			this.m_type = 1;
			this.m_str = value.m_expression.substring(1, value.m_expression.length() - 1);

		} else if (value.m_expression.equals("LIST")) {
			this.m_type = 2;
			this.m_list = new java.util.ArrayList();
		} else if (value.m_expression.equals("MAP")) {
			this.m_type = 3;
			this.m_map = new HashMap();
		} else if (indicator.m_tempVars.containsKey(Integer.valueOf(value.m_field))) {
			CVarMe otherCVar = (CVarMe) indicator.m_tempVars.get(Integer.valueOf(value.m_field));
			if (otherCVar.m_type == 1) {
				this.m_type = 1;
				this.m_str = otherCVar.m_str;
			} else {
				this.m_type = 0;
				this.m_num = otherCVar.m_num;
			}
		} else {
			this.m_type = 0;
			result = indicator.GetValue(value);
			this.m_num = result;
		}

		return result;
	}

	public void SetValue(CIndicatorMe indicator, CVariableMe name, CVariableMe value) {
		if (this.m_type == 1) {
			this.m_str = indicator.GetText(value);
		} else {
			this.m_num = indicator.GetValue(value);
		}
	}
}
