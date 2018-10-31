rdt_rcv(rcvpkt) && (isCurrupt(rcvpkt == false) || isACK(rcvpkt)==true)

rdt_send(data)

Send_Packet=make_pkt(0, data, checksum)
Udt_send(Sent_Packet)
Start_timer()

timeout
udt_send(Send_Packet)
start_timer()

rdt_rcv(rcvpkt) && isCurrupt(rcvpkt)==false) && isACK(rcvpkt)==false
------------------------------
stop_timer()

nothing

rdt_rcv(rcvpkt)

rdt_sent(data)
------------------------------
sndpkt=make_pkt(1, data, checksum)
udt_send(sndpkt)
start_timer()


rdt_rcv(rcvpkt) && (isCurrupt(rcvpkt)=true || isACK(rcvpkt)==0)
------------------------------

timeout
------------------------------
udt_send(sndpkt)
start_timer()

rdt_rcv(rcvpkt) && isCurrupt(rcvpkt)==false && isACK(rcvpkt)==true
------------------------------
stop_timer()

rdt_rcv(rcvpkt)
------------------------------

rdt_rcv(rcvpkt) && (isCurrupt(rcvpkt==true) || isACK_0(rcvpkt)==false)

isACK_1(rcvpkt)==false)