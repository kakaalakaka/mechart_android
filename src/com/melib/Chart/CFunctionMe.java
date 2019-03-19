package com.melib.Chart;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class CFunctionMe {
	public int m_ID;
	public String m_name;
	public int m_type;

	public CFunctionMe() {
	}

	public CFunctionMe(int id, String name) {
		this.m_ID = id;
		this.m_name = name;
	}

	public double OnCalculate(CVariableMe var) {
		return 0.0D;
	}
}
