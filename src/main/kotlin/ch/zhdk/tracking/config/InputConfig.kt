package ch.zhdk.tracking.config

import ch.bildspur.model.DataModel
import ch.bildspur.ui.properties.EnumParameter
import ch.bildspur.ui.properties.NumberParameter
import ch.zhdk.tracking.io.InputProviderType
import com.google.gson.annotations.Expose

class InputConfig {
    @Expose
    @EnumParameter("Input Provider")
    var inputProvider = DataModel(InputProviderType.RealSense2)

    @Expose
    @NumberParameter("Device Index")
    var deviceIndex = DataModel(0)
}