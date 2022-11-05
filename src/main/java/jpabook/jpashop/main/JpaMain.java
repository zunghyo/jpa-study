package jpabook.jpashop.main;

import jpabook.jpashop.domain.Book;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{

            /*
            // 양방향
            Order order = new Order();
            em.persist(order);
            order.addOrderItem(new OrderItem());

            // 단방향
            Order order1 = new Order();
            em.persist(order1);
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order1);
            em.persist(orderItem);

            // 상속
            Book book = new Book();
            book.setName("JPA");
            book.setAuthor("김영한");
            em.persist(book);
            */

            // 영속성 전이
            OrderItem orderItemA = new OrderItem();
            OrderItem orderItemB = new OrderItem();

            Order order2 = new Order();
            order2.addOrderItem(orderItemA);
            order2.addOrderItem(orderItemB);
            em.persist(order2);

            // 지연 로딩
            em.flush();
            em.clear();

            System.out.println("==================");
            OrderItem findOrderItem = em.find(OrderItem.class, orderItemA.getId());
            System.out.println("findOrderItem = " + findOrderItem.getOrder().getClass());
            System.out.println("==================");
            System.out.println("getOrderDate = " + findOrderItem.getOrder().getOrderDate());

            tx.commit();
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
