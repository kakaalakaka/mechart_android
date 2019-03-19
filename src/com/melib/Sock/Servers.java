package com.melib.Sock;

import java.util.HashMap;
import com.melib.Sock.SocketListener;
import com.melib.Sock.Server;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class Servers {
	private static SocketListener m_listener;
	private static HashMap<Integer, Server> m_servers = new HashMap();
	private static int m_socketID;

	public Servers() {
	}

	public static int Close(int socketID) {
		int ret = -1;
		if (m_servers.containsKey(Integer.valueOf(socketID))) {
			Server server = (Server) m_servers.get(Integer.valueOf(socketID));
			ret = server.Close();
			m_servers.remove(Integer.valueOf(socketID));
		}

		return ret;
	}

	public static int CloseClient(int socketID) {
		int ret = -1;
		if (m_servers.containsKey(Integer.valueOf(socketID))) {
			Server server = (Server) m_servers.get(Integer.valueOf(socketID));
			ret = server.CloseClient(socketID);
			m_servers.remove(Integer.valueOf(socketID));
		}

		return ret;
	}

	public static void RecvClientMsg(int socketID, int localSID, byte[] str, int len) {
		m_listener.CallBack(socketID, localSID, str, len);
	}

	public static int Send(int socketID, int localSID, byte[] str, int len) {
		int ret = -1;
		if (m_servers.containsKey(Integer.valueOf(localSID))) {
			Server server = (Server) m_servers.get(Integer.valueOf(localSID));
			ret = server.Send(socketID, str, len);
		}

		return ret;
	}

	public static int SendTo(int localSID, byte[] str, int len) {
		int ret = -1;
		if (m_servers.containsKey(Integer.valueOf(localSID))) {
			Server server = (Server) m_servers.get(Integer.valueOf(localSID));
			ret = server.SendTo(str, len);
		}

		return ret;
	}

	public static int SetListener(SocketListener listener) {
		m_listener = listener;
		return 1;
	}

	public static int Start(int type, int port) {
		try {
			Server ex = new Server();
			if (type == 0) {
				ex.Start(port);
			} else if (type == 1) {
				ex.StartCompletion(port);
			}

			++m_socketID;
			Integer socketID = Integer.valueOf(m_socketID);
			ex.m_socketID = m_socketID;
			m_servers.put(socketID, ex);
			return m_socketID;
		} catch (Exception var4) {
			return -1;
		}
	}

	public static void WriteServerLog(int socketID, int localSID, int state, String log) {
		m_listener.WriteLog(socketID, localSID, state, log);
	}
}
