package ch.zhdk.tracking.io

enum class BaudRate(val rate: Int) {
    B110(110),
    B150(150),
    B300(300),
    B1200(1200),
    B2400(2400),
    B4800(4800),
    B9600(9600),
    B19200(19200),
    B38400(57600),
    B115200(115200),
    B230400(230400),
    B460800(460800),
    B921600(921600)
}