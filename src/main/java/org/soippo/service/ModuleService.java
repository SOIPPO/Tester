package org.soippo.service;

import org.soippo.entity.GroupModules;
import org.soippo.entity.Module;
import org.soippo.repository.GroupModuleRepository;
import org.soippo.repository.InterviewRepository;
import org.soippo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Transactional
public class ModuleService {
    @Resource
    private InterviewRepository interviewRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private GroupModuleRepository groupModuleRepository;

    public List<Module> findAll() {
        return interviewRepository.findAll();
    }

    public Module findOne(Long id) {
        return interviewRepository.findOne(id);
    }

    public Module save(Module module) {
        return interviewRepository.save(module);
    }

    public void delete(Long moduleId) {
        groupModuleRepository.deleteByModuleId(moduleId);
        groupModuleRepository.flush();
        interviewRepository.delete(moduleId);
    }

    public List<Module> availableModulesForUser(Long userId) {
        Predicate<LocalDate> isToday = date -> date.getYear() == LocalDate.now().getYear() && date.getDayOfYear() == LocalDate.now().getDayOfYear();
        return groupModuleRepository.findByGroupId(userRepository.findOne(userId).getGroupId())
                .stream()
                .filter(item -> isToday.test(item.getFinalDate()) || isToday.test(item.getIncomingDate()))
                .map(GroupModules::getModule)
                .collect(Collectors.toList());
    }
}
