package org.management.service.impl;

import org.management.dto.SenderDTO;
import org.management.mapper.SenderMapper;
import org.management.repository.SenderRepository;
import org.management.service.SenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SenderServiceImpl implements SenderService {

    private final SenderRepository senderRepository;
    private final SenderMapper senderMapper;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public SenderServiceImpl(SenderRepository senderRepository, SenderMapper senderMapper) {
        this.senderRepository = senderRepository;
        this.senderMapper = senderMapper;
    }

    // get all of senders
    @Override
    public List<SenderDTO> getAllOfSenders() {
        logger.info("get All of Senders");
        return senderRepository.findAll().stream().map(senderMapper::toDto).collect(Collectors.toList());
    }
}
