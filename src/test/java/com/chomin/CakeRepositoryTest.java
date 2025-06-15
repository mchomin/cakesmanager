package com.chomin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class CakeRepositoryTest {

    private CakeRepository repository;

    @BeforeEach
    void setUp() {
        // Tto start with an initial repository
        repository = new CakeRepository();
    }

    @Test
    void testLoadsLiveDataOnConstruction() {
        CakeRepository repo = new CakeRepository();
        assertThat(repo.findAll(), is(not(empty())));
    }

    @Test
    void testAddAndFindById() {
        CakeRequest cake = new CakeRequest();
        cake.setTitle("Chocolate");
        cake.setDesc("Rich chocolate cake");
        cake.setImage("http://example.com/choco.jpg");

        Cake added = repository.add(cake);
        assertNotNull(added.getId());

        Optional<Cake> retrieved = repository.findById(added.getId());
        assertTrue(retrieved.isPresent());
        assertEquals("Chocolate", retrieved.get().getTitle());
        assertEquals("Rich chocolate cake", retrieved.get().getDesc());
        assertEquals("http://example.com/choco.jpg", retrieved.get().getImage());
    }

    @Test
    void testFindAll() {
        repository.add(new CakeRequest("A", "desc A", "img A"));
        repository.add(new CakeRequest("B", "desc B", "img B"));

        List<Cake> all = repository.findAll();
        assertEquals(22, all.size());
    }

    @Test
    void testFindByIdReturnsEmptyForNonexistentId() {
        Optional<Cake> result = repository.findById(999);
        assertTrue(result.isEmpty());
    }

    @Test
    void testUpdateSuccess() {
        Cake cake = repository.add(new CakeRequest("Original", "desc", "img"));
        int id = cake.getId();

        CakeRequest updated = new CakeRequest( "Updated", "new desc", "new img");
        boolean result = repository.update(id, updated);

        assertTrue(result);
        Optional<Cake> retrieved = repository.findById(id);
        assertEquals("Updated", retrieved.get().getTitle());
        assertEquals("new desc", retrieved.get().getDesc());
        assertEquals("new img", retrieved.get().getImage());
        assertEquals(id, retrieved.get().getId()); // ID should be retained
    }

    @Test
    void testUpdateFailsForNonexistentId() {
        CakeRequest cake = new CakeRequest( "Ghost", "desc", "img");
        boolean result = repository.update(12345, cake);
        assertFalse(result);
    }

    @Test
    void testDeleteSuccess() {
        Cake cake = repository.add(new CakeRequest( "Delete Me", "desc", "img"));
        int id = cake.getId();

        boolean deleted = repository.delete(id);
        assertTrue(deleted);
        assertTrue(repository.findById(id).isEmpty());
    }

    @Test
    void testDeleteFailsForNonexistentId() {
        assertFalse(repository.delete(9999));
    }
}