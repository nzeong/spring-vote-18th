package com.gotcha.vote.environ;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DatabaseCleaner implements InitializingBean {

    @PersistenceContext
    private EntityManager entityManager;

    private final List<String> tableNames = new ArrayList<>();

    @PostConstruct
    public void getTableNames(){
        entityManager.createNativeQuery("SHOW TABLES")
                .getResultList().stream()
                .forEach(name->tableNames.add(String.valueOf(name)));
    }

    private void truncate() {
        entityManager.createNativeQuery("SET foreign_key_checks" + " = 0").executeUpdate();
        for (String tableName : tableNames) {
            entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
        }
        entityManager.createNativeQuery("SET foreign_key_checks" + " = 1").executeUpdate();
    }

    @Override
    public void afterPropertiesSet() {
        getTableNames();
    }

    @Transactional
    public void clear() {
        entityManager.clear();
        truncate();
    }

}
