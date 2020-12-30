package vn.elca.training.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.elca.training.model.entity.new_entity.Group;
import vn.elca.training.repository.GroupRepository;
import vn.elca.training.service.GroupService;

import java.util.List;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    public GroupServiceImpl(@Autowired GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public List<Long> findGroupIds() {
        return groupRepository.findGroupIds();
    }

}
