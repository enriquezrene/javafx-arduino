package ec.edu.udla.domain.dao;

import java.sql.Types;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ec.edu.udla.domain.LecturaOffLine;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import ec.edu.udla.domain.LecturaGlucometro;
import ec.edu.udla.domain.Paciente;
import ec.edu.udla.domain.PojoBase;

public class PacienteDao extends AbstractDao {

    private LecturaGlucometroDao lecturaGlucometroDao = new LecturaGlucometroDao();

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

    public int saveAndGetId(PojoBase entidad) {
        Paciente paciente = (Paciente) entidad;

        String sql = "insert into paciente" + " (nombre, apellido, cedula, email, direccion, telefono) "
                + "values (:nombre, :apellido, :cedula, :email, :direccion, :telefono)";
        SqlParameterSource fileParameters = new BeanPropertySqlParameterSource(paciente);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        NamedParameterJdbcDaoSupport named = new NamedParameterJdbcDaoSupport();
        named.setJdbcTemplate(conexion.getJdbcTemplate());
        named.getNamedParameterJdbcTemplate().update(sql, fileParameters, keyHolder);

        return (Integer) keyHolder.getKeys().get("id");
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

        Calendar calendarFechaFin = Calendar.getInstance();
        calendarFechaFin.setTime(fechaFin);
        calendarFechaFin.add(Calendar.HOUR, 24);
//        calendarFechaFin.set(Calendar.MINUTE, 59);

        Object[] params = new Object[]{idPaciente, fechaInicio, calendarFechaFin.getTime()};
        int[] types = new int[]{Types.INTEGER, Types.DATE, Types.DATE};
        List<LecturaGlucometro> lecturas = conexion.getJdbcTemplate().query(sql, params, types,
                new BeanPropertyRowMapper<LecturaGlucometro>(LecturaGlucometro.class));
        return lecturas;
    }


    public Paciente buscarPacientePorCedula(String cedula) {
        String sql = "SELECT * FROM paciente WHERE cedula = ?";
        Object[] params = new Object[]{cedula};
        Paciente paciente = null;
        try {
            paciente = conexion.getJdbcTemplate().queryForObject(sql, params, new BeanPropertyRowMapper<>(Paciente.class));
        //System.out.println(paciente.getApellido()+"pa"+paciente.getId());
        } catch (EmptyResultDataAccessException e) {
        	e.printStackTrace();
            paciente = Paciente.NEW;
        }
        return paciente;
    }

    public Paciente buscarPacientePorId(int id) {
        String sql = "SELECT * FROM paciente WHERE id = ?";
        Object[] params = new Object[]{id};
        Paciente paciente = null;
        try {
            paciente = conexion.getJdbcTemplate().queryForObject(sql, params, new BeanPropertyRowMapper<>(Paciente.class));
        } catch (EmptyResultDataAccessException e) {
            paciente = Paciente.NEW;
        }
        return paciente;
    }

    public void registrarLectura(LecturaGlucometro lectura) {
        Paciente paciente = buscarPacientePorCedula(lectura.getCedulaPaciente());
        int id = paciente.getId();
        if (paciente.equals(Paciente.NEW)) {
            paciente.setCedula(lectura.getCedulaPaciente());
            id = saveAndGetId(paciente);
        }
        lectura.setIdPaciente(id);
        lecturaGlucometroDao.save(lectura);
    }

    public void registrarLecturaOffLine(LecturaGlucometro lectura) {
    	System.out.println(lectura.getCedulaPaciente()+"jkhjkhjkhkh");
        Paciente paciente = buscarPacientePorCedula(lectura.getCedulaPaciente());
        lectura.setIdPaciente(paciente.getId());
        LecturaOffLine lecturaOffLine = new LecturaOffLine.Buider().fromLecturaGlucosa(lectura);

        new LecturaOffLineDao().save(lecturaOffLine);
    }

    public void setLecturaGlucometroDao(LecturaGlucometroDao lecturaGlucometroDao) {
        this.lecturaGlucometroDao = lecturaGlucometroDao;
    }
}
