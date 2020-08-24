package com.zty.robot.service.impl;

import com.zty.robot.Repository.FaqRepository;
import com.zty.robot.entity.Faq;
import com.zty.robot.service.FaqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FaqServiceImpl implements FaqService {

    @Autowired
    private FaqRepository faqRepository;


    @Override
    public List<Faq> findAll() {
       return  faqRepository.findAll();
    }
}
