package com.Shubham.Url_Shortener.Repo;

import com.Shubham.Url_Shortener.model.UrlDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlDetailsRepo extends JpaRepository<UrlDetails,Long> {

        Optional<UrlDetails> findByEncodedValue(String encodedValue);
}
