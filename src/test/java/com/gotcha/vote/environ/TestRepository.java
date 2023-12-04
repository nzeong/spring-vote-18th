package com.gotcha.vote.environ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.stereotype.Component;

@Component
public class TestRepository {

    @Autowired
    private TestEntityManager em;

    public void 저장하기(Object... objects) {
        for(Object o: objects) {
            em.persist(o);
        }
    }
}
