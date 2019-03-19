package com.melib.TextBox;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class WordLine {
	public int m_end;
	public int m_start;

	public WordLine() {
	}

	public WordLine(int start, int end) {
		this.m_start = start;
		this.m_end = end;
	}

	public WordLine Clone() {
		WordLine varCopy = new WordLine();

		varCopy.m_end = this.m_end;
		varCopy.m_start = this.m_start;

		return varCopy;
	}
}
