package org.soippo.service;

import org.soippo.entity.Group;
import org.soippo.repository.GroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class GroupService {
    @Resource
    private GroupRepository groupRepository;

    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    public  Group findGroup(Long groupId) {
        return groupRepository.findOne(groupId);
    }

    public boolean checkGroupNameAvailability(String name) {
        return groupRepository.findByName(name).isEmpty();
    }

    public Group saveGroup(Group group) {
        return groupRepository.save(group);
    }

    public void deleteGroup(Long groupId) {
        groupRepository.delete(groupId);
    }
}
