package ec.edu.udla.domain.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rene on 26/04/16.
 */
public class Context {

    public static final String PACIENTE_EN_MEMORIA = "paciente.seleccionado";

    private Map<String, Object> data;
    private final static Context instance = new Context();

    private Context() {
        data = new HashMap<>();
    }

    public static Context getInstance() {
        return instance;
    }

    public void put(String key, Object valor) {
        data.put(key, valor);
    }

    public Object get(String key) {
        return data.get(key);
    }
}
