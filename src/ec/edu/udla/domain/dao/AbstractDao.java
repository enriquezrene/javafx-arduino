package ec.edu.udla.domain.dao;

import ec.edu.udla.domain.PojoBase;

public abstract class AbstractDao {

	protected ConexionPostgreSQL conexion;

	public AbstractDao() {
		conexion = ConexionPostgreSQL.getInstance();
	}

	public void saveOrUpdate(PojoBase entidad) {
		if (entidad.getId() != 0) {
			update(entidad);
		} else {
			save(entidad);
		}
	}
	public abstract void save(PojoBase entidad);

	public abstract void update(PojoBase entidad);
}
