package com.melib.Indicator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import com.melib.Base.CMathLibMe;
import com.melib.Base.COLOR;
import com.melib.Base.CStrMe;
import com.melib.Base.RefObject;
import com.melib.Chart.AttachVScale;
import com.melib.Chart.CBarShapeMe;
import com.melib.Chart.BarStyle;
import com.melib.Chart.CBaseShapeMe;
import com.melib.Chart.CDivMe;
import com.melib.Chart.CFunctionMe;
import com.melib.Chart.CIndicatorMe;
import com.melib.Chart.CMathElementMe;
import com.melib.Chart.CTableMe;
import com.melib.Chart.CTitleMe;
import com.melib.Chart.CVarMe;
import com.melib.Chart.CVarFactoryMe;
import com.melib.Chart.CVariableMe;
import com.melib.Chart.CCandleShapeMe;
import com.melib.Chart.CandleStyle;
import com.melib.Chart.CChartMe;
import com.melib.Chart.CPolylineShapeMe;
import com.melib.Chart.PolylineStyle;
import com.melib.Chart.CTextShapeMe;
import com.melib.Chart.CTitleBarMe;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class CIndicatorMeEx extends CIndicatorMe {
	private HashMap<String, Double> m_defineParams;

	public CIndicatorMeEx() {
		this.m_mainVariables = new HashMap();
		this.m_variables = new ArrayList();
		this.m_defineParams = new HashMap();
		this.m_lines = new ArrayList();
		String[] functions = FUNCTIONS.split("[,]");
		String[] fields = FUNCTIONS_FIELD.split("[,]");
		int funcLen = functions.length;
		int fieldLen = fields.length;
		for (int i = 0; i < funcLen; i++) {
			int cType = 0;
			for (int j = 0; j < fieldLen; j++) {
				if (functions[i].equals(fields[j])) {
					cType = 1;
					break;
				}
			}
			CFunctionMe funcI = new CFunctionMe(i, functions[i]);
			funcI.m_type = cType;
			this.m_functions.put(funcI.m_name, funcI);
		}
		this.m_systemColors.add(Long.valueOf(COLOR.ARGB(255, 255, 255)));
		this.m_systemColors.add(Long.valueOf(COLOR.ARGB(255, 255, 0)));
		this.m_systemColors.add(Long.valueOf(COLOR.ARGB(255, 0, 255)));
		this.m_systemColors.add(Long.valueOf(COLOR.ARGB(0, 255, 0)));
		this.m_systemColors.add(Long.valueOf(COLOR.ARGB(82, 255, 255)));
		this.m_systemColors.add(Long.valueOf(COLOR.ARGB(255, 82, 82)));
		this.m_varFactory = new CVarFactoryMe();
	}

	protected void finalize() throws Throwable {
		Dispose();
	}

	private static String FUNCTIONS = "CURRBARSCOUNT,BARSCOUNT,DRAWKLINE,STICKLINE,VALUEWHEN,BARSLAST,DOWNNDAY,DRAWICON,DRAWNULL,FUNCTION,FUNCVAR,DRAWTEXT,POLYLINE,BETWEEN,CEILING,EXPMEMA,HHVBARS,INTPART,LLVBARS,DOTIMES,DOWHILE,CONTINUE,RETURN,REVERSE,AVEDEV,MINUTE,SQUARE,UPNDAY,DELETE,COUNT,CROSS,EVERY,EXIST,EXPMA,FLOOR,MONTH,ROUND,TIME2,WHILE,BREAK,CHUNK,ACOS,ASIN,ATAN,DATE,HOUR,LAST,MEMA,NDAY,RAND,SIGN,SQRT,TIME,YEAR,ABS,AMA,COS,DAY,DMA,EMA,EXP,HHV,IFF,IFN,LLV,LOG,MAX,MIN,MOD,NOT,POW,SIN,SMA,STD,SUM,TAN,REF,SAR,FOR,GET,SET,TMA,VAR,WMA,ZIG,IF,MA,STR.CONTACT,STR.EQUALS,STR.FIND,STR.FINDLAST,STR.LENGTH,STR.SUBSTR,STR.REPLACE,STR.SPLIT,STR.TOLOWER,STR.TOUPPER,LIST.ADD,LIST.CLEAR,LIST.GET,LIST.INSERT,LIST.REMOVE,LIST.SIZE,MAP.CLEAR,MAP.CONTAINSKEY,MAP.GET,MAP.GETKEYS,MAP.REMOVE,MAP.SET,MAP.SIZE";

	private int m_break;

	private static int FUNCTIONID_FUNCVAR = 10;

	private static int FUNCTIONID_FUNCTION = 9;

	private static int FUNCTIONID_VAR = 82;

	private static String FUNCTIONS_FIELD = "EXPMEMA,EXPMA,MEMA,AMA,DMA,EMA,SMA,SUM,SAR,TMA,WMA,MA";

	private HashMap<String, CFunctionMe> m_functions = new HashMap();

	private HashMap<Integer, CFunctionMe> m_functionsMap = new HashMap();

	private ArrayList<CVariableMe> m_lines;

	private Random m_random = new Random();

	private double m_result;

	private CVarMe m_resultVar;

	private HashMap<String, CVariableMe> m_tempFunctions = new HashMap();

	private HashMap<String, CVariableMe> m_tempVariables = new HashMap();

	private static String VARIABLE = "~";

	private static String VARIABLE2 = "@";

	private static String VARIABLE3 = "?";

	protected ArrayList<CVariableMe> m_variables;

	protected AttachVScale m_attachVScale = AttachVScale.Left;

	public AttachVScale GetAttachVScale() {
		return this.m_attachVScale;
	}

	public void SetAttachVScale(AttachVScale vscale) {
		this.m_attachVScale = vscale;
		for (CVariableMe var : this.m_variables) {
			if (var.m_polylineShape != null) {
				var.m_barShape.SetAttachVScale(vscale);
				var.m_candleShape.SetAttachVScale(vscale);
				var.m_polylineShape.SetAttachVScale(vscale);
				var.m_textShape.SetAttachVScale(vscale);
			}
		}
	}

	protected CTableMe m_dataSource = null;

	public CTableMe GetDataSource() {
		return this.m_dataSource;
	}

	public void SetDataSource(CTableMe value) {
		this.m_dataSource = value;
	}

	protected CDivMe m_div = null;

	public CDivMe GetDiv() {
		return this.m_div;
	}

	public void SetDiv(CDivMe value) {
		this.m_div = value;
		this.m_dataSource = this.m_div.GetChart().GetDataSource();
	}

	protected boolean m_isDisposed = false;

	public boolean IsDisposed() {
		return this.m_isDisposed;
	}

	protected int m_index = -1;
	protected HashMap<String, Integer> m_mainVariables;
	protected String m_name;

	public int GetIndex() {
		return this.m_index;
	}

	public HashMap<String, Integer> GetMainVariables() {
		return this.m_mainVariables;
	}

	public String GetName() {
		return this.m_name;
	}

	public void SetName(String value) {
		this.m_name = value;
	}

	public double GetResult() {
		return this.m_result;
	}

	public void SetScript(String value) {
		synchronized (this) {
			this.m_lines.clear();
			this.m_defineParams.clear();
			ArrayList<String> arrayList = new ArrayList();
			GetMiddleScript(value, arrayList);
			int size = arrayList.size();
			for (int i = 0; i < size; i++) {
				String str = (String) arrayList.get(i);
				if (str.startsWith("FUNCTION ")) {
					String subStr = str.substring(9, str.indexOf('(')).toUpperCase();
					AddFunction(new CFunctionMe(FUNCTIONID_FUNCTION, subStr));
				} else if (str.startsWith("CONST ")) {
					String[] consts = str.substring(6).split("[:]");
					this.m_defineParams.put(consts[0], Double.valueOf(CStrMe.ConvertStrToDouble(consts[1])));
					arrayList.remove(i);
					i--;
					size--;
				}
			}
			size = arrayList.size();
			for (int j = 0; j < size; j++) {
				AnalysisScriptLine((String) arrayList.get(j));
			}
			arrayList.clear();
		}
	}

	protected ArrayList<Long> m_systemColors = new ArrayList();

	public ArrayList<Long> GetSystemColors() {
		return this.m_systemColors;
	}

	public void SetSystemColors(ArrayList<Long> value) {
		this.m_systemColors = value;
	}

	protected Object m_tag = null;
	protected CVarFactoryMe m_varFactory;

	public Object GetTag() {
		return this.m_tag;
	}

	public void SetTag(Object value) {
		this.m_tag = value;
	}

	public CVarFactoryMe GetVarFactory() {
		return this.m_varFactory;
	}

	public void SetVarFactory(CVarFactoryMe value) {
		this.m_varFactory = value;
	}

	public void AddFunction(CFunctionMe function) {
		this.m_functions.put(function.m_name, function);
		this.m_functionsMap.put(Integer.valueOf(function.m_ID), function);
	}

	public double CallFunction(String funcName) {
		double res = 0.0D;
		synchronized (this) {
			ArrayList<String> arrayList = new ArrayList();
			GetMiddleScript(funcName, arrayList);
			int size = arrayList.size();
			this.m_result = 0.0D;
			for (int i = 0; i < size; i++) {
				String sScript = (String) arrayList.get(i);
				int index = sScript.indexOf('(');
				String sUpper = sScript.substring(0, index).toUpperCase();
				if (this.m_tempFunctions.containsKey(sUpper)) {
					CVariableMe variable = (CVariableMe) this.m_tempFunctions.get(sUpper);
					int rindex = sScript.lastIndexOf(')');
					CVariableMe newVariable = new CVariableMe(this);
					if (rindex - index > 1) {
						String subStr = sScript.substring(index + 1, rindex);
						String[] sss = subStr.split("[" + VARIABLE2 + "]");
						String[] sss2 = variable.m_fieldText.split("[" + VARIABLE2 + "]");
						int sssLen = sss.length;
						if ((sssLen != 1) || (sss[0].length() != 0)) {
							newVariable.m_parameters = new CVariableMe[sssLen * 2];
							for (int j = 0; j < sssLen; j++) {
								String str2 = sss2[j];
								String str = sss[j];
								CVariableMe tempVar = null;
								if (this.m_tempVariables.containsKey(str2)) {
									tempVar = (CVariableMe) this.m_tempVariables.get(str2);
								}
								CVariableMe var = new CVariableMe(this);
								var.m_expression = str;
								if (str.startsWith("'")) {
									var.m_type = 1;
								} else {
									var.m_type = 3;
									var.m_value = CStrMe.ConvertStrToDouble(str);
								}
								newVariable.m_parameters[(j * 2)] = tempVar;
								newVariable.m_parameters[(j * 2 + 1)] = var;
							}
							FUNCVAR(newVariable);
						}
					}
					GetValue((CVariableMe) this.m_tempFunctions.get(sUpper));
					if (newVariable.m_parameters != null) {
						int len = newVariable.m_parameters.length;
						for (int j = 0; j < len; j++) {
							if (j % 2 == 0) {
								int id = newVariable.m_parameters[j].m_field;
								if (this.m_tempVars.containsKey(Integer.valueOf(id))) {
									CVarMe cvar = (CVarMe) this.m_tempVars.get(Integer.valueOf(id));
									if (cvar.m_parent != null) {
										this.m_tempVars.put(Integer.valueOf(id), cvar.m_parent);
									} else {
										this.m_tempVars.remove(Integer.valueOf(id));
									}
									cvar.Dispose();
								}
							}
						}
					}
				}
			}
			arrayList.clear();
			res = this.m_result;
			this.m_result = 0.0D;
			this.m_break = 0;
		}
		return res;
	}

	private void AnalysisVariables(RefObject<String> sentence, int line, String funcName, String fieldText,
			boolean isFunction) {
		ArrayList<String> arrayList = new ArrayList();
		String[] ssSplit = SplitExpression2((String) sentence.argvalue);
		int len = ssSplit.length;
		for (int s = 0; s < len; s++) {
			String stringS = ssSplit[s];
			String[] keys = stringS.split(VARIABLE2 + "|:");
			int keysLen = keys.length;
			for (int u = 0; u < keysLen; u++) {
				if (this.m_functions.containsKey(keys[u])) {
					arrayList.add(keys[u]);
				}
			}
		}
		int arraySize = arrayList.size();
		for (int i = 0; i < arraySize; i++) {
			String strI = (String) arrayList.get(i);
			CFunctionMe funcI = (CFunctionMe) this.m_functions.get(strI);
			String funcIName = funcI.m_name;
			int funcID = funcI.m_ID;
			int funcType = funcI.m_type;
			String tempStr = funcIName + "(";
			int findIndex = ((String) sentence.argvalue).indexOf(tempStr);
			while (findIndex != -1) {
				int t1 = 0;
				int t2 = 0;
				int t3 = 0;
				char[] chars = ((String) sentence.argvalue).toCharArray();
				for (char c : chars) {
					if (t2 >= findIndex) {
						if (c == '(') {
							t3++;
						} else if (c == ')') {
							t3--;
							if (t3 == 0) {
								t1 = t2;
								break;
							}
						}
					}
					t2++;
				}
				if (t1 == 0) {
					break;
				}

				String sSentence = ((String) sentence.argvalue).substring(findIndex, t1 + 1);
				CVariableMe newVar = new CVariableMe(this);
				newVar.m_name = String.format("%s%d",
						new Object[] { VARIABLE, Integer.valueOf(this.m_variables.size()) });
				newVar.m_expression = sSentence.substring(0, sSentence.indexOf('('));
				newVar.m_type = 0;
				newVar.m_functionID = funcID;
				newVar.m_fieldText = sSentence;
				if (funcType == 1) {
					int ii = CTableMe.GetAutoField();
					newVar.m_field = ii;
					this.m_dataSource.AddColumn(ii);
				}
				this.m_variables.add(newVar);
				if (findIndex == 0) {
					if (isFunction) {
						newVar.m_funcName = funcName;
						newVar.m_line = line;
						newVar.m_fieldText = fieldText;
						this.m_lines.add(newVar);
						this.m_tempFunctions.put(funcName, newVar);
						isFunction = false;
					}
				}
				newVar.m_splitExpression = SplitExpression(newVar.m_expression);
				int ii = findIndex + tempStr.length();
				String subStr = ((String) sentence.argvalue).substring(ii, t1);
				if (funcID == FUNCTIONID_FUNCTION) {
					if (this.m_tempFunctions.containsKey(funcIName)) {
						if (((CVariableMe) this.m_tempFunctions.get(funcIName)).m_fieldText != null) {
							String[] sSplit = ((CVariableMe) this.m_tempFunctions.get(funcIName)).m_fieldText
									.split("[" + VARIABLE2 + "]");
							String[] sSplit2 = subStr.split("[" + VARIABLE2 + "]");
							subStr = "";
							int len2 = sSplit2.length;
							for (int ii2 = 0; ii2 < len2; ii2++) {
								if (ii2 == 0) {
									subStr = "FUNCVAR(";
								}
								subStr = subStr + sSplit[ii2] + VARIABLE2 + sSplit2[ii2];
								if (ii2 != len - 1) {
									subStr = subStr + VARIABLE2;
								} else {
									subStr = subStr + ")";
								}
							}
						}
					}
				}
				RefObject<String> refSubStr = new RefObject(subStr);
				AnalysisVariables(refSubStr, 0, "", "", false);
				subStr = (String) refSubStr.argvalue;
				String[] splitSub = subStr.split("[" + VARIABLE2 + "]");
				if ((splitSub != null) && (splitSub.length > 0) && (splitSub[0].length() > 0)) {
					newVar.m_parameters = new CVariableMe[splitSub.length];
					for (int j = 0; j < splitSub.length; j++) {
						String strJ = splitSub[j];
						strJ = Replace(strJ);
						CVariableMe newVarJ = new CVariableMe(this);
						newVarJ.m_expression = strJ;
						newVarJ.m_name = String.format("%s%d",
								new Object[] { VARIABLE, Integer.valueOf(this.m_variables.size()) });
						newVarJ.m_type = 1;
						newVar.m_parameters[j] = newVarJ;
						for (CVariableMe v : this.m_variables) {
							if ((v.m_type == 2) && (v.m_expression.equals(splitSub[j]))
									&& (v.m_field != CTableMe.NULLFIELD)) {
								newVarJ.m_type = 2;
								newVarJ.m_field = v.m_field;
								newVarJ.m_fieldText = splitSub[j];
								break;
							}
						}
						if (newVarJ.m_type == 1) {
							String s = strJ;
							if (s.indexOf("[REF]") == 0) {
								s = s.substring(5);
							}
							if (this.m_tempVariables.containsKey(s)) {
								newVarJ.m_field = ((CVariableMe) this.m_tempVariables.get(s)).m_field;
							} else {
								newVarJ.m_field = (-this.m_variables.size());
								this.m_tempVariables.put(s, newVarJ);
							}
						}
						this.m_variables.add(newVarJ);
						newVarJ.m_splitExpression = SplitExpression(strJ);
						if ((newVarJ.m_splitExpression != null) && (newVarJ.m_splitExpression.length == 2)) {
							if (newVarJ.m_splitExpression[0].m_var == newVarJ) {
								newVarJ.m_splitExpression = null;
							}
						}
					}
				}
				sentence.argvalue = (((String) sentence.argvalue).substring(0, findIndex) + newVar.m_name
						+ ((String) sentence.argvalue).substring(t1 + 1));
				findIndex = ((String) sentence.argvalue).indexOf(tempStr,
						((String) sentence.argvalue).indexOf(newVar.m_name));
			}
		}
		arrayList.clear();
	}

	private void AnalysisScriptLine(String line) {
		CVariableMe var = new CVariableMe(this);
		boolean boo = false;
		String sline = line;
		String str1 = null;
		String str2 = null;
		if (line.startsWith("FUNCTION ")) {
			int index = sline.indexOf('(');
			str1 = sline.substring(9, index);
			int index2 = sline.indexOf(')');
			if (index2 - index > 1) {
				str2 = sline.substring(index + 1, index2);
				String[] splitStr2 = str2.split("[" + VARIABLE2 + "]");
				int len2 = splitStr2.length;
				for (int i = 0; i < len2; i++) {
					String str = splitStr2[i];
					if (str.indexOf("[REF]") != -1) {
						str = str.substring(5);
					}
					String temp = "VAR(" + str + VARIABLE2 + "0)";
					RefObject<String> refT = new RefObject(temp);
					AnalysisVariables(refT, 0, "", "", false);
				}
			}
			sline = sline.substring(index2 + 1);
			sline = "CHUNK" + sline.substring(0, sline.length() - 1) + ")";
			boo = true;
		}
		RefObject<String> refLine = new RefObject(sline);
		AnalysisVariables(refLine, this.m_lines.size(), str1, str2, boo);
		sline = (String) refLine.argvalue;
		var.m_line = this.m_lines.size();
		if (boo) {
			return;
		}
		var.m_name = "";
		String strG = null;
		String strL = null;
		String strX = "";
		String strD = "";
		char[] chars = sline.toCharArray();
		for (char c : chars) {
			if ((c != ':') && (c != '=')) {
				if (strD.length() > 0) {
					break;
				}

			} else {
				strD = strD + new Character(c).toString();
			}
		}
		if (strD.equals(":=")) {
			strG = sline.substring(0, sline.indexOf(":="));
			strL = sline.substring(sline.indexOf(":=") + 2);
		} else if (strD.equals(":")) {
			strX = "COLORAUTO";
			strG = sline.substring(0, sline.indexOf(":"));
			strL = sline.substring(sline.indexOf(":") + 1);
			if (strL.indexOf(VARIABLE2) != -1) {
				strX = strL.substring(strL.indexOf(VARIABLE2) + 1);
				strL = strL.substring(0, strL.indexOf(VARIABLE2));
			}
		} else {
			strL = sline;
			String[] sss = strL.split("[" + VARIABLE2 + "]");
			if ((sss != null) && (sss.length > 1)) {
				String sssStr = sss[0];
				strL = sssStr;
				int iValue = CStrMe.ConvertStrToInt(sssStr.substring(1));
				if (iValue < this.m_variables.size()) {
					CVariableMe varTC = (CVariableMe) this.m_variables.get(iValue);
					int ii = 0;
					if (varTC.m_parameters == null) {
						varTC.m_parameters = new CVariableMe[sss.length - 1];
						ii = 0;
					} else {
						CVariableMe[] vv = new CVariableMe[varTC.m_parameters.length + sss.length - 1];
						for (int j = 0; j < varTC.m_parameters.length; j++) {
							vv[j] = varTC.m_parameters[j];
						}
						ii = varTC.m_parameters.length;
						varTC.m_parameters = vv;
					}
					for (int j = 1; j < sss.length; j++) {
						CVariableMe cv = new CVariableMe(this);
						cv.m_type = 1;
						cv.m_expression = sss[j];
						varTC.m_parameters[(ii + j - 1)] = cv;
					}
				}
			}
		}
		var.m_expression = Replace(strL);
		this.m_variables.add(var);
		this.m_lines.add(var);
		if (strG != null) {
			var.m_type = 1;
			CVariableMe varHF = new CVariableMe(this);
			varHF.m_type = 2;
			varHF.m_name = String.format("%s%d", new Object[] { VARIABLE, Integer.valueOf(this.m_variables.size()) });
			int iXIONG = CTableMe.NULLFIELD;
			if (strL.startsWith(VARIABLE)) {
				boolean bb = IsNumeric(strL.replace(VARIABLE, ""));
				if (bb) {
					for (CVariableMe varTC : this.m_variables) {
						if ((varTC.m_name.equals(strL)) && (varTC.m_field != CTableMe.NULLFIELD)) {
							iXIONG = varTC.m_field;
							break;
						}
					}
				}
			}
			if (iXIONG == CTableMe.NULLFIELD) {
				iXIONG = CTableMe.GetAutoField();
				this.m_dataSource.AddColumn(iXIONG);
			} else {
				var.m_type = 0;
			}
			varHF.m_field = iXIONG;
			varHF.m_expression = strG;
			varHF.m_splitExpression = SplitExpression(strG);
			this.m_variables.add(varHF);
			this.m_mainVariables.put(strG, Integer.valueOf(iXIONG));
			var.m_field = iXIONG;
		}
		if ((strX != null) && (strX.length() > 0)) {
			String sYW = null;
			if (strX.indexOf("COLORSTICK") != -1) {
				sYW = "STICKLINE(1" + VARIABLE2 + strG + VARIABLE2 + "0" + VARIABLE2 + "1" + VARIABLE2 + "2" + VARIABLE2
						+ "DRAWTITLE)";
			} else if (strX.indexOf("CIRCLEDOT") != -1) {
				sYW = "DRAWICON(1" + VARIABLE2 + strG + VARIABLE2 + "CIRCLEDOT" + VARIABLE2 + "DRAWTITLE)";
			} else if (strX.indexOf("POINTDOT") != -1) {
				sYW = "DRAWICON(1" + VARIABLE2 + strG + VARIABLE2 + "POINTDOT" + VARIABLE2 + "DRAWTITLE)";
			} else {
				sYW = "POLYLINE(1" + VARIABLE2 + strG + VARIABLE2 + strX + VARIABLE2 + "DRAWTITLE)";
			}
			AnalysisScriptLine(sYW);
		}
		var.m_splitExpression = SplitExpression(var.m_expression);
	}

	private double Calculate(CMathElementMe[] expr) {
		CMathElementMe[] mathElem = new CMathElementMe[expr.length];
		int iNM = 1;
		CMathElementMe mathItem = new CMathElementMe();
		mathItem.m_type = 3;
		mathElem[0] = mathItem;
		CMathElementMe[] mathElemArray = new CMathElementMe[expr.length];
		int iSDL = 0;
		int iQB = 0;
		CMathElementMe dxzz = null;
		while ((iQB < expr.length) && ((expr[iQB].m_type != 3) || (mathElem[(iNM - 1)].m_type != 3))) {
			CMathElementMe tntn = expr[iQB];
			if ((tntn.m_type != 0) && (tntn.m_type != 3)) {
				mathElemArray[iSDL] = tntn;
				iSDL++;
				iQB++;
			} else {
				CMathElementMe wsp = mathElem[(iNM - 1)];
				int iHF = -1;
				if (tntn.m_type == 3) {
					if (wsp.m_type == 3) {
						iHF = 3;
					} else {
						iHF = 4;
					}
				} else {
					int iKS = (int) wsp.m_value;
					int iBKZ = (int) tntn.m_value;
					switch (iBKZ) {
					case 0:
					case 1:
					case 3:
					case 4:
					case 5:
					case 7:
					case 8:
					case 10:
					case 11:
					case 13:
					case 14:
						if ((wsp.m_type == 3) || ((wsp.m_type == 0) && (iKS == 6))) {
							iHF = 7;
						} else {
							iHF = 4;
						}
						break;
					case 2:
					case 9:
						if ((wsp.m_type == 0) && ((iKS == 9) || (iKS == 2) || (iKS == 12))) {
							iHF = 4;
						} else {
							iHF = 7;
						}
						break;
					case 6:
						iHF = 7;
						break;
					case 12:
						if ((wsp.m_type == 0) && (iKS == 6)) {
							iHF = 3;
						} else {
							iHF = 4;
						}
						break;
					}
				}
				switch (iHF) {
				case 7:
					mathElem[iNM] = tntn;
					iNM++;
					iQB++;
					break;
				case 3:
					iNM--;
					iQB++;
					break;
				case 4:
					if (iSDL == 0)
						return 0.0D;
					int iHZW = (int) wsp.m_value;
					iNM--;
					double dZQ = 0.0D;
					double dZR = 0.0D;
					CMathElementMe tempElem = mathElemArray[(iSDL - 1)];
					if (tempElem.m_type == 2) {
						dZR = GetValue(tempElem.m_var);
					} else {
						dZR = tempElem.m_value;
					}
					if (iSDL > 1) {
						dxzz = mathElemArray[(iSDL - 2)];
						if (dxzz.m_type == 2) {
							dZQ = GetValue(dxzz.m_var);
						} else {
							dZQ = dxzz.m_value;
						}
						iSDL -= 2;
					} else {
						iSDL--;
					}
					double dHJ = 0.0D;
					switch (iHZW) {
					case 0:
						dHJ = dZQ + dZR;
						break;
					case 13:
						dHJ = dZQ - dZR;
						break;
					case 9:
						dHJ = dZQ * dZR;
						break;

					case 2:
						if (dZR == 0.0D) {
							dHJ = 0.0D;
						} else {
							dHJ = dZQ / dZR;
						}
						break;

					case 14:
						if (dZR == 0.0D) {
							dHJ = 0.0D;
						} else {
							dHJ = dZQ % dZR;
						}
						break;
					case 5:
						dHJ = dZQ >= dZR ? 1 : 0;
						break;
					case 8:
						dHJ = dZQ <= dZR ? 1 : 0;
						break;

					case 10:
						if (((tempElem.m_var != null) && (tempElem.m_var.m_functionID == -2))
								|| ((dxzz != null) && (dxzz.m_var != null) && (dxzz.m_var.m_functionID == -2))) {
							if ((dxzz != null) && (tempElem.m_var != null) && (dxzz.m_var != null)) {
								if (!GetText(tempElem.m_var).equals(GetText(dxzz.m_var))) {
									dHJ = 1.0D;
								}

							}
						} else {
							dHJ = dZQ != dZR ? 1 : 0;
						}
						break;

					case 3:
						if (((tempElem.m_var != null) && (tempElem.m_var.m_functionID == -2))
								|| ((dxzz != null) && (dxzz.m_var != null) && (dxzz.m_var.m_functionID == -2))) {
							if ((dxzz != null) && (tempElem.m_var != null) && (dxzz.m_var != null)) {
								if (GetText(tempElem.m_var).equals(GetText(dxzz.m_var))) {
									dHJ = 1.0D;
								}

							}
						} else {
							dHJ = dZQ == dZR ? 1 : 0;
						}
						break;
					case 4:
						dHJ = dZQ > dZR ? 1 : 0;
						break;
					case 7:
						dHJ = dZQ < dZR ? 1 : 0;
						break;
					case 1:
						if ((dZQ == 1.0D) && (dZR == 1.0D))
							dHJ = 1.0D;
						else
							dHJ = 0.0D;
						break;
					case 11:
						if ((dZQ == 1.0D) || (dZR == 1.0D))
							dHJ = 1.0D;
						else
							dHJ = 0.0D;
						break;
					case 6:
					case 12:
					default:
						dHJ = 0.0D;
					}
					if (this.m_break > 0) {
						return dHJ;
					}

					CMathElementMe newElem = new CMathElementMe();
					newElem.m_type = 1;
					newElem.m_value = dHJ;
					mathElemArray[iSDL] = newElem;
					iSDL++;
				}

			}
		}

		if (iSDL > 0) {
			CMathElementMe elem = mathElemArray[(iSDL - 1)];
			if (elem.m_type == 2) {
				return GetValue(elem.m_var);
			}

			return elem.m_value;
		}

		return 0.0D;
	}

	private double CallFunction(CVariableMe var) {
		switch (var.m_functionID) {
		case 0:
			return CURRBARSCOUNT(var);
		case 1:
			return BARSCOUNT(var);
		case 2:
			return DRAWKLINE(var);
		case 3:
			return STICKLINE(var);
		case 4:
			return VALUEWHEN(var);
		case 5:
			return BARSLAST(var);
		case 6:
			return DOWNNDAY(var);
		case 7:
			return DRAWICON(var);
		case 8:
			return DRAWNULL(var);
		case 9:
			return FUNCTION(var);
		case 10:
			return FUNCVAR(var);
		case 11:
			return DRAWTEXT(var);
		case 12:
			return POLYLINE(var);
		case 13:
			return BETWEEN(var);
		case 14:
			return CEILING(var);
		case 15:
			return EXPMEMA(var);
		case 16:
			return HHVBARS(var);
		case 17:
			return INTPART(var);
		case 18:
			return LLVBARS(var);
		case 19:
			return DOTIMES(var);
		case 20:
			return DOWHILE(var);
		case 21:
			return CONTINUE(var);
		case 22:
			return RETURN(var);
		case 23:
			return REVERSE(var);
		case 24:
			return AVEDEV(var);
		case 25:
			return MINUTE(var);
		case 26:
			return SQUARE(var);
		case 27:
			return UPNDAY(var);
		case 28:
			return DELETE(var);
		case 29:
			return COUNT(var);
		case 30:
			return CROSS(var);
		case 31:
			return EVERY(var);
		case 32:
			return EXIST(var);
		case 33:
			return EMA(var);
		case 34:
			return FLOOR(var);
		case 35:
			return MONTH(var);
		case 36:
			return ROUND(var);
		case 37:
			return TIME2(var);
		case 38:
			return WHILE(var);
		case 39:
			return BREAK(var);
		case 40:
			return CHUNK(var);
		case 41:
			return ACOS(var);
		case 42:
			return ASIN(var);
		case 43:
			return ATAN(var);
		case 44:
			return DATE(var);
		case 45:
			return HOUR(var);
		case 46:
			return LAST(var);
		case 47:
			return MEMA(var);
		case 48:
			return NDAY(var);
		case 49:
			return RAND(var);
		case 50:
			return SIGN(var);
		case 51:
			return SQRT(var);
		case 52:
			return TIME(var);
		case 53:
			return YEAR(var);
		case 54:
			return ABS(var);
		case 55:
			return AMA(var);
		case 56:
			return COS(var);
		case 57:
			return DAY(var);
		case 58:
			return DMA(var);
		case 59:
			return EMA(var);
		case 60:
			return EXP(var);
		case 61:
			return HHV(var);
		case 62:
			return IF(var);
		case 63:
			return IFN(var);
		case 64:
			return LLV(var);
		case 65:
			return LOG(var);
		case 66:
			return MAX(var);
		case 67:
			return MIN(var);
		case 68:
			return MOD(var);
		case 69:
			return NOT(var);
		case 70:
			return POW(var);
		case 71:
			return SIN(var);
		case 72:
			return SMA(var);
		case 73:
			return STD(var);
		case 74:
			return SUM(var);
		case 75:
			return TAN(var);
		case 76:
			return REF(var);
		case 77:
			return SAR(var);
		case 78:
			return FOR(var);
		case 79:
			return GET(var);
		case 80:
			return SET(var);
		case 81:
			return TMA(var);
		case 82:
			return VAR(var);
		case 83:
			return WMA(var);
		case 84:
			return ZIG(var);
		case 85:
			return IF(var);
		case 86:
			return MA(var);
		case 87:
			return STR_CONTACT(var);
		case 88:
			return STR_EQUALS(var);
		case 89:
			return STR_FIND(var);
		case 90:
			return STR_FINDLAST(var);
		case 91:
			return STR_LENGTH(var);
		case 92:
			return STR_SUBSTR(var);
		case 93:
			return STR_REPLACE(var);
		case 94:
			return STR_SPLIT(var);
		case 95:
			return STR_TOLOWER(var);
		case 96:
			return STR_TOUPPER(var);
		case 97:
			return LIST_ADD(var);
		case 98:
			return LIST_CLEAR(var);
		case 99:
			return LIST_GET(var);
		case 100:
			return LIST_INSERT(var);
		case 101:
			return LIST_REMOVE(var);
		case 102:
			return LIST_SIZE(var);
		case 103:
			return MAP_CLEAR(var);
		case 104:
			return MAP_CONTAINSKEY(var);
		case 105:
			return MAP_GET(var);
		case 106:
			return MAP_GETKEYS(var);
		case 107:
			return MAP_REMOVE(var);
		case 108:
			return MAP_SET(var);
		case 109:
			return MAP_SIZE(var);
		}
		if (this.m_functionsMap.containsKey(Integer.valueOf(var.m_functionID))) {
			return ((CFunctionMe) this.m_functionsMap.get(Integer.valueOf(var.m_functionID))).OnCalculate(var);
		}
		return 0.0D;
	}

	public void Clear() {
		synchronized (this) {
			if (this.m_div != null) {
				ArrayList<CBaseShapeMe> shapes = GetShapes();
				for (CBaseShapeMe bs : shapes) {
					this.m_div.RemoveShape(bs);
					this.m_div.GetTitleBar().GetTitles().clear();
					bs.Dispose();
				}
				if (shapes != null) {
					shapes.clear();
				}
			}
			for (CVariableMe cv : this.m_variables) {
				if (cv.m_field >= 10000) {
					this.m_dataSource.RemoveColumn(cv.m_field);
					if (cv.m_tempFields != null) {
						for (int i = 0; i < cv.m_tempFields.length; i++) {
							if (cv.m_tempFields[i] >= 10000) {
								this.m_dataSource.RemoveColumn(cv.m_tempFields[i]);
							}
						}
					}
				}
			}
			this.m_lines.clear();
			this.m_variables.clear();
			this.m_mainVariables.clear();
			this.m_defineParams.clear();
			this.m_tempFunctions.clear();
			DeleteTempVars();
			this.m_tempVariables.clear();
		}
	}

	public CVarMe CopyTempVar(CVarMe var) {
		CVarMe newVar = new CVarMe();
		newVar.m_type = var.m_type;
		newVar.m_str = var.m_str;
		newVar.m_num = var.m_num;
		return newVar;
	}

	private void DeleteTempVars() {
		while (this.m_tempVars.size() > 0) {
			ArrayList<Integer> arrayList = new ArrayList();
			for (Entry<Integer, CVarMe> par : this.m_tempVars.entrySet()) {
				arrayList.add(par.getKey());
			}
			int size = arrayList.size();
			for (int i = 0; i < size; i++) {
				int iValue = ((Integer) arrayList.get(i)).intValue();
				if (this.m_tempVars.containsKey(Integer.valueOf(iValue))) {
					CVarMe v = (CVarMe) this.m_tempVars.get(Integer.valueOf(iValue));
					if (v.m_parent != null) {
						this.m_tempVars.put(Integer.valueOf(iValue), v.m_parent);
					} else {
						this.m_tempVars.remove(Integer.valueOf(iValue));
					}
					v.Dispose();
				}
			}
			arrayList.clear();
		}
	}

	private void DeleteTempVars(CVariableMe var) {
		if (var.m_parameters != null) {
			int len = var.m_parameters.length;
			if (len > 0) {
				for (int i = 0; i < len; i++) {
					CVariableMe varI = var.m_parameters[i];
					if ((varI.m_splitExpression != null) && (varI.m_splitExpression.length > 0)) {
						CVariableMe vLL = varI.m_splitExpression[0].m_var;
						if ((vLL != null)
								&& ((vLL.m_functionID == FUNCTIONID_FUNCVAR) || (vLL.m_functionID == FUNCTIONID_VAR))) {
							int lenLL = vLL.m_parameters.length;
							for (int j = 0; j < lenLL; j++) {
								if (j % 2 == 0) {
									CVariableMe vFH = vLL.m_parameters[j];
									int id = vFH.m_field;
									if (vFH.m_expression.indexOf("[REF]") == 0) {
										int size = this.m_variables.size();
										for (int jH = 0; jH < size; jH++) {
											CVariableMe vvvv = (CVariableMe) this.m_variables.get(jH);
											if (vvvv.m_expression == vFH.m_expression) {
												vvvv.m_field = id;
											}

										}

									} else if (this.m_tempVars.containsKey(Integer.valueOf(id))) {
										CVarMe vvvv = (CVarMe) this.m_tempVars.get(Integer.valueOf(id));
										if (vvvv.m_parent != null) {
											this.m_tempVars.put(Integer.valueOf(id), vvvv.m_parent);
										} else {
											this.m_tempVars.remove(Integer.valueOf(id));
										}
										vvvv.Dispose();
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public static CIndicatorMe CreateIndicator() {
		CIndicatorMe res = new CIndicatorMeEx();
		return res;
	}

	public void Dispose() {
		if (!this.m_isDisposed) {
			Clear();
			this.m_functionsMap.clear();
			this.m_functions.clear();
			this.m_isDisposed = true;
		}
	}

	private long GetColor(String strColor) {
		if (strColor.equals("COLORRED")) {
			return COLOR.ARGB(255, 0, 0);
		}
		if (strColor.equals("COLORGREEN")) {
			return COLOR.ARGB(0, 255, 0);
		}
		if (strColor.equals("COLORBLUE")) {
			return COLOR.ARGB(0, 0, 255);
		}
		if (strColor.equals("COLORMAGENTA")) {
			return COLOR.ARGB(255, 0, 255);
		}
		if (strColor.equals("COLORYELLOW")) {
			return COLOR.ARGB(255, 255, 0);
		}
		if (strColor.equals("COLORLIGHTGREY")) {
			return COLOR.ARGB(211, 211, 211);
		}
		if (strColor.equals("COLORLIGHTRED")) {
			return COLOR.ARGB(255, 82, 82);
		}
		if (strColor.equals("COLORLIGHTGREEN")) {
			return COLOR.ARGB(144, 238, 144);
		}
		if (strColor.equals("COLORLIGHTBLUE")) {
			return COLOR.ARGB(173, 216, 230);
		}
		if (strColor.equals("COLORBLACK")) {
			return COLOR.ARGB(0, 0, 0);
		}
		if (strColor.equals("COLORWHITE")) {
			return COLOR.ARGB(255, 255, 255);
		}
		if (strColor.equals("COLORCYAN")) {
			return COLOR.ARGB(0, 255, 255);
		}
		if (strColor.equals("COLORAUTO")) {
			int idx = 0;
			long res = COLOR.EMPTY;
			for (CBaseShapeMe shape : GetShapes()) {
				if ((shape instanceof CPolylineShapeMe)) {
					idx++;
				}
			}
			int size = this.m_systemColors.size();
			if (size > 0) {
				res = ((Long) this.m_systemColors.get(idx % size)).longValue();
			}
			return res;
		}

		return 0L;
	}

	private LPDATA GetDatas(int fieldIndex, int mafieldIndex, int index, int n) {
		LPDATA lpData = new LPDATA();
		lpData.mode = 1;
		if (index >= 0) {
			double dZ = this.m_dataSource.Get3(index, mafieldIndex);
			if (!Double.isNaN(dZ)) {
				lpData.lastvalue = dZ;
				if (index >= n - 1) {
					double nValue = this.m_dataSource.Get3(index + 1 - n, fieldIndex);
					if (!Double.isNaN(nValue)) {
						lpData.first_value = nValue;
					}
				}
			} else {
				lpData.mode = 0;
				int temp = index - n + 2;
				if (temp < 0) {
					temp = 0;
				}
				for (int i = temp; i <= index; i++) {
					double dValue = this.m_dataSource.Get3(i, fieldIndex);
					if (!Double.isNaN(dValue)) {
						lpData.sum += dValue;
					}
				}
			}
		}
		return lpData;
	}

	public ArrayList<CFunctionMe> GetFunctions() {
		ArrayList<CFunctionMe> arrayList = new ArrayList();
		for (Entry<String, CFunctionMe> par : this.m_functions.entrySet()) {
			arrayList.add(par.getValue());
		}
		return arrayList;
	}

	private float GetLineWidth(String strLine) {
		float res = 1.0F;
		if (strLine.length() > 9) {
			res = CStrMe.ConvertStrToFloat(strLine.substring(9));
		}
		return res;
	}

	private int GetOperator(String op) {
		if (op.equals(">=")) {
			return 5;
		}
		if (op.equals("<=")) {
			return 8;
		}
		if ((op.equals("<>")) || (op.equals("!"))) {
			return 10;
		}
		if (op.equals("+")) {
			return 0;
		}
		if (op.equals(VARIABLE3)) {
			return 13;
		}
		if (op.equals("*")) {
			return 9;
		}
		if (op.equals("/")) {
			return 2;
		}
		if (op.equals("(")) {
			return 6;
		}
		if (op.equals(")")) {
			return 12;
		}
		if (op.equals("=")) {
			return 3;
		}
		if (op.equals(">")) {
			return 4;
		}
		if (op.equals("<")) {
			return 7;
		}
		if (op.equals("&")) {
			return 1;
		}
		if (op.equals("|")) {
			return 11;
		}
		if (op.equals("%")) {
			return 14;
		}
		return -1;
	}

	private int GetMiddleScript(String script, ArrayList<String> lines) {
		script = script.replace(" AND ", "&").replace(" OR ", "|");
		String sHX = "";
		boolean bS = false;
		char c = '0';
		boolean bCM = false;
		boolean bBG = false;
		int iKH = 0;
		boolean isReturn = false;
		boolean isVar = false;
		boolean isNewParam = false;
		boolean isSet = false;
		char[] chars = script.toCharArray();
		for (char vc : chars) {
			if (vc != 65279) {

				if (vc == '\'') {
					bS = !bS;
				}
				if (!bS) {
					if (vc == '{') {
						int len = sHX.length();
						if (len == 0) {
							bCM = true;

						} else if (!bCM) {
							iKH++;
							if ((bBG) && (iKH == 1)) {
								sHX = sHX + "(";

							} else if (sHX.lastIndexOf(")") == len - 1) {
								sHX = sHX.substring(0, len - 1) + VARIABLE2 + "CHUNK(";
							} else if (sHX.lastIndexOf("))" + VARIABLE2 + "ELSE") == len - 7) {
								sHX = sHX.substring(0, len - 6) + VARIABLE2 + "CHUNK(";
							}

						}

					} else if (vc == '}') {
						if (bCM) {
							bCM = false;
						} else {
							iKH--;
							if ((bBG) && (iKH == 0)) {
								int len = sHX.length();
								if (len > 0) {
									if (sHX.substring(len - 1).equals(VARIABLE2)) {
										sHX = sHX.substring(0, len - 1);
									}
								}
								sHX = sHX + ")";
								lines.add(sHX);
								bBG = false;
								sHX = "";

							} else if (iKH == 0) {
								sHX = sHX + "))";
								lines.add(sHX);
								sHX = "";
							} else {
								sHX = sHX + "))" + VARIABLE2;
							}

						}
					} else if (vc == ' ') {
						int lineLength = sHX.length();
						if (sHX.equals("CONST")) {
							sHX = "CONST ";
						} else if (sHX.equals("FUNCTION")) {
							sHX = "FUNCTION ";
							bBG = true;
						} else if ((!isReturn) && (sHX.lastIndexOf("RETURN") == lineLength - 6)) {
							if ((lineLength == 6) || (sHX.lastIndexOf(")RETURN") == lineLength - 7)
									|| (sHX.lastIndexOf("(RETURN") == lineLength - 7)
									|| (sHX.lastIndexOf(VARIABLE2 + "RETURN") == lineLength - 7)) {

								sHX = sHX + "(";
								isReturn = true;
							}
						} else if ((!isVar) && (sHX.lastIndexOf("VAR") == lineLength - 3)) {
							if ((lineLength == 3) || (sHX.lastIndexOf(")VAR") == lineLength - 4)
									|| (sHX.lastIndexOf("(VAR") == lineLength - 4)
									|| (sHX.lastIndexOf(VARIABLE2 + "VAR") == lineLength - 4)) {

								sHX = sHX + "(";
								isVar = true;
								isNewParam = true;
							}
						} else {
							if ((isSet) || (sHX.lastIndexOf("SET") != lineLength - 3))
								continue;
							if ((lineLength == 3) || (sHX.lastIndexOf(")SET") == lineLength - 4)
									|| (sHX.lastIndexOf("(SET") == lineLength - 4)
									|| (sHX.lastIndexOf(VARIABLE2 + "SET") == lineLength - 4)) {

								sHX = sHX.substring(0, lineLength - 3) + "SET(";
								isSet = true;
								isNewParam = true;

							}

						}

					} else if ((vc != '\t') && (vc != '\r') && (vc != '\n')) {
						if (!bCM) {
							if (vc == '&') {
								if (c != '&') {
									sHX = sHX + vc;
								}
							} else if (vc == '|') {
								if (c != '|') {
									sHX = sHX + vc;
								}
							} else if (vc == '=') {
								if ((isVar) && (isNewParam)) {
									isNewParam = false;
									sHX = sHX + VARIABLE2;
								} else if ((isSet) && (isNewParam)) {
									isNewParam = false;
									sHX = sHX + VARIABLE2;
								} else if ((c != '=') && (c != '!')) {
									sHX = sHX + vc;
								}
							} else if (vc == '-') {
								String strLh = String.valueOf(c);
								if ((!strLh.equals(VARIABLE2)) && (GetOperator(strLh) != -1) && (!strLh.equals(")"))) {

									sHX = sHX + vc;
								} else {
									sHX = sHX + VARIABLE3;
									c = VARIABLE3.charAt(0);
									continue;
								}
							} else if (vc == ',') {
								isNewParam = true;
								sHX = sHX + VARIABLE2;
							} else if (vc == ';') {
								if (isReturn) {
									sHX = sHX + ")";
									isReturn = false;
								} else if (isVar) {
									sHX = sHX + ")";
									isVar = false;
								} else if (isSet) {
									sHX = sHX + ")";
									isSet = false;
								} else {
									int lineLength = sHX.length();
									if (sHX.lastIndexOf("BREAK") == lineLength - 5) {
										if ((sHX.lastIndexOf(")BREAK") == lineLength - 6)
												|| (sHX.lastIndexOf("(BREAK") == lineLength - 6)
												|| (sHX.lastIndexOf(VARIABLE2 + "BREAK") == lineLength - 6)) {

											sHX = sHX + "()";
										}
									} else if (sHX.lastIndexOf("CONTINUE") == lineLength - 8) {
										if ((sHX.lastIndexOf(")CONTINUE") == lineLength - 9)
												|| (sHX.lastIndexOf("(CONTINUE") == lineLength - 9)
												|| (sHX.lastIndexOf(VARIABLE2 + "CONTINUE") == lineLength - 9)) {

											sHX = sHX + "()";
										}
									}
								}
								if (iKH > 0) {
									sHX = sHX + VARIABLE2;
								} else {
									lines.add(sHX);
									sHX = "";
								}
							} else if (vc == '(') {
								int len = sHX.length();
								if ((iKH > 0) && (sHX.lastIndexOf("))" + VARIABLE2 + "ELSEIF") == len - 9)) {
									sHX = sHX.substring(0, len - 9) + ")" + VARIABLE2;
								} else {
									sHX = sHX + "(";
								}
							} else {
								String newStr = String.valueOf(vc).toUpperCase();
								sHX = sHX + newStr;
							}

						}
					}
				} else {
					sHX = sHX + vc;
				}
				c = vc;
			}
		}
		return 0;
	}

	public ArrayList<CBaseShapeMe> GetShapes() {
		ArrayList<CBaseShapeMe> arrayList = new ArrayList();
		for (CVariableMe var : this.m_variables) {
			if (var.m_barShape != null) {
				arrayList.add(var.m_barShape);
			}
			if (var.m_candleShape != null) {
				arrayList.add(var.m_candleShape);
			}
			if (var.m_polylineShape != null) {
				arrayList.add(var.m_polylineShape);
			}
			if (var.m_textShape != null) {
				arrayList.add(var.m_textShape);
			}
		}
		return arrayList;
	}

	public String GetText(CVariableMe var) {
		if ((var.m_expression.length() > 0) && (var.m_expression.startsWith("'"))) {
			return var.m_expression.substring(1, var.m_expression.length() - 1);
		}

		if (this.m_tempVars.containsKey(Integer.valueOf(var.m_field))) {
			CVarMe cv = (CVarMe) this.m_tempVars.get(Integer.valueOf(var.m_field));
			return cv.GetText(this, var);
		}

		return CStrMe.ConvertDoubleToStr(GetValue(var));
	}

	public double GetValue(CVariableMe var) {
		switch (var.m_type) {
		case 0:
			return CallFunction(var);
		case 1:
			if (this.m_tempVars.containsKey(Integer.valueOf(var.m_field))) {
				CVarMe cv = (CVarMe) this.m_tempVars.get(Integer.valueOf(var.m_field));
				return cv.GetValue(this, var);
			}

			if ((var.m_expression.length() > 0) && (var.m_expression.startsWith("'"))) {
				return CStrMe.ConvertStrToDouble(var.m_expression.substring(1, var.m_expression.length() - 1));
			}

			if (var.m_splitExpression != null) {
				return Calculate(var.m_splitExpression);
			}

			return 0.0D;

		case 2:
			return this.m_dataSource.Get3(this.m_index, var.m_fieldIndex);
		case 3:
			return var.m_value;
		}
		return 0.0D;
	}

	public CVariableMe GetVariable(String name) {
		if (this.m_tempVariables.containsKey(name)) {
			return (CVariableMe) this.m_tempVariables.get(name);
		}

		return null;
	}

	public static boolean IsNumeric(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				if (str.charAt(i) != '.') {
					return false;
				}
			}
		}
		return true;
	}

	public void OnCalculate(int index) {
		synchronized (this) {
			if ((this.m_lines != null) && (this.m_lines.size() > 0)) {
				for (CVariableMe vr : this.m_lines) {
					if (vr.m_field != CTableMe.NULLFIELD) {
						vr.m_fieldIndex = this.m_dataSource.GetColumnIndex(vr.m_field);
					}
				}
				for (CVariableMe tmp : this.m_variables) {
					if (tmp.m_field != CTableMe.NULLFIELD) {
						tmp.m_fieldIndex = this.m_dataSource.GetColumnIndex(tmp.m_field);
					}
					if (tmp.m_tempFields != null) {
						for (int j = 0; j < tmp.m_tempFields.length; j++) {
							tmp.m_tempFieldsIndex[j] = this.m_dataSource.GetColumnIndex(tmp.m_tempFields[j]);
						}
					}
				}
				for (int j = index; j < this.m_dataSource.GetRowsCount(); j++) {
					this.m_break = 0;
					this.m_index = j;
					int size = this.m_lines.size();
					for (int m = 0; m < size; m++) {
						CVariableMe vr = (CVariableMe) this.m_lines.get(m);
						if ((vr.m_funcName == null) || ((vr.m_funcName != null) && (vr.m_line != m))) {
							double ddd = Calculate(vr.m_splitExpression);
							if ((vr.m_type == 1) && (vr.m_field != CTableMe.NULLFIELD)) {
								this.m_dataSource.Set3(j, vr.m_fieldIndex, ddd);
							}
						}
						if (this.m_break == 1) {
							break;
						}
					}
				}
			}
		}
	}

	public void RemoveFunction(CFunctionMe function) {
		this.m_functions.remove(function.m_name);
		this.m_functionsMap.remove(Integer.valueOf(function.m_ID));
	}

	private String Replace(String parameter) {
		String[] sSplit = SplitExpression2(parameter);
		String str;
		for (int i = 0; i < sSplit.length; i++) {
			str = sSplit[i];
			if (this.m_defineParams.containsKey(str)) {
				sSplit[i] = ((Double) this.m_defineParams.get(str)).toString();
			} else {
				for (CVariableMe vr : this.m_variables) {
					if ((vr.m_type == 2) && (vr.m_expression.equals(str))) {
						sSplit[i] = vr.m_name;
						break;
					}
				}
			}
		}
		String res = "";
		for (int i = 0; i < sSplit.length - 1; i++) {
			res = res + sSplit[i];
		}
		return res;
	}

	public void SetSourceField(String key, int value) {
		CVariableMe vr = new CVariableMe(this);
		vr.m_type = 2;
		vr.m_name = String.format("%s%d", new Object[] { VARIABLE, Integer.valueOf(this.m_variables.size()) });
		vr.m_expression = key;
		vr.m_splitExpression = SplitExpression(key);
		vr.m_field = value;
		this.m_variables.add(vr);
	}

	public void SetSourceValue(String key, double value) {
		CVariableMe var = null;
		for (CVariableMe cv : this.m_variables) {
			if ((cv.m_type == 3) && (cv.m_expression.equals(key))) {
				var = cv;
				break;
			}
		}
		if (var == null) {
			var = new CVariableMe(this);
			var.m_type = 3;
			var.m_name = String.format("%s%d", new Object[] { VARIABLE, Integer.valueOf(this.m_variables.size()) });
			var.m_expression = key;
			var.m_splitExpression = SplitExpression(key);
			this.m_variables.add(var);
		}
		var.m_value = value;
	}

	public void SetVariable(CVariableMe variable, CVariableMe parameter) {
		int type = variable.m_type;
		int field = variable.m_field;
		switch (type) {
		case 2:
			double dVal = GetValue(parameter);
			this.m_dataSource.Set3(this.m_index, variable.m_fieldIndex, dVal);
			break;
		default:
			if (this.m_tempVars.containsKey(Integer.valueOf(field))) {
				CVarMe tempV = (CVarMe) this.m_tempVars.get(Integer.valueOf(field));
				tempV.SetValue(this, variable, parameter);
				if (this.m_resultVar != null) {
					tempV.m_str = this.m_resultVar.m_str;
					this.m_resultVar = null;
				}
			} else {
				variable.m_value = GetValue(parameter);
			}
			break;
		}
	}

	private CMathElementMe[] SplitExpression(String expression) {
		CMathElementMe[] elements = null;
		ArrayList<String> arrayList = new ArrayList();
		int exlen = expression.length();
		String sFLX = "";
		String sFZW = "";
		boolean bHS = false;
		while (exlen != 0) {
			sFZW = expression.substring(expression.length() - exlen, expression.length() - exlen + 1);
			if (sFZW.equals("'")) {
				bHS = !bHS;
			}
			if ((bHS) || (GetOperator(sFZW) == -1)) {
				sFLX = sFLX + sFZW;
			} else {
				if (!sFLX.equals("")) {
					arrayList.add(sFLX);
				}
				sFLX = "";
				int idcx = expression.length() - exlen + 1;
				String ss = "";
				if (idcx < expression.length() - 1) {
					ss = expression.substring(idcx, idcx + 1);
				}
				String sAFH = sFZW + ss;
				if ((sAFH.equals(">=")) || (sAFH.equals("<=")) || (sAFH.equals("<>"))) {
					arrayList.add(sAFH);
					exlen--;
				} else {
					arrayList.add(sFZW);
				}
			}
			exlen--;
		}
		if (!sFLX.equals("")) {
			arrayList.add(sFLX);
		}
		elements = new CMathElementMe[arrayList.size() + 1];
		int sizeYLK = arrayList.size();
		for (int i = 0; i < sizeYLK; i++) {
			CMathElementMe newElem = new CMathElementMe();
			String stringII = (String) arrayList.get(i);
			int iOP = GetOperator(stringII);
			if (iOP != -1) {
				newElem.m_type = 0;
				newElem.m_value = iOP;
			} else {
				boolean bCG = IsNumeric(stringII);
				if (bCG) {
					double zc = Double.parseDouble(stringII);
					newElem.m_type = 1;
					newElem.m_value = zc;
				} else {
					for (CVariableMe chars : this.m_variables) {
						if ((chars.m_name.equals(stringII)) || (chars.m_expression.equals(stringII))) {
							newElem.m_type = 2;
							newElem.m_var = chars;
							break;
						}
					}
				}
			}
			elements[i] = newElem;
		}
		CMathElementMe ll = new CMathElementMe();
		ll.m_type = 3;
		elements[arrayList.size()] = ll;
		return elements;
	}

	private String[] SplitExpression2(String expression) {
		String[] res = null;
		ArrayList<String> arrayList = new ArrayList();
		int len = expression.length();
		String str = "";
		String sNM = "";
		boolean bDD = false;
		while (len != 0) {
			sNM = expression.substring(expression.length() - len, expression.length() - len + 1);
			if (sNM.equals("'")) {
				bDD = !bDD;
			}
			if ((bDD) || (GetOperator(sNM) == -1)) {
				str = str + sNM;
			} else {
				if (!str.equals("")) {
					arrayList.add(str);
				}
				str = "";
				int nextIndex = expression.length() - len + 1;
				String chNext = "";
				if (nextIndex < expression.length() - 1) {
					chNext = expression.substring(nextIndex, nextIndex + 1);
				}
				String unionText = sNM + chNext;
				if ((unionText.equals(">=")) || (unionText.equals("<=")) || (unionText.equals("<>"))) {
					arrayList.add(unionText);
					len--;
				} else {
					arrayList.add(sNM);
				}
			}
			len--;
		}
		if (!str.equals("")) {
			arrayList.add(str);
		}
		res = new String[arrayList.size() + 1];
		for (int i = 0; i < arrayList.size(); i++) {
			res[i] = ((String) arrayList.get(i));
		}
		res[arrayList.size()] = "#";
		return res;
	}

	private double ABS(CVariableMe var) {
		return Math.abs(GetValue(var.m_parameters[0]));
	}

	private double AMA(CVariableMe var) {
		double dv = GetValue(var.m_parameters[0]);
		double dZH = 0.0D;
		if (this.m_index > 0) {
			dZH = this.m_dataSource.Get3(this.m_index - 1, var.m_fieldIndex);
		}
		double temp = GetValue(var.m_parameters[1]);
		double res = dZH + temp * (dv - dZH);
		this.m_dataSource.Set3(this.m_index, var.m_fieldIndex, res);
		return res;
	}

	private double ACOS(CVariableMe var) {
		return Math.acos(GetValue(var.m_parameters[0]));
	}

	private double ASIN(CVariableMe var) {
		return Math.asin(GetValue(var.m_parameters[0]));
	}

	private double ATAN(CVariableMe var) {
		return Math.atan(GetValue(var.m_parameters[0]));
	}

	private double AVEDEV(CVariableMe var) {
		int iPP = (int) GetValue(var.m_parameters[1]);
		CVariableMe param0 = var.m_parameters[0];
		int index = param0.m_fieldIndex;
		double dJSL = GetValue(param0);
		int field = param0.m_field;
		if (index == -1) {
			if (var.m_tempFields == null) {
				var.CreateTempFields(1);
			}
			index = var.m_tempFieldsIndex[0];
			field = var.m_tempFields[0];
			this.m_dataSource.Set3(this.m_index, index, dJSL);
		}
		double[] dSB = this.m_dataSource.DATA_ARRAY(field, this.m_index, iPP);
		double avg = 0.0D;
		if ((dSB != null) && (dSB.length > 0)) {
			double dJ = 0.0D;
			for (int i = 0; i < dSB.length; i++) {
				dJ += dSB[i];
			}
			avg = dJ / dSB.length;
		}
		return CMathLibMe.M002(dJSL, dSB, dSB.length, avg);
	}

	private int BARSCOUNT(CVariableMe var) {
		return this.m_dataSource.GetRowsCount();
	}

	private int BARSLAST(CVariableMe var) {
		int exlen = 0;
		int newElem = this.m_index;
		for (int i = this.m_index; i >= 0; i--) {
			this.m_index = i;
			double tt = GetValue(var.m_parameters[0]);
			if (tt == 1.0D) {
				break;
			}

			if (i == 0) {
				exlen = 0;
			} else {
				exlen++;
			}
		}

		this.m_index = newElem;
		return exlen;
	}

	private int BETWEEN(CVariableMe var) {
		double newElem = GetValue(var.m_parameters[0]);
		double nm = GetValue(var.m_parameters[1]);
		double bDD = GetValue(var.m_parameters[2]);
		int exlen = 0;
		if ((newElem >= nm) && (newElem <= bDD)) {
			exlen = 1;
		}
		return exlen;
	}

	private int BREAK(CVariableMe var) {
		this.m_break = 2;
		return 0;
	}

	private double CEILING(CVariableMe var) {
		return Math.ceil(GetValue(var.m_parameters[0]));
	}

	private double CHUNK(CVariableMe var) {
		int len = var.m_parameters.length;
		if (len > 0) {
			for (int i = 0; (this.m_break == 0) && (i < len); i++) {
				GetValue(var.m_parameters[i]);
			}
		}
		DeleteTempVars(var);
		return 0.0D;
	}

	private double COS(CVariableMe var) {
		return Math.cos(GetValue(var.m_parameters[0]));
	}

	private int CONTINUE(CVariableMe var) {
		this.m_break = 3;
		return 0;
	}

	private int COUNT(CVariableMe var) {
		int param1 = (int) GetValue(var.m_parameters[1]);
		if (param1 < 0) {
			param1 = this.m_dataSource.GetRowsCount();
		} else if (param1 > this.m_index + 1) {
			param1 = this.m_index + 1;
		}
		int index = this.m_index;
		int res = 0;
		for (int i = 0; i < param1; i++) {
			if (GetValue(var.m_parameters[0]) > 0.0D) {
				res++;
			}
			this.m_index -= 1;
		}
		this.m_index = index;
		return res;
	}

	private int CROSS(CVariableMe var) {
		double param0 = GetValue(var.m_parameters[0]);
		double param1 = GetValue(var.m_parameters[1]);
		int res = 0;
		int index = this.m_index;
		this.m_index -= 1;
		if (this.m_index < 0) {
			this.m_index = 0;
		}
		double dVV = GetValue(var.m_parameters[0]);
		double dCZ = GetValue(var.m_parameters[1]);
		this.m_index = index;
		if ((param0 >= param1) && (dVV < dCZ)) {
			res = 1;
		}
		return res;
	}

	private int CURRBARSCOUNT(CVariableMe var) {
		return this.m_index + 1;
	}

	private int DATE(CVariableMe var) {
		int iGS = 0;
		int iGT = 0;
		int iGX = 0;
		int iGK = 0;
		int iGL = 0;
		int iGZ = 0;
		int IGTH = 0;
		RefObject<Integer> exlen = new RefObject(Integer.valueOf(iGS));
		RefObject<Integer> refGT = new RefObject(Integer.valueOf(iGT));
		RefObject<Integer> bDD = new RefObject(Integer.valueOf(iGX));
		RefObject<Integer> newElem = new RefObject(Integer.valueOf(iGK));
		RefObject<Integer> refGL = new RefObject(Integer.valueOf(iGL));
		RefObject<Integer> refGZ = new RefObject(Integer.valueOf(iGZ));
		RefObject<Integer> refGTH = new RefObject(Integer.valueOf(IGTH));
		CMathLibMe.M130(this.m_dataSource.GetXValue(this.m_index), exlen, refGT, bDD, newElem, refGL, refGZ, refGTH);
		iGS = ((Integer) exlen.argvalue).intValue();
		iGT = ((Integer) refGT.argvalue).intValue();
		iGX = ((Integer) bDD.argvalue).intValue();
		iGK = ((Integer) newElem.argvalue).intValue();
		iGL = ((Integer) refGL.argvalue).intValue();
		iGZ = ((Integer) refGZ.argvalue).intValue();
		IGTH = ((Integer) refGTH.argvalue).intValue();
		return iGS * 10000 + iGT * 100 + iGX;
	}

	private int DAY(CVariableMe var) {
		return CStrMe.ConvertNumToDate(this.m_dataSource.GetXValue(this.m_index)).get(5);
	}

	private int DELETE(CVariableMe var) {
		int len = var.m_parameters.length;
		for (int i = 0; i < len; i++) {
			CVariableMe cv = var.m_parameters[i];
			int field = cv.m_field;
			if (this.m_tempVars.containsKey(Integer.valueOf(field))) {
				CVarMe tempVar = (CVarMe) this.m_tempVars.get(Integer.valueOf(field));
				if (tempVar.m_parent != null) {
					this.m_tempVars.put(Integer.valueOf(field), tempVar.m_parent);
				} else {
					this.m_tempVars.remove(Integer.valueOf(field));
				}
				tempVar.Dispose();
			}
		}
		return 0;
	}

	private double DMA(CVariableMe var) {
		double param0 = GetValue(var.m_parameters[0]);
		double d = 0.0D;
		if (this.m_index > 0) {
			d = this.m_dataSource.Get3(this.m_index - 1, var.m_fieldIndex);
		}
		double param1 = GetValue(var.m_parameters[1]);
		double res = param1 * param0 + (1.0D - param1) * d;
		this.m_dataSource.Set3(this.m_index, var.m_fieldIndex, res);
		return res;
	}

	private int DOTIMES(CVariableMe var) {
		int param0 = (int) GetValue(var.m_parameters[0]);
		int len = var.m_parameters.length;
		if (len > 1) {
			for (int i = 0; i < param0; i++) {
				for (int j = 1; (this.m_break == 0) && (j < len); j++) {
					GetValue(var.m_parameters[j]);
				}
				if (this.m_break > 0) {
					if (this.m_break == 3) {
						this.m_break = 0;
						DeleteTempVars(var);

					} else {
						this.m_break = 0;
						DeleteTempVars(var);
						break;
					}

				} else {
					DeleteTempVars(var);
				}
			}
		}
		return 0;
	}

	private int DOWHILE(CVariableMe var) {
		int len = var.m_parameters.length;
		if (len > 1) {
			for (;;) {
				for (int i = 0; (this.m_break == 0) && (i < len - 1); i++) {
					GetValue(var.m_parameters[i]);
				}
				if (this.m_break > 0) {
					if (this.m_break == 3) {
						this.m_break = 0;
						DeleteTempVars(var);

					} else {
						this.m_break = 0;
						DeleteTempVars(var);
						break;
					}
				} else {
					double dv = GetValue(var.m_parameters[(len - 1)]);
					DeleteTempVars(var);
					if (dv <= 0.0D) {
						break;
					}
				}
			}
		}
		return 0;
	}

	private int DOWNNDAY(CVariableMe var) {
		int bDD = (int) GetValue(var.m_parameters[0]);
		if (bDD < 0) {
			bDD = this.m_dataSource.GetRowsCount();
		} else if (bDD > this.m_index + 1) {
			bDD = this.m_index + 1;
		}
		int index = this.m_index;
		int res = 1;
		for (int i = 0; i < bDD; i++) {
			double ll = GetValue(var.m_parameters[0]);
			this.m_index -= 1;
			double yx = this.m_index >= 0 ? GetValue(var.m_parameters[0]) : 0.0D;
			if (ll >= yx) {
				res = 0;
				break;
			}
		}
		this.m_index = index;
		return res;
	}

	private double DRAWICON(CVariableMe var) {
		if (this.m_div != null) {
			CVariableMe param0 = var.m_parameters[0];
			CVariableMe param1 = var.m_parameters[1];
			CPolylineShapeMe shape = null;
			if (var.m_polylineShape == null) {
				String sYX = "COLORAUTO";
				String sELL = "CIRCLEDOT";
				for (int i = 2; i < var.m_parameters.length; i++) {
					String sPress = var.m_parameters[i].m_expression;
					if (sPress.startsWith("COLOR")) {
						sYX = sPress;
						break;
					}
					if (sPress.equals("CIRCLEDOT")) {
						sELL = sPress;
						break;
					}
					if (sPress.equals("POINTDOT")) {
						sELL = sPress;
						break;
					}
				}
				if (var.m_expression.equals("DRAWICON")) {
					sELL = var.m_expression;
				}
				shape = new CPolylineShapeMe();
				this.m_div.AddShape(shape);
				long lineColor = GetColor(sYX);
				shape.SetAttachVScale(this.m_attachVScale);
				shape.SetFieldText(param1.m_fieldText);
				shape.SetColor(lineColor);
				shape.SetStyle(PolylineStyle.Cycle);
				var.CreateTempFields(1);
				var.m_polylineShape = shape;
			} else {
				shape = var.m_polylineShape;
			}
			if ((param1.m_expression != null) && (param1.m_expression.length() > 0)) {
				if (shape.GetFieldName() == CTableMe.NULLFIELD) {
					if (param1.m_field != CTableMe.NULLFIELD) {
						shape.SetFieldName(param1.m_field);
					} else {
						param1.CreateTempFields(1);
						shape.SetFieldName(param1.m_tempFields[0]);
					}
					for (int i = 2; i < var.m_parameters.length; i++) {
						String sPress = var.m_parameters[i].m_expression;
						if (sPress.equals("DRAWTITLE")) {
							if (shape.GetFieldText() != null) {
								this.m_div.GetTitleBar().GetTitles().add(new CTitleMe(shape.GetFieldName(),
										shape.GetFieldText(), shape.GetColor(), 2, true));
							}
						}
					}
				}
				if (param1.m_tempFields != null) {
					double value = GetValue(param1);
					this.m_dataSource.Set3(this.m_index, param1.m_tempFieldsIndex[0], value);
				}
			}
			double dcc = 1.0D;
			if ((param0.m_expression != null) && (param0.m_expression.length() > 0)
					&& (!param0.m_expression.equals("1"))) {
				dcc = GetValue(param0);
				if (dcc != 1.0D) {
					this.m_dataSource.Set3(this.m_index, var.m_tempFieldsIndex[0], -10000.0D);
				} else {
					this.m_dataSource.Set3(this.m_index, var.m_tempFieldsIndex[0], 1.0D);
				}
			}
		}
		return 0.0D;
	}

	private double DRAWKLINE(CVariableMe var) {
		if (this.m_div != null) {
			CVariableMe param0 = var.m_parameters[0];
			CVariableMe param1 = var.m_parameters[1];
			CVariableMe param2 = var.m_parameters[2];
			CVariableMe param3 = var.m_parameters[3];
			CCandleShapeMe shape = null;
			if (var.m_candleShape == null) {
				shape = new CCandleShapeMe();
				shape.SetHighFieldText(param0.m_fieldText);
				shape.SetOpenFieldText(param1.m_fieldText);
				shape.SetLowFieldText(param2.m_fieldText);
				shape.SetCloseFieldText(param3.m_fieldText);
				shape.SetAttachVScale(this.m_attachVScale);
				shape.SetStyle(CandleStyle.Rect);
				this.m_div.AddShape(shape);
				var.m_candleShape = shape;
			} else {
				shape = var.m_candleShape;
			}
			if ((param0.m_expression != null) && (param0.m_expression.length() > 0)) {
				if (shape.GetHighField() == CTableMe.NULLFIELD) {
					if (param0.m_field != CTableMe.NULLFIELD) {
						shape.SetHighField(param0.m_field);
					} else {
						param0.CreateTempFields(1);
						shape.SetHighField(param0.m_tempFields[0]);
					}
				}
				if (param0.m_tempFields != null) {
					double zz = GetValue(param0);
					this.m_dataSource.Set3(this.m_index, param0.m_tempFieldsIndex[0], zz);
				}
			}
			if ((param1.m_expression != null) && (param1.m_expression.length() > 0)) {
				if (param1.m_field != CTableMe.NULLFIELD) {
					shape.SetOpenField(param1.m_field);
				} else {
					param1.CreateTempFields(1);
					shape.SetOpenField(param1.m_tempFields[0]);
				}
				if (param1.m_tempFields != null) {
					double zz = GetValue(param1);
					this.m_dataSource.Set3(this.m_index, param1.m_tempFieldsIndex[0], zz);
				}
			}
			if ((param2.m_expression != null) && (param2.m_expression.length() > 0)) {
				if (param2.m_field != CTableMe.NULLFIELD) {
					shape.SetLowField(param2.m_field);
				} else {
					param2.CreateTempFields(1);
					shape.SetLowField(param2.m_tempFields[0]);
				}
				if (param2.m_tempFields != null) {
					double zz = GetValue(param2);
					this.m_dataSource.Set3(this.m_index, param2.m_tempFieldsIndex[0], zz);
				}
			}
			if ((param3.m_expression != null) && (param3.m_expression.length() > 0)) {
				if (param3.m_field != CTableMe.NULLFIELD) {
					shape.SetCloseField(param3.m_field);
				} else {
					param3.CreateTempFields(1);
					shape.SetCloseField(param3.m_tempFields[0]);
				}
				if (param3.m_tempFields != null) {
					double zz = GetValue(param3);
					this.m_dataSource.Set3(this.m_index, param3.m_tempFieldsIndex[0], zz);
				}
			}
		}
		return 0.0D;
	}

	private double DRAWNULL(CVariableMe var) {
		return Double.NaN;
	}

	private double DRAWTEXT(CVariableMe var) {
		if (this.m_div != null) {
			CVariableMe bDD = var.m_parameters[0];
			CVariableMe exlen = var.m_parameters[1];
			CVariableMe newElem = var.m_parameters[2];
			CTextShapeMe refGT = null;
			if (var.m_textShape == null) {
				refGT = new CTextShapeMe();
				refGT.SetAttachVScale(this.m_attachVScale);
				refGT.SetText(GetText(newElem));
				var.CreateTempFields(1);
				refGT.SetStyleField(var.m_tempFields[0]);
				String refGZ = "COLORAUTO";
				for (int refGL = 3; refGL < var.m_parameters.length; refGL++) {
					String refGTH = var.m_parameters[refGL].m_expression;
					if (refGTH.startsWith("COLOR")) {
						refGZ = refGTH;
						break;
					}
				}
				if (!refGZ.equals("COLORAUTO")) {
					refGT.SetForeColor(GetColor(refGZ));
				}
				this.m_div.AddShape(refGT);
				var.m_textShape = refGT;
			} else {
				refGT = var.m_textShape;
			}
			if ((exlen.m_expression != null) && (exlen.m_expression.length() > 0)) {
				if (refGT.GetFieldName() == CTableMe.NULLFIELD) {
					if (exlen.m_field != CTableMe.NULLFIELD) {
						refGT.SetFieldName(exlen.m_field);
					} else {
						exlen.CreateTempFields(1);
						refGT.SetFieldName(exlen.m_tempFields[0]);
					}
				}
				if (exlen.m_tempFields != null) {
					double zz = GetValue(exlen);
					this.m_dataSource.Set3(this.m_index, exlen.m_tempFieldsIndex[0], zz);
				}
			}
			double dtt = 1.0D;
			if ((bDD.m_expression != null) && (bDD.m_expression.length() > 0) && (!bDD.m_expression.equals("1"))) {
				dtt = GetValue(bDD);
				if (dtt != 1.0D) {
					this.m_dataSource.Set3(this.m_index, var.m_tempFieldsIndex[0], -10000.0D);
				} else {
					this.m_dataSource.Set3(this.m_index, var.m_tempFieldsIndex[0], 0.0D);
				}
			}
		}
		return 0.0D;
	}

	private int EXIST(CVariableMe var) {
		int param1 = (int) GetValue(var.m_parameters[1]);
		if (param1 < 0) {
			param1 = this.m_dataSource.GetRowsCount();
		} else if (param1 > this.m_index + 1) {
			param1 = this.m_index + 1;
		}
		int index = this.m_index;
		int res = 0;
		for (int i = 0; i < param1; i++) {
			if (GetValue(var.m_parameters[0]) > 0.0D) {
				res = 1;
				break;
			}
			this.m_index -= 1;
		}
		this.m_index = index;
		return res;
	}

	private double EMA(CVariableMe var) {
		double param0 = GetValue(var.m_parameters[0]);
		double dHE = 0.0D;
		if (this.m_index > 0) {
			dHE = this.m_dataSource.Get3(this.m_index - 1, var.m_fieldIndex);
		}
		int jm = (int) GetValue(var.m_parameters[1]);
		double res = CMathLibMe.M006(jm, param0, dHE);
		this.m_dataSource.Set3(this.m_index, var.m_fieldIndex, res);
		return res;
	}

	private int EVERY(CVariableMe var) {
		int param1 = (int) GetValue(var.m_parameters[1]);
		if (param1 < 0) {
			param1 = this.m_dataSource.GetRowsCount();
		} else if (param1 > this.m_index + 1) {
			param1 = this.m_index + 1;
		}
		int index = this.m_index;
		int refGTH = 1;
		for (int i = 0; i < param1; i++) {
			if (GetValue(var.m_parameters[0]) <= 0.0D) {
				refGTH = 0;
				break;
			}
			this.m_index -= 1;
		}
		this.m_index = index;
		return refGTH;
	}

	private double EXPMEMA(CVariableMe var) {
		CVariableMe param0 = var.m_parameters[0];
		double gVal = GetValue(param0);
		int index = param0.m_fieldIndex;
		int param1 = (int) GetValue(var.m_parameters[1]);
		if (var.m_tempFields == null) {
			if (index == -1) {
				var.CreateTempFields(2);
			} else {
				var.CreateTempFields(1);
			}
		}
		if (var.m_tempFields.length == 2) {
			index = var.m_tempFieldsIndex[1];
			this.m_dataSource.Set3(this.m_index, index, gVal);
		}
		int i = var.m_tempFieldsIndex[0];
		double refGTH = CMathLibMe.M003(this.m_index, param1, gVal, GetDatas(index, i, this.m_index - 1, param1));
		this.m_dataSource.Set3(this.m_index, i, refGTH);
		double dvvv = 0.0D;
		if (this.m_index > 0) {
			dvvv = this.m_dataSource.Get3(this.m_index, var.m_fieldIndex);
		}
		double res = CMathLibMe.M006(param1, refGTH, dvvv);
		this.m_dataSource.Set3(this.m_index, var.m_fieldIndex, res);
		return res;
	}

	private double EXP(CVariableMe var) {
		return Math.exp(GetValue(var.m_parameters[0]));
	}

	private double FLOOR(CVariableMe var) {
		return Math.floor(GetValue(var.m_parameters[0]));
	}

	private int FOR(CVariableMe var) {
		int len = var.m_parameters.length;
		if (len > 3) {
			int param0 = (int) GetValue(var.m_parameters[0]);
			int param1 = (int) GetValue(var.m_parameters[1]);
			int param2 = (int) GetValue(var.m_parameters[2]);
			if (param2 > 0) {
				for (int i = param0; i < param1; i += param2) {
					for (int j = 3; j < len; j++) {
						GetValue(var.m_parameters[j]);
						if (this.m_break != 0) {
							break;
						}
					}

					if (this.m_break > 0) {
						if (this.m_break == 3) {
							this.m_break = 0;
							DeleteTempVars(var);

						} else {
							this.m_break = 0;
							DeleteTempVars(var);
							break;
						}

					} else {
						DeleteTempVars(var);
					}

				}
			} else if (param2 < 0) {
				for (int i = param0; i > param1; i += param2) {
					for (int j = 3; j < len; j++) {
						if (this.m_break != 0) {
							break;
						}
					}

					if (this.m_break > 0) {
						if (this.m_break == 3) {
							this.m_break = 0;
							DeleteTempVars(var);

						} else {
							this.m_break = 0;
							DeleteTempVars(var);
							break;
						}

					} else {
						DeleteTempVars(var);
					}
				}
			}
		}
		return 0;
	}

	private double FUNCTION(CVariableMe var) {
		this.m_result = 0.0D;
		if (var.m_parameters != null) {
			int refGTH = var.m_parameters.length;
			if (refGTH > 0) {
				for (int i = 0; i < refGTH; i++) {
					GetValue(var.m_parameters[i]);
				}
			}
		}
		String dvvv = var.m_expression;
		if (this.m_tempFunctions.containsKey(dvvv)) {
			GetValue((CVariableMe) this.m_tempFunctions.get(dvvv));
		}
		if (this.m_break == 1) {
			this.m_break = 0;
		}
		double res = this.m_result;
		this.m_result = 0.0D;
		DeleteTempVars(var);
		return res;
	}

	private double FUNCVAR(CVariableMe var) {
		double res = 0.0D;
		int len = var.m_parameters.length;
		HashMap<CVarMe, Integer> hashMap = new HashMap();
		for (int i = 0; i < len; i++) {
			if (i % 2 == 0) {
				CVariableMe varI = var.m_parameters[i];
				CVariableMe value = var.m_parameters[(i + 1)];
				int field = varI.m_field;
				if (varI.m_expression.indexOf("[REF]") == 0) {
					int size = this.m_variables.size();
					for (int refGTH = 0; refGTH < size; refGTH++) {
						CVariableMe vvv = (CVariableMe) this.m_variables.get(refGTH);
						if (vvv != varI) {
							if (vvv.m_field == field) {
								vvv.m_field = value.m_field;
							}

						}
					}
				} else {
					CVarMe tempV = this.m_varFactory.CreateVar();
					res = tempV.OnCreate(this, varI, value);
					if (tempV.m_type == 1) {
						varI.m_functionID = -1;
					}
					hashMap.put(tempV, Integer.valueOf(field));
				}
			}
		}
		for (Entry<CVarMe, Integer> entry : hashMap.entrySet()) {
			int field = ((Integer) entry.getValue()).intValue();
			CVarMe tempV = (CVarMe) entry.getKey();
			if (this.m_tempVars.containsKey(Integer.valueOf(field))) {
				CVarMe tv = (CVarMe) this.m_tempVars.get(Integer.valueOf(field));
				tempV.m_parent = tv;
			}
			this.m_tempVars.put(Integer.valueOf(field), tempV);
		}
		hashMap.clear();
		return res;
	}

	private double GET(CVariableMe var) {
		return GetValue(var.m_parameters[0]);
	}

	private double HHV(CVariableMe var) {
		int n = (int) GetValue(var.m_parameters[1]);
		CVariableMe param0 = var.m_parameters[0];
		int index = param0.m_fieldIndex;
		int field = param0.m_field;
		if (index == -1) {
			if (param0.m_tempFields == null) {
				param0.CreateTempFields(0);
			}
			index = param0.m_tempFieldsIndex[0];
			field = param0.m_tempFields[0];
			double dl = GetValue(param0);
			this.m_dataSource.Set3(this.m_index, index, dl);
		}
		double[] gd = this.m_dataSource.DATA_ARRAY(field, this.m_index, n);
		return CMathLibMe.GetMax(gd, gd.length);
	}

	private double HHVBARS(CVariableMe var) {
		int param1 = (int) GetValue(var.m_parameters[1]);
		CVariableMe param0 = var.m_parameters[0];
		int field = param0.m_field;
		int index = param0.m_fieldIndex;
		if (index == -1) {
			if (param0.m_tempFields == null) {
				param0.CreateTempFields(0);
			}
			field = param0.m_tempFields[0];
			index = param0.m_tempFieldsIndex[0];
			double dd = GetValue(param0);
			this.m_dataSource.Set3(this.m_index, index, dd);
		}
		double[] refGTH = this.m_dataSource.DATA_ARRAY(field, this.m_index, param1);
		double res = 0.0D;
		if (refGTH.length > 0) {
			int tempI = 0;
			double dDK = 0.0D;
			for (int j = 0; j < refGTH.length; j++) {
				if (j == 0) {
					dDK = refGTH[j];
					tempI = 0;
				} else if (refGTH[j] > dDK) {
					dDK = refGTH[j];
					tempI = j;
				}
			}

			res = refGTH.length - tempI - 1;
		}
		return res;
	}

	private int HOUR(CVariableMe var) {
		return CStrMe.ConvertNumToDate(this.m_dataSource.GetXValue(this.m_index)).get(11);
	}

	private double IF(CVariableMe var) {
		double res = 0.0D;
		int len = var.m_parameters.length;
		for (int i = 0; i < len; i++) {
			res = GetValue(var.m_parameters[i]);
			if (i % 2 != 0)
				break;
			if (res == 0.0D) {
				i++;
			}
		}

		DeleteTempVars(var);
		return res;
	}

	private double IFN(CVariableMe var) {
		double res = 0.0D;
		int len = var.m_parameters.length;
		for (int i = 0; i < len; i++) {
			res = GetValue(var.m_parameters[i]);
			if (i % 2 != 0)
				break;
			if (res != 0.0D) {
				i++;
			}
		}

		DeleteTempVars(var);
		return res;
	}

	private double INTPART(CVariableMe var) {
		double res = GetValue(var.m_parameters[0]);
		if (res != 0.0D) {
			int temo = (int) res;
			double refGTH = Math.abs(res - temo);
			if (refGTH >= 0.5D) {
				if (res > 0.0D) {
					res = temo - 1;
				} else {
					res = temo + 1;
				}

			} else {
				res = temo;
			}
		}
		return res;
	}

	private int LAST(CVariableMe var) {
		int param1 = (int) GetValue(var.m_parameters[1]);
		int param2 = (int) GetValue(var.m_parameters[2]);
		if (param1 < 0) {
			param1 = this.m_dataSource.GetRowsCount();
		} else if (param1 > this.m_index + 1) {
			param1 = this.m_index + 1;
		}
		if (param2 < 0) {
			param2 = this.m_dataSource.GetRowsCount();
		} else if (param2 > this.m_index + 1) {
			param2 = this.m_index + 1;
		}
		int refGTH = this.m_index;
		int res = 1;
		for (int j = param2; j < param1; j++) {
			this.m_index = (refGTH - param2);
			if (GetValue(var.m_parameters[0]) <= 0.0D) {
				res = 0;
				break;
			}
		}
		this.m_index = refGTH;
		return res;
	}

	private double LLV(CVariableMe var) {
		int param1 = (int) GetValue(var.m_parameters[1]);
		CVariableMe param0 = var.m_parameters[0];
		int field = param0.m_field;
		int index = param0.m_fieldIndex;
		if (field == CTableMe.NULLFIELD) {
			if (param0.m_tempFields == null) {
				param0.CreateTempFields(0);
			}
			field = param0.m_tempFields[0];
			index = param0.m_tempFieldsIndex[0];
			double dv = GetValue(param0);
			this.m_dataSource.Set3(this.m_index, index, dv);
		}
		double[] array = this.m_dataSource.DATA_ARRAY(field, this.m_index, param1);
		return CMathLibMe.GetMin(array, array.length);
	}

	private double LLVBARS(CVariableMe var) {
		int param1 = (int) GetValue(var.m_parameters[1]);
		CVariableMe param0 = var.m_parameters[0];
		int field = param0.m_field;
		int index = param0.m_fieldIndex;
		if (field == CTableMe.NULLFIELD) {
			if (param0.m_tempFields == null) {
				param0.CreateTempFields(0);
			}
			field = param0.m_tempFields[0];
			index = param0.m_tempFieldsIndex[0];
			double dd = GetValue(param0);
			this.m_dataSource.Set3(this.m_index, index, dd);
		}
		double[] dArray = this.m_dataSource.DATA_ARRAY(field, this.m_index, param1);
		double res = 0.0D;
		if (dArray.length > 0) {
			int tempIdx = 0;
			double dd = 0.0D;
			for (int j = 0; j < dArray.length; j++) {
				if (dd == 0.0D) {
					dd = dArray[j];
					tempIdx = 0;

				} else if (dArray[j] < dd) {
					dd = dArray[j];
					tempIdx = j;
				}
			}

			res = dArray.length - tempIdx - 1;
		}
		return res;
	}

	private double LOG(CVariableMe var) {
		return Math.log10(GetValue(var.m_parameters[0]));
	}

	private double MA(CVariableMe var) {
		CVariableMe param0 = var.m_parameters[0];
		double dv = GetValue(param0);
		int param1 = (int) GetValue(var.m_parameters[1]);
		int index = param0.m_fieldIndex;
		if (index == -1) {
			if (var.m_tempFields == null) {
				var.CreateTempFields(1);
			}
			index = var.m_tempFieldsIndex[0];
			this.m_dataSource.Set3(this.m_index, index, dv);
		}
		double res = CMathLibMe.M003(this.m_index, param1, dv,
				GetDatas(index, var.m_fieldIndex, this.m_index - 1, param1));
		this.m_dataSource.Set3(this.m_index, var.m_fieldIndex, res);
		return res;
	}

	private double MAX(CVariableMe var) {
		double param0 = GetValue(var.m_parameters[0]);
		double res = GetValue(var.m_parameters[1]);
		if (param0 >= res) {
			return param0;
		}

		return res;
	}

	private double MEMA(CVariableMe var) {
		double param0 = GetValue(var.m_parameters[0]);
		int param1 = (int) GetValue(var.m_parameters[1]);
		double d = 0.0D;
		if (this.m_index > 0) {
			d = this.m_dataSource.Get3(this.m_index - 1, var.m_fieldIndex);
		}
		double res = CMathLibMe.M015(param0, d, param1, 1);
		this.m_dataSource.Set3(this.m_index, var.m_fieldIndex, res);
		return res;
	}

	private double MIN(CVariableMe var) {
		double param0 = GetValue(var.m_parameters[0]);
		double param1 = GetValue(var.m_parameters[1]);
		if (param0 <= param1) {
			return param0;
		}

		return param1;
	}

	private int MINUTE(CVariableMe var) {
		return CStrMe.ConvertNumToDate(this.m_dataSource.GetXValue(this.m_index)).get(12);
	}

	private double MOD(CVariableMe var) {
		double param0 = GetValue(var.m_parameters[0]);
		double param1 = GetValue(var.m_parameters[1]);
		if (param1 != 0.0D) {
			return param0 % param1;
		}
		return 0.0D;
	}

	private int MONTH(CVariableMe var) {
		return CStrMe.ConvertNumToDate(this.m_dataSource.GetXValue(this.m_index)).get(2) + 1;
	}

	private int NDAY(CVariableMe var) {
		int param2 = (int) GetValue(var.m_parameters[2]);
		if (param2 < 0) {
			param2 = this.m_dataSource.GetRowsCount();
		} else if (param2 > this.m_index + 1) {
			param2 = this.m_index + 1;
		}
		int index = this.m_index;
		int res = 1;
		for (int i = 0; i < param2; i++) {
			if (GetValue(var.m_parameters[0]) <= GetValue(var.m_parameters[1])) {
				res = 0;
				break;
			}
			this.m_index -= 1;
		}
		this.m_index = index;
		return res;
	}

	private int NOT(CVariableMe var) {
		double dv = GetValue(var.m_parameters[0]);
		if (dv == 0.0D) {
			return 1;
		}

		return 0;
	}

	private double POLYLINE(CVariableMe var) {
		if (this.m_div != null) {
			CVariableMe param0 = var.m_parameters[0];
			CVariableMe param1 = var.m_parameters[1];
			CPolylineShapeMe xyf = null;
			if (var.m_polylineShape == null) {
				String sGZ = "COLORAUTO";
				String sZQ = "LINETHICK";
				boolean bSQ = false;
				for (int i = 2; i < var.m_parameters.length; i++) {
					String sZG = var.m_parameters[i].m_expression;
					if (sZG.startsWith("COLOR")) {
						sGZ = sZG;
					} else if (sZG.startsWith("LINETHICK")) {
						sZQ = sZG;
					} else if (sZG.startsWith("DOTLINE")) {
						bSQ = true;
					}
				}
				xyf = new CPolylineShapeMe();
				this.m_div.AddShape(xyf);
				xyf.SetAttachVScale(this.m_attachVScale);
				xyf.SetColor(GetColor(sGZ));
				xyf.SetWidth(GetLineWidth(sZQ));
				var.CreateTempFields(1);
				xyf.SetColorField(var.m_tempFields[0]);
				xyf.SetFieldText(param1.m_fieldText);
				if (bSQ) {
					xyf.SetStyle(PolylineStyle.DotLine);
				}
				var.m_polylineShape = xyf;
			} else {
				xyf = var.m_polylineShape;
			}
			if ((param1.m_expression != null) && (param1.m_expression.length() > 0)) {
				if (xyf.GetFieldName() == CTableMe.NULLFIELD) {
					if (param1.m_field != CTableMe.NULLFIELD) {
						xyf.SetFieldName(param1.m_field);
					} else {
						param1.CreateTempFields(1);
						xyf.SetFieldName(param1.m_tempFields[0]);
					}
					for (int i = 2; i < var.m_parameters.length; i++) {
						String sZG = var.m_parameters[i].m_expression;
						if (sZG.equals("DRAWTITLE")) {
							if (xyf.GetFieldText() != null) {
								this.m_div.GetTitleBar().GetTitles().add(
										new CTitleMe(xyf.GetFieldName(), xyf.GetFieldText(), xyf.GetColor(), 2, true));
							}
						}
					}
				}
				if (param1.m_tempFieldsIndex != null) {
					double dx = GetValue(param1);
					this.m_dataSource.Set3(this.m_index, param1.m_tempFieldsIndex[0], dx);
				}
			}
			double dy = 1.0D;
			if ((param0.m_expression != null) && (param0.m_expression.length() > 0)
					&& (!param0.m_expression.equals("1"))) {
				dy = GetValue(param0);
				if (dy != 1.0D) {
					this.m_dataSource.Set3(this.m_index, var.m_tempFieldsIndex[0], -10000.0D);
				} else {
					this.m_dataSource.Set3(this.m_index, var.m_tempFieldsIndex[0], 1.0D);
				}
			}
		}
		return 0.0D;
	}

	private double POW(CVariableMe var) {
		double param0 = GetValue(var.m_parameters[0]);
		double param1 = GetValue(var.m_parameters[1]);
		return Math.pow(param0, param1);
	}

	private int RAND(CVariableMe var) {
		int v = (int) GetValue(var.m_parameters[0]);
		return this.m_random.nextInt(v + 1);
	}

	private double REF(CVariableMe var) {
		int param1 = (int) GetValue(var.m_parameters[1]);
		param1 = this.m_index - param1;
		double d = 0.0D;
		if (param1 >= 0) {
			int dvvv = this.m_index;
			this.m_index = param1;
			d = GetValue(var.m_parameters[0]);
			this.m_index = dvvv;
		}
		return d;
	}

	private double RETURN(CVariableMe var) {
		this.m_resultVar = null;
		this.m_result = GetValue(var.m_parameters[0]);
		if (this.m_tempVars.containsKey(Integer.valueOf(var.m_parameters[0].m_field))) {
			this.m_resultVar = CopyTempVar((CVarMe) this.m_tempVars.get(Integer.valueOf(var.m_parameters[0].m_field)));

		} else if (var.m_parameters[0].m_expression.indexOf('\'') == 0) {
			this.m_resultVar = new CVarMe();
			this.m_resultVar.m_type = 1;
			this.m_resultVar.m_str = var.m_parameters[0].m_expression;
		}

		this.m_break = 1;
		return this.m_result;
	}

	private double REVERSE(CVariableMe var) {
		return -GetValue(var.m_parameters[0]);
	}

	private double ROUND(CVariableMe var) {
		return Math.round(GetValue(var.m_parameters[0]));
	}

	private double SAR(CVariableMe var) {
		int param2 = (int) GetValue(var.m_parameters[2]);
		double param3 = GetValue(var.m_parameters[3]) / 100.0D;
		double param4 = GetValue(var.m_parameters[4]) / 100.0D;
		double dDDD = 0.0D;
		double dTS = 0.0D;
		CVariableMe param0 = var.m_parameters[0];
		CVariableMe param1 = var.m_parameters[1];
		dDDD = GetValue(param0);
		dTS = GetValue(param1);
		if (var.m_tempFields == null) {
			if ((param0.m_field == CTableMe.NULLFIELD) || (param1.m_field == CTableMe.NULLFIELD)) {
				var.CreateTempFields(4);
			} else {
				var.CreateTempFields(2);
			}
		}
		int field = param0.m_field;
		int index = param0.m_fieldIndex;
		if (field == CTableMe.NULLFIELD) {
			field = var.m_tempFields[2];
			index = var.m_tempFieldsIndex[2];
			this.m_dataSource.Set3(this.m_index, index, dDDD);
		}
		int field1 = param1.m_field;
		int index1 = param1.m_fieldIndex;
		if (field1 == CTableMe.NULLFIELD) {
			field1 = var.m_tempFields[3];
			index1 = var.m_tempFieldsIndex[3];
			this.m_dataSource.Set3(this.m_index, index1, dTS);
		}
		double[] dataArray1 = this.m_dataSource.DATA_ARRAY(field, this.m_index - 1, param2);
		double[] dataArray2 = this.m_dataSource.DATA_ARRAY(field1, this.m_index - 1, param2);
		double dSZ = CMathLibMe.GetMax(dataArray1, dataArray1.length);
		double dTTDD = CMathLibMe.GetMin(dataArray2, dataArray2.length);
		int iQWE = 0;
		double dGGSS = 0.0D;
		double dPTT = 0.0D;
		if (this.m_index > 0) {
			iQWE = (int) this.m_dataSource.Get3(this.m_index - 1, var.m_tempFieldsIndex[0]);
			dGGSS = this.m_dataSource.Get3(this.m_index - 1, var.m_fieldIndex);
			dPTT = this.m_dataSource.Get3(this.m_index - 1, var.m_tempFieldsIndex[1]);
		}
		int iEQ = 0;
		double dSM = 0.0D;
		double sar = 0.0D;
		RefObject<Integer> refEQ = new RefObject(Integer.valueOf(iEQ));
		RefObject<Double> refSM = new RefObject(Double.valueOf(dSM));
		RefObject<Double> refSAR = new RefObject(Double.valueOf(sar));
		CMathLibMe.M001(this.m_index, param2, param3, param4, dDDD, dTS, dSZ, dTTDD, iQWE, dGGSS, dPTT, refEQ, refSM,
				refSAR);
		iEQ = ((Integer) refEQ.argvalue).intValue();
		dSM = ((Double) refSM.argvalue).doubleValue();
		sar = ((Double) refSAR.argvalue).doubleValue();
		this.m_dataSource.Set3(this.m_index, var.m_tempFieldsIndex[1], dSM);
		this.m_dataSource.Set3(this.m_index, var.m_tempFieldsIndex[0], iEQ);
		this.m_dataSource.Set3(this.m_index, var.m_fieldIndex, sar);
		return sar;
	}

	private double SET(CVariableMe var) {
		int len = var.m_parameters.length;
		for (int i = 0; i < len; i++) {
			if (i % 2 == 0) {
				CVariableMe varI = var.m_parameters[i];
				CVariableMe varI1 = var.m_parameters[(i + 1)];
				SetVariable(varI, varI1);
			}
		}
		return 0.0D;
	}

	private int SIGN(CVariableMe var) {
		double f = GetValue(var.m_parameters[0]);
		if (f > 0.0D) {
			return 1;
		}
		if (f < 0.0D) {
			return -1;
		}
		return 0;
	}

	private double SIN(CVariableMe var) {
		return Math.sin(GetValue(var.m_parameters[0]));
	}

	private double SMA(CVariableMe var) {
		double param0 = GetValue(var.m_parameters[0]);
		int param1 = (int) GetValue(var.m_parameters[1]);
		int param2 = (int) GetValue(var.m_parameters[2]);
		double ddd = 0.0D;
		if (this.m_index > 0) {
			ddd = this.m_dataSource.Get3(this.m_index - 1, var.m_fieldIndex);
		}
		double res = CMathLibMe.M015(param0, ddd, param1, param2);
		this.m_dataSource.Set3(this.m_index, var.m_fieldIndex, res);
		return res;
	}

	private double SQRT(CVariableMe var) {
		return Math.sqrt(GetValue(var.m_parameters[0]));
	}

	private double SQUARE(CVariableMe var) {
		double vv = GetValue(var.m_parameters[0]);
		vv *= vv;
		return vv;
	}

	private double STD(CVariableMe var) {
		int param1 = (int) GetValue(var.m_parameters[1]);
		CVariableMe param0 = var.m_parameters[0];
		double vv = GetValue(param0);
		int field = param0.m_field;
		int param2 = param0.m_fieldIndex;
		if (field == CTableMe.NULLFIELD) {
			if (var.m_tempFields == null) {
				var.CreateTempFields(1);
			}
			field = var.m_tempFields[0];
			param2 = var.m_tempFieldsIndex[0];
			this.m_dataSource.Set3(this.m_index, param2, vv);
		}
		double[] dayArray = this.m_dataSource.DATA_ARRAY(field, this.m_index, param1);
		double field1 = 0.0D;
		double sum = 0.0D;
		if ((dayArray != null) && (dayArray.length > 0)) {
			for (int i = 0; i < dayArray.length; i++) {
				sum += dayArray[i];
			}
			field1 = sum / dayArray.length;
		}
		double res = CMathLibMe.M007(dayArray, dayArray.length, field1, 1.0D);
		return res;
	}

	private double STICKLINE(CVariableMe var) {
		if (this.m_div != null) {
			CVariableMe param0 = var.m_parameters[0];
			CVariableMe param1 = var.m_parameters[1];
			CVariableMe param2 = var.m_parameters[2];
			CVariableMe param3 = var.m_parameters[3];
			CVariableMe param4 = var.m_parameters[4];
			CBarShapeMe barShape = null;
			if (var.m_barShape == null) {
				barShape = new CBarShapeMe();
				this.m_div.AddShape(barShape);
				barShape.SetAttachVScale(this.m_attachVScale);
				barShape.SetFieldText(param1.m_fieldText);
				barShape.SetFieldText2(param2.m_fieldText);
				CVariableMe cVar = null;
				for (int i = 5; i < var.m_parameters.length; i++) {
					String ex = var.m_parameters[i].m_expression;
					if (ex.startsWith("COLOR")) {
						cVar = var.m_parameters[i];
						break;
					}
				}
				if (cVar != null) {
					barShape.SetUpColor(GetColor(cVar.m_expression));
					barShape.SetDownColor(GetColor(cVar.m_expression));
				} else {
					barShape.SetUpColor(COLOR.ARGB(255, 82, 82));
					barShape.SetDownColor(COLOR.ARGB(82, 255, 255));
				}
				barShape.SetStyle(BarStyle.Line);
				var.CreateTempFields(1);
				barShape.SetStyleField(var.m_tempFields[0]);
				barShape.SetLineWidth((int) Math.round(CStrMe.ConvertStrToDouble(param3.m_expression)));
				var.m_barShape = barShape;
			} else {
				barShape = var.m_barShape;
			}
			if ((param1.m_expression != null) && (param1.m_expression.length() > 0)) {
				if (barShape.GetFieldName() == CTableMe.NULLFIELD) {
					if (param1.m_field != CTableMe.NULLFIELD) {
						barShape.SetFieldName(param1.m_field);
					} else {
						param1.CreateTempFields(1);
						barShape.SetFieldName(param1.m_tempFields[0]);
					}
					for (int i = 5; i < var.m_parameters.length; i++) {
						String ss = var.m_parameters[i].m_expression;
						if (ss.equals("DRAWTITLE")) {
							if (barShape.GetFieldText() == null)
								break;
							this.m_div.GetTitleBar().GetTitles().add(new CTitleMe(barShape.GetFieldName(),
									barShape.GetFieldText(), barShape.GetDownColor(), 2, true));
							break;
						}
					}
				}

				if (param1.m_tempFieldsIndex != null) {
					double vs = GetValue(param1);
					this.m_dataSource.Set3(this.m_index, param1.m_tempFieldsIndex[0], vs);
				}
			}
			if ((param2.m_expression != null) && (param2.m_expression.length() > 0)
					&& (!param2.m_expression.equals("0"))) {
				if (param2.m_field != CTableMe.NULLFIELD) {
					barShape.SetFieldName2(param2.m_field);
				} else {
					param2.CreateTempFields(1);
					barShape.SetFieldName2(param2.m_tempFields[0]);
				}
				if (param2.m_tempFieldsIndex != null) {
					double va = GetValue(param2);
					this.m_dataSource.Set3(this.m_index, param2.m_tempFieldsIndex[0], va);
				}
			}
			double dd = 1.0D;
			if ((param0.m_expression != null) && (param0.m_expression.length() > 0)
					&& (!param0.m_expression.equals("1"))) {
				dd = GetValue(param0);
				if (dd != 1.0D) {
					this.m_dataSource.Set3(this.m_index, var.m_tempFieldsIndex[0], -10000.0D);
				} else {
					int ttt = 2;
					if ((param4.m_expression != null) && (param4.m_expression.length() > 0)) {
						ttt = (int) GetValue(param4);
						this.m_dataSource.Set3(this.m_index, var.m_tempFieldsIndex[0], ttt);
					}
				}
			}
		}
		return 0.0D;
	}

	private double SUM(CVariableMe var) {
		double dVal = GetValue(var.m_parameters[0]);
		int param2 = var.m_parameters[0].m_fieldIndex;
		if (param2 == -1) {
			if (var.m_tempFields == null) {
				var.CreateTempFields(1);
			}
			param2 = var.m_tempFieldsIndex[0];
			this.m_dataSource.Set3(this.m_index, param2, dVal);
		}
		int param1 = (int) GetValue(var.m_parameters[1]);
		if (param1 == 0) {
			param1 = this.m_index + 1;
		}
		double res = CMathLibMe.M004(this.m_index, param1, dVal,
				GetDatas(param2, var.m_fieldIndex, this.m_index - 1, param1));
		this.m_dataSource.Set3(this.m_index, var.m_fieldIndex, res);
		return res;
	}

	private double TAN(CVariableMe var) {
		return Math.tan(GetValue(var.m_parameters[0]));
	}

	private double TIME(CVariableMe var) {
		int i = 0;
		int j = 0;
		int k = 0;
		int l = 0;
		int m = 0;
		int n = 0;
		int t = 0;
		RefObject<Integer> refI = new RefObject(Integer.valueOf(i));
		RefObject<Integer> refJ = new RefObject(Integer.valueOf(j));
		RefObject<Integer> refK = new RefObject(Integer.valueOf(k));
		RefObject<Integer> refL = new RefObject(Integer.valueOf(l));
		RefObject<Integer> refM = new RefObject(Integer.valueOf(m));
		RefObject<Integer> refN = new RefObject(Integer.valueOf(n));
		RefObject<Integer> refT = new RefObject(Integer.valueOf(t));
		CMathLibMe.M130(this.m_dataSource.GetXValue(this.m_index), refI, refJ, refK, refL, refM, refN, refT);
		i = ((Integer) refI.argvalue).intValue();
		j = ((Integer) refJ.argvalue).intValue();
		k = ((Integer) refK.argvalue).intValue();
		l = ((Integer) refL.argvalue).intValue();
		m = ((Integer) refM.argvalue).intValue();
		n = ((Integer) refN.argvalue).intValue();
		t = ((Integer) refT.argvalue).intValue();
		return l * 100 + m;
	}

	private double TIME2(CVariableMe var) {
		int i = 0;
		int j = 0;
		int k = 0;
		int l = 0;
		int m = 0;
		int n = 0;
		int t = 0;
		RefObject<Integer> refI = new RefObject(Integer.valueOf(i));
		RefObject<Integer> refJ = new RefObject(Integer.valueOf(j));
		RefObject<Integer> refK = new RefObject(Integer.valueOf(k));
		RefObject<Integer> refL = new RefObject(Integer.valueOf(l));
		RefObject<Integer> refM = new RefObject(Integer.valueOf(m));
		RefObject<Integer> refN = new RefObject(Integer.valueOf(n));
		RefObject<Integer> refT = new RefObject(Integer.valueOf(t));
		CMathLibMe.M130(this.m_dataSource.GetXValue(this.m_index), refI, refJ, refK, refL, refM, refN, refT);
		i = ((Integer) refI.argvalue).intValue();
		j = ((Integer) refJ.argvalue).intValue();
		k = ((Integer) refK.argvalue).intValue();
		l = ((Integer) refL.argvalue).intValue();
		m = ((Integer) refM.argvalue).intValue();
		n = ((Integer) refN.argvalue).intValue();
		t = ((Integer) refT.argvalue).intValue();
		return l * 10000 + m * 100 + n;
	}

	private double TMA(CVariableMe var) {
		double d = GetValue(var.m_parameters[0]);
		int val1 = (int) GetValue(var.m_parameters[1]);
		int val2 = (int) GetValue(var.m_parameters[2]);
		double ddd = 0.0D;
		if (this.m_index > 0) {
			ddd = this.m_dataSource.Get3(this.m_index - 1, var.m_fieldIndex);
		}
		double res = val1 * ddd + val2 * d;
		this.m_dataSource.Set3(this.m_index, var.m_fieldIndex, res);
		return res;
	}

	private int UPNDAY(CVariableMe var) {
		int val0 = (int) GetValue(var.m_parameters[0]);
		if (val0 < 0) {
			val0 = this.m_dataSource.GetRowsCount();
		} else if (val0 > this.m_index + 1) {
			val0 = this.m_index + 1;
		}
		int param2 = this.m_index;
		int res = 1;
		for (int i = 0; i < val0; i++) {
			double tempVar = GetValue(var.m_parameters[0]);
			this.m_index -= 1;
			double d = this.m_index >= 0 ? GetValue(var.m_parameters[0]) : 0.0D;
			if (tempVar <= d) {
				res = 0;
				break;
			}
		}
		this.m_index = param2;
		return res;
	}

	private double VALUEWHEN(CVariableMe var) {
		int count = this.m_dataSource.GetRowsCount();
		int param2 = this.m_index;
		double res = 0.0D;
		for (int i = 0; i < count; i++) {
			double value = GetValue(var.m_parameters[0]);
			if (value == 1.0D) {
				res = GetValue(var.m_parameters[1]);
				break;
			}
			this.m_index -= 1;
		}
		this.m_index = param2;
		return res;
	}

	private double VAR(CVariableMe var) {
		double res = 0.0D;
		int len = var.m_parameters.length;
		for (int i = 0; i < len; i++) {
			if (i % 2 == 0) {
				CVariableMe param = var.m_parameters[i];
				CVariableMe param1 = var.m_parameters[(i + 1)];
				int field = param.m_field;
				CVarMe tempVar = this.m_varFactory.CreateVar();
				res = tempVar.OnCreate(this, param, param1);
				if (tempVar.m_type == 1) {
					param.m_functionID = -1;
				}
				if (this.m_tempVars.containsKey(Integer.valueOf(field))) {
					CVarMe temp = (CVarMe) this.m_tempVars.get(Integer.valueOf(field));
					tempVar.m_parent = temp;
				}
				this.m_tempVars.put(Integer.valueOf(field), tempVar);
			}
		}
		return res;
	}

	private int WHILE(CVariableMe var) {
		int len = var.m_parameters.length;
		if (len > 1) {
			while (GetValue(var.m_parameters[0]) > 0.0D) {
				for (int i = 1; (this.m_break == 0) && (i < len); i++) {
					GetValue(var.m_parameters[i]);
				}
				if (this.m_break > 0) {
					if (this.m_break == 3) {
						this.m_break = 0;
						DeleteTempVars(var);

					} else {
						this.m_break = 0;
						DeleteTempVars(var);
						break;
					}

				} else {
					DeleteTempVars(var);
				}
			}
		}
		return 0;
	}

	private double WMA(CVariableMe var) {
		double value = GetValue(var.m_parameters[0]);
		int value1 = (int) GetValue(var.m_parameters[1]);
		int value2 = (int) GetValue(var.m_parameters[2]);
		double flag = 0.0D;
		if (this.m_index > 0) {
			flag = this.m_dataSource.Get3(this.m_index - 1, var.m_fieldIndex);
		}
		double res = CMathLibMe.M005(value1, value2, value, flag);
		this.m_dataSource.Set3(this.m_index, var.m_fieldIndex, res);
		return res;
	}

	private int YEAR(CVariableMe var) {
		return CStrMe.ConvertNumToDate(this.m_dataSource.GetXValue(this.m_index)).get(1);
	}

	private double ZIG(CVariableMe var) {
		double dValue1 = 0.0D;
		double dValue2 = 0.0D;
		int iValue1 = 0;
		int iValue2 = 0;
		int iValue3 = 0;
		double getValue = GetValue(var.m_parameters[1]);
		CVariableMe param0 = var.m_parameters[0];
		double val0 = GetValue(param0);
		int index0 = param0.m_fieldIndex;
		if (var.m_tempFields == null) {
			if (index0 == -1) {
				var.CreateTempFields(6);
			} else {
				var.CreateTempFields(5);
			}
		}
		if (index0 == -1) {
			index0 = var.m_tempFieldsIndex[5];
			this.m_dataSource.Set3(this.m_index, index0, val0);
		}
		if (this.m_index > 0) {
			iValue1 = (int) this.m_dataSource.Get3(this.m_index - 1, var.m_tempFieldsIndex[0]);
			dValue2 = this.m_dataSource.Get3(this.m_index - 1, var.m_tempFieldsIndex[1]);
			dValue1 = this.m_dataSource.Get3(this.m_index - 1, var.m_tempFieldsIndex[2]);
			iValue2 = (int) this.m_dataSource.Get3(this.m_index - 1, var.m_tempFieldsIndex[3]);
			iValue3 = (int) this.m_dataSource.Get3(this.m_index - 1, var.m_tempFieldsIndex[4]);
		}
		int s = -1;
		int t = -1;
		double d1 = 0.0D;
		double d2 = 0.0D;
		RefObject<Double> refdValue1 = new RefObject(Double.valueOf(dValue1));
		RefObject<Integer> refiValue2 = new RefObject(Integer.valueOf(iValue2));
		RefObject<Double> refdValue2 = new RefObject(Double.valueOf(dValue2));
		RefObject<Integer> refiValue3 = new RefObject(Integer.valueOf(iValue3));
		RefObject<Integer> refiValue1 = new RefObject(Integer.valueOf(iValue1));
		RefObject<Integer> refs = new RefObject(Integer.valueOf(s));
		RefObject<Integer> reft = new RefObject(Integer.valueOf(t));
		RefObject<Double> refd1 = new RefObject(Double.valueOf(d1));
		RefObject<Double> refd2 = new RefObject(Double.valueOf(d2));
		CMathLibMe.M013(this.m_index, val0, getValue, refdValue1, refiValue2, refdValue2, refiValue3, refiValue1, refs,
				reft, refd1, refd2);
		dValue1 = ((Double) refdValue1.argvalue).doubleValue();
		iValue2 = ((Integer) refiValue2.argvalue).intValue();
		dValue2 = ((Double) refdValue2.argvalue).doubleValue();
		iValue3 = ((Integer) refiValue3.argvalue).intValue();
		iValue1 = ((Integer) refiValue1.argvalue).intValue();
		s = ((Integer) refs.argvalue).intValue();
		t = ((Integer) reft.argvalue).intValue();
		d1 = ((Double) refd1.argvalue).doubleValue();
		d2 = ((Double) refd2.argvalue).doubleValue();
		this.m_dataSource.Set3(this.m_index, var.m_tempFieldsIndex[0], iValue1);
		this.m_dataSource.Set3(this.m_index, var.m_tempFieldsIndex[1], dValue2);
		this.m_dataSource.Set3(this.m_index, var.m_tempFieldsIndex[2], dValue1);
		this.m_dataSource.Set3(this.m_index, var.m_tempFieldsIndex[3], iValue2);
		this.m_dataSource.Set3(this.m_index, var.m_tempFieldsIndex[4], iValue3);
		if ((s != -1) && (t != -1)) {
			return 1.0D;
		}

		return 0.0D;
	}

	private int STR_CONTACT(CVariableMe var) {
		int len = var.m_parameters.length;
		String str = "'";
		for (int i = 0; i < len; i++) {
			str = str + GetText(var.m_parameters[i]);
		}
		str = str + "'";
		this.m_resultVar = new CVarMe();
		this.m_resultVar.m_type = 1;
		this.m_resultVar.m_str = str;
		return 0;
	}

	private int STR_FIND(CVariableMe var) {
		return GetText(var.m_parameters[0]).indexOf(GetText(var.m_parameters[1]));
	}

	private int STR_EQUALS(CVariableMe var) {
		int res = 0;
		if (GetText(var.m_parameters[0]).equals(GetText(var.m_parameters[1]))) {
			res = 1;
		}
		return res;
	}

	private int STR_FINDLAST(CVariableMe var) {
		return GetText(var.m_parameters[0]).lastIndexOf(GetText(var.m_parameters[1]));
	}

	private int STR_LENGTH(CVariableMe var) {
		return GetText(var.m_parameters[0]).length();
	}

	private int STR_SUBSTR(CVariableMe var) {
		int pLen = var.m_parameters.length;
		if (pLen == 2) {
			this.m_resultVar = new CVarMe();
			this.m_resultVar.m_type = 1;
			this.m_resultVar.m_str = ("'" + GetText(var.m_parameters[0]).substring((int) GetValue(var.m_parameters[1]))
					+ "'");
		} else if (pLen >= 3) {
			this.m_resultVar = new CVarMe();
			this.m_resultVar.m_type = 1;
			this.m_resultVar.m_str = ("'" + GetText(var.m_parameters[0]).substring((int) GetValue(var.m_parameters[1]),
					(int) GetValue(var.m_parameters[1]) + (int) GetValue(var.m_parameters[2])) + "'");
		}
		return 0;
	}

	private int STR_REPLACE(CVariableMe var) {
		this.m_resultVar = new CVarMe();
		this.m_resultVar.m_type = 1;
		this.m_resultVar.m_str = ("'"
				+ GetText(var.m_parameters[0]).replace(GetText(var.m_parameters[1]), GetText(var.m_parameters[2]))
				+ "'");
		return 0;
	}

	private int STR_SPLIT(CVariableMe var) {
		CVariableMe param0 = var.m_parameters[0];
		int field = param0.m_field;
		if (this.m_tempVars.containsKey(Integer.valueOf(field))) {
			ArrayList<String> arrayList = ((CVarMe) this.m_tempVars.get(Integer.valueOf(field))).m_list;
			arrayList.clear();
			String[] sss = GetText(var.m_parameters[1]).split("[" + GetText(var.m_parameters[2]) + "]");
			int len = sss.length;
			for (int i = 0; i < len; i++) {
				if (sss[i].length() > 0) {
					arrayList.add(sss[i]);
				}
			}
			return 1;
		}
		return 0;
	}

	private int STR_TOLOWER(CVariableMe var) {
		this.m_resultVar = new CVarMe();
		this.m_resultVar.m_type = 1;
		this.m_resultVar.m_str = GetText(var.m_parameters[0]).toLowerCase();
		return 0;
	}

	private int STR_TOUPPER(CVariableMe var) {
		this.m_resultVar = new CVarMe();
		this.m_resultVar.m_type = 1;
		this.m_resultVar.m_str = GetText(var.m_parameters[0]).toUpperCase();
		return 0;
	}

	private int LIST_ADD(CVariableMe var) {
		CVariableMe param0 = var.m_parameters[0];
		int listName = param0.m_field;
		if (this.m_tempVars.containsKey(Integer.valueOf(listName))) {
			ArrayList<String> arrayList = ((CVarMe) this.m_tempVars.get(Integer.valueOf(listName))).m_list;
			int len = var.m_parameters.length;
			for (int i = 1; i < len; i++) {
				arrayList.add(GetText(var.m_parameters[i]));
			}
			return 1;
		}
		return 0;
	}

	private int LIST_CLEAR(CVariableMe var) {
		CVariableMe param0 = var.m_parameters[0];
		int field = param0.m_field;
		if (this.m_tempVars.containsKey(Integer.valueOf(field))) {
			((CVarMe) this.m_tempVars.get(Integer.valueOf(field))).m_list.clear();
			return 1;
		}
		return 0;
	}

	private int LIST_GET(CVariableMe var) {
		CVariableMe param1 = var.m_parameters[1];
		int field = param1.m_field;
		if (this.m_tempVars.containsKey(Integer.valueOf(field))) {
			ArrayList<String> arrayList = ((CVarMe) this.m_tempVars.get(Integer.valueOf(field))).m_list;
			int value2 = (int) GetValue(var.m_parameters[2]);
			if (value2 < arrayList.size()) {
				String str = (String) arrayList.get(value2);
				CVariableMe param0 = var.m_parameters[0];
				int field0 = param0.m_field;
				int type = param0.m_type;
				switch (type) {
				case 2:
					double d = CStrMe.ConvertStrToDouble(str);
					this.m_dataSource.Set3(this.m_index, param0.m_fieldIndex, d);
					break;
				default:
					if (this.m_tempVars.containsKey(Integer.valueOf(field0))) {
						CVarMe cVar = (CVarMe) this.m_tempVars.get(Integer.valueOf(field0));
						CVariableMe newVar = new CVariableMe(this);
						newVar.m_type = 1;
						newVar.m_expression = ("'" + str + "'");
						cVar.SetValue(this, param0, newVar);
					}
					break;
				}
			}
			return 1;
		}
		return 0;
	}

	private int LIST_INSERT(CVariableMe var) {
		CVariableMe param0 = var.m_parameters[0];
		int field = param0.m_field;
		if (this.m_tempVars.containsKey(Integer.valueOf(field))) {
			((CVarMe) this.m_tempVars.get(Integer.valueOf(field))).m_list.add((int) GetValue(var.m_parameters[1]),
					GetText(var.m_parameters[2]));
			return 1;
		}
		return 0;
	}

	private int LIST_REMOVE(CVariableMe var) {
		CVariableMe param0 = var.m_parameters[0];
		int field = param0.m_field;
		if (this.m_tempVars.containsKey(Integer.valueOf(field))) {
			((CVarMe) this.m_tempVars.get(Integer.valueOf(field))).m_list.remove((int) GetValue(var.m_parameters[1]));
			return 1;
		}
		return 0;
	}

	private int LIST_SIZE(CVariableMe var) {
		int res = 0;
		CVariableMe param0 = var.m_parameters[0];
		int field = param0.m_field;
		if (this.m_tempVars.containsKey(Integer.valueOf(field))) {
			res = ((CVarMe) this.m_tempVars.get(Integer.valueOf(field))).m_list.size();
		}
		return res;
	}

	private int MAP_CLEAR(CVariableMe var) {
		CVariableMe param1 = var.m_parameters[0];
		int field = param1.m_field;
		if (this.m_tempVars.containsKey(Integer.valueOf(field))) {
			((CVarMe) this.m_tempVars.get(Integer.valueOf(field))).m_map.clear();
			return 1;
		}
		return 0;
	}

	private int MAP_CONTAINSKEY(CVariableMe var) {
		int res = 0;
		CVariableMe param1 = var.m_parameters[0];
		int field = param1.m_field;
		if (this.m_tempVars.containsKey(Integer.valueOf(field))) {
			if (((CVarMe) this.m_tempVars.get(Integer.valueOf(field))).m_map
					.containsKey(GetText(var.m_parameters[1]))) {
				res = 1;
			}
		}
		return res;
	}

	private int MAP_GET(CVariableMe in_val) {
		CVariableMe var = in_val.m_parameters[1];
		int field = var.m_field;
		if (this.m_tempVars.containsKey(Integer.valueOf(field))) {
			HashMap<String, String> haspMap = ((CVarMe) this.m_tempVars.get(Integer.valueOf(field))).m_map;
			String text = GetText(in_val.m_parameters[2]);
			if (haspMap.containsKey(text)) {
				String s = (String) haspMap.get(text);
				CVariableMe variable = in_val.m_parameters[0];
				int iField = variable.m_field;
				int type = variable.m_type;
				switch (type) {
				case 2:
					double d = CStrMe.ConvertStrToDouble(s);
					this.m_dataSource.Set3(this.m_index, variable.m_fieldIndex, d);
					break;
				default:
					if (this.m_tempVars.containsKey(Integer.valueOf(iField))) {
						CVarMe tempVar = (CVarMe) this.m_tempVars.get(Integer.valueOf(iField));
						CVariableMe newVar = new CVariableMe(this);
						newVar.m_type = 1;
						newVar.m_expression = ("'" + s + "'");
						tempVar.SetValue(this, variable, newVar);
					}
					break;
				}
			}
			return 1;
		}
		return 0;
	}

	private int MAP_GETKEYS(CVariableMe in_val) {
		CVariableMe var = in_val.m_parameters[1];
		int field = var.m_field;
		if (this.m_tempVars.containsKey(Integer.valueOf(field))) {
			int temp = in_val.m_parameters[0].m_field;
			if (this.m_tempVars.containsKey(Integer.valueOf(temp))) {
				HashMap<String, String> hMap = ((CVarMe) this.m_tempVars.get(Integer.valueOf(field))).m_map;
				ArrayList<String> arrayList = ((CVarMe) this.m_tempVars.get(Integer.valueOf(temp))).m_list;
				arrayList.clear();
				for (Entry<String, String> item : hMap.entrySet()) {
					arrayList.add(item.getKey());
				}
				return 1;
			}
		}
		return 0;
	}

	private int MAP_REMOVE(CVariableMe in_val) {
		CVariableMe var = in_val.m_parameters[0];
		int field = var.m_field;
		if (this.m_tempVars.containsKey(Integer.valueOf(field))) {
			((CVarMe) this.m_tempVars.get(Integer.valueOf(field))).m_map.remove(GetText(in_val.m_parameters[1]));
			return 1;
		}
		return 0;
	}

	private int MAP_SET(CVariableMe in_val) {
		CVariableMe var = in_val.m_parameters[0];
		int field = var.m_field;
		if (this.m_tempVars.containsKey(Integer.valueOf(field))) {
			((CVarMe) this.m_tempVars.get(Integer.valueOf(field))).m_map.put(GetText(in_val.m_parameters[1]),
					GetText(var.m_parameters[2]));
		}
		return 0;
	}

	private int MAP_SIZE(CVariableMe in_val) {
		int res = 0;
		CVariableMe var = in_val.m_parameters[0];
		int field = var.m_field;
		if (this.m_tempVars.containsKey(Integer.valueOf(field))) {
			res = ((CVarMe) this.m_tempVars.get(Integer.valueOf(field))).m_map.size();
		}
		return res;
	}
}
