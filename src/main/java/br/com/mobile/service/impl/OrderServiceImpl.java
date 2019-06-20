package br.com.mobile.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mobile.entities.Order;
import br.com.mobile.repositories.OrderRepository;
import br.com.mobile.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	
	Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Autowired
	public OrderRepository orderRepository;
	
	@Override
	@Transactional
	public Order post(Order order) {
		try {
			logger.debug("\tMétodo POST executado.");
			logger.debug("\tMétodo POST invocado");
			logger.debug(String.format("\tValor recebido: %s",order.toString()));
			
			orderRepository.save(order);
			
			logger.info(String.format("\tValor persistido: %s",order.toString()));
			return order;
		} catch (Exception e) {
			logger.error(String.format("Error ao persistir registro. \nMensagem:%s",e.getMessage()));
		}
		return null;
	}

	@Override
	public Order get(Order order) {
		try {
			orderRepository.getOne(order.getIdOrder());
		} catch (Exception e) {
			logger.error("Error ao recuperar método GET.");
		}
		return null;
	}

	@Override
	@Transactional
	public void put(Order order) {
		try {
			orderRepository.getOne(order.getIdOrder());
		} catch (Exception e) {
			logger.error("Error ao recuperar método GET.");
		}
	}

	@Override
	@Transactional
	public void delete(Order entity) {
		try {
			orderRepository.delete(entity);
		} catch (Exception e) {
			logger.error("Error ao deletar registro.");
		}
	}

	@Override
	@Transactional
	public void patch(Order entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional
	public List<Order> post(List<Order> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public void put(List<Order> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional
	public void delete(List<Order> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional
	public void patch(List<Order> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Order> get() {
		try {
			return orderRepository.findAll();
		} catch (Exception e) {
			logger.error("Error ao recuperar registro." +e.getMessage());
		}
		return null;
	}
}
