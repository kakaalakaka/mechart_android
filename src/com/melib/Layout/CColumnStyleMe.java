package com.melib.Layout;

import java.util.ArrayList;
import com.melib.Base.RefObject;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class CColumnStyleMe implements com.melib.Base.CPropertyMe {
	public CColumnStyleMe(SizeTypeA sizeType, float width) {
		this.m_sizeType = sizeType;
		this.m_width = width;
	}

	protected SizeTypeA m_sizeType = SizeTypeA.AbsoluteSize;
	protected float m_width;

	public SizeTypeA GetSizeType() {
		return this.m_sizeType;
	}

	public void SetSizeType(SizeTypeA value) {
		this.m_sizeType = value;
	}

	public float GetWidth() {
		return this.m_width;
	}

	public void SetWidth(float value) {
		this.m_width = value;
	}

	public void GetProperty(String name, RefObject<String> value, RefObject<String> type) {
		if (name.equals("sizetype")) {
			type.argvalue = "enum:SizeType";
			if (this.m_sizeType == SizeTypeA.AbsoluteSize) {
				value.argvalue = "absolutesize";
			} else if (this.m_sizeType == SizeTypeA.AutoFill) {
				value.argvalue = "autofill";
			} else if (this.m_sizeType == SizeTypeA.PercentSize) {
				value.argvalue = "percentsize";
			}
		} else if (name.equals("width")) {
			type.argvalue = "float";
			value.argvalue = com.melib.Base.CStrMe.ConvertFloatToStr(GetWidth());
		}
	}

	public ArrayList<String> GetPropertyNames() {
		ArrayList<String> propertyNames = new ArrayList();
		propertyNames.add("SizeType");
		propertyNames.add("Width");
		return propertyNames;
	}

	public void SetProperty(String name, String value) {
		if (name.equals("sizetype")) {
			String lowerStr = value.toLowerCase();
			if (value.equals("absolutesize")) {
				this.m_sizeType = SizeTypeA.AbsoluteSize;
			} else if (value.equals("autofill")) {
				this.m_sizeType = SizeTypeA.AutoFill;
			} else if (value.equals("percentsize")) {
				this.m_sizeType = SizeTypeA.PercentSize;
			}
		} else if (name.equals("width")) {
			SetWidth(com.melib.Base.CStrMe.ConvertStrToFloat(value));
		}
	}
}
