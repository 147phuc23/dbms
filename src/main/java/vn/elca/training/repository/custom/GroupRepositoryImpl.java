package vn.elca.training.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import vn.elca.training.model.entity.new_entity.QGroup;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class GroupRepositoryImpl implements GroupRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Long> findGroupIds() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        QGroup group = QGroup.group;

        return queryFactory.select(group.id).from(group)
                .fetch();
    }

}
