package DualSimUtils;

import android.content.Context;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by hexuan on 16-6-29.
 */
public class HuaweiSimManager extends DualSimManager {
    public static final String TAG = HuaweiSimManager.class.getSimpleName();


    @Override
    protected int getassistantImsi(Context context) throws IllegalAccessException, InvocationTargetException, ClassNotFoundException, NoSuchMethodException {
        Class<?> telephonyClass = Class.forName("android.telephony.MSimTelephonyManager");

        Method getdefault = telephonyClass.getMethod("getDefault");
        Object telephonyManager = getdefault.invoke(null);
        Method method = null;
        boolean param = true;
        try {
            method = telephonyClass.getMethod("getSubscriberId", int.class);
        } catch (NoSuchMethodException e) {
            try {
                method = telephonyClass.getMethod("getSubscriberId", long.class);
                param = false;
            } catch (NoSuchMethodException e2) {
                return NO_SUCH_METHOD;
            }
        }
        Object object = null;
        if (param) {
            for (int i = 0; i < 9; i++) {
                object = method.invoke(telephonyManager, i);
                if (object != null) {
                    addreult(object.toString());
                }
            }
        } else {
            for (long i = 0; i < 9; i++) {
                object = method.invoke(telephonyManager, i);
                if (object != null) {
                    addreult(object.toString());
                }
            }
        }

        return SUCCESS;
    }

    @Override
    protected boolean isMultiSimEnabled(Context context) throws IllegalAccessException, InvocationTargetException, ClassNotFoundException, NoSuchMethodException {
        return false;
    }
}
