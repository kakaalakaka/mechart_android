package com.melib.Layout;

import java.util.ArrayList;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class CRowStyleMe implements com.melib.Base.CPropertyMe {
	protected float m_height;

	public CRowStyleMe(SizeTypeA sizeType, float height) {
		this.m_sizeType = sizeType;
		this.m_height = height;
	}

	public float GetHeight() {
		return this.m_height;
	}

	public void SetHeight(float value) {
		this.m_height = value;
	}

	protected SizeTypeA m_sizeType = SizeTypeA.AbsoluteSize;

	public SizeTypeA GetSizeType() {
		return this.m_sizeType;
	}

	public void SetSizeType(SizeTypeA value) {
		this.m_sizeType = value;
	}

	public void GetProperty(String name, com.melib.Base.RefObject<String> value,
			com.melib.Base.RefObject<String> type) {
		if (name.equals("sizetype")) {
			type.argvalue = "enum:SizeType";
			if (this.m_sizeType == SizeTypeA.AbsoluteSize) {
				value.argvalue = "absolutesize";
			} else if (this.m_sizeType == SizeTypeA.AutoFill) {
				value.argvalue = "autofill";
			} else if (this.m_sizeType == SizeTypeA.PercentSize) {
				value.argvalue = "percentsize";
			}
		} else if (name.equals("height")) {
			type.argvalue = "float";
			value.argvalue = com.melib.Base.CStrMe.ConvertFloatToStr(GetHeight());
		}
	}

	public ArrayList<String> GetPropertyNames() {
		ArrayList<String> propertyNames = new ArrayList();
		propertyNames.add("SizeType");
		propertyNames.add("Height");
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
		} else if (name.equals("height")) {
			SetHeight(com.melib.Base.CStrMe.ConvertStrToFloat(value));
		}
	}
}
