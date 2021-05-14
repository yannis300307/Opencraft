package fr.opencraft.client.network

import fr.opencraft.client.GameClient
import fr.opencraft.client.GameState
import fr.opencraft.core.GameCore
import fr.opencraft.client.network.ReceivePacket.Companion.PACKET_SIZE
import fr.opencraft.core.util.DataInput
import fr.opencraft.core.util.DataOutput
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.net.*
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread
import kotlin.concurrent.withLock

class Network(val client: GameClient) {
	private var connected = false
	private var closing = false
	private var address: InetAddress? = null
	private var port: Int = -1
	private val sendThreadLock = ReentrantLock()

	private var tcpSocket: Socket? = null
	private var tcpReceiveThread = thread {}
	private var tcpSendThread = thread {}
	private val tcpSendPackets = ConcurrentLinkedQueue<SendPacket>()
	private val tcpSendCondition = sendThreadLock.newCondition()
	val tcpPort get() = tcpSocket?.localPort ?: -1

	private var udpSocket: DatagramSocket? = null
	private var udpReceiveThread = thread(false) {}
	private var udpSendThread = thread(false) {}
	private val udpSendPackets = ConcurrentLinkedQueue<SendPacket>()
	private val udpSendCondition = sendThreadLock.newCondition()
	val udpPort get() = udpSocket?.localPort ?: -1

	fun connect(address: InetAddress, port: Int) {
		if (connected) disconnect()

		this.address = address
		this.port = port

		try {
			print("Try to connect (${address.hostAddress}:$port).. ")
			tcpSocket = Socket(address, port)
			tcpSendPackets.clear()
			tcpReceiveThread = thread { tcpReceiveThread() }
			tcpSendThread = thread { tcpSendThread() }

			udpSocket = DatagramSocket()
			udpSendPackets.clear()
			udpReceiveThread = thread { udpReceiveThread() }
			udpSendThread = thread { udpSendThread() }

			println("Success")
			connected = true
			client.state = GameState.CONNECTING
		} catch (e: Exception) {
			println("Fail : ${e.message}")
		}
	}

	fun disconnect() {
		if (!connected || closing) return
		closing = true

		tcpSocket!!.close()
		tcpReceiveThread.join()
		sendThreadLock.withLock {
			tcpSendCondition.signal()
		}
		tcpSendThread.join()
		tcpSocket = null

		udpSocket!!.close()
		udpReceiveThread.join()
		sendThreadLock.withLock {
			udpSendCondition.signal()
		}
		udpSendThread.join()
		udpSocket = null

		println("Network Closed")
		connected = false
		closing = false
		client.state = GameState.DISCONNECTED
	}

	fun sendTcp(packet: SendPacket) {
		tcpSendPackets.add(packet)
		sendThreadLock.withLock {
			tcpSendCondition.signal()
		}
	}

	fun sendUdp(packet: SendPacket) {
		udpSendPackets.add(packet)
		sendThreadLock.withLock {
			udpSendCondition.signal()
		}
	}

	private fun tcpReceiveThread() {
		//println("TCP Receive Thread Started")
		val input = DataInput(tcpSocket!!.getInputStream())
		while (!closing) {
			try {
				val identifier = input.read()
				if (identifier == (-1).toByte()) throw SocketException("TCP Read input closed")
				val packet = ReceivePacket.getPacket(identifier)
				packet.process(client, input)
			} catch (e: SocketException) {
				break
			} catch (e: Exception) {
				println("Read Packet Error : ${e.message}")
				break
			}
		}
		if (!closing) thread { disconnect() }
		//println("TCP Receive Thread Closed")
	}

	private fun tcpSendThread() {
		//println("TCP Send Thread Started")
		val output = DataOutput(tcpSocket!!.getOutputStream())
		while (!closing) {
			try {
				if (tcpSendPackets.size > 0) {
					val packet = tcpSendPackets.poll()
					output.write(packet.writeID)
					packet.write(output)
				} else {
					sendThreadLock.withLock {
						tcpSendCondition.await()
					}
				}
			} catch (e: SocketException) {
				break
			} catch (e: Exception) {
				println("Send Packet Error : ${e.message}")
				break
			}
		}
		if (!closing) thread { disconnect() }
		//println("TCP Send Thread Closed")
	}

	private fun udpReceiveThread() {
		//println("UDP Receive Thread Started")
		while (!closing) {
			try {
				val datagram = DatagramPacket(ByteArray(PACKET_SIZE), PACKET_SIZE)
				udpSocket!!.receive(datagram)
				val input = DataInput(ByteArrayInputStream(datagram.data))
				val identifier = input.read()
				val packet = ReceivePacket.getPacket(identifier)
				packet.process(client, input)
			} catch (e: SocketException) {
				break
			} catch (e: Exception) {
				println("Read Packet Error : ${e.message}")
				break
			}
		}
		if (!closing) thread { disconnect() }
		//println("UDP Receive Thread Closed")
	}

	private fun udpSendThread() {
		//println("UDP Send Thread Started")
		while (!closing) {
			try {
				if (udpSendPackets.size > 0) {
					val packet = udpSendPackets.poll()
					val data = ByteArrayOutputStream()
					val output = DataOutput(data)
					output.write(packet.writeID)
					packet.write(output)
					val datagram = DatagramPacket(data.toByteArray(), data.size(), address, port)
					udpSocket!!.send(datagram)
				} else {
					sendThreadLock.withLock {
						udpSendCondition.await()
					}
				}
			} catch (e: SocketException) {
				break
			} catch (e: Exception) {
				println("Send Packet Error : ${e.message}")
				break
			}
		}
		if (!closing) thread { disconnect() }
		//println("UDP Send Thread Closed")
	}
}