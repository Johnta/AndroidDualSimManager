package DualSimUtils;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by hexuan on 16-6-28.
 */
public class DefaultSimManager extends DualSimManager{
    public static final String TAG = DefaultSimManager.class.getSimpleName();

    @Override
    public int getassistantImsi(Context context) throws InvocationTargetException,IllegalAccessException, NoSuchMethodException {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        Class<?> telephonyclass = telephonyManager.getClass();
        Method method = null;
        boolean param = true;
        try {
            method = telephonyclass.getDeclaredMethod("getSubscriberId",int.class);

        }catch (NoSuchMethodException e){
            try {
                method = telephonyclass.getDeclaredMethod("getSubscriberId",long.class);
                param = false;
            }catch (NoSuchMethodException e2) {
                return NO_SUCH_METHOD;
            }
        }
        Object object = null;
        if (param){
            for(int i = 0;i < 9;i++) {
                object = method.invoke(telephonyManager,i);
                if (object != null){
                    addreult(object.toString());
                }
            }
        } else {
            for(long i = 0;i < 9;i++) {
                object = method.invoke(telephonyManager,i);
                if (object != null){
                    addreult(object.toString());
                }
            }
        }

        return SUCCESS;
    }

    @Override
    protected boolean isMultiSimEnabled(Context context)throws IllegalAccessException,InvocationTargetException,ClassNotFoundException,NoSuchMethodException {
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        Class<?> telephonyClass = TelephonyManager.class;
        Method getSubscriberId = telephonyClass.getMethod("isMultiSimEnabled");
        Object ret  = getSubscriberId.invoke(telephonyManager);
        return (boolean)ret;
    }
}
