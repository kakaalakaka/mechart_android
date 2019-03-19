package com.melib.Base;

import java.util.ArrayList;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public abstract interface CPropertyMe {
	public abstract void GetProperty(String paramString, RefObject<String> paramRefObject1,
			RefObject<String> paramRefObject2);

	public abstract ArrayList<String> GetPropertyNames();

	public abstract void SetProperty(String paramString1, String paramString2);
}
