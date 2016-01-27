import com.luxoft.bankapp.Annotation.NoDB;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by LChlebda on 2016-01-26.
 */
public class TestService {

    public static boolean isEquals(Object o1, Object o2) {

        Field[] fildsO1 = o1.getClass().getDeclaredFields();
        Field[] fildsO2 = o2.getClass().getDeclaredFields();

        for(int i=0; i< fildsO1.length; i++) {
            fildsO1[i].setAccessible(true);
            fildsO2[i].setAccessible(true);
            if(!fildsO1[i].isAnnotationPresent(NoDB.class)) {
                try {
                    if(!fildsO1[i].get(o1).equals(fildsO2[i].get(o2))) {
                        return false;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}
