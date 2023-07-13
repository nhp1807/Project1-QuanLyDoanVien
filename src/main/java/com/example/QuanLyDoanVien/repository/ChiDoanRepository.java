package com.example.QuanLyDoanVien.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.QuanLyDoanVien.entity.ChiDoan;

import java.util.List;

@Repository
public interface ChiDoanRepository extends JpaRepository<ChiDoan, Long> {
    List<ChiDoan> findByTenChiDoanContaining(String keyword);
    @Query("SELECT cd FROM ChiDoan cd WHERE cd.tenChiDoan = ?1")
    ChiDoan findByTenChiDoan(String tenChiDoan);

    // @Query("SELECT cd.idChiDoan FROM ChiDoan cd WHERE cd.tenChiDoan = ?1")
    // Long findIdChiDoan(String tenChiDoan);
}
