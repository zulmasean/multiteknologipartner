package tests;

import base.BaseTest;
import client.OrdersClient;
import db.DBHelper;
import model.OrderRequest;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;

public class OrdersTest extends BaseTest {

    @Test
    void createOrder_positive() {

        String id = "order-" + UUID.randomUUID();

        OrderRequest order = new OrderRequest();
        order.id = id;
        order.quantity = 2;
        order.price = 10;
        order.total = 20;

        OrdersClient.createOrder(token, order)
                .then()
                .statusCode(201)
                .body("status", equalTo("CREATED"));

        assert DBHelper.orderExists(id);
    }

    @Test
    void createOrder_quantityZero_shouldFail() {

        String id = "order-" + UUID.randomUUID();

        OrderRequest order = new OrderRequest();
        order.id = id;
        order.quantity = 0;
        order.price = 10;
        order.total = 0;

        OrdersClient.createOrder(token, order)
                .then()
                .statusCode(400);
    }

    @Test
    void createOrder_boundary_quantityOne() {

        String id = "order-" + UUID.randomUUID();

        OrderRequest order = new OrderRequest();
        order.id = id;
        order.quantity = 1;
        order.price = 10;
        order.total = 10;

        OrdersClient.createOrder(token, order)
                .then()
                .statusCode(201);
    }

    @Test
    void createOrder_invalidTotal_shouldFail() {

        String id = "order-" + UUID.randomUUID();

        OrderRequest order = new OrderRequest();
        order.id = id;
        order.quantity = 2;
        order.price = 10;
        order.total = 100;

        OrdersClient.createOrder(token, order)
                .then()
                .statusCode(400);
    }

    @Test
    void createOrder_duplicateId_shouldFail() {

        String id = "duplicate-order";

        OrderRequest order = new OrderRequest();
        order.id = id;
        order.quantity = 2;
        order.price = 10;
        order.total = 20;

        OrdersClient.createOrder(token, order);

        OrdersClient.createOrder(token, order)
                .then()
                .statusCode(409);
    }

    @Test
    void updateOrder_paidOrder_totalCannotChange() {

        String id = "order-" + UUID.randomUUID();

        OrderRequest order = new OrderRequest();
        order.id = id;
        order.quantity = 2;
        order.price = 10;
        order.total = 20;
        order.status = "PAID";

        OrdersClient.createOrder(token, order);

        order.total = 30;

        OrdersClient.updateOrder(token, id, order)
                .then()
                .statusCode(400);
    }

    @Test
    void deleteOrder_idempotent() {

        String id = "order-" + UUID.randomUUID();

        OrderRequest order = new OrderRequest();
        order.id = id;
        order.quantity = 2;
        order.price = 10;
        order.total = 20;

        OrdersClient.createOrder(token, order);

        OrdersClient.deleteOrder(token, id)
                .then()
                .statusCode(204);

        OrdersClient.deleteOrder(token, id)
                .then()
                .statusCode(204);
    }
}