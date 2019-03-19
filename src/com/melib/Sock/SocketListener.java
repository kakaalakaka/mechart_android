package com.melib.Sock;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public abstract interface SocketListener {
	public abstract void CallBack(int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3);

	public abstract void WriteLog(int paramInt1, int paramInt2, int paramInt3, String paramString);
}
