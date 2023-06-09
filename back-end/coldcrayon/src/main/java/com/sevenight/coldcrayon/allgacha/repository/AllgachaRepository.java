package com.sevenight.coldcrayon.allgacha.repository;

import com.sevenight.coldcrayon.allgacha.entity.Allgacha;
import com.sevenight.coldcrayon.allgacha.entity.GachaClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface AllgachaRepository extends JpaRepository<Allgacha, String> {
    Optional<Allgacha> findByAllgachaIdx(Long gachaIdx);

    List<Allgacha> findByAllgachaClass(GachaClass allgachaClass);
}
