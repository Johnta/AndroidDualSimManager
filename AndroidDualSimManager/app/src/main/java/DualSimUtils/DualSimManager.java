package DualSimUtils;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;

/**
 * Created by hexuan on 16-6-28.
 */
public abstract class DualSimManager {
    public static final String TAG = DualSimManager.class.getSimpleName();
    public static String NO_SECOND_IMSI = "NO_SECOND_IMSI";
    public static String NO_MAIN_IMSI = "NO_MAIN_IMSI";

    protected static final int NO_CLASS_FOUND = -1;
    protected static final int NO_SUCH_METHOD = -2;
    protected static final int ILL_ACCESS = -3;
    protected static final int OTHER_WORNG = -4;
    protected static final int SUCCESS = 0;

    private static DualSimManager instance;
    protected HashSet<String> result;
    protected abstract int getassistantImsi(Context context) throws IllegalAccessException,InvocationTargetException,ClassNotFoundException,NoSuchMethodException;
    protected abstract boolean isMultiSimEnabled(Context context)throws IllegalAccessException,InvocationTargetException,ClassNotFoundException,NoSuchMethodException;
    protected DualSimManager() {
        result = new HashSet<String>();
    }


    public static DualSimManager getInstance(String build) {
        if (instance == null) {
            instance = SimManagerFectory.createProduct(build);
        }
        return instance;
    }

    /**
     * for debug
     * @param context
     */
    public static void SimManagerDebug(Context context){
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        Class<?> telephonyclass = telephonyManager.getClass();
        Method[] methods = telephonyclass.getMethods();
        for(int i = 0;i < methods.length; i++) {
            Log.d(TAG, "" + methods[i].toString()+ "\n");
        }
    }

    /**
     * @param string add IMSI TO SET<String>
     */
    protected final void addreult(String string) {
        if (string != null)
        result.add(string);
    }

    /**
     * @param context
     * @return
     * MainCard must be supported, so String[0] is MainSimnothing or IMSI
     * AssentCard maybe supported ,so String[1] is nothing, null, or IMSI
     */
    public final String[] getEntirImsi(Context context) {
        result.clear();
        result.add(getSubscriberId(context));
        checkAssentSim(context);
        String[] strings = new String[result.size()];
        Object[] ret = result.toArray();
        for (int i = 0; i< ret.length;i++) {
            strings[i] = ret[i].toString();
        }
        return strings;
    }

    /**
     *
     * @param context
     * @return ret single card phone or not
     */
    private final void checkAssentSim(Context context){
        int ret = OTHER_WORNG;
        try {
            ret = getassistantImsi(context);
        }catch (InvocationTargetException e) {
            ret = OTHER_WORNG;
        }catch (IllegalAccessException e){
            ret = OTHER_WORNG;
        }catch (ClassNotFoundException e){
            ret = NO_CLASS_FOUND;
        }catch (NoSuchMethodException e){
            ret = NO_SUCH_METHOD;
        }catch (NullPointerException e){
            ret = NO_CLASS_FOUND;
        }
        if (ret == SUCCESS && isMultiSimSupport(context)){
            addreult(NO_SECOND_IMSI);
        }
    }

    /**
     *
     * @param context
     * @return ret single card phone or not
     */
    public final boolean isMultiSimSupport(Context context) {
        boolean ret = false;
        try {
            ret = isMultiSimEnabled(context);
        }catch (InvocationTargetException e) {
            ret = false;
        }catch (IllegalAccessException e){
            ret = false;
        }catch (ClassNotFoundException e){
            ret = false;
        }catch (NoSuchMethodException e){
            ret = false;
        }catch (NullPointerException e){
            ret = false;
        }
        return ret;
    }

    //main simcard, if mainsimCard is null ,add nothing,

    /**
     *
     * @param context
     * @return IMSI or "MainSimnothing", it cannot to be null
     */
    public final String getSubscriberId(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String ret = telephonyManager.getSubscriberId();
        return ret != null ? ret : NO_MAIN_IMSI;
    }

}
