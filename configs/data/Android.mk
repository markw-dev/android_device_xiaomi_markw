LOCAL_PATH:= $(call my-dir)

# Qualcomm XML Files

include $(CLEAR_VARS)
LOCAL_MODULE       := dsi_config.xml
LOCAL_MODULE_TAGS  := optional eng
LOCAL_MODULE_CLASS := ETC
LOCAL_SRC_FILES    := dsi_config.xml
LOCAL_MODULE_PATH  := $(TARGET_OUT_ETC)/data
include $(BUILD_PREBUILT)

include $(CLEAR_VARS)
LOCAL_MODULE       := netmgr_config.xml
LOCAL_MODULE_TAGS  := optional eng
LOCAL_MODULE_CLASS := ETC
LOCAL_SRC_FILES    := netmgr_config.xml
LOCAL_MODULE_PATH  := $(TARGET_OUT_ETC)/data
include $(BUILD_PREBUILT)

include $(CLEAR_VARS)
LOCAL_MODULE       := qmi_config.xml
LOCAL_MODULE_TAGS  := optional eng
LOCAL_MODULE_CLASS := ETC
LOCAL_SRC_FILES    := qmi_config.xml
LOCAL_MODULE_PATH  := $(TARGET_OUT_ETC)/data
include $(BUILD_PREBUILT)
