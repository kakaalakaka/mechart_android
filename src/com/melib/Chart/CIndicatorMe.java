package com.melib.Chart;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public abstract class CIndicatorMe {
	public HashMap<Integer, CVarMe> m_tempVars = new HashMap();

	public abstract AttachVScale GetAttachVScale();

	public abstract void SetAttachVScale(AttachVScale paramAttachVScale);

	public abstract CTableMe GetDataSource();

	public abstract void SetDataSource(CTableMe paramCTable);

	public abstract CDivMe GetDiv();

	public abstract void SetDiv(CDivMe paramCDiv);

	public abstract int GetIndex();

	public abstract boolean IsDisposed();

	public abstract HashMap<String, Integer> GetMainVariables();

	public abstract String GetName();

	public abstract void SetName(String paramString);

	public abstract double GetResult();

	public abstract void SetScript(String paramString);

	public abstract ArrayList<Long> GetSystemColors();

	public abstract void SetSystemColors(ArrayList<Long> paramArrayList);

	public abstract Object GetTag();

	public abstract void SetTag(Object paramObject);

	public abstract CVarFactoryMe GetVarFactory();

	public abstract void SetVarFactory(CVarFactoryMe paramCVarFactory);

	public abstract void AddFunction(CFunctionMe paramCFunction);

	public abstract double CallFunction(String paramString);

	public abstract void Clear();

	public abstract void Dispose();

	public abstract ArrayList<CFunctionMe> GetFunctions();

	public abstract ArrayList<CBaseShapeMe> GetShapes();

	public abstract String GetText(CVariableMe paramCVariable);

	public abstract double GetValue(CVariableMe paramCVariable);

	public abstract CVariableMe GetVariable(String paramString);

	public abstract void OnCalculate(int paramInt);

	public abstract void RemoveFunction(CFunctionMe paramCFunction);

	public abstract void SetSourceField(String paramString, int paramInt);

	public abstract void SetSourceValue(String paramString, double paramDouble);

	public abstract void SetVariable(CVariableMe paramCVariable1, CVariableMe paramCVariable2);
}
