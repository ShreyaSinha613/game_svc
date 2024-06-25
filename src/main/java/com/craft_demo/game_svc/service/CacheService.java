package com.craft_demo.game_svc.service;

import com.craft_demo.game_svc.exception.CacheException;

import java.util.List;

public interface CacheService<T> {
    public void getInitialScoreBoardData(Integer sizeOfBoard, List<T> data) throws CacheException;

    public void checkAndAddNewHighPlayerScore(T data) throws CacheException;

    public List<T> getTopScorers();
}
