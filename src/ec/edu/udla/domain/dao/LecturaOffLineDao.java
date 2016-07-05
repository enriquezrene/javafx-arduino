package ec.edu.udla.domain.dao;

import ec.edu.udla.domain.LecturaOffLine;
import ec.edu.udla.domain.PojoBase;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.sql.Types;
import java.util.List;

/**
 * Created by rene on 09/05/16.
 */
public class LecturaOffLineDao extends AbstractDao {

    public List<LecturaOffLine> findAll() {
        String sql = "SELECT * FROM lectura_offline ORDER BY id DESC";
        List<LecturaOffLine> lecturas = conexion.getJdbcTemplate().query(sql,
                new BeanPropertyRowMapper<LecturaOffLine>(LecturaOffLine.class));
        return lecturas;
    }

    @Override
    public void save(PojoBase entidad) {
        LecturaOffLine lectura = (LecturaOffLine) entidad;
        String sql = "insert into lectura_offline" + " (id_paciente, valor, fecha, estado, leido) "
                + "values (?, ?, ?, ?, ?)";
        Object[] params = new Object[]{lectura.getIdPaciente(), lectura.getValor(), lectura.getFecha(),
                lectura.getEstado()};
        int[] types = new int[]{Types.NUMERIC, Types.VARCHAR, Types.TIMESTAMP, Types.VARCHAR, Types.BOOLEAN};
        conexion.getJdbcTemplate().update(sql, params, types, lectura.isLeido());
    }

    public void marcarComoLeido(LecturaOffLine lecturaOffLine){
        String sql = "update lectura_offline set leido=true where id="+lecturaOffLine.getId();
        conexion.getJdbcTemplate().update(sql);
    }

    @Override
    public void update(PojoBase entidad) {
//        throw new NotImplementedException();
    }
}
