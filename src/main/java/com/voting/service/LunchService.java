package com.voting.service;

import com.voting.model.Lunch;
import com.voting.repository.LunchCrudRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static com.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class LunchService {

    private final LunchCrudRepository lunchCrudRepository;

    public LunchService(LunchCrudRepository lunchCrudRepository) {
        this.lunchCrudRepository = lunchCrudRepository;
    }

    public Lunch create(Lunch lunch) {
        Assert.notNull(lunch, "lunch must be not null");
        return lunchCrudRepository.save(lunch);
    }

    public void delete(int id) {
        checkNotFoundWithId(lunchCrudRepository.delete(id) != 0, id);
    }

    public Lunch get(int id) {
        return checkNotFoundWithId(lunchCrudRepository.findById(id).orElse(null), id);
    }

    public List<Lunch> getAll() {
        return lunchCrudRepository.findAll(Sort.by(Sort.Direction.DESC, "lunchDate"));
    }

    public void update(Lunch lunch) {
        Assert.notNull(lunch, "lunch must be not null");
        checkNotFoundWithId(lunchCrudRepository.save(lunch), lunch.getId());
    }

    public List<Lunch> getByDate(LocalDate date) {
        return lunchCrudRepository.getByLunchDate(date);
    }

    public List<Lunch> getBetweenDates(LocalDate startDate, LocalDate endDate) {
        return lunchCrudRepository.getBetweenDates(startDate, endDate);
    }
}
