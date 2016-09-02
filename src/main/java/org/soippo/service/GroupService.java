package org.soippo.service;

import org.soippo.entity.Group;
import org.soippo.entity.GroupModules;
import org.soippo.repository.GroupModuleRepository;
import org.soippo.repository.GroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GroupService {
    @Resource
    private GroupRepository groupRepository;

    @Resource
    private GroupModuleRepository groupModuleRepository;

    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    public  Group findGroup(Long groupId) {
        return groupRepository.findOne(groupId);
    }

    public boolean checkGroupNameAvailability(String name) {
        return groupRepository.findByName(name).isEmpty();
    }

    public Group save(Group group) {
        Group saved = groupRepository.save(group);
        List<GroupModules> groupModules = group
                .getGroupModules()
                .stream()
                .map(item -> item.setGroup(saved))
                .collect(Collectors.toList());
        groupModuleRepository.deleteByGroupId(saved.getId());
        groupModuleRepository.save(groupModules);
        return saved;
    }

    public void deleteGroup(Long groupId) {
        groupModuleRepository.deleteByGroupId(groupId);
        groupRepository.delete(groupId);
    }
}
