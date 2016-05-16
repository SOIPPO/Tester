package org.soippo.service;

import org.soippo.entity.Group;
import org.soippo.repository.GroupRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GroupService {
    @Resource
    private GroupRepository groupRepository;

    public List<Group> groupList() {
        return groupRepository.findAll();
    }

    public  Group findGroup(Long groupId) {
        return groupRepository.findOne(groupId);
    }
}
