rdt_rcv(rcvpkt) && isCurrupt(rcvpkt)==false && has_seq0(rcvpkt)
--------------------------------
extract(rcvpkt, data)
deliver_data(data)
sndpkt=make_pkt(ACK, 0, checksum)
udt_send(sndpkt)



rdt_rcv(rcvpkt) && (isCurrupt(rcvpkt)==true || has_seq1(rcvpkt)==false)
--------------------------------
sndpkt=make_pkt(ACK, 0, checksum)
udt_send(sndpkt)


rdt_rcv(rcvpkt) && isCorrupt(rcvpkt)==true && has_seq1(rcvpkt)==1
--------------------------------
extract(rcvpkt, data)
deliver_data(data)
sndpkt=make_pkt(ACK, 1, checksum)
udt_send(sndpkt)


rdt_rcv(rcvpkt) && (isCurrupt(rcvpkt)==true || has_seq1(rcvpkt)==false)
--------------------------------
sndpkt=make_pkt(ACK, 1, checksum)
udt_send(sndpkt)