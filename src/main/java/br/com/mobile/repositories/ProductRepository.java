package br.com.mobile.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mobile.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
