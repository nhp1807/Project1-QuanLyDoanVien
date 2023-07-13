package com.example.QuanLyDoanVien.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.QuanLyDoanVien.entity.DoanVien;

import java.util.List;

@Repository
public interface DoanVienRepository extends JpaRepository<DoanVien, Long> {
    @Query("SELECT dv FROM DoanVien dv WHERE dv.idChiDoan = ?1")
    List<DoanVien> getListDoanVien(Long idChiDoan);

    @Query("SELECT dv FROM DoanVien dv WHERE dv.chucVu = ?1 AND dv.idChiDoan = ?2")
    DoanVien getDoanVienByChucVu(String chucVu, Long idChiDoan);

    @Query("SELECT dv FROM DoanVien dv WHERE dv.email = ?1")
    public DoanVien findByEmail(String email);

    @Query("SELECT dv FROM DoanVien dv WHERE dv.maDoanVien = ?1")
    public DoanVien findByMaDoanVien(String maDoanVien);

    List<DoanVien> findByHoTenContaining(String keyword);
}
