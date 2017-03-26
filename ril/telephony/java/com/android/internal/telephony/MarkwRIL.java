/*
 * Copyright (C) 2017 The LineageOS Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.internal.telephony;

import static com.android.internal.telephony.RILConstants.*;
import android.content.Context;
import android.os.Message;
import android.os.Parcel;
import android.os.AsyncResult;
import com.android.internal.telephony.uicc.IccIoResult;
import java.util.ArrayList;

public class MarkwRIL extends RIL implements CommandsInterface {

    public MarkwRIL(Context context, int networkMode, int cdmaSubscription) {
        super(context, networkMode, cdmaSubscription, null);
    }

    public MarkwRIL(Context context, int networkMode, int cdmaSubscription, Integer instanceId) {
        super(context, networkMode, cdmaSubscription, instanceId);
    }

    static final int NETWORK_TYPE_TDS = 0x11;
    static final int NETWORK_TYPE_TDS_HSDPA = 0x12; //=> 8
    static final int NETWORK_TYPE_TDS_HSUPA = 0x13; //=> 9

    @Override
    protected RILRequest
    processSolicited(Parcel p) {
        int serial, error;
        boolean found = false;

        serial = p.readInt();
        error = p.readInt();

        RILRequest rr;

        rr = findAndRemoveRequestFromList(serial);

        if (rr == null) {
            return null;
        }

        Object ret = null;

        if (error == 0 || p.dataAvail() > 0) {
            try {
                switch (rr.mRequest) {
                    case RIL_REQUEST_GET_SIM_STATUS:
                        ret = responseIccCardStatus(p);
                        break;
                    case RIL_REQUEST_ENTER_SIM_PIN:
                        ret = responseInts(p);
                        break;
                    case RIL_REQUEST_ENTER_SIM_PUK:
                        ret = responseInts(p);
                        break;
                    case RIL_REQUEST_ENTER_SIM_PIN2:
                        ret = responseInts(p);
                        break;
                    case RIL_REQUEST_ENTER_SIM_PUK2:
                        ret = responseInts(p);
                        break;
                    case RIL_REQUEST_CHANGE_SIM_PIN:
                        ret = responseInts(p);
                        break;
                    case RIL_REQUEST_CHANGE_SIM_PIN2:
                        ret = responseInts(p);
                        break;
                    case RIL_REQUEST_GET_CURRENT_CALLS:
                        ret = responseCallList(p);
                        break;
                    case RIL_REQUEST_DIAL:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_GET_IMSI:
                        ret = responseString(p);
                        break;
                    case RIL_REQUEST_HANGUP:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_HANGUP_WAITING_OR_BACKGROUND:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_HANGUP_FOREGROUND_RESUME_BACKGROUND:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_SWITCH_WAITING_OR_HOLDING_AND_ACTIVE:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_CONFERENCE:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_UDUB:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_LAST_CALL_FAIL_CAUSE:
                        ret = responseFailCause(p);
                        break;
                    case RIL_REQUEST_SIGNAL_STRENGTH:
                        ret = responseSignalStrength(p);
                        break;
                    case RIL_REQUEST_VOICE_REGISTRATION_STATE:
                        ret = responseStrings(p);
                        break;
                    case RIL_REQUEST_DATA_REGISTRATION_STATE:
                        ret = responseStrings(p);
                        break;
                    case RIL_REQUEST_OPERATOR:
                        ret = responseStrings(p);
                        break;
                    case RIL_REQUEST_RADIO_POWER:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_DTMF:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_SEND_SMS:
                        ret = responseSMS(p);
                        break;
                    case RIL_REQUEST_SEND_SMS_EXPECT_MORE:
                        ret = responseSMS(p);
                        break;
                    case RIL_REQUEST_SETUP_DATA_CALL:
                        ret = responseSetupDataCall(p);
                        break;
                    case RIL_REQUEST_SIM_IO:
                        ret = responseICC_IO(p);
                        break;
                    case RIL_REQUEST_SEND_USSD:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_CANCEL_USSD:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_GET_CLIR:
                        ret = responseInts(p);
                        break;
                    case RIL_REQUEST_SET_CLIR:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_QUERY_CALL_FORWARD_STATUS:
                        ret = responseCallForward(p);
                        break;
                    case RIL_REQUEST_SET_CALL_FORWARD:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_QUERY_CALL_WAITING:
                        ret = responseInts(p);
                        break;
                    case RIL_REQUEST_SET_CALL_WAITING:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_SMS_ACKNOWLEDGE:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_GET_IMEI:
                        ret = responseString(p);
                        break;
                    case RIL_REQUEST_GET_IMEISV:
                        ret = responseString(p);
                        break;
                    case RIL_REQUEST_ANSWER:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_DEACTIVATE_DATA_CALL:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_QUERY_FACILITY_LOCK:
                        ret = responseInts(p);
                        break;
                    case RIL_REQUEST_SET_FACILITY_LOCK:
                        ret = responseInts(p);
                        break;
                    case RIL_REQUEST_CHANGE_BARRING_PASSWORD:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_QUERY_NETWORK_SELECTION_MODE:
                        ret = responseInts(p);
                        break;
                    case RIL_REQUEST_SET_NETWORK_SELECTION_AUTOMATIC:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_SET_NETWORK_SELECTION_MANUAL:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_QUERY_AVAILABLE_NETWORKS:
                        ret = responseOperatorInfos(p);
                        break;
                    case RIL_REQUEST_DTMF_START:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_DTMF_STOP:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_BASEBAND_VERSION:
                        ret = responseString(p);
                        break;
                    case RIL_REQUEST_SEPARATE_CONNECTION:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_SET_MUTE:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_GET_MUTE:
                        ret = responseInts(p);
                        break;
                    case RIL_REQUEST_QUERY_CLIP:
                        ret = responseInts(p);
                        break;
                    case RIL_REQUEST_LAST_DATA_CALL_FAIL_CAUSE:
                        ret = responseInts(p);
                        break;
                    case RIL_REQUEST_DATA_CALL_LIST:
                        ret = responseDataCallList(p);
                        break;
                    case RIL_REQUEST_RESET_RADIO:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_OEM_HOOK_RAW:
                        ret = responseRaw(p);
                        break;
                    case RIL_REQUEST_OEM_HOOK_STRINGS:
                        ret = responseStrings(p);
                        break;
                    case RIL_REQUEST_SCREEN_STATE:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_SET_SUPP_SVC_NOTIFICATION:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_WRITE_SMS_TO_SIM:
                        ret = responseInts(p);
                        break;
                    case RIL_REQUEST_DELETE_SMS_ON_SIM:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_SET_BAND_MODE:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_QUERY_AVAILABLE_BAND_MODE:
                        ret = responseInts(p);
                        break;
                    case RIL_REQUEST_STK_GET_PROFILE:
                        ret = responseString(p);
                        break;
                    case RIL_REQUEST_STK_SET_PROFILE:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_STK_SEND_ENVELOPE_COMMAND:
                        ret = responseString(p);
                        break;
                    case RIL_REQUEST_STK_SEND_TERMINAL_RESPONSE:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_STK_HANDLE_CALL_SETUP_REQUESTED_FROM_SIM:
                        ret = responseInts(p);
                        break;
                    case RIL_REQUEST_EXPLICIT_CALL_TRANSFER:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_SET_PREFERRED_NETWORK_TYPE:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_GET_PREFERRED_NETWORK_TYPE:
                        ret = responseGetPreferredNetworkType(p);
                        break;
                    case RIL_REQUEST_GET_NEIGHBORING_CELL_IDS:
                        ret = responseCellList(p);
                        break;
                    case RIL_REQUEST_SET_LOCATION_UPDATES:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_CDMA_SET_SUBSCRIPTION_SOURCE:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_CDMA_SET_ROAMING_PREFERENCE:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_CDMA_QUERY_ROAMING_PREFERENCE:
                        ret = responseInts(p);
                        break;
                    case RIL_REQUEST_SET_TTY_MODE:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_QUERY_TTY_MODE:
                        ret = responseInts(p);
                        break;
                    case RIL_REQUEST_CDMA_SET_PREFERRED_VOICE_PRIVACY_MODE:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_CDMA_QUERY_PREFERRED_VOICE_PRIVACY_MODE:
                        ret = responseInts(p);
                        break;
                    case RIL_REQUEST_CDMA_FLASH:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_CDMA_BURST_DTMF:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_CDMA_SEND_SMS:
                        ret = responseSMS(p);
                        break;
                    case RIL_REQUEST_CDMA_SMS_ACKNOWLEDGE:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_GSM_GET_BROADCAST_CONFIG:
                        ret = responseGmsBroadcastConfig(p);
                        break;
                    case RIL_REQUEST_GSM_SET_BROADCAST_CONFIG:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_GSM_BROADCAST_ACTIVATION:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_CDMA_GET_BROADCAST_CONFIG:
                        ret = responseCdmaBroadcastConfig(p);
                        break;
                    case RIL_REQUEST_CDMA_SET_BROADCAST_CONFIG:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_CDMA_BROADCAST_ACTIVATION:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_CDMA_VALIDATE_AND_WRITE_AKEY:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_CDMA_SUBSCRIPTION:
                        ret = responseStrings(p);
                        break;
                    case RIL_REQUEST_CDMA_WRITE_SMS_TO_RUIM:
                        ret = responseInts(p);
                        break;
                    case RIL_REQUEST_CDMA_DELETE_SMS_ON_RUIM:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_DEVICE_IDENTITY:
                        ret = responseStrings(p);
                        break;
                    case RIL_REQUEST_GET_SMSC_ADDRESS:
                        ret = responseString(p);
                        break;
                    case RIL_REQUEST_SET_SMSC_ADDRESS:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_EXIT_EMERGENCY_CALLBACK_MODE:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_REPORT_SMS_MEMORY_STATUS:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_REPORT_STK_SERVICE_IS_RUNNING:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_CDMA_GET_SUBSCRIPTION_SOURCE:
                        ret = responseInts(p);
                        break;
                    case RIL_REQUEST_ISIM_AUTHENTICATION:
                        ret = responseString(p);
                        break;
                    case RIL_REQUEST_ACKNOWLEDGE_INCOMING_GSM_SMS_WITH_PDU:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_STK_SEND_ENVELOPE_WITH_STATUS:
                        ret = responseICC_IO(p);
                        break;
                    case RIL_REQUEST_VOICE_RADIO_TECH:
                        ret = responseInts(p);
                        break;
                    case RIL_REQUEST_GET_CELL_INFO_LIST:
                        ret = responseCellInfoList(p);
                        break;
                    case RIL_REQUEST_SET_UNSOL_CELL_INFO_LIST_RATE:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_SET_INITIAL_ATTACH_APN:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_SET_DATA_PROFILE:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_IMS_REGISTRATION_STATE:
                        ret = responseInts(p);
                        break;
                    case RIL_REQUEST_IMS_SEND_SMS:
                        ret = responseSMS(p);
                        break;
                    case RIL_REQUEST_SIM_TRANSMIT_APDU_BASIC:
                        ret = responseICC_IO(p);
                        break;
                    case RIL_REQUEST_SIM_OPEN_CHANNEL:
                        ret = responseInts(p);
                        break;
                    case RIL_REQUEST_SIM_CLOSE_CHANNEL:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_SIM_TRANSMIT_APDU_CHANNEL:
                        ret = responseICC_IO(p);
                        break;
                    case RIL_REQUEST_NV_READ_ITEM:
                        ret = responseString(p);
                        break;
                    case RIL_REQUEST_NV_WRITE_ITEM:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_NV_WRITE_CDMA_PRL:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_NV_RESET_CONFIG:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_SET_UICC_SUBSCRIPTION:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_ALLOW_DATA:
                        ret = responseVoid(p);
                        break;
                    case RIL_REQUEST_GET_HARDWARE_CONFIG:
                        ret = responseHardwareConfig(p);
                        break;
                    case RIL_REQUEST_SIM_AUTHENTICATION:
                        ret = responseICC_IOBase64(p);
                        break;
                    case RIL_REQUEST_SHUTDOWN:
                        ret = responseVoid(p);
                        break;
                    default:
                        throw new RuntimeException("Unrecognized solicited response: " + rr.mRequest);
                        //break;
                }
            } catch (Throwable tr) {
                if (rr.mResult != null) {
                    AsyncResult.forMessage(rr.mResult, null, tr);
                    rr.mResult.sendToTarget();
                }
                return rr;
            }
        }

        switch (rr.mRequest) {
            case RIL_REQUEST_ENTER_SIM_PUK:
            case RIL_REQUEST_ENTER_SIM_PUK2:
                if (mIccStatusChangedRegistrants != null) {
                    mIccStatusChangedRegistrants.notifyRegistrants();
                }
                break;
        }

        if (error != 0) {
            switch (rr.mRequest) {
                case RIL_REQUEST_ENTER_SIM_PIN:
                case RIL_REQUEST_ENTER_SIM_PIN2:
                case RIL_REQUEST_CHANGE_SIM_PIN:
                case RIL_REQUEST_CHANGE_SIM_PIN2:
                case RIL_REQUEST_SET_FACILITY_LOCK:
                    if (mIccStatusChangedRegistrants != null) {
                        mIccStatusChangedRegistrants.notifyRegistrants();
                    }
                    break;
            }

            rr.onError(error, ret);
        } else {
            if (rr.mResult != null) {
                AsyncResult.forMessage(rr.mResult, ret, null);
                rr.mResult.sendToTarget();
            }
        }
        return rr;
    }

    protected Object
    responseHardwareConfig(Parcel p) {
       int num;
       ArrayList<HardwareConfig> response;
       HardwareConfig hw;

       num = p.readInt();
       response = new ArrayList<HardwareConfig>(num);

       for (int i = 0 ; i < num ; i++) {
          int type = p.readInt();
          switch(type) {
             case HardwareConfig.DEV_HARDWARE_TYPE_MODEM: {
                hw = new HardwareConfig(type);
                hw.assignModem(p.readString(), p.readInt(), p.readInt(),
                   p.readInt(), p.readInt(), p.readInt(), p.readInt());
                break;
             }
             case HardwareConfig.DEV_HARDWARE_TYPE_SIM: {
                hw = new HardwareConfig(type);
                hw.assignSim(p.readString(), p.readInt(), p.readString());
                break;
             }
             default: {
                throw new RuntimeException(
                   "RIL_REQUEST_GET_HARDWARE_CONFIG invalid hardward type:" + type);
             }
          }
 
          response.add(hw);
       }
 
       return response;
    }

    protected Object
    responseICC_IOBase64(Parcel p) {
        int sw1, sw2;
        Message ret;

        sw1 = p.readInt();
        sw2 = p.readInt();

        String s = p.readString();

        return new IccIoResult(sw1, sw2, android.util.Base64.decode(s, android.util.Base64.DEFAULT));
    }
}
