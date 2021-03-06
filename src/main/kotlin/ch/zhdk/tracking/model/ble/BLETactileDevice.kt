package ch.zhdk.tracking.model.ble

import ch.broox.ble.BLECharacteristic
import ch.broox.ble.BLEDevice
import ch.broox.ble.BLEService
import ch.zhdk.tracking.model.TactileDevice

val BLE_SERVICE_ID = "846123f6-ccf1-11ea-87d0-0242ac130003"
val BLE_NEOPIXEL_COLOR_ID = "fc3affa6-5020-47ce-93db-2e9dc45c9b55"
val BLE_IMU_ID = "98f09e34-73ab-4f2a-a5eb-a95e7e7ab733"
val BLE_IR_LED_ID = "fc9a2e54-a7f2-4bad-aebc-9879e896f1b9"
val BLE_IDENTIFIER_ID = "58b8dbc8-045e-4a20-a52d-f181f01e12fe"

class BLETactileDevice(private val bleDevice : BLEDevice) {
    var tactileDevice: TactileDevice? = null
    var lastUpdateTimestamp = 0L

    val matched : Boolean
        get() = tactileDevice != null

    fun connect() {
        bleDevice.connect()
    }

    fun disconnect() {
        bleDevice.disconnect()
    }

    fun match(device : TactileDevice) {
        device.identifier = identifier
        this.tactileDevice = device
    }

    fun unmatch() {
        this.tactileDevice = null
    }

    private val service : BLEService
        get() = bleDevice.getService(BLE_SERVICE_ID)

    private val irLedCharactersitic : BLECharacteristic
        get() = service.getCharacteristic(BLE_IR_LED_ID)

    private val imuCharactersitic : BLECharacteristic
        get() = service.getCharacteristic(BLE_IMU_ID)

    private val neopixelCharactersitic : BLECharacteristic
        get() = service.getCharacteristic(BLE_NEOPIXEL_COLOR_ID)

    private val identifierCharactersitic : BLECharacteristic
        get() = service.getCharacteristic(BLE_IDENTIFIER_ID)

    var isIRLedOn : Boolean
        set(value) = irLedCharactersitic.writeUInt32(if(value) 0x01 else 0x00)
        get() = irLedCharactersitic.readUInt32() == 1

    var identifier : Int
        set(value) = identifierCharactersitic.writeUInt8(value)
        get() = identifierCharactersitic.readUInt8()

    val bleId : String
        get() = bleDevice.id

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BLETactileDevice

        if (bleDevice != other.bleDevice) return false

        return true
    }

    override fun hashCode(): Int {
        return bleDevice.hashCode()
    }
}