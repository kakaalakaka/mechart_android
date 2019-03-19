package com.melib.Base;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class FONT {
	public boolean m_bold;

	public FONT() {
	}

	public FONT(String fontFamily, float fontSize, boolean bold, boolean underline, boolean italic) {
		this.m_fontFamily = fontFamily;
		this.m_fontSize = fontSize;
		this.m_bold = bold;
		this.m_underline = underline;
		this.m_italic = italic;
	}

	public FONT(String fontFamily, float fontSize, boolean bold, boolean underline, boolean italic, boolean strikeout) {
		this.m_fontFamily = fontFamily;
		this.m_fontSize = fontSize;
		this.m_bold = bold;
		this.m_underline = underline;
		this.m_italic = italic;
		this.m_strikeout = strikeout;
	}

	public String m_fontFamily = "Arial";

	public float m_fontSize = 12.0F;
	public boolean m_italic;
	public boolean m_strikeout;
	public boolean m_underline;
}
