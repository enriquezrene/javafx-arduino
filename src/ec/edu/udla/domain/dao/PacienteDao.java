package ec.edu.udla.domain.dao;

import java.sql.Types;
import java.util.Date;
import java.util.List;

import org.mockito.asm.Type;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import ec.edu.udla.domain.LecturaGlucometro;
import ec.edu.udla.domain.Paciente;
import ec.edu.udla.domain.PojoBase;

public class PacienteDao extends AbstractDao {

    public List<Paciente> findAll() {
        String sql = "SELECT * FROM paciente ORDER BY id";
        List<Paciente> pacientes = conexion.getJdbcTemplate().query(sql,
                new BeanPropertyRowMapper<Paciente>(Paciente.class));
        return pacientes;
    }


    @Override
    public void save(PojoBase entidad) {
        Paciente paciente = (Paciente) entidad;
        String sql = "insert into paciente" + " (nombre, apellido, cedula, email, direccion, telefono, peso, estatura) "
                + "values (?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] params = new Object[]{paciente.getNombre(), paciente.getApellido(), paciente.getCedula(),
                paciente.getEmail(), paciente.getDireccion(), paciente.getTelefono(), paciente.getPeso(),
                paciente.getEstatura()};
        int[] types = new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.NUMERIC, Types.INTEGER};
        conexion.getJdbcTemplate().update(sql, params, types);
    }

    @Override
    public void update(PojoBase entidad) {
        Paciente paciente = (Paciente) entidad;
        String sql = "UPDATE paciente set nombre = ?, apellido=?, cedula=?, email=?, direccion=?, telefono=?, peso=?, estatura=? "
                + "where id=?";
        Object[] params = new Object[]{paciente.getNombre(), paciente.getApellido(), paciente.getCedula(),
                paciente.getEmail(), paciente.getDireccion(), paciente.getTelefono(), paciente.getPeso(),
                paciente.getEstatura(), paciente.getId()};
        int[] types = new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.NUMERIC, Types.INTEGER, Types.INTEGER};
        conexion.getJdbcTemplate().update(sql, params, types);
    }

    public void delete(int id) {
        String sql = "delete from paciente where id = " + id;
        conexion.getJdbcTemplate().update(sql);
    }

    public List<LecturaGlucometro> buscarLecturasGlucosa(int idPaciente) {
        String sql = "SELECT * FROM lectura_glucometro WHERE id_paciente = " + idPaciente + " ORDER BY id";
        List<LecturaGlucometro> lecturas = conexion.getJdbcTemplate().query(sql,
                new BeanPropertyRowMapper<LecturaGlucometro>(LecturaGlucometro.class));
        return lecturas;
    }

    public List<LecturaGlucometro> buscarLecturasGlucosaEntreFechas(int idPaciente, Date fechaInicio, Date fechaFin) {
        String sql = "SELECT * FROM lectura_glucometro WHERE id_paciente = ? and  fecha BETWEEN ? AND ?";
        Object[] params = new Object[]{idPaciente, fechaInicio, fechaFin};
        int [] types = new int[]{Types.INTEGER, Types.DATE, Types.DATE};
        List<LecturaGlucometro> lecturas = conexion.getJdbcTemplate().query(sql, params, types,
                new BeanPropertyRowMapper<LecturaGlucometro>(LecturaGlucometro.class));
        return lecturas;
    }


}
