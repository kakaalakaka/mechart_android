package com.melib.Sock;

import java.net.Socket;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class PUSHDATA {
	public byte[] m_buffer = null;
	public int m_bufferRemain;
	public boolean m_get;
	public int m_head;
	public int m_headSize = 4;
	public byte[] m_headStr = new byte[4];
	public int m_index;
	public int m_len;
	public int m_pos;
	public int m_socketID;
	public Socket m_socket;
	public byte[] m_str = null;
	public int m_strRemain;
	public boolean m_submit;

	public PUSHDATA() {
	}
}
