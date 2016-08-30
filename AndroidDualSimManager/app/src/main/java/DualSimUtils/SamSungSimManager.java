package DualSimUtils;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by hexuan on 16-6-28.
 */
public class SamSungSimManager extends DualSimManager {
    public static final String TAG = SamSungSimManager.class.getSimpleName();

    @Override
    protected int getassistantImsi(Context context) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String ret = "";
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone2");
            ret = telephonyManager.getSubscriberId() != null ? telephonyManager.getSubscriberId() : NO_SECOND_IMSI;
        } catch (NullPointerException e) {
            return NO_CLASS_FOUND;
        }
        addreult(ret);
        return SUCCESS;
    }

    @Override
    protected boolean isMultiSimEnabled(Context context) throws IllegalAccessException, InvocationTargetException, ClassNotFoundException, NoSuchMethodException {
        return true;
    }
}
