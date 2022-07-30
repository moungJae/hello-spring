package hello.core.scope;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;
import java.lang.annotation.Annotation;

public class SingletoneWithPrototypeTest1 {

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addPrice();
        Assertions.assertEquals(1000, prototypeBean1.getPrice());

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addPrice();
        Assertions.assertEquals(1000, prototypeBean2.getPrice());

        ac.close();
    }

    @Test
    void singletonClientUserPrototype() {
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int price1 = clientBean1.logic();
        Assertions.assertEquals(1000, price1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int price2 = clientBean2.logic();
        Assertions.assertEquals(1000, price2);
    }

    @Scope("singleton")
    static class ClientBean {
        @Autowired
        private Provider<PrototypeBean> prototypeBeanProvider;

        public int logic() {
            PrototypeBean prototypeBean = prototypeBeanProvider.get();
            prototypeBean.addPrice();
            return prototypeBean.getPrice();
        }
    }

    @Scope("prototype")
    static class PrototypeBean {
        private int price = 0;

        public void addPrice() {
            price += 1000;
        }

        public int getPrice() {
            return price;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init " + this);
        }

        @PreDestroy
        public void clear() {
            System.out.println("PrototypeBean.clear " + this);
        }
    }
}
