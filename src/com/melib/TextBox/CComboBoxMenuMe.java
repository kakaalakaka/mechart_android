package com.melib.TextBox;

import com.melib.Menu.CMenuMe;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class CComboBoxMenuMe extends CMenuMe {
	protected CComboBoxMe m_comboBox = null;

	public CComboBoxMe GetComboBox() {
		return this.m_comboBox;
	}

	public void SetComboBox(CComboBoxMe value) {
		this.m_comboBox = value;
	}

	public boolean OnAutoHide() {
		if ((this.m_comboBox != null) && (this.m_comboBox.IsFocused())) {
			return false;
		}
		return true;
	}
}
