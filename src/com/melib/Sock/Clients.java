
package com.melib.Sock;

import java.util.HashMap;
import com.melib.Sock.Client;
import com.melib.Sock.SocketListener;
import com.melib.Sock.Client.ConnectStatus;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class Clients {
	private static HashMap<Integer, Client> m_clients = new HashMap();
	private static SocketListener m_listener;
	private static int m_socketID;

	public Clients() {
	}

	public static int Close(int socketID) {
		int ret = -1;
		if (m_clients.containsKey(Integer.valueOf(socketID))) {
			Client client = (Client) m_clients.get(Integer.valueOf(socketID));
			ret = client.Close();
			m_clients.remove(Integer.valueOf(socketID));
		}

		return ret;
	}

	public static int Connect(int type, int proxyType, String ip, int port, String proxyIp, int proxyPort,
			String proxyUserName, String proxyUserPwd, String proxyDomain, int timeout) {
		Client client = new Client(type, (long) proxyType, ip, port, proxyIp, proxyPort, proxyUserName, proxyUserPwd,
				proxyDomain, timeout);
		ConnectStatus ret = client.Connect();
		if (ret != ConnectStatus.CONNECT_SERVER_FAIL) {
			++m_socketID;
			Integer socketID = Integer.valueOf(m_socketID);
			client.m_socketID = m_socketID;
			m_clients.put(socketID, client);
			return socketID.intValue();
		} else {
			client.Dispose();
			return -1;
		}
	}

	public static void RecvClientMsg(int socketID, int localSID, byte[] str, int len) {
		m_listener.CallBack(socketID, localSID, str, len);
	}

	public static int Send(int socketID, byte[] str, int len) {
		int ret = -1;
		if (m_clients.containsKey(Integer.valueOf(socketID))) {
			Client client = (Client) m_clients.get(Integer.valueOf(socketID));
			ret = client.SendTo(str, len);
		}

		return ret;
	}

	public static int SendTo(int socketID, byte[] str, int len) {
		int ret = -1;
		if (m_clients.containsKey(Integer.valueOf(socketID))) {
			Client client = (Client) m_clients.get(Integer.valueOf(socketID));
			ret = client.SendTo(str, len);
		}

		return ret;
	}

	public static int SetListener(SocketListener listener) {
		m_listener = listener;
		return 1;
	}

	public static void WriteClientLog(int socketID, int localSID, int state, String log) {
		m_listener.WriteLog(socketID, localSID, state, log);
	}
}
