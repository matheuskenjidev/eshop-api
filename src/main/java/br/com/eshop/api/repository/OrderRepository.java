package br.com.eshop.api.repository;

import br.com.eshop.api.model.Order;
import br.com.eshop.api.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
