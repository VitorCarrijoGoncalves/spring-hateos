package br.com.mobile.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.mobile.controller.abstracts.PadraoController;
import br.com.mobile.entities.Order;
import br.com.mobile.interfaces.GenericOperations;
import br.com.mobile.service.OrderService;


@RestController("/orders")
public class OrderController extends PadraoController<Order> {

	Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	public OrderService orderService;

	@Override
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaTypes.HAL_JSON_VALUE })
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Resource<Order>> post(@RequestBody Order order) {
		try {
			orderService.post(order);
			getLogger().info("Registro inserido");

			Link link = linkTo(OrderController.class).slash(order.getId()).withSelfRel();
			Resource<Order> result = new Resource<Order>(order, link);
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			getLogger().error(String.format("Erro ao executar o método POST.\nMensagem: %s", e.getMessage()));
			return ResponseEntity.badRequest().body(null);
		}
	}

	@Override
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
			MediaTypes.HAL_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resources<Order>> get() {
		try {
			List<Order> orders = orderService.get();

			getLogger().info(String.format("Registro(s) recuperados(s): %s", orders.toString()));

			for (final Order order : orders) {
				Link selfLink = linkTo(OrderController.class).slash(order.getIdOrder()).withSelfRel();

				Link selfLinkPeople = linkTo(PeopleController.class).slash(order.getPeople().getIdPeople())
						.withSelfRel();

				order.getPeople().add(selfLinkPeople);
				order.add(selfLink);
			}
			Link link = linkTo(OrderController.class).withSelfRel();
			Resources<Order> result = new Resources<Order>(orders, link);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			getLogger().error(String.format("Erro executar o método GET.\nMensagem: %s", e.getMessage()));
			return ResponseEntity.badRequest().body(null);
		}
	}

	@Override
	@GetMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE, MediaTypes.HAL_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resource<Order>> get(@PathVariable("id") Long id) {
		try {
			Order order = orderService.get(Order.builder().idOrder(id).build());
			getLogger().info(String.format("Registro recuperado: %s", order.toString()));
			Link link = linkTo(OrderController.class).slash(order.getId()).withSelfRel();
			Resource<Order> result = new Resource<Order>(order, link);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			getLogger().error(String.format("Erro executar o método GET.\nMensagem: %s", e.getMessage()));
			return ResponseEntity.badRequest().body(null);
		}
	}

	@Override
	public Class<?> getClassController() {
		return OrderController.class;
	}

	@Override
	public GenericOperations<Order> getGenericOperations() {
		return orderService;
	}

}
