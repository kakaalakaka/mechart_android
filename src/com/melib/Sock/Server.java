package com.melib.Sock;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import com.melib.Sock.PUSHDATA;
import com.melib.Sock.Servers;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class Server {
	private HashMap<Integer, PUSHDATA> m_datas = new HashMap();
	public int m_socketID;
	private boolean m_isRunning;
	private Selector m_selector = null;
	private Lock m_lock;
	private ServerSocket m_hSocket = null;
	private ServerSocketChannel m_serverSocketChannel = null;
	private DatagramSocket m_datagramSocket = null;

	public Server() {
	}

	public int Close() {
		if (this.m_hSocket != null) {
			try {
				this.m_hSocket.close();
				return 1;
			} catch (Exception var3) {
				;
			}
		}

		if (this.m_datagramSocket != null) {
			try {
				this.m_datagramSocket.close();
				return 1;
			} catch (Exception var2) {
				;
			}
		}

		return -1;
	}

	public int CloseClient(int socketID) {
		if (this.m_datas.containsKey(Integer.valueOf(socketID))) {
			try {
				((PUSHDATA) this.m_datas.get(Integer.valueOf(socketID))).m_socket.close();
				return 1;
			} catch (Exception var3) {
				;
			}
		}

		return -1;
	}

	public int Recv(PUSHDATA data) {
		if (!data.m_submit) {
			if (data.m_len == 1024 && data.m_buffer[0] == 109 && data.m_buffer[1] == 105 && data.m_buffer[2] == 97
					&& data.m_buffer[3] == 111) {
				data.m_submit = true;
				return 1;
			} else {
				return -1;
			}
		} else {
			byte intSize = 4;
			data.m_index = 0;

			while (data.m_index < data.m_len) {
				int i;
				if (!data.m_get) {
					int diffSize = intSize - data.m_headSize;
					if (diffSize == 0) {
						data.m_head = 255 & data.m_buffer[data.m_index]
								| '\uff00' & data.m_buffer[data.m_index + 1] << 8
								| 16711680 & data.m_buffer[data.m_index + 2] << 16
								| -16777216 & data.m_buffer[data.m_index + 3] << 24;
					} else {
						for (i = 0; i < diffSize; ++i) {
							data.m_headStr[data.m_headSize + i] = data.m_buffer[i];
						}

						data.m_head = 255 & data.m_headStr[0] | '\uff00' & data.m_headStr[1] << 8
								| 16711680 & data.m_headStr[2] << 16 | -16777216 & data.m_headStr[3] << 24;
					}

					if (data.m_str != null) {
						data.m_str = null;
					}

					data.m_str = new byte[data.m_head];
					if (diffSize > 0) {
						for (i = 0; i < data.m_headSize; ++i) {
							data.m_str[i] = data.m_headStr[i];
						}

						data.m_pos += data.m_headSize;
						data.m_headSize = intSize;
					}
				}

				data.m_bufferRemain = data.m_len - data.m_index;
				data.m_strRemain = data.m_head - data.m_pos;
				data.m_get = data.m_strRemain > data.m_bufferRemain;
				i = Math.min(data.m_strRemain, data.m_bufferRemain);
				System.arraycopy(data.m_buffer, data.m_index, data.m_str, data.m_pos, i);
				data.m_pos += i;
				data.m_index += i;
				if (!data.m_get) {
					Servers.RecvClientMsg(data.m_socketID, this.m_socketID, data.m_str, data.m_head);
					data.m_head = 0;
					data.m_pos = 0;
					if (data.m_len - data.m_index != 0 && data.m_len - data.m_index < intSize) {
						data.m_headSize = data.m_bufferRemain - data.m_strRemain;

						for (int j = 0; j < data.m_headSize; ++j) {
							data.m_headStr[j] = data.m_buffer[data.m_index + j];
						}

						return 1;
					}

					data.m_headSize = intSize;
				}
			}

			return 1;
		}
	}

	protected void acceptHandle(SelectionKey key) throws IOException {
		SocketChannel socketChannel = (SocketChannel) key.channel();
		Socket socket = socketChannel.socket();
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		int len = -1;

		try {
			len = socketChannel.read(buffer);
		} catch (Exception var10) {
			;
		}

		if (len > 0) {
			byte[] bytes = buffer.array();
			int socketID = socket.hashCode();
			PUSHDATA pushData = (PUSHDATA) this.m_datas.get(Integer.valueOf(socketID));
			if (pushData != null) {
				pushData.m_buffer = bytes;
				pushData.m_len = len;
				int KKKKKKKKKKK = this.Recv(pushData);
				if (KKKKKKKKKKK == -1) {
					socketChannel.close();
					Servers.WriteServerLog(socketID, this.m_socketID, 2, "socket exit");
					this.m_datas.put(Integer.valueOf(socketID), null);
				}
			}
		} else {
			int socketID = socket.hashCode();
			socketChannel.close();
			Servers.WriteServerLog(socketID, this.m_socketID, 2, "socket exit");
		}

		buffer.clear();
	}

	public int Send(int socketID, byte[] str, int len) {
		if (this.m_hSocket != null && this.m_isRunning) {
			int ret = -1;

			try {
				PUSHDATA ex = (PUSHDATA) this.m_datas.get(Integer.valueOf(socketID));
				if (ex != null) {
					ByteBuffer buffer = ByteBuffer.wrap(str);
					ex.m_socket.getChannel().write(buffer);
					buffer.clear();
					ret = len;
				}
			} catch (Exception var7) {
				ret = -1;
			}

			return ret;
		} else {
			return -1;
		}
	}

	public int SendTo(byte[] str, int len) {
		if (this.m_hSocket != null && this.m_isRunning) {
			int ret;
			try {
				DatagramPacket ex = new DatagramPacket(str, len);
				this.m_datagramSocket.send(ex);
				ret = len;
			} catch (Exception var5) {
				ret = -1;
			}
			return ret;
		} else {
			return -1;
		}
	}

	public int Start(int port) throws IOException {
		try {
			byte number = 2;
			this.m_serverSocketChannel = ServerSocketChannel.open();
			this.m_serverSocketChannel.configureBlocking(false);
			this.m_hSocket = this.m_serverSocketChannel.socket();
			this.m_selector = Selector.open();
			this.m_hSocket.bind(new InetSocketAddress(port));
			this.m_serverSocketChannel.register(this.m_selector, 16);
			this.m_lock = new ReentrantLock(true);
			this.m_isRunning = true;

			for (int i = 0; i < number; ++i) {
				Thread thread = new Thread() {
					public void run() {
						while (Server.this.m_isRunning) {
							try {
								HashSet hashSet = null;
								if (!Server.this.m_lock.tryLock()) {
									Thread.sleep(10L);
								} else if (Server.this.m_selector.select(1000L) <= 0) {
									Server.this.m_lock.unlock();
									Thread.sleep(10L);
								} else {
									Iterator sIter = Server.this.m_selector.selectedKeys().iterator();
									hashSet = new HashSet();

									SelectionKey selectionKey;
									while (sIter.hasNext()) {
										selectionKey = (SelectionKey) sIter.next();
										sIter.remove();
										hashSet.add(selectionKey);
									}

									Server.this.m_lock.unlock();
									sIter = hashSet.iterator();

									while (sIter.hasNext()) {
										selectionKey = (SelectionKey) sIter.next();
										sIter.remove();
										if (selectionKey.isValid()) {
											if (selectionKey.isAcceptable()) {
												SocketChannel socketChannel = Server.this.m_serverSocketChannel
														.accept();
												if (socketChannel != null) {
													socketChannel.configureBlocking(false);
													socketChannel.register(Server.this.m_selector, 1);
													PUSHDATA pushData = new PUSHDATA();
													pushData.m_socket = socketChannel.socket();
													pushData.m_socketID = pushData.m_socket.hashCode();
													Server.this.m_datas.put(Integer.valueOf(pushData.m_socketID),
															pushData);
													Servers.WriteServerLog(pushData.m_socketID, Server.this.m_socketID,
															1, "accept:" + socketChannel.getRemoteAddress());
												}
											} else if (selectionKey.isReadable()) {
												Server.this.acceptHandle(selectionKey);
											}
										}
									}
								}
							} catch (Exception var6) {
								;
							}
						}

					}
				};
				thread.start();
			}

			return 1;
		} catch (Exception var5) {
			return -1;
		}
	}

	public int StartCompletion(int port) throws IOException {
		try {
			this.m_datagramSocket = new DatagramSocket(port);
			PUSHDATA ex = new PUSHDATA();
			this.m_datas.put(Integer.valueOf(0), ex);
			Thread thead = new Thread() {
				public void run() {
					while (true) {
						try {
							byte[] bytes = new byte[1024];
							DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);
							Server.this.m_datagramSocket.receive(datagramPacket);
							int len = datagramPacket.getLength();
							if (len > 0) {
								PUSHDATA pushData = (PUSHDATA) Server.this.m_datas.get(Integer.valueOf(0));
								if (pushData != null) {
									pushData.m_buffer = bytes;
									pushData.m_len = len;
									int retLen = Server.this.Recv(pushData);
									if (retLen == -1) {
										;
									}
								}
							}
						} catch (Exception var6) {
							;
						}
					}
				}
			};
			thead.start();
			return 1;
		} catch (Exception var4) {
			return -1;
		}
	}
}
