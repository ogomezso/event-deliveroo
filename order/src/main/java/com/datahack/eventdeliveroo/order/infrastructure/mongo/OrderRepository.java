package com.datahack.eventdeliveroo.order.infrastructure.mongo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.datahack.eventdeliveroo.order.infrastructure.mongo.model.OrderDocument;

@Repository
interface OrderRepository extends ReactiveMongoRepository<OrderDocument,String> {

}
