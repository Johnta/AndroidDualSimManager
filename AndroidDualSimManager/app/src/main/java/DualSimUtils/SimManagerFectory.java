package DualSimUtils;

/**
 * Created by hexuan on 16-6-28.
 */
public class SimManagerFectory {

    public static DualSimManager createProduct(String build) {
        DualSimManager DualSimManager = null;
        switch (Modelinfo.valuesOf(build)) {
            case HUAWEIMATE8:
            case DEFAULT:
                DualSimManager = new DefaultSimManager();
                break;
            case SAMSUNGSM:
                DualSimManager = new SamSungSimManager();
                break;
            case HUAWEI:
            case HUAWEIP7:
                DualSimManager = new HuaweiSimManager();
            default:
                break;

        }
        return DualSimManager;
    }

    public enum Modelinfo {
        DEFAULT(""),
        HUAWEIMATE8("HUAWEI NXT-DL00"),
        HUAWEI("HUAWEI"),
        SAMSUNGSM("SM-G5308W"),
        HUAWEIP7("HUAWEI P7-L09");

        private final String value;

        Modelinfo(String value) {
            this.value = value;
        }

        public static Modelinfo valuesOf(String value) {
            for (Modelinfo type : Modelinfo.values()) {
                if (value.equals(type.value) ) {
                    return type;
                }
            }
            return DEFAULT;
        }
        public String value() {
            return value;
        }
    }
}
