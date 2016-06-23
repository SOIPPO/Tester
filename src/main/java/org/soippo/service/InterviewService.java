package org.soippo.service;

import org.soippo.entity.Interview;
import org.soippo.repository.InterviewRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class InterviewService {
    @Resource
    private InterviewRepository interviewRepository;

    public List<Interview> findAll() {
        return interviewRepository.findAll();
    }
}
