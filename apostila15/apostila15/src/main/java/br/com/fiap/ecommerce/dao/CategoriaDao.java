package br.com.fiap.ecommerce.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;

@ApplicationScoped
public class CategoriaDao {

    @Inject
    private DataSource dataSource;


}
