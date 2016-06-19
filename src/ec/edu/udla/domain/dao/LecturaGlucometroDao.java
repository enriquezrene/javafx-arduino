package ec.edu.udla.domain.dao;

import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import ec.edu.udla.domain.LecturaGlucometro;
import ec.edu.udla.domain.PojoBase;

/**
 * Created by rene on 09/05/16.
 */
public class LecturaGlucometroDao extends AbstractDao {

    public List<LecturaGlucometro> findAll() {
        String sql = "SELECT * FROM lectura_glucometro ORDER BY id";
        List<LecturaGlucometro> lecturas = conexion.getJdbcTemplate().query(sql,
                new BeanPropertyRowMapper<LecturaGlucometro>(LecturaGlucometro.class));
        for (LecturaGlucometro lectura:
             lecturas) {
            SimpleDateFormat isoFormat = new SimpleDateFormat("yyy-MM-dd'T'HH:mm:ss");
            String fecha = isoFormat.format(lectura.getFecha());
            isoFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            try {
                Date date= isoFormat.parse(fecha);
                lectura.setFecha(isoFormat.parse(fecha));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return lecturas;
    }

    @Override
    public void save(PojoBase entidad) {
        LecturaGlucometro lectura = (LecturaGlucometro) entidad;
        String sql = "insert into lectura_glucometro" + " (id_paciente, valor, fecha, estado) "
                + "values (?, ?, ?, ?)";
        Object[] params = new Object[]{lectura.getIdPaciente(), lectura.getValor(), lectura.getFecha(),
                lectura.getEstado()};
        int[] types = new int[]{Types.NUMERIC, Types.VARCHAR, Types.TIMESTAMP, Types.VARCHAR};
        conexion.getJdbcTemplate().update(sql, params, types);
    }

    @Override
    public void update(PojoBase entidad) {
//        throw new NotImplementedException();
    }
}
