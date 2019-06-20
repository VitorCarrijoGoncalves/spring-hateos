package br.com.mobile.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mobile.entities.People;

@Repository
public interface PeopleRepository extends JpaRepository<People, Long> {

}
