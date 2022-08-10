package com.example.analisistexxt.service;

import com.example.analisistexxt.model.Result;
import org.springframework.stereotype.Service;

@Service
public interface AnalizeService {
    Result analizeText(String text);
}
