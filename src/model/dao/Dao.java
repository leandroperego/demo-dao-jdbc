package model.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import db.DbException;

public interface Dao<T> {

	void insert(T obj);
	void update(T obj);
	void deleteById(Integer id);
	T findById(Integer id);
	List<T> findAll();
	default void rollBack(Connection conn, SQLException e) {
		try {
			conn.rollback();
			throw new DbException(e.getMessage());
		}catch (SQLException e1) {
			throw new DbException("Erro. Não foi possivel realizar o rollback! Causa: " + e1.getMessage());
		}
	}
}
