package com.melib.Sock;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import com.melib.Sock.Clients;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class Client implements Runnable {
	private boolean m_blnProxyServerOK = false;
	private boolean m_running = false;
	public int m_socketID;
	private DataInputStream m_dataInputStream = null;
	private String m_ip;
	private boolean m_isDisposed;
	private int m_port;
	private String m_proxyDomain;
	private long m_proxyType;
	private String m_proxyIP;
	private int m_proxyPort;
	private String m_proxyUserName;
	private String m_proxyUserPwd;
	private ArrayList<byte[]> m_msgList = new ArrayList();
	private Socket m_socket = null;
	int m_timeout;
	int m_type;
	private DatagramSocket m_datagramSocket = null;

	public Client(int type, long proxyType, String ip, int port, String proxyIp, int proxyPort, String proxyUserName,
			String proxyUserPwd, String proxyDomain, int timeout) {
		this.m_blnProxyServerOK = true;
		this.m_proxyDomain = proxyDomain;
		this.m_proxyType = proxyType;
		this.m_ip = ip;
		this.m_port = port;
		this.m_proxyIP = proxyIp;
		this.m_proxyPort = proxyPort;
		this.m_proxyUserName = proxyUserName;
		this.m_proxyUserPwd = proxyUserPwd;
		this.m_timeout = timeout;
		this.m_type = type;
	}

	protected void finalize() throws Throwable {
		this.Dispose();
	}

	public int Close() {
		byte ret = -1;
		byte[] bytes;
		if (this.m_socket != null) {
			try {
				this.m_socket.close();
				ret = 1;
			} catch (Exception var5) {
				bytes = new byte[] { 2 };
				Clients.RecvClientMsg(this.m_socketID, this.m_socketID, bytes, 1);
				Clients.WriteClientLog(this.m_socketID, this.m_socketID, 2, "socket exit");
				ret = -1;
			}
		}

		if (this.m_datagramSocket != null) {
			try {
				this.m_datagramSocket.close();
				ret = 1;
			} catch (Exception var4) {
				bytes = new byte[] { 2 };
				Clients.RecvClientMsg(this.m_socketID, this.m_socketID, bytes, 1);
				Clients.WriteClientLog(this.m_socketID, this.m_socketID, 2, "udp exit");
				ret = -1;
			}
		}

		this.m_running = false;
		return ret;
	}

	public Client.ConnectStatus Connect() {
		return this.ConnectBySocket();
	}

	private Client.ConnectStatus ConnectBySocket() {
		Client.ConnectStatus status = Client.ConnectStatus.CONNECT_SERVER_FAIL;
		if (this.m_type == 0) {
			if (this.m_socket == null) {
				try {
					this.m_socket = new Socket();
					InetSocketAddress socketAddress = new InetSocketAddress(this.m_ip, this.m_port);
					this.m_socket.connect(socketAddress, this.m_timeout);
					this.m_dataInputStream = new DataInputStream(this.m_socket.getInputStream());
					this.m_running = true;
					status = Client.ConnectStatus.SUCCESS;
					byte[] bytes = new byte[1024];
					bytes[0] = 109;
					bytes[1] = 105;
					bytes[2] = 97;
					bytes[3] = 111;
					this.Send(bytes, 1024);
					Thread thead = new Thread(this);
					thead.start();
				} catch (Exception var6) {
					status = Client.ConnectStatus.CONNECT_SERVER_FAIL;
				}
			}
		} else if (this.m_type == 1 && this.m_datagramSocket != null) {
			try {
				InetAddress address = InetAddress.getByName(this.m_ip);
				this.m_datagramSocket = new DatagramSocket(this.m_port, address);
			} catch (Exception var5) {
				;
			}
		}

		(new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						if (Client.this.m_running) {
							byte[] bytes = null;
							synchronized (Client.this.m_msgList) {
								int msgSize = Client.this.m_msgList.size();
								if (msgSize > 0) {
									bytes = (byte[]) Client.this.m_msgList.get(0);
									Client.this.m_msgList.remove(0);
								}
							}

							if (bytes != null) {
								DataOutputStream dataOutputStream = new DataOutputStream(
										Client.this.m_socket.getOutputStream());
								dataOutputStream.write(bytes);
								dataOutputStream.flush();
								continue;
							}

							Thread.sleep(1L);
							continue;
						}
					} catch (Exception var6) {
						;
					}

					return;
				}
			}
		})).start();
		return status;
	}

	private Client.ConnectStatus ConnectByHttp() {
		return Client.ConnectStatus.SUCCESS;
	}

	private Client.ConnectStatus ConnectBySock4() {
		return Client.ConnectStatus.SUCCESS;
	}

	private Client.ConnectStatus ConnectBySock5() {
		return Client.ConnectStatus.SUCCESS;
	}

	private Client.ConnectStatus ConnectProxyServer() {
		return Client.ConnectStatus.SUCCESS;
	}

	public void Dispose() {
		if (!this.m_isDisposed) {
			this.Close();
			this.m_running = false;
			this.m_isDisposed = true;
		}

	}

	public void run() {
		byte[] bytes = null;
		boolean get = false;
		int head = 0;
		int pos = 0;
		int len = 0;
		byte intSize = 4;
		byte[] headStr = new byte[4];
		int headSize = 4;

		byte[] buf;
		label94: while (this.m_running) {
			try {
				buf = new byte[10240];
				if (this.m_type == 0) {
					len = this.m_dataInputStream.read(buf);
				} else if (this.m_type == 1) {
					DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);
					this.m_datagramSocket.receive(datagramPacket);
				}

				if (len != 0 && len != -1) {
					int index = 0;

					while (true) {
						if (index >= len) {
							continue label94;
						}
						int remain;
						if (!get) {
							int var23 = intSize - headSize;
							if (var23 == 0) {
								head = 255 & buf[index] | '\uff00' & buf[index + 1] << 8
										| 16711680 & buf[index + 2] << 16 | -16777216 & buf[index + 3] << 24;
							} else {
								for (remain = 0; remain < var23; ++remain) {
									headStr[headSize + remain] = buf[remain];
								}

								head = 255 & headStr[0] | '\uff00' & headStr[1] << 8 | 16711680 & headStr[2] << 16
										| -16777216 & headStr[3] << 24;
							}

							if (bytes != null) {
								Object var17 = null;
							}

							bytes = new byte[head];
							if (var23 > 0) {
								for (remain = 0; remain < headSize; ++remain) {
									bytes[remain] = headStr[remain];
								}

								pos += headSize;
								headSize = intSize;
							}
						}

						int var19 = len - index;
						int var18 = head - pos;
						get = var18 > var19;
						remain = Math.min(var18, var19);
						System.arraycopy(buf, index, bytes, pos, remain);
						pos += remain;
						index += remain;
						if (!get) {
							Clients.RecvClientMsg(this.m_socketID, this.m_socketID, bytes, head);
							head = 0;
							pos = 0;
							if (len - index != 0 && len - index < intSize) {
								headSize = var19 - var18;
								int j = 0;

								while (true) {
									if (j >= headSize) {
										continue label94;
									}

									headStr[j] = buf[index + j];
									++j;
								}
							}

							headSize = intSize;
						}
					}
				}

				byte[] var21 = new byte[] { 3 };
				Clients.RecvClientMsg(this.m_socketID, this.m_socketID, var21, 1);
				Clients.WriteClientLog(this.m_socketID, this.m_socketID, 3, "socket error");
			} catch (Exception var16) {
				;
			}
			break;
		}

		buf = new byte[] { 2 };
		Clients.RecvClientMsg(this.m_socketID, this.m_socketID, buf, 1);
		Clients.WriteClientLog(this.m_socketID, this.m_socketID, 2, "socket exit");
		this.m_running = false;
	}

	public int Send(byte[] str, int len) {
		if (this.m_socket != null && this.m_running) {
			boolean ret = true;

			int ret1;
			try {
				ArrayList ex = this.m_msgList;
				synchronized (this.m_msgList) {
					this.m_msgList.add(str);
				}

				ret1 = len;
			} catch (Exception var7) {
				ret1 = -1;
			}

			return ret1;
		} else {
			return -1;
		}
	}

	public int SendTo(byte[] str, int len) {
		if (this.m_socket != null && this.m_running) {
			boolean ret = true;

			int ret1;
			try {
				ArrayList ex = this.m_msgList;
				synchronized (this.m_msgList) {
					this.m_msgList.add(str);
				}

				(new Thread(new Runnable() {
					public void run() {
						try {
							byte[] bytes = null;
							synchronized (Client.this.m_msgList) {
								int size = Client.this.m_msgList.size();
								if (size > 0) {
									bytes = (byte[]) Client.this.m_msgList.get(0);
									Client.this.m_msgList.remove(0);
								}
							}

							if (bytes != null) {
								DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
								Client.this.m_datagramSocket.send(packet);
							}
						} catch (Exception var6) {
							;
						}

					}
				})).start();
				ret1 = len;
			} catch (Exception var7) {
				ret1 = -1;
			}

			return ret1;
		} else {
			return -1;
		}
	}

	public static enum ConnectStatus {
		SUCCESS, CONNECT_PROXY_FAIL, NOT_CONNECT_PROXY, CONNECT_SERVER_FAIL;

		private ConnectStatus() {
		}

		public int GetValue() {
			return this.ordinal();
		}

		public static Client.ConnectStatus ForValue(int value) {
			return values()[value];
		}
	}
}
