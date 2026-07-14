package com.rider.essentials.repository;

import com.rider.essentials.domain.model.User;
import com.rider.essentials.domain.model.UserBike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserBikeRepository extends JpaRepository<UserBike, Long> {
    List<UserBike> findByUserOrderByIsPrimaryDescCreatedAtAsc(User user);

    Optional<UserBike> findByBikeIdAndUser(Long bikeId, User user);

    long countByUser(User user);
}
