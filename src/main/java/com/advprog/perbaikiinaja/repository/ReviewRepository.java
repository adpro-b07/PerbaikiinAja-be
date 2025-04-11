package com.advprog.perbaikiinaja.repository;
import com.advprog.perbaikiinaja.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    // Custom query method to find reviews by technicianId
    List<Review> findByTechnicianId(int technicianId);
}