package com.chomin;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Singleton;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Singleton
public class CakeRepository {

    private final Map<Integer, Cake> cakeMap = new HashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);


    public CakeRepository() {
        URL url = null;
        try {
            url = URL.of( new URI("https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json"), null);
        } catch (MalformedURLException | URISyntaxException e) {
            throw new RuntimeException("Incorrect URL for the cake data", e);
        }
        List<CakeRequest> cakes = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            cakes = objectMapper.readValue(url, new TypeReference<List<CakeRequest>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Could not deserialize the list of cakes", e);
        }
        for (CakeRequest cake : cakes) {
            add(cake);
        }
    }

    public synchronized List<Cake> findAll() {
        return new ArrayList<>(cakeMap.values());
    }

    public synchronized Optional<Cake> findById(int id) {
        return Optional.ofNullable(cakeMap.get(id));
    }

    public synchronized Cake add(CakeRequest cakeRequest) {
        Cake cake = new Cake(cakeRequest);
        cake.setId(idCounter.getAndIncrement());
        cakeMap.put(cake.getId(), cake);
        return cake;
    }

    public synchronized boolean update(int id, CakeRequest cakeRequest) {
        if (!cakeMap.containsKey(id)) return false;
        Cake cake = new Cake(cakeRequest);
        cake.setId(id);
        cakeMap.put(id, cake);
        return true;
    }

    public synchronized boolean delete(int id) {
        return cakeMap.remove(id) != null;
    }
}
