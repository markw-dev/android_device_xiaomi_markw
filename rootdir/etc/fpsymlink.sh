#!/system/bin/sh

# Dynamicly symlink fingerprint HAL
if [ -f /system/lib64/hw/fingerprint.default.so ];then
    echo "Already linked"
else
    fpsensor=`getprop persist.sys.fp.vendor`
    mount -o rw,remount /system
    case "$fpsensor" in
         "searchf")
             ln -sf /system/lib64/hw/fingerprint.fpc.so /system/lib64/hw/fingerprint.default.so;;
         "switchf")
             ln -sf /system/lib64/hw/fingerprint.fpc.so /system/lib64/hw/fingerprint.default.so;;
         "goodix")
             ln -sf /system/lib64/hw/fingerprint.goodix.so /system/lib64/hw/fingerprint.default.so;;
    esac
    mount -o ro,remount /system
fi
