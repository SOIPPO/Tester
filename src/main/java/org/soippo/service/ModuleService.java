package org.soippo.service;

import org.soippo.entity.Module;
import org.soippo.entity.UserModules;
import org.soippo.repository.InterviewRepository;
import org.soippo.repository.UserModuleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ModuleService {
    @Resource
    private InterviewRepository interviewRepository;
    @Resource
    private UserModuleRepository userModuleRepository;

    public List<Module> findAll() {
        return interviewRepository.findAll();
    }

    public Module findOne(Long id) {
        return interviewRepository.findOne(id);
    }

    public Module save(Module module) {
        return interviewRepository.save(module);
    }

    public void delete(Long interviewId) {
        interviewRepository.delete(interviewId);
    }

    public List<Module> availableModulesForUser(Long userId) {
        List<UserModules> userModules = userModuleRepository.findAllByUserId(userId);
        return userModules.stream().map(UserModules::getModule).collect(Collectors.toList());
    }
}
