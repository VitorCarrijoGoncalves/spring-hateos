package br.com.mobile.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mobile.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
