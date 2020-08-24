package com.zty.robot.service.impl;


import com.zty.robot.Repository.StopRepository;
import com.zty.robot.entity.Stop;
import com.zty.robot.service.StopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StopServiceImpl implements StopService {

    @Autowired
    private StopRepository stopRepository;

    @Override
    public List<Stop> getAll() {

        return stopRepository.findAll();
    }
}
